package com.malpo.potluck.extensions

import com.trello.rxlifecycle2.RxLifecycle
import com.trello.rxlifecycle2.android.FragmentEvent
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.schedulers.Schedulers

fun <T> Observable<T>.bindToFragment(sub: BehaviorProcessor<FragmentEvent>): Observable<T> =
        compose(RxLifecycle.bindUntilEvent(sub.toObservable(), FragmentEvent.DESTROY))

fun <T> Flowable<T>.bindToFragment(sub: BehaviorProcessor<FragmentEvent>): Flowable<T> =
        compose(RxLifecycle.bindUntilEvent(sub.toObservable(), FragmentEvent.DESTROY))

fun <T> Maybe<T>.bindToFragment(sub: BehaviorProcessor<FragmentEvent>): Maybe<T> =
        compose(RxLifecycle.bindUntilEvent(sub.toObservable(), FragmentEvent.DESTROY))

fun <T> Observable<T>.observeMain() : Observable<T> = this.observeOn(AndroidSchedulers.mainThread())
fun <T> Observable<T>.subscribeMain() : Observable<T> = this.subscribeOn(AndroidSchedulers.mainThread())
fun <T> Observable<T>.observeIo() : Observable<T> = this.observeOn(Schedulers.io())
fun <T> Observable<T>.subscribeIo() : Observable<T> = this.subscribeOn(Schedulers.io())

fun <T> Flowable<T>.observeMain() : Flowable<T> = this.observeOn(AndroidSchedulers.mainThread())
fun <T> Flowable<T>.subscribeMain() : Flowable<T> = this.subscribeOn(AndroidSchedulers.mainThread())
fun <T> Flowable<T>.observeIo() : Flowable<T> = this.observeOn(Schedulers.io())
fun <T> Flowable<T>.subscribeIo() : Flowable<T> = this.subscribeOn(Schedulers.io())

fun <T> Maybe<T>.observeMain() : Maybe<T> = this.observeOn(AndroidSchedulers.mainThread())
fun <T> Maybe<T>.subscribeMain() : Maybe<T> = this.subscribeOn(AndroidSchedulers.mainThread())
fun <T> Maybe<T>.observeIo() : Maybe<T> = this.observeOn(Schedulers.io())
fun <T> Maybe<T>.subscribeIo() : Maybe<T> = this.subscribeOn(Schedulers.io())
