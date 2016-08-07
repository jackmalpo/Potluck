package com.metova.flyingsaucer.ui.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.metova.slim.Slim
import com.trello.rxlifecycle.ActivityEvent
import com.trello.rxlifecycle.ActivityEvent.*
import rx.subjects.BehaviorSubject

open class BaseActivity : AppCompatActivity() {

    protected val mLifecycleSubject = BehaviorSubject.create<ActivityEvent>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLifecycleSubject.onNext(CREATE)
        val layout = Slim.createLayout(this, this)
        if (layout != null) {
            setContentView(layout)
        }
        Slim.injectExtras(intent.extras, this)
    }

    override fun onStop() {
        super.onStop()
        mLifecycleSubject.onNext(STOP)
    }

    override fun onDestroy() {
        super.onDestroy()
        mLifecycleSubject.onNext(DESTROY)
    }

    override fun onPause() {
        super.onPause()
        mLifecycleSubject.onNext(PAUSE)
    }

    override fun onResume() {
        super.onResume()
        mLifecycleSubject.onNext(RESUME)
    }

    override fun onStart() {
        super.onStart()
        mLifecycleSubject.onNext(START)
    }
}
