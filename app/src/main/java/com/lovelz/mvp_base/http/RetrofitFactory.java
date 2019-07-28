package com.lovelz.mvp_base.http;


import com.blankj.utilcode.util.NetworkUtils;
import com.lovelz.mvp_base.BuildConfig;
import com.lovelz.mvp_base.app.BaseApplication;
import com.lovelz.mvp_base.app.Constants;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitFactory {

    private static final Interceptor cacheControlInterceptor = chain -> {
        Request request = chain.request();
        Response originalResponse = chain.proceed(request);
        if (NetworkUtils.isAvailable()) {
            //有网络时，设置缓存为默认值
            String cacheControl = request.cacheControl().toString();
            return originalResponse.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma")
                    .build();
        } else {
            //无网络时，设置超时为一周
            int maxStale = 60 * 60 * 24 * 7;
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("Pragma")
                    .build();
        }
    };

    private volatile static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (RetrofitFactory.class) {
                if (retrofit == null) {
                    //指定缓存路径，并且最大缓存为50M
                    Cache cache = new Cache(new File(BaseApplication.appContext.getCacheDir(), "HttpCache"),
                            1024 * 1024 * 50);
                    OkHttpClient.Builder builder = new OkHttpClient.Builder()
                            .cache(cache)
                            .addInterceptor(cacheControlInterceptor)
                            .connectTimeout(10, TimeUnit.SECONDS)
                            .readTimeout(15, TimeUnit.SECONDS)
                            .writeTimeout(15, TimeUnit.SECONDS)
                            .retryOnConnectionFailure(true);

                    //Log拦截器
                    if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                        builder.addInterceptor(loggingInterceptor);
                    }

                    retrofit = new Retrofit.Builder()
                            .baseUrl(Constants.DEBUG_URL)
                            .client(builder.build())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                                              .build();
                }
            }
        }
        return retrofit;
    }
}
