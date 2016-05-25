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
        mySolo.enterText(1,"pwdqwert");
        mySolo.enterText(2,"pwdqwert1");
        mySolo.enterText(3,"name");

        mySolo.clickOnButton("Register");


    }


    public void testMissingField() throws InterruptedException {
        mySolo.enterText(0,"username");
        mySolo.enterText(1,"pwdpwdpwd");
        mySolo.enterText(2,"pwdpwdpwd");

        mySolo.clickOnButton("Register");
        mySolo.sleep(1000);

    }


    public void testMissingFieldAndWrongPassword() throws InterruptedException {
        mySolo.enterText(0,"username");
        mySolo.enterText(1,"pwdpwdpwd");
        mySolo.enterText(2,"pwdpwdpwd1");

        mySolo.clickOnButton("Register");


    }


    public void testEnterAllFieldsCorrect() {
        mySolo.enterText(0,"username");
        mySolo.enterText(1,"pwdqwert");
        mySolo.enterText(2,"pwdqwert");
        mySolo.enterText(3,"name");
        mySolo.clickOnRadioButton(1);
        mySolo.enterText(4, "34");
        mySolo.enterText(5, "none");

        mySolo.clickOnButton("Register");

    }

    public void testInputFailUsername()
    {

        mySolo.enterText(0,"asd%&f");
        mySolo.enterText(1,"pwdpwdpwd");
        mySolo.enterText(2,"pwdpwdpwd");
        mySolo.enterText(3,"name");

        mySolo.clickOnButton("Register");

        boolean actual = mySolo.searchText("Please just use letters, numbers and underscore!");


        assertEquals("Fail", true, actual);
    }

    public void testInputFailUsernamelength()
    {
        mySolo.enterText(0, "name");

        mySolo.enterText(1, "password");
        mySolo.enterText(2, "password");
        mySolo.enterText(3, "name");

        mySolo.clickOnButton("Register");

        boolean actual = mySolo.searchText("Please use a username between 5 & 25 characters!");

        assertEquals("Fail", true, actual);
    }

    public void testInputFailUsernameOneUnderscore()
    {
        mySolo.enterText(0, "name____123");

        mySolo.enterText(1, "password");
        mySolo.enterText(2, "password");
        mySolo.enterText(3, "name");

        mySolo.clickOnButton("Register");

        boolean actual = mySolo.searchText("Please just use one underscore!");


        assertEquals("Fail", true, actual);
    }

    public void testInputFailUsernamebeginnUnderscore()
    {
        mySolo.enterText(0, "_name00123");
        mySolo.enterText(1, "password");
        mySolo.enterText(2, "password");
        mySolo.enterText(3, "name");

        mySolo.clickOnButton("Register");

        boolean actual = mySolo.searchText("Please use underscore not at the beginning or end!");


        assertEquals("Fail", true, actual);
    }

    public void testInputFailUsernameEndUnderscore()
    {
        mySolo.enterText(0, "name00123_");
        mySolo.enterText(1, "password");
        mySolo.enterText(2, "password");
        mySolo.enterText(3, "name");

        mySolo.clickOnButton("Register");

        boolean actual = mySolo.searchText("Please use underscore not at the beginning or end!");

        assertEquals("Fail", true, actual);
    }


    public void testInputFailPasswordlength()
    {
        mySolo.enterText(0, "meinname");
        mySolo.enterText(1, "kurz");
        mySolo.enterText(2, "kurz");
        mySolo.enterText(3, "name");

        mySolo.clickOnButton("Register");

        boolean actual = mySolo.searchText("Please use a password between 8 & 25 characters!");

        assertEquals("Fail", true, actual);
    }

}