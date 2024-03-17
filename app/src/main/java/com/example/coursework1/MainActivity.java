//
// Name:__Ayishatu Saeed__
// Student ID:__S2110987__
// Programme of Study:__Mobile Platform Development__
//
package com.example.coursework1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;
import com.example.coursework1.OnboardingAdapter;
import com.google.android.material.button.MaterialButton;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private OnboardingAdapter onboardingAdapter;
    private LinearLayout layoutOnboardingIndicators;
    private MaterialButton buttonOnboardingAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutOnboardingIndicators = findViewById(R.id.layoutOnboardingIndicators);
        buttonOnboardingAction = findViewById(R.id.buttonOnboardingAction);

        setOnboardingItems();

        final ViewPager2 onboardingViewPager = findViewById(R.id.onboardingViewpager);
        onboardingViewPager.setAdapter(onboardingAdapter);

        setupOnboardingIndicators();
        setCurrentOnboardingIndicator(0);

        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicator(position);
            }
        });

        buttonOnboardingAction.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int currentItem = onboardingViewPager.getCurrentItem();
                if (currentItem + 1 < onboardingAdapter.getItemCount()) {
                    // Move to the next onboarding screen
                    onboardingViewPager.setCurrentItem(currentItem + 1);
                } else {
                    // Start the HomeActivity if it's the last onboarding screen
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish(); // Close the current activity
                }
            }
        });
    }

    private void setOnboardingItems() {
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        OnboardingItem itemWeatherInsight = new OnboardingItem();
        itemWeatherInsight.setTitle("Welcome to Weather Insight");
        itemWeatherInsight.setDescription("Stay informed about the weather conditions in your area and beyond with Weather Insight.");
        itemWeatherInsight.setImage(R.drawable.sunny_rainy1);

        OnboardingItem itemWeatherUpdate = new OnboardingItem();
        itemWeatherUpdate.setTitle("Stay Updated");
        itemWeatherUpdate.setDescription("Get the latest weather updates and forecasts at your fingertips.");
        itemWeatherUpdate.setImage(R.drawable.sunny_rainy1);

        OnboardingItem itemPlanTogether = new OnboardingItem();
        itemPlanTogether.setTitle("Plan Your Day");
        itemPlanTogether.setDescription("Use Weather Insight to plan your day with confidence.");
        itemPlanTogether.setImage(R.drawable.img);

        onboardingItems.add(itemWeatherInsight);
        onboardingItems.add(itemWeatherUpdate);
        onboardingItems.add(itemPlanTogether);

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }

    private void setupOnboardingIndicators() {
        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8, 0);
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.onboarding_indicator_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            layoutOnboardingIndicators.addView(indicators[i]);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setCurrentOnboardingIndicator(int index) {
        int childCount = layoutOnboardingIndicators.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutOnboardingIndicators.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_active)
                );
            } else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.onboarding_indicator_inactive)
                );
            }
        }
        if(index == onboardingAdapter.getItemCount() - 1) {
            buttonOnboardingAction.setText("START");
        } else {
            buttonOnboardingAction.setText("NEXT");
        }
    }
}
