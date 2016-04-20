package at.tugraz.thedrunksailor;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;



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
        AlertDialog.Builder asdf = new AlertDialog.Builder(SignInActivity.this);

        mEdit = (EditText)findViewById(R.id.text_user_name);
        mPassword = (EditText)findViewById(R.id.text_password);


                asdf.setMessage("-----");
                asdf.setIcon(android.R.drawable.ic_dialog_alert);

        String UserName = mEdit.getText().toString();
        String Password = mPassword.getText().toString();

        if (UserName.length() > 25 || UserName.length() < 5)
        {
            asdf.setMessage("Bitte nur einen Usernamen zwischen 5 & 25 Zeichen eingeben!");
            asdf.setTitle("Fail");
            asdf.show();
        }

        if (Password.length() > 25 || Password.length() < 8)
        {
            asdf.setMessage("Bitte nur ein Passwort zwischen 8 & 25 Zeichen eingeben!");
            asdf.setTitle("Fail");
            asdf.show();
        }


        UserName = UserName.toUpperCase();
        int ascii = -1;
        for (int i = 0; i < UserName.length(); i++) {
            ascii = UserName.charAt(i);
            if (((ascii > 65 && ascii < 90) || (ascii > 47 && ascii < 58) || ascii == 95) == false)
            {
              asdf.setMessage("Bitte nur Buchstaben, Zahlen und _ verwenden!");
              asdf.setTitle("Fail");
              asdf.show();
            }
        }

    }
}
