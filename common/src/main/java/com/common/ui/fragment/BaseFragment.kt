package com.common.ui.fragment

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.common.ui.dialog.LoadingDialog
import com.common.ui.delegate.BaseDelegate

import java.util.HashSet

import butterknife.ButterKnife
import butterknife.Unbinder
import com.common.ui.common.IController
import com.common.ui.dialog.BaseDialogFragment
import org.greenrobot.eventbus.EventBus

/**
 * Created by laohuang on 2018/9/9.
 */

abstract class BaseFragment<S : BaseDelegate> : BaseDelegateFragment<S>(), IController {

    private val postRefreshEvent = HashSet<Int>(10)

    protected var unbinder: Unbinder? = null

    protected var loadingDialog: LoadingDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        if (view != null) {
            unbinder = ButterKnife.bind(this, view)
        }
        return attachToSwipeBack(view!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isRegisterEventBus()) EventBus.getDefault().register(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder?.unbind()
        unbinder = null
    }

    override fun showLoading(message: String, outsideEnable: Boolean, backEnable: Boolean, listener: ((dialog: BaseDialogFragment) -> Unit)?) {
        if (loadingDialog == null)
            loadingDialog = LoadingDialog.Builder()
                    .setOutsideEnable(outsideEnable)
                    .setText(message)
                    .setOutsideEnable(outsideEnable)
                    .setBackEnable(backEnable)
                    .setOnDismissListener(listener)
                    .build()
        loadingDialog?.show(childFragmentManager)
    }

    override fun hideLoading() {
        loadingDialog?.dismissAllowingStateLoss()
    }

    /**
     * 在fragment有空(出现在用于面前)的时候再刷新
     */
    fun postRefresh(type: Int) {
        if (isSupportVisible)
            refresh(type)
        else postRefreshEvent.add(type)
    }

    /**
     * 立即刷新 ，推荐使用PostRefresh
     */
    fun refresh(type: Int) {}

    override fun onSupportVisible() {
        super.onSupportVisible()
        if (!isFirstVisible)
            for (type in postRefreshEvent) refresh(type)
        postRefreshEvent.clear()
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
    }

    /** 是否注册EventBus */
    open fun isRegisterEventBus(): Boolean {
        return false
    }

}
