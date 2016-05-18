package at.tugraz.thedrunksailor.uitest;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;

import com.robotium.solo.Solo;

import at.tugraz.thedrunksailor.FragmentManagerActivity;
import at.tugraz.thedrunksailor.R;

/**
 * Created by hueme on 27.04.2016.
 */
public class ListViewTest extends ActivityInstrumentationTestCase2<FragmentManagerActivity> {

    private Solo mySolo;

    public ListViewTest() {
        super(FragmentManagerActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        mySolo = new Solo(getInstrumentation(), getActivity());
    }

    public void tearDown() throws Exception {

    }

    public void testListClick() {
        mySolo.clickInList(1);
        mySolo.clickInList(1);
    }
}