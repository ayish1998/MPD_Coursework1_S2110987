package com.example.ayishatusaeedS2110987.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ayishatusaeedS2110987.AboutFragment;
import com.example.ayishatusaeedS2110987.Adapters.ForecastDataAdapter;
import com.example.ayishatusaeedS2110987.R;
import com.example.ayishatusaeedS2110987.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ForecastDataAdapter adapter;

    private static final String TAG = "HomeFragment";
    private BottomNavigationView bottomNavigationView;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize the RecyclerView
//        RecyclerView recyclerViewForecast = view.findViewById(R.id.recyclerViewForecast);
//        recyclerViewForecast.setLayoutManager(new LinearLayoutManager(getActivity()));

//        ForecastDataAdapter adapter = new ForecastDataAdapter();
//        recyclerViewForecast.setAdapter(adapter);

        fetchForecastData(adapter);

        // Make sure the BottomNavigationView is visible
        bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
        if (bottomNavigationView != null) {
            bottomNavigationView.setVisibility(View.VISIBLE);
            bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        }

        setVariable(view);
        return view;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.home:
                        // Do nothing, as we're already on the HomeFragment
                        break;
                    case R.id.search:
                        selectedFragment = new SearchFragment();
                        break;
                    case R.id.about:
                        selectedFragment = new AboutFragment();
                        break;
                }

                if (selectedFragment != null) {
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, selectedFragment)
                            .commit();
                    return true;
                }

                return false;
            };

    private void fetchForecastData(ForecastDataAdapter adapter) {
        // Use your method or library to fetch the forecast data
        // For example:
        String observationUrl = "observationUrl";
        String forecastUrl = "forecastUrl";

        new FetchWeatherDataTask(adapter).execute(observationUrl, forecastUrl);
    }

    private void setVariable(@NonNull View view) {
        Log.d(TAG, "Setting variables");

        TextView next3dayBtn = view.findViewById(R.id.nextBtn);
        if (next3dayBtn != null) {
            next3dayBtn.setOnClickListener(v -> {
                Log.d(TAG, "Next button clicked");
                if (getActivity() != null) {
                    Intent intent = new Intent(getActivity(), FutureActivity.class);
                    startActivity(intent);
                } else {
                    Log.e(TAG, "Activity is null, cannot start FutureActivity");
                }
            });
        } else {
            Log.e(TAG, "Next button not found");
        }
    }

}
