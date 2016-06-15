package at.tugraz.thedrunksailor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by Lissy on 11.05.2016.
 */

public class FragmentUtilActivity extends FragmentActivity {
    private static final int CONTAINER_ID = 2;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout.LayoutParams params =
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setId(CONTAINER_ID);
        setContentView(frameLayout, params);
    }

    public void addFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
            .add(CONTAINER_ID, fragment, tag)
            .commit();
    }
}