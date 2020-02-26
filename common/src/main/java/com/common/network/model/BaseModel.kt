package com.common.network.model

import com.common.network.NetworkManager

abstract class BaseModel<T> {

    protected var model: T

    init {
        model = NetworkManager.getRequest(getApi())
    }

    protected abstract fun  getApi(): Class<T>

}