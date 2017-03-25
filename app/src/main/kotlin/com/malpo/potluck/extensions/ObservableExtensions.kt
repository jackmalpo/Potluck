package com.malpo.potluck.extensions


import com.trello.rxlifecycle2.RxLifecycle
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.Observable
import io.reactivex.processors.BehaviorProcessor


/**
 * Observable Extensions
 */
fun <T> Observable<T>.bindToFragment(sub: BehaviorProcessor<FragmentEvent>): Observable<T> =
        compose(RxLifecycle.bindUntilEvent(sub.toObservable(), FragmentEvent.DESTROY))
