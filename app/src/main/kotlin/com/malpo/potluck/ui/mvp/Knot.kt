@file:Suppress("UNUSED_EXPRESSION")

package com.malpo.potluck.ui.mvp

import com.malpo.potluck.misc.EmptyAction
import rx.Observable
import rx.Observer
import rx.functions.Action0
import rx.functions.Action1
import rx.internal.util.ActionSubscriber

class Knot<T : Any?> private constructor(val from: Observable<T>, val to: Observer<T>) {

    companion object {
        fun <T> tie(source: Observable<T>, onNext : () -> T): Knot<T> {
            return tie(source, Action1 { onNext }, Action1 {  })
        }

        fun <T> tie(source: Observable<T>, onNext: Action1<T>, onError: Action1<Throwable>): Knot<T> {
            return tie(source, onNext, onError, EmptyAction<Any, Any, Any, Any, Any, Any, Any, Any, Any>())
        }

        fun <T> tie(source: Observable<T>, onNext: Action1<T>, onError: Action1<Throwable>, onCompleted: Action0): Knot<T> {
            return tie(source, ActionSubscriber(onNext, onError, onCompleted))
        }

        fun <T> tie(source: Observable<T>, sink: Observer<T>): Knot<T> {
            return Knot(source, sink)
        }
    }
}