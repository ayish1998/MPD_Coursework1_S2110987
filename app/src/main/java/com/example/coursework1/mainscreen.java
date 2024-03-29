package com.example.coursework1;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.coursework1.Activities.HomeFragment;

public class mainscreen extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);

        // Array of countries
        String[] countries = {"Glasgow", "London", "New York", "Oman", "Mauritius", "Bangladesh"};

        // Find the ListView and EditText defined in activity_mainscreen.xml
        ListView listView = findViewById(R.id.listView);
        EditText searchBar = findViewById(R.id.searchBar);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, countries);

        // Attach the adapter to the ListView
        listView.setAdapter(adapter);

        // Set an OnEditorActionListener on the searchBar to listen for when the user submits the search query
        searchBar.setOnEditorActionListener((v, actionId, event) -> {
            String query = searchBar.getText().toString().trim();
            if (query.equalsIgnoreCase("Mauritius")) {
                HomeFragment homeFragment = new HomeFragment();
                navigateToHomeFragment(homeFragment);
                return true; // Consume the event
            }
            return false; // Let the system handle the event
        });
    }

    @Override
    public void onNavigateToHome() {

    }

    @Override
    public void onNavigateToSearch() {

    }

    @Override
    public void onNavigateToAbout() {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        // Implement the method to handle interactions from the fragment
    }
    private void navigateToHomeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
