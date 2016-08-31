package com.malpo.potluck.misc

import rx.functions.*

/**
 * Had to pull this out from Rx because of Kotlin type checks
 */
public class EmptyAction<T0, T1, T2, T3, T4, T5, T6, T7, T8> : Action0, Action1<T0>, Action2<T0, T1>, Action3<T0, T1, T2>, Action4<T0, T1, T2, T3>, Action5<T0, T1, T2, T3, T4>, Action6<T0, T1, T2, T3, T4, T5>, Action7<T0, T1, T2, T3, T4, T5, T6>, Action8<T0, T1, T2, T3, T4, T5, T6, T7>, Action9<T0, T1, T2, T3, T4, T5, T6, T7, T8>, ActionN {

    override fun call() {
        // deliberately no op
    }

    override fun call(t1: T0) {
        // deliberately no op
    }

    override fun call(t1: T0, t2: T1) {
        // deliberately no op
    }

    override fun call(t1: T0, t2: T1, t3: T2) {
        // deliberately no op
    }

    override fun call(t1: T0, t2: T1, t3: T2, t4: T3) {
        // deliberately no op
    }

    override fun call(t1: T0, t2: T1, t3: T2, t4: T3, t5: T4) {
        // deliberately no op
    }

    override fun call(t1: T0, t2: T1, t3: T2, t4: T3, t5: T4, t6: T5) {
        // deliberately no op
    }

    override fun call(t1: T0, t2: T1, t3: T2, t4: T3, t5: T4, t6: T5, t7: T6) {
        // deliberately no op
    }

    override fun call(t1: T0, t2: T1, t3: T2, t4: T3, t5: T4, t6: T5, t7: T6, t8: T7) {
        // deliberately no op
    }

    override fun call(t1: T0, t2: T1, t3: T2, t4: T3, t5: T4, t6: T5, t7: T6, t8: T7, t9: T8) {
        // deliberately no op
    }

    override fun call(vararg args: Any) {
        // deliberately no op
    }
}