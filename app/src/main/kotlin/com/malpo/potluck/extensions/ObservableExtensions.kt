package com.malpo.potluck.extensions

import com.trello.rxlifecycle.ActivityEvent
import com.trello.rxlifecycle.FragmentEvent
import com.trello.rxlifecycle.RxLifecycle
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subjects.BehaviorSubject

/**
 * Observable Extensions
 */
fun <T> Observable<T>.bindToFragment(sub : BehaviorSubject<FragmentEvent>): Observable<T> =
        subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindUntilEvent<T>(FragmentEvent.DESTROY, sub))

fun <T> Observable<T>.bindToActivity(sub : BehaviorSubject<ActivityEvent>): Observable<T> =
        subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindUntilEvent<T>(ActivityEvent.DESTROY, sub))

fun <T> bindUntilEvent(event: FragmentEvent, sub: BehaviorSubject<FragmentEvent>): Observable.Transformer<T, T> {
    return RxLifecycle.bindUntilFragmentEvent<T>(sub, event)
}

fun <T> bindUntilEvent(event: ActivityEvent, sub: BehaviorSubject<ActivityEvent>): Observable.Transformer<T, T> {
    return RxLifecycle.bindUntilActivityEvent<T>(sub, event)
}

