package com.hospital.snbhis;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class Appointments extends Activity {
    DatePicker myDatePicker ;
    TimePicker myTime;
    private static final String TAG_SUCCESS = "success";
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static String url = Data.server+"/snbhis/appointment.php";
    String mydatetime;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);
        myDatePicker = (DatePicker) findViewById(R.id.datePicker);
        myTime = ((TimePicker) findViewById(R.id.timePicker));
        myTime.setIs24HourView(true);
        // set current date into datepicker
        final Calendar c = Calendar.getInstance();
        myDatePicker.updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        // set current time into timepicker
        myTime.setCurrentHour(c.get(Calendar.HOUR));
        myTime.setCurrentMinute(c.get(Calendar.MINUTE));
        //
        Button Button1 = (Button) findViewById(R.id.submitappo);
        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 mydatetime=String.format("%d-%d-%d  %d:%d:00", myDatePicker.getYear(), myDatePicker.getMonth() + 1
                        , myDatePicker.getDayOfMonth(), myTime.getCurrentHour(), myTime.getCurrentMinute());

                AlertDialog.Builder alert = new AlertDialog.Builder(Appointments.this);
                alert.setTitle("Save Appointments ");
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
            pDialog = new ProgressDialog(Appointments.this);
            pDialog.setMessage("save data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... arg0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();

            params.add(new BasicNameValuePair("emailuser",Data.emailuser));
            params.add(new BasicNameValuePair("idhsp",Integer.toString(Data.Idhospital)));
            params.add(new BasicNameValuePair("iddr",Integer.toString(Data.Iddoctor)));
            params.add(new BasicNameValuePair("appodate",mydatetime ));

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
                Toast.makeText(Appointments.this,"Saved successfully ", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(Appointments.this,"Save failed ", Toast.LENGTH_LONG).show();
            }
        }

    }

}
