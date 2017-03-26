package com.malpo.potluck.knot

import io.reactivex.Maybe
import io.reactivex.MaybeObserver
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

class MaybeKnot<T : Any?>(override val from: Maybe<T>, override val to: MaybeObserver<T>) : Knot<Maybe<T>, MaybeObserver<T>> {
    companion object {
        fun <T> tie(source: Maybe<T>): MaybeKnot<T>
                = tie(source, Consumer<T> {}, Consumer {})

        fun <T> tie(source: Maybe<T>, onNext: (T) -> Unit): MaybeKnot<T>
                = tie(source, Consumer<T> { it -> onNext.invoke(it) }, Consumer {})

        fun <T> tie(source: Maybe<T>, onNext: Consumer<T>): MaybeKnot<T>
                = tie(source, onNext, Consumer {})

        fun <T> tie(source: Maybe<T>, onNext: Consumer<T>, onError: Consumer<Throwable>): MaybeKnot<T>
                = tie(source, onNext, onError, Action {})

        fun <T> tie(source: Maybe<T>, onNext: Consumer<T>, onError: Consumer<Throwable>, onCompleted: Action): MaybeKnot<T>
                = tie(source, LambdaMaybeObserver(onNext, onError, onCompleted, Consumer {}))

        fun <T> tie(source: Maybe<T>, sink: MaybeObserver<T>): MaybeKnot<T> = MaybeKnot(source, sink)
    }
}
