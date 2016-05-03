package at.tugraz.thedrunksailor;

import android.provider.Settings;
import android.test.ActivityInstrumentationTestCase2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.robotium.solo.Solo;

import static org.junit.Assert.*;

/**
 * Created by robert on 20.04.2016.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityTest() {
        super(MainActivity.class);
    }
    private Solo mySolo;

    public void setUp() throws Exception {
        super.setUp();
        mySolo = new Solo(getInstrumentation(), getActivity());


    }

    public void tearDown() throws Exception {
        super.tearDown();
    }


    @Test
    public void testButtons() {
        mySolo.clickOnButton("Search");

    }


//    public void testTextFields() {
//
//
//        mySolo.clickOnEditText(0);
//
//        String strInput = "Sample code 1";
//
//        mySolo.enterText(0, strInput);
//
//        boolean actual = mySolo.searchEditText(strInput);
//
//        assertEquals("text entered is not matching", true, actual);
//
//
//        mySolo.clearEditText(0);
//
//
//        strInput = "Sample code 2";
//        mySolo.typeText(0, strInput);
//        actual = mySolo.searchEditText(strInput);
//
//        assertEquals("text entered is not matching", true, actual);
//
//    }

    public void testWrongPassword() throws InterruptedException {

        mySolo.enterText(1,"name");
        mySolo.enterText(2,"8020");
        mySolo.enterText(3,"place");

        mySolo.clickOnButton("Search");

    }

    public void testRatingBar() throws InterruptedException {

        mySolo.clickOnActionBarItem(1);
        mySolo.clickOnButton("Search");

    }


}