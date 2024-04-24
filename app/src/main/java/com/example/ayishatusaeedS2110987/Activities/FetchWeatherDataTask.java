package com.example.ayishatusaeedS2110987.Activities;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;
import android.widget.TextView;

import com.example.ayishatusaeedS2110987.Adapters.ForecastDataAdapter;
import com.example.ayishatusaeedS2110987.Domains.ForecastData;

import java.util.List;

/** @noinspection ALL*/
public class FetchWeatherDataTask extends AsyncTask<String, Void, Pair<ObservationData, List<ForecastData>>> {
    private ForecastDataAdapter forecastDataAdapter;

    public FetchWeatherDataTask(ForecastDataAdapter forecastDataAdapter) {
        this.forecastDataAdapter = forecastDataAdapter;
    }

    private TextView textViewTemperature;
    private TextView textViewVisibility;
    private TextView textViewPressure;
    private TextView textViewHumidity;
    private TextView textViewWindDirection;
    private TextView textViewWindSpeed;
    private TextView textViewWeatherCondition;
    private TextView textViewDate;
    private TextView textViewTime;
    private TextView textViewForecastDay1;
    private TextView textViewForecastDay2;


    public FetchWeatherDataTask(TextView textViewTemperature, TextView textViewVisibility, TextView textViewPressure, TextView textViewHumidity, TextView textViewWindDirection, TextView textViewWindSpeed, TextView textViewWeatherCondition, TextView textViewDate, TextView textViewTime, TextView textViewForecastDay1, TextView textViewForecastDay2) {
        this.textViewTemperature = textViewTemperature;
        this.textViewVisibility = textViewVisibility;
        this.textViewPressure = textViewPressure;
        this.textViewHumidity = textViewHumidity;
        this.textViewWindDirection = textViewWindDirection;
        this.textViewWindSpeed = textViewWindSpeed;
        this.textViewWeatherCondition = textViewWeatherCondition;
        this.textViewDate = textViewDate;
        this.textViewTime = textViewTime;
        this.textViewForecastDay1 = textViewForecastDay1;
        this.textViewForecastDay2 = textViewForecastDay2;
    }

    @Override
    protected Pair<ObservationData, List<ForecastData>> doInBackground(String... urls) {
        ObservationData observationData = null;
        List<ForecastData> forecastDataList = null;
        try {
            observationData = WeatherDataParser.fetchAndParseObservationData(urls[0]);
            forecastDataList = WeatherDataParser.fetchAndParseForecastData(urls[1]);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions, e.g., network errors
        }
        return new Pair<>(observationData, forecastDataList);
    }

    @Override
    protected void onPostExecute(Pair<ObservationData, List<ForecastData>> result) {
        ObservationData observationData = result.first;
        List<ForecastData> forecastDataList = result.second;

        if (forecastDataAdapter != null && forecastDataList != null) {
            forecastDataAdapter.setForecastDataList(forecastDataList);
            forecastDataAdapter.notifyDataSetChanged();
            Log.d("FetchWeatherDataTask", "Forecast data updated in adapter");
        }
        // Update the TextViews with the observation data
        if (observationData != null) {
            if (textViewTemperature != null) {
                textViewTemperature.setText(observationData.getTemperature());
            }
            if (textViewVisibility != null) {
                textViewVisibility.setText(observationData.getVisibility());
            }
            if (textViewPressure != null) {
                textViewPressure.setText(observationData.getPressure());
            }
            if (textViewHumidity != null) {
                textViewHumidity.setText(observationData.getHumidity());
            }
            if (textViewWindDirection != null) {
                textViewWindDirection.setText(observationData.getWindDirection());
            }
            if (textViewWindSpeed != null) {
                textViewWindSpeed.setText(observationData.getWindSpeed());
            }
            if (textViewWeatherCondition != null) {
                textViewWeatherCondition.setText(observationData.getWeatherCondition());
            }
            if (textViewDate != null) {
                textViewDate.setText(observationData.getDate());
            }
            if (textViewTime != null) {
                textViewTime.setText(observationData.getTime());
            }
        } else {
            // Handle the case where observationData is null
            Log.e("FetchWeatherDataTask", "ObservationData is null");
        }

        if (forecastDataList != null && !forecastDataList.isEmpty()) {
            int numDaysToDisplay = Math.min(forecastDataList.size(), 2); // Display the first two forecast days

            StringBuilder forecastDay1Text = new StringBuilder();
            StringBuilder forecastDay2Text = new StringBuilder();

            for (int i = 0; i < numDaysToDisplay; i++) {
                ForecastData forecastData = forecastDataList.get(i);
                if (i == 0) {
                    forecastDay1Text.append("Day 2: ").append(forecastData.getDay()).append(", Weather Condition: ").append(forecastData.getWeatherCondition()).append("\n");
                } else if (i == 1) {
                    forecastDay2Text.append("Day 3: ").append(forecastData.getDay()).append(", Weather Condition: ").append(forecastData.getWeatherCondition()).append("\n");
                }
            }

            if (textViewForecastDay1 != null) {
                textViewForecastDay1.setText(forecastDay1Text.toString());
            }
            if (textViewForecastDay2 != null) {
                textViewForecastDay2.setText(forecastDay2Text.toString());
            }
        } else {
            // Handle the case where forecastDataList is null or empty
            Log.e("FetchWeatherDataTask", "Forecast data is null or empty");
        }
    }
}