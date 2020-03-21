package r.sardorbek.mytaxi_task2

import android.graphics.Color
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.bottom_dialog_region.*

class MapsActivity : FragmentActivity(), OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {

        val centerPoint = Location("Center of Radius")
        val pinPosition = LatLng(41.305859, 69.263757)

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pinPosition, 18.5.toFloat()), 1, null)
        googleMap.addCircle(CircleOptions().center(pinPosition).radius(9500.0).strokeColor(Color.RED))

        //The center of radius
        centerPoint.latitude = pinPosition.latitude
        centerPoint.longitude = pinPosition.longitude

        //When marker stops moving this function called
        googleMap.setOnCameraIdleListener {
            val cameraLat = googleMap.cameraPosition.target.latitude
            val cameraLong = googleMap.cameraPosition.target.longitude
            val markerPosition = Location("Marker position")
            markerPosition.latitude = cameraLat
            markerPosition.longitude = cameraLong
            if (centerPoint.distanceTo(markerPosition) > 9500) {
                BottomSheetDialogFragment().show(supportFragmentManager, "TAG")
            }
        }

        //When marker is moving this function called
        googleMap.setOnCameraMoveListener {
            val cameraLat = googleMap.cameraPosition.target.latitude
            val cameraLong = googleMap.cameraPosition.target.longitude
            val markerPosition = Location("Marker position")
            markerPosition.latitude = cameraLat
            markerPosition.longitude = cameraLong
            if (centerPoint.distanceTo(markerPosition) > 9500) {
                regionSupported.text = getString(R.string.region_is_not_supported)
            } else {
                regionSupported.text = getString(R.string.region_is_supported)
            }
        }
    }
}