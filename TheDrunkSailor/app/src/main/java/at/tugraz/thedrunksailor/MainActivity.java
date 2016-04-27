package at.tugraz.thedrunksailor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView listview = (ListView) findViewById(R.id.listview);

        if (listview != null) {
            listview.setAdapter(new PlaceItemAdapter(this, createDummyList()));

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    view.getTag();

                }
            });

        }


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    public String[][] createDummyList() {

        String[][] place = {
            {"1", "Place1", "3", "5"},
            {"2", "Place2", "3", "3"},
            {"3", "Place3", "5", "2.3"},
            {"4", "Place4", "1", "2.7"},
            {"5", "Place5", "3", "4"},
            {"6", "Place6", "2", "1.2"},
            {"7", "Place7", "3", "3"},
            {"15", "Place8", "5", "2.3"},
            {"9", "Place9", "1", "2.7"},
            {"10", "Place10", "3", "4"},
            {"11", "Place11", "2", "1.2"},
        };

        return place;

    }

    public void makeDummyEntries() {
        Place tmp = new Place("Place 1", 3.4, 7);
        List<Place> returnPlaces = new ArrayList<Place>();
        returnPlaces.add(tmp);
        tmp = new Place("Place 2", 6.5, 2.2);
        returnPlaces.add(tmp);
        tmp = new Place("Place 3", 6.1, 2.2);
        returnPlaces.add(tmp);

        TableLayout tl = (TableLayout) findViewById(R.id.list_places_table);
        for (int i = 0; i < returnPlaces.size(); i++) {
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            for (int j = 0; j < 4; j++) {

                TextView tv = new TextView(this);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                //tv.setBackgroundResource(R.drawable.cell_shape);
                tv.setPadding(5, 5, 5, 5);
                String tmp_string = new String();
                switch (j) {
                    case 0:
                        break;
                    case 1:
                        tmp_string = returnPlaces.get(i).getName();
                        break;
                    case 2:
                        tmp_string = Double.toString(returnPlaces.get(i).getCurrent_capacity());
                        break;
                    case 3:
                        tmp_string = Double.toString(returnPlaces.get(i).getUser_note());
                        break;
                }
                tv.setText(tmp_string);

                row.addView(tv);

            }
            tl.addView(row, i);
        }
    }
}