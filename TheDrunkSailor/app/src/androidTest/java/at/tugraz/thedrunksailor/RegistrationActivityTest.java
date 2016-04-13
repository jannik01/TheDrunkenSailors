package at.tugraz.thedrunksailor;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import junit.framework.TestCase;

/**
 * Created by Clemens on 13.04.2016.
 */
public class RegistrationActivityTest extends ActivityInstrumentationTestCase2<RegistrationActivity> {


    private Solo mySolo;

    public RegistrationActivityTest() {
        super(RegistrationActivity.class);
    }


    public void setUp() throws Exception {
        super.setUp();
        mySolo = new Solo(getInstrumentation(), getActivity());

    }

    public void tearDown() throws Exception {
        super.tearDown();
    }


    public void testButtons() {
        mySolo.clickOnButton("Register");

    }

    public void testTextFields() {


        mySolo.clickOnEditText(0);

        String strInput = "Sample code 1";

        mySolo.enterText(0, strInput);

        boolean actual = mySolo.searchEditText(strInput);

        assertEquals("text entered is not matching", true, actual);



        mySolo.clearEditText(0);


        strInput = "Sample code 2";
        mySolo.typeText(0, strInput);
        actual = mySolo.searchEditText(strInput);

        assertEquals("text entered is not matching", true, actual);

    }


    public void testRadioButtons() {

        mySolo.clickOnRadioButton(1);
        mySolo.clickOnRadioButton(0);

    }

}