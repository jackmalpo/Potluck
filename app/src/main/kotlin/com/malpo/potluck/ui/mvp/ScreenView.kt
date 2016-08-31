package com.malpo.potluck.ui.mvp

interface ScreenView<V : ScreenView<V, P>, P : ScreenPresenter<V, P>> : Screen<P>