package com.hospital.snbhis;

import android.app.Activity;

import android.os.AsyncTask;
import android.os.Bundle;


public class AddContactActivity extends Activity{


    AsyncTask<Void, Void, Void> mRegisterTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);


    }


}

