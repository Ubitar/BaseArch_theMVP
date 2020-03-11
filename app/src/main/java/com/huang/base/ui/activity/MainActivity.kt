package com.huang.base.ui.activity

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import butterknife.OnClick
import com.alibaba.android.arouter.facade.annotation.Route
import com.common.common.IntentRouter
import com.common.network.ResponseCompose
import com.common.network.RetryWhenFunction
import com.common.network.SchedulerCompose
import com.common.ui.activity.BaseActivity
import com.common.ui.adapter.ViewPagerAdapter
import com.common.ui.dialog.AlertConfirmDialog
import com.common.util.AutoDisposeUtil
import com.huang.base.R
import com.huang.base.event.TurnViewPagerEvent
import com.huang.base.network.model.UserModel
import com.huang.base.ui.delegate.MainDelegate
import com.huang.base.ui.fragment.MainFragment
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

@Route(path = IntentRouter.MAIN_ACITIVTY)
class MainActivity : BaseActivity<MainDelegate>() {

    private lateinit var adapter: ViewPagerAdapter

    private val userModel = UserModel()

    override fun isRegisterEventBus(): Boolean {
        return true
    }

    override fun getDelegateClass(): Class<MainDelegate> {
        return MainDelegate::class.java
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDelegate?.setText("点击发起网络请求1")
        initViewPager()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: TurnViewPagerEvent) {
        viewDelegate?.setCurrentAt(event.index)
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
                .build().show(supportFragmentManager, "test")
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

    private fun initViewPager() {
        adapter = ViewPagerAdapter(supportFragmentManager)
        val fragments = ArrayList<Fragment>(4)
        fragments.add(MainFragment.newInstance(0))
        fragments.add(MainFragment.newInstance(1))
        fragments.add(MainFragment.newInstance(2))
        fragments.add(MainFragment.newInstance(3))
        adapter.fragments = fragments
        viewDelegate?.initViewPager(adapter)
    }
}
