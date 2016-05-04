package at.tugraz.thedrunksailor.uitest;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import junit.framework.TestCase;

import at.tugraz.thedrunksailor.MainActivity;

/**
 * Created by Sami on 13/04/2016.
 */
public class MenuButtonTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo mySolo;

    public MenuButtonTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        mySolo = new Solo(getInstrumentation(), getActivity());
    }

    public void tearDown() throws Exception {

    }

    public void testButtons() {
        mySolo.clickOnButton("Welcome XY");
        mySolo.clickOnButton("Search place");
        mySolo.clickOnButton("Search Person");
        mySolo.clickOnButton("Add place");
        mySolo.clickOnButton("Add place");
    }
}