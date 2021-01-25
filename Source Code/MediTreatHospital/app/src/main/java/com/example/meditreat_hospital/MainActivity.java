package com.example.meditreat_hospital;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.meditreat_hospital.Common.Common;
import com.example.meditreat_hospital.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class MainActivity extends AppCompatActivity {

    Button btnLogin,btnRegister;
    RelativeLayout rootlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = (Button)findViewById(R.id.login_btn);
        btnRegister = (Button)findViewById(R.id.register_btn);
        rootlayout = (RelativeLayout)findViewById(R.id.rootlayout);



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginDialog();
            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRegisterDialog();
            }
        });
    }

    private void showRegisterDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("REGISTER");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference tabel_user = database.getReference("User");

        LayoutInflater inflater = LayoutInflater.from(this);
        final View register_layout = inflater.inflate(R.layout.register,null);

        final MaterialEditText edtUname = register_layout.findViewById(R.id.edtName);
        final MaterialEditText edtNum = register_layout.findViewById(R.id.edtNumber);
        final MaterialEditText edtPass = register_layout.findViewById(R.id.edtPass);
        final ProgressDialog mDialog1 = new ProgressDialog(MainActivity.this);
        mDialog1.setMessage("Please Wait");
        mDialog1.show();
        dialog.setView(register_layout);

        dialog.setPositiveButton("REGISTER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mDialog1.dismiss();

                if(TextUtils.isEmpty(edtUname.getText().toString())){
                    Snackbar.make(rootlayout,"Enter Name",Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(edtNum.getText().toString())){
                    Snackbar.make(rootlayout,"Enter Number",Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(edtPass.getText().toString())){
                    Snackbar.make(rootlayout,"Enter Password",Snackbar.LENGTH_SHORT).show();
                    return;
                }
                tabel_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.child(edtNum.getText().toString()).exists()){
                            mDialog1.dismiss();
                            Toast.makeText(MainActivity.this,"No User Found",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            mDialog1.dismiss();
                            User user = new User(edtUname.getText().toString(),edtPass.getText().toString());
                            tabel_user.child(edtNum.getText().toString()).setValue(user);
                            Toast.makeText(MainActivity.this,"Registration Success",Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showLoginDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("LOGIN");
        dialog.setMessage("Use Mobile and Password");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference tabel_user = database.getReference("User");

        LayoutInflater inflater = LayoutInflater.from(this);
        final View login_layout = inflater.inflate(R.layout.login,null);

        final MaterialEditText edtUname = login_layout.findViewById(R.id.edtUname);
        final MaterialEditText edtPass = login_layout.findViewById(R.id.edtPass);
        final ProgressDialog mDialog1 = new ProgressDialog(MainActivity.this);
        mDialog1.setMessage("Please Wait");
        mDialog1.show();
        dialog.setView(login_layout);

        dialog.setPositiveButton("LOGIN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mDialog1.dismiss();

                if(TextUtils.isEmpty(edtUname.getText().toString())){
                    Snackbar.make(rootlayout,"Enter Your User Name",Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(edtPass.getText().toString())){
                    Snackbar.make(rootlayout,"Enter Your Password",Snackbar.LENGTH_SHORT).show();
                    return;
                }
                tabel_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.child(edtUname.getText().toString()).exists()){
                            User user = dataSnapshot.child(edtUname.getText().toString()).getValue(User.class);
                            if(!Boolean.parseBoolean(user.getIsStaff())){
                                if(user.getPassword().equals(edtPass.getText().toString())){
                                    Intent homeIntenet = new Intent(MainActivity.this,HomeScreen.class);
                                    Common.currentUser = user;
                                    startActivity(homeIntenet);
                                    finish();
                                }
                                else{
                                    Toast.makeText(MainActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(MainActivity.this,"Not Allowed",Toast.LENGTH_SHORT).show();
                            }


                        }
                        else{
                            Toast.makeText(MainActivity.this,"No User Found",Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }



}
