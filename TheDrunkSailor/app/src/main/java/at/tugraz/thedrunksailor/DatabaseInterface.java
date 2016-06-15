package at.tugraz.thedrunksailor;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;


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
    private static String url_get_place_data = "http://drunkensailors.robert-thomann.at/get_place.php";
    private static String url_search_person = "http://drunkensailors.robert-thomann.at/search_person.php";
    private static String url_search_persons_id_is_following = "http://drunkensailors.robert-thomann.at/url_search_persons_id_is_following.php";
    private static String url_get_person_data = "http://drunkensailors.robert-thomann.at/get_person.php";

    private static String url_follow_person = "http://drunkensailors.robert-thomann.at/follow_person.php";
    private static String url_do_i_follow_person = "http://drunkensailors.robert-thomann.at/do_i_follow_person.php";
    private static String url_unfollow_person = "http://drunkensailors.robert-thomann.at/unfollow_person.php";

    private static String url_get_place_list = "http://drunkensailors.robert-thomann.at/search_place_for_maps.php";
    private static String url_search_friends_at_place = "http://drunkensailors.robert-thomann.at/search_friends_at_place.php";



    public static Integer login(String user_name, String password) {
        JSONParser jsonParser = new JSONParser();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user_name", user_name));
        params.add(new BasicNameValuePair("password", password));
        JSONObject json = jsonParser.makeHttpRequest(url_login_user, "POST", params);
        Integer success = 0;
        Integer userid = 0;

        try {
            success = json.getInt("success");
        } catch (Exception e) {
            Log.e("[ERROR]", "can't get string from json.");
        }

        if (success == 1) {
            Log.e("[DEBUG]", "DB-Interface success = " + success.toString());
            try {
                String test = json.getString("uid");
                userid = Integer.parseInt(test);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.e("[DEBUG]", "DB-Interface success = " + success.toString() + " - considered as false");

        Globals.uid = userid;
        return (userid);
    }

    public static boolean createUser(String user_name, String password, String name, String sex, Integer age, String job) throws JSONException {
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

        boolean success = false;

        if (json != null) {
            try {
                Globals.message=json.getString("message");
                success = (1 == json.getInt(TAG_SUCCESS));

            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
        return (success);
    }

    public static boolean createPlace(String place_name, String description, Integer sector_id, String address, String country, String zipcode, String town) throws JSONException {
        JSONParser jsonParser = new JSONParser();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("place_name", place_name));
        params.add(new BasicNameValuePair("description", description));
        params.add(new BasicNameValuePair("sector_ID", sector_id.toString()));
        params.add(new BasicNameValuePair("address", address));
        params.add(new BasicNameValuePair("country", country));
        params.add(new BasicNameValuePair("zipcode", zipcode));
        params.add(new BasicNameValuePair("town", town));

        boolean success = false;
        JSONObject json = jsonParser.makeHttpRequest(url_create_place,
                "POST", params);

        if(json != null) {

            //Log.d("Create Response", json.toString());
            try {
                Globals.message=json.getString("message");
                if (1 == json.getInt(TAG_SUCCESS)){
                    success = true;
                }
            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
        return(success);
    }

    public static boolean deleteUser(String user_name, String password) {
        JSONParser jsonParser = new JSONParser();
        String url_delete_user = "http://drunkensailors.robert-thomann.at/delete_user.php";
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user_name", user_name));
        params.add(new BasicNameValuePair("password", password));
        JSONObject json = jsonParser.makeHttpRequest(url_delete_user, "POST", params);
        Integer success = 0;
        try {
            success = json.getInt("success");
        } catch (Exception e) {
            Log.e("[ERROR]", "can't get string from json.");
        }
        if (success == 1) {
            Log.e("[DEBUG]", "DB-Interface success = " + success.toString());
            return (true);
        }
        return (false);
    }

    public static JSONArray searchPlace(String place_name, String sector_ID, String zipcode,String town, String min_use, String max_use, String min_rating, String max_rating) {
        JSONParser jsonParser = new JSONParser();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("place_name", place_name));
        params.add(new BasicNameValuePair("sector_ID", sector_ID));
        params.add(new BasicNameValuePair("zipcode", zipcode));
        params.add(new BasicNameValuePair("town", town));
        if (min_use == "0.0")
            params.add(new BasicNameValuePair("min_use", ""));
        else
            params.add(new BasicNameValuePair("min_use", min_use.toString()));
        if (max_use == "0.0")
            params.add(new BasicNameValuePair("max_use", ""));
        else
            params.add(new BasicNameValuePair("max_use", max_use.toString()));
        if (min_rating == "0.0")
            params.add(new BasicNameValuePair("min_rating", ""));
        else
            params.add(new BasicNameValuePair("min_rating", min_rating.toString()));
        if (max_rating == "0.0")
            params.add(new BasicNameValuePair("max_rating", ""));
        else
            params.add(new BasicNameValuePair("max_rating", max_rating.toString()));
        JSONArray places = new JSONArray();
        JSONArray true_use = new JSONArray();
        JSONObject json = jsonParser.makeHttpRequest(url_search_place, "POST", params);
        if(json != null) {
            try {
                int success = json.getInt(TAG_SUCCESS);
                if(success == 1) {
                    places = json.getJSONArray("place_string");
                    true_use=json.getJSONArray("current_use");
                    int places_length=places.length();
                    int curr_length=true_use.length();

                    for (int i=0;places_length>i;i++)
                    {
                        places.getJSONObject(i).put("current_use","0.0000");
                        for (int j=0;curr_length>j;j++)
                        {
                            int place_int= places.getJSONObject(i).getInt("place_ID");
                            int use_int=true_use.getJSONObject(j).getInt("place_ID");



                            if(place_int==use_int)
                            {
                                String use=true_use.getJSONObject(j).getString("current_use");
                                places.getJSONObject(i).put("current_use",use);
                            }
                        }

                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return(places);
    }

    public static JSONArray startPagePlaces(Integer user_id) {
        JSONParser jsonParser = new JSONParser();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user_ID", user_id.toString()));
//        params.add(new BasicNameValuePair("user_ID", Integer.toString(LastVisitedPlace_Fragment.uid)));
        JSONObject json = jsonParser.makeHttpRequest(url_start_page_places,
                "POST", params);
        //Log.d("Create Response", json.toString());
        JSONArray places = new JSONArray();
        try {
            int success2 = json.getInt(TAG_SUCCESS);
            int success = (json!=null ? json.getInt(TAG_SUCCESS) : 0);
            if (success == 1) {
                places = json.getJSONArray("last_places");
                Globals.ratings = json.getJSONArray("rating");
                Globals.new_user=false;
                return places;
            } else if (success2 == 2) {
                Globals.new_user=true;
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return(places);
    }

    public static JSONArray getSectors() {
        JSONParser jsonParser = new JSONParser();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        JSONArray sectors = new JSONArray();
        JSONObject json = jsonParser.makeHttpRequest(url_get_sector,
                "POST", params);

        //Log.d("Create Response", json.toString());
        if(json != null) {
            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    sectors = json.getJSONArray("sector_name");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return(sectors);
    }

    public static boolean ratePlace(String rating , String current_use) {
        JSONParser jsonParser = new JSONParser();
        String url_rate_place = "http://drunkensailors.robert-thomann.at/rate_place.php";
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user_id", Integer.toString(Globals.uid)));
        params.add(new BasicNameValuePair("place_id", Integer.toString(Globals.pid)));
        params.add(new BasicNameValuePair("rating", rating));
        params.add(new BasicNameValuePair("current_use", current_use));
        JSONObject json = jsonParser.makeHttpRequest(url_rate_place, "POST", params);
        Integer success = 0;
        try {
            success = json.getInt("success");
        } catch (Exception e) {
            Log.e("[ERROR]", "can't get string from json.");
        }
        if (success == 1) {
            Log.e("[DEBUG]", "DB-Interface success = " + success.toString());
            return (true);
        }
        return (false);
    }

    public static JSONArray getPlaceData(Integer pid) {
        JSONParser jsonParser = new JSONParser();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("place_id", Integer.toString(pid)));
        JSONArray place_data = new JSONArray();

        JSONObject result = jsonParser.makeHttpRequest(url_get_place_data,
                "POST", params);
        //Log.d("Create Response", result.toString());
        if (result != null) {
            try {
                int success = result.getInt(TAG_SUCCESS);
                if (success == 1) {
                    place_data = result.getJSONArray("place_data");
                    if(result.getJSONArray("c_use").getJSONObject(0).getString("current_use").equals("null"))
                    {
                        Globals.c_use=0.0;
                    }
                    else
                        Globals.c_use=result.getJSONArray("c_use").getJSONObject(0).getDouble("current_use");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        return (place_data);
    }

    public static JSONArray searchPerson(String name, String age, String sex, String job, String lastly) {
        JSONArray person_list = new JSONArray();
        JSONParser jsonParser = new JSONParser();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("age", age));
        params.add(new BasicNameValuePair("sex", sex));
        params.add(new BasicNameValuePair("job", job));
        params.add(new BasicNameValuePair("lastly_visited", lastly));
        JSONObject result = jsonParser.makeHttpRequest(url_search_person,
                "POST", params);
        if(result != null) {
            try {
                int success = result.getInt(TAG_SUCCESS);
                if (success == 1) {
                    person_list = result.getJSONArray("person_list");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return(person_list);
    }

    public static JSONArray searchPersonsIdIsFollowing(Integer pers_id) {
        JSONParser jsonParser = new JSONParser();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user_id", Integer.toString(pers_id)));
        JSONArray person_list = new JSONArray();
        JSONObject response = jsonParser.makeHttpRequest(url_search_persons_id_is_following,
                "POST", params);
        //Log.d("Create Response", json.toString());
        if (response != null) {
            try {
                int success = response.getInt(TAG_SUCCESS);
                if (success == 1) {

                    person_list = response.getJSONArray("person_list");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return (person_list);
    }
    public static JSONArray searchFriendsAtPlace(Integer user_id, Integer pid) {
        JSONParser jsonParser = new JSONParser();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user_id", Integer.toString(user_id)));
        params.add(new BasicNameValuePair("place_id", Integer.toString(pid)));

        JSONObject json = jsonParser.makeHttpRequest(url_search_friends_at_place,
                "POST", params);
        //Log.d("Create Response", json.toString());
        try {
            int success = json.getInt(TAG_SUCCESS);
            if (success == 1) {
                JSONArray person_list = json.getJSONArray("person_list");
                return (person_list);
            }
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONArray getPersonData(Integer pers_id) {
        JSONArray person_data = new JSONArray();
        JSONParser jsonParser = new JSONParser();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user_id", Integer.toString(pers_id)));
        JSONObject response = jsonParser.makeHttpRequest(url_get_person_data,
                "POST", params);
        //Log.d("Create Response", json.toString());
        if(response != null) {
            try {
                int success = response.getInt(TAG_SUCCESS);

                if (success == 1) {
                    person_data = response.getJSONArray("person_data");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return(person_data);
    }

    public static JSONArray getPlacesByPostal(String zip_code) {
        JSONParser jsonParser = new JSONParser();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        if(false) {
            params.add(new BasicNameValuePair("zipcode", zip_code));
        }
        JSONObject response = jsonParser.makeHttpRequest(url_get_place_list,"POST", params);
        JSONArray places = new JSONArray();
        if(response != null){
            try {
                int success = ( response.getInt(TAG_SUCCESS));
                if (success == 1) {
                    places = response.getJSONArray("place_string");
                }
                else {
                    Log.e("[ERROR]", "query not successful.");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return (places);
    }

    public static void logout() {
        Globals.uid=0;
    }


    public static Boolean followPerson(Integer pers_id) {

        JSONParser jsonParser = new JSONParser();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("leader_id", Integer.toString(pers_id)));
        params.add(new BasicNameValuePair("follower_id", Integer.toString(Globals.uid)));
        JSONObject json = jsonParser.makeHttpRequest(url_follow_person,
                "POST", params);

        try {
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                return true;
            }
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;

    }

    public static Boolean doIFollowPerson(Integer pers_id) {

        JSONParser jsonParser = new JSONParser();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("leader_id", Integer.toString(pers_id)));
        params.add(new BasicNameValuePair("follower_id", Integer.toString(Globals.uid)));
        JSONObject json = jsonParser.makeHttpRequest(url_do_i_follow_person,
                "POST", params);

        try {
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                return true;
            }
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;

    }

    public static Boolean unfollowPerson(Integer pers_id) {

        JSONParser jsonParser = new JSONParser();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("leader_id", Integer.toString(pers_id)));
        params.add(new BasicNameValuePair("follower_id", Integer.toString(Globals.uid)));
        JSONObject json = jsonParser.makeHttpRequest(url_unfollow_person,
                "POST", params);

        try {
            int success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                return true;
            }
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;

    }



}
