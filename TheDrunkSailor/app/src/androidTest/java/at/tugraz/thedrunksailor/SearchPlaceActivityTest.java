package at.tugraz.thedrunksailor;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;
import com.robotium.solo.Solo;

/**
 * Created by robert on 20.04.2016.
 */
public class SearchPlaceActivityTest extends ActivityInstrumentationTestCase2<FragmentManagerActivity> {

    public SearchPlaceActivityTest() {
        super(FragmentManagerActivity.class);
    }
    private Solo mySolo;

    public void setUp() throws Exception {
        super.setUp();
        mySolo = new Solo(getInstrumentation(), getActivity());

    }

    public void tearDown() throws Exception {
        super.tearDown();
    }


    @Test
    public void testButtons() throws InterruptedException {

        mySolo.clickOnButton("Find");
        mySolo.sleep(5000);

        // TODO testDropdownList - Sector

    }

    public void testTextInput() throws InterruptedException {

        mySolo.enterText(1,"name");
        mySolo.enterText(2,"8020");
        mySolo.enterText(3,"place");

        mySolo.clickOnButton("Find");
        mySolo.sleep(5000);

    }

    public void testRatingBars() throws InterruptedException {

        mySolo.setProgressBar(0, 1);
        mySolo.sleep(3000);
        mySolo.setProgressBar(1, 2);
        mySolo.sleep(3000);
        mySolo.setProgressBar(2, 5);
        mySolo.sleep(3000);
        mySolo.setProgressBar(3, 4);
        mySolo.sleep(3000);

        mySolo.clickOnButton("Find");
        mySolo.sleep(5000);
    }

}