package com.hospital.snbhis;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;


public class AdapterHospital extends BaseAdapter {
    private Activity activity;
    private List<HospitalClass> HospitalList;
    private LayoutInflater inflater;

    public AdapterHospital(Activity activity, List<HospitalClass> Items) {
        this.activity = activity;
        this.HospitalList = Items;
    }
    @Override
    public int getCount() {
        return HospitalList.size();
    }
    @Override
    public Object getItem(int position) {
        return HospitalList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.hosprow, null);
        TextView nhosp = (TextView) convertView.findViewById(R.id.txthospn);
        TextView addr = (TextView) convertView.findViewById(R.id.txthospadd);
        RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.ratinghosp);
        HospitalClass ho= HospitalList.get(position);
        nhosp.setText(ho.getname());
        addr.setText(ho.getaddress());
        ratingBar.setRating(ho.getrate());

        return convertView;
    }

}