package at.tugraz.thedrunksailor.uitest;


import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;

import com.robotium.solo.Solo;

import at.tugraz.thedrunksailor.AddPlace_Fragment;
import at.tugraz.thedrunksailor.FragmentUtilActivity;
import at.tugraz.thedrunksailor.LastVisitedPlace_Fragment;
import at.tugraz.thedrunksailor.R;

public class AddPlaceFragmentTest extends ActivityInstrumentationTestCase2<FragmentUtilActivity> {

        private Solo mySolo;
        private AddPlace_Fragment addPlace_Fragment;

        public AddPlaceFragmentTest() {
            super("at.tugraz.thedrunksailor", FragmentUtilActivity.class);
        }

        @Override
        protected void setUp() throws Exception {
            super.setUp();
            addPlace_Fragment = new AddPlace_Fragment();
            getActivity().addFragment(addPlace_Fragment, AddPlace_Fragment.class.getSimpleName());
            getInstrumentation().waitForIdleSync();
            mySolo = new Solo(getInstrumentation(), getActivity());

        }

    public void testTextInput() throws InterruptedException {
        mySolo.enterText(0,"Pail");
        mySolo.enterText(1,"Inffeldgasse 11");
        mySolo.enterText(2,"8010");
        mySolo.enterText(3,"Graz");
        mySolo.enterText(4,"Österreich");
        mySolo.enterText(5,"Description...");
        mySolo.clickOnButton("Plus");
    }
}

