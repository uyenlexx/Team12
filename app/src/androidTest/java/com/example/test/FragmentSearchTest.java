package com.example.test;

import static androidx.test.espresso.matcher.ViewMatchers.withId;

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
public class FragmentSearchTest {

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
    public void testSearch() {
        // Login
        Espresso.onView(withId(R.id.username_input)).perform(ViewActions.typeText("haha@gmail.com"));
        Espresso.onView(withId(R.id.password_input)).perform(ViewActions.typeText("password"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.login_button)).perform(ViewActions.click());
        delayIdlingResource.startWaiting();
        Espresso.onView(withId(R.id.frame_layout_main)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(withId(R.id.bottom_navigation_view)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        // Navigate to Search
        Espresso.onView(withId(R.id.search_tab)).perform(ViewActions.click());
        delayIdlingResource.startWaiting();

        Espresso.onView(withId(R.id.category_rv_child_item)).perform(ViewActions.click());

        // Close keyboard
        Espresso.closeSoftKeyboard();

        // Wait for animation/transition to complete
        delayIdlingResource.startWaiting();

        // Perform a click action
        Espresso.onView(withId(R.id.search_recycler_view)).perform(ViewActions.click());

        // Wait for animation/transition to complete
        delayIdlingResource.startWaiting();

        // Check if the BMI result is displayed correctly
        Espresso.onView(withId(R.id.frame_layout_main)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(withId(R.id.bottom_navigation_view)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));  // Update with the expected result
    }
}
