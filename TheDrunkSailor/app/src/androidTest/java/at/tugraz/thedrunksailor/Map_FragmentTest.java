package at.tugraz.thedrunksailor;

import android.test.ActivityInstrumentationTestCase2;

import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.*;

import com.robotium.solo.Solo;



/**
 * Created by robert on 08.06.2016.
 */
public class Map_FragmentTest extends ActivityInstrumentationTestCase2<FragmentUtilActivity> {


    private Solo mySolo;
    private Map_Fragment my_map_fragment;

    public Map_FragmentTest() {
        super("at.tugraz.thedrunksailor", FragmentUtilActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        my_map_fragment = new Map_Fragment();
        getActivity().addFragment(my_map_fragment,Map_Fragment.class.getSimpleName());
        getInstrumentation().waitForIdleSync();
        mySolo = new Solo(getInstrumentation(), getActivity());
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testMap_Fregment(){
        mySolo.sleep(2000);
    }
}