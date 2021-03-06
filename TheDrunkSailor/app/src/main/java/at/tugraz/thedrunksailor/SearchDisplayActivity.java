package at.tugraz.thedrunksailor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

public class SearchDisplayActivity extends AppCompatActivity {
    CheckConnection stateOfConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_display_search);
        final ListView listview = (ListView) findViewById(R.id.listview);
        stateOfConnection = new CheckConnection(this);
        final String [][] places_list= getResultSearch();
        if (listview != null) {
            listview.setAdapter(new PlaceItemAdapter(this, places_list));
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(SearchDisplayActivity.this);
                    if(!stateOfConnection.isOnline(alert))
                        return;
                    Globals.pid=Integer.parseInt(places_list[position][0]);
                    Intent intent = new Intent(SearchDisplayActivity.this, PlaceDetailActivity.class);
                    startActivity(intent);
                }
            });
        }
        DatabaseInterface database_interface_object = new DatabaseInterface();
    }

    public String[][] getResultSearch() {

        JSONArray places = Globals.place_list;
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

