package com.example.ayishatusaeedS2110987;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ayishatusaeedS2110987.Adapters.CustomLocationAdapter;
import com.example.ayishatusaeedS2110987.Activities.LocationDataActivity;
import com.example.ayishatusaeedS2110987.Activities.WeatherData;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class mainscreen extends AppCompatActivity {

    private Map<String, Integer> locationMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);

        // Initialize the location map
        locationMap.put("Glasgow", 2648579);
        locationMap.put("London", 2643743);
        locationMap.put("New York", 5128581);
        locationMap.put("Oman", 287286);
        locationMap.put("Mauritius", 934154);
        locationMap.put("Bangladesh", 1185241);

        // Initialize the list of locations and corresponding image IDs
        List<String> locations = Arrays.asList("Glasgow", "London", "New York", "Oman", "Mauritius", "Bangladesh");
        int[] imageIds = new int[]{R.drawable.flag_of_scotland, R.drawable.longdon_flag, R.drawable.new_york_flag, R.drawable.oman_flag, R.drawable.mauritius_flag, R.drawable.bangledesh_flag};

        // Set up the ListView with the custom adapter
        ListView listViewLocations = findViewById(R.id.listViewLocations);
        CustomLocationAdapter adapter = new CustomLocationAdapter(mainscreen.this, locations, imageIds);
        listViewLocations.setAdapter(adapter);

        // Handle location clicks
        listViewLocations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedLocation = (String) parent.getItemAtPosition(position);
                int locationId = locationMap.get(selectedLocation);

                // Generate URLs for observation and forecast
                String observationUrl = WeatherData.getObservationUrl(locationId);
                String forecastUrl = WeatherData.getForecastUrl(locationId);

                // Start the new activity or fragment and pass the URLs
                Intent intent = new Intent(mainscreen.this, LocationDataActivity.class);
                intent.putExtra("observationUrl", observationUrl);
                intent.putExtra("forecastUrl", forecastUrl);
                startActivity(intent);
            }
        });
    }
}
