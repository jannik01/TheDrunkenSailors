package at.tugraz.thedrunksailor;


import android.app.Fragment;
import android.content.Intent;

import android.os.AsyncTask;
import android.support.annotation.BoolRes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import java.util.concurrent.ExecutionException;

public class AddPlace_Fragment extends Fragment {
    View rootview;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.activity_add_place, container, false);
        String Name="";
        String Description="";
        String Sector_ID="";
        String Address="";
        String Country="";
        String Zipcode="";
        String Town="";
        return rootview;
    }




    public void buttonOnClick(View v) {

        EditText Name = (EditText) rootview.findViewById(R.id.txtName);
        EditText Address = (EditText) rootview.findViewById(R.id.txtAdress);
        EditText Town = (EditText) rootview.findViewById(R.id.txtTown);
        EditText Sector = (EditText) rootview.findViewById(R.id.txtSector);
        EditText Zip = (EditText) rootview.findViewById(R.id.txtZip);
        EditText Country = (EditText) rootview.findViewById(R.id.txtCountry);
        EditText Description = (EditText) rootview.findViewById(R.id.txtDescription);

        if (!Name.getText().toString().isEmpty() && !Address.getText().toString().isEmpty() && !Zip.getText().toString().isEmpty() && !Sector.getText().toString().isEmpty() && Zip.length() >= 3 && Zip.length() <= 5 && Integer.parseInt(Sector.getText().toString()) >= 0) {

            String[] params = new String[]{Name.getText().toString(), Description.getText().toString(), Sector.getText().toString(), Address.getText().toString(), Country.getText().toString(), Zip.getText().toString(), Town.getText().toString()};

            try {
                if (new doTask().execute(params).get()) {
                    /*Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);*/
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            new AlertDialog.Builder(getActivity())
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
}


