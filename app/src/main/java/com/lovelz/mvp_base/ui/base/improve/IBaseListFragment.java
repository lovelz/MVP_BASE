package com.lovelz.mvp_base.ui.base.improve;


import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.classic.common.MultipleStatusView;
import com.lovelz.mvp_base.R;
import com.lovelz.mvp_base.view.LoadEmptyLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;

public abstract class IBaseListFragment<T extends IBasePresenter, B> extends ILazyLoadFragment<T> implements IBaseListView<T, B> {

    private static final RelativeLayout.LayoutParams DEFAULT_LAYOUT_PARAMS = new RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

    @BindView(R.id.refresh_layout)
    protected SmartRefreshLayout refreshLayout;
    @BindView(R.id.multiple_status_view)
    protected MultipleStatusView multipleStatusView;
    @BindView(R.id.recycler_view)
    protected RecyclerView recyclerView;

    protected int page = 1;
    protected boolean isRefresh;
    protected boolean isLoadMore;

    private LoadEmptyLayout loadEmptyLayout;

    @Override
    protected void initView(View view) {
        super.initView(view);
        showLoading();
    }

    protected void showLoading() {
        multipleStatusView.showLoading(R.layout.layout_load_loading, DEFAULT_LAYOUT_PARAMS);
    }

    @Override
    protected void initEvent() {
        super.initEvent();

        //刷新以及加载更多
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                isRefresh = false;
                isLoadMore = true;
                onLoadData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshData();
            }
        });

        //重新加载
        multipleStatusView.setOnRetryClickListener(v -> {
            showLoading();
            refreshData();
        });
    }

    /**
     * 刷新数据
     */
    public void refreshData() {
        if (isDataInitiated) {
            page = 1;
            isRefresh = true;
            isLoadMore = false;
            recyclerView.scrollToPosition(0);
            onLoadData();
        }
    }

    @Override
    public void onSetAdapter(List<B> list) {
        clearSmartState();
        if (!isLoadMore) {
            multipleStatusView.showContent();
        }
    }

    @Override
    public void onShowEmpty(int adapterCount, int resId, int iconId) {
        if (adapterCount == 0) {
            setLoadEmptyInfo(resId, iconId);
            multipleStatusView.showEmpty(loadEmptyLayout, DEFAULT_LAYOUT_PARAMS);
        }
    }

    /**
     * 设置空布局相关信息
     * @param resId
     * @param iconId
     */
    private void setLoadEmptyInfo(@StringRes int resId, @DrawableRes int iconId) {
        if (loadEmptyLayout == null) {
            loadEmptyLayout = new LoadEmptyLayout(getActivity());
        }
        loadEmptyLayout.setEmptyInfo(resId, iconId);
        multipleStatusView.showEmpty(loadEmptyLayout, DEFAULT_LAYOUT_PARAMS);
    }

    @Override
    public void onShowNoMore(int maxPage) {
        refreshLayout.setNoMoreData(page >= maxPage);
        page += 1;
    }

    @Override
    protected void showErrorLayout(String msg) {
        if (!isLoadMore) {
            clearSmartState();
            multipleStatusView.showError(R.layout.layout_load_error, DEFAULT_LAYOUT_PARAMS);
        } else {
            ToastUtils.showShort(msg);
            refreshLayout.finishLoadMore(false);
        }
    }

    /**
     * 清除刷新加载状态
     */
    private void clearSmartState() {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
    }
}
