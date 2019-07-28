package com.lovelz.mvp_base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.lovelz.mvp_base.R;


public class LoadEmptyLayout extends LinearLayout {

    private TextView emptyInfoView;
    private ImageView emptyIconView;

    public LoadEmptyLayout(Context context) {
        this(context, null);
    }

    public LoadEmptyLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadEmptyLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initLayout();
    }

    private void initLayout() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_load_empty, this, true);
        emptyInfoView = findViewById(R.id.empty_info);
        emptyIconView = findViewById(R.id.empty_icon);
    }

    /**
     * 设置空布局
     */
    public void setEmptyInfo() {
        setEmptyInfo(R.string.load_empty_info);
    }

    /**
     * 设置空布局文字信息，图标默认
     * @param stringId
     */
    public void setEmptyInfo(@StringRes int stringId) {
//        setEmptyInfo(stringId, R.mipmap.empty_order_icon);
    }

    /**
     * 设置空布局文字以及图标
     * @param stringId
     * @param iconId
     */
    public void setEmptyInfo(@StringRes int stringId, @DrawableRes int iconId) {
        emptyInfoView.setText(stringId);
        emptyIconView.setImageResource(iconId);
    }
}
