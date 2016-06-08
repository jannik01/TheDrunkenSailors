package at.tugraz.thedrunksailor;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Sami on 01/06/2016.
 */
class getLast extends AsyncTask<String, String, String[][]> {
    protected String[][] doInBackground(String... args) {
        Integer pers_int = Integer.parseInt(args[0]);
        JSONArray places = DatabaseInterface.startPagePlaces(pers_int);
        Integer places_length;
        places_length = places.length();
        if (places_length==0)
                return null;
        if (places_length>5)
            places_length=5;
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
                places_list[i][3] = Globals.ratings.getJSONObject(i).getString("rating");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return places_list;
    }
}