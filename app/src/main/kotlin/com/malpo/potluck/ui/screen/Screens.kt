package com.malpo.potluck.ui.screen

import com.malpo.potluck.misc.Knot
import com.malpo.potluck.ui.Page

interface ScreenPresenter<V : ScreenView<V, P>, P : ScreenPresenter<V, P>> : Screen<V>
interface ScreenView<V : ScreenView<V, P>, P : ScreenPresenter<V, P>> : Screen<P>

interface Screen<in X> {
    fun bind(holder: ScreenHolder, x: X, knots: MutableCollection<Knot<*>>)
}

interface ScreenHolder {
    fun goTo(page: Page): Boolean
}

fun <X> MutableCollection<X>.wrap(vararg items: X) {
    items.forEach { each -> this.add(each) }
}


