package com.example.coursework1.Activities;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursework1.Adapters.FutureAdapters;
import com.example.coursework1.Domains.FutureDomain;
import com.example.coursework1.R;

import java.util.ArrayList;

public class FutureActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener  {
    private RecyclerView.Adapter adapterTomorrow;
    public RecyclerView recyclerView;

    @Override
    public void onNavigateToHome() {
        // Empty implementation, as this method is not needed in FutureActivity
    }

    @Override
    public void onNavigateToSearch() {
        // Implement navigation to the search screen
    }

    @Override
    public void onNavigateToAbout() {
        // Implement navigation to the about screen
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future);

        initRecyclerView();
        setVariable();
    }

    private void setVariable() {
        ConstraintLayout backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> replaceFragment());
    }

    private void initRecyclerView() {
        ArrayList<FutureDomain> items = new ArrayList<>();

        items.add(new FutureDomain("Sat", "storm", "Storm", 25, 10));
        items.add(new FutureDomain("Sun", "cloudyy", "Cloudy", 25, 10));
        items.add(new FutureDomain("Mon", "windy", "Windy", 25, 10));
        items.add(new FutureDomain("Tue", "cloudy_sunny", "Cloudy Sunny", 25, 10));
        items.add(new FutureDomain("Wed", "sunny", "Sunny", 25, 10));
        items.add(new FutureDomain("Thu", "rain", "Rainy", 25, 10));

        recyclerView = findViewById(R.id.view2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        adapterTomorrow = new FutureAdapters(items);
        recyclerView.setAdapter(adapterTomorrow);
    }

    private void replaceFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        fragmentTransaction.replace(R.id.frame_layout, homeFragment);
        fragmentTransaction.commit();
    }
}
