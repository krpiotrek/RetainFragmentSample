/*
 * Copyright (C) 2016 Piotr Krystyniak
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.krpiotrek.retainfragment.retainFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import static com.krpiotrek.retainfragment.util.Preconditions.checkNotNull;

public class RetainFragmentHelper {

    private static final String RETAIN_FRAGMENT_TAG = "retain_fragment";

    /**
     * @return @Nullable RetainFragment. Keep in mind, even if fragment has been set, system may
     * remove it from memory at arbitrary time.
     */
    @Nullable
    private static <T> RetainFragment<T> getInstance(FragmentManager fragmentManager, String tag) {
        //noinspection unchecked
        return (RetainFragment<T>) fragmentManager.findFragmentByTag(tag);
    }

    /**
     * @return get Object or null if fragment has been cleaned up.
     */
    @Nullable
    public static <T> T getObjectOrNull(@NonNull Object tag,
                                        @NonNull FragmentManager fragmentManager) {
        final RetainFragment<T> instance = RetainFragmentHelper.getInstance(fragmentManager, getTag(tag));
        if (instance == null) {
            return null;
        }

        return checkNotNull(instance.getObject());
    }

    public static <T> void setObject(@NonNull Object tag,
                                     @NonNull FragmentManager fragmentManager,
                                     @NonNull T object) {
        RetainFragment<T> instance = getInstance(fragmentManager, getTag(tag));
        if (instance == null) {
            instance = RetainFragment.newInstance();
            fragmentManager
                    .beginTransaction()
                    .add(instance, getTag(tag))
                    .commit();
        }
        instance.setObject(object);
    }

    @NonNull
    private static String getTag(@NonNull Object object) {
        return RETAIN_FRAGMENT_TAG + object.getClass().getCanonicalName();
    }
}