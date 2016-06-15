package at.tugraz.thedrunksailor;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.concurrent.ExecutionException;

public class PlaceDetailActivity extends AppCompatActivity {
    public static final int MAX = 4;
    CheckConnection stateOfConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_detail_place);
        TextView name = (TextView)findViewById(R.id.name);
        TextView address = (TextView)findViewById(R.id.Address);
        TextView zipcode = (TextView)findViewById(R.id.zipcode);
        TextView town = (TextView)findViewById(R.id.town);
        TextView country = (TextView)findViewById(R.id.country);
        TextView description = (TextView)findViewById(R.id.description);
        TextView current_use = (TextView)findViewById(R.id.db_current_use);
        TextView rating = (TextView)findViewById(R.id.db_rating);
        final ListView listviewfriends = (ListView) findViewById(R.id.listviewfriends);
        final String [][] persons_list=getFriendos();
        String []details =getDetails();
        name.setText(details[0]);
        address.setText(details[1]);
        zipcode.setText(details[2]);
        town.setText(details[3]);
        country.setText(details[4]);
        if (details[5].equals("0.0000"))
            current_use.setText("-");
        else
        current_use.setText(details[5]);
        if (details[6].equals("0.0000"))
            rating.setText("-");
        else
        rating.setText(details[6]);
        description.setText(details[7]);

        SeekBar rank = (SeekBar) findViewById(R.id.rngRanking);
        SeekBar use = (SeekBar) findViewById(R.id.rngUse);
        if (rank != null) {
            rank.setOnSeekBarChangeListener(new MySeekListener(R.id.lbRank));
            rank.setMax(MAX);
            rank.setProgress(2);
        }
        if (use != null) {
            use.setOnSeekBarChangeListener(new MySeekListener(R.id.lbUse));
            use.setMax(4);
            use.setProgress(2);
        }
        if (persons_list!=null) {
            if (listviewfriends != null) {
                listviewfriends.setAdapter(new PlaceItemAdapter(this, persons_list));
                listviewfriends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Globals.pers_id = Integer.parseInt(persons_list[position][0]);
                        Intent intent = new Intent(PlaceDetailActivity.this, PersonDetailActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }
        else
        {
            String[][] no_friends = {
                    {"1", "No Friends were there recently", ":(", ":("},
            };
            if (listviewfriends != null) {
                listviewfriends.setAdapter(new PlaceItemAdapter(this, no_friends));
            }
        }
        stateOfConnection = new CheckConnection(this);
    }

    public void buttonOnClick(View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(PlaceDetailActivity.this);
        if(!stateOfConnection.isOnline(alert))
            return;
        SeekBar rank = (SeekBar) findViewById(R.id.rngRanking);
        SeekBar use = (SeekBar) findViewById(R.id.rngUse);
        rank.getProgress();
        String rating = Integer.toString(rank.getProgress()+1);
        String current_use_= Integer.toString(use.getProgress()+1);
        String[] params = new String[]{rating, current_use_};
        try {
            if (new doTask().execute(params).get()) {
                Intent intent = new Intent(this, PlaceDetailActivity.class);
                startActivity(intent);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    public String[] getDetails() {
        String[] params = new String[]{Integer.toString(Globals.pid)};
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
    public String[][] getFriendos() {
        String[] params = new String[]{Integer.toString(Globals.uid),Integer.toString(Globals.pid)};
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
            Integer pid_int = Integer.parseInt(args[0]);
            JSONArray details = DatabaseInterface.getPlaceData(pid_int);
            String[] detailslist = new String[8];
            try {
                detailslist[0] = details.getJSONObject(0).getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                detailslist[1] = details.getJSONObject(0).getString("address");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                detailslist[2] = details.getJSONObject(0).getString("zipcode");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                detailslist[3] = details.getJSONObject(0).getString("town");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                detailslist[4] = details.getJSONObject(0).getString("country");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(Globals.c_use==0.0){
                detailslist[5]="-";
            }
            else
                detailslist[5] = Globals.c_use.toString();

            try {
                if(details.getJSONObject(0).getString("rating")==null){
                    detailslist[6]="0";
                }
                else
                    detailslist[6] = details.getJSONObject(0).getString("rating");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                detailslist[7] = details.getJSONObject(0).getString("description");
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            Log.e("Error",detailslist[7]);
            return detailslist;
        }
    }
    class getFriends extends AsyncTask<String, String, String[][]> {
        protected String[][] doInBackground(String... args) {
            Integer pers_int = Integer.parseInt(args[0]);
            Integer pid_int = Integer.parseInt(args[1]);


            JSONArray persons = DatabaseInterface.searchFriendsAtPlace(pers_int,pid_int);

                if(persons==null)
                    return null;

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
}

