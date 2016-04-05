package com.hospital.snbhis;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class NewUser extends Activity {
    RadioGroup rg;
    Button btnsave;
    EditText etfullname, etemail,etpassword,etphone,etage,etaddress,editanswer;
    String sfullname, semail,spassword,sphone,sage,saddress,sanswer;
    private static final String TAG_SUCCESS = "success";
    private ProgressDialog pDialog;
    String valuesex="";
    int flag=0;
    JSONParser jsonParser = new JSONParser();
    private static String url = Data.server+"/snbhis/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etfullname=(EditText)findViewById(R.id.edt_fullName);
        etemail=(EditText)findViewById(R.id.edt_email);
        etpassword=(EditText)findViewById(R.id.edt_password);
        etphone=(EditText)findViewById(R.id.edt_contactNo);
        etage=(EditText)findViewById(R.id.edt_age);
        etaddress=(EditText)findViewById(R.id.edt_address);
        editanswer=(EditText)findViewById(R.id.editanswer);
        rg = (RadioGroup) findViewById(R.id.rg_sex);
    //show LoginActivity
        btnsave = (Button) findViewById(R.id.btnsave);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sfullname=etfullname.getText().toString();
                semail=etemail.getText().toString();
                spassword=etpassword.getText().toString();
                sphone=etphone.getText().toString();
                sage=etage.getText().toString();
                saddress=etaddress.getText().toString();
                sanswer=editanswer.getText().toString();
                valuesex = ((RadioButton)findViewById(rg.getCheckedRadioButtonId() )).getText().toString();
                if(sanswer.equals("3"))
                {
                if(!sfullname.isEmpty() && !semail.isEmpty() && !spassword.isEmpty() && !sphone.isEmpty() &&
                        !sage.isEmpty() && !saddress.isEmpty() )
                    {
                        new savedata().execute();
                    }
                    else
                        {
                            Toast.makeText(getApplicationContext(), "Enter the all data!", Toast.LENGTH_LONG).show();
                        }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Answer error!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    class savedata extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(NewUser.this);
            pDialog.setMessage("save data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... arg0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("fullname", sfullname));
            params.add(new BasicNameValuePair("email", semail));
            params.add(new BasicNameValuePair("password", spassword));
            params.add(new BasicNameValuePair("sex", valuesex));
            params.add(new BasicNameValuePair("age", sage));
            params.add(new BasicNameValuePair("phone", sphone));
            params.add(new BasicNameValuePair("address", saddress));
            JSONObject json = jsonParser.makeHttpRequest(url,"POST", params);
            Log.d("Create Response", json.toString());
            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1)
                {
                    Data.fullname=sfullname;
                    Data.emailuser=semail;
                    Data.phone=sphone;
                    flag=0;
                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    // failed to Sign in
                    flag=1;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            if(flag==1)
                Toast.makeText(NewUser.this,"Save failed ", Toast.LENGTH_LONG).show();
        }

    }


}
