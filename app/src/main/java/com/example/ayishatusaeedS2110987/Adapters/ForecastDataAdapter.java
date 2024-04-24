package com.example.ayishatusaeedS2110987.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ayishatusaeedS2110987.Domains.ForecastData;
import com.example.ayishatusaeedS2110987.R;

import java.util.List;

public class ForecastDataAdapter extends RecyclerView.Adapter<ForecastDataAdapter.ViewHolder> {

    private List<ForecastData> forecastDataList;

    public void setForecastDataList(List<ForecastData> forecastDataList) {
        this.forecastDataList = forecastDataList;
        Log.d("ForecastDataAdapter", "Data set with size: " + forecastDataList.size());
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.forecast_data_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ForecastData forecastData = forecastDataList.get(position);
        holder.bind(forecastData);
    }

    @Override
    public int getItemCount() {
        return forecastDataList != null ? forecastDataList.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewForecastDay;
        TextView textViewWeatherCondition;
        TextView textViewMinTemperature;
        TextView textViewMaxTemperature;
        TextView textViewWindDirection;
        TextView textViewWindSpeed;
        TextView textViewVisibility;
        TextView textViewPressure;
        TextView textViewHumidity;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewForecastDay = itemView.findViewById(R.id.textViewForecastDay);
            textViewWeatherCondition = itemView.findViewById(R.id.textViewWeatherCondition);
            textViewMinTemperature = itemView.findViewById(R.id.textViewMinTemperature);
            textViewMaxTemperature = itemView.findViewById(R.id.textViewMaxTemperature);
            textViewWindDirection = itemView.findViewById(R.id.textViewWindDirection);
            textViewWindSpeed = itemView.findViewById(R.id.textViewWindSpeed);
            textViewVisibility = itemView.findViewById(R.id.textViewVisibility);
            textViewPressure = itemView.findViewById(R.id.textViewPressure);
            textViewHumidity = itemView.findViewById(R.id.textViewHumidity);
        }

        void bind(ForecastData forecastData) {
            textViewForecastDay.setText(forecastData.getDay());
            textViewWeatherCondition.setText(forecastData.getWeatherCondition());
            textViewMinTemperature.setText(forecastData.getMinTemperature());
            textViewMaxTemperature.setText(forecastData.getMaxTemperature());
            textViewWindDirection.setText(forecastData.getWindDirection());
            textViewWindSpeed.setText(forecastData.getWindSpeed());
            textViewVisibility.setText(forecastData.getVisibility());
            textViewPressure.setText(forecastData.getPressure());
            textViewHumidity.setText(forecastData.getHumidity());

        }
    }
}