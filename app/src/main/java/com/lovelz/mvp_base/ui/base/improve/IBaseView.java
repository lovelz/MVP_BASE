package com.lovelz.mvp_base.ui.base.improve;

import android.content.Context;

import com.uber.autodispose.AutoDisposeConverter;

public interface IBaseView<T> {

    /**
     * 设置presenter
     * @param presenter
     */
    void setPresenter(T presenter);

    /**
     * 返回的一般错误信息
     * @param msg
     */
    void onError(String msg);

    /**
     * 返回的错误信息
     * @param msg
     * @param isShowOtherView 是否显示加载失败或错误或为空页面
     */
    void onError(String msg, boolean isShowOtherView);

    /**
     * 返回的网络错误信息
     * @param msg
     * @param isShowOtherView 是否显示加载失败或错误或为空页面
     */
    void onNetError(String msg, boolean isShowOtherView);

    /**
     * 绑定生命周期
     * @param <X>
     * @return
     */
    <X> AutoDisposeConverter<X> bindAutoDispose();

}
