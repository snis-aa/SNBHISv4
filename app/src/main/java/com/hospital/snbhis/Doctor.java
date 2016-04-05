package com.hospital.snbhis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;


public class Doctor extends Activity {
    DoctorClass drclass;
    TextView textDrName,textDrDegree,textDrExp,textDrFee,textDrDesc,textDrSpeciality;
   // RatingBar ratingBar1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        textDrName = (TextView) findViewById(R.id.textDrName);
        textDrDegree = (TextView) findViewById(R.id.textDrDegree);
        textDrExp = (TextView) findViewById(R.id.textDrExp);
        textDrFee = (TextView) findViewById(R.id.textDrFee);
        textDrDesc = (TextView) findViewById(R.id.textDrDesc);
        textDrSpeciality = (TextView) findViewById(R.id.textDrSpeciality);
      //  ratingBar1 = (RatingBar) findViewById(R.id.ratingBar1);

        for(DoctorClass dc:Data.ListDoctor)
        {
            if(dc.getiddr()==Data.Iddoctor) {
                drclass=dc;
               break;
            }
        }
        textDrName.setText(drclass.getnamedr());
        textDrDegree.setText(drclass.getdrdegree());
        textDrExp.setText(drclass.getdrexp());
        textDrFee.setText(Double.toString(drclass.getfee()));
        textDrDesc.setText(drclass.getdescription());
        textDrSpeciality.setText(drclass.getspeciality());


        Button Button1 = (Button) findViewById(R.id.btnappo);
        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Appointments.class);
                startActivity(intent);

            }


        });
        Button Button2 = (Button) findViewById(R.id.btnratedr);
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DoctorRate.class);
                startActivity(intent);


            }


        });

    }
}
