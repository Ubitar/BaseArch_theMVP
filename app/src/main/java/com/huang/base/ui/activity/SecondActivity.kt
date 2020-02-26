package com.huang.base.ui.activity

import android.os.Bundle
import butterknife.OnClick
import com.alibaba.android.arouter.facade.annotation.Route
import com.common.common.IntentRouter
import com.common.ui.activity.BaseActivity
import com.huang.base.R
import com.huang.base.ui.delegate.SecondDelegate
import com.huang.base.ui.fragment.SecondFragment

@Route(path = IntentRouter.SECOND_ACITIVTY)
class SecondActivity : BaseActivity<SecondDelegate>() {
    override fun getDelegateClass(): Class<SecondDelegate> {
        return SecondDelegate::class.java
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadRootFragment(R.id.layoutContainer, SecondFragment())
    }

}