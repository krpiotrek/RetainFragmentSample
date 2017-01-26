# Retain Fragment Sample

A sample project using retained Fragment and RxJava Observable to handle data caching during orientation change.

The whole idea is to use an instance of viewless fragment with setRetainInstance(true) called, which keeps the instance undestroyed during orientation change and keep your Observable in there. 
Below is a sample code to be put in Fragment/Activity onCreate method, which basing on savedInstanceState creates or gets Observable. 

```
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
  
```
