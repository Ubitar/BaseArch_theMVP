package com.common.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.StringUtils;
import com.common.R;
import com.common.R2;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/**
 * 不要转成kotlin，会绑定不了butterknife
 */
public class DefActionBarAdapter implements BaseActionBarAdapter {

    @BindView(R2.id.layoutBaseStatusBar)
    RelativeLayout layoutBaseStatusBar;
    @BindView(R2.id.layoutBaseActionBar)
    ConstraintLayout layoutBaseActionBar;
    @BindView(R2.id.txtBaseTitle)
    TextView txtBaseTitle;

    private Unbinder unbinder;

    private  Function0<Unit> onClickLeftListener;
    private  Function0<Unit> onClickRightListener;

    @Override
    public void injectView(ViewGroup root) {
        View head = LayoutInflater.from(root.getContext()).inflate(R.layout.activity_base_head, null);
        root.addView(head, 0, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        unbinder = ButterKnife.bind(this, head);

        //初始化状态栏高度
        layoutBaseStatusBar.getLayoutParams().height = BarUtils.getStatusBarHeight();

        //设置标题
        Activity activity = (Activity) root.getContext();
        if (!StringUtils.isSpace(activity.getTitle().toString()))
            txtBaseTitle.setText(activity.getTitle());
    }

    @OnClick({R2.id.imgBaseBack, R2.id.txtBaseBack})
    void onClickLeft(View view) {
        if (onClickLeftListener == null) {
            Activity activity = (Activity) view.getContext();
            activity.onBackPressed();
        } else {
            onClickLeftListener.invoke();
        }
    }

    @OnClick({R2.id.imgBaseFunction, R2.id.txtBaseFunction})
    void onClickRight() {
        if (onClickRightListener != null)
            onClickRightListener.invoke();
    }

    @Override
    public void showActionBar() {
        layoutBaseActionBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideActionBar() {
        layoutBaseActionBar.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        unbinder = null;
    }

    @Override
    public void setOnClickLeftListener(@NotNull Function0<Unit> listener) {
        this.onClickLeftListener = listener;
    }

    @Override
    public void setOnClickRightListener(@NotNull Function0<Unit> listener) {
        this.onClickRightListener = listener;
    }
}
