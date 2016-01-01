package com.krpiotrek.retainfragment.service.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class GithubResponse {

    @SerializedName("current_user_url")
    @NonNull
    private final String currentUserUrl;

    public GithubResponse(@NonNull String currentUserUrl) {
        this.currentUserUrl = currentUserUrl;
    }

    @NonNull
    public String getCurrentUserUrl() {
        return currentUserUrl;
    }
}
