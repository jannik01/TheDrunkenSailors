package at.tugraz.thedrunksailor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class RegistrationActivity extends AppCompatActivity {

    public static String TAG = "REGISTRATION_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    public void doRegistration(View v) {

        Log.e("REGISTRATION", "btn clicked");
        EditText usernameText = (EditText) findViewById(R.id.txtUsername);
        EditText passwordText  = (EditText) findViewById(R.id.txtPassword);
        EditText passwordReText  = (EditText) findViewById(R.id.txtPasswordRe);
        EditText nameText  = (EditText) findViewById(R.id.txtName);
        EditText ageText  = (EditText) findViewById(R.id.txtAge);
        EditText jobText  = (EditText) findViewById(R.id.txtJob);

        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();
        String passwordRe = passwordReText.getText().toString();
        String name = nameText.getText().toString();
        String job = jobText.getText().toString();
        int age = Integer.parseInt(ageText.getText().toString());

        if(!password.equals(passwordRe)) {
            Log.d(TAG, "password not the same");
        }



    }
}
