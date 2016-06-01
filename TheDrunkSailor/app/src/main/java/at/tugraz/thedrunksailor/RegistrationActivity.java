package at.tugraz.thedrunksailor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import java.util.concurrent.ExecutionException;

public class RegistrationActivity extends AppCompatActivity {
    public static String TAG = "REGISTRATION_ACTIVITY";
    EditText mEdit;
    EditText mPassword;

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
        String ErrorMsg = "";
        RadioButton female = (RadioButton) findViewById(R.id.radioFemale);
        if (female.isSelected())
            gender = "f";
        else
            gender = "m";
        if (!ageText.getText().toString().equals(""))
            age = Integer.parseInt(ageText.getText().toString());
        if (!password.equals(passwordRe) || password.equals("") || passwordRe.equals(""))
        {
            ErrorMsg= ErrorMsg +"Password not matching or empty\n";
            isAllAlright=false;
        }
        if (username.equals("") || name.equals(""))
        {
            ErrorMsg= ErrorMsg +"Not all required Fields are entered\n";
            isAllAlright=false;
        }
        mEdit = (EditText)findViewById(R.id.txtUsername);
        mPassword = (EditText)findViewById(R.id.txtPassword);

        AlertDialog.Builder alert = new AlertDialog.Builder(RegistrationActivity.this);
        alert.setMessage("-----");
        alert.setIcon(android.R.drawable.ic_dialog_alert);

        String UserName = mEdit.getText().toString();
        String Password = mPassword.getText().toString();
        if (UserName.length() > 25 || UserName.length() < 5)
        {
            ErrorMsg= ErrorMsg +"Please use a username between 5 & 25 characters!\n";
            isAllAlright=false;
        }
        if (Password.length() > 25 || Password.length() < 8)
        {
            ErrorMsg= ErrorMsg +"Please use a password between 8 & 25 characters!\n";
            isAllAlright=false;
        }
        int count_underscore = 0;
        int ascii = -1;
        for (int i = 0; i < UserName.length(); i++) {
            ascii = UserName.charAt(i);
            if(ascii == 95)
            {
                if (count_underscore > 0)
                {
                    ErrorMsg= ErrorMsg +"Please just use one underscore!\n";
                    isAllAlright=false;
                }
                if (i == 0 || i == UserName.length()-1)
                {
                    ErrorMsg= ErrorMsg +"Please use underscore not at the beginning or end!\n";
                    isAllAlright=false;
                }
                count_underscore++;
            }
            if (((ascii > 64 && ascii < 91) || (ascii > 96 && ascii < 123) || (ascii > 47 && ascii < 58) || ascii == 95) == false)
            {
                ErrorMsg= ErrorMsg +"Please just use letters, numbers and underscore!\n";
                isAllAlright=false;
            }
        }
        if(isAllAlright)
        {
            boolean success=false;
            try {
                String[] params=new String[]{username, password, name,  gender,ageText.getText().toString(), job};
                success = new doTask().execute(params).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            if (success==true)
            {
                Intent intent = new Intent(this, SignInActivity.class);
                startActivity(intent);
            }
        }
        else{
            alert.setMessage(ErrorMsg);
            alert.setTitle("Fail");
            alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // continue with delete
                }
            });
            alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // do nothing
                }
            });
            alert.show();
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

    class doTask extends AsyncTask<String, String, Boolean> {
        protected Boolean doInBackground(String... args) {
            int age = 0;
            if (!args[4].isEmpty())
            {
                age = Integer.parseInt(args[4]);
            }
            boolean success = DatabaseInterface.createUser(args[0],args[1],args[2],args[3],age,args[5]);
            return success;
        }
    }
}
