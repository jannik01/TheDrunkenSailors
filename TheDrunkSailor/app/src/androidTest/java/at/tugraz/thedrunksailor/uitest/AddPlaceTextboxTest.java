package at.tugraz.thedrunksailor.uitest;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import at.tugraz.thedrunksailor.ActivityAddPlace;

/**
 * Created by hueme on 04.05.2016.
 */

public class AddPlaceTextboxTest extends ActivityInstrumentationTestCase2<ActivityAddPlace> {

    public AddPlaceTextboxTest() {
        super(ActivityAddPlace.class);
    }
    private Solo mySolo;

    public void setUp() throws Exception {
        super.setUp();
        mySolo = new Solo(getInstrumentation(), getActivity());

    }


    public void testTextInput() throws InterruptedException {

        mySolo.enterText(0,"Franz");
        mySolo.enterText(1,"Wienerstraße 27");
        mySolo.enterText(2,"Graz");
        mySolo.enterText(3,"Sector");
        mySolo.enterText(4,"80200");
        mySolo.enterText(5,"Country");
        mySolo.enterText(6,"Description...");

        mySolo.clickOnImageButton(0);

        mySolo.clickOnButton("+");
        mySolo.sleep(5000);

    }
}