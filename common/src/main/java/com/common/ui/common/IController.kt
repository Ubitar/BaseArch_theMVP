package com.common.ui.common

import androidx.lifecycle.LifecycleOwner
import com.common.ui.dialog.BaseDialogFragment

interface IController : LifecycleOwner {
    fun showLoading(message: String = "加载中", outsideEnable: Boolean = false, backEnable: Boolean = true, listener: ((dialog: BaseDialogFragment) -> Unit)? = null)

    fun hideLoading()
}