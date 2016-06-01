package at.tugraz.thedrunksailor;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SearchDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_search);
        final ListView listview = (ListView) findViewById(R.id.listview);

        final String [][] places_list=createDummyList();
        if (listview != null) {
            listview.setAdapter(new PlaceItemAdapter(this, places_list));

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    LastVisitedPlace_Fragment.pid=Integer.parseInt(places_list[position][0]);



                    Intent intent = new Intent(SearchDisplayActivity.this, PlaceDetailActivity.class);
                    startActivity(intent);

                }
            });
        }


        DatabaseInterface database_interface_object = new DatabaseInterface();


    }


//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.logout:
//                Intent intent = new Intent(SearchDisplayActivity.this, SignInActivity.class);
//                startActivity(intent);
//                return true;
//
//            default:
//                return false;
//
//        }
//    }



    public String[][] createDummyList() {

        JSONArray places = LastVisitedPlace_Fragment.place_list;
        Integer places_length = places.length();
        String[][] places_list = new String[places_length][4];
        for (Integer i = 0; places.length() > i; i++) {
            try {
                places_list[i][0] = places.getJSONObject(i).getString("place_ID");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                places_list[i][1] = places.getJSONObject(i).getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {

                if(places.getJSONObject(i).getString("current_use").equals("0.0000"))
                    places_list[i][2] = "-";
                else
                    places_list[i][2] = places.getJSONObject(i).getString("current_use");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if(places.getJSONObject(i).getString("result").equals("0.0000"))
                    places_list[i][3] = "-";
                else
                    places_list[i][3] = places.getJSONObject(i).getString("result");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return places_list;
    }







}

