package com.krpiotrek.retainfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.krpiotrek.retainfragment.retainFragment.RetainFragmentHelper;
import com.krpiotrek.retainfragment.service.model.GithubResponse;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private Observable<GithubResponse> mInfoObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView text = (TextView) findViewById(android.R.id.text1);
        final ProgressBar progress = (ProgressBar) findViewById(android.R.id.progress);

        getOrCreateObservable(savedInstanceState);

        mInfoObservable.subscribe(
                new Action1<GithubResponse>() {
                    @Override
                    public void call(GithubResponse response) {
                        text.setText(response.getCurrentUserUrl());
                        progress.setVisibility(View.GONE);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        text.setText("error");
                        progress.setVisibility(View.GONE);
                    }
                });
    }

    private void getOrCreateObservable(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            // first run, create observable
            createAndSetInfoObservable();
        } else {
            // following runs, get observable from retained fragment
            mInfoObservable = RetainFragmentHelper.getObjectOrNull(this, getSupportFragmentManager());
            // fragment may be removed during memory clean up, if so, create observable again
            if (mInfoObservable == null) createAndSetInfoObservable();
        }
    }

    private void createAndSetInfoObservable() {
        mInfoObservable = createInfoObservable();
        RetainFragmentHelper.setObject(this, getSupportFragmentManager(), mInfoObservable);
    }

    @NonNull
    private Observable<GithubResponse> createInfoObservable() {
        return App.getAppFromActivity(this)
                .getService()
                .baseInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache();
    }
}
