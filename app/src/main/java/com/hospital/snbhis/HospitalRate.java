package com.hospital.snbhis;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.os.Bundle;
import android.app.AlertDialog;
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

public class HospitalRate extends Activity {
    RatingBar rbloc,rbtren,rbclen,rbprice;
    Float vrbloc,vrbtren,vrbclen,vrbprice;
    private static final String TAG_SUCCESS = "success";
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static String url = Data.server+"/snbhis/ratehospital.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_rate);
        rbloc = (RatingBar) findViewById(R.id.ratingBarloc);
        rbtren = (RatingBar) findViewById(R.id.ratingBartren);
        rbclen = (RatingBar) findViewById(R.id.ratingBarclen);
        rbprice = (RatingBar) findViewById(R.id.ratingBarprice);
        // Search Doctor Activity
        Button Button2 = (Button) findViewById(R.id.Submitratehosp);
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vrbloc=rbloc.getRating();
                vrbtren=rbtren.getRating();
                vrbclen=rbclen.getRating();
                vrbprice=rbprice.getRating();

                AlertDialog.Builder alert = new AlertDialog.Builder(HospitalRate.this);
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
            pDialog = new ProgressDialog(HospitalRate.this);
            pDialog.setMessage("save data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... arg0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("Idhosp",Integer.toString(Data.Idhospital)));
            params.add(new BasicNameValuePair("useremail",Data.emailuser));
            params.add(new BasicNameValuePair("location_r",Float.toString(vrbloc)));
            params.add(new BasicNameValuePair("treatments_r",Float.toString(vrbtren)));
            params.add(new BasicNameValuePair("cleanliness_r", Float.toString(vrbclen)));
            params.add(new BasicNameValuePair("prices_r", Float.toString(vrbprice)));

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
                Toast.makeText(HospitalRate.this,"Saved successfully ", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(HospitalRate.this,"Save failed ", Toast.LENGTH_LONG).show();
            }
        }

    }

}
