# RxjavaWithRetrofit

Retrofit network call using RxJava 2 example


# Api Call Code

```Java
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
```
