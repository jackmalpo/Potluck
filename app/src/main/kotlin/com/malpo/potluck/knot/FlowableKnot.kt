package com.malpo.potluck.knot

import io.reactivex.Flowable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.internal.subscribers.LambdaSubscriber
import org.reactivestreams.Subscriber

class FlowableKnot<T : Any?>(override val from: Flowable<T>, override val to: Subscriber<T>) : Knot<Flowable<T>, Subscriber<T>> {
    companion object {
        fun <T> tie(source: Flowable<T>): FlowableKnot<T>
                = tie(source, Consumer<T> {}, Consumer {})

        fun <T> tie(source: Flowable<T>, onNext: (T) -> Unit): FlowableKnot<T>
                = tie(source, Consumer<T> { it -> onNext.invoke(it) }, Consumer {})

        fun <T> tie(source: Flowable<T>, onNext: Consumer<T>): FlowableKnot<T>
                = tie(source, onNext, Consumer {})

        fun <T> tie(source: Flowable<T>, onNext: Consumer<T>, onError: Consumer<Throwable>): FlowableKnot<T>
                = tie(source, onNext, onError, Action {})

        fun <T> tie(source: Flowable<T>, onNext: Consumer<T>, onError: Consumer<Throwable>, onCompleted: Action): FlowableKnot<T>
                = tie(source, LambdaSubscriber(onNext, onError, onCompleted, Consumer {}))

        fun <T> tie(source: Flowable<T>, sink: Subscriber<T>): FlowableKnot<T> = FlowableKnot(source, sink)
    }
}