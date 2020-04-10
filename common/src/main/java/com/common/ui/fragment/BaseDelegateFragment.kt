package com.common.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.databinding.ViewDataBinding
import butterknife.ButterKnife
import com.common.ui.delegate.BaseDelegate

abstract class BaseDelegateFragment<S : BaseDelegate> : BaseSwipeBackFragment() {

    var viewDelegate: S = lazy { createViewDelegate() }.value

    protected var isFirstVisible = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (viewDelegate.rootView == null) {
            createMainViewBinding<ViewDataBinding>(inflater, container, savedInstanceState)
            return viewDelegate.rootView
        }
        return viewDelegate.rootView!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDelegate.initWidget()
    }

    override fun onDestroyView() {
        viewDelegate.onDestroyWidget()
        initFragmentStatus()
        super.onDestroyView()
    }

    protected fun <D : ViewDataBinding> createMainViewBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): D? {
        return this.viewDelegate.createMainView(inflater, container, savedInstanceState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (viewDelegate == null) createViewDelegate()
    }

    private fun createViewDelegate(): S {
        return getDelegateClass().newInstance()
    }

    private fun initFragmentStatus() {
        viewDelegate.rootView = null
        isFirstVisible = true
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        viewDelegate.onSupportVisible()
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        isFirstVisible = true
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        viewDelegate.onSupportInvisible()
    }

    protected abstract fun getDelegateClass(): Class<S>

}