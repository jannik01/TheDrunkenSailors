package at.tugraz.thedrunksailor;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;


/**
 * Created by robert on 20.04.2016.
 */
public class DatabaseInterface {

    public DatabaseInterface(){}

    private static final String TAG_SUCCESS  = "success";

    public static boolean login(String user_name, String password) {
        JSONParser jsonParser=new JSONParser();
        String url_login_user="http://drunkensailors.robert-thomann.at/login_user.php";
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user_name",user_name));
        params.add(new BasicNameValuePair("password",password));
        JSONObject json=jsonParser.makeHttpRequest(url_login_user,"POST",params);
        String success=json.toString();

        if(success=="1"){
            return true;

        }
        return(false);
    }

    public static boolean createUser(String user_name, String password, String name, String sex, Integer age, String job ) {

        String url_create_user="http://drunkensailors.robert-thomann.at/create_user.php";
        JSONParser jsonParser=new JSONParser();
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
        int success = 0;
        try {
            success = json.getInt(TAG_SUCCESS);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(success == 1)
        {
            return(true);
        }
        return(false);
    }

    public static boolean deleteUser(String user_name, String password) {
        JSONParser jsonParser=new JSONParser();
        String url_delete_user="http://drunkensailors.robert-thomann.at/delete_user.php";
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("user_name",user_name));
        params.add(new BasicNameValuePair("password",password));
        JSONObject json=jsonParser.makeHttpRequest(url_delete_user,"POST",params);
        String success=json.toString();
        if(success=="1"){
            return true;

        }
        else{
            return false;
        }

    }
}
