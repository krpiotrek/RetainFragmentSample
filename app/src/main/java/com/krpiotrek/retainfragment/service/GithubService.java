package com.krpiotrek.retainfragment.service;


import com.krpiotrek.retainfragment.service.model.GithubResponse;

import retrofit.http.GET;
import rx.Observable;

public interface GithubService {

    @GET("/")
    Observable<GithubResponse> baseInfo();
}
