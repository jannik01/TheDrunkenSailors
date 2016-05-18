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

    public void testButtonsWelcome() {
        mySolo.clickOnButton("Welcome XY");
    }

    public void testButtonsSearchPlace() {
        mySolo.clickOnButton("Search place");
    }

    public void testButtonsSearchPerson() {
        mySolo.clickOnButton("Search Person");
    }

    public void testButtonsAddPlace() {
        mySolo.clickOnButton("Add place");
    }

}