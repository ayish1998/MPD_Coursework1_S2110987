package com.example.ayishatusaeedS2110987.Activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ayishatusaeedS2110987.Adapters.FutureAdapters;
import com.example.ayishatusaeedS2110987.Domains.ForecastData;
import com.example.ayishatusaeedS2110987.Domains.FutureDomain;
import com.example.ayishatusaeedS2110987.R;

import java.util.ArrayList;
import java.util.List;

public class FutureActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FutureAdapters adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future);

        // Initialize the RecyclerView
        recyclerView = findViewById(R.id.view2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Example: Fetch ForecastData (this should be replaced with actual data fetching logic)
        List<ForecastData> forecastDataList = fetchForecastData();

        // Convert ForecastData to FutureDomain
        ArrayList<FutureDomain> items = new ArrayList<>();
        for (ForecastData forecastData : forecastDataList) {
            items.add(mapForecastDataToFutureDomain(forecastData));
        }

        // Initialize the adapter with the items list
        adapter = new FutureAdapters(items);
        recyclerView.setAdapter(adapter);
    }

    @NonNull
    private List<ForecastData> fetchForecastData() {
        // Simulate fetching ForecastData (replace with actual data fetching logic)
        List<ForecastData> forecastDataList = new ArrayList<>();
        // Example ForecastData object
        ForecastData forecastData = new ForecastData();
        forecastData.setDay("Sat");
        forecastData.setWeatherCondition("Storm");
        forecastData.setMinTemperature("10");
        forecastData.setMaxTemperature("25");
        forecastDataList.add(forecastData);
        return forecastDataList;
    }

    @NonNull
    private FutureDomain mapForecastDataToFutureDomain(@NonNull ForecastData forecastData) {
        String day = forecastData.getDay();
        String picPath = "default"; // Assuming you have a default image for weather conditions
        String status = forecastData.getWeatherCondition();
        int highTemp = Integer.parseInt(forecastData.getMaxTemperature());
        int lowTemp = Integer.parseInt(forecastData.getMinTemperature());
        return new FutureDomain(day, picPath, status, highTemp, lowTemp);
    }
}
