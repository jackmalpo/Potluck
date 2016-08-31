package com.malpo.potluck.ui.mvp

interface Screen<in X>{
    fun bind(elements: MutableCollection<Knot<*>>, x: X)
}

