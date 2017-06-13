package sixtus.com.homenavigation;

/**
 * Created by markzeno on 5/30/17.
 */


import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


    public class MeterAdapter extends ArrayAdapter<Meter> {
        Activity activity;
        int layoutResourceId;
        ArrayList<Meter> data = new ArrayList<Meter>();
        Meter meter;

        public MeterAdapter(Activity activity, int layoutResourceId, ArrayList<Meter> data) {
            super(activity, layoutResourceId, data);

            this.activity = activity;
            this.layoutResourceId = layoutResourceId;
            this.data = data;


        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View row = convertView;
            MeterHolder holder = null;


            if (row == null) {

                LayoutInflater inflater = LayoutInflater.from(activity);

                row = inflater.inflate(layoutResourceId, parent, false);

                holder = new MeterHolder();
                holder.meterId =(TextView)row.findViewById(R.id.meterId);
                holder.meter_number = (TextView) row.findViewById(R.id.meter_number);
                holder.current_units = (TextView) row.findViewById(R.id.current_credit);


                row.setTag(holder);

            } else {
                holder = (MeterHolder) row.getTag();
            }

            meter = data.get(position);
            holder.meterId.setText(meter.getMeterId());
            holder.meter_number.setText(meter.getMeter_number());
            holder.current_units.setText((int) meter.getCurrent_credit());


            return row;
        }


        class MeterHolder {

            TextView meter_number,current_units,meterId;
        }
    }
