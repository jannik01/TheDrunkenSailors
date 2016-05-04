package test;

import android.test.ActivityInstrumentationTestCase2;
import com.robotium.solo.Solo;
import at.tugraz.thedrunksailor.SignInActivity;

//import junit.framework.TestCase;

/**
 * Created by M0S3R on 13.04.2016.
 */
public class SignInLogicTest extends ActivityInstrumentationTestCase2<SignInActivity>
{

    private Solo mySolo;

    public SignInLogicTest()
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



    public void testNoNameAndNoPW()
    {
        mySolo.clickOnButton("Sign in");
        mySolo.sleep(3000);
        mySolo.clickOnButton(0);
        mySolo.sleep(3000);
        mySolo.clickOnButton(0);
    }

    public void testWrongName()
    {
        mySolo.sleep(10000);
        mySolo.enterText(1, "aes&fzg");
        mySolo.enterText(0, "asdfqwredfg");
        mySolo.sleep(5000);
        mySolo.clickOnButton("Sign in");
        mySolo.sleep(5000);
        mySolo.clickOnButton(0);
        mySolo.clearEditText(1);
        mySolo.enterText(1, "asfzzsu_");
        mySolo.sleep(5000);
        mySolo.clickOnButton("Sign in");
        mySolo.sleep(5000);
        mySolo.clickOnButton(0);
        mySolo.clearEditText(1);
        mySolo.enterText(1, "asf_sdfz_zsu");
        mySolo.sleep(5000);
        mySolo.clickOnButton("Sign in");
        mySolo.sleep(5000);
    }

    public void testTooShortName()
    {
        mySolo.sleep(10000);
        mySolo.enterText(1, "asfz");
        mySolo.enterText(0, "asdfqwredfg");
        mySolo.sleep(5000);
        mySolo.clickOnButton("Sign in");
        mySolo.sleep(5000);

    }

    public void testTooShortPW()
    {
        mySolo.sleep(10000);
        mySolo.enterText(1, "sdkflgwertz");
        mySolo.enterText(0, "qwre");
        mySolo.sleep(5000);
        mySolo.clickOnButton("Sign in");
        mySolo.sleep(5000);
    }

    public void testUnderscore()
    {
        mySolo.sleep(10000);
        mySolo.enterText(1, "qwre_laskdfj");
        mySolo.enterText(0, "asdfuzdsfg");
        mySolo.sleep(5000);
        mySolo.clickOnButton("Sign in");
        mySolo.sleep(5000);
    }
}