package in.paritystack.evide;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager = null;
    LocationListener listener = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView view = (TextView) findViewById(R.id.log);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Location lastLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        view.append("Last cached location :" + decodeLocation(lastLocation) + "\n\n\n");
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Toast.makeText(MainActivity.this,"Location Changed",Toast.LENGTH_SHORT).show();
                view.append(decodeLocation(location) + "\n");

                //Toast.makeText(MainActivity.this,location.toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Toast.makeText(MainActivity.this,"Status Changed",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onProviderEnabled(String provider) {
                Toast.makeText(MainActivity.this,"Provider got enabled",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(MainActivity.this,"Provider got disabled",Toast.LENGTH_LONG).show();
            }
        };

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2*60*1000, 0, listener);

    }

    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(listener);
    }

    @Override
    public void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2 * 60 * 1000, 0, listener);
    }

    private String decodeLocation(Location location) {

        if(location == null){
            return "No location\n";
        }
        String decodedLocation = new String();

        decodedLocation = "";
        //Log.e("Manu", "Came in decodeLocation");
        //Log.e("Manu", ""+ location.getLatitude());

        decodedLocation = decodedLocation.concat("Latitude: " + location.getLatitude() + "\n");
        decodedLocation = decodedLocation.concat("Longitude: "+location.getLongitude() + "\n");
        decodedLocation = decodedLocation.concat("Time : "+location.getTime() + "\n");
        decodedLocation = decodedLocation.concat("Provider : "+location.getProvider() + "\n");

        if (location.hasAccuracy()){
            decodedLocation = decodedLocation.concat("Accuracy: "+location.getAccuracy() + "\n");
        }
        if (location.hasAltitude()){
            decodedLocation = decodedLocation.concat(""+location.getAltitude()+ "\n");
        }
        if (location.hasBearing()){
            decodedLocation = decodedLocation.concat(""+location.getBearing()+ "\n");
        }
        if (location.hasSpeed()){
            decodedLocation = decodedLocation.concat(""+location.getSpeed()+ "\n");
        }
        //Log.e("Manu",decodedLocation);
        return decodedLocation;
    }


}
