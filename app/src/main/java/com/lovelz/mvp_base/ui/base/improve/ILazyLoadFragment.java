package com.lovelz.mvp_base.ui.base.improve;

import android.os.Bundle;

import androidx.annotation.Nullable;

public abstract class ILazyLoadFragment<T extends IBasePresenter> extends IBaseFragment<T> {

    /**
     * 是否初始化过布局
     */
    protected boolean isViewInitiated;

    /**
     * 当前界面是否可见
     */
    protected boolean isVisibleToUser;

    /**
     * 是否加载过数据
     */
    protected boolean isDataInitiated;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareFetchData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser) {
            prepareFetchData();
        }
    }

    public void prepareFetchData() {
        prepareFetchData(false);
    }

    /**
     * 是否懒加载
     *
     * @param forceUpdate 强制更新，好像没什么用？
     */
    public void prepareFetchData(boolean forceUpdate) {
        if (isViewInitiated && isVisibleToUser && (!isDataInitiated || forceUpdate)) {
            fetchData();
            isDataInitiated = true;
        }
    }

    /**
     * 懒加载
     */
    protected abstract void fetchData();
}
