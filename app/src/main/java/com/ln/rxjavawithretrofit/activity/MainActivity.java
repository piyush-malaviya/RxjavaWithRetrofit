package com.ln.rxjavawithretrofit.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.ln.rxjavawithretrofit.R;
import com.ln.rxjavawithretrofit.adapter.GitHubUserAdapter;
import com.ln.rxjavawithretrofit.api.ApiHelper;
import com.ln.rxjavawithretrofit.api.response.GitHubUser;
import com.ln.rxjavawithretrofit.api.service.GitHubUserService;
import com.ln.rxjavawithretrofit.rx2callback.RxJava2ApiCallHelper;
import com.ln.rxjavawithretrofit.rx2callback.RxJava2ApiCallback;
import com.ln.rxjavawithretrofit.utils.NetworkUtils;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private GitHubUserAdapter mGitHubUserAdapter;
    private CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCompositeDisposable = new CompositeDisposable();
        initView();
        callApi();
    }

    /**
     * initialize views
     */
    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mGitHubUserAdapter = new GitHubUserAdapter(this);
        mRecyclerView.setAdapter(mGitHubUserAdapter);
    }

    private void callApi() {
        // check network is available or not if available then call api
        // otherwise show alert dialog
        if (!NetworkUtils.isInternetAvailable(this)) {
            NetworkUtils.showNetworkAlertDialog(this);
            return;
        }

        Observable<JsonArray> observable = ApiHelper
                .getInstance(this)
                .getService(GitHubUserService.class)
                .getGitHubUser();

        Disposable disposable = RxJava2ApiCallHelper.call(observable, new RxJava2ApiCallback<JsonArray>() {
            @Override
            public void onSuccess(JsonArray jsonArray) {
                Log.d(TAG, "onSuccess() called with: jsonArray = [" + jsonArray + "]");
                if (jsonArray != null) {
                    ArrayList<GitHubUser> gitHubUserArrayList = new ArrayList<>();
                    for (JsonElement jsonElement : jsonArray) {
                        GitHubUser gitHubUsers = new Gson().fromJson(jsonElement, GitHubUser.class);
                        gitHubUserArrayList.add(gitHubUsers);
                    }
                    mGitHubUserAdapter.setData(gitHubUserArrayList);
                }
            }

            @Override
            public void onFailed(Throwable throwable) {
                Log.d(TAG, "onFailed() called with: throwable = [" + throwable + "]");
            }
        });

        mCompositeDisposable.add(disposable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }
}
