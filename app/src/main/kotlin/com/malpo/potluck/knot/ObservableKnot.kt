package com.malpo.potluck.knot

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.internal.observers.LambdaObserver

class ObservableKnot<T : Any?>(override val from: Observable<T>, override val to: Observer<T>) : Knot<Observable<T>, Observer<T>> {
    companion object {
        fun <T> tie(source: Observable<T>): ObservableKnot<T>
                = tie(source, Consumer<T> {}, Consumer {})

        fun <T> tie(source: Observable<T>, onNext: (T) -> Unit): ObservableKnot<T>
                = tie(source, Consumer<T> { it -> onNext.invoke(it) }, Consumer {})

        fun <T> tie(source: Observable<T>, onNext: Consumer<T>): ObservableKnot<T>
                = tie(source, onNext, Consumer {})

        fun <T> tie(source: Observable<T>, onNext: Consumer<T>, onError: Consumer<Throwable>): ObservableKnot<T>
                = tie(source, onNext, onError, Action {})

        fun <T> tie(source: Observable<T>, onNext: Consumer<T>, onError: Consumer<Throwable>, onCompleted: Action): ObservableKnot<T>
                = tie(source, LambdaObserver(onNext, onError, onCompleted, Consumer {}))

        fun <T> tie(source: Observable<T>, sink: Observer<T>): ObservableKnot<T> = ObservableKnot(source, sink)
    }
}