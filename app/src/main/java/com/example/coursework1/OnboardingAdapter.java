//
// Name:__Ayishatu Saeed__
// Student ID:__S2110987__
// Programme of Study:__Mobile Platform Development__
//
package com.example.coursework1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class OnboardingAdapter extends RecyclerView.Adapter<OnboardingAdapter.OnboardingViewHolder> {

    private List<OnboardingItem> onboardingItems;

    public OnboardingAdapter(List<OnboardingItem> onBoardingItems) {
        this.onboardingItems = onBoardingItems;
    }

    @NonNull
    @Override
    public OnboardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_onboarding, parent, false);
        return new OnboardingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OnboardingViewHolder holder, int position) {
        OnboardingItem onboardingItem = onboardingItems.get(position);
        holder.bind(onboardingItem);
    }

    @Override
    public int getItemCount() {
        return onboardingItems.size();
    }

    public static class OnboardingViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView;
        TextView descriptionTextView;

        public OnboardingViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageOnboarding);
            titleTextView = itemView.findViewById(R.id.textTitle);
            descriptionTextView = itemView.findViewById(R.id.textDescription);
        }

        public void bind(@NonNull OnboardingItem onboardingItem) {
            imageView.setImageResource(onboardingItem.getImage());
            titleTextView.setText(onboardingItem.getTitle());
            descriptionTextView.setText(onboardingItem.getDescription());
        }
    }

}
