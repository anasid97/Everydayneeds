package com.example.everydayneed;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnWeather = findViewById(R.id.main_btn_weather);
        Button btnMaps = findViewById(R.id.main_btn_maps);
        Button btnContacts = findViewById(R.id.main_btn_contacts);

        btnWeather.setOnClickListener(v -> goToWeatherPage());

        btnMaps.setOnClickListener(v -> goToMapsPage());

        btnContacts.setOnClickListener(v -> goToContactsPage());
    }

    private void goToWeatherPage() {
        Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
        startActivity(intent);
    }

    private void goToMapsPage() {
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(intent);
    }

    private void goToContactsPage() {
        Intent intent = new Intent(MainActivity.this, ContactsActivity.class);
        startActivity(intent);
    }
}
