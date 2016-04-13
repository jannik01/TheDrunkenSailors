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
        
    }

}