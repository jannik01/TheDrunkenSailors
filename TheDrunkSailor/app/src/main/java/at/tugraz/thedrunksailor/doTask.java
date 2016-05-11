package at.tugraz.thedrunksailor;

import android.os.AsyncTask;

/**
 * Created by Lissy on 11.05.2016.
 */

class doTask extends AsyncTask<String, String, Boolean> {
    protected Boolean doInBackground(String... args) {
        boolean success = DatabaseInterface.createPlace(args[0], args[1], Integer.parseInt(args[2]), args[3], args[4], args[5], args[6]);
        return success;
    }

}