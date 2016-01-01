package com.krpiotrek.retainfragment;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.NonNull;

import com.krpiotrek.retainfragment.service.GithubService;

import retrofit.RestAdapter;

public class App extends Application {

    private GithubService mService;


    public static App getAppFromActivity(@NonNull Activity activity) {
        return (App) activity.getApplication();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mService = creaateService();
    }

    @NonNull
    public GithubService getService() {
        return mService;
    }

    @NonNull
    private GithubService creaateService() {
        return new RestAdapter.Builder()
                .setLogLevel(
                        BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .setEndpoint("https://api.github.com")
                .build()
                .create(GithubService.class);
    }
}
