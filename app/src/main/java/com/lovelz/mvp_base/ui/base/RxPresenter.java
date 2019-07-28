package com.lovelz.mvp_base.ui.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 用于订阅与取消订阅，后续present需继承此
 *
 * @author lovelz
 * @date on 2018/4/19.
 */

public abstract class RxPresenter<T extends BaseView> implements BasePresenter<T> {

    protected T mView;

    private CompositeDisposable compositeDisposable;

    /**
     * 订阅
     *
     * @param disposable
     */
    protected void addSubscribe(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    /**
     * 取消订阅
     */
    private void unSubscribe() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
        unSubscribe();
    }
}
