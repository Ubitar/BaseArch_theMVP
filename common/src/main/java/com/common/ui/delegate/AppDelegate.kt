package com.common.ui.delegate

import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

import com.common.ui.activity.BaseActivity

/**
 * Created by laohuang on 2018/9/9.
 */

abstract class AppDelegate {
    protected val mViews: SparseArray<View> = SparseArray()
    var rootView: View? = null

    protected abstract fun getLayoutId(): Int

    fun <D : ViewDataBinding> createMainView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): D? {
        val rootLayoutId = getLayoutId()
        val binding = DataBindingUtil.inflate<D>(inflater, rootLayoutId, container, false)
        if (binding == null) {
            this.rootView = inflater.inflate(rootLayoutId, container, false)
        } else {
            this.rootView = binding.root
        }

        return binding
    }

    open fun initWidget() {

    }

    open fun onDestroyWidget() {

    }

    fun <S : View> bindView(id: Int): S? {
        var view: S? = this.mViews.get(id) as S
        if (view == null) {
            view = this.rootView?.findViewById<View>(id) as S
            this.mViews.put(id, view)
        }

        return view
    }

    operator fun <S : View> get(id: Int): S? {
        return this.bindView(id)
    }

    fun <S : BaseActivity<*>> getActivity(): S {
        return this.rootView?.context as S
    }
}
