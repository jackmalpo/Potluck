package com.malpo.potluck.extensions

import com.trello.rxlifecycle2.RxLifecycle
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.processors.BehaviorProcessor


/**
 * Observable Extensions
 */
fun <T> Observable<T>.bindToFragment(sub: BehaviorProcessor<FragmentEvent>): Observable<T> =
        compose(RxLifecycle.bindUntilEvent(sub.toObservable(), FragmentEvent.DESTROY))

fun <T> Flowable<T>.bindToFragment(sub: BehaviorProcessor<FragmentEvent>): Flowable<T> =
        compose(RxLifecycle.bindUntilEvent(sub.toObservable(), FragmentEvent.DESTROY))

fun <T> Maybe<T>.bindToFragment(sub: BehaviorProcessor<FragmentEvent>): Maybe<T> =
        compose(RxLifecycle.bindUntilEvent(sub.toObservable(), FragmentEvent.DESTROY))
