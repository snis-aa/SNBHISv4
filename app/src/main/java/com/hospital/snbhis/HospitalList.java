package com.hospital.snbhis;

import android.app.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HospitalList extends Activity {

    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    JSONObject json;
    private static String url = Data.server+"/snbhis/gettalldoctorbyhosp.php";
    private AdapterHospital adapter;
    private ListView listView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);
        listView1 = (ListView) findViewById(R.id.listViewhosp);
        adapter = new AdapterHospital(HospitalList.this, Data.ListHospital);
        listView1.setAdapter(adapter);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final HospitalClass ho = (HospitalClass) adapter.getItem(position);
                Data.Idhospital=ho.getid();
                Data.Nhospital=ho.getname();
                Data.ListDoctor=new ArrayList<DoctorClass>();
                new getdata().execute();

            }
        });

    }

    class getdata extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(HospitalList.this);
            pDialog.setMessage("get data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... arg0) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Idhosp",Integer.toString(Data.Idhospital)));
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
                    Toast.makeText(HospitalList.this, "not found ", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}

