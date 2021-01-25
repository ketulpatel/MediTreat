package com.example.meditreat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginActivity extends Activity {

    EditText input_mobile,input_password;
    Button login;
    ProgressBar progressBar;

    Connection con;

    SessionManager session;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        con = connectionclass("MediTreatAdmin","MediTreat","MediTreat","@ip");

        if(con == null){
            oninternet();
        }
        else{
            session = new SessionManager(getApplicationContext());
            input_mobile = (EditText)findViewById(R.id.input_mobile);
            input_password = (EditText)findViewById(R.id.input_password);
            progressBar = (ProgressBar)findViewById(R.id.progressBar);

            progressBar.setVisibility(View.GONE);

            login = (Button)findViewById(R.id.submit);
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CheckLogin checkLogin = new CheckLogin();
                    checkLogin.execute("");
                }
            });
        }

    }

    public class CheckLogin extends AsyncTask<String,String,String> {

        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(LoginActivity.this,s,Toast.LENGTH_SHORT).show();
            if(isSuccess){
                Toast.makeText(LoginActivity.this,"Login Success",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            String mobile = input_mobile.getText().toString();
            String password = input_password.getText().toString();
            if(mobile.trim().equals("") || password.trim().equals("")){
                z = "Please enter Mobile Number & Password";
            }else{
                try{
                        con = connectionclass("MediTreatAdmin","MediTreat","MediTreat","@ip");
                        if(con == null){
                            z = "Check Your Internet Connection";
                        }
                        else{
                            String query = "select * from UserMasters where Mobile='"+mobile.toString()+"' and Passwords='"+password.toString()+"' ";
                            Statement stmt = con.createStatement();
                            ResultSet rs = stmt.executeQuery(query);
                            if(rs.next()){
                                session.createLoginSession(mobile, password);
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                                finish();
                                z = "Login Successful";
                                isSuccess = true;
                                con.close();
                            }
                            else{
                                z = "Invalid Username or Password";
                                isSuccess = false;
                            }
                        }
                }catch (Exception ex){
                    isSuccess = false;
                    z = ex.getMessage();
                }
            }
            return z;
        }
    }

    @SuppressLint("NewApi")
    public Connection connectionclass(String username, String Password,String Database,String Server){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
            Connection connection = null;
            try{
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://@ip/MediTreat?"
                        + "user=MediTreatAdmin&password=MediTreat");
            }catch (Exception ex){
                ex.getMessage();
            }
            return connection;
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void oninternet(){
        Intent i = new Intent(getApplicationContext(), OnInternetConnection.class);
        startActivity(i);
    }
}
