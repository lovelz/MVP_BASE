package com.lovelz.mvp_base.ui.base.improve;

import android.widget.RelativeLayout;

import com.classic.common.MultipleStatusView;
import com.lovelz.mvp_base.R;

import butterknife.BindView;


public abstract class IBaseDetailActivity<T extends IBasePresenter> extends IBaseActivity<T> implements IDetailView<T> {

    private static final RelativeLayout.LayoutParams DEFAULT_LAYOUT_PARAMS = new RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

    @BindView(R.id.multiple_status_view)
    protected MultipleStatusView multipleStatusView;

    @Override
    protected void initView() {
        super.initView();
        showLoading();
    }

    protected void showLoading() {
        multipleStatusView.showLoading(R.layout.layout_load_loading, DEFAULT_LAYOUT_PARAMS);
    }

    @Override
    protected void initEvent() {
        super.initEvent();

        //重新加载
        multipleStatusView.setOnRetryClickListener(v -> {
            showLoading();
            refreshData();
        });

        //加载数据
        onLoadData();
    }

    /**
     * 刷新数据
     */
    public void refreshData() {
        onLoadData();
    }

    @Override
    protected void showErrorLayout(String msg) {
        multipleStatusView.showError(R.layout.layout_load_error, DEFAULT_LAYOUT_PARAMS);
    }
}
