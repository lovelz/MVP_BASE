package com.lovelz.mvp_base.http;

import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.lovelz.mvp_base.bean.BaseData;
import com.lovelz.mvp_base.ui.base.improve.IBaseView;

import org.json.JSONException;

import java.io.EOFException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.observers.ResourceObserver;


public abstract class ICommonSubscriber<T> extends ResourceObserver<BaseData<T>> {

    private IBaseView baseView;
    private boolean isShowOtherView = false;

    public ICommonSubscriber(IBaseView baseView) {
        this.baseView = baseView;
    }

    public ICommonSubscriber(IBaseView baseView, boolean isShowOtherView) {
        this.baseView = baseView;
        this.isShowOtherView = isShowOtherView;
    }

    @Override
    public void onNext(BaseData<T> baseData) {
        if (TextUtils.equals(baseData.getCode(), "1")) {
            if (baseData.getData() == null) {
                baseView.onError("服务君不知怎么了！！！", isShowOtherView);
            } else {
                onSuccess(baseData.getData());
            }
        } else {
            baseView.onError(baseData.getMsg(), isShowOtherView);
        }
    }

    protected abstract void onSuccess(T data);

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        if (baseView == null) {
            return;
        }

        if (e instanceof EOFException || e instanceof ConnectException || e instanceof SocketException
                || e instanceof SocketTimeoutException || e instanceof UnknownHostException) {
            baseView.onNetError("网络异常，请稍后重试", isShowOtherView);
        } else if (e instanceof JSONException) {
            LogUtils.e(e.toString());
            baseView.onNetError("服务君不知怎么呢!", isShowOtherView);
        } else {
            baseView.onNetError("未知错误", isShowOtherView);
        }
    }
}
