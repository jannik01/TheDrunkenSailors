package uitest;

import android.test.ActivityInstrumentationTestCase2;
import com.robotium.solo.Solo;
import at.tugraz.thedrunksailor.SignInActivity;

//import junit.framework.TestCase;

/**
 * Created by M0S3R on 13.04.2016.
 */
public class SignInActivityTest extends ActivityInstrumentationTestCase2<SignInActivity>
{

    private Solo mySolo;

    public SignInActivityTest()
    {
        super(SignInActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        mySolo = new Solo(getInstrumentation(), getActivity());

    }

    public void tearDown() throws Exception
    {
        super.tearDown();
    }

    public void testButtons()
    {
        mySolo.searchButton("New? Register!");
        mySolo.clickOnButton(1);
        mySolo.searchButton("Sign in");
        //mySolo.clickOnButton(0);
        //mySolo.clickOnButton("Sign in");
    }

    public void testInput()
    {

        mySolo.clickOnText("UserName");
        mySolo.enterText(1, "meinname");

        mySolo.enterText(0, "password");
        mySolo.clickOnText("name");
        mySolo.clickOnText("password");
        mySolo.enterText(0, "123");

        mySolo.clickOnButton("Sign in");

        boolean actual = mySolo.searchText("meinname");


        assertEquals("Fail", true, actual);
        //mySolo.searchEditText("++++++++++++++++++");
    }

    public void testInputFailUsername()
    {

        mySolo.clickOnText("UserName");
        mySolo.enterText(1, "name");

        mySolo.enterText(0, "password");
        mySolo.clickOnText("name");
        mySolo.enterText(1, "*_:.");
        mySolo.clickOnText("password");
        mySolo.enterText(0, "123");

        mySolo.clickOnButton("Sign in");

        boolean actual = mySolo.searchText("Please just use letters, numbers and underscore!");


        assertEquals("Fail", true, actual);
    }

    public void testInputFailUsernamelength()
    {

        mySolo.clickOnText("UserName");
        mySolo.enterText(1, "name");

        mySolo.enterText(0, "password");

        mySolo.clickOnButton("Sign in");

        boolean actual = mySolo.searchText("Please use a username between 5 & 25 characters!");


        assertEquals("Fail", true, actual);
    }

    public void testInputFailUsernameOneUnderscore()
    {

        mySolo.clickOnText("UserName");
        mySolo.enterText(1, "name____123");

        mySolo.enterText(0, "password");

        mySolo.clickOnButton("Sign in");

        boolean actual = mySolo.searchText("Please just use one underscore!");


        assertEquals("Fail", true, actual);
    }

    public void testInputFailUsernamebeginnUnderscore()
    {

        mySolo.clickOnText("UserName");
        mySolo.enterText(1, "_name00123");

        mySolo.enterText(0, "password");

        mySolo.clickOnButton("Sign in");

        boolean actual = mySolo.searchText("Please use underscore not at the beginning or end!");


        assertEquals("Fail", true, actual);
    }

    public void testInputFailUsernameEndUnderscore()
    {

        mySolo.clickOnText("UserName");
        mySolo.enterText(1, "name00123_");

        mySolo.enterText(0, "password");

        mySolo.clickOnButton("Sign in");

        boolean actual = mySolo.searchText("Please use underscore not at the beginning or end!");


        assertEquals("Fail", true, actual);
    }


    public void testInputFailPasswordlength()
    {

        mySolo.clickOnText("UserName");
        mySolo.enterText(1, "meinname");

        mySolo.enterText(0, "kurz");
        mySolo.clickOnText("name");

        mySolo.clickOnButton("Sign in");

        boolean actual = mySolo.searchText("Please use a password between 8 & 25 characters!");

        assertEquals("Fail", true, actual);
    }




}