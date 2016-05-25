package at.tugraz.thedrunksailor;

/**
 * Created by Strw on 11.05.16.
 */
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;


public class MapActivity extends AppCompatActivity implements
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMyLocationButtonClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {



    private Context context;

    LocationManager locman;
    Location location;

    String [][] places_list;

    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private boolean mPermissionDenied = false;

    private GoogleMap mMap;

    private Location lastlocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_layout);

        mLocationRequest = LocationRequest.create();
        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();


        context = getApplicationContext();

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mGoogleApiClient.connect();

    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        mMap.setOnMyLocationButtonClickListener(this);
        enableMyLocation();

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                marker.get
                Intent intent = new Intent(MapActivity.this, PlaceDetailActivity.class);
                startActivity(intent);



            }
        });

    }


    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            CharSequence text = "Missing Permission!";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_PERMISSION_REQUEST_CODE);

            // Permission to access the location is missing.
            //PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
            //  Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);

            locman = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            location = locman.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if(location != null){

               // mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("My Position") );
                //mMap.addMarker(new MarkerOptions().position(new LatLng(20, 20)).title("20/20") );
            }



        }


    }



    @Override
    public boolean onMyLocationButtonClick() {
        CameraPosition pos = mMap.getCameraPosition();
        updateMarker();
        Toast.makeText(this, "MyLocation button clicked" + pos.toString(), Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).

        return false;
    }




    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            mPermissionDenied = false;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }


    @Override
    public void onConnected(Bundle bundle) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            CharSequence text = "Missing Permission!";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);


        }else {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

            location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            //mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("My Position"));
            if(location != null) {
                LatLng position = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
            } else {

                Log.e("test", "No last location");
            }


            Geocoder mGeocoder = new Geocoder(this);
            List<Address> address;

            Log.e("test", "geocode");

            try {
                address = mGeocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                Log.e("test", "address");
                Address addr = address.get(0);

                String zip_code = addr.getPostalCode();
                Log.e("test", "zip " + zip_code);

                String[] params=new String[]{zip_code};
                places_list = new doTask().execute(params).get();

                Log.e("test", "finished");

                fillMap(places_list);



            }catch(Exception e){}




            /*for(int i = 0; i < my_places.length; i++){


                mMap.addMarker(new MarkerOptions().position(new LatLng( Float.parseFloat(my_places[i][5]), Float.parseFloat(my_places[i][4]) )).title(my_places[i][1]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)) );

            }*/
        }
    }

    public void updateMarker(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            CharSequence text = "Missing Permission!";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);


        }else {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

            location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            //mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("My Position"));
            if(location != null) {

                LatLng position = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 15));



                Geocoder mGeocoder = new Geocoder(this);
                List<Address> address;

                Log.e("test", "geocode");

                try {
                  address = mGeocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    Log.e("test", "address");
                    Address addr = address.get(0);

                    String zip_code = addr.getPostalCode();
                    Log.e("test", "zip " + zip_code);

                     String[] params=new String[]{zip_code};
                     places_list = new doTask().execute(params).get();

                    Log.e("test", "finished");

                    fillMap(places_list);



                }catch(Exception e){}

            } else {

                Log.e("test", "No last location");
            }


            /*for(int i = 0; i < my_places.length; i++){


                mMap.addMarker(new MarkerOptions().position(new LatLng( Float.parseFloat(my_places[i][5]), Float.parseFloat(my_places[i][4]) )).title(my_places[i][1]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)) );

            }*/
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        //u
        if(lastlocation == null) {
            lastlocation = location;
            updateMarker();
        }else if( (location.getLongitude() != lastlocation.getLongitude()) && (location.getLatitude() != lastlocation.getLatitude()) ){
            lastlocation = location;
            updateMarker();
        }

        Log.e("onLocationChanged", "Location changed");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public void fillMap(String[][] places_list){

        Log.e("test", "fillMap() called");

        Geocoder mGeocoder = new Geocoder(this);

        Log.e("test", "geocoder");

        List<Address> address;
        Log.e("test", "address");
        Log.e("test", "place list: " + places_list.length);

        for(int i = 0; i < places_list.length; i++) {
            try {
                address = mGeocoder.getFromLocationName("Inffeldgasse 10 8010 Graz",1);//places_list[i][3]+" "+ places_list[i][4] + " " + places_list[i][5], 1);


                if (address != null) {
                    Address loc = address.get(0);

                    Log.e("test","place fill adr: "+  places_list[i][1] + " " + loc.toString() );

                    // colors for types
                    if(places_list[i][2].equals("1") ){
                        Log.e("test","set marker" );
                        mMap.addMarker(new MarkerOptions().position(new LatLng(loc.getLatitude(), loc.getLongitude())).title(places_list[i][1]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                    }else if(places_list[i][2].equals("2") ){
                        mMap.addMarker(new MarkerOptions().position(new LatLng(loc.getLatitude(), loc.getLongitude())).title(places_list[i][1]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));

                    }else if(places_list[i][2].equals("3") ){
                        mMap.addMarker(new MarkerOptions().position(new LatLng(loc.getLatitude(), loc.getLongitude())).title(places_list[i][1]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

                    }else if(places_list[i][2].equals("4")){
                        mMap.addMarker(new MarkerOptions().position(new LatLng(loc.getLatitude(), loc.getLongitude())).title(places_list[i][1]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

                    }else if(places_list[i][2].equals("5") ){
                        mMap.addMarker(new MarkerOptions().position(new LatLng(loc.getLatitude(), loc.getLongitude())).title(places_list[i][1]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                    }else if(places_list[i][2].equals("6") ){
                        mMap.addMarker(new MarkerOptions().position(new LatLng(loc.getLatitude(), loc.getLongitude())).title(places_list[i][1]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));

                    }else if(places_list[i][2].equals("7") ){
                        mMap.addMarker(new MarkerOptions().position(new LatLng(loc.getLatitude(), loc.getLongitude())).title(places_list[i][1]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

                    }else {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(loc.getLatitude(), loc.getLongitude())).title(places_list[i][1]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

                    }


                }
            } catch (Exception e) {

            }
        }

    }

    class doTask extends AsyncTask<String, String, String[][]> {



        protected String[][] doInBackground(String... args) {

            String [][] places_list_2;


            JSONArray json_array = DatabaseInterface.searchPlace("Mensa","1","","","","","","");


            if (json_array != null) {

                places_list_2 = new String[json_array.length()][5];

                Log.e("test", "json array filled: "+ json_array.length() + ";" );

                for (int i = 0; i < json_array.length(); i++) {
                    try {
                        places_list_2[i][0] = json_array.getJSONObject(i).getString("place_ID");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        places_list_2[i][1] = json_array.getJSONObject(i).getString("name");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        places_list_2[i][2] = json_array.getJSONObject(i).getString("sector_ID");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        places_list_2[i][3] = json_array.getJSONObject(i).getString("address");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        places_list_2[i][4] = json_array.getJSONObject(i).getString("zipcode");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        places_list_2[i][5] = json_array.getJSONObject(i).getString("town");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                //Log.e("test", places_list_2[0][0].toString());
                //Log.e("test", places_list_2[0][2].toString());

                return places_list_2;
            }

            return new String[0][0];

        }

    }


}

