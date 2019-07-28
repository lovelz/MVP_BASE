package com.lovelz.mvp_base.ui.base.improve;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;

import com.blankj.utilcode.util.ToastUtils;
import com.lovelz.mvp_base.ui.base.BaseFragment;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

public abstract class IBaseFragment<T extends IBasePresenter> extends BaseFragment implements IBaseView<T> {

    protected T mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter(mPresenter);
    }

    @Nullable
    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public <X> AutoDisposeConverter<X> bindAutoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY));
    }

    @Override
    public void onError(String msg) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void onError(String msg, boolean isShowOtherView) {
        ToastUtils.showShort(msg);

        if (isShowOtherView) {
            showErrorLayout(msg);
        }
    }

    @Override
    public void onNetError(String msg, boolean isShowOtherView) {
        if (isShowOtherView) {
            showErrorLayout(msg);
        } else {
            ToastUtils.showShort(msg);
        }
    }

    /**
     * 显示错误界面
     * 需要时需调用此方法
     */
    protected void showErrorLayout(String msg) {

    }

}
