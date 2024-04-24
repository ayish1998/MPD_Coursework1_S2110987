package com.example.ayishatusaeedS2110987.Activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ayishatusaeedS2110987.R;

public class LocationDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        String observationUrl = getIntent().getStringExtra("observationUrl");
        String forecastUrl = getIntent().getStringExtra("forecastUrl");

        TextView textViewTemperature = findViewById(R.id.textViewTemperature);
        TextView textViewVisibility = findViewById(R.id.textViewVisibility);
        TextView textViewPressure = findViewById(R.id.textViewPressure);
        TextView textViewHumidity = findViewById(R.id.textViewHumidity);
        TextView textViewWindDirection = findViewById(R.id.textViewWindDirection);
        TextView textViewWindSpeed = findViewById(R.id.textViewWindSpeed);
        TextView textViewWeatherCondition = findViewById(R.id.textViewWeatherCondition);
        TextView textViewDate = findViewById(R.id.textViewDate);
        TextView textViewTime = findViewById(R.id.textViewTime);
        TextView textViewForecastDay1 = findViewById(R.id.textViewForecastDay1);
        TextView textViewForecastDay2 = findViewById(R.id.textViewForecastDay2);

        // Pass all TextViews to the FetchWeatherDataTask constructor
        new FetchWeatherDataTask(textViewTemperature, textViewVisibility, textViewPressure, textViewHumidity, textViewWindDirection, textViewWindSpeed, textViewWeatherCondition, textViewDate, textViewTime, textViewForecastDay1, textViewForecastDay2).execute(observationUrl, forecastUrl);
    }
}
