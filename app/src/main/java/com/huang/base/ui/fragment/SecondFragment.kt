package com.huang.base.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import butterknife.OnClick
import com.common.network.ResponseCompose
import com.common.network.RetryWhenFunction
import com.common.network.SchedulerCompose
import com.common.ui.adapter.ViewPagerAdapter
import com.common.ui.dialog.AlertConfirmDialog
import com.common.ui.fragment.BaseFragment
import com.common.util.AutoDisposeUtil
import com.huang.base.R
import com.huang.base.event.TurnViewPagerEvent
import com.huang.base.network.model.UserModel
import com.huang.base.ui.delegate.SecondFragmentDelegate
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

class SecondFragment : BaseFragment<SecondFragmentDelegate>() {

    private lateinit var adapter: ViewPagerAdapter

    private val userModel = UserModel()

    override fun isRegisterEventBus(): Boolean =true

    override fun getDelegateClass(): Class<SecondFragmentDelegate> {
        return SecondFragmentDelegate::class.java
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        viewDelegate?.setText("点击发起网络请求1")
        initViewPager()
    }

    @OnClick(R.id.txt)
    fun onClickTxt() {
        userModel.login("123", "123")
                .compose(SchedulerCompose.io2main())
                .compose(ResponseCompose.parseResult())//过滤数据
                .retryWhen(RetryWhenFunction(3000, 3))//网络问题重试请求
                .flatMap { response ->
                    //                    UserInfoSaver.saveUserInfo(response.data!!)
                    userModel.logout(response.data?.token.toString())
                }
                .compose(SchedulerCompose.io2main())
                .compose(ResponseCompose.parseResult())
                .doOnSubscribe { showLoading() }
                .doFinally { hideLoading() }
                .retryWhen(RetryWhenFunction(3000, 3))//网络问题重试请求
                .`as`(AutoDisposeUtil.fromOnDestroy(this))
                .subscribe({
                    println("网络请求成功")
                }, {
                    hideLoading()
                })
    }

    @OnClick(R.id.txtConfirmDialog)
    fun onClickTxtConfirmDialog(view: View) {
        AlertConfirmDialog.Builder()
                .setOutsideCancelable(false)
                .setBackEnable(false)
                .build().show(childFragmentManager, "test")
    }

    @OnClick(R.id.tab1)
    fun onClickTab1() {
        viewDelegate?.setCurrentAt(0)
    }

    @OnClick(R.id.tab2)
    fun onClickTab2() {
        viewDelegate?.setCurrentAt(1)
    }

    @OnClick(R.id.tab3)
    fun onClickTab3() {
        viewDelegate?.setCurrentAt(2)
    }

    @OnClick(R.id.tab4)
    fun onClickTab4() {
        viewDelegate?.setCurrentAt(3)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: TurnViewPagerEvent) {
        viewDelegate?.setCurrentAt(event.index)
    }

    private fun initViewPager() {
        adapter = ViewPagerAdapter(childFragmentManager)
        val fragments = ArrayList<Fragment>(4)
        fragments.add(MainFragment.newInstance(0))
        fragments.add(MainFragment.newInstance(1))
        fragments.add(MainFragment.newInstance(2))
        fragments.add(MainFragment.newInstance(3))
        adapter.fragments = fragments
        viewDelegate?.initViewPager(adapter)
    }
}