// Name:Ayishatu Saeed
// Student ID:S2110987
// Programme of Study:Mobile Platform Development
package com.example.coursework1.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coursework1.Adapters.HourlyAdapters;
import com.example.coursework1.Domains.Hourly;
import com.example.coursework1.R;
import com.example.coursework1.RssFeedModel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomeFragment extends Fragment {
    private RecyclerView.Adapter adapterHourly;
    private RecyclerView recyclerView;
    private OnFragmentInteractionListener mListener;
    private static final String TAG = "HomeFragment";
    private static final String MAURITIUS_3_DAY_FORECAST_URL = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/934154";
    private static final String MAURITIUS_LATEST_OBSERVATION_URL = "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/934154";
    // Interface for fragment interaction
    public interface OnFragmentInteractionListener {
        void onNavigateToHome();

        void onNavigateToSearch();
        void onNavigateToAbout();
        void onFragmentInteraction(Uri uri);
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initRecyclerview(view); // Pass the inflated view to initRecyclerview
        setVariable(view);

        // Fetch and parse the 3-day forecast and latest observation data for Mauritius
        new FetchWeatherDataTask().execute(MAURITIUS_3_DAY_FORECAST_URL, MAURITIUS_LATEST_OBSERVATION_URL);

        // Example of safely accessing a view
        TextView temperatureTextView = view.findViewById(R.id.temperatureTextView);
        if (temperatureTextView != null) {
            // Now it's safe to use temperatureTextView
        }

        return view;
    }

    private void setVariable(View view) {
        TextView next3dayBtn = view.findViewById(R.id.nextBtn);
        next3dayBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), FutureActivity.class);
            startActivity(intent);
        });
    }

    private void initRecyclerview(@NonNull View view) {
        ArrayList<Hourly> items = new ArrayList<>();

        items.add(new Hourly("9 pm", 28, "cloudyy"));
        items.add(new Hourly("11 pm", 29, "sunny"));
        items.add(new Hourly("12 pm", 30, "windy"));
        items.add(new Hourly("1 am", 29, "rainy"));
        items.add(new Hourly("2 am", 27, "storm"));

        recyclerView = view.findViewById(R.id.view1); // Use the passed view to find the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        adapterHourly = new HourlyAdapters(items);
        recyclerView.setAdapter(adapterHourly);
    }


    public void showBottomNavigation() {
        // Implement logic to show the bottom navigation
    }

    @Override
    public void onResume() {
        super.onResume();
        showBottomNavigation();
    }

    private class FetchWeatherDataTask extends AsyncTask<String, Void, List<RssFeedModel>> {

        @NonNull
        @Override
        protected List<RssFeedModel> doInBackground(@NonNull String... urls) {
            List<RssFeedModel> items = new ArrayList<>();
            for (String url : urls) {
                try {
                    URL urlObj = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
                    connection.setRequestMethod("GET");
                    connection.connect();

                    InputStream inputStream = connection.getInputStream();
                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    factory.setNamespaceAware(false);
                    XmlPullParser xmlPullParser = factory.newPullParser();
                    xmlPullParser.setInput(inputStream, null);

                    if (url.contains("forecast")) {
                        items.addAll(parseForecastFeed(xmlPullParser));
                    } else if (url.contains("observation")) {
                        items.addAll(parseObservationFeed(xmlPullParser));
                    }
                } catch (IOException | XmlPullParserException e) {
                    Log.e(TAG, "Error fetching or parsing RSS feed", e);
                }
            }
            return items;
        }

        @NonNull
        private List<RssFeedModel> parseForecastFeed(@NonNull XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
            String title = null;
            String link = null;
            String description = null;
            boolean isItem = false;
            List<RssFeedModel> items = new ArrayList<>();

            int eventType = xmlPullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    String name = xmlPullParser.getName();
                    if (name.equalsIgnoreCase("item")) {
                        isItem = true;
                    } else if (isItem) {
                        if (name.equalsIgnoreCase("title")) {
                            title = xmlPullParser.nextText();
                        } else if (name.equalsIgnoreCase("link")) {
                            link = xmlPullParser.nextText();
                        } else if (name.equalsIgnoreCase("description")) {
                            description = xmlPullParser.nextText();
                        }
                    }
                } else if (eventType == XmlPullParser.END_TAG && xmlPullParser.getName().equalsIgnoreCase("item")) {
                    if (title != null && link != null && description != null) {
                        items.add(new RssFeedModel(title, link, description));
                        Log.d("RSS_FEED", "Forecast Title: " + title + ", Link: " + link + ", Description: " + description);
                    }
                    title = null;
                    link = null;
                    description = null;
                    isItem = false;
                }
                eventType = xmlPullParser.next();
            }
            return items;
        }


        @NonNull
        private List<RssFeedModel> parseObservationFeed(@NonNull XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
            String title = null;
            String link = null;
            String description = null;
            boolean isItem = false;
            List<RssFeedModel> items = new ArrayList<>();

            int eventType = xmlPullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    String name = xmlPullParser.getName();
                    if (name.equalsIgnoreCase("item")) {
                        isItem = true;
                    } else if (isItem) {
                        if (name.equalsIgnoreCase("title")) {
                            title = xmlPullParser.nextText();
                        } else if (name.equalsIgnoreCase("link")) {
                            link = xmlPullParser.nextText();
                        } else if (name.equalsIgnoreCase("description")) {
                            description = xmlPullParser.nextText();
                            // Extract individual data from the description
                            String temperature = extractData(description, "Temperature (\\d+\\.?\\d*)°C");
                            String windDirection = extractData(description, "Wind Direction (\\w+\\s?\\w*)");
                            String windSpeed = extractData(description, "Wind Speed (\\d+mph)");
                            String humidity = extractData(description, "Humidity (\\d+)%");
                            String pressure = extractData(description, "Pressure (\\d+mb)");
                            String visibility = extractData(description, "Visibility (\\w+)");
                            // Log extracted data
                            Log.d("RSS_FEED", "Temperature: " + temperature + ", Wind Direction: " + windDirection + ", Wind Speed: " + windSpeed + ", Humidity: " + humidity + ", Pressure: " + pressure + ", Visibility: " + visibility);
                        }
                    }
                } else if (eventType == XmlPullParser.END_TAG && xmlPullParser.getName().equalsIgnoreCase("item")) {
                    if (title != null && link != null && description != null) {
                        items.add(new RssFeedModel(title, link, description));
                    }
                    title = null;
                    link = null;
                    description = null;
                    isItem = false;
                }
                eventType = xmlPullParser.next();
            }
            return items;
        }

        @Nullable
        private String extractData(String description, String regex) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(description);
            if (matcher.find()) {
                return matcher.group(1);
            }
            return null;
        }

        // Example method to navigate to HomeFragment
    public void navigateToHome() {
        if (listener != null) {
            listener.onNavigateToSearch();
        }
    }

    // Example method to navigate to AboutFragment
    public void navigateToAbout() {
        if (listener != null) {
            listener.onNavigateToAbout();
        }
    }

        @Override
        protected void onPostExecute(@NonNull List<RssFeedModel> items) {
            if (items.size() > 0 && isAdded()) { // Check if the fragment is still added to its activity
                RssFeedModel firstItem = items.get(0);
                // Assuming the first item contains the observation data
                String temperature = extractData(firstItem.getDescription(), "Temperature: (\\d+\\.?\\d*)°C");
                String windDirection = extractData(firstItem.getDescription(), "Wind Direction: (\\w+\\s?\\w*)");
                String windSpeed = extractData(firstItem.getDescription(), "Wind Speed: (\\d+mph)");
                String humidity = extractData(firstItem.getDescription(), "Humidity: (\\d+)%");
                String pressure = extractData(firstItem.getDescription(), "Pressure: (\\d+mb)");
                String visibility = extractData(firstItem.getDescription(), "Visibility: (\\w+)");

                // Safely access the views
                View view = getView();
                if (view != null) {
                    TextView temperatureTextView = view.findViewById(R.id.temperatureTextView);
                    TextView windDirectionTextView = view.findViewById(R.id.windDirectionTextView);
                    TextView windSpeedTextView = view.findViewById(R.id.windSpeedTextView);
                    TextView humidityTextView = view.findViewById(R.id.humidityTextView);
                    TextView pressureTextView = view.findViewById(R.id.pressureTextView);
                    TextView visibilityTextView = view.findViewById(R.id.visibilityTextView);

                    temperatureTextView.setText(temperature);
                    windDirectionTextView.setText(windDirection);
                    windSpeedTextView.setText(windSpeed);
                    humidityTextView.setText(humidity);
                    pressureTextView.setText(pressure);
                    visibilityTextView.setText(visibility);
                }
            }
        }

    }

}
