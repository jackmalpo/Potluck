package com.malpo.potluck.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.malpo.potluck.R
import com.malpo.potluck.ui.Page.*
import com.malpo.potluck.ui.guest.PlaylistSearchFragment
import com.malpo.potluck.ui.host.HostFragment
import com.malpo.potluck.ui.screen.ScreenHolder
import com.malpo.potluck.ui.welcome.WelcomeFragment

class PotluckActivity : BaseActivity(), ScreenHolder {

    companion object {
        val PAGE_EXTRA = "page_extra"
    }

    private var current: Fragment? = null

    private var activityResultListener: ActivityResultListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component().inject(this)
        setContentView(R.layout.empty_container)
        var page: String = Page.WELCOME.value
        val pageExtra = intent.getStringExtra(PAGE_EXTRA)
//        val pageAction = intentToRoute(intent)
        when {
            pageExtra != null -> page = pageExtra
//            pageAction != null -> page = pageAction
        }
        if (savedInstanceState == null) {
            goTo(page)
        }
    }

    override fun goTo(page: String): Boolean {
        when (page) {
            WELCOME.value -> {
                replaceFragment(getFragment(WelcomeFragment::class.java), false, page)
                return true
            }
            HOST.value -> {
                replaceFragment(getFragment(HostFragment::class.java), false, page)
                return true
            }
            GUEST.value -> {
                replaceFragment(getFragment(PlaylistSearchFragment::class.java), false, page)
                return true
            }
            else -> return false
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val extraPath = getIntent().getStringExtra(PAGE_EXTRA)
        if (extraPath != null) {
            goTo(extraPath)
            return
        }
//        val actionPath = intentToRoute(intent)
//        if (actionPath != null) {
//            goTo(actionPath)
//        }
    }

    private fun replaceFragment(f: Fragment, backstack: Boolean, page: String?) {
        when {
            current !== f -> {
                val ft = supportFragmentManager.beginTransaction()
                val args = Bundle(1)
                args.putSerializable(PAGE_EXTRA, page)
                f.arguments = args
                ft.replace(R.id.container, f, f.javaClass.simpleName)
                if (backstack) ft.addToBackStack(null)
                ft.commit()
                current = f
            }
            page != null && f is ScreenHolder -> f.goTo(page)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        activityResultListener?.whenActivityResult(requestCode, resultCode, data)
    }

    fun setActivityResultListener(listener: ActivityResultListener) {
        activityResultListener = listener
    }

    interface ActivityResultListener {
        fun whenActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    }
}
