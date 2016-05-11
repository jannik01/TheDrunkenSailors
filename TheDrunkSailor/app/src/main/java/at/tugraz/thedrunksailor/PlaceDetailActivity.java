package at.tugraz.thedrunksailor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

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
    public void buttonOnClick(View v) {
        SeekBar rank = (SeekBar) findViewById(R.id.rngRanking);
        SeekBar use = (SeekBar) findViewById(R.id.rngUse);
        rank.getProgress();

        String rating = Integer.toString(rank.getProgress()+1);
        String current_use_= Integer.toString(use.getProgress()+1);


        String[] params = new String[]{rating, current_use_};

        try {
            if (new doTask().execute(params).get()) {
                Intent intent = new Intent(this, PlaceDetailActivity.class);
                startActivity(intent);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
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

class doTask extends AsyncTask<String, String, Boolean> {
    protected Boolean doInBackground(String... args) {
        boolean success = DatabaseInterface.ratePlace(args[0], args[1]);
        return success;
    }

}
}

