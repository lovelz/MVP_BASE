package com.lovelz.mvp_base.ui.base.improve;


import com.lovelz.mvp_base.http.RetrofitFactory;
import com.lovelz.mvp_base.http.api.APIService;

public abstract class IPresenter<T extends IBaseView> {

    protected T mView;

    public IPresenter(T view) {
        this.mView = view;
    }

    protected APIService getApiService() {
        return RetrofitFactory.getRetrofit().create(APIService.class);
    }

}
