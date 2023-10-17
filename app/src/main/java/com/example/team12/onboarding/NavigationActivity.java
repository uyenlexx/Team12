package com.example.team12.onboarding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.team12.R;
import com.example.team12.logging.LoggingActivity;

public class NavigationActivity extends AppCompatActivity {
    ViewPager slideViewPager;
    LinearLayout dotIndicator;
    ViewPageAdapter viewPageAdapter;
    Button backBtn, skipBtn, nextBtn;
    TextView[] dots;

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) { }

        @Override
        public void onPageSelected(int position) {
            setDotIndicator(position);
            if (position > 0) {
                backBtn.setVisibility(View.VISIBLE);
            } else {
                backBtn.setVisibility(View.INVISIBLE);
            }
            if (position == 2) {
                nextBtn.setText(R.string.finish);
                skipBtn.setVisibility(View.INVISIBLE);
            } else {
                nextBtn.setText(R.string.next);
                skipBtn.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) { }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        backBtn = (Button) findViewById(R.id.back_btn);
        skipBtn = (Button) findViewById(R.id.skip_btn);
        nextBtn = (Button) findViewById(R.id.next_btn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getItem(0) > 0) {
                    slideViewPager.setCurrentItem(getItem(-1), true);
                }
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getItem(0) < 2) {
                    slideViewPager.setCurrentItem(getItem(1), true);
                } else {
                    Intent intent = new Intent(NavigationActivity.this, LoggingActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavigationActivity.this, LoggingActivity.class);
                startActivity(intent);
                finish();
            }
        });

        slideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        dotIndicator = (LinearLayout) findViewById(R.id.dotIndicator);

        viewPageAdapter = new ViewPageAdapter(this);
        slideViewPager.setAdapter(viewPageAdapter);

        setDotIndicator(0);
        slideViewPager.addOnPageChangeListener(viewListener);
    }

    public void setDotIndicator(int position) {
        dots = new TextView[3];
        dotIndicator.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;", Html.FROM_HTML_MODE_LEGACY));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.dark_green, getApplicationContext().getTheme()));
            dotIndicator.addView(dots[i]);
        }
        dots[position].setTextColor(getResources().getColor(R.color.medium_green, getApplicationContext().getTheme()));
    }

    private int getItem(int i) {
        return slideViewPager.getCurrentItem() + i;
    }
}