package com.ln.rxjavawithretrofit.api;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * OkHttpClientHelper class initialize OkHttpClient by setting cache size, cache directory,
 * connection time out time and add logging interceptor for debug http call.
 */
class OkHttpClientHelper {

    private static final int DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB

    private OkHttpClientHelper() {
    }

    @NonNull
    static OkHttpClient provideOkHttpClient(Context context, boolean debug) {
        // Install an HTTP cache in the context cache directory.
        File cacheDir = new File(context.getCacheDir(), "http_cache");
        Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);

        OkHttpClient.Builder builder = new OkHttpClient.Builder().cache(cache);
        builder.connectTimeout(5, TimeUnit.MINUTES).readTimeout(5, TimeUnit.MINUTES);

        if (debug) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }
        return builder.build();
    }

    static OkHttpClient provideOkHttpClient(Context context) {
        return provideOkHttpClient(context, false);
    }
}