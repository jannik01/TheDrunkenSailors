package at.tugraz.thedrunksailor;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView listview = (ListView) findViewById(R.id.listview);

        if (listview != null) {
            listview.setAdapter(new PlaceItemAdapter(this, createDummyList()));

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    view.getTag();

                }
            });

        }


        DatabaseInterface database_interface_object = new DatabaseInterface();


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);
                return true;

            default:
                return false;

        }
    }


    public String[][] createDummyList() {

        String[][] places_list = new String[0][];
        String user_id="17";
        try {
            places_list = new doTask().execute(user_id).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        String[][] place = {
                {"1", "Place1", "3", "5"},
                {"2", "Place2", "3", "3"},
                {"3", "Place3", "5", "2.3"},
                {"4", "Place4", "1", "2.7"},
                {"5", "Place5", "3", "4"},
                {"6", "Place6", "2", "1.2"},
                {"7", "Place7", "3", "3"},
                {"15", "Place8", "5", "2.3"},
                {"9", "Place9", "1", "2.7"},
                {"10", "Place10", "3", "4"},
                {"11", "Place11", "2", "1.2"},
        };

        return places_list;

    }
    public void goToAddPlace(View view)
    {
        Intent intent = new Intent(MainActivity.this, ActivityAddPlace.class);
        startActivity(intent);
    }

    public void goToSearchPlace(View view)
    {
        //Intent intent = new Intent(NavigationBarActivity.this, .class);
        //startActivity(intent);
    }

    public void goToSearchPerson(View view)
    {
        //Intent intent = new Intent(NavigationBarActivity.this, .class);
        //startActivity(intent);
    }

    public void makeDummyEntries() {
        Place tmp = new Place("Place 1", 3.4, 7);
        List<Place> returnPlaces = new ArrayList<Place>();
        returnPlaces.add(tmp);
        tmp = new Place("Place 2", 6.5, 2.2);
        returnPlaces.add(tmp);
        tmp = new Place("Place 3", 6.1, 2.2);
        returnPlaces.add(tmp);
        JSONArray places = DatabaseInterface.startPagePlaces(16);
        TableLayout tl = (TableLayout) findViewById(R.id.list_places_table);
        for (int i = 0; i < places.length(); i++) {
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            for (int j = 0; j < 4; j++) {

                TextView tv = new TextView(this);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                //tv.setBackgroundResource(R.drawable.cell_shape);
                tv.setPadding(5, 5, 5, 5);
                String tmp_string = new String();
                switch (j) {
                    case 0:
                        break;
                    case 1:
                        try {
                            tmp_string = places.getJSONObject(i).getString("name");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        try {
                            tmp_string = places.getJSONObject(i).getString("current_use");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3:
                        try {
                            tmp_string = places.getJSONObject(i).getString("rating");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                tv.setText(tmp_string);

                row.addView(tv);

            }
            tl.addView(row, i);
        }
    }
    class doTask extends AsyncTask<String, String, String[][]> {





        protected String[][] doInBackground(String... args) {
            Integer uid_int=Integer.parseInt(args[0]);
            System.out.print(uid_int);
            JSONArray places = DatabaseInterface.startPagePlaces(uid_int);
            Integer places_length=places.length();
            String [][] places_list= new String[places_length][4];
            for (Integer i=0;places.length()>i;i++)
            {
                try {
                    places_list[i][0]=places.getJSONObject(i).getString("place_ID");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    places_list[i][1]=places.getJSONObject(i).getString("name");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    places_list[i][2]=places.getJSONObject(i).getString("current_use");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    places_list[i][3]=places.getJSONObject(i).getString("rating");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return places_list;
        }




    }
}

