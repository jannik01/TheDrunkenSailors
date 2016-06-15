package at.tugraz.thedrunksailor;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.concurrent.ExecutionException;

public class FriendList_Fragment extends Fragment {
    View rootview;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_friendlist, container, false);

        final ListView listview = (ListView) rootview.findViewById(R.id.listview);
        final String[][] person_list = getFriendos();
        if (person_list!=null) {
            if (listview != null) {
                listview.setAdapter(new PlaceItemAdapter(getActivity(), person_list));
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Globals.pers_id = Integer.parseInt(person_list[position][0]);
                        Intent intent = new Intent(getActivity(), PersonDetailActivity.class);
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
            if (listview != null) {
                listview.setAdapter(new PlaceItemAdapter(getActivity(), no_friends));
            }
        }
        DatabaseInterface database_interface_object = new DatabaseInterface();
        return rootview;
    }


    public String[][] getFriendos() {
        String[] params = new String[]{Integer.toString(Globals.uid)};
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

    class getFriends extends AsyncTask<String, String, String[][]> {
        protected String[][] doInBackground(String... args) {
            Integer pers_int = Integer.parseInt(args[0]);
            JSONArray persons = DatabaseInterface.searchPersonsIdIsFollowing(pers_int);
            int persons_length = persons.length();
            if(persons_length==0)
                return null;
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













