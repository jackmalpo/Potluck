package com.malpo.potluck.knot

interface Knot<out T, out O> {
    val from: T
    val to: O
}