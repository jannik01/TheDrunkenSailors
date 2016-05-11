package at.tugraz.thedrunksailor;


        import android.app.Fragment;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

/**
 * Created by Lissy on 11.05.2016.
 */
public class menu1_Fragment extends Fragment {
    View rootview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.menu1_layout, container, false);
        return rootview;
    }
}
