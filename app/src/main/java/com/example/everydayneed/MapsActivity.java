package com.example.everydayneed;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;

public class MapsActivity extends AppCompatActivity {

    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_maps);

        ImageButton imageButtonMaps = findViewById(R.id.imageButtonMaps);
        mapView = findViewById(R.id.mapview);

        imageButtonMaps.setOnClickListener(v -> goToHomePage());

        MapKitFactory.setApiKey("8ae00d4e-743a-4f7a-b7e9-766eb32baaf7");
        MapKitFactory.initialize(this);

        mapView.getMap().move(
                new CameraPosition(new Point(56.007673, 92.847295), 10.0f, 0.0f, 0.0f),
                new com.yandex.mapkit.Animation(Animation.Type.SMOOTH, 0),
                null);
        Point placemarkPoint = new Point(56.007673, 92.847295);
        String placemarkTitle = "Органный зал";

        PlacemarkMapObject placemark = mapView.getMap().getMapObjects().addPlacemark(placemarkPoint);
        placemark.setUserData(placemarkTitle);
    }


    private void goToHomePage() {
        Intent intent = new Intent(MapsActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }
}
