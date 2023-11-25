package com.example.everydayneed;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherActivity extends AppCompatActivity {

    private static final String YANDEX_API_KEY = "05c23021-c39d-466a-8390-98cb8a0e0bc4";
    private static final String YANDEX_WEATHER_URL = "https://api.weather.yandex.ru/v2/informers?lat=55.75396&lon=37.620393&lang=en_US";

    private TextView textViewYandexWeather;

    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_weather);

        textViewYandexWeather = findViewById(R.id.textViewYandexWeather);
        ImageButton imageButtonWeather = findViewById(R.id.imageButtonWeather);

        imageButtonWeather.setOnClickListener(v -> goToHomePage());

        fetchWeather();
    }

    private void goToHomePage() {
        Intent intent = new Intent(WeatherActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void fetchWeather() {
        new Thread(() -> {
            String result = getWeatherData();

            handler.post(() -> updateUI(result));
        }).start();
    }

    private String getWeatherData() {
        try {
            URL url = new URL(YANDEX_WEATHER_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("X-Yandex-API-Key", YANDEX_API_KEY);

            try (InputStream inputStream = connection.getInputStream();
                 Scanner scanner = new Scanner(inputStream).useDelimiter("\\A")) {

                if (scanner.hasNext()) {
                    return scanner.next();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void updateUI(String result) {
        if (result != null) {
            try {
                JSONObject jsonResult = new JSONObject(result);
                JSONObject fact = jsonResult.getJSONObject("fact");

                String temperature = fact.getString("temp");
                String condition = fact.getString("condition");

                String weatherText = "Температура: " + temperature + "°C, Условия: " + condition;
                textViewYandexWeather.setText(weatherText);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            textViewYandexWeather.setText("Не получилось получить информацию");
        }
    }
}
