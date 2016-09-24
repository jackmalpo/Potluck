package com.malpo.potluck.ui.host.login

import android.content.Intent
import com.malpo.potluck.di.component.ViewComponent
import com.malpo.potluck.ui.PotluckActivity
import com.malpo.potluck.ui.host.login.screen.HostLoginPresenter
import com.malpo.potluck.ui.host.login.screen.HostLoginScreen
import com.malpo.potluck.ui.screen.ScreenFragment

class HostLoginFragment : ScreenFragment<HostLoginScreen.Presenter, HostLoginScreen.View, HostLoginViewAndroid>(), PotluckActivity.ActivityResultListener {

    override fun inject(component: ViewComponent) {
        component.inject(this)
    }

    override fun buildView(): HostLoginViewAndroid {
        return HostLoginViewAndroid()
    }

    override fun onStart() {
        super.onStart()
        (activity as PotluckActivity).setActivityResultListener(this)
        (presenter as HostLoginPresenter).initAuth(activity)
    }

    override fun whenActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        (presenter as HostLoginPresenter).handleLoginResult(requestCode, resultCode, data)
    }
}
