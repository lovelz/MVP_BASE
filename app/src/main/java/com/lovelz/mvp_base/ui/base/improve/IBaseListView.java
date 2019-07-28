package com.lovelz.mvp_base.ui.base.improve;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import java.util.List;

public interface IBaseListView<T, B> extends IBaseView<T> {

    /**
     * 请求数据
     */
    void onLoadData();

    /**
     * 设置列表适配器
     * @param list
     */
    void onSetAdapter(List<B> list);

    /**
     * 显示空布局
     */
    void onShowEmpty(int adapterCount, @StringRes int resId, @DrawableRes int iconId);

    /**
     * 加载完毕，没有更多了
     */
    void onShowNoMore(int maxPage);

}
