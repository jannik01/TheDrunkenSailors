package at.tugraz.thedrunksailor.uitest;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import at.tugraz.thedrunksailor.MainActivity;

import junit.framework.TestCase;

/**
 * Created by hueme on 13.04.2016.
 */
public class TextMessageTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo mySolo;

    public TextMessageTest(){
        super(MainActivity.class);
    }


    public void setUp() throws Exception {
        super.setUp();
        mySolo = new Solo(getInstrumentation(), getActivity());
    }

    public void tearDown() throws Exception {

    }

    //testcases always returns true, use a other funktion!
    public void testTextfields() {
        mySolo.clickOnEditText(0);
        String strInput = "test";
        mySolo.enterText(0, strInput);
        boolean actual = mySolo.searchEditText(strInput);
        assertEquals("text entered is not matching", false, actual);
    }
}