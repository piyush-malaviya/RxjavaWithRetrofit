package com.ln.rxjavawithretrofit.rx2callback;

public interface RxJava2ApiCallback<T> {
    void onSuccess(T t);

    void onFailed(Throwable throwable);
}