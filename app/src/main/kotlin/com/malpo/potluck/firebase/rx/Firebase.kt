/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.malpo.potluck.firebase.rx

import com.google.firebase.database.*
import com.malpo.potluck.firebase.FirebaseChildEvent
import com.malpo.potluck.firebase.FirebaseChildEvent.EventType
import com.malpo.potluck.firebase.exceptions.*
import rx.Observable
import rx.Subscriber
import rx.functions.Func1
import rx.subscriptions.Subscriptions
import javax.inject.Singleton

/**
 * The class is used as wrapper to firebase functionlity with
 * RxJava
 */
@Singleton
class Firebase {

    val database: FirebaseDatabase
        get() {
            return FirebaseDatabase.getInstance()
        }


    /**
     * This methods observes a firebase query and returns back
     * an Observable of the [DataSnapshot]
     * when the firebase client uses a [ValueEventListener]

     * @param ref [Query] this is reference of a Firebase Query
     * *
     * @return an [rx.Observable] of datasnapshot to use
     */
    fun observeValueEvent(ref: Query): Observable<DataSnapshot> {
        return Observable.create { subscriber ->
            val listener = ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    subscriber.onNext(dataSnapshot)
                }

                override fun onCancelled(error: DatabaseError) {
                    attachErrorHandler<DataSnapshot>(subscriber, error)
                }
            })

            // When the subscription is cancelled, remove the listener
            subscriber.add(Subscriptions.create { ref.removeEventListener(listener) })
        }
    }

    /**
     * This methods observes a firebase query and returns back ONCE
     * an Observable of the [DataSnapshot]
     * when the firebase client uses a [ValueEventListener]

     * @param ref [Query] this is reference of a Firebase Query
     * *
     * @return an [rx.Observable] of datasnapshot to use
     */
    fun observeSingleValue(ref: Query): Observable<DataSnapshot> {
        return Observable.create { subscriber ->
            val listener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    subscriber.onNext(dataSnapshot)
                }

                override fun onCancelled(error: DatabaseError) {
                    attachErrorHandler<DataSnapshot>(subscriber, error)
                }
            }

            ref.addValueEventListener(listener)

            // When the subscription is cancelled, remove the listener
            subscriber.add(Subscriptions.create { ref.removeEventListener(listener) })
        }
    }

    /**
     * This methods observes a firebase query and returns back
     * an Observable of the [DataSnapshot]
     * when the firebase client uses a [ChildEventListener]

     * @param ref [Query] this is reference of a Firebase Query
     * *
     * @return an [rx.Observable] of [FirebaseChildEvent]
     * * to use
     */
    fun observeChildEvent(ref: Query): Observable<FirebaseChildEvent> {
        return Observable.create { subscriber ->
            val childEventListener = ref.addChildEventListener(object : ChildEventListener {

                override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String) {
                    subscriber.onNext(
                            FirebaseChildEvent(dataSnapshot, previousChildName, EventType.ADDED))
                }

                override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String) {
                    subscriber.onNext(
                            FirebaseChildEvent(dataSnapshot, previousChildName, EventType.CHANGED))
                }

                override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                    subscriber.onNext(FirebaseChildEvent(dataSnapshot, EventType.REMOVED))
                }

                override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String) {
                    subscriber.onNext(
                            FirebaseChildEvent(dataSnapshot, previousChildName, EventType.MOVED))
                }

                override fun onCancelled(error: DatabaseError) {
                    attachErrorHandler<FirebaseChildEvent>(subscriber, error)
                }
            })
            // this is used to remove the listener when the subscriber is
            // cancelled (unsubscribe)
            subscriber.add(Subscriptions.create { ref.removeEventListener(childEventListener) })
        }
    }

    /**
     * Creates an observable only for the child added method

     * @param ref [Query] this is reference of a Firebase Query
     * *
     * @return an [rx.Observable] of [FirebaseChildEvent]
     * * to use
     */
    fun observeChildAdded(ref: Query): Observable<FirebaseChildEvent> {
        return observeChildEvent(ref).filter(filterChildEvent(EventType.ADDED))
    }

    /**
     * Creates an observable only for the child changed method

     * @param ref [Query] this is reference of a Firebase Query
     * *
     * @return an [rx.Observable] of [FirebaseChildEvent]
     * * to use
     */
    fun observeChildChanged(ref: Query): Observable<FirebaseChildEvent> {
        return observeChildEvent(ref).filter(filterChildEvent(EventType.CHANGED))
    }

    /**
     * Creates an observable only for the child removed method

     * @param ref [Query] this is reference of a Firebase Query
     * *
     * @return an [rx.Observable] of [FirebaseChildEvent]
     * * to use
     */
    fun observeChildRemoved(ref: Query): Observable<FirebaseChildEvent> {
        return observeChildEvent(ref).filter(filterChildEvent(EventType.REMOVED))
    }

    /**
     * Creates an observable only for the child removed method

     * @param ref [Query] this is reference of a Firebase Query
     * *
     * @return an [rx.Observable] of [FirebaseChildEvent]
     * * to use
     */
    fun observeChildMoved(ref: Query): Observable<FirebaseChildEvent> {
        return observeChildEvent(ref).filter(filterChildEvent(EventType.MOVED))
    }

    /**
     * Functions which filters a stream of [Observable] according to firebase
     * child event type

     * @param type [FirebaseChildEvent]
     * *
     * @return [Func1] a function which returns a boolean if the type are equals
     */
    private fun filterChildEvent(type: EventType): (FirebaseChildEvent) -> Boolean {
        return { firebaseChildEvent -> firebaseChildEvent.eventType === type }
    }

    /**
     * This method add to subsriber the proper error according to the

     * @param subscriber [rx.Subscriber]
     * *
     * @param datab      [com.google.firebase.database.DatabaseError]
     * *
     * @param         generic subscriber
     */
    private fun <T> attachErrorHandler(subscriber: Subscriber<in T>, datab: DatabaseError) {
        when (datab.code) {
            DatabaseError.INVALID_TOKEN -> subscriber.onError(FirebaseInvalidTokenException(datab.message))
            DatabaseError.EXPIRED_TOKEN -> subscriber.onError(FirebaseExpiredTokenException(datab.message))
            DatabaseError.NETWORK_ERROR -> subscriber.onError(FirebaseNetworkErrorException(datab.message))
            DatabaseError.PERMISSION_DENIED -> subscriber.onError(FirebasePermissionDeniedException(datab.message))
            else -> subscriber.onError(FirebaseGeneralException(datab.message))
        }
    }

}
