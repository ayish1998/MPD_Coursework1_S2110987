package com.example.ayishatusaeedS2110987.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.ayishatusaeedS2110987.AboutFragment;
import com.example.ayishatusaeedS2110987.R;
import com.example.ayishatusaeedS2110987.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FragmentNavigation extends AppCompatActivity {

    private static final String TAG = "FragmentNavigation";
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_navigation);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        // Load the HomeFragment initially
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, new HomeFragment())
                .commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
                        Log.d(TAG, "Replacing fragment with: " + selectedFragment.getClass().getSimpleName());
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame_layout, selectedFragment)
                                .commit();
                        return true; // Return true to indicate that the item has been selected
                    }

                    return false; // Return false if no fragment was selected
                }
            };
}