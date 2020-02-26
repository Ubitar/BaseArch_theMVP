package com.huang.base.ui.activity

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.core.app.ActivityCompat

import com.common.common.IntentRouter
import com.common.ui.activity.BaseActivity
import com.common.ui.dialog.AlertConfirmDialog
import com.common.util.AutoDisposeUtil
import com.gyf.immersionbar.ImmersionBar
import com.huang.base.ui.delegate.WelcomeDelegate
import com.huang.lib.helper.IntentHelper
import com.qw.soul.permission.SoulPermission
import com.qw.soul.permission.bean.Permission
import com.qw.soul.permission.bean.Permissions
import com.qw.soul.permission.callbcak.CheckRequestPermissionsListener
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

import java.util.concurrent.TimeUnit

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

class WelcomeActivity : BaseActivity<WelcomeDelegate>() {
    override fun getDelegateClass(): Class<WelcomeDelegate> {
        return WelcomeDelegate::class.java
    }

    override fun getImmersionBar(): ImmersionBar? {
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermission()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        checkPermission()
    }

    private fun checkPermission() {
        SoulPermission.getInstance().checkAndRequestPermissions(
                Permissions.build(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                object : CheckRequestPermissionsListener {
                    override fun onAllPermissionOk(allPermissions: Array<Permission>) {
                     next()
                    }

                    override fun onPermissionDenied(refusedPermissions: Array<Permission>) {
                        AlertConfirmDialog.Builder()
                                .setTitle("缺少必要权限")
                                .setContent("当前应用缺少必要权限,请点击\"设置\"-\"权限\"-打开所需权限。")
                                .setSubmitText("设置").setCancelText("退出")
                                .setOutsideCancelable(false)
                                .setBackEnable(false)
                                .setOnClickSubmitListener { IntentHelper.startSystemPermissionActivity(activity) }
                                .setOnClickCancelListener { ActivityCompat.finishAfterTransition(this@WelcomeActivity) }
                                .build()
                                .showNow(supportFragmentManager, "TAG")
                    }
                })
    }

    private fun next(){
        Observable.timer(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .`as`(AutoDisposeUtil.fromOnDestroy(activity))
                .subscribe({
                    //            IntentRouter.build(IntentRouter.SECOND_ACITIVTY).navigation()
                    IntentRouter.build(IntentRouter.MAIN_ACITIVTY).navigation()
                    ActivityCompat.finishAfterTransition(this@WelcomeActivity)
                }, {})
    }
}
