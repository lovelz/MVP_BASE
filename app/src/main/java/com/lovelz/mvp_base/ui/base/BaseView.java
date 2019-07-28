package com.lovelz.mvp_base.ui.base;

public interface BaseView {

    /**
     * 返回一个普通的信息
     * @param msg
     */
    void onMsg(String msg);

    /**
     * 返回的错误信息
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

}
