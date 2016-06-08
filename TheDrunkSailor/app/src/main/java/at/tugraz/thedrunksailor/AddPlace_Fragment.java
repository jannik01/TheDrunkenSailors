package at.tugraz.thedrunksailor;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.concurrent.ExecutionException;

public class AddPlace_Fragment extends Fragment implements AdapterView.OnItemSelectedListener {
    View rootview;
    CheckConnection stateOfConnection;
    Spinner spinner;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_add_place, container, false);
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

        Button button = (Button) rootview.findViewById(R.id.btnAddPlace);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View rootview)
            {
                buttonOnClick(rootview);
            }
        });
        stateOfConnection = new CheckConnection(getActivity());
        return rootview;
    }

    public void onNothingSelected(AdapterView<?> parent){
    }


    public void buttonOnClick(View v) {
        AlertDialog.Builder alerter = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.myDialog));
        if(!stateOfConnection.isOnline(alerter))
            return;
        EditText Name = (EditText) rootview.findViewById(R.id.txtName);
        EditText Address = (EditText) rootview.findViewById(R.id.txtAdress);
        EditText Town = (EditText) rootview.findViewById(R.id.txtTown);
        EditText Zip = (EditText) rootview.findViewById(R.id.txtZip);
        EditText Country = (EditText) rootview.findViewById(R.id.txtCountry);
        EditText Description = (EditText) rootview.findViewById(R.id.txtDescription);
        Integer sectorID= (int) (long)spinner.getSelectedItemId() +1;

        if (!Name.getText().toString().isEmpty() && !Address.getText().toString().isEmpty() &&
            !Zip.getText().toString().isEmpty() && !sectorID.toString().isEmpty() &&
            Zip.length() >= 3 && Zip.length() <= 5 && Integer.parseInt(sectorID.toString()) >= 0)
        {
            String[] params = new String[]{Name.getText().toString(), Description.getText().toString(), sectorID.toString(),
                    Address.getText().toString(), Country.getText().toString(), Zip.getText().toString(), Town.getText().toString()};
            try {
                if (new doTask().execute(params).get()) {
                   Intent intent = new Intent(getActivity(), FragmentManagerActivity.class);
                   startActivity(intent);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.myDialog))
                .setTitle("Wrong input")
                .setMessage("Please fill all the required fields")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        }
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        TextView myText = (TextView) view;
        Toast.makeText(getActivity(), "You selected " + myText.getText(), Toast.LENGTH_SHORT).show();
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