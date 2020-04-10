package com.common.ui.dialog

import android.os.Bundle
import android.view.View
import android.widget.TextView
import butterknife.BindView
import com.common.R2
import com.common.R
import kotlinx.android.synthetic.main.dialog_base_alert_confirm.*

class AlertConfirmDialog : BaseDialogFragment() {

    var hideCancel: Boolean = false
    var hideSubmit: Boolean = false
    var interceptClose: Boolean = false
    var title: String? = null
    var content: String? = null
    var cancelText: String? = null
    var submitText: String? = null

    private var dimAmount = 0.5f
    private var dimAmountEnable= true
    private var outsideEnable = false
    private var backEnable = true

    private var onClickSubmitListener: (() -> Unit?)? = null
    private var onClickCancelListener: (() -> Unit?)? = null

    @BindView(R2.id.txtCancel)
    lateinit var txtCancel:TextView
    @BindView(R2.id.txtSubmit)
    lateinit var txtSubmit:TextView
    @BindView(R2.id.txtTitle)
    lateinit var txtTitle:TextView
    @BindView(R2.id.txtContent)
    lateinit var txtContent:TextView

   override fun getLayoutRes(): Int =R.layout.dialog_base_alert_confirm
    override fun getDimAmount(): Float =dimAmount
    override fun isDimAmountEnable(): Boolean =dimAmountEnable
    override fun getOutsideEnable(): Boolean =outsideEnable
    override fun getBackEnable(): Boolean =backEnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            dimAmount = arguments?.getFloat("dim_amount", dimAmount)!!
            outsideEnable = arguments?.getBoolean("out_side_enable", outsideEnable)!!
            dimAmountEnable = arguments?.getBoolean("dim_amount_enable", dimAmountEnable)!!
            backEnable = arguments?.getBoolean("back_enable", backEnable)!!
            hideCancel = arguments?.getBoolean("hide_cancel", hideCancel)!!
            hideSubmit = arguments?.getBoolean("hide_submit", hideSubmit)!!
            interceptClose = arguments?.getBoolean("intercept_close", interceptClose)!!
            title = arguments?.getString("title", title).toString()
            content = arguments?.getString("content", content).toString()
            cancelText = arguments?.getString("cancel_text", cancelText).toString()
            submitText = arguments?.getString("submit_text", submitText).toString()
        }
    }

    override fun initView(contentView :View) {
        super.initView(contentView )
        if (hideCancel) txtCancel.visibility = View.GONE
        if (hideSubmit) txtSubmit.visibility = View.GONE
        if (hideCancel || hideSubmit) viewSep2.visibility = View.GONE
        if (cancelText != null) txtCancel.text = cancelText
        if (submitText != null) txtSubmit.text = submitText
        if (title != null) {
            txtTitle.text = title
            txtTitle.visibility = View.VISIBLE
        }
        if (content != null) {
            txtContent.text = content
            txtContent.visibility = View.VISIBLE
        }
    }

    override fun initEvent() {
        super.initEvent()
        txtSubmit.setOnClickListener {
            onClickSubmitListener?.invoke()
            if (!interceptClose) dismiss()
        }
        txtCancel.setOnClickListener {
            onClickCancelListener?.invoke()
            if (!interceptClose) dismiss()
        }
    }


    class Builder {
        private val bundle: Bundle = Bundle()
        private var onClickSubmitListener: (() -> Unit)? = null
        private var onClickCancelListener: (() -> Unit)? = null

        fun setOutsideCancelable(cancelable: Boolean): Builder {
            bundle.putBoolean("outside_cancelable", cancelable)
            return this
        }

        fun setBackEnable(enable: Boolean): Builder {
            bundle.putBoolean("back_enable", enable)
            return this
        }

        fun setDimAount(dimAmount: Float): Builder {
            bundle.putFloat("dim_amount", dimAmount)
            return this
        }

        fun setOnClickSubmitListener(listener: () -> Unit): Builder {
            this.onClickSubmitListener = listener
            return this
        }

        fun setOnClickCancelListener(listener: (() -> Unit)): Builder {
            this.onClickCancelListener = listener
            return this
        }

        fun setTitle(title: String): Builder {
            bundle.putString("title", title)
            return this
        }

        fun setContent(content: String): Builder {
            bundle.putString("content", content)
            return this
        }

        fun setSubmitText(text: String): Builder {
            bundle.putString("submit_text", text)
            return this
        }

        fun setCancelText(text: String): Builder {
            bundle.putString("cancel_text", text)
            return this
        }

        fun setHideCancel(hide: Boolean): Builder {
            bundle.putBoolean("hide_cancel", hide)
            return this
        }

        fun setHideSubmit(hide: Boolean): Builder {
            bundle.putBoolean("hide_submit", hide)
            return this
        }

        fun setInterceptClose(interceptClose: Boolean): Builder {
            bundle.putBoolean("intercept_close", interceptClose)
            return this
        }


        fun build(): AlertConfirmDialog {
            val dialog = AlertConfirmDialog()
            dialog.onClickSubmitListener=onClickSubmitListener
            dialog.onClickCancelListener=onClickCancelListener
            dialog.arguments = bundle
            return dialog
        }
    }

}
