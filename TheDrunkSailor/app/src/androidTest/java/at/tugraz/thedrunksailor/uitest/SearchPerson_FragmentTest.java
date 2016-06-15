package at.tugraz.thedrunksailor.uitest;


import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;

import at.tugraz.thedrunksailor.FragmentUtilActivity;
import at.tugraz.thedrunksailor.Globals;
import at.tugraz.thedrunksailor.SearchPerson_Fragment;

import static org.junit.Assert.*;

/**
 * Created by Lissy on 11.06.2016.
 */
public class SearchPerson_FragmentTest extends ActivityInstrumentationTestCase2<FragmentUtilActivity> {

    private Solo mySolo;
    private SearchPerson_Fragment searchPerson_Fragment;
    public SearchPerson_FragmentTest() {
        super("at.tugraz.thedrunksailor", FragmentUtilActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        searchPerson_Fragment = new SearchPerson_Fragment();
        getActivity().addFragment(searchPerson_Fragment, SearchPerson_Fragment.class.getSimpleName());
        getInstrumentation().waitForIdleSync();
        mySolo = new Solo(getInstrumentation(), getActivity());

    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testSearchPerson_Fragment(){
        mySolo.sleep(2000);
    }

    public void testPersonSearchDisplayActivity()
    {
        mySolo.clickOnButton("Find");
        mySolo.sleep(2000);
        assertTrue("Error Search Display Activity ", Globals.person_list != null);
        mySolo.clickInList(0);
        mySolo.sleep(2000);
    }
}