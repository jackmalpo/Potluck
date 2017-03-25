package com.malpo.potluck.misc

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.internal.observers.LambdaObserver


class Knot<T : Any?> private constructor(val from: Observable<T>, val to: Observer<T>) {
    companion object {
        fun <T> tie(source: Observable<T>): Knot<T>
                = tie(source, Consumer<T> {}, Consumer {})

        fun <T> tie(source: Observable<T>, onNext: (T) -> Unit): Knot<T>
                = tie(source, Consumer<T> { it -> onNext.invoke(it) }, Consumer {})

        fun <T> tie(source: Observable<T>, onNext: Consumer<T>): Knot<T>
                = tie(source, onNext, Consumer {})

        fun <T> tie(source: Observable<T>, onNext: Consumer<T>, onError: Consumer<Throwable>): Knot<T>
                = tie(source, onNext, onError, Action {})

        fun <T> tie(source: Observable<T>, onNext: Consumer<T>, onError: Consumer<Throwable>, onCompleted: Action): Knot<T>
                = tie(source, LambdaObserver(onNext, onError, onCompleted, Consumer {}))

        fun <T> tie(source: Observable<T>, sink: Observer<T>): Knot<T> = Knot(source, sink)
    }
}

fun <T> Observable<T>.toNothing(): Knot<T> = Knot.tie(this)
infix fun <T> Observable<T>.to(onNext: (T) -> Unit): Knot<T> = Knot.tie(this, onNext)
infix fun <T> Observable<T>.to(onNext: Consumer<T>): Knot<T> = Knot.tie(this, onNext)

