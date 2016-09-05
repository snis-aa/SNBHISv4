package com.hospital.snbhis;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Search Hospital Activity
        Button Button1 = (Button) findViewById(R.id.btnHospitals);
        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SeachHospital.class);
                startActivity(i);
            }
        });
        // Search Doctor Activity
        Button Button2 = (Button) findViewById(R.id.btnDoctors);
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),SearchDoctor.class);
                startActivity(i);

            }
        });
        
        // Search Doctor Activity
        Button Button4 = (Button) findViewById(R.id.btnExit);
        Button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
