# Retain Fragment Sample

A sample project using retained Fragment and RxJava Observable to handle data caching during orientation change.

The whole idea is to use an instance of viewless fragment with setRetainInstance(true) called, which keeps the instance undestroyed during orientation change and keep your Observable in there. 
Below is a sample code to be put in Fragment/Activity onCreate method, which basing on savedInstanceState creates or gets Observable. 

```
private void getOrCreateObservable(Bundle savedInstanceState) {
    if (savedInstanceState == null) {
        // first run, create observable
        mInfoObservable = createInfoObservable();
        // set Observable in retained fragment
        RetainFragmentHelper.setObject(this, getSupportFragmentManager(), mInfoObservable);
    } else {
        // following runs, get observable from retained fragment
        mInfoObservable = RetainFragmentHelper.getObjectOrNull(this, getSupportFragmentManager());
        // fragment may be removed during memory clean up, if so, create and set observable again
        if (mInfoObservable == null) {
            mInfoObservable = createInfoObservable();
            RetainFragmentHelper.setObject(this, getSupportFragmentManager(), mInfoObservable);
        }
    }
}
  
```
