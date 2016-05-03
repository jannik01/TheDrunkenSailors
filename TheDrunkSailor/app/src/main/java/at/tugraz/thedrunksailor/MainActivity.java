package at.tugraz.thedrunksailor;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText mPLZ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_place);
        DatabaseInterface database_interface_object = new DatabaseInterface();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void searchPlaceLogic(View view)
    {
        AlertDialog.Builder alerter = new AlertDialog.Builder(MainActivity.this);
        mPLZ = (EditText)findViewById(R.id.plz);

        alerter.setIcon(android.R.drawable.ic_dialog_alert);

        String plz = mPLZ.getText().toString();

        if (plz.length() < 3 || plz.length() > 5)
        {
            alerter.setMessage("Please put in a zip code between 3 - 5 digits");
            alerter.setTitle("Fail");
            alerter.show();
        }

    }


}
