// Name:Ayishatu Saeed
// Student ID:S2110987
// Programme of Study:Mobile Platform Development 23/24B
package com.example.coursework1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.coursework1.Activities.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener, SearchFragment.OnFragmentInteractionListener, AboutFragment.OnFragmentInteractionListener {
    // Your existing code

    @Override
    public void onNavigateToHome() {
        replaceFragment(new HomeFragment(), true); // Show bottom navigation
    }

    @Override
    public void onNavigateToSearch() {
        replaceFragment(new SearchFragment(), true); // Show bottom navigation
    }

    @Override
    public void onNavigateToAbout() {
        replaceFragment(new AboutFragment(), true); // Show bottom navigation
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    ViewPager mSlideViewPager;
    LinearLayout mDotLayout;
    Button backbtn, nextbtn, skipbtn;


    TextView[] dots;
    ViewPagerAdapter viewPagerAdapter;
    private ConstraintLayout includeNavigation;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backbtn = findViewById(R.id.backbtn);
        nextbtn = findViewById(R.id.nextbtn);
        skipbtn = findViewById(R.id.skipButton);

        includeNavigation = findViewById(R.id.includeNavigation);


        // Show bottom navigation starts here

        //Back button starts here
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getitem(0) > 0){

                    mSlideViewPager.setCurrentItem(getitem(-1),true);
                }
            }
        });
        //Next button starts here
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getitem(0)< 3)

                    mSlideViewPager.setCurrentItem(getitem(1),true);
                else {
                    Intent i = new Intent(MainActivity.this,mainscreen.class);
                    startActivity(i);
                    finish();

                }
            }
        });
    //Skip button starts here
        skipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this,mainscreen.class);
                startActivity(i);
                finish();
                // Navigate directly to the HomeFragment
                //HomeFragment homeFragment = new HomeFragment();
                //replaceFragment(homeFragment, true);

            }
        });


        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.indicator_layout);

        viewPagerAdapter = new ViewPagerAdapter(this);

        mSlideViewPager.setAdapter(viewPagerAdapter);

        setUpindicator(0);
        mSlideViewPager.addOnPageChangeListener(viewListerner);


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
                replaceFragment(selectedFragment, true); // Show bottom navigation
            }
            return true;
        });
    }

    public void setUpindicator(int position){

        dots = new TextView[4];
        mDotLayout.removeAllViews();

        for (int i = 0 ; i< dots.length ; i++){

            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.InActiveColor,getApplicationContext().getTheme()));
            mDotLayout.addView(dots[i]);
        }

        dots[position].setTextColor(getResources().getColor(R.color.ActiveColor,getApplicationContext().getTheme()));
    }
    //Button Behaviour starts here
    ViewPager.OnPageChangeListener viewListerner = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setUpindicator(position);

            if(position > 0){

                backbtn.setVisibility(View.VISIBLE);
            }else {

                backbtn.setVisibility(View.INVISIBLE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private int getitem(int i){

        return mSlideViewPager.getCurrentItem() + i;
    }


    private void replaceFragment(Fragment fragment, boolean showBottomNavigation) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
        if (showBottomNavigation) {
            includeNavigation.setVisibility(View.VISIBLE);
        } else {
            includeNavigation.setVisibility(View.GONE);
        }
    }


    // Implement the methods from the OnFragmentInteractionListener interface
    public interface OnFragmentInteractionListener {
        void onNavigateToHome();
        void onNavigateToSearch();
        void onNavigateToAbout();
    }




}