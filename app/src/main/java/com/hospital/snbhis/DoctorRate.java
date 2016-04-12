package com.hospital.snbhis;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.os.Bundle;
import android.app.AlertDialog;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class DoctorRate extends Activity {
    TextView ndr;
    RatingBar rbexp,rbknow,rbtreat;
    Float vrbexp,vrbknow,vrbtreat;
    private static final String TAG_SUCCESS = "success";
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static String url = Data.server+"/snbhis/ratedoctor.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_rate);
        ndr=(TextView) findViewById(R.id.txtnamedr);
        ndr.setText(Data.Ndoctor);

        rbexp = (RatingBar) findViewById(R.id.ratingBardrexp);
        rbknow = (RatingBar) findViewById(R.id.ratingBardrknow);
        rbtreat = (RatingBar) findViewById(R.id.ratingBardrtreat);

        Button Button1 = (Button) findViewById(R.id.Submitratedr);
        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vrbexp=rbexp.getRating();
                vrbknow=rbknow.getRating();
                vrbtreat=rbtreat.getRating();

                AlertDialog.Builder alert = new AlertDialog.Builder(DoctorRate.this);
                alert.setTitle("Update Rating ");
                alert.setMessage("Do you want to save?");
                //--------------ok
                alert.setPositiveButton("Save",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int whichButton) {
                        new savedata().execute();
                    }
                });
                //------------Canceled
                alert.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                        dialog.cancel();
                    }
                });
                //--------------show
                alert.show();
            }
        });
    }
    class savedata extends AsyncTask<String, String, String> {
        int success;
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DoctorRate.this);
            pDialog.setMessage("save data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... arg0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("iddr",Integer.toString(Data.Iddoctor)));
            params.add(new BasicNameValuePair("useremail",Data.emailuser));
            params.add(new BasicNameValuePair("exp_r",Float.toString(vrbexp)));
            params.add(new BasicNameValuePair("know_r",Float.toString(vrbknow)));
            params.add(new BasicNameValuePair("treat_r", Float.toString(vrbtreat)));

            JSONObject json = jsonParser.makeHttpRequest(url,"POST", params);
            Log.d("Create Response", json.toString());
            try {
                success = json.getInt(TAG_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            if (success == 1)
            {
                Toast.makeText(DoctorRate.this,"Saved successfully ", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(DoctorRate.this,"Save failed ", Toast.LENGTH_LONG).show();
            }
        }

    }

}
