package com.ln.rxjavawithretrofit.api;

import android.content.Context;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiHelper {

    private static final String BASE_URL = "https://api.github.com/";
    private OkHttpClient okHttpClient;

    private ApiHelper(Context context) {
        okHttpClient = OkHttpClientHelper.provideOkHttpClient(context);
    }

    public static ApiHelper getInstance(Context context) {
        return new ApiHelper(context);
    }

    private Retrofit provideRestAdapter() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public <S> S getService(Class<S> serviceClass) {
        return provideRestAdapter().create(serviceClass);
    }
}