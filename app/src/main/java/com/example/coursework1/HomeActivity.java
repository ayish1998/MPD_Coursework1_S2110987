//
// Name:__Ayishatu Saeed__
// Student ID:__S2110987__
// Programme of Study:__Mobile Platform Development__
//
package com.example.coursework1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
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



public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private static final String MAURITIUS_3_DAY_FORECAST_URL = "https://weather-broker-cdn.api.bbci.co.uk/en/forecast/rss/3day/934154";
    private static final String MAURITIUS_LATEST_OBSERVATION_URL = "https://weather-broker-cdn.api.bbci.co.uk/en/observation/rss/934154";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Fetch and parse the 3-day forecast and latest observation data for Mauritius
        new FetchWeatherDataTask().execute(MAURITIUS_3_DAY_FORECAST_URL, MAURITIUS_LATEST_OBSERVATION_URL);
    }




    private class FetchWeatherDataTask extends AsyncTask<String, Void, List<RssFeedModel>> {

        @Override
        protected List<RssFeedModel> doInBackground(String... urls) {
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

        private List<RssFeedModel> parseForecastFeed(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
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


        private List<RssFeedModel> parseObservationFeed(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
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
                            String temperature = extractData(description, "Temperature: (\\d+\\.?\\d*)°C");
                            String windDirection = extractData(description, "Wind Direction: (\\w+\\s?\\w*)");
                            String windSpeed = extractData(description, "Wind Speed: (\\d+mph)");
                            String humidity = extractData(description, "Humidity: (\\d+)%");
                            String pressure = extractData(description, "Pressure: (\\d+mb)");
                            String visibility = extractData(description, "Visibility: (\\w+)");
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

        private String extractData(String description, String regex) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(description);
            if (matcher.find()) {
                return matcher.group(1);
            }
            return null;
        }


        @Override
        protected void onPostExecute(List<RssFeedModel> items) {
            if (items.size() > 0) {
                RssFeedModel firstItem = items.get(0);
                // Assuming the first item contains the observation data
                String temperature = extractData(firstItem.getDescription(), "Temperature: (\\d+\\.?\\d*)°C");
                String windDirection = extractData(firstItem.getDescription(), "Wind Direction: (\\w+\\s?\\w*)");
                String windSpeed = extractData(firstItem.getDescription(), "Wind Speed: (\\d+mph)");
                String humidity = extractData(firstItem.getDescription(), "Humidity: (\\d+)%");
                String pressure = extractData(firstItem.getDescription(), "Pressure: (\\d+mb)");
                String visibility = extractData(firstItem.getDescription(), "Visibility: (\\w+)");

                // Update the TextViews with the extracted data
                TextView temperatureTextView = findViewById(R.id.temperatureTextView);
                TextView windDirectionTextView = findViewById(R.id.windDirectionTextView);
                TextView windSpeedTextView = findViewById(R.id.windSpeedTextView);
                TextView humidityTextView = findViewById(R.id.humidityTextView);
                TextView pressureTextView = findViewById(R.id.pressureTextView);
                TextView visibilityTextView = findViewById(R.id.visibilityTextView);

                temperatureTextView.setText("Temperature: " + temperature);
                windDirectionTextView.setText("Wind Direction: " + windDirection);
                windSpeedTextView.setText("Wind Speed: " + windSpeed);
                humidityTextView.setText("Humidity: " + humidity);
                pressureTextView.setText("Pressure: " + pressure);
                visibilityTextView.setText("Visibility: " + visibility);

            }
        }




    }

}
