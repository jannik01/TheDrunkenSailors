package at.tugraz.thedrunksailor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.logging.Logger;

import at.tugraz.thedrunksailor.R;

/**
 * Created by Clemens on 27.04.2016.
 */


class PlaceItemAdapter extends BaseAdapter {

    Context context;
    String[][] data;
    final static String TAG="LIST LOGGER";
    private static LayoutInflater inflater = null;

    public PlaceItemAdapter(Context context, String[][] data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
//        Log.d(TAG, data[position]);
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.place_row_item, null);

        vi.setTag(data[position][0]);
        TextView name = (TextView) vi.findViewById(R.id.name);
        TextView currentUse = (TextView) vi.findViewById(R.id.current_use);
        TextView average = (TextView) vi.findViewById(R.id.average);

        name.setText(data[position][1]);
        currentUse.setText(data[position][2]);
        average.setText(data[position][3]);

        return vi;
    }
}
