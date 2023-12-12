package com.example.test;

import android.widget.DatePicker;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.team12.R;
import com.example.team12.logging.LoggingActivity;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class FragmentSignupTest {

    private static class DelayIdlingResource implements IdlingResource {
        private volatile ResourceCallback resourceCallback;
        private long startTime;
        private long waitingTime = 10000; // Wait time: 10 seconds

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
    public ActivityTestRule<LoggingActivity> activityRule = new ActivityTestRule<>(LoggingActivity.class);

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
    public void testSignupFailByDuplicatedEmail() {
        // Populate the signup fields with valid data and click signup
        Espresso.onView(ViewMatchers.withId(R.id.dont_have_account_2)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.name_input)).perform(ViewActions.typeText("Test"));
        selectDateOfBirth(1990, 1, 1);
        Espresso.onView(ViewMatchers.withId(R.id.email_input)).perform(ViewActions.typeText("haha@gmail.com"));
        Espresso.onView(ViewMatchers.withId(R.id.username_input)).perform(ViewActions.typeText("john_doe"));
        Espresso.onView(ViewMatchers.withId(R.id.password_input)).perform(ViewActions.typeText("password"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.signup_button)).perform(ViewActions.click());

        // Start waiting for 10 seconds
        delayIdlingResource.startWaiting();

        // Check if the main screen is displayed after successful signup
        Espresso.onView(ViewMatchers.withId(R.id.name_input)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.dob_input)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.email_input)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.username_input)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.password_input)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.signup_button)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testSignupFailByEmptyField() {
        // Populate the signup fields with valid data and click signup
        Espresso.onView(ViewMatchers.withId(R.id.dont_have_account_2)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.name_input)).perform(ViewActions.typeText(""));
        selectDateOfBirth(1990, 1, 1);
        Espresso.onView(ViewMatchers.withId(R.id.email_input)).perform(ViewActions.typeText(""));
        Espresso.onView(ViewMatchers.withId(R.id.username_input)).perform(ViewActions.typeText("john_doe"));
        Espresso.onView(ViewMatchers.withId(R.id.password_input)).perform(ViewActions.typeText("password"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.signup_button)).perform(ViewActions.click());

        // Start waiting for 10 seconds
        delayIdlingResource.startWaiting();

        // Check if the main screen is displayed after successful signup
        Espresso.onView(ViewMatchers.withId(R.id.name_input)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.dob_input)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.email_input)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.username_input)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.password_input)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.signup_button)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testSignupSuccess() {
        // Populate the signup fields with invalid data and click signup
        Espresso.onView(ViewMatchers.withId(R.id.dont_have_account_2)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withId(R.id.name_input)).perform(ViewActions.typeText("Test"));
        selectDateOfBirth(2023, 10, 12);
        Espresso.onView(ViewMatchers.withId(R.id.email_input)).perform(ViewActions.typeText("haha1@gmail.com"));
        Espresso.onView(ViewMatchers.withId(R.id.username_input)).perform(ViewActions.typeText("test1"));
        Espresso.onView(ViewMatchers.withId(R.id.password_input)).perform(ViewActions.typeText("test123"));
        Espresso.closeSoftKeyboard();
        Espresso.onView(ViewMatchers.withId(R.id.signup_button)).perform(ViewActions.click());

        // Start waiting for 10 seconds
        delayIdlingResource.startWaiting();

        // Check if the signup components are still displayed after failed signup
        Espresso.onView(ViewMatchers.withId(R.id.name_input)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.dob_input)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.email_input)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.username_input)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.password_input)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.signup_button)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    private void selectDateOfBirth(int year, int month, int day) {
        // Click on the date of birth EditText to open the DatePickerDialog
        Espresso.onView(ViewMatchers.withId(R.id.dob_input)).perform(ViewActions.click());

        // Set the date in the DatePickerDialog
        Espresso.onView(ViewMatchers.withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(year, month, day));

        // Confirm the selected date
        Espresso.onView(ViewMatchers.withText("OK")).perform(ViewActions.click());
    }
}

