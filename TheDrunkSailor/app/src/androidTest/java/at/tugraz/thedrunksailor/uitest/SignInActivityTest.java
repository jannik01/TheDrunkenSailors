package at.tugraz.thedrunksailor.uitest;

import android.test.ActivityInstrumentationTestCase2;
import com.robotium.solo.Solo;

import at.tugraz.thedrunksailor.Globals;
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
       // mySolo.clickOnText("UserName");
        mySolo.enterText(1, "testuser1");

        mySolo.enterText(0, "pass");
        //mySolo.clickOnText("name");
       // mySolo.clickOnText("password");
        mySolo.enterText(0, "word");

        mySolo.clickOnButton("Sign in");

        mySolo.sleep(500);
        assertNotSame("Fail",0,Globals.uid);
        //mySolo.searchEditText("++++++++++++++++++");
    }






}