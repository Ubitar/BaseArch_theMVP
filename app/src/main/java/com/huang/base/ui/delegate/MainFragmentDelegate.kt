package com.huang.base.ui.delegate

import android.graphics.Color
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView

import com.common.ui.delegate.BaseDelegate
import com.huang.base.R

import butterknife.BindView

class MainFragmentDelegate : BaseDelegate() {
    @BindView(R.id.bg)
    lateinit var bg: LinearLayout
    @BindView(R.id.txt)
    lateinit var txt: TextView

    override fun getLayoutId(): Int {
        return R.layout.activity_main_fragment
    }

    override fun initWidget() {
        super.initWidget()
        txt.text = "测试"
    }

    fun setBackgroundColor(index: Int) {
        if (index <= 0)
            bg.setBackgroundColor(Color.RED)
        else if (index == 1)
            bg.setBackgroundColor(Color.GREEN)
        else if (index == 2)
            bg.setBackgroundColor(Color.BLUE)
        else if (index == 3) bg.setBackgroundColor(Color.GRAY)
    }

    fun setText(text: String) {
        txt.text = text
    }
}
