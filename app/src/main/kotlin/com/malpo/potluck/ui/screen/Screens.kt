package com.malpo.potluck.ui.screen

import com.malpo.potluck.misc.Knot

interface ScreenPresenter<V : ScreenView<V, P>, P : ScreenPresenter<V, P>> : Screen<V>
interface ScreenView<V : ScreenView<V, P>, P : ScreenPresenter<V, P>> : Screen<P>

interface Screen<in X> {
    fun bind(holder: ScreenHolder, x: X, knots: MutableCollection<Knot<*>>)
}

interface ScreenHolder {
    fun goTo(page: String): Boolean
}

fun <X> MutableCollection<X>.tie(vararg items: X) {
    items.forEach { each -> this.add(each) }
}


