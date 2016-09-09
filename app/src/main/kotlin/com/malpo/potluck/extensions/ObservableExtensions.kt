package com.malpo.potluck.extensions

import com.trello.rxlifecycle.FragmentEvent
import com.trello.rxlifecycle.RxLifecycle
import rx.Observable
import rx.subjects.BehaviorSubject

/**
 * Observable Extensions
 */
fun <T> Observable<T>.bindToFragment(sub: BehaviorSubject<FragmentEvent>): Observable<T> =
        compose(bindUntilEvent<T>(sub))

fun <T> bindUntilEvent(sub: BehaviorSubject<FragmentEvent>): Observable.Transformer<T, T> {
    return RxLifecycle.bindFragment(sub)
}
