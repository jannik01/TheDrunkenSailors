package at.tugraz.thedrunksailor;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

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
        setActivityInitialTouchMode(true);

        mySolo.sleep(2000);
        if(mySolo.searchText("ALLOW")){
            Log.e("test"," allow");
            mySolo.clickOnText("ALLOW");

        }

    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testMap_Fregment(){

       mySolo.sleep(4000);
       if(mySolo.searchText("Allow")){
           Log.e("test"," allow");
           mySolo.clickOnText("ALLOW");
           mySolo.clickOnButton("ALLOW");
           mySolo.clickOnButton("Allow");
           mySolo.clickOnButton("allow");

       }
        mySolo.sleep(2000);
        if(mySolo.searchText("ALLOW")){
            Log.e("test"," allow");
            mySolo.clickOnText("ALLOW");

        }


    }

    public  void testGetMyLocation(){

        mySolo.sleep(4000);

        if(mySolo.searchText("ALLOW")){
            mySolo.clickOnText("ALLOW");

        }
    }
}