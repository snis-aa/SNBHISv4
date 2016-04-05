package com.hospital.snbhis;

import android.app.Activity;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import android.widget.ListView;


public class DoctorList extends Activity {
    private AdapterDoctor adapter;
    private ListView listView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
        listView1 = (ListView) findViewById(R.id.listViewdr);

        adapter = new AdapterDoctor(DoctorList.this, Data.ListDoctor);
        listView1.setAdapter(adapter);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final DoctorClass ho = (DoctorClass) adapter.getItem(position);
                Data.Iddoctor = ho.getiddr();
                Data.Ndoctor = ho.getnamedr();
                 Intent intent = new Intent(getApplicationContext(), Doctor.class);
                 startActivity(intent);
            }
        });

        // Search Doctor Activity
        Button Button2 = (Button) findViewById(R.id.btndrrate);
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), HospitalRate.class);
                startActivity(i);

            }
        });
    }


}
