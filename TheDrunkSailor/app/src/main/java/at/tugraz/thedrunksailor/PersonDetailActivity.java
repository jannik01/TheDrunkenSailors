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
import android.widget.SeekBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.concurrent.ExecutionException;

public class PersonDetailActivity extends AppCompatActivity {

    public static final int MAX = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail_person);
        TextView name = (TextView)findViewById(R.id.name);
        TextView age = (TextView)findViewById(R.id.age);
        TextView sex = (TextView)findViewById(R.id.sex);
        TextView job = (TextView)findViewById(R.id.job);
        //TextView lastly_visited = (TextView)findViewById(R.id.lastly_visited);
        //TextView friends = (TextView)findViewById(R.id.friends);

        setButtonFollow();
        Button follow_button = (Button) findViewById(R.id.button2);
        follow_button.setOnClickListener(follow_button_handler);



        String []details =getDetails();
        String [][]last_places=getLastPlaces();
        final ListView listview = (ListView) findViewById(R.id.listview);
        final String [][] places_list=getLastPlaces();
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
        }
        else
        {
            String[][] no_places = {
                    {"1", "No Place visited yet", "-", "-"},

            };
            if (listview != null) {
                listview.setAdapter(new PlaceItemAdapter(this, no_places));
            }
        }
        name.setText(details[0]);
        age.setText(details[1]);
        sex.setText(details[2]);
        job.setText(details[3]);








    }

    View.OnClickListener follow_button_handler = new View.OnClickListener() {
        public void onClick(View v) {
            Button follow_button = (Button) findViewById(R.id.button2);
            String buttonText = follow_button.getText().toString();
            if(buttonText.equals("follow") || buttonText.equals("FOLLOW")) {
                Log.e("Follower Button", "Follower Button clicked");
                followPerson();
            } else if(buttonText.equals("unfollow") || buttonText.equals("UNFOLLOW")){
                Log.e("Follower Button", "Unollower Button clicked");
                unfollowPerson();
            }


        }
    };

    public String[] getDetails() {
        String[] params = new String[]{Integer.toString(Globals.pers_id)};
        new getData().execute(params);
        String[] detail_list = new String[0];
        try {
            detail_list = new getData().execute(params).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        return detail_list;

    }
    public String[][] getLastPlaces() {
        String[] params = new String[]{Integer.toString(Globals.pers_id)};
        new getData().execute(params);
        String[][] detail_list = new String[0][];
        try {
            detail_list = new getLast().execute(params).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        return detail_list;

    }

    public Boolean followPerson(){
        String[] params = new String[]{Integer.toString(Globals.pers_id)};
        try {
            Button button = (Button)findViewById(R.id.button2);
            Boolean following_success = new doTaskFollowPerson().execute(params).get();
            if(following_success){
                // set button to "unfollow"
                assert button != null;
                button.setText("unfollow");
                return true;
            } else {

                // Dont change button
                // show Alert message that user cant be followed
                assert button != null;
                button.setText("follow");
                return false;
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return false;

    }

    public Boolean unfollowPerson(){
        String[] params = new String[]{Integer.toString(Globals.pers_id)};
        try {
            Button button = (Button)findViewById(R.id.button2);
            Boolean unfollowing_success = new doTaskUnFollowPerson().execute(params).get();
            if(unfollowing_success){
                // set button to "unfollow"
                assert button != null;
                button.setText("follow");
                return true;
            } else {

                assert button != null;
                button.setText("unfollow");
                // Dont change button
                // show Alert message that user cant be followed
                return false;
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return false;

    }

    private class MySeekListener implements SeekBar.OnSeekBarChangeListener {


        private int uiID = 0;

        public MySeekListener(int uid) {
            uiID = uid;
        }

        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            // Log the progress
            Log.d("DEBUG", "Progress is: " + progress);
            TextView view = (TextView) findViewById(uiID);
            if (view != null) {
                String text = progress+1 + "/" + (MAX+1);
                view.setText( text);
            }


        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
        }


    }

    class doTask extends AsyncTask<String, String, Boolean> {
        protected Boolean doInBackground(String... args) {
            boolean success = DatabaseInterface.ratePlace(args[0], args[1]);
            return success;
        }

    }
    class getData extends AsyncTask<String, String, String[]> {
        protected String[] doInBackground(String... args) {
            Integer pers_int = Integer.parseInt(args[0]);
            JSONArray details = DatabaseInterface.getPersonData(pers_int);
            String[] detailslist = new String[4];
            try {
                detailslist[0] = details.getJSONObject(0).getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                detailslist[1] = details.getJSONObject(0).getString("age");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                detailslist[2] = details.getJSONObject(0).getString("sex");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                detailslist[3] = details.getJSONObject(0).getString("job");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return detailslist;
        }
    }


    class getLast extends AsyncTask<String, String, String[][]> {

        protected String[][] doInBackground(String... args) {
            Integer pers_int = Integer.parseInt(args[0]);
            JSONArray places = DatabaseInterface.startPagePlaces(pers_int);
            Integer places_length;
            if(places==null)
                return null;

            places_length = places.length();
            if (places_length>5)
                places_length=5;
            String[][] places_list = new String[places_length][4];
            for (Integer i = 0; places.length() > i; i++) {
                try {
                    places_list[i][0] = places.getJSONObject(i).getString("place_ID");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    places_list[i][1] = places.getJSONObject(i).getString("name");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    places_list[i][2] = places.getJSONObject(i).getString("current_use");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    places_list[i][3] = places.getJSONObject(i).getString("rating");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return places_list;
        }
    }


    class doTaskFollowPerson extends AsyncTask<String, String, Boolean> {


        protected Boolean doInBackground(String... args) {


            Boolean following_succeeded = DatabaseInterface.followPerson(Integer.parseInt(args[0]));


            if (following_succeeded) {
                //Log.e("test", places_list_2[0][0].toString());
                Log.e("Follwing", "Follwing succeeded");
                return true;
            } else {
                Log.e("Follwing", "Follwing did not succeed");
                return false;
            }

        }
    }

    class doTaskUnFollowPerson extends AsyncTask<String, String, Boolean> {


        protected Boolean doInBackground(String... args) {


            Boolean unfollowing_succeeded = DatabaseInterface.unfollowPerson(Integer.parseInt(args[0]));


            if (unfollowing_succeeded) {
                //Log.e("test", places_list_2[0][0].toString());
                Log.e("Unfollwing", "Unfollwing succeeded");
                return true;
            } else {
                Log.e("Unfollwing", "Unfollwing did not succeed");
                return false;
            }

        }
    }


    public void setButtonFollow()
    {
        boolean isfollower;
        String[] params = new String[]{Integer.toString(Globals.pers_id)};

        try {
            isfollower = new getFollower().execute(params).get();
            Button button = (Button)findViewById(R.id.button2);
            Log.e("setButtonFollow", "isfollower: " + isfollower);
            if(isfollower){

                // set Button Text "unfollow"
                assert button != null;
                button.setText("Unfollow");
            } else {
                // set Button Text "follow"
                assert button != null;
                button.setText("follow");

            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        // Toast toast_subscribed = Toast.makeText(PersonDetailActivity.this, "You are subscribed", Toast.LENGTH_LONG);

        //        Toast toast_unsubscribed = Toast.makeText(getApplicationContext()., "You are not subscribed", Toast.LENGTH_LONG);
        //        toast_subscribed.setGravity(Gravity.TOP, 0,1500);
        //        toast_unsubscribed.setGravity(Gravity.TOP, 0,1500);


    }


    class getFollower extends AsyncTask<String, String, Boolean> {

        protected Boolean doInBackground(String... args) {
            Integer pers_int = Integer.parseInt(args[0]);
            Boolean isfollower = DatabaseInterface.doIFollowPerson(pers_int);


            return isfollower;
        }
    }



}




