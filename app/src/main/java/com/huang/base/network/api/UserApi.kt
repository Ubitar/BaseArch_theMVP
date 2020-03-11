package com.huang.base.network.api

import com.common.bean.BaseResponse
import com.common.bean.UserBean
import io.reactivex.Flowable

import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * 用户相关接口路由
 */
interface UserApi {

    @FormUrlEncoded
    @POST("student/login")
    fun login(
            @Field("account") account: String,
            @Field("password") password: String): Flowable<BaseResponse<UserBean>>

    @FormUrlEncoded
    @POST("student/logout")
    fun logout(@Field("token") token: String): Flowable<BaseResponse<Any>>
}