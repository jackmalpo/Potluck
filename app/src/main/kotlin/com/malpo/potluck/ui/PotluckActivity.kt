package com.malpo.potluck.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.malpo.potluck.R
import com.malpo.potluck.ui.Page.*
import com.malpo.potluck.ui.guest.PlaylistSearchFragment
import com.malpo.potluck.ui.host.HostLoginFragment
import com.malpo.potluck.ui.screen.ScreenHolder
import com.malpo.potluck.ui.welcome.WelcomeFragment

class PotluckActivity : BaseActivity(), ScreenHolder {

    companion object {
        val PAGE_EXTRA = "page_extra"
    }

    private var current: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component().inject(this)
        setContentView(R.layout.potluck_activity)
        var page = Page.WELCOME
        val pageExtra = intent.getSerializableExtra(PAGE_EXTRA) as Page?
//        val pageAction = intentToRoute(intent)
        when {
            pageExtra != null -> page = pageExtra
//            pageAction != null -> page = pageAction
        }
        if (savedInstanceState == null) {
            goTo(page)
        }
    }

    override fun goTo(page: Page): Boolean {
        when (page) {
            WELCOME -> {
                replaceFragment(getFragment(WelcomeFragment::class.java), false, page)
                return true
            }
            HOST_LOGIN -> {
                replaceFragment(getFragment(HostLoginFragment::class.java), false, page)
                return true
            }
            PLAYLIST_SEARCH -> {
                replaceFragment(getFragment(PlaylistSearchFragment::class.java), false, page)
                return true
            }
            else -> return false
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val extraPath = getIntent().getSerializableExtra(PAGE_EXTRA) as Page?
        if (extraPath != null) {
            goTo(extraPath)
            return
        }
//        val actionPath = intentToRoute(intent)
//        if (actionPath != null) {
//            goTo(actionPath)
//        }
    }

    private fun replaceFragment(f: Fragment, backstack: Boolean, page: Page?) {
        if (current !== f) {
            val ft = supportFragmentManager.beginTransaction()
            val args = Bundle(1)
            args.putSerializable(PAGE_EXTRA, page)
            f.arguments = args
            ft.replace(R.id.container, f, f.javaClass.simpleName)
            if (backstack) {
                ft.addToBackStack(null)
            }
            ft.commit()
            current = f
        } else if (page != null && f is ScreenHolder) {
            f.goTo(page)
        }
    }

    private fun getFragment(clazz: Class<out Fragment>): Fragment {
        val f = supportFragmentManager.findFragmentByTag(clazz.simpleName)
        when {
            f != null && clazz.isInstance(f) -> return f
            else -> try {
                return clazz.newInstance()
            } catch (e: InstantiationException) {
                throw RuntimeException(e)
            } catch (e: IllegalAccessException) {
                throw RuntimeException(e)
            }
        }
    }
}
