package com.example.test;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.team12.logging.LoggingActivity;
import com.example.team12.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class FragmentLoginTest {

    // Định nghĩa một IdlingResource để chờ đợi 10 giây
    private static class DelayIdlingResource implements IdlingResource {
        private volatile ResourceCallback resourceCallback;
        private long startTime;
        private long waitingTime = 10000; // Thời gian chờ đợi: 10 giây

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
    public void testLoginSuccess() {
        // Nhập thông tin đăng nhập hợp lệ và ấn nút đăng nhập
        Espresso.onView(ViewMatchers.withId(R.id.username_input)).perform(ViewActions.typeText("haha@gmail.com"));
        Espresso.onView(ViewMatchers.withId(R.id.password_input)).perform(ViewActions.typeText("password"));
        Espresso.closeSoftKeyboard(); // Ẩn bàn phím ảo nếu có
        Espresso.onView(ViewMatchers.withId(R.id.login_button)).perform(ViewActions.click());

        // Bắt đầu thời gian chờ đợi 10 giây
        delayIdlingResource.startWaiting();

        // Kiểm tra xem có chuyển đến màn hình chính sau khi đăng nhập thành công không
        Espresso.onView(ViewMatchers.withId(R.id.frame_layout_main)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.bottom_navigation_view)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testLoginFail() {
        Espresso.onView(ViewMatchers.withId(R.id.username_input)).perform(ViewActions.typeText("false_username"));
        Espresso.onView(ViewMatchers.withId(R.id.password_input)).perform(ViewActions.typeText("password"));
        Espresso.closeSoftKeyboard(); // Ẩn bàn phím ảo nếu có
        Espresso.onView(ViewMatchers.withId(R.id.login_button)).perform(ViewActions.click());

        // Bắt đầu thời gian chờ đợi 10 giây
        delayIdlingResource.startWaiting();

        Espresso.onView(ViewMatchers.withId(R.id.calo4u_title)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.login_title)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.username_input)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.password_input)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.login_button)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.or)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.login_button_2)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.dont_have_account_1)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.dont_have_account_2)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
