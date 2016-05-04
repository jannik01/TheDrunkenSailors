package at.tugraz.thedrunksailor;

import android.os.AsyncTask;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.concurrent.ExecutionException;

public class ActivityAddPlace extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_add_place);
        String Name="";
        String Description="";
        String Sector_ID="";
        String Address="";
        String Country="";
        String Zipcode="";
        String Town="";
        try {
            new doTask().execute(Name, Description, Sector_ID, Address, Country, Zipcode, Town).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
class doTask extends AsyncTask<String, String, Boolean> {


    protected Boolean doInBackground(String... args) {
        boolean success = DatabaseInterface.createPlace(args[0], args[1], Integer.parseInt(args[2]), args[3], args[4], args[5],args[6]);

        return success;
    }
}
