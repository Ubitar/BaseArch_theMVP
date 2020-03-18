package com.huang.base.ui.fragment

import android.os.Bundle
import butterknife.OnClick
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.common.common.IntentRouter
import com.common.ui.fragment.BaseFragment
import com.huang.base.R
import com.huang.base.event.TurnViewPagerEvent
import com.huang.base.ui.delegate.MainFragmentDelegate
import org.greenrobot.eventbus.EventBus

class MainFragment : BaseFragment<MainFragmentDelegate>() {
    @JvmField
    @Autowired(name = "index")
    var index: Int = 0

    override fun getDelegateClass(): Class<MainFragmentDelegate> {
        return MainFragmentDelegate::class.java
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        viewDelegate?.setText("fragment" + (index + 1) + "\n点击后打开fragment")
        viewDelegate?.setBackgroundColor(index)
    }

    @OnClick(R.id.txt)
    fun onClickTxt() {
        (parentFragment as BaseFragment<*>).start(SecondFragment())
    }

    @OnClick(R.id.txt2)
    fun onClickTxt2() {
        EventBus.getDefault().post(TurnViewPagerEvent(1))
    }

    companion object {
        fun newInstance(index: Int): MainFragment {
            val fragment = MainFragment()
            val bundle = Bundle()
            bundle.putInt("index", index)
            fragment.arguments = bundle
            return fragment
        }
    }
}
