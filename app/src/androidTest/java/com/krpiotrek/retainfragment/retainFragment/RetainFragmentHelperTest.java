package com.krpiotrek.retainfragment.retainFragment;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.app.AppCompatActivity;

import com.krpiotrek.retainfragment.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(AndroidJUnit4.class)
public class RetainFragmentHelperTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testSettingAndRetrieving() {
        final AppCompatActivity activity = activityRule.getActivity();

        final String object = "test";
        RetainFragmentHelper.setObject(this, activity.getSupportFragmentManager(), object);
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        final String value = RetainFragmentHelper.getObjectOrNull(this, activity.getSupportFragmentManager());

        assertThat(value, equalTo(object));
    }

    @Test
    public void testSettingAndRetrievingAfterRotation() {
        final AppCompatActivity activity = activityRule.getActivity();

        final String object = "test";
        RetainFragmentHelper.setObject(this, activity.getSupportFragmentManager(), object);
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        rotateScreen();
        final String value = RetainFragmentHelper.getObjectOrNull(this, activity.getSupportFragmentManager());

        assertThat(value, equalTo(object));
    }

    private void rotateScreen() {
        Context context = InstrumentationRegistry.getTargetContext();
        int orientation = context.getResources().getConfiguration().orientation;

        Activity activity = activityRule.getActivity();
        activity.setRequestedOrientation(
                (orientation == Configuration.ORIENTATION_PORTRAIT) ?
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}