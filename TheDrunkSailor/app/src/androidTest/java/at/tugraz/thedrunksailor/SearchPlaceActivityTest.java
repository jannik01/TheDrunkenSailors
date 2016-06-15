package at.tugraz.thedrunksailor;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;
import com.robotium.solo.Solo;

/**
 * Created by robert on 20.04.2016.
 */
public class SearchPlaceActivityTest extends ActivityInstrumentationTestCase2<FragmentUtilActivity> {

    private Solo mySolo;
    private SearchPlace_Fragment searchPlace_Fragment;
    public SearchPlaceActivityTest() {
        super("at.tugraz.thedrunksailor", FragmentUtilActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        searchPlace_Fragment = new SearchPlace_Fragment();
        getActivity().addFragment(searchPlace_Fragment, SearchPlace_Fragment.class.getSimpleName());
        getInstrumentation().waitForIdleSync();
        mySolo = new Solo(getInstrumentation(), getActivity());

    }

    public void tearDown() throws Exception {
        super.tearDown();
    }


    @Test
    public void testButtons() throws InterruptedException {
        mySolo.clickOnButton("Find");
    }

    public void testTextInput() throws InterruptedException {
        mySolo.enterText(0,"name");
        mySolo.enterText(1,"8020");
        mySolo.enterText(2,"place");
        mySolo.clickOnButton("Find");
    }

    public void testRatingBars() throws InterruptedException {
        mySolo.setProgressBar(0, 1);
        mySolo.sleep(500);
        mySolo.setProgressBar(1, 2);
        mySolo.sleep(500);
        mySolo.setProgressBar(2, 5);
        mySolo.sleep(500);
        mySolo.setProgressBar(3, 4);
        mySolo.sleep(500);
        mySolo.clickOnButton("Find");
        mySolo.sleep(500);
    }

    public void testSearchDisplayActivity()
    {
        mySolo.enterText(1,"8010");
        mySolo.clickOnButton("Find");
        mySolo.sleep(2000);
        assertTrue("Error Search Display Activity ", Globals.place_list != null);
        mySolo.clickInList(0);
        mySolo.sleep(2000);
    }

}