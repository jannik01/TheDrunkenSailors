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
            asdf.setMessage("Please use a username between 5 & 25 characters!");
            asdf.setTitle("Fail");
            asdf.show();
        }

        if (Password.length() > 25 || Password.length() < 8)
        {
            asdf.setMessage("Please use a password between 8 & 25 characters!");
            asdf.setTitle("Fail");
            asdf.show();
        }


        int count_underscore = 0;
        UserName = UserName.toUpperCase();
        int ascii = -1;
        for (int i = 0; i < UserName.length(); i++) {
            ascii = UserName.charAt(i);

            if(ascii == 95)
            {
                if (count_underscore > 0)
                {
                    asdf.setMessage("Please just use one underscore!");
                    asdf.setTitle("Fail");
                    asdf.show();
                }
                if (i == 0 || i == UserName.length()-1)
                {
                    asdf.setMessage("Please use underscore not at the beginning or end!");
                    asdf.setTitle("Fail");
                    asdf.show();
                }

                count_underscore++;



            }
            if (((ascii > 64 && ascii < 91) || (ascii > 47 && ascii < 58) || ascii == 95) == false)
            {
              asdf.setMessage("Please just use letters, numbers and underscore!");
              asdf.setTitle("Fail");
              asdf.show();
            }
        }

    }
}
