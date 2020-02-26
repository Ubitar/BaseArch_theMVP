package com.huang.base

import android.app.Application

import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.Utils
import com.bumptech.glide.Glide
import com.common.network.NetworkManager
import com.huang.lib.common.Constant
import com.huang.lib.util.L
import com.huang.lib.util.T
import com.orhanobut.hawk.Hawk
import com.qw.soul.permission.SoulPermission
import me.yokeyword.fragmentation.Fragmentation

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        T.init(this)
        L.init(this)
        Utils.init(this)
        SoulPermission.init(this)
        NetworkManager.init()
        Hawk.init(this).build()

        if (Constant.isDebug) {           // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog()     // Print log
            ARouter.openDebug()   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(this) // As earl

    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Glide.get(this).onTrimMemory(level)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Glide.get(this).onLowMemory()
    }

    companion object {
        var instance: App? = null
            private set
    }
}
