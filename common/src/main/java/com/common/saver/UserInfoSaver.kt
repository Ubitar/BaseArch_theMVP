package com.common.saver

import com.blankj.utilcode.util.StringUtils
import com.common.bean.UserBean
import com.orhanobut.hawk.Hawk

object UserInfoSaver {
    private val USER_INFO = "USER_INFO"
    private var userBean: UserBean? = null
    private var token: String? = null

    val userInfo: UserBean?
        get() {
            if (userBean == null) userBean = Hawk.get<UserBean>(USER_INFO)
            return userBean
        }

    val isLogined: Boolean
        get() = !StringUtils.isSpace(userBean?.token)

    fun saveUserInfo(userBean: UserBean) {
        UserInfoSaver.userBean = userBean
        token = userBean.token
        Hawk.put(USER_INFO, userBean)
    }

    fun getToken(): String? {
        return if (token == null) {
            userBean = userInfo
            if (userBean == null) {
                null
            } else {
                token = userBean?.token
                token
            }
        } else token
    }
}
