package com.huang.base.ui.fragment

import butterknife.OnClick
import com.common.R2
import com.common.ui.fragment.BaseFragment
import com.huang.base.R
import com.huang.base.ui.delegate.SecondFragmentDelegate

class SecondFragment : BaseFragment<SecondFragmentDelegate>() {
    override fun getDelegateClass(): Class<SecondFragmentDelegate> {
        return SecondFragmentDelegate::class.java
    }

    @OnClick(R.id.txt)
    fun onClickTxt(){
        start(SecondFragment())
    }
}