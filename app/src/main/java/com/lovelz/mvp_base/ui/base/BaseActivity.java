package com.lovelz.mvp_base.ui.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Activity的基类，经常用到的操作都在这哟！！！
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;

    protected RequestManager mImageLoader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());

        if (getSupportActionBar() != null) {
            //隐藏action bar
            getSupportActionBar().hide();
        }

        //绑定
        unbinder = ButterKnife.bind(this);

        initView();

        initEvent();
    }

    /**
     * 用于获取布局id，必须实现的
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 一般用于对view相关的进行操作
     */
    protected void initView() {

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
    protected void onDestroy() {
        super.onDestroy();
        //解除绑定
        unbinder.unbind();
    }

}
