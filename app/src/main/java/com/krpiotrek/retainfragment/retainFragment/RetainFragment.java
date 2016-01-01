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
import android.support.v4.app.Fragment;

public class RetainFragment<T> extends Fragment {

    @NonNull
    public static <T> RetainFragment<T> newInstance() {
        final RetainFragment<T> retainFragment = new RetainFragment<>();
        retainFragment.setRetainInstance(true);
        return retainFragment;
    }

    private T object;

    @NonNull
    public T getObject() {
        return object;
    }

    public void setObject(@NonNull T object) {
        this.object = object;
    }
}
