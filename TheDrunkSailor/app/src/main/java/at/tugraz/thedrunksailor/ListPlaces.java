package at.tugraz.thedrunksailor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TableLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sami on 18/04/2016.
 */
public class ListPlaces extends AppCompatActivity{

    private List<Place> list_places;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_places_main);
        list_places = getPlacesFromDB();
        TableLayout tl = (TableLayout) findViewById(R.id.list_places_table);
        for(final Place x: list_places) {
            //ADD ROW CONTENT HERE
        }
    }

    private List<Place> getPlacesFromDB () {
        //TO BE CHANGED ONCE THE DATABASE IS ONLINE
        Place tmp = new Place("Place 1", 3.4, 7);
        List<Place> returnPlaces = new ArrayList<Place>();
        returnPlaces.add(tmp);
        tmp = new Place("Place 2", 6.5, 2.2);
        returnPlaces.add(tmp);
        tmp = new Place("Place 3", 6.1, 2.2);
        returnPlaces.add(tmp);
        return returnPlaces;
    }

}
