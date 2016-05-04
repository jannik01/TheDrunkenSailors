package at.tugraz.thedrunksailor;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ActivityAddPlace extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);
    }

    public void buttonOnClick(View v){

        EditText Name = (EditText)findViewById(R.id.txtName);
        EditText Adress = (EditText)findViewById(R.id.txtAdress);
        EditText Town = (EditText)findViewById(R.id.txtTown);
        EditText Sector = (EditText)findViewById(R.id.txtSector);
        EditText Zip = (EditText)findViewById(R.id.txtZip);
        EditText Country = (EditText)findViewById(R.id.txtCountry);
        EditText Description = (EditText)findViewById(R.id.txtDescription);

        //DatabaseInterface.createPlace(Name.getText().toString(), Description.getText().toString(), Integer.parseInt(Sector.getText().toString()), Adress.getText().toString(), Country.getText().toString(), Zip.getText().toString());


        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }



}