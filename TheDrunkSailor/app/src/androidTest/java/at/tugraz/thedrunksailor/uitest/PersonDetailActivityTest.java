package at.tugraz.thedrunksailor.uitest;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.robotium.solo.Solo;

import junit.framework.TestCase;

import at.tugraz.thedrunksailor.PersonDetailActivity;

/**
 * Created by Strw on 01.06.16.
 */
public class PersonDetailActivityTest extends ActivityInstrumentationTestCase2<PersonDetailActivity> {

    private Solo mySolo;

    public PersonDetailActivityTest() {
        super(PersonDetailActivity.class);
    }


    public void setUp() throws Exception {
        super.setUp();
        mySolo = new Solo(getInstrumentation(), getActivity());
    }

    public void tearDown() throws Exception {
        mySolo.finishOpenedActivities();
        super.tearDown();
    }

    public void testFollowPerson(){
        if(mySolo.getButton(0).getText().toString().equals("follow")) {
            mySolo.sleep(1000);
            mySolo.clickOnButton("follow");
            mySolo.sleep(2000);
            assertEquals("unfollow", mySolo.getButton(0).getText().toString());
        }
    }

    public void testUnFollowPerson(){
        if(mySolo.getButton(0).getText().toString().equals("unfollow")) {
            mySolo.sleep(1000);
            mySolo.clickOnButton("unfollow");
            mySolo.sleep(2000);
            assertEquals("follow", mySolo.getButton(0).getText().toString());
        }

    }

    public void testLastPlacesTable(){
        mySolo.clickInList(0,0);
        mySolo.sleep(2000);

    }





}