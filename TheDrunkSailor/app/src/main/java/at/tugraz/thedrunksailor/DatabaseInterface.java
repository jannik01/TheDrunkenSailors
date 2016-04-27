package at.tugraz.thedrunksailor;

import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.jar.Attributes;


/**
 * Created by robert on 20.04.2016.
 */
public class DatabaseInterface {

    public DatabaseInterface() {
    }

    private static final String TAG_SUCCESS = "success";
    private static String url_create_user = "http://drunkensailors.robert-thomann.at/create_user.php";
    private static String url_login_user = "http://drunkensailors.robert-thomann.at/login_user.php";
    private static String url_create_place = "http://drunkensailors.robert-thomann.at/create_place.php";
    private static String url_search_place = "http://drunkensailors.robert-thomann.at/search_place.php";
    private static String url_get_sector = "http://drunkensailors.robert-thomann.at/get_sectors.php";
    private static String url_start_page_places = "http://drunkensailors.robert-thomann.at/start_page_places.php";



    public static boolean login(String user_name, String password) {
        JSONParser jsonParser = new JSONParser();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user_name", user_name));
        params.add(new BasicNameValuePair("password", password));
        JSONObject json = jsonParser.makeHttpRequest(url_login_user, "POST", params);

        try {
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                return (true);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean createUser(String user_name, String password, String name, String sex, Integer age, String job) {

        JSONParser jsonParser = new JSONParser();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user_name", user_name));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("sex", sex));
        params.add(new BasicNameValuePair("age", age.toString()));
        params.add(new BasicNameValuePair("job", job));
        JSONObject json = jsonParser.makeHttpRequest(url_create_user,
                "POST", params);

        //Log.d("Create Response", json.toString());
        try {
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                return (true);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;

    }

    public static boolean createPlace(String place_name, String description, Integer sector_id, String address, String country, String zipcode) {

        JSONParser jsonParser = new JSONParser();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("place_name", place_name));
        params.add(new BasicNameValuePair("description", description));
        params.add(new BasicNameValuePair("sector_ID", sector_id.toString()));
        params.add(new BasicNameValuePair("address", address));
        params.add(new BasicNameValuePair("country", country));
        params.add(new BasicNameValuePair("zipcode", zipcode));
        JSONObject json = jsonParser.makeHttpRequest(url_create_place,
                "POST", params);

        //Log.d("Create Response", json.toString());
        try {
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                return (true);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;

    }

    public static boolean deleteUser(String user_name, String password) {
        JSONParser jsonParser = new JSONParser();
        String url_delete_user = "http://drunkensailors.robert-thomann.at/delete_user.php";
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user_name", user_name));
        params.add(new BasicNameValuePair("password", password));
        JSONObject json = jsonParser.makeHttpRequest(url_delete_user, "POST", params);
        String success = json.toString();
        if (success == "1") {
            return true;

        } else {
            return false;
        }
    }


    public static JSONArray searchPlace(String place_name, Integer sector_ID, String address, String country, String zipcode, Double min_use, Double max_use, Double min_rating, Double max_rating) {
        JSONParser jsonParser = new JSONParser();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        if (min_use==0)
        params.add(new BasicNameValuePair("place_name", place_name));
        params.add(new BasicNameValuePair("sector_ID", sector_ID.toString()));
        params.add(new BasicNameValuePair("address", address));
        params.add(new BasicNameValuePair("country", country));
        params.add(new BasicNameValuePair("zipcode", zipcode));
        if (min_use==0)
            params.add(new BasicNameValuePair("min_use", ""));
        else
            params.add(new BasicNameValuePair("min_use", min_use.toString()));
        if (max_use==0)
            params.add(new BasicNameValuePair("max_use", ""));
        else
            params.add(new BasicNameValuePair("max_use", max_use.toString()));
        if (min_rating==0)
            params.add(new BasicNameValuePair("min_rating", ""));
        else
            params.add(new BasicNameValuePair("min_rating", min_rating.toString()));
        if (max_rating ==0)
            params.add(new BasicNameValuePair("max_rating", ""));
        else
            params.add(new BasicNameValuePair("max_rating", max_rating.toString()));
        JSONObject json = jsonParser.makeHttpRequest(url_search_place, "POST", params);

        try {
            int success = json.getInt(TAG_SUCCESS);
            JSONArray places=json.getJSONArray("place_string");



        if (success == 1) {
                return places;

            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

        }
    public static JSONArray startPagePlaces(Integer user_id) {

        JSONParser jsonParser = new JSONParser();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user_ID", user_id.toString()));
        JSONObject json = jsonParser.makeHttpRequest(url_start_page_places,
                "POST", params);

        //Log.d("Create Response", json.toString());
        try {
            int success = json.getInt(TAG_SUCCESS);
            JSONArray places=json.getJSONArray("last_places");

            if (success == 1) {
                return places;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }
    public static JSONArray getSectors() {

        JSONParser jsonParser = new JSONParser();
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        JSONObject json = jsonParser.makeHttpRequest(url_get_sector,
                "POST", params);

        //Log.d("Create Response", json.toString());
        try {
            int success = json.getInt(TAG_SUCCESS);
            JSONArray sectors=json.getJSONArray("sector_name");

            if (success == 1) {
                return (sectors);
            }
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }
    }
