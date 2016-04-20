package test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

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

    public void testLogic()
    {
        Button asdf = mySolo.getButton("Sign in");
       // asdf.onCli

    }





}