package com.common.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.WindowManager
import androidx.fragment.app.Fragment
import butterknife.ButterKnife
import butterknife.Unbinder
import com.blankj.utilcode.util.KeyboardUtils
import com.common.ui.common.IController
import com.common.ui.delegate.BaseDelegate
import com.common.ui.dialog.LoadingDialog
import com.gyf.immersionbar.ImmersionBar
import com.huang.lib.BaseDialogFragment
import com.huang.lib.util.ActivityManager
import com.huang.lib.util.KeyboardUtil
import com.huang.lib.util.T
import com.noober.background.BackgroundLibrary
import org.greenrobot.eventbus.EventBus

/**
 * Created by laohuang on 2018/9/9.
 */

abstract class BaseActivity<S : BaseDelegate> : BaseDelegateActivity<S>(), IController {

    var isDoubleBack: Boolean = false
    var isAutoHideKeyBoard = true
    protected var curMillsTime: Long = 0

    protected var unbinder: Unbinder? = null

    protected var loadingDialog: LoadingDialog? = null

    protected val activity: BaseActivity<*>
        get() = this

    override fun onCreate(savedInstanceState: Bundle?) {
        BackgroundLibrary.inject(this)
        super.onCreate(savedInstanceState)

        unbinder = ButterKnife.bind(this)

        getImmersionBar()?.init()

        ActivityManager.getManager().addActivity(this)

        if (isRegisterEventBus()) EventBus.getDefault().register(this)
    }


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        if (isFixSwipeBackTransparentBug()) {
            swipeBackLayout.getChildAt(0).setBackgroundResource(android.R.color.transparent)
        }
    }

    //使onActivityResult能够传到fragment
    @SuppressLint("RestrictedApi")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        onActivityResultForFragment(supportFragmentManager.fragments, requestCode, resultCode, data)
    }

    @SuppressLint("RestrictedApi")
    private fun onActivityResultForFragment(rootFragmentList: List<Fragment>?, requestCode: Int, resultCode: Int, data: Intent?) {
        if (rootFragmentList != null) {
            for (fragment in rootFragmentList) {
                if (fragment == null) continue
                fragment.onActivityResult(requestCode, resultCode, data)
                onActivityResultForFragment(fragment.childFragmentManager.fragments, requestCode, resultCode, data)
            }
        }
    }

    override fun onBackPressedSupport() {
        if (isDoubleBack && supportFragmentManager.backStackEntryCount <= 1) {
            if (System.currentTimeMillis() - curMillsTime < 1500)
                super.onBackPressedSupport()
            else {
                curMillsTime = System.currentTimeMillis()
                T.showShort("再点击一次退出")
            }
        } else super.onBackPressedSupport()
    }

    override fun finish() {
        KeyboardUtils.hideSoftInput(this)
        super.finish()
        overridePendingTransition(com.resource.R.anim.anim_no, com.resource.R.anim.anim_to_right_close)
    }

    override fun onDestroy() {
        unbinder?.unbind()
        unbinder = null
        ActivityManager.getManager().finishActivity(this)
        super.onDestroy()
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (isAutoHideKeyBoard && ev.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (KeyboardUtil.isShouldHideInput(v, ev)) {
                KeyboardUtils.hideSoftInput(v)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun showLoading(message: String, outsideEnable: Boolean, backEnable: Boolean, listener: ((dialog: BaseDialogFragment) -> Unit)?) {
        if (isDestroyed || isFinishing) return
        if (loadingDialog == null)
            loadingDialog = LoadingDialog.Builder()
                    .setOutsideEnable(outsideEnable)
                    .setText(message)
                    .setOutsideEnable(outsideEnable)
                    .setBackEnable(backEnable)
                    .setOnDismissListener(listener)
                    .build()
        loadingDialog?.show(supportFragmentManager)
    }

    override fun hideLoading() {
        if (isDestroyed || isFinishing) return
        loadingDialog?.dismissAllowingStateLoss()
    }

    /** 修复继承swipeback界面后无法实现activity背景透明的问题*/
    open fun isFixSwipeBackTransparentBug(): Boolean {
        return false
    }

    /** 是否注册EventBus */
    open fun isRegisterEventBus(): Boolean {
        return false
    }

    /** 设置状态栏效果  */
    open fun getImmersionBar(): ImmersionBar? {
        return ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .keyboardEnable(true, WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                .navigationBarDarkIcon(true)
                .navigationBarColor(android.R.color.white)
    }

}
