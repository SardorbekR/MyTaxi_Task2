package r.sardorbek.mytaxi_task2;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    TextView supportedRegion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        supportedRegion = findViewById(R.id.regionSupported_text);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        final Location centerPoint = new Location("Center of Radius");
        LatLng pinPosition = new LatLng(41.305859, 69.263757);

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pinPosition, (float) 18.5), 1, null);
        googleMap.addCircle(new CircleOptions().center(pinPosition).radius(20000).strokeColor(Color.RED));

        //The center of radius
        centerPoint.setLatitude(pinPosition.latitude);
        centerPoint.setLongitude(pinPosition.longitude);

        //When marker stops moving this function called
        googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                double CameraLat = googleMap.getCameraPosition().target.latitude;
                double CameraLong = googleMap.getCameraPosition().target.longitude;
                Location markerPosition = new Location("point B");
                markerPosition.setLatitude(CameraLat);
                markerPosition.setLongitude(CameraLong);

                if (centerPoint.distanceTo(markerPosition) > 20000) {
                    new BottomSheetDialogFragment().show(getSupportFragmentManager(), "TAG");
                }
            }
        });


        //When marker is moving this function called
        googleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                double CameraLat = googleMap.getCameraPosition().target.latitude;
                double CameraLong = googleMap.getCameraPosition().target.longitude;
                Location markerPosition = new Location("point B");
                markerPosition.setLatitude(CameraLat);
                markerPosition.setLongitude(CameraLong);

                if (centerPoint.distanceTo(markerPosition) > 20000) {
                    supportedRegion.setText("Region is not supported");
                } else {
                    supportedRegion.setText("Region is supported");
                }

            }
        });
    }
}
