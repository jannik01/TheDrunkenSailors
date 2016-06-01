package at.tugraz.thedrunksailor;

import android.content.DialogInterface;
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
        mEdit = (EditText)findViewById(R.id.text_user_name);
        mPassword = (EditText)findViewById(R.id.text_password);

        String UserName = mEdit.getText().toString();
        String Password = mPassword.getText().toString();

        Integer userid=0;

        AlertDialog.Builder alert = new AlertDialog.Builder(SignInActivity.this);

        alert.setMessage("-----");
        alert.setIcon(android.R.drawable.ic_dialog_alert);

        try {
             userid = new doTask().execute(UserName, Password).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(userid == 0)
        {
        alert.setMessage("Username and Password is wrong!");
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

        if (Globals.uid>0)
        {
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
