package com.lovelz.mvp_base.ui.base.tab;

import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.lovelz.mvp_base.R;
import com.lovelz.mvp_base.ui.base.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 万能tabActivity
 */
public abstract class BaseTabActivity extends BaseActivity implements OnTabOperateListener {

    @BindView(R.id.tab_title)
    TextView tabTitle;
    @BindView(R.id.tab_operate_text)
    TextView tabOperateText;
    @BindView(R.id.tab_operate_icon)
    protected ImageView tabOperateIcon;
    @BindView(R.id.common_tab)
    protected TabLayout commonTab;
    @BindView(R.id.line_indicator)
    View lineIndicator;
    @BindView(R.id.common_pager)
    protected ViewPager commonPager;
    @BindView(R.id.iv_back)
    ImageView ivBack;

    private int tabWidth = 0;
    //指示器距最左侧的距离
    private int lDistance = 0;
    private LinearLayout.LayoutParams lineLp;

    private OnTabOperateListener onTabOperateListener;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_base_tab;
    }

    @Override
    protected void initEvent() {
        super.initEvent();

        CommonTabPagerAdapter commonTabPagerAdapter = new CommonTabPagerAdapter(getSupportFragmentManager());
        commonPager.setAdapter(commonTabPagerAdapter);
        commonTab.setupWithViewPager(commonPager);

        initIndicator();

        setOnTabOperateListener(this);

        initTabInfo();
    }

    /**
     * 初始化tab的相关配置信息
     */
    private void initTabInfo() {
        tabTitle.setText(getCenterTitle());

        //是否显示右侧图标
        if (provideRightIcon() != -1) {
            tabOperateIcon.setVisibility(View.VISIBLE);
            tabOperateIcon.setImageResource(provideRightIcon());
        }

        //是否显示右侧文字
        if (!TextUtils.isEmpty(provideRightText())) {
            tabOperateText.setVisibility(View.VISIBLE);
            tabOperateText.setText(provideRightText());
        }
    }

    /**
     * 初始化指示器位置
     */
    private void initIndicator() {
        commonTab.post(() -> {
            tabWidth = commonTab.getWidth() / getTabTitle().length;
            lDistance = tabWidth / 2 - lineIndicator.getWidth() / 2;
            lineLp = (LinearLayout.LayoutParams) lineIndicator.getLayoutParams();
            lineLp.leftMargin = lDistance;
            lineIndicator.setLayoutParams(lineLp);
        });

        new Handler().postDelayed(() -> commonPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                lineLp.leftMargin = (int) (tabWidth * positionOffset + tabWidth * position + lDistance);
                lineIndicator.setLayoutParams(lineLp);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        }), 500);
    }

    @OnClick({R.id.iv_back, R.id.tab_operate_text, R.id.tab_operate_icon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tab_operate_text:
                if (onTabOperateListener != null) {
                    onTabOperateListener.onTextOperate();
                }
                break;
            case R.id.tab_operate_icon:
                if (onTabOperateListener != null) {
                    onTabOperateListener.onIconOperate();
                }
                break;
        }
    }

    private class CommonTabPagerAdapter extends FragmentPagerAdapter {

        private CommonTabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return getFragmentList().get(position);
        }

        @Override
        public int getCount() {
            return getTabTitle().length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return getTabTitle()[position];
        }
    }

    /**
     * 显示右侧图标
     */
    protected int provideRightIcon() {
        return -1;
    }

    /**
     * 显示右侧文字
     */
    protected String provideRightText() {
        return null;
    }

    protected abstract String getCenterTitle();

    protected abstract String[] getTabTitle();

    protected abstract List<Fragment> getFragmentList();

    public void setOnTabOperateListener(OnTabOperateListener onTabOperateListener) {
        this.onTabOperateListener = onTabOperateListener;
    }

    @Override
    public void onTextOperate() {

    }

    @Override
    public void onIconOperate() {

    }
}
