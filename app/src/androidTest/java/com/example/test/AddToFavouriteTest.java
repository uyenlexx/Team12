package com.example.test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

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
public class AddToFavouriteTest {

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
    public void addToFavouriteTest() {
        // Login
        onView(withId(R.id.username_input)).perform(ViewActions.typeText("haha@gmail.com"));
        onView(withId(R.id.password_input)).perform(ViewActions.typeText("password"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.login_button)).perform(click());
        delayIdlingResource.startWaiting();
        onView(withId(R.id.frame_layout_main)).check(matches(isDisplayed()));
        onView(withId(R.id.bottom_navigation_view)).check(matches(isDisplayed()));

//        // Navigate to Search
//        Espresso.onView(withId(R.id.search_tab)).perform(ViewActions.click());
//        delayIdlingResource.startWaiting();

        onView(withText("Bruschetta")).perform(click());

        // Wait for animation/transition to complete
        delayIdlingResource.startWaiting();

        Espresso.onView(ViewMatchers.withId(R.id.add_to_favorite_btn))
                .perform(ViewActions.scrollTo());

        onView(withId(R.id.add_to_favorite_btn)).perform(click());

        // Check if the recipe detail is displayed correctly
        Espresso.onView(ViewMatchers.withId(R.id.add_to_favorite_btn))
                .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
        Espresso.onView(ViewMatchers.withId(R.id.add_to_favorite_btn))
                .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
                .check(ViewAssertions.matches(ViewMatchers.withText("Remove from favorite")));

        onView(withId(R.id.add_to_favorite_btn)).perform(click());
    }
}
