package at.tugraz.thedrunksailor;

/**
 * Created by Strw on 11.05.16.
 */

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
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

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class Map_Fragment extends Fragment implements
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMyLocationButtonClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private HashMap<Marker, Integer> mHashMap = new HashMap<Marker, Integer>();

    private Context context;

    LocationManager locationmanager;
    Location location;

    String [][] places_list;

    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;


    private GoogleMap mMap;

    private Location lastlocation;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         View rootview = inflater.inflate(R.layout.map_layout, container, false);

        mLocationRequest = LocationRequest.create();
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity()).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();


        context = getContext();

        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null) {
            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            fragmentTransaction.replace(R.id.map, mapFragment).commit();
        }

        if (mapFragment != null)
        {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                                        @Override
                                        public void onMapReady(GoogleMap googleMap) {
                                            if (googleMap != null) {

                                                googleMap.getUiSettings().setAllGesturesEnabled(true);


                                            }

                                        }
                                    });
        }
        mapFragment.getMapAsync(this);

        mGoogleApiClient.connect();
        return rootview;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        mMap.setOnMyLocationButtonClickListener(this);
        enableMyLocation();

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(getActivity(), PlaceDetailActivity.class);
                Globals.pid = mHashMap.get(marker);
                startActivity(intent);


            }
        });

    }


    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            CharSequence text = "Missing Permission!";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_PERMISSION_REQUEST_CODE);

            // Permission to access the location is missing.

        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);

            locationmanager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            location = locationmanager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        }


    }



    @Override
    public boolean onMyLocationButtonClick() {
        CameraPosition pos = mMap.getCameraPosition();
        updateMarker();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).

        return false;
    }






    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();

    }

    @Override
    public void onStop() {
        super.onStop();
        if(mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }


    @Override
    public void onConnected(Bundle bundle) {
        updateMarker();
    }

    public void updateMarker(){
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            CharSequence text = "Missing Permission!";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);


        }else {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

            location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if(location != null) {

                LatLng position = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 15));

                Geocoder mGeocoder = new Geocoder(getActivity());
                List<Address> address;


                try {
                    address = mGeocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    Address addr = address.get(0);

                    String zip_code = addr.getPostalCode();

                    String[] params=new String[]{zip_code};
                    places_list = new doTask().execute(params).get();


                    fillMap(places_list);



                }catch(Exception e){}

            } else {

                Log.e("test", "No last location");
            }

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

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public void fillMap(String[][] places_list){

        Log.e("fillMap", "Function Called");

        Geocoder mGeocoder = new Geocoder(getActivity());

        List<Address> address;
        String[][]  markers = new String[places_list.length][5];
        int markers_fill_counter = 0;

        // clear map
        //mMap.clear();



        for(int i = 0; i < places_list.length; i++) {

            try {
                address = mGeocoder.getFromLocationName(places_list[i][3] + " " + places_list[i][4] + " " + places_list[i][5], 1);


                if (address != null) {
                    Address adr = address.get(0);
                    if (adr.getLatitude() != 0 && adr.getLongitude() != 0 ) {
                        markers[markers_fill_counter][0] = places_list[i][1];                          // Location Name
                        markers[markers_fill_counter][1] = String.valueOf(adr.getLatitude());         // Longitude
                        markers[markers_fill_counter][2] = String.valueOf(adr.getLongitude());
                        markers[markers_fill_counter][3] = places_list[i][2];                          // Type
                        markers[markers_fill_counter][4] = places_list[i][0];                           // ID
                        markers_fill_counter++;
                    }

                }

                Log.e("Addresses", "Test");
            } catch (Exception e) {
                Log.e("Addresses", "Failed to get Addresses");
                e.printStackTrace();


            }
        }



            for(int marker_counter = 0; marker_counter < markers_fill_counter; marker_counter++) {

                for(int compare_with_counter = 0; compare_with_counter < markers_fill_counter; compare_with_counter++) {
                    // colors for types
                    if( (marker_counter != compare_with_counter) && markers[marker_counter][1].equals(markers[compare_with_counter][1]) && markers[marker_counter][2].equals(markers[compare_with_counter][2]) ) {


                        Log.e("Markers Equal", "Markers on Equal position");
                        double random = new Random().nextDouble();
                        double result_long = 0.00002 + (random * ((0.0001 * (double) 2) - 0.0001));
                        double result_lat = 0.00002 + (random * ((0.0001 * (double) 1) - 0.0001));
                        int negative_lat = new Random().nextInt(2);
                        int negative_long = new Random().nextInt(2);

                        if (negative_lat == 1) {
                            result_lat *= -1.0;
                        }
                        if (negative_long == 1) {
                            result_long *= -1.0;
                        }


                        markers[compare_with_counter][1] = String.valueOf((Double.parseDouble(markers[marker_counter][1]) + result_lat));
                        markers[compare_with_counter][2] = String.valueOf((Double.parseDouble(markers[marker_counter][2]) + result_long));
                    }




                }


            }

        addMarkers(markers);




    }


    public void addMarkers(String[][] places_list ){

        Log.e("addMarkers", "Called");
        Marker marker;
        for(int i = 0; i < places_list.length; i++) {

            LatLng position = new LatLng(Double.parseDouble(places_list[i][1]), Double.parseDouble(places_list[i][2]) );


            if (places_list[i][3].equals("1")) {
                marker = mMap.addMarker(new MarkerOptions().position(position).title(places_list[i][0]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

            } else if (places_list[i][3].equals("2")) {
                marker = mMap.addMarker(new MarkerOptions().position(position).title(places_list[i][0]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));

            } else if (places_list[i][3].equals("3")) {
                marker = mMap.addMarker(new MarkerOptions().position(position).title(places_list[i][0]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

            } else if (places_list[i][3].equals("4")) {
                 marker = mMap.addMarker(new MarkerOptions().position(position).title(places_list[i][0]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

            } else if (places_list[i][3].equals("5")) {
                 marker = mMap.addMarker(new MarkerOptions().position(position).title(places_list[i][0]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

            } else if (places_list[i][3].equals("6")) {
                 marker = mMap.addMarker(new MarkerOptions().position(position).title(places_list[i][0]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));

            } else if (places_list[i][3].equals("7")) {
                 marker = mMap.addMarker(new MarkerOptions().position(position).title(places_list[i][0]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

            } else {
                 marker = mMap.addMarker(new MarkerOptions().position(position).title(places_list[i][0]).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

            }

            mHashMap.put(marker, Integer.valueOf(places_list[i][4]) );
        }


    }

    class doTask extends AsyncTask<String, String, String[][]> {



        protected String[][] doInBackground(String... args) {

            String [][] places_list_2;


            JSONArray json_array = DatabaseInterface.getPlacesByPostal(args[0]);


            if (json_array != null) {

                places_list_2 = new String[json_array.length()][6];

                for (int i = 0; i < json_array.length(); i++) {
                    try {
                        places_list_2[i][0] = json_array.getJSONObject(i).getString("place_ID");
                        places_list_2[i][1] = json_array.getJSONObject(i).getString("name");
                        places_list_2[i][2] = json_array.getJSONObject(i).getString("sector_ID");
                        places_list_2[i][3] = json_array.getJSONObject(i).getString("address");
                        places_list_2[i][4] = json_array.getJSONObject(i).getString("zipcode");
                        places_list_2[i][5] = json_array.getJSONObject(i).getString("town");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                return places_list_2;
            }

            return new String[0][0];

        }

    }


}

