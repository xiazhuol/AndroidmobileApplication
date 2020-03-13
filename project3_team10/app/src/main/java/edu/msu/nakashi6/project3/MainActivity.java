package edu.msu.nakashi6.project3;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {

    private LocationManager locationManager = null;

    private final ActiveListener activeListener = new ActiveListener();

    private int points;

    private double latitude = 0;
    private double longitude = 0;
    private boolean valid = false;

    private double toLatitude;
    private double toLongitude;

    private SharedPreferences settings = null;

    private final static String TOLAT = "tolat";
    private final static String TOLONG = "tolong";

    private final double[] hiddenLatitude = new double[4];
    private final double[] hiddenLongitude = new double[4];
    private final String[] hiddenPlaces = new String[4];
    private final String[] hiddenPlacesHints = new String[4];
    private final TextView[] textViews = new TextView[4];

    // max latitude 42.728
    // min latitude 42.722
    // max longitude -84.478
    // min longitude -84.485
//    private double maxLatitude = 42.728000;
//    private double minLatitude = 42.722000;
//    private double maxLongitude = -84.478000;
//    private double minLongitude = -84.485000;

    private int nextLocation;

    /**
     * Set all user interface components to the current state
     */
    @SuppressLint("DefaultLocale")
    private void setUI() {
        TextView viewLatitude = findViewById(R.id.textLatitude);
        TextView viewLongitude = findViewById(R.id.textLongitude);
        TextView viewDistance  = findViewById(R.id.textDistance);
        TextView viewPoints = findViewById(R.id.points);


        float[] results = new float[1];
        Location.distanceBetween(latitude, longitude, toLatitude, toLongitude, results);
        float distance = results[0];

        if(valid){
            viewLatitude.setText(String.format("%1$6.6f", latitude));
            viewLongitude.setText(String.format("%1$6.6f", longitude));
            viewDistance.setText(String.format("%1$6.1fm", distance));
        }
        else{
            viewLatitude.setText("");
            viewLongitude.setText("");
            viewDistance.setText("");
        }

        viewPoints.setText(String.format("%1$d", points));

        for(int i = 0; i < 4; i ++){
            if (i < nextLocation){
                textViews[i].setText(hiddenPlaces[i]);
            }
            else if (i == nextLocation){
                textViews[i].setText(hiddenPlacesHints[i]);
            }
            else{
                textViews[i].setText(R.string.hidden);
            }
        }

    }

    /**
     * Called when this application is no longer the foreground application.
     */
    @Override
    protected void onPause() {
        unregisterListeners();
        super.onPause();
    }

    /**
     * Called when this application becomes foreground again.
     */
    @Override
    protected void onResume() {
        super.onResume();

        setUI();
        registerListeners();
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);

        // random generate hidden location

//        for (int i = 0; i < 4; i++){
//            hiddenLatitude[i] = minLatitude + Math.random() * (maxLatitude - minLatitude);
//            hiddenLongitude[i] = minLongitude + Math.random() * (maxLongitude - minLongitude);
//        }

        initialHiddenPlaces();
        initialTextViewsList();

        if(bundle != null){
            // We have saved state
            points = bundle.getInt("POINTS");
            toLatitude = bundle.getDouble("TOLATITUDE");
            toLongitude = bundle.getDouble("TOLONGITUDE");
            nextLocation = bundle.getInt("NEXTLOCATION");
            setUI();
        }
        else{
            points = 0;
            toLatitude = 0;
            toLongitude = 0;
            nextLocation = 0;
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }

        settings = PreferenceManager.getDefaultSharedPreferences(this);

        // eb, first location
        toLatitude = Double.parseDouble(settings.getString(TOLAT, Double.toString(hiddenLatitude[nextLocation])));
        toLongitude = Double.parseDouble(settings.getString(TOLONG, Double.toString(hiddenLongitude[nextLocation])));

        // Get the location manager
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        // Force the screen to say on and bright
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void initialTextViewsList() {
        textViews[0] = findViewById(R.id.location1);
        textViews[1] = findViewById(R.id.location2);
        textViews[2] = findViewById(R.id.location3);
        textViews[3] = findViewById(R.id.location4);
    }

    private void initialHiddenPlaces() {
        // initial 4 hidden location

        // Main Lib
        hiddenLatitude[0] = 42.730707;
        hiddenLongitude[0] = -84.483135;
        hiddenPlaces[0] = "Main Library";
        hiddenPlacesHints[0] = "Hints: Books and Books.";
        // Stadium
        hiddenLatitude[1] = 42.727084;
        hiddenLongitude[1] = -84.484833;
        hiddenPlaces[1] = "Spartan Stadium";
        hiddenPlacesHints[1] = "Hints: Big Ten!!!!";
        // IC
        hiddenLatitude[2] = 42.726200;
        hiddenLongitude[2] = -84.480594;
        hiddenPlaces[2] = "International Center";
        hiddenPlacesHints[2] = "Hints: OISS and Book store.";
        // Urban Planning
        hiddenLatitude[3] = 42.723556;
        hiddenLongitude[3] = -84.482974;
        hiddenPlaces[3] = "Urban Planning";
        hiddenPlacesHints[3] = "Hints: CSE476.";
/*
        // home
        hiddenLatitude[0] = 42.772685 ;
        hiddenLongitude[0] =-84.492567;
        hiddenPlaces[0] = "Home";
        hiddenPlacesHints[0] = "Hints: The Place that you never forget.";
        // hidden tree
        hiddenLatitude[1] = 42.772685 ;
        hiddenLongitude[1] = -84.492567;
        hiddenPlaces[1] = "Hidden Tree";
        hiddenPlacesHints[1] = "Hints: Best place in East Lansing.";
        // home
        hiddenLatitude[2] = 42.772685  ;
        hiddenLongitude[2] =-84.492567;
        hiddenPlaces[2] = "Block 36";
        hiddenPlacesHints[2] = "Hints: The Place that you never forget.";

        hiddenLatitude[3] =42.772685 ;//42.747914;
        hiddenLongitude[3] = -84.492567;//-84.488860;
        hiddenPlaces[3] = "Home";
        hiddenPlacesHints[3] = "Hints: The Place that you never forget.";
*/
        // eb
//        hiddenLatitude[3] = 42.724303;
//        hiddenLongitude[3] = -84.480507;
//        hiddenPlaces[3] = "College of Engineering";
//        hiddenPlacesHints[3] = "Hints: The Place has most computers.";
    }

    private class ActiveListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            onLocation(location);
        }

        @Override
        public void onStatusChanged(String s, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {
            registerListeners();
        }

    }

    private void registerListeners() {
        unregisterListeners();

        // Create a Criteria object
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        criteria.setAltitudeRequired(true);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(false);

        String bestAvailable = locationManager.getBestProvider(criteria, true);

        if(bestAvailable != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(bestAvailable, 500, 1, activeListener);
            Location location = locationManager.getLastKnownLocation(bestAvailable);
            onLocation(location);
        }
    }

    private void unregisterListeners() {
        locationManager.removeUpdates(activeListener);
    }

    private void onLocation(Location location) {
        if(location == null) {
            return;
        }

        latitude = location.getLatitude();
        longitude = location.getLongitude();
        valid = true;

        setUI();
    }

    public void onCheckButton(View view){
        float[] results = new float[1];
        Location.distanceBetween(latitude, longitude, toLatitude, toLongitude, results);
        float distance = results[0];
        if (distance < 20){
            points += 25;
            nextLocation += 1;
            if (nextLocation == 4){
                setUI();
                Intent intent = new Intent(this, EndActivity.class);
                startActivity(intent);
            }
            else {
                toLatitude = hiddenLatitude[nextLocation];
                toLongitude = hiddenLongitude[nextLocation];
                setUI();
            }
        }
        else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, R.string.not_in_range, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);

        bundle.putInt("NEXTLOCATION", nextLocation);
        bundle.putDouble("TOLATITUDE", toLatitude);
        bundle.putDouble("TOLONGITUDE", toLongitude);
        bundle.putInt("POINTS", points);
    }
}
