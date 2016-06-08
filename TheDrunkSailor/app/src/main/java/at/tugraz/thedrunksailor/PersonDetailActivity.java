package at.tugraz.thedrunksailor;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.concurrent.ExecutionException;

public class PersonDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail_person);
        TextView name = (TextView)findViewById(R.id.name);
        TextView age = (TextView)findViewById(R.id.age);
        TextView sex = (TextView)findViewById(R.id.sex);
        TextView job = (TextView)findViewById(R.id.job);


        setButtonFollow();
        Button follow_button = (Button) findViewById(R.id.button2);
        follow_button.setOnClickListener(follow_button_handler);

        final ListView listview = (ListView) findViewById(R.id.listview);
        final String [][] places_list=getLastPlaces();
        String []details =getPersonDetails();

        if (places_list!=null) {

            if (listview != null) {

                listview.setAdapter(new PlaceItemAdapter(this, places_list));
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {

                        Globals.pid = Integer.parseInt(places_list[position][0]);

                        Intent intent = new Intent(PersonDetailActivity.this, PlaceDetailActivity.class);
                        startActivity(intent);

                    }
                });
            }
        } else {

            String[][] no_places = {{"1", "No Place visited yet", "-", "-"}};
            if (listview != null) {

                listview.setAdapter(new PlaceItemAdapter(this, no_places));
            }
        }
        if (name != null && age != null && sex != null && job != null) {
            name.setText(details[0]);
            age.setText(details[1]);
            sex.setText(details[2]);
            job.setText(details[3]);
        }

    }

    View.OnClickListener follow_button_handler = new View.OnClickListener() {
        public void onClick(View v) {
            Button follow_button = (Button) findViewById(R.id.button2);
            String buttonText = follow_button.getText().toString();

            if(buttonText.equals("follow") || buttonText.equals("FOLLOW"))
            {
                followPerson();

            } else if(buttonText.equals("unfollow") || buttonText.equals("UNFOLLOW"))
            {
                unfollowPerson();
            }


        }
    };

    public String[] getPersonDetails() {
        String[] params = new String[]{Integer.toString(Globals.pers_id)};
        String[] detail_list = new String[0];

        try {
            detail_list = new getPersonData().execute(params).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return detail_list;

    }
    public String[][] getLastPlaces() {
        String[] params = new String[]{Integer.toString(Globals.pers_id)};
        String[][] last_places_list = new String[0][];

        try {
            last_places_list = new GetLastPlaces().execute(params).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return last_places_list;

    }

    public Boolean followPerson(){
        String[]    params = new String[]{Integer.toString(Globals.pers_id)};
        Button      button = (Button)findViewById(R.id.button2);

        try {
            Boolean following_success = new DoTaskFollowPerson().execute(params).get();

            if(following_success){

                Toast.makeText(PersonDetailActivity.this, "You are subscribed", Toast.LENGTH_LONG).show();

                assert button != null;
                button.setText("unfollow");
                return true;
            } else {
                return false;
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return false;

    }

    public Boolean unfollowPerson(){
        String[] params = new String[]{Integer.toString(Globals.pers_id)};
        Button button = (Button)findViewById(R.id.button2);

        try {
            Boolean unfollowing_success = new DoTaskUnFollowPerson().execute(params).get();

            if(unfollowing_success){
                assert button != null;
                button.setText("follow");
                return true;
            } else {
                return false;
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return false;

    }

    class getPersonData extends AsyncTask<String, String, String[]> {

        protected String[] doInBackground(String... args) {

            String[]    detailslist = new String[4];
            Integer     person_id    = Integer.parseInt(args[0]);
            JSONArray   details     = DatabaseInterface.getPersonData(person_id);

            try {
                detailslist[0] = details.getJSONObject(0).getString("name");
                detailslist[1] = details.getJSONObject(0).getString("age");
                detailslist[2] = details.getJSONObject(0).getString("sex");
                detailslist[3] = details.getJSONObject(0).getString("job");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return detailslist;
        }
    }


    class GetLastPlaces extends AsyncTask<String, String, String[][]> {

        protected String[][] doInBackground(String... args) {

            Integer person_id = Integer.parseInt(args[0]);
            JSONArray places = DatabaseInterface.startPagePlaces(person_id);
            Integer places_length;

            if (places == null) {
                return null;
            }

            places_length = places.length();
            if (places_length > 5){
                places_length = 5;
            }

            String[][] places_list = new String[places_length][4];

            for (Integer i = 0; places.length() > i; i++) {

                try {
                    places_list[i][0] = places.getJSONObject(i).getString("place_ID");
                    places_list[i][1] = places.getJSONObject(i).getString("name");
                    places_list[i][2] = places.getJSONObject(i).getString("current_use");
                    places_list[i][3] = places.getJSONObject(i).getString("rating");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return places_list;
        }
    }


    class DoTaskFollowPerson extends AsyncTask<String, String, Boolean> {

        protected Boolean doInBackground(String... args) {

            Boolean following_succeeded = DatabaseInterface.followPerson(Integer.parseInt(args[0]));

            if (following_succeeded) {
                Log.e("Task Follow", "Following succeeded");
                return true;
            } else {
                Log.e("Task Follow", "Following did not succeed");
                return false;
            }
        }
    }

    class DoTaskUnFollowPerson extends AsyncTask<String, String, Boolean> {

        protected Boolean doInBackground(String... args) {

            Boolean unfollowing_succeeded = DatabaseInterface.unfollowPerson(Integer.parseInt(args[0]));

            if (unfollowing_succeeded) {
                Log.e("Task Unfollow", "Unfollwing succeeded");
                return true;
            } else {
                Log.e("Task Unfollow", "Unfollwing did not succeed");
                return false;
            }

        }
    }


    public void setButtonFollow()
    {
        boolean is_follower;
        String[]    params = new String[]{Integer.toString(Globals.pers_id)};
        Button      button = (Button)findViewById(R.id.button2);

        try {
            is_follower = new DoTaskDoIFollow().execute(params).get();
            Log.e("setButtonFollow", "Do I follow this Person?: " + is_follower);

            if(is_follower){
                assert button != null;
                button.setText("unfollow");
            } else {
                assert button != null;
                button.setText("follow");
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    class DoTaskDoIFollow extends AsyncTask<String, String, Boolean> {

        protected Boolean doInBackground(String... args) {

            Integer person_id = Integer.parseInt(args[0]);
            return DatabaseInterface.doIFollowPerson(person_id);
        }
    }



}




