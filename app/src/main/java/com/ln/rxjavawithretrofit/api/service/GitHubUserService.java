package com.ln.rxjavawithretrofit.api.service;


import com.google.gson.JsonArray;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Retrofit turns your HTTP API into a Java interface.
 */
public interface GitHubUserService {

    @GET("users")
    Observable<JsonArray> getGitHubUser();

}
