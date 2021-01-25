package com.example.meditreat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.encoder.QRCode;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static java.lang.Thread.sleep;

public class PatientDetails extends Activity {

    AutoCompleteTextView hospital_name;

    ArrayAdapter<String> adapter;
    String[] hname ;
    String uname;
    Connection con;
    PreparedStatement stmt;
    ResultSet rs;

    EditText paitent_name,age,disease,description,arrivaltime;

    RadioButton radioButton1,radioButton2,radioButton3;
    String mgender;

    Spinner bloodgroup;
    String bloodg;
    ArrayAdapter<String> adapter_blood;
    String BloogGName[] = {"Don't Known","A+","A-","AB+","AB-","B+","B-","O+","O-"};

    CheckBox bp,diabetes,thyroid,allergy,breathingproblem,pasts,pregnancy;
    String strSymtoms = "";

    ImageView iv;

    private ProgressDialog progressDialog;
    private int progressbarper = 0;

    Button btn_submit;
    SessionManager session;

    String bArray;
    String Checked = "N";
    Calendar c = Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String formattedDate = df.format(c.getTime());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patientdetails);
        session = new SessionManager(getApplicationContext());

        session.checkLogin();

        progressDialog = new ProgressDialog(PatientDetails.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                progressDialog.dismiss();
                allSet();

            }
        }, 2000);

    }






    public void allSet(){
        GetAutoTextView();
        SetEditText();
        getBloodgroup();

        insertdata();
    }

    public void GetAutoTextView(){
        hospital_name = (AutoCompleteTextView)findViewById(R.id.hospital_name);
        con = connectionclass("MediTreatAdmin","MediTreat","MediTreat","@ip");
        String query = "select HospitalName from HospitalMasters";


        try{
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
            ArrayList<String> data = new ArrayList<String>();
            while(rs.next()){
                String id = rs.getString("HospitalName");
                data.add(id);
            }
            String[] array = data.toArray(new String[0]);
            ArrayAdapter NoCoreAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,data);
            hospital_name.setAdapter(NoCoreAdapter);
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }



    @SuppressLint("NewApi")
    public Connection connectionclass(String username, String Password,String Database,String Server){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://@ipMediTreat?"
                    + "user=MediTreatAdmin&password=MediTreat");
        }catch (Exception ex){
            ex.getMessage();
        }
        return connection;
    }

    public void generateQrCode(){
        con = connectionclass("MediTreatAdmin","MediTreat","MediTreat","@ip");
        String query = "select APatientID from APatientMasters";
        iv = (ImageView)findViewById(R.id.iv);
        int n = 0;
        try{
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();

            while(rs.next()){
                int id = rs.getInt("APatientID");
                if(id<0){
                    n = 0;
                }
                if(id>0){
                    n = id+1;
                }
                if(id == 0){
                    n = 0;
                }

            }

        }catch (Exception ex){
            ex.printStackTrace();
        }

        String nn = String.valueOf(n);

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(nn, BarcodeFormat.QR_CODE,500,500);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            iv.setImageBitmap(bitmap);
            Bitmap b1 = ((BitmapDrawable)iv.getDrawable()).getBitmap();

           ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,bos);
            bArray = Base64.encodeToString(bos.toByteArray(),Base64.DEFAULT);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public void SetEditText(){
        paitent_name = (EditText)findViewById(R.id.patient_name);
        age = (EditText)findViewById(R.id.age);
        disease = (EditText)findViewById(R.id.disease);
        description = (EditText)findViewById(R.id.description);
        arrivaltime = (EditText)findViewById(R.id.arrivaltime);
    }
    public void getGender(View view){
            boolean checked = ((RadioButton)view).isChecked();

            switch (view.getId()){
                case R.id.male:
                    if(checked){
                        mgender = "Male";
                    }
                    break;

                case R.id.female:
                    if(checked){
                        mgender = "Female";
                    }
                    break;

                case  R.id.other:
                    if(checked){
                        mgender = "Other";
                    }
                    break;
            }
    }

    public void getBloodgroup(){
        bloodgroup = (Spinner)findViewById(R.id.blood_group);
        adapter_blood = new ArrayAdapter<String>((Activity)this,android.R.layout.simple_list_item_1,BloogGName);
        bloodgroup.setAdapter(adapter_blood);
        bloodgroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        bloodg = "Don't Know";
                        break;

                    case 1:
                        bloodg = "A+";
                        break;

                    case 2:
                        bloodg = "A-";
                        break;

                    case 3:
                        bloodg = "AB+";
                        break;

                    case 4:
                        bloodg = "AB-";
                        break;

                    case 5:
                        bloodg = "B+";
                        break;

                    case 6:
                        bloodg = "B-";
                        break;

                    case 7:
                        bloodg = "O+";
                        break;

                    case 8:
                        bloodg = "O-";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getSymptoms1(View view){

        bp = (CheckBox)findViewById(R.id.bp);


        if(bp.isChecked()){
            strSymtoms += "BP, ";
        }

    }


    public void getSymptoms2(View view){

        diabetes = (CheckBox)findViewById(R.id.diabetes);

        if(diabetes.isChecked()){
            strSymtoms +="Diabetes, ";
        }

    }


    public void getSymptoms3(View view){


        thyroid = (CheckBox)findViewById(R.id.thyroid);



        if(thyroid.isChecked()){
            strSymtoms += "Thyroid, ";
        }

    }


    public void getSymptoms4(View view){

        allergy = (CheckBox)findViewById(R.id.allergy);



        if(allergy.isChecked()){
            strSymtoms += "Allergy, ";
        }

    }


    public void getSymptoms5(View view){


        breathingproblem = (CheckBox)findViewById(R.id.breathingproblem);



        if(breathingproblem.isChecked()){
            strSymtoms += "Breathing Problem, ";
        }

    }


    public void getSymptoms6(View view){


        pasts = (CheckBox)findViewById(R.id.pasts);


        if(pasts.isChecked()){
            strSymtoms += "Past Surgery, ";
        }

    }


    public void getSymptoms7(View view){


        pregnancy = (CheckBox)findViewById(R.id.pregnancy);


        if(pregnancy.isChecked()){
            strSymtoms += "Pregnancy, ";
        }
    }




    public void insertdata(){
        btn_submit = (Button)findViewById(R.id.btn_submit);
        final String active = "A";
        final HashMap<String,String> name = session.getUserDetails();
        uname = name.get(SessionManager.KEY_NAME);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(v.getContext());
                progressDialog.setMessage("Sending to Hospital.......");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setProgress(0);
                progressDialog.setMax(100);
                progressDialog.show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (progressbarper<100){
                            try {
                                sleep(10);
                                progressbarper++;
                                progressDialog.setProgress(progressbarper);

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        con = connectionclass("MediTreatAdmin","MediTreat","MediTreat","@ip");
                        String query = "insert into APatientMasters (HospitalName,PatientName,PatientAge,Gender,BloodGroup,Disease,Description,Symptoms,ExpectedArrivalTime,Status,UserID,CreatedDateTime,HospitalChecked) values ('"+hospital_name.getText().toString()+"','"+paitent_name.getText().toString()+"','"+age.getText().toString()+"','"+mgender+"','"+bloodg+"','"+disease.getText().toString()+"','"+description.getText().toString()+"','"+strSymtoms+"','"+arrivaltime.getText().toString()+"','"+active+"','"+uname+"','"+formattedDate+"','"+Checked+"')";
                        try{

                            stmt.executeUpdate(query);
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }

                        progressDialog.dismiss();
                        Intent myIntent = new Intent(PatientDetails.this,
                                PopUpCompelete.class);
                        startActivity(myIntent);
                    }
                }).start();



            }
        });
    }
}
