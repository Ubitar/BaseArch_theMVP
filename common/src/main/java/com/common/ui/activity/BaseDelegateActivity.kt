package com.common.ui.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.common.ui.delegate.BaseDelegate

abstract class BaseDelegateActivity<S : BaseDelegate> : BaseSwipeBackActivity() {

    protected var viewDelegate: S? = null

    init {
        try {
            viewDelegate = this.getDelegateClass().newInstance()
        } catch (var2: InstantiationException) {
            throw RuntimeException("createMainView IDelegate error")
        } catch (var3: IllegalAccessException) {
            throw RuntimeException("createMainView IDelegate error")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createMainBinding<ViewDataBinding>(layoutInflater, null, savedInstanceState)

        setContentView(viewDelegate?.rootView)

        viewDelegate?.initWidget()
    }

    override fun onResume() {
        super.onResume()
        viewDelegate?.onSupportVisible()
    }

    override fun onPause() {
        viewDelegate?.onSupportInvisible()
        super.onPause()
    }


    override fun onDestroy() {
        viewDelegate?.onDestroyWidget()
        viewDelegate = null
        super.onDestroy()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (this.viewDelegate == null) {
            try {
                viewDelegate = this.getDelegateClass().newInstance()
                createMainBinding<ViewDataBinding>(this.layoutInflater, null, savedInstanceState)
            } catch (var3: InstantiationException) {
                throw RuntimeException("createMainView IDelegate error")
            } catch (var4: IllegalAccessException) {
                throw RuntimeException("createMainView IDelegate error")
            }
        }
    }

    protected fun <D : ViewDataBinding> createMainBinding(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): D? {
        return this.viewDelegate?.createMainView(inflater, container, savedInstanceState)
    }

    protected abstract fun getDelegateClass(): Class<S>

}