package com.example.leo_r.myapplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

class NewProductActivity extends Activity {

    // Progress Dialog
    private ProgressDialog pDialog;

    JSONParser jsonParser = new JSONParser();
    EditText inputName;
    EditText inputPrice;
    EditText inputDesc;

    // url to create new product
    private static String url_create_product = "http://drunkensailors.robert-thomann.at/login_user.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);

        // Edit Text
        inputName = (EditText) findViewById(R.id.inputName);
        inputPrice = (EditText) findViewById(R.id.inputPrice);
        inputDesc = (EditText) findViewById(R.id.inputDesc);

        // Create button
        Button btnCreateProduct = (Button) findViewById(R.id.btnCreateProduct);

        // button click event
        btnCreateProduct.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // creating new product in background thread
                new CreateNewProduct().execute();
            }
        });
    }

    /**
     * Background Async Task to Create new product
     * */
    class CreateNewProduct extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */



        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            String userName = "pope";//inputUserName.getText().toString();
            String password = "$2y$10$TCaTynz76mfHAzGzfsmwhuoS6n.EiFZDr3dqJBhXtmKP2gqIa1nWu";//inputPasswordSalt.getText().toString();
            /*String name = "popo";//inputName.getText().toString();
            String sex  = "m";//inputSex.getText().toString();
            String age = "80";//inputAge.getText().toString();
            String job = "pope";//inputJob.getText().toString();*/

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("user_name", userName));
            params.add(new BasicNameValuePair("password", password));
            /*params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("sex", sex));
            params.add(new BasicNameValuePair("age", age));
            params.add(new BasicNameValuePair("job", job));*/
            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                    "POST", params);
            // getting JSON Object
            // Note that create product url accepts POST method

            //System.out.println(Arrays.toString(params.toArray()));
            // check log cat fro response
            if(json == null)
                return null;
            Log.d("Create Response", json.toString());
            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product


                    // closing this screen
                    finish();
                } else {
                    // failed to create product
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/



    }
}