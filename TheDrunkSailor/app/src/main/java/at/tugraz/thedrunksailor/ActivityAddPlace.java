package at.tugraz.thedrunksailor;


import android.content.Intent;

import android.os.AsyncTask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import java.util.concurrent.ExecutionException;

public class ActivityAddPlace extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);
        String Name = "";
        String Description = "";
        String Sector_ID = "";
        String Address = "";
        String Country = "";
        String Zipcode = "";
        String Town = "";
        String[] sector_list={""};
        try {
            sector_list = new getSector().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        spinner = (Spinner) findViewById(R.id.static_spinner);
        ArrayAdapter adapter = new ArrayAdapter(this,  android.R.layout.simple_spinner_item,sector_list);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    public void goToSearchPlace(View view) {
        Intent intent = new Intent(ActivityAddPlace.this, SearchPlaceActivity.class);
        startActivity(intent);
    }

    public void goToSearchPerson(View view) {
        //Intent intent = new Intent(NavigationBarActivity.this, .class);
        //startActivity(intent);
    }

    public void goToHome(View view) {
        Intent intent = new Intent(ActivityAddPlace.this, MainActivity.class);
        startActivity(intent);
    }

    public void buttonOnClick(View v) {

        EditText Name = (EditText) findViewById(R.id.txtName);
        EditText Address = (EditText) findViewById(R.id.txtAdress);
        EditText Town = (EditText) findViewById(R.id.txtTown);
        EditText Zip = (EditText) findViewById(R.id.txtZip);
        EditText Country = (EditText) findViewById(R.id.txtCountry);
        EditText Description = (EditText) findViewById(R.id.txtDescription);
        Integer sectorID= (int) (long)spinner.getSelectedItemId() +1;
        if (!Name.getText().toString().isEmpty() && !Address.getText().toString().isEmpty() && !Zip.getText().toString().isEmpty() && !sectorID.toString().isEmpty() && Zip.length() >= 3 && Zip.length() <= 5 && Integer.parseInt(sectorID.toString()) >= 0) {

            String[] params = new String[]{Name.getText().toString(), Description.getText().toString(), sectorID.toString(), Address.getText().toString(), Country.getText().toString(), Zip.getText().toString(), Town.getText().toString()};

            try {
                if (new doTask().execute(params).get()) {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } else {
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
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        TextView myText = (TextView) view;
        Toast.makeText(this, "You selected " + myText.getText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    class getSector extends AsyncTask<String, String, String[]> {
        protected String[] doInBackground(String... args) {
            JSONArray sectors = DatabaseInterface.getSectors();
            Integer places_length = sectors.length();
            String[] sectors_list = new String[places_length];
            for (Integer i = 0; sectors.length() > i; i++) {
                try {
                    sectors_list[i] = sectors.getJSONObject(i).getString("name");
                } catch (JSONException e) {
                    e.printStackTrace();


                }

            }
                return sectors_list;

            }

        }
    }


    class doTask extends AsyncTask<String, String, Boolean> {
        protected Boolean doInBackground(String... args) {
            boolean success = DatabaseInterface.createPlace(args[0], args[1], Integer.parseInt(args[2]), args[3], args[4], args[5], args[6]);
            return success;
        }

    }


