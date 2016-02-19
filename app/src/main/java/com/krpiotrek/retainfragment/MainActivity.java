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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView text = (TextView) findViewById(android.R.id.text1);
        final ProgressBar progress = (ProgressBar) findViewById(android.R.id.progress);

        final Observable<GithubResponse> infoObservable = getOrCreateObservable(savedInstanceState);

        infoObservable.subscribe(
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

    @NonNull
    private Observable<GithubResponse> getOrCreateObservable(Bundle savedInstanceState) {
        Observable<GithubResponse> infoObservable;

        if (savedInstanceState == null) {
            // first run, create and set observable
            infoObservable = createandSetInfoObservable();
        } else {
            // following runs, get observable from retained fragment
            infoObservable = RetainFragmentHelper.getObjectOrNull(this, getSupportFragmentManager());
            // fragment may be removed during memory clean up, if so, create and set observable again
            if (infoObservable == null) {
                infoObservable = createandSetInfoObservable();
            }
        }

        return infoObservable;
    }

    @NonNull
    private Observable<GithubResponse> createandSetInfoObservable() {
        final Observable<GithubResponse> infoObservable = App.getAppFromActivity(this)
                .getService()
                .baseInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache();

        RetainFragmentHelper.setObject(this, getSupportFragmentManager(), infoObservable);
        
        return infoObservable;
    }
}
