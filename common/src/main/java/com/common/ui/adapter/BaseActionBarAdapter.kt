package com.common.ui.adapter

import android.view.ViewGroup

interface BaseActionBarAdapter {

    fun injectView(viewGroup: ViewGroup)

    fun showActionBar()

    fun hideActionBar()

    fun onDestroy()

    fun setOnClickLeftListener(listener:  (()->Unit))

    fun setOnClickRightListener(listener:  (()->Unit))

    interface OnClickLeftListener {
        fun onClick()
    }

    interface OnClickRightListener {
        fun onClick()
    }

}
