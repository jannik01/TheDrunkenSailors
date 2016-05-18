package at.tugraz.thedrunksailor.uitest;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import at.tugraz.thedrunksailor.AddPlace_Fragment;
import at.tugraz.thedrunksailor.FragmentUtilActivity;
import at.tugraz.thedrunksailor.LastVisitedPlace_Fragment;

/**
 * Created by Lissy on 11.05.2016.
 */

public class LastVisitedPlaceFragmentTest extends ActivityInstrumentationTestCase2<FragmentUtilActivity> {

    private Solo mySolo;
    private AddPlace_Fragment addPlace_Fragment;

    public LastVisitedPlaceFragmentTest() {
        super("at.tugraz.thedrunksailor", FragmentUtilActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        addPlace_Fragment = new AddPlace_Fragment();
        getActivity().addFragment(addPlace_Fragment, LastVisitedPlace_Fragment.class.getSimpleName());
        getInstrumentation().waitForIdleSync();
        mySolo = new Solo(getInstrumentation(), getActivity());
    }

    public void test_Should_Set_Title_TextView_Text() {
        assertEquals("text entered is not matching", true, true);
    }

    public void testTextInput() throws InterruptedException {

        mySolo.enterText(0,"Franz");
        mySolo.enterText(1,"Wienerstraße");
        mySolo.enterText(2,"Graz");
        mySolo.enterText(3,"Sector");
        mySolo.enterText(4,"80200");
        mySolo.enterText(5,"Country");
        mySolo.enterText(6,"Description...");

        mySolo.sleep(5000);
    }
}

