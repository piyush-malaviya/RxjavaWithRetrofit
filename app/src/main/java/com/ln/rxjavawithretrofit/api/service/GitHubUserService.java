package com.ln.rxjavawithretrofit.api.service;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface GitHubUserService {

    @GET("users")
    Observable<JsonArray> getGitHubUser();

}
