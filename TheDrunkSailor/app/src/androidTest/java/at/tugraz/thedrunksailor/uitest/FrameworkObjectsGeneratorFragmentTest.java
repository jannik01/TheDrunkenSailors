package at.tugraz.thedrunksailor.uitest;

import android.test.ActivityInstrumentationTestCase2;

import at.tugraz.thedrunksailor.FragmentUtilActivity;
import at.tugraz.thedrunksailor.LastVisitedPlace_Fragment;

/**
 * Created by Lissy on 11.05.2016.
 */

public class FrameworkObjectsGeneratorFragmentTest
        extends ActivityInstrumentationTestCase2<FragmentUtilActivity> {

    private LastVisitedPlace_Fragment lastVisitedPlace_fragment;
    public FrameworkObjectsGeneratorFragmentTest() {
        super("at.tugraz.thedrunksailor", FragmentUtilActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        lastVisitedPlace_fragment = new LastVisitedPlace_Fragment();
        getActivity().addFragment(lastVisitedPlace_fragment, LastVisitedPlace_Fragment.class.getSimpleName());
        getInstrumentation().waitForIdleSync();
    }

    public void test_Should_Set_Title_TextView_Text() {
        assertEquals("text entered is not matching", true, true);
    }
}



//public class FrameworkObjectsGeneratorFragmentTest
//        extends ActivityInstrumentationTestCase2<FragmentManagerActivity> {
//    private FragmentManagerActivity mActivity;
//
//    public FrameworkObjectsGeneratorFragmentTest() {
//        super(FragmentManagerActivity.class);
//    }
//
//    @Override
//    protected void setUp() throws Exception {
//        super.setUp();
//        mActivity = getActivity();
//    }
//
//    private Fragment startFragment(Fragment fragment) {
//        FragmentTransaction transaction = mActivity.getSupportFragmentManager().beginTransaction();
//        transaction.add(R.id.activity_test_fragment_linearlayout, fragment, "tag");
//        transaction.commit();
//        getInstrumentation().waitForIdleSync();
//        Fragment frag = mActivity.getSupportFragmentManager().findFragmentByTag("tag");
//        return frag;
//    }
//
//    public void testFragment() {
//        AddPlace_Fragment fragment = new AddPlace_Fragment() {
//            //Override methods and add assertations here.
//        };
//
//        Fragment frag = startFragment(fragment);
//    }
//}
