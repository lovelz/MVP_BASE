package com.lovelz.mvp_base.ui.base;

public interface BasePresenter<T extends BaseView> {

    /**
     * 依附某个View
     * @param view
     */
    void attachView(T view);

    /**
     * 移除View
     */
    void detachView();

}
