package com.lovelz.mvp_base.ui.base;

import android.app.ProgressDialog;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Fragment的基类
 */

public abstract class BaseFragment extends Fragment {

    private View mView;
    private Unbinder unbinder;

    private ProgressDialog mLoadingDialog;

    protected RequestManager mImageLoader;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mView != null) {
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (parent != null) {
                parent.removeView(mView);
            }
        } else {
            mView = inflater.inflate(getLayoutId(), container, false);
            //绑定view
            unbinder = ButterKnife.bind(this, mView);

            initView(mView);

            initEvent();
        }

        return mView;
    }

    /**
     * 用于获取布局id，必须实现的
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 一般用于对view相关的进行操作
     */
    protected void initView(View view) {

    }

    /**
     * 一般用于对事件相关的进行操作
     */
    protected void initEvent() {

    }

    /**
     * 获取图片加载器
     * @return
     */
    protected RequestManager getImageLoader() {
        if (mImageLoader == null) {
            mImageLoader = Glide.with(this);
        }
        return mImageLoader;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解除绑定
        unbinder.unbind();
    }

}
