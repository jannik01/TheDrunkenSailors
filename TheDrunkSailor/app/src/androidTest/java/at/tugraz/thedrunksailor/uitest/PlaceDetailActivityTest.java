package at.tugraz.thedrunksailor.uitest;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import at.tugraz.thedrunksailor.PlaceDetailActivity;
import at.tugraz.thedrunksailor.RegistrationActivity;

/**
 * Created by Clemens on 13.04.2016.
 */
public class PlaceDetailActivityTest extends ActivityInstrumentationTestCase2<PlaceDetailActivity> {


    private Solo mySolo;

    public PlaceDetailActivityTest() {
        super(PlaceDetailActivity.class);
    }


    public void setUp() throws Exception {
        super.setUp();
        mySolo = new Solo(getInstrumentation(), getActivity());
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }


    public void testSeekBars() {
        mySolo.setProgressBar(1, 3);
        mySolo.setProgressBar(0, 2);

    }


}