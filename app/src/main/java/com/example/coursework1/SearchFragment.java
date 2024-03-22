// Name:Ayishatu Saeed
// Student ID:S2110987
// Programme of Study:Mobile Platform Development
package com.example.coursework1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class SearchFragment extends Fragment {

    // Interface for fragment interaction
    public interface OnFragmentInteractionListener {
        void onNavigateToHome();
        void onNavigateToAbout();
    }

    private OnFragmentInteractionListener listener;

    @Override
    public void onAttach(@NonNull android.content.Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    // Example method to navigate to HomeFragment
    public void navigateToHome() {
        if (listener != null) {
            listener.onNavigateToHome();
        }
    }

    // Example method to navigate to AboutFragment
    public void navigateToAbout() {
        if (listener != null) {
            listener.onNavigateToAbout();
        }
    }
}
