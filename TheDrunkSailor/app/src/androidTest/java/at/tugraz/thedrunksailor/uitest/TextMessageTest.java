package at.tugraz.thedrunksailor.uitest;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

import at.tugraz.thedrunksailor.MainActivity;

import junit.framework.TestCase;

/**
 * Created by hueme on 13.04.2016.
 */
public class TextMessageTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo mySolo;

    public TextMessageTest(){
        super(MainActivity.class);
    }


    public void setUp() throws Exception {
        super.setUp();
        mySolo = new Solo(getInstrumentation(), getActivity());
    }

    public void tearDown() throws Exception {

    }

    public void testTextfields() {
        mySolo.clickOnEditText(0);
        EditText impressum_test = mySolo.getEditText("The Drunken Sailors 2016TM");
        assertNotNull(impressum_test);
    }
}