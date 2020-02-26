package com.common.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class UserBean(
        val id: Int = 0,
        var account: String? = null,
        var password: String? = null,
        var token: String? = null,
        var headview: String? = null,
        var name: String? = null,
        var mobile: String? = null,
        var idCard: String? = null,
        var gender: Int = 0,//0为未设置  1为男  2为女
        var birthday: Long = 0,
        var province: String? = null,
        var city: String? = null,
        var buildTime: Long = 0//创建时间
) : Parcelable
