package com.malpo.potluck.ui.host

import android.os.Bundle
import android.support.v4.app.Fragment
import com.malpo.potluck.R
import com.malpo.potluck.di.component.ViewComponent
import com.malpo.potluck.ui.PotluckActivity
import com.malpo.potluck.ui.host.login.HostLoginFragment
import com.malpo.potluck.ui.host.screen.HostScreen
import com.malpo.potluck.ui.screen.ScreenFragment
import com.malpo.potluck.ui.screen.ScreenHolder

class HostFragment : ScreenFragment<HostScreen.Presenter, HostScreen.View, HostViewAndroid>(), ScreenHolder {

    var currentFragment: Fragment? = null

    var loginFragment: HostLoginFragment? = null

    override fun inject(component: ViewComponent) {
        component.inject(this)
    }

    override fun buildView(): HostViewAndroid {
        return HostViewAndroid()
    }

    override fun goTo(page: String): Boolean {
        if (page.startsWith(HostScreen.prefix)) {
            when (page) {
                HostScreen.Page.login.value -> {
                    view.updatePage().call(HostScreen.Page.login)
                    switchPage(loginFragment())
                    return true
                }
                else -> return false
            }
        } else {
            return super.goTo(page)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var path = HostScreen.Page.login.value
        val extraPath = if (arguments != null) arguments.getString(PotluckActivity.PAGE_EXTRA) else null
        if (extraPath != null && extraPath != HostScreen.prefix) path = extraPath
        if (savedInstanceState == null) goTo(path)
    }

    private fun switchPage(f: Fragment?) {
        if (f != null) {
            val ft = childFragmentManager.beginTransaction()
            if (!f.isAdded) ft.add(R.id.container, f, f.javaClass.simpleName) else ft.show(f)
            if (currentFragment != null) ft.hide(currentFragment)
            ft.commit()
            currentFragment = f
        }
    }

    private fun <T : Fragment> fragmentFrom(clazz: Class<T>): T {
        val f = childFragmentManager.findFragmentByTag(clazz.simpleName)
        when {
            !clazz.isInstance(f) -> {
                try {
                    return clazz.newInstance()
                } catch (e: java.lang.InstantiationException) {
                    throw RuntimeException(e)
                } catch (e: IllegalAccessException) {
                    throw RuntimeException(e)
                }
            }
        }
        return clazz.cast(f)
    }

    fun loginFragment(): HostLoginFragment = loginFragment ?: fragmentFrom(HostLoginFragment::class.java)
}
