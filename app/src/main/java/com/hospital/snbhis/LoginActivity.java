package com.hospital.snbhis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gcm.GCMRegistrar;
import android.content.SharedPreferences;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends Activity {
    EditText EmailUser, Password;
    String emailuser,password;
    private ProgressDialog pDialog;
    int flag=0;
    JSONParser jsonParser = new JSONParser();
    private static String url = Data.server+"/snbhis/login.php";





    public void openMainActivity(View view){
        // Checks whether Device is already registered on GCM
        if (GCMRegistrar.isRegisteredOnServer(this)) {

            SharedPreferences prefs = getPreferences(MODE_PRIVATE);
            String name = prefs.getString("namePGCM", "");

            Intent i = new Intent(this, Chat.class);
            Data.fullname=name;
            startActivity(i);
        }
        else{
            Toast.makeText(getApplicationContext(), "The device is not registered yet", Toast.LENGTH_LONG).show();
        }
    }

    public void abrirActivityAddContact(View view){
        // Checks whether Device is already registered on GCM
        if (GCMRegistrar.isRegisteredOnServer(this)) {
            Intent i = new Intent(this, AddContactActivity.class);
            startActivity(i);
        }
        else{
            Toast.makeText(getApplicationContext(), "The device is not registered yet", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        EmailUser = (EditText) findViewById(R.id.txtemail);
        Password = (EditText) findViewById(R.id.txtpassword);
        //show LoginActivity
        Button Button1 = (Button) findViewById(R.id.btnlogin);
        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailuser = EmailUser.getText().toString();
                 password = Password.getText().toString();


                //Check all fields
                if(emailuser.length()<1)
                {
                    Toast.makeText(LoginActivity.this,"Please Enter correct Email", Toast.LENGTH_LONG).show();
                    return;
                }
                if(password.length()<1)
                {
                    Toast.makeText(LoginActivity.this,"Please Enter correct password", Toast.LENGTH_LONG).show();
                    return;
                }
                //check connectivity

                //from login.java
                new loginAccess().execute();


            }


        });

        // Search Doctor Activity
        Button Button2 = (Button) findViewById(R.id.btnnewuser);
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),NewUser.class);
                startActivity(i);

            }
        });
    }


    class loginAccess extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... arg0) {
            try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email", emailuser));
            params.add(new BasicNameValuePair("password", password));
            JSONObject json = jsonParser.makeHttpRequest(url,"POST", params);
            Log.d("Create Response", json.toString());
                int success = json.getInt("success");
                if (success == 1)
                {
                    // successfully received user details
                    JSONArray userObj = json.getJSONArray("user"); // JSON Array

                    // get first user object from JSON Array
                    JSONObject user= userObj.getJSONObject(0);
                    Data.emailuser=emailuser;
                    Data.fullname=user.getString("fullname");
                    Data.phone=user.getString("phone");
                    SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
                    editor.putString("namePGCM", Data.fullname);
                    editor.commit();
                    flag=0;
                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    // failed to login
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
                Toast.makeText(LoginActivity.this,"Please Enter Correct Informations", Toast.LENGTH_LONG).show();

        }

    }

}
