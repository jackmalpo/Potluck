package com.malpo.potluck.knot

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.functions.Consumer

fun <T> Observable<T>.solo(): ObservableKnot<T> = ObservableKnot.tie(this)
infix fun <T> Observable<T>.to(onNext: (T) -> Unit): ObservableKnot<T> = ObservableKnot.tie(this, onNext)
infix fun <T> Observable<T>.to(onNext: Consumer<T>): ObservableKnot<T> = ObservableKnot.tie(this, onNext)

fun <T> Flowable<T>.solo(): FlowableKnot<T> = FlowableKnot.tie(this)
infix fun <T> Flowable<T>.to(onNext: (T) -> Unit): FlowableKnot<T> = FlowableKnot.tie(this, onNext)
infix fun <T> Flowable<T>.to(onNext: Consumer<T>): FlowableKnot<T> = FlowableKnot.tie(this, onNext)

fun <T> Maybe<T>.solo(): MaybeKnot<T> = MaybeKnot.tie(this)
infix fun <T> Maybe<T>.to(onNext: (T) -> Unit): MaybeKnot<T> = MaybeKnot.tie(this, onNext)
infix fun <T> Maybe<T>.to(onNext: Consumer<T>): MaybeKnot<T> = MaybeKnot.tie(this, onNext)

