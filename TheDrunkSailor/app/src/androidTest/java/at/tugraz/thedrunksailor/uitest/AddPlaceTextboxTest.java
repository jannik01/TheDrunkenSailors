package at.tugraz.thedrunksailor.uitest;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import at.tugraz.thedrunksailor.AddPlace_Fragment;
import at.tugraz.thedrunksailor.FragmentManagerActivity;

/**
 * Created by hueme on 04.05.2016.
 */

public class AddPlaceTextboxTest extends ActivityInstrumentationTestCase2<FragmentManagerActivity> {

    private AddPlace_Fragment fragment;
    public AddPlaceTextboxTest() {
        super(FragmentManagerActivity.class);
    }
    private Solo mySolo;
    
    public void setUp() throws Exception {

        super.setUp();
        mySolo = new Solo(getInstrumentation(), getActivity());

    }


    public void testTextInput() throws InterruptedException {

        mySolo.enterText(0,"Franz");

        mySolo.enterText(1,"Wienerstraße 27");
        mySolo.enterText(2,"8010");
        mySolo.enterText(3,"Graz");
        mySolo.enterText(4,"Country");
        mySolo.enterText(5,"Description...");



        //mySolo.clickOnButton("+");

    }
}