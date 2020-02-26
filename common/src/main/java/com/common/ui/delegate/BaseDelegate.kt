package com.common.ui.delegate

import butterknife.ButterKnife
import butterknife.Unbinder

/**
 * Created by laohuang on 2018/9/7.
 */

abstract class BaseDelegate : AppDelegate() {

    protected var unbinder: Unbinder? = null

    override fun initWidget() {
        unbinder = ButterKnife.bind(this, rootView!!)
    }

    fun onSupportInvisible() {

    }

    fun onSupportVisible() {

    }

    override fun onDestroyWidget() {
        unbinder?.unbind()
        unbinder = null
    }

}
