package at.tugraz.thedrunksailor;


import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchPlace_Fragment extends Fragment implements AdapterView.OnItemSelectedListener{

    EditText mPLZ;
    Spinner spinner;

    View rootview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.search_place, container, false);
        DatabaseInterface database_interface_object = new DatabaseInterface();

        spinner = (Spinner) rootview.findViewById(R.id.static_spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),R.array.Sectors,android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        return rootview;
    }



//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }

    public void searchPlaceLogic(View view)
    {
        AlertDialog.Builder alerter = new AlertDialog.Builder(getActivity());
        mPLZ = (EditText)rootview.findViewById(R.id.plz);

        alerter.setIcon(android.R.drawable.ic_dialog_alert);

        String plz = mPLZ.getText().toString();

        if (plz.length() < 3 || plz.length() > 5)
        {
            alerter.setMessage("Please put in a zip code between 3 - 5 digits");
            alerter.setTitle("Fail");
            alerter.show();
        }

    }


    // ---------------------------------------------------------------------------------------------
    // Used for Dropdown menu -- Sector
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        TextView myText = (TextView) view;
        Toast.makeText(getActivity(),"You selected "+myText.getText(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    // ---------------------------------------------------------------------------------------------

//    class doTask extends AsyncTask<String, String, Boolean> {
//        protected Boolean doInBackground(String... args) {
//            DatabaseInterface.getSectors();
//
//            return success;
//        }
//    }
}
