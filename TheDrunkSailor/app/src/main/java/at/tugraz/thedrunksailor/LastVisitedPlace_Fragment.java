package at.tugraz.thedrunksailor;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.concurrent.ExecutionException;

public class LastVisitedPlace_Fragment extends Fragment {
    View rootview;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_last_places_visited, container, false);

        final ListView listview = (ListView) rootview.findViewById(R.id.listview);
        final String [][] places_list=createDummyList();
        if (listview != null) {
            listview.setAdapter(new PlaceItemAdapter(getActivity(), createDummyList()));
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Globals.pid=Integer.parseInt(places_list[position][0]);
                    Intent intent = new Intent(getActivity(), PlaceDetailActivity.class);
                    startActivity(intent);
                }
            });
        }
        DatabaseInterface database_interface_object = new DatabaseInterface();
        return rootview;
    }


    public String[][] createDummyList() {
        String[][] places_list = new String[0][];
        String user_id = String.valueOf(Globals.uid);
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

    class doTask extends AsyncTask<String, String, String[][]> {

        protected String[][] doInBackground(String... args) {
            Integer uid_int = Integer.parseInt(args[0]);
            JSONArray places = DatabaseInterface.startPagePlaces(uid_int);
            if (Globals.new_user==true) {
                String[][] no_places = {
                        {"1", "No Place visited yet", "-", "-"},
                };
                return no_places;
            } else {
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
                        places_list[i][2] = places.getJSONObject(i).getString("current_use");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        places_list[i][3] = places.getJSONObject(i).getString("rating");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return places_list;
            }
        }
    }
}












