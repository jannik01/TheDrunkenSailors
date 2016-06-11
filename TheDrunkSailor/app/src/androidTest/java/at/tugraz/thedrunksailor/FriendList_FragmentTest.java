package at.tugraz.thedrunksailor;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.*;

/**
 * Created by Lissy on 11.06.2016.
 */
public class FriendList_FragmentTest extends ActivityInstrumentationTestCase2<FragmentUtilActivity> {

    private Solo mySolo;
    private FriendList_Fragment my_friend_fragment;

    public FriendList_FragmentTest(){
        super("at.tugraz.thedrunksailor", FragmentUtilActivity.class);
    }

    @Before
    public void setUp() throws Exception {
            super.setUp();
            my_friend_fragment = new FriendList_Fragment();
            getActivity().addFragment(my_friend_fragment,FriendList_Fragment.class.getSimpleName());
            getInstrumentation().waitForIdleSync();
            mySolo = new Solo(getInstrumentation(), getActivity());
    }

    @After
    public void tearDown() throws Exception {

    }

    public void testFriendList_Fragment(){
        mySolo.sleep(2000);
    }

}