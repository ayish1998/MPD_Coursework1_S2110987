package com.example.ayishatusaeedS2110987.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ayishatusaeedS2110987.R;

import java.util.List;

public class CustomLocationAdapter extends ArrayAdapter<String> {

    private List<String> locations;
    private int[] imageIds;

    public CustomLocationAdapter(Context context, List<String> locations, int[] imageIds) {
        super(context, R.layout.list_item_location, locations);
        this.locations = locations;
        this.imageIds = imageIds;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_location, parent, false);
        }

        String location = locations.get(position);
        ImageView imageViewFlag = convertView.findViewById(R.id.imageViewFlag);
        TextView textViewLocation = convertView.findViewById(R.id.textViewLocation);

        // Set the flag image and country name
        imageViewFlag.setImageResource(imageIds[position]);
        textViewLocation.setText(location);

        return convertView;
    }
}
