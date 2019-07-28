package com.lovelz.mvp_base.ui.main.nav;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.lovelz.mvp_base.R;
import com.lovelz.mvp_base.ui.base.BaseFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 首页底部导航栏
 * 关联了上面的界面
 */

public class NavFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.nav_home)
    NavigationButton navHome;
    @BindView(R.id.nav_product)
    NavigationButton navProduct;
    @BindView(R.id.nav_learn_center)
    NavigationButton navLearnCenter;
    @BindView(R.id.nav_info)
    NavigationButton navInfo;
    @BindView(R.id.nav_user)
    NavigationButton navUser;

    private Context mContext;
    private FragmentManager mFragmentManager;
    private int containerId;
    private NavigationButton currentNavButton;

    private int selectPosition = 0;
    private Bundle navBundle;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nav;
    }

    @Override
    protected void initEvent() {
        super.initEvent();


    }

    /**
     * 配置相关信息
     * @param context
     * @param fragmentManager
     * @param containerId
     * @param isDarkColor
     */
    public void setup(Context context, FragmentManager fragmentManager, int containerId, boolean isDarkColor) {
        this.mContext = context;
        this.mFragmentManager = fragmentManager;
        this.containerId = containerId;

        navBundle = new Bundle();
        navBundle.putBoolean("is_dark_color", isDarkColor);

        clearOldFragment();
        //默认选中首页
        doSelect(navHome, 0);
    }

    /**
     * 清除之前有过缓存的Fragment
     */
    private void clearOldFragment() {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        List<Fragment> fragmentList = mFragmentManager.getFragments();

        if (transaction == null || fragmentList == null || fragmentList.size() == 0) {
            return;
        }

        boolean doCommit = false;
        for (Fragment fragment : fragmentList) {
            if (fragment != this && fragment != null) {
                transaction.remove(fragment);
                doCommit = true;
            }
        }
        if (doCommit) {
            transaction.commitNow();
        }
    }

    /**
     * 选中某个tab
     * @param newNavButton
     */
    private void doSelect(NavigationButton newNavButton, int position) {
        NavigationButton oldNavButton = null;
        if (currentNavButton != null) {
            oldNavButton = currentNavButton;
            if (oldNavButton == newNavButton) {
                //再次选中，有需求可做处理
                return;
            }
            oldNavButton.setSelected(false);
        }

        newNavButton.setSelected(true);
        currentNavButton = newNavButton;

        if (mListener != null) {
            mListener.onTabSelect(position);
        }

        doTabSelect(oldNavButton, newNavButton, position);

    }

    /**
     * 执行选中的tab界面切换
     * @param oldNavButton
     * @param newNavButton
     */
    private void doTabSelect(NavigationButton oldNavButton, NavigationButton newNavButton, int position) {
        this.selectPosition = position;

        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        if (oldNavButton != null) {
            if (oldNavButton.getFragment() != null) {
                transaction.detach(oldNavButton.getFragment());
            }
        }

        if (newNavButton != null) {
            if (newNavButton.getFragment() == null) {
                Fragment fragment = Fragment.instantiate(mContext, newNavButton.getClx().getName(), navBundle);
                transaction.add(containerId, fragment, newNavButton.getmTag());
                newNavButton.setFragment(fragment);
            } else {
                transaction.attach(newNavButton.getFragment());
            }
        }

        transaction.commitNow();

    }

    @OnClick({R.id.nav_home, R.id.nav_product, R.id.nav_info, R.id.nav_user})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_home:
                if (v instanceof NavigationButton) {
                    NavigationButton nav = (NavigationButton) v;
                    doSelect(nav, 0);
                }
                break;
            case R.id.nav_product:
                if (v instanceof NavigationButton) {
                    NavigationButton nav = (NavigationButton) v;
                    doSelect(nav, 1);
                }
                break;
            case R.id.nav_info:
                if (v instanceof NavigationButton) {
                    NavigationButton nav = (NavigationButton) v;
                    doSelect(nav, 3);
                }
                break;
            case R.id.nav_user:
                if (v instanceof NavigationButton) {
                    NavigationButton nav = (NavigationButton) v;
                    doSelect(nav, 4);
                }
                break;
            default:
                break;
        }

    }

    public int getSelectPosition() {
        return selectPosition;
    }

    /**
     * 设置选择位置
     * @param selectPosition
     */
    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        switch (selectPosition) {
            case 0:
                doSelect(navHome, 0);
                break;
            case 1:
                doSelect(navProduct, 1);
                break;
            case 2:
                doSelect(navLearnCenter, 2);
                break;
            case 3:
                doSelect(navInfo, 3);
                break;
            case 4:
                doSelect(navUser, 4);
                break;
            default:
                doSelect(navHome, 0);
                break;
        }

    }

    private OnTabSelectListener mListener;

    public interface OnTabSelectListener {
        void onTabSelect(int position);
    }

    public void setOnTabSelectListener(OnTabSelectListener mListener) {
        this.mListener = mListener;
    }
}
