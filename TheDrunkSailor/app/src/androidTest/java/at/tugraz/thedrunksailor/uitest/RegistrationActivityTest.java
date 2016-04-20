package at.tugraz.thedrunksailor.uitest;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import junit.framework.TestCase;

import at.tugraz.thedrunksailor.RegistrationActivity;

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

    public void testWrongPassword() throws InterruptedException {
        mySolo.enterText(0,"username");
        mySolo.enterText(1,"pwd");
        mySolo.enterText(2,"pwd1");
        mySolo.enterText(3,"name");

        mySolo.clickOnButton("Register");

        mySolo.clickOnButton("OK");

    }


    public void testMissingField() throws InterruptedException {
        mySolo.enterText(0,"username");
        mySolo.enterText(1,"pwd");
        mySolo.enterText(2,"pwd");

        mySolo.clickOnButton("Register");

        mySolo.clickOnButton("OK");

    }


    public void testMissingFieldAndWrongPassword() throws InterruptedException {
        mySolo.enterText(0,"username");
        mySolo.enterText(1,"pwd");
        mySolo.enterText(2,"pwd1");

        mySolo.clickOnButton("Register");
        mySolo.clickOnButton("OK");
        mySolo.clickOnButton("OK");

    }


    public void testEnterAllFieldsCorrect() {
        mySolo.enterText(0,"username");
        mySolo.enterText(1,"pwd");
        mySolo.enterText(2,"pwd");
        mySolo.enterText(3,"name");
        mySolo.clickOnRadioButton(1);
        mySolo.enterText(4, "34");
        mySolo.enterText(5, "none");

        mySolo.clickOnButton("Register");

    }

}