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
        //mySolo.searchButton("Sign in");
        //mySolo.clickOnButton("Sign in");
        mySolo.clickOnText("UserName");
        mySolo.enterText(1, "asdf");
        // mySolo.clickOnText("Password");
        mySolo.enterText(0, "fghhj");
        mySolo.clickOnText("asdf");
        mySolo.enterText(1, "asdf");

        boolean actual = mySolo.searchText("asdf");

        assertEquals("Fail", true, actual);
        //mySolo.searchEditText("++++++++++++++++++");
    }





}