package at.tugraz.thedrunksailor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;

public class PersonSearchDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_search_person);
        final ListView listview = (ListView) findViewById(R.id.listview);
        final String [][] person_list=createDummyList();
        if (listview != null) {
            listview.setAdapter(new PlaceItemAdapter(this, person_list));
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Globals.pers_id=Integer.parseInt(person_list[position][0]);
                    Intent intent = new Intent(PersonSearchDisplayActivity.this, PersonDetailActivity.class);
                    startActivity(intent);
                }
            });
        }
        DatabaseInterface database_interface_object = new DatabaseInterface();
    }


//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.logout:
//                Intent intent = new Intent(SearchDisplayActivity.this, SignInActivity.class);
//                startActivity(intent);
//                return true;
//
//            default:
//                return false;
//
//        }
//    }

    public String[][] createDummyList() {
        JSONArray persons = Globals.person_list;
        Integer persons_length = persons.length();
        String[][] person_list = new String[persons_length][4];
        for (Integer i = 0; persons.length() > i; i++) {
            try {
                person_list[i][0] = persons.getJSONObject(i).getString("user_id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                person_list[i][1] = persons.getJSONObject(i).getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                person_list[i][2] = persons.getJSONObject(i).getString("age");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                person_list[i][3] = persons.getJSONObject(i).getString("sex");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return person_list;
    }
}

