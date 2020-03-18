package com.huang.base.ui.delegate

import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import butterknife.BindView
import com.common.ui.adapter.ViewPagerAdapter
import com.common.ui.delegate.BaseDelegate
import com.huang.base.R

class SecondFragmentDelegate : BaseDelegate() {
    @BindView(R.id.txt)
    lateinit var txt: TextView
    @BindView(R.id.viewPager)
    lateinit var viewPager: ViewPager

    override fun getLayoutId(): Int {
        return R.layout.fragment_second
    }


    fun setText(text: String) {
        txt.text = text
    }

    fun initViewPager(adapter: ViewPagerAdapter) {
        viewPager.offscreenPageLimit = 4
        viewPager.adapter = adapter
    }

    fun setCurrentAt(index: Int) {
        viewPager.setCurrentItem(index, false)
    }


}