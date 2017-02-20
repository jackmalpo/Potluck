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
package com.malpo.potluck.firebase


import com.google.firebase.database.DataSnapshot

/**

 * This class represents a firebase child event when we are
 * using the [com.google.firebase.database.ChildEventListener]
 */
class FirebaseChildEvent {

    enum class EventType {
        ADDED, CHANGED, REMOVED, MOVED
    }

    /**
     * An [DataSnapshot] instance contains data from a Firebase location
     */
    var dataSnapshot: DataSnapshot? = null

    /**
     * The key name of sibling location ordered before the new child
     */
    var previousChildName: String? = null

    /**
     * Represents the type of the children event
     */
    var eventType: EventType? = null

    constructor(dataSnapshot: DataSnapshot, previousChildName: String,
                eventType: EventType) {
        this.dataSnapshot = dataSnapshot
        this.previousChildName = previousChildName
        this.eventType = eventType
    }

    constructor(dataSnapshot: DataSnapshot, eventType: EventType) {
        this.dataSnapshot = dataSnapshot
        this.eventType = eventType
    }
}
