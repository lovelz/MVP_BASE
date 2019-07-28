package com.lovelz.mvp_base.ui.base.tab;

import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.lovelz.mvp_base.R;
import com.lovelz.mvp_base.ui.base.BaseFragment;

import java.util.List;

import butterknife.BindView;

public abstract class BaseTabFragment extends BaseFragment {

    @BindView(R.id.common_tab)
    protected TabLayout commonTab;
    @BindView(R.id.line_indicator)
    View lineIndicator;
    @BindView(R.id.common_pager)
    protected ViewPager commonPager;

    private int tabWidth = 0;
    //指示器距最左侧的距离
    private int lDistance = 0;
    private LinearLayout.LayoutParams lineLp;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_tab;
    }

    @Override
    protected void initEvent() {
        super.initEvent();

        CommonTabPagerAdapter commonTabPagerAdapter = new CommonTabPagerAdapter(getChildFragmentManager());
        commonPager.setAdapter(commonTabPagerAdapter);
        commonTab.setupWithViewPager(commonPager);

        initIndicator();
    }

    /**
     * 自定义tab文字大小
     * @param textSize
     */
    protected void setCustomTextSize(int textSize) {
        setCustomTextSize(textSize, R.color.black);
    }

    /**
     * 自定义tab文字大小以及颜色
     * @param textSize
     * @param textColor
     */
    protected void setCustomTextSize(int textSize, int textColor) {
        for (int i = 0; i < getTabTitle().length; i++) {
            TabLayout.Tab tab = commonTab.getTabAt(i);
            tab.setCustomView(R.layout.layout_tab_custom_text);
            TextView tabText = tab.getCustomView().findViewById(R.id.tab_text);
            tabText.setText(getTabTitle()[i]);
            tabText.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            tabText.setTextColor(ContextCompat.getColor(getActivity(), textColor));

            if (i == 0) {
                tabText.setSelected(true);
            }
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

    private class CommonTabPagerAdapter extends FragmentPagerAdapter {

        public CommonTabPagerAdapter(FragmentManager fm) {
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

    protected abstract String[] getTabTitle();

    protected abstract List<Fragment> getFragmentList();

}
