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



public class SeachHospital extends Activity {
    EditText ethospital;
    String shospital,scity,phospital,pcity;
    private ProgressDialog pDialog;
    Spinner city;

    JSONParser jsonParser = new JSONParser();
    JSONObject json;

    private CheckBox ch1,ch2,ch3,ch4;
    String ch1s,ch2s,ch3s,ch4s;
    private static String url = Data.server+"/snbhis/gettallhospital.php";
    private static String url1 = Data.server+"/snbhis/gettallhospitalbyrate.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seach_hospital);
        ch1   = (CheckBox) findViewById(R.id.chLocation);
        ch2   = (CheckBox) findViewById(R.id.chPrice);
        ch3   = (CheckBox) findViewById(R.id.chTreatment);
        ch4   = (CheckBox) findViewById(R.id.chCleaness);

        ethospital = (EditText) findViewById(R.id.hospitalname);
        Data.ListHospital=new ArrayList<HospitalClass>();

        city= (Spinner) findViewById(R.id.spinner);

        // Search Doctor Activity
        Button Button1 = (Button) findViewById(R.id.btnsearch1);
        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scity = city.getSelectedItem().toString();
                if (scity.equals("All Cities")) {
                    pcity = "0";
                } else {
                    pcity = scity;
                }
                shospital = ethospital.getText().toString();
                if (shospital.isEmpty()) {
                    phospital = "0";
                } else {
                    phospital = shospital;
                }

                new getdata().execute();

                    Intent intent = new Intent(getApplicationContext(), HospitalList.class);
                    startActivity(intent);

            }
        });

        // Search Doctor Activity
        Button Button2 = (Button) findViewById(R.id.btnsearch2);
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ch1.isChecked()){ch1s="1";}else {ch1s="0"; }
                if(ch2.isChecked()){ch2s="1";}else {ch2s="0"; }
                if(ch3.isChecked()){ch3s="1";}else {ch3s="0"; }
                if(ch4.isChecked()){ch4s="1";}else {ch4s="0"; }
                new getdata1().execute();
                Intent intent = new Intent(getApplicationContext(), HospitalList.class);
                startActivity(intent);
            }
    });

    }
    class getdata extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SeachHospital.this);
            pDialog.setMessage("get data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... arg0) {

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("city", pcity));
                params.add(new BasicNameValuePair("name", phospital));
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
                    Data.ListHospital.clear();
                    // successfully received user details
                    JSONArray Obj = json.getJSONArray("hospital"); // JSON Array
                    Log.d("********", Obj.toString());
                    for (int i = 0; i < Obj.length(); i++) {
                        HospitalClass hc=new HospitalClass();
                        hc.setid(Integer.parseInt(Obj.getJSONObject(i).getString("id")));
                        hc.setname(Obj.getJSONObject(i).getString("name"));
                        hc.setcity(Obj.getJSONObject(i).getString("city"));
                        hc.setaddress(Obj.getJSONObject(i).getString("address"));
                        hc.setlocation_r(Float.parseFloat(Obj.getJSONObject(i).getString("location_r")));
                        hc.setcleanliness_r(Float.parseFloat(Obj.getJSONObject(i).getString("cleanliness_r")));
                        hc.settreatments_r(Float.parseFloat(Obj.getJSONObject(i).getString("treatments_r")));
                        hc.setprices_r(Float.parseFloat(Obj.getJSONObject(i).getString("prices_r")));
                        Data.ListHospital.add(hc);
                    }
                }
                else
                {
                    Toast.makeText(SeachHospital.this,"not found ", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    class getdata1 extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SeachHospital.this);
            pDialog.setMessage("get data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... arg0) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("chLocation", ch1s));
            params.add(new BasicNameValuePair("chPrice", ch2s));
            params.add(new BasicNameValuePair("chTreatment", ch3s));
            params.add(new BasicNameValuePair("chCleaness", ch4s));

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
                    Data.ListHospital.clear();
                    // successfully received user details
                    JSONArray Obj = json.getJSONArray("hospital"); // JSON Array
                    Log.d("********", Obj.toString());
                    for (int i = 0; i < Obj.length(); i++) {
                        HospitalClass hc=new HospitalClass();
                        hc.setid(Integer.parseInt(Obj.getJSONObject(i).getString("id")));
                        hc.setname(Obj.getJSONObject(i).getString("name"));
                        hc.setcity(Obj.getJSONObject(i).getString("city"));
                        hc.setaddress(Obj.getJSONObject(i).getString("address"));
                        hc.setlocation_r(Float.parseFloat(Obj.getJSONObject(i).getString("location_r")));
                        hc.setcleanliness_r(Float.parseFloat(Obj.getJSONObject(i).getString("cleanliness_r")));
                        hc.settreatments_r(Float.parseFloat(Obj.getJSONObject(i).getString("treatments_r")));
                        hc.setprices_r(Float.parseFloat(Obj.getJSONObject(i).getString("prices_r")));
                        Data.ListHospital.add(hc);
                    }
                }
                else
                {
                    Toast.makeText(SeachHospital.this,"Error", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

}
