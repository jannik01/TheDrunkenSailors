package at.tugraz.thedrunksailor;


import android.content.Intent;

import android.os.AsyncTask;
import android.support.annotation.BoolRes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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

    public void goToHome(View view)
    {
        Intent intent = new Intent(ActivityAddPlace.this, MainActivity.class);
        startActivity(intent);
    }
}
class doTask extends AsyncTask<String, String, Boolean> {


    protected Boolean doInBackground(String... args) {
        boolean success = DatabaseInterface.createPlace(args[0], args[1], Integer.parseInt(args[2]), args[3], args[4], args[5],args[6]);

        return success;
    }
}
