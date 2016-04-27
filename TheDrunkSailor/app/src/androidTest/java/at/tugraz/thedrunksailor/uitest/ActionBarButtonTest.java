package at.tugraz.thedrunksailor.uitest;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import junit.framework.TestCase;

import at.tugraz.thedrunksailor.MainActivity;
import at.tugraz.thedrunksailor.R;

/**
 * Created by Sami on 13/04/2016.
 */
public class ActionBarButtonTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo mySolo;

    public ActionBarButtonTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        mySolo = new Solo(getInstrumentation(), getActivity());
    }

    public void tearDown() throws Exception {

    }

    public void testButtons() {

        mySolo.clickOnView(getActivity().findViewById(R.id.logout));
        mySolo.clickOnView(getActivity().findViewById(R.id.logout));
        mySolo.clickOnView(getActivity().findViewById(R.id.logout));
        mySolo.clickOnView(getActivity().findViewById(R.id.logout));

    }
}