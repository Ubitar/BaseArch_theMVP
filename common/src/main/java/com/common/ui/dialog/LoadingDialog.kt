package com.common.ui.dialog

import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import butterknife.BindView
import com.common.R
import com.common.R2

class LoadingDialog : BaseDialogFragment() {
    companion object {
        val TAG = "LOADING_DIALOG"
    }

    @BindView(R2.id.txt_tips_loading_msg)
    lateinit var txt_tips_loading_msg: TextView

    private var dimAmount = 0.5f
    private var dimAmountEnable= true
    private var outsideEnable = false
    private var backEnable = true
    private var text: String? = "加载中"

    override fun getDimAmount(): Float =dimAmount
    override fun isDimAmountEnable(): Boolean =dimAmountEnable
    override fun getOutsideEnable(): Boolean =outsideEnable
    override fun getBackEnable(): Boolean =backEnable
    override fun getLayoutRes(): Int =R.layout.dialog_base_loading

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dimAmount = arguments?.getFloat("dim_amount", dimAmount)!!
        outsideEnable = arguments?.getBoolean("out_side_enable", outsideEnable)!!
        dimAmountEnable = arguments?.getBoolean("dim_amount_enable", dimAmountEnable)!!
        backEnable = arguments?.getBoolean("back_enable", backEnable)!!
        text = arguments?.getString("text", text)
    }

    override fun onResume() {
        super.onResume()
        val bundle = arguments
        if (bundle != null) {
            if (bundle.containsKey("text")) setText(bundle.getString("text", text))
        }
    }

    fun setText(text: String?) {
        this.text = text
        txt_tips_loading_msg.text = this.text
    }

    fun show(manager: FragmentManager) {
        show(manager, TAG)
    }

    class Builder {
        private val bundle: Bundle = Bundle()
        private var onDismissListener: ((dialog: BaseDialogFragment) -> Unit)? = null

        fun setDimAount(dimAmount: Float): Builder {
            bundle.putFloat("dim_amount", dimAmount)
            return this
        }

        fun setDimAountEnable(enable: Float): Builder {
            bundle.putFloat("dim_amount_enable", enable)
            return this
        }

        fun setOutsideEnable(enable: Boolean): Builder {
            bundle.putBoolean("out_side_enable", enable)
            return this
        }

        fun setBackEnable(enable: Boolean): Builder {
            bundle.putBoolean("back_enable", enable)
            return this
        }

        fun setOnDismissListener(listener: ((dialog: BaseDialogFragment) -> Unit)?): Builder {
            this.onDismissListener = listener
            return this
        }

        fun setText(text: String?): Builder {
            bundle.putString("text", text)
            return this
        }

        fun build(): LoadingDialog {
            val dialog = LoadingDialog()
            dialog.setOnDismissListener(onDismissListener)
            dialog.arguments = bundle
            return dialog
        }
    }


}
