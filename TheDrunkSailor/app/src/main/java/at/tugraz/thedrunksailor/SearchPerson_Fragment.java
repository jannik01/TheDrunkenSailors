package at.tugraz.thedrunksailor;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SearchPerson_Fragment extends Fragment implements AdapterView.OnItemSelectedListener {


    Spinner spinner;
    CheckConnection stateOfConnection;
    View rootview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_search_person, container, false);
        DatabaseInterface database_interface_object = new DatabaseInterface();


        Button button = (Button) rootview.findViewById(R.id.searchbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View rootview) {
                searchPersonLogic(rootview);
            }
        });
        stateOfConnection = new CheckConnection(getActivity());
        return rootview;
    }


//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }

    public void searchPersonLogic(View view) {
        AlertDialog.Builder alerter = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.myDialog));
        if(!stateOfConnection.isOnline(alerter))
            return;
        EditText mName = (EditText) rootview.findViewById(R.id.name);
        EditText mAge = (EditText) rootview.findViewById(R.id.age);
        EditText mJob = (EditText) rootview.findViewById(R.id.job);
        EditText mLast = (EditText) rootview.findViewById(R.id.lastly_visited);
        String Sex = "";
        String Name = mName.getText().toString();
        String Age = mAge.getText().toString();
        String Job = mJob.getText().toString();
        String Last = mLast.getText().toString();

        RadioButton female = (RadioButton) rootview.findViewById(R.id.radioFemale);
        RadioButton male = (RadioButton) rootview.findViewById(R.id.radioMale);

        if (female.isChecked())
            Sex = "f";
        if (male.isChecked())
            Sex = "m";

        String[] params = new String[]{Name, Age, Sex, Job, Last};

        try {
            if (new doTask().execute(params).get()) {
                Intent intent = new Intent(getActivity(), PersonSearchDisplayActivity.class);
                startActivity(intent);
            }
            else
            {
                Toast toast = Toast.makeText(getActivity(), "No User(s) found", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 0,1500);
                toast.show();

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
        Toast.makeText(getActivity(), "You selected " + myText.getText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }

    class doTask extends AsyncTask<String, String, Boolean> {
        protected Boolean doInBackground(String... args) {
            JSONArray persons = DatabaseInterface.searchPerson(args[0], args[1], args[2], args[3], args[4]);
            if (persons.length() != 0) {
                return false;
            } else {
                Globals.person_list = persons;
                return true;
            }

        }


    }
}
