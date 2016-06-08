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
import android.widget.Toast;

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
        String []details =getDetails();
        final ListView listviewfriends = (ListView) findViewById(R.id.listviewfriends);
        final ListView listview = (ListView) findViewById(R.id.listview);
        final String [][] places_list=getLastPlaces();
        final String [][] persons_list=getFriendos();
        setButtonFollow();
        Button follow_button = (Button) findViewById(R.id.button2);
        follow_button.setOnClickListener(follow_button_handler);


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
        }if (persons_list!=null) {
            if (listviewfriends != null) {
                listviewfriends.setAdapter(new PlaceItemAdapter(this, persons_list));
                listviewfriends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Globals.pers_id = Integer.parseInt(persons_list[position][0]);
                        Intent intent = new Intent(PersonDetailActivity.this, PersonDetailActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }
        else
        {
            String[][] no_friends = {
                    {"1", "No Friends", ":(", ":("},
            };
            if (listviewfriends != null) {
                listviewfriends.setAdapter(new PlaceItemAdapter(this, no_friends));
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

            if(buttonText.equals("follow") || buttonText.equals("FOLLOW"))
            {
                followPerson();

            } else if(buttonText.equals("unfollow") || buttonText.equals("UNFOLLOW"))
            {
                unfollowPerson();
            }


        }
    };

    public String[] getDetails() {
        String[] params = new String[]{Integer.toString(Globals.pers_id)};
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
        String[][] last_places_list  = new String[0][];
        try {
            last_places_list  = new getLast().execute(params).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return last_places_list ;
    }
    public String[][] getFriendos() {
        String[] params = new String[]{Integer.toString(Globals.pers_id)};
        String[][] friend_list = new String[0][];
        try {
            friend_list = new getFriends().execute(params).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return friend_list;
    }
    public Boolean followPerson() {
        String[] params = new String[]{Integer.toString(Globals.pers_id)};
        Button button = (Button) findViewById(R.id.button2);

        try {
            Boolean following_success = new DoTaskFollowPerson().execute(params).get();

            if (following_success) {

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

    private class MySeekListener implements SeekBar.OnSeekBarChangeListener {
        private int uiID = 0;
        public MySeekListener(int uid) {
            uiID = uid;
        }

        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
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
    class getFriends extends AsyncTask<String, String, String[][]> {
        protected String[][] doInBackground(String... args) {
            Integer pers_int = Integer.parseInt(args[0]);
            JSONArray persons = DatabaseInterface.searchPersonsIdIsFollowing(pers_int);
            try {
                if(persons.getJSONObject(0).getString("name")=="null")
                    return null;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            int persons_length = persons.length();
            String[][] it_follows = new String[persons_length][4];
            for (Integer i = 0; persons.length() > i; i++) {
                try {
                    it_follows[i][0] = persons.getJSONObject(i).getString("user_id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    it_follows[i][1] = persons.getJSONObject(i).getString("name");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    it_follows[i][2] = persons.getJSONObject(i).getString("age");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    it_follows[i][3] = persons.getJSONObject(i).getString("sex");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return it_follows;
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