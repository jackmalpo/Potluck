package com.malpo.potluck.ui.mvp

interface ScreenPresenter<V : ScreenView<V, P>, P : ScreenPresenter<V, P>> : Screen<V>