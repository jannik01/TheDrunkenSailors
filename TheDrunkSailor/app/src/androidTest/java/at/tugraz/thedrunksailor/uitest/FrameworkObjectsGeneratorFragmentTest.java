package at.tugraz.thedrunksailor.uitest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.test.ActivityInstrumentationTestCase2;

import at.tugraz.thedrunksailor.AddPlace_Fragment;
import at.tugraz.thedrunksailor.FragmentManagerActivity;
import at.tugraz.thedrunksailor.R;
import at.tugraz.thedrunksailor.TestFragmentActivity;

/**
 * Created by Lissy on 11.05.2016.
 */
public class FrameworkObjectsGeneratorFragmentTest
        extends ActivityInstrumentationTestCase2<FragmentManagerActivity> {
    private FragmentManagerActivity mActivity;

    public FrameworkObjectsGeneratorFragmentTest() {
        super(FragmentManagerActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = getActivity();
    }

    private Fragment startFragment(Fragment fragment) {
        FragmentTransaction transaction = mActivity.getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.activity_test_fragment_linearlayout, fragment, "tag");
        transaction.commit();
        getInstrumentation().waitForIdleSync();
        Fragment frag = mActivity.getSupportFragmentManager().findFragmentByTag("tag");
        return frag;
    }

    public void testFragment() {
        AddPlace_Fragment fragment = new AddPlace_Fragment() {
            //Override methods and add assertations here.
        };

        Fragment frag = startFragment(fragment);
    }
}
