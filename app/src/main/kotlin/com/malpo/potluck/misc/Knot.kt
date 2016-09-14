package com.malpo.potluck.misc

import rx.Observable
import rx.Observer
import rx.functions.Action0
import rx.functions.Action1
import rx.internal.util.ActionSubscriber
import timber.log.Timber

class Knot<T : Any?> private constructor(val from: Observable<T>, val to: Observer<T>) {
    companion object {
        fun <T> tie(source: Observable<T>, onNext: (T) -> Unit): Knot<T> = tie(source, Action1<T> { it -> onNext.invoke(it) }, Action1 { it -> Timber.e(it.message) })
        fun <T> tie(source: Observable<T>, onNext: Action1<T>): Knot<T> = tie(source, onNext, Action1 { it -> Timber.e(it.message) })
        fun <T> tie(source: Observable<T>, onNext: Action1<T>, onError: Action1<Throwable>): Knot<T> = tie(source, onNext, onError, EmptyAction<Any, Any, Any, Any, Any, Any, Any, Any, Any>())
        fun <T> tie(source: Observable<T>, onNext: Action1<T>, onError: Action1<Throwable>, onCompleted: Action0): Knot<T> = tie(source, ActionSubscriber(onNext, onError, onCompleted))
        fun <T> tie(source: Observable<T>, sink: Observer<T>): Knot<T> = Knot(source, sink)
    }
}