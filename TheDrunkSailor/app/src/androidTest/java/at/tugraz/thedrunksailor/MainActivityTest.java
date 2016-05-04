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
    public void testButtons() throws InterruptedException {

        mySolo.clickOnButton("Find");
        mySolo.sleep(5000);

        // TODO testDropdownList - Sector

    }

    public void testTextInput() throws InterruptedException {

        mySolo.enterText(1,"name");
        mySolo.enterText(2,"8020");
        mySolo.enterText(3,"place");

        mySolo.clickOnButton("Find");
        mySolo.sleep(5000);

    }

    public void testRatingBars() throws InterruptedException {

        mySolo.setProgressBar(0, 1);
        mySolo.sleep(3000);
        mySolo.setProgressBar(1, 2);
        mySolo.sleep(3000);
        mySolo.setProgressBar(2, 5);
        mySolo.sleep(3000);
        mySolo.setProgressBar(3, 4);
        mySolo.sleep(3000);

        mySolo.clickOnButton("Find");
        mySolo.sleep(5000);
    }

}