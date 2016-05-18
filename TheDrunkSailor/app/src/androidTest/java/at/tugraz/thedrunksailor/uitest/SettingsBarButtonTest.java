package at.tugraz.thedrunksailor.uitest;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import at.tugraz.thedrunksailor.FragmentManagerActivity;
import at.tugraz.thedrunksailor.R;

/**
 * Created by Sami on 18/05/2016.
 */
public class SettingsBarButtonTest extends ActivityInstrumentationTestCase2<FragmentManagerActivity> {
    private Solo mySolo;

    public SettingsBarButtonTest() {
        super(FragmentManagerActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        mySolo = new Solo(getInstrumentation(), getActivity());
    }

    public void tearDown() throws Exception {

    }

    public void testButtons() {
//        mySolo.clickOnButton("Settings");
        mySolo.clickOnView(getActivity().findViewById(R.id.section_label));
    }
}
