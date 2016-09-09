package com.malpo.potluck.ui.screen

interface ScreenPresenter<V : ScreenView<V, P>, P : ScreenPresenter<V, P>> : Screen<V>
interface ScreenView<V : ScreenView<V, P>, P : ScreenPresenter<V, P>> : Screen<P>

interface Screen<in X> {
    fun bind(knots: MutableCollection<Knot<*>>, x: X)
}

fun <X> MutableCollection<X>.wrap(vararg items: X) {
    items.forEach { each -> this.add(each) }
}

interface ScreenHolder
