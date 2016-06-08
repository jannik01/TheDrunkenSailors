package at.tugraz.thedrunksailor;

import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.util.Log;

/**
 * Created by hueme on 08.06.2016.
 */
public class CheckConnection {

    Context mContext;
    public CheckConnection(Context mContext) {
        this.mContext = mContext;
    }

    public boolean isOnline(AlertDialog.Builder alert) {
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        boolean result=netInfo != null && netInfo.isConnectedOrConnecting();

        if(!result)
        {
            alert.setMessage("No internet connection!");
            alert.setTitle("Fail");
            alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // continue with delete
                }
            });
            alert.show();
        }
        return result;
    }
}
