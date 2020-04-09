package com.huang.base.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.common.common.IntentRouter
import com.common.ui.activity.BaseActivity
import com.huang.base.R
import com.huang.base.ui.delegate.MainDelegate
import com.huang.base.ui.fragment.SecondFragment

@Route(path = IntentRouter.MAIN_ACITIVTY)
class MainActivity : BaseActivity<MainDelegate>() {

    override fun getDelegateClass(): Class<MainDelegate> {
        return MainDelegate::class.java
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadRootFragment(R.id.layoutContainer,SecondFragment())
    }

}
