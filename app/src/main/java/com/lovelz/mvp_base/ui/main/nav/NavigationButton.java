package com.lovelz.mvp_base.ui.main.nav;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import com.lovelz.mvp_base.R;


/**
 * 自定义导航栏下的单个导航view
 */

public class NavigationButton extends FrameLayout {

    private ImageView navIcon;
    private TextView navTitle;

    private Fragment mFragment = null;
    private Class<?> mClx;
    private String mTag;

    public NavigationButton(@NonNull Context context) {
        this(context, null);
    }

    public NavigationButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigationButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_nav_button, this, true);

        navIcon = findViewById(R.id.nav_icon);
        navTitle = findViewById(R.id.nav_title);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        navIcon.setSelected(selected);
        navTitle.setSelected(selected);
    }

    /**
     * 初始化相关数据
     * @param resId
     * @param strId
     * @param clx
     */
    public void init(boolean isShowIcon, @DrawableRes int resId, @StringRes int strId, Class<?> clx) {
        navIcon.setVisibility(INVISIBLE);
        init(resId, strId, clx);
    }

    /**
     * 初始化相关数据
     * @param resId
     * @param strId
     * @param clx
     */
    public void init(@DrawableRes int resId, @StringRes int strId, Class<?> clx) {
        navIcon.setImageResource(resId);
        navTitle.setText(strId);
        this.mClx = clx;
        this.mTag = mClx.getName();
    }

    public Fragment getFragment() {
        return mFragment;
    }

    public void setFragment(Fragment mFragment) {
        this.mFragment = mFragment;
    }

    public Class<?> getClx() {
        return mClx;
    }

    public String getmTag() {
        return mTag;
    }
}
