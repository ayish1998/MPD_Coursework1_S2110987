// Name:Ayishatu Saeed
// Student ID:S2110987
// Programme of Study:Mobile Platform Development 23/24B
package com.example.coursework1;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener, SearchFragment.OnFragmentInteractionListener, AboutFragment.OnFragmentInteractionListener {
    private ViewPager2 onboardingViewPager;
    private LinearLayout layoutOnboardingIndicators;
    private MaterialButton buttonOnboardingAction;
    private ConstraintLayout includeNavigation;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutOnboardingIndicators = findViewById(R.id.layoutOnboardingIndicators);
        onboardingViewPager = findViewById(R.id.viewPager2);
        buttonOnboardingAction = findViewById(R.id.btnNext);
        includeNavigation = findViewById(R.id.includeNavigation);
        tabLayout = findViewById(R.id.tabLayout);

        List<OnboardingItem> onBoardingItems = Arrays.asList(
                new OnboardingItem(R.drawable.sunny_rainy1, "Title 1", "Description 1"),
                new OnboardingItem(R.drawable.img_removebg_preview, "Title 2", "Description 2"),
                new OnboardingItem(R.drawable.img, "Title 3", "Description 3")
        );

        OnboardingAdapter onboardingAdapter = new OnboardingAdapter(onBoardingItems);
        onboardingViewPager.setAdapter(onboardingAdapter);

        setOnboardingIndicators(onBoardingItems.size());

        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                setCurrentOnboardingIndicators(position);
                // Show or hide the back button based on the current page
                MaterialButton btnBack = findViewById(R.id.btnBack);
                if (position == 0) {
                    // Hide the back button on the first page
                    btnBack.setVisibility(View.GONE);
                } else {
                    // Show the back button on the second and third pages
                    btnBack.setVisibility(View.VISIBLE);
                }

                // Adjust the visibility of the "Skip" button based on the current page
                MaterialButton btnSkip = findViewById(R.id.btnSkip);
                if (position == 0) {
                    // Show the "Skip" button only on the first page
                    btnSkip.setVisibility(View.VISIBLE);
                } else {
                    // Hide the "Skip" button on other pages
                    btnSkip.setVisibility(View.GONE);
                }
            }
        });


        // Handle the skip button click
        MaterialButton btnSkip = findViewById(R.id.btnSkip);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate directly to the HomeFragment
                replaceFragment(new HomeFragment());
            }
        });

        // Handle the back button click
        MaterialButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Go to the previous page
                int currentPosition = onboardingViewPager.getCurrentItem();
                if (currentPosition > 0) {
                    onboardingViewPager.setCurrentItem(currentPosition - 1, true);
                }
            }
        });

        buttonOnboardingAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPosition = onboardingViewPager.getCurrentItem();
                if (currentPosition < onBoardingItems.size() - 1) {
                    onboardingViewPager.setCurrentItem(currentPosition + 1, true);
                } else {
                    // Change the button text to "Let's start"
                    buttonOnboardingAction.setText(R.string.intro_lets_start);
                    // Hide the onboarding UI elements
                    onboardingViewPager.setVisibility(View.GONE);
                    layoutOnboardingIndicators.setVisibility(View.GONE);
                    buttonOnboardingAction.setVisibility(View.GONE);
                    // Hide the skip button
                    findViewById(R.id.btnSkip).setVisibility(View.GONE);
                    // Hide the TabLayout
                    tabLayout.setVisibility(View.GONE);
                    // Navigate to the main fragment screens
                    replaceFragment(new HomeFragment());
                    includeNavigation.setVisibility(View.VISIBLE);
                }
            }
        });
        // Initialize the BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.search:
                    selectedFragment = new SearchFragment();
                    break;
                case R.id.about:
                    selectedFragment = new AboutFragment();
                    break;
            }
            if (selectedFragment != null) {
                replaceFragment(selectedFragment);
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }


    private void setOnboardingIndicators(int count) {
        layoutOnboardingIndicators.removeAllViews();
        for (int i = 0; i < count; i++) {
            ImageView indicator = new ImageView(this);
            indicator.setImageResource(R.drawable.indicator_dot);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(10, 0, 10, 0);
            indicator.setLayoutParams(params);
            layoutOnboardingIndicators.addView(indicator);
        }
    }

    private void setCurrentOnboardingIndicators(int position) {
        for (int i = 0; i < layoutOnboardingIndicators.getChildCount(); i++) {
            ImageView indicator = (ImageView) layoutOnboardingIndicators.getChildAt(i);
            if (i == position) {
                indicator.setImageResource(R.drawable.indicator_dot_selected);
            } else {
                indicator.setImageResource(R.drawable.indicator_dot);
            }
        }
    }

    // Implement the methods from the OnFragmentInteractionListener interface
    @Override
    public void onNavigateToHome() {
        replaceFragment(new HomeFragment());
    }
    @Override
    public void onNavigateToSearch() {
        // Implement navigation to the SearchFragment
        replaceFragment(new SearchFragment());
    }
    @Override
    public void onNavigateToAbout() {
        // Implement navigation to the AboutFragment
        replaceFragment(new AboutFragment());
    }

}