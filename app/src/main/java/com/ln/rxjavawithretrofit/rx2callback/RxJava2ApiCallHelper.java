package com.ln.rxjavawithretrofit.rx2callback;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RxJava2ApiCallHelper {

    public static <T> Disposable call(Observable<T> observable, final RxJava2ApiCallback<T> rxApiCallback) {

        if (observable == null) {
            throw new IllegalArgumentException("Observable must not be null.");
        }

        if (rxApiCallback == null) {
            throw new IllegalArgumentException("Callback must not be null.");
        }

        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<T>() {
                    @Override
                    public void accept(@NonNull T t) throws Exception {
                        // success response
                        rxApiCallback.onSuccess(t);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        // failure response
                        if (throwable != null) {
                            rxApiCallback.onFailed(throwable);
                        } else {
                            rxApiCallback.onFailed(new Throwable("Something went wrong"));
                        }
                    }
                });
    }
}
