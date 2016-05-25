package at.tugraz.thedrunksailor;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SearchPlace_Fragment extends Fragment implements AdapterView.OnItemSelectedListener{


    Spinner spinner;

    View rootview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.search_place, container, false);
        DatabaseInterface database_interface_object = new DatabaseInterface();
        String[] sector_list={""};
        try {
            sector_list = new getSector().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
           e.printStackTrace();
        }
        spinner = (Spinner) rootview.findViewById(R.id.static_spinner);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(),  android.R.layout.simple_spinner_item,sector_list);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        Button button = (Button) rootview.findViewById(R.id.searchbutton);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View rootview)
            {
                searchPlaceLogic(rootview);
            }
        });
        return rootview;
    }



//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }

    public void searchPlaceLogic(View view)
    {
        AlertDialog.Builder alerter = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.myDialog));
        EditText mPLZ = (EditText)rootview.findViewById(R.id.plz);

        alerter.setIcon(android.R.drawable.ic_dialog_alert);

        String plz = mPLZ.getText().toString();

        if (plz.length() < 3 || plz.length() > 5)
        {
            alerter.setMessage("Please put in a zip code between 3 - 5 digits");
            alerter.setTitle("Fail");
            alerter.show();
        }
        RatingBar minCBar = (RatingBar) rootview.findViewById(R.id.occmin);
        RatingBar maxCBar = (RatingBar) rootview.findViewById(R.id.occmax);
        RatingBar minRBar = (RatingBar) rootview.findViewById(R.id.rankmin);
        RatingBar maxRBar = (RatingBar) rootview.findViewById(R.id.rankmax);

        EditText mName = (EditText) rootview.findViewById(R.id.name);
        EditText mTown = (EditText) rootview.findViewById(R.id.town);
        String Name = mName.getText().toString();
        String Town = mTown.getText().toString();
        Float minC = minCBar.getRating();
        Float maxC = maxCBar.getRating();
        Float minR = minRBar.getRating();
        Float maxR = maxRBar.getRating();
        Integer sectorID= (int) (long)spinner.getSelectedItemId() +1;
        String[] params = new String[]{Name, sectorID.toString(),plz,Town,minC.toString(),maxC.toString(),minR.toString(),maxR.toString() };

        try {
            if (new doTask().execute(params).get()) {
                Intent intent = new Intent(getActivity(), SearchDisplayActivity.class);
                startActivity(intent);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
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

    }class getSector extends AsyncTask<String, String, String[]> {
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

    class doTask extends AsyncTask<String, String, Boolean> {





        protected Boolean doInBackground(String... args) {
            JSONArray places = DatabaseInterface.searchPlace(args[0],args[1],args[2],args[3],args[4],args[5],args[6],args[7]);
            if (places==null)
            {
                return false;
            }
            else
            {
                LastVisitedPlace_Fragment.place_list=places;
                return true;
            }

        }




    }
}

