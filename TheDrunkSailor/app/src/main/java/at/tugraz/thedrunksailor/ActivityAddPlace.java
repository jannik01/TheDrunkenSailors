package at.tugraz.thedrunksailor;

<<<<<<< HEAD

import android.content.Intent;

import android.os.AsyncTask;
import android.support.annotation.BoolRes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
=======
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
>>>>>>> fb_addPlace_logic

import java.util.concurrent.ExecutionException;

public class ActivityAddPlace extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
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
=======
        setContentView(R.layout.activity_add_place);
    }

    public void buttonOnClick(View v){

        EditText Name = (EditText)findViewById(R.id.txtName);
        EditText Address = (EditText)findViewById(R.id.txtAdress);
        EditText Town = (EditText)findViewById(R.id.txtTown);
        EditText Sector = (EditText)findViewById(R.id.txtSector);
        EditText Zip = (EditText)findViewById(R.id.txtZip);
        EditText Country = (EditText)findViewById(R.id.txtCountry);
        EditText Description = (EditText)findViewById(R.id.txtDescription);

        if(!Name.getText().toString().isEmpty() && !Address.getText().toString().isEmpty() && !Zip.getText().toString().isEmpty() && !Sector.getText().toString().isEmpty() && Zip.length() >= 3 && Zip.length() <= 5 && Integer.parseInt(Sector.getText().toString()) >= 0) {

            String[] params = new String []{Name.getText().toString(), Description.getText().toString(), Sector.getText().toString(), Address.getText().toString(), Country.getText().toString(), Zip.getText().toString(), Town.getText().toString()};

            try {
                if(new doTask().execute(params).get()){
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        else
        {
            new AlertDialog.Builder(this)
                    .setTitle("wrong input")
                    .setMessage("Please fill all the required fields")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
>>>>>>> fb_addPlace_logic
    }
}

class doTask extends AsyncTask<String, String, Boolean> {

    protected Boolean doInBackground(String... args) {
        boolean success = DatabaseInterface.createPlace(args[0], args[1], Integer.parseInt(args[2]), args[3], args[4], args[5],args[6]);

        return success;
    }
}