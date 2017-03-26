package com.malpo.potluck.knot

import io.reactivex.MaybeObserver
import io.reactivex.disposables.Disposable
import io.reactivex.exceptions.CompositeException
import io.reactivex.exceptions.Exceptions
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import io.reactivex.internal.disposables.DisposableHelper
import io.reactivex.plugins.RxJavaPlugins

import java.util.concurrent.atomic.AtomicReference

class LambdaMaybeObserver<T>(internal val onNext: Consumer<in T>, internal val onError: Consumer<in Throwable>,
                             internal val onComplete: Action,
                             internal val onSubscribe: Consumer<in Disposable>) : AtomicReference<Disposable>(),
        MaybeObserver<T>, Disposable {

    override fun onSubscribe(s: Disposable) {
        if (DisposableHelper.setOnce(this, s)) {
            try {
                onSubscribe.accept(this)
            } catch (ex: Throwable) {
                Exceptions.throwIfFatal(ex)
                onError(ex)
            }

        }
    }

    override fun onSuccess(t: T) {
        if (!isDisposed) {
            try {
                onNext.accept(t)
            } catch (e: Throwable) {
                Exceptions.throwIfFatal(e)
                onError(e)
            }

        }
    }

    override fun onError(t: Throwable) {
        if (!isDisposed) {
            lazySet(DisposableHelper.DISPOSED)
            try {
                onError.accept(t)
            } catch (e: Throwable) {
                Exceptions.throwIfFatal(e)
                RxJavaPlugins.onError(CompositeException(t, e))
            }

        }
    }

    override fun onComplete() {
        if (!isDisposed) {
            lazySet(DisposableHelper.DISPOSED)
            try {
                onComplete.run()
            } catch (e: Throwable) {
                Exceptions.throwIfFatal(e)
                RxJavaPlugins.onError(e)
            }

        }
    }

    override fun dispose() {
        DisposableHelper.dispose(this)
    }

    override fun isDisposed(): Boolean {
        return get() === DisposableHelper.DISPOSED
    }

    companion object {

        private val serialVersionUID = -7251123623727029452L
    }
}