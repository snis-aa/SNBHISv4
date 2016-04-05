package com.hospital.snbhis;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

public class SearchDoctor extends Activity {
    EditText etdoctor;
    String doctor;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    JSONObject json;
    private CheckBox ch1,ch2,ch3;
    String ch1s,ch2s,ch3s;
    private static String url = Data.server+"/snbhis/gettalldoctor.php";
    private static String url1 = Data.server+"/snbhis/gettalldoctorbyrate.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_doctor);

        ch1   = (CheckBox) findViewById(R.id.chexp);
        ch2   = (CheckBox) findViewById(R.id.chknow);
        ch3   = (CheckBox) findViewById(R.id.chtreat);


        etdoctor = (EditText) findViewById(R.id.editdoctor);
        Data.ListDoctor=new ArrayList<DoctorClass>();
        Button Button1 = (Button) findViewById(R.id.btnsearchdoctor);
        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                doctor = etdoctor.getText().toString();
                if (doctor.isEmpty()) {
                    Toast.makeText(SearchDoctor.this,"Enter Name ", Toast.LENGTH_LONG).show();
                } else {
                    new getdata().execute();
                }
            }
        });
        Button Button2 = (Button) findViewById(R.id.btnsearchdrrate);
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch1.isChecked()){ch1s="1";}else {ch1s="0"; }
                if(ch2.isChecked()){ch2s="1";}else {ch2s="0"; }
                if(ch3.isChecked()){ch3s="1";}else {ch3s="0"; }
                new getdata1().execute();

            }
        });
    }

    class getdata extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SearchDoctor.this);
            pDialog.setMessage("get data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... arg0) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("namedr",doctor));
            json = jsonParser.makeHttpRequest(url,"POST", params);
            return null;
        }
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            try {
                Log.d("Create Response", json.toString());
                int success = json.getInt("success");
                if (success == 1)
                {
                    Data.ListDoctor.clear();
                    // successfully received user details
                    JSONArray Obj = json.getJSONArray("doctor"); // JSON Array
                    Log.d("********", Obj.toString());
                    for (int i = 0; i < Obj.length(); i++) {
                        DoctorClass dr=new DoctorClass();
                        dr.setiddr(Integer.parseInt(Obj.getJSONObject(i).getString("iddr")));
                        dr.setnamedr(Obj.getJSONObject(i).getString("namedr"));
                        dr.setidhsp(Integer.parseInt(Obj.getJSONObject(i).getString("idhsp")));
                        dr.setdrdegree(Obj.getJSONObject(i).getString("drdegree"));
                        dr.setdrexp(Obj.getJSONObject(i).getString("drexp"));
                        dr.setfee(Double.parseDouble(Obj.getJSONObject(i).getString("fee")));
                        dr.setspeciality(Obj.getJSONObject(i).getString("speciality"));
                        dr.setdescription(Obj.getJSONObject(i).getString("description"));
                        dr.setexp_r(Float.parseFloat(Obj.getJSONObject(i).getString("exp_r")));
                        dr.setknow_r(Float.parseFloat(Obj.getJSONObject(i).getString("know_r")));
                        dr.settreat_r(Float.parseFloat(Obj.getJSONObject(i).getString("treat_r")));
                        Data.ListDoctor.add(dr);
                    }
                   Intent intent = new Intent(getApplicationContext(), DoctorList.class);
                   startActivity(intent);
                }
                else
                {
                    Toast.makeText(SearchDoctor.this,"not found ", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    class getdata1 extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SearchDoctor.this);
            pDialog.setMessage("get data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... arg0) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("exp", ch1s));
            params.add(new BasicNameValuePair("know", ch2s));
            params.add(new BasicNameValuePair("treat", ch3s));
            json = jsonParser.makeHttpRequest(url1,"POST", params);
            return null;
        }
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            try {
                Log.d("Create Response", json.toString());
                int success = json.getInt("success");
                if (success == 1)
                {
                    Data.ListDoctor.clear();
                    // successfully received user details
                    JSONArray Obj = json.getJSONArray("doctor"); // JSON Array
                    Log.d("********", Obj.toString());
                    for (int i = 0; i < Obj.length(); i++) {
                        DoctorClass dr=new DoctorClass();
                        dr.setiddr(Integer.parseInt(Obj.getJSONObject(i).getString("iddr")));
                        dr.setnamedr(Obj.getJSONObject(i).getString("namedr"));
                        dr.setidhsp(Integer.parseInt(Obj.getJSONObject(i).getString("idhsp")));
                        dr.setdrdegree(Obj.getJSONObject(i).getString("drdegree"));
                        dr.setdrexp(Obj.getJSONObject(i).getString("drexp"));
                        dr.setfee(Double.parseDouble(Obj.getJSONObject(i).getString("fee")));
                        dr.setspeciality(Obj.getJSONObject(i).getString("speciality"));
                        dr.setdescription(Obj.getJSONObject(i).getString("description"));
                        dr.setexp_r(Float.parseFloat(Obj.getJSONObject(i).getString("exp_r")));
                        dr.setknow_r(Float.parseFloat(Obj.getJSONObject(i).getString("know_r")));
                        dr.settreat_r(Float.parseFloat(Obj.getJSONObject(i).getString("treat_r")));
                        Data.ListDoctor.add(dr);
                    }
                    Intent intent = new Intent(getApplicationContext(), DoctorList.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(SearchDoctor.this,"not found ", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
