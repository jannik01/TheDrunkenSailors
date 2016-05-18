package at.tugraz.thedrunksailor;

import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.concurrent.ExecutionException;


public class SignInActivity extends AppCompatActivity
{
    EditText mEdit;
    EditText mPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
    }

    public void signInLogic(View view)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(SignInActivity.this);

        mEdit = (EditText)findViewById(R.id.text_user_name);
        mPassword = (EditText)findViewById(R.id.text_password);


                alert.setMessage("-----");
                alert.setIcon(android.R.drawable.ic_dialog_alert);

        String UserName = mEdit.getText().toString();
        String Password = mPassword.getText().toString();
        Boolean isAllAlright = true;

        /*if (UserName.length() > 25 || UserName.length() < 5)
        {
            alert.setMessage("Please use a username between 5 & 25 characters!");
            alert.setTitle("Fail");
            alert.show();
            isAllAlright=false;
        }

        if (Password.length() > 25 || Password.length() < 8)
        {
            alert.setMessage("Please use a password between 8 & 25 characters!");
            alert.setTitle("Fail");
            alert.show();
            isAllAlright=false;
        }*/


        if(isAllAlright)
        {
            Integer userid=0;
            try {
                 userid = new doTask().execute(UserName, Password).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
                Intent intent = new Intent(this, FragmentManagerActivity.class);
                startActivity(intent);
        }
    }

    public void register(View view)
    {
        Intent intent = new Intent(SignInActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }
    class doTask extends AsyncTask<String, String, Integer> {





        protected Integer doInBackground(String... args) {
            Integer user_id = DatabaseInterface.login(args[0],args[1]);


            return user_id;
        }




    }
}
