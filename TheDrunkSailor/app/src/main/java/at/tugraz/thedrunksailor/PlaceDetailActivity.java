package at.tugraz.thedrunksailor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class PlaceDetailActivity extends AppCompatActivity {

    public static final int MAX = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_place);

        SeekBar rank = (SeekBar) findViewById(R.id.rngRanking);
        SeekBar use = (SeekBar) findViewById(R.id.rngUse);

        if (rank != null) {
            rank.setOnSeekBarChangeListener(new MySeekListener(R.id.lbRank));
            rank.setMax(MAX);
            rank.setProgress(2);
        }

        if (use != null) {
            use.setOnSeekBarChangeListener(new MySeekListener(R.id.lbUse));
            use.setMax(4);
            use.setProgress(2);
        }


    }


    private class MySeekListener implements SeekBar.OnSeekBarChangeListener {


        private int uiID = 0;

        public MySeekListener(int uid) {
            uiID = uid;
        }

        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            // Log the progress
            Log.d("DEBUG", "Progress is: " + progress);
            TextView view = (TextView) findViewById(uiID);
            if (view != null) {
                String text = progress+1 + "/" + (MAX+1);
                view.setText( text);
            }


        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
        }


    }


}
