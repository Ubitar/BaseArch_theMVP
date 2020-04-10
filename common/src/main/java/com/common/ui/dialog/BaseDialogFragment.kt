package com.common.ui.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import butterknife.ButterKnife
import butterknife.Unbinder
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.common.R


/**
 * * DialogFragment通用基类
 */
abstract class BaseDialogFragment : DialogFragment() {
    private lateinit var unBinder: Unbinder

    private var onDismissListener: ((dialog: BaseDialogFragment) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(ActivityUtils.getTopActivity(), R.style.BaseDialogTheme)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        if (!isDimAmountEnable())
            dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        dialog.setContentView(getLayoutRes())
        dialog.setOnKeyListener { dialogInterface, i, keyEvent ->
            return@setOnKeyListener !getBackEnable() && i == KeyEvent.KEYCODE_BACK
        }
        dialog.setCanceledOnTouchOutside(getOutsideEnable())
        val dialogWindow = dialog.window
        dialogWindow?.setBackgroundDrawableResource(android.R.color.transparent)
        dialogWindow?.setGravity(getGravity())
        val layoutParams = dialogWindow?.attributes
        layoutParams?.width = WindowManager.LayoutParams.MATCH_PARENT
        dialogWindow?.attributes = layoutParams

        return dialog
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val dialogView = dialog?.window?.decorView
        initView(dialogView!!)
        initEvent()
        initData()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismissListener?.invoke(this)
        if (KeyboardUtils.isSoftInputVisible(activity!!))
            KeyboardUtils.toggleSoftInput()
    }

    fun setOnDismissListener(listener: ((dialog: BaseDialogFragment) -> Unit)?): BaseDialogFragment {
        this.onDismissListener = listener
        return this
    }

    open fun initView(contentView: View) {
        unBinder = ButterKnife.bind( this,contentView)
    }

    open fun initEvent() {

    }

    open fun initData() {

    }

    override fun onDestroyView() {
        unBinder.unbind()
        super.onDestroyView()
    }

    open fun getGravity(): Int {
        return Gravity.CENTER
    }

    open fun getOutsideEnable(): Boolean {
        return true
    }

    open fun getBackEnable(): Boolean {
        return true
    }

    open fun isDimAmountEnable(): Boolean {
        return true
    }

    open fun getDimAmount(): Float{
        return 0.5f
    }

    abstract fun getLayoutRes(): Int

}