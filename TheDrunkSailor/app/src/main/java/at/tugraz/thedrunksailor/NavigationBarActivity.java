package at.tugraz.thedrunksailor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Sami on 04/05/2016.
 */
public class NavigationBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToHome(View view)
    {
        Intent intent = new Intent(NavigationBarActivity.this, MainActivity.class);
        startActivity(intent);
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

    public void goToAddPlace(View view)
    {
        Intent intent = new Intent(NavigationBarActivity.this, ActivityAddPlace.class);
        startActivity(intent);
    }
}