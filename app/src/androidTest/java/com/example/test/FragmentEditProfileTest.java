package com.example.test;

import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.team12.MainActivity;
import com.example.team12.R;
import com.example.team12.logging.LoggingActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class FragmentEditProfileTest {

    private static class DelayIdlingResource implements IdlingResource {
        private volatile ResourceCallback resourceCallback;
        private long startTime;
        private long waitingTime = 10000;

        @Override
        public String getName() {
            return DelayIdlingResource.class.getName();
        }

        @Override
        public boolean isIdleNow() {
            long elapsedTime = System.currentTimeMillis() - startTime;
            boolean idle = elapsedTime >= waitingTime;

            if (idle && resourceCallback != null) {
                resourceCallback.onTransitionToIdle();
            }

            return idle;
        }

        @Override
        public void registerIdleTransitionCallback(ResourceCallback callback) {
            this.resourceCallback = callback;
        }

        void startWaiting() {
            startTime = System.currentTimeMillis();
        }
    }

    @Rule
    public ActivityTestRule<LoggingActivity> loginActivityRule = new ActivityTestRule<>(LoggingActivity.class);

    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<>(MainActivity.class);

    private DelayIdlingResource delayIdlingResource;

    @Before
    public void setup() {
        delayIdlingResource = new DelayIdlingResource();
        IdlingRegistry.getInstance().register(delayIdlingResource);
    }

    @After
    public void cleanup() {
        IdlingRegistry.getInstance().unregister(delayIdlingResource);
    }

    @Test
    public void testEditProfile() {
        // Login
        Espresso.onView(withId(R.id.username_input)).perform(ViewActions.typeText("haha@gmail.com"));
        Espresso.onView(withId(R.id.password_input)).perform(ViewActions.typeText("password"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.login_button)).perform(ViewActions.click());
        delayIdlingResource.startWaiting();
        Espresso.onView(withId(R.id.frame_layout_main)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(withId(R.id.bottom_navigation_view)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Navigate to User Profile
        Espresso.onView(withId(R.id.user_tab)).perform(ViewActions.click());
        delayIdlingResource.startWaiting();

        Espresso.onView(withId(R.id.section_text)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.profile_edit_btn)).perform(ViewActions.click());

        // Perform Edit User Profile
        Espresso.onView(allOf(withId(R.id.user_name_2), withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
                .perform(ViewActions.clearText(), ViewActions.typeText("Kaylous1"));


//        Espresso.onView(ViewMatchers.withId(R.id.user_dob)).perform(ViewActions.replaceText("70"));
//        Espresso.onView(ViewMatchers.withId(R.id.user_email)).perform(ViewActions.replaceText("haha1@gmail.com"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.profile_save_btn)).perform(ViewActions.click());
        delayIdlingResource.startWaiting();

        // Check if the BMI result is displayed correctly
        Espresso.onView(withId(R.id.user_name_1))
                .check(ViewAssertions.matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        Espresso.onView(withId(R.id.user_name_1))
                .check(ViewAssertions.matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
                .check(ViewAssertions.matches(ViewMatchers.withText("Kaylous1")));  // Update with the expected result
    }
}
