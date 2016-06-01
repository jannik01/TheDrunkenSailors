package at.tugraz.thedrunksailor.uitest;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import org.junit.Test;

import at.tugraz.thedrunksailor.PersonDetailActivity;

/**
 * Created by M0S3R on 01.06.2016.
 */
public class PersonDetailActivityTest extends ActivityInstrumentationTestCase2<PersonDetailActivity>
{

    private Solo mySolo;

    public PersonDetailActivityTest() {
        super(PersonDetailActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        mySolo = new Solo(getInstrumentation(), getActivity());
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void FollowButton() throws InterruptedException
    {
        mySolo.clickOnButton("button2");
        //mySolo.clickOnButton("Unfollow");

    }
}