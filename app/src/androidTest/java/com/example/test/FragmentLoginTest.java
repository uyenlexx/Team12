package com.example.test;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.team12.logging.LoggingActivity;
import com.example.team12.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class FragmentLoginTest {

    @Rule
    public ActivityTestRule<LoggingActivity> activityRule = new ActivityTestRule<>(LoggingActivity.class);

    @Test
    public void testLoginSuccess() {
        // Nhập thông tin đăng nhập hợp lệ và ấn nút đăng nhập
        Espresso.onView(ViewMatchers.withId(R.id.username_input)).perform(ViewActions.typeText("haha@gmail.com"));
        Espresso.onView(ViewMatchers.withId(R.id.password_input)).perform(ViewActions.typeText("password"));
        Espresso.closeSoftKeyboard(); // Ẩn bàn phím ảo nếu có
        Espresso.onView(ViewMatchers.withId(R.id.login_button)).perform(ViewActions.click());

        // Kiểm tra xem có chuyển đến màn hình chính sau khi đăng nhập thành công không
        Espresso.onView(ViewMatchers.withId(R.layout.activity_main_screen)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testLoginFailure() {
        // Nhập thông tin đăng nhập không hợp lệ và ấn nút đăng nhập
        Espresso.onView(ViewMatchers.withId(R.id.username_input)).perform(ViewActions.typeText("invalid_username"));
        Espresso.onView(ViewMatchers.withId(R.id.password_input)).perform(ViewActions.typeText("invalid_password"));
        Espresso.closeSoftKeyboard(); // Ẩn bàn phím ảo nếu có
        Espresso.onView(ViewMatchers.withId(R.id.login_button)).perform(ViewActions.click());

        // Kiểm tra xem có hiển thị thông báo lỗi không
        Espresso.onView(ViewMatchers.withId(R.layout.fragment_login)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
