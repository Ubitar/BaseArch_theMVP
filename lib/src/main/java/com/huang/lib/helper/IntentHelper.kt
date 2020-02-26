package com.huang.lib.helper

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings

/**
 * Created by laohuang on 2018/11/10.
 */
object IntentHelper {

    //跳转至系统自带的权限控制界面
    fun startSystemPermissionActivity(activity: Activity) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:" + activity.packageName)
        activity.startActivityForResult(intent, 0)
    }

}
