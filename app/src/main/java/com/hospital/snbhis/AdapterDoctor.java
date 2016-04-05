package com.hospital.snbhis;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;


public class AdapterDoctor extends BaseAdapter {
    private Activity activity;
    private List<DoctorClass> DoctorList;
    private LayoutInflater inflater;

    public AdapterDoctor(Activity activity, List<DoctorClass> Items) {
        this.activity = activity;
        this.DoctorList = Items;
    }
    @Override
    public int getCount() {
        return DoctorList.size();
    }
    @Override
    public Object getItem(int position) {
        return DoctorList.get(position);
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
            convertView = inflater.inflate(R.layout.drrow, null);
        TextView ndoctor = (TextView) convertView.findViewById(R.id.txtdrn);
        TextView speciality = (TextView) convertView.findViewById(R.id.txtdrsp);
        RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.ratingdr);
        DoctorClass doctor = DoctorList.get(position);
        ndoctor.setText(doctor.getnamedr());
        speciality.setText(doctor.getspeciality());
        ratingBar.setRating(doctor.getrate());

        return convertView;
    }

}