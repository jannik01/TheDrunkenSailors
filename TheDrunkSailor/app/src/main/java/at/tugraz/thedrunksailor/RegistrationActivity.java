package at.tugraz.thedrunksailor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class RegistrationActivity extends AppCompatActivity {

    public static String TAG = "REGISTRATION_ACTIVITY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        
    }

    public void doRegistration(View v) {

        EditText usernameText = (EditText) findViewById(R.id.txtUsername);
        EditText passwordText = (EditText) findViewById(R.id.txtPassword);
        EditText passwordReText = (EditText) findViewById(R.id.txtPasswordRe);
        EditText nameText = (EditText) findViewById(R.id.txtName);
        EditText ageText = (EditText) findViewById(R.id.txtAge);
        EditText jobText = (EditText) findViewById(R.id.txtJob);

        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();
        String passwordRe = passwordReText.getText().toString();
        String name = nameText.getText().toString();
        String job = jobText.getText().toString();
        String gender;
        int age = 0;
        Boolean isAllAlright = true;

        RadioButton female = (RadioButton) findViewById(R.id.radioFemale);
        if (female.isSelected())
            gender = "f";
        else
            gender = "m";


        if (!ageText.getText().toString().equals(""))
            age = Integer.parseInt(ageText.getText().toString());

        if (!password.equals(passwordRe) || password.equals("") || passwordRe.equals(""))
        {
            showAlert("Password Failure", "Password not matching or empty");
            isAllAlright=false;
        }

        if (username.equals("") || name.equals(""))
        {
            showAlert("Empty Fields", "Not all required Fields are entered");
            isAllAlright=false;
        }

        if(isAllAlright)
        {
            DatabaseInterface.createUser(username,password,name,gender,age,job);
        }

    }

    private void showAlert(String title, String messaage) {
        AlertDialog alertDialog = new AlertDialog.Builder(RegistrationActivity.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(messaage);
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void returnLogIn(View view)
    {
        Intent intent = new Intent(RegistrationActivity.this, SignInActivity.class);
        startActivity(intent);
    }


}