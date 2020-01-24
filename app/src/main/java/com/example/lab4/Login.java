package com.example.lab4;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

public class Login extends AppCompatActivity {

    RelativeLayout rellay1, rellay2;
    TextView textViewForWrongRecord;
    FirebaseAuth myAuth;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
            rellay2.setVisibility(View.VISIBLE);
        }
    };

    EditText editTextForUsername, editTextForPassword;
    Button buttonForLogin, buttonForSignUp, buttonForForgetPassword;
    boolean loginStatusTrigger = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myAuth = FirebaseAuth.getInstance();


        RelativeLayout constraintLayout = findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        rellay1 = (RelativeLayout) findViewById(R.id.rellay1);
        rellay2 = (RelativeLayout) findViewById(R.id.rellay2);

        handler.postDelayed(runnable, 1000);

        editTextForUsername = (EditText) findViewById(R.id.editTextForUserName);
        editTextForPassword = (EditText) findViewById(R.id.editTextForPassword);
        buttonForLogin = (Button) findViewById(R.id.buttonForLogin);
        buttonForSignUp = (Button) findViewById(R.id.buttonForSignUp);
        textViewForWrongRecord = findViewById(R.id.textViewForMissRecord);
        buttonForForgetPassword = findViewById(R.id.buttonForForgetPassword);


        buttonForLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myAuth.signInWithEmailAndPassword(editTextForUsername.getText().toString(), editTextForPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){

                                    textViewForWrongRecord.setVisibility(View.INVISIBLE);
                                    Toast.makeText(getApplicationContext(),
                                            "Access Granted",
                                            Toast.LENGTH_SHORT).show();
                                    loginStatusTrigger = true;
                                    openProfileActivity();

                                }else{

                                    textViewForWrongRecord.setVisibility(View.VISIBLE);

                                    Toast.makeText(getApplicationContext(),
                                            "Access Denied",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }
        });

        buttonForSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignUpPage();
            }
        });
        buttonForForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openForgetPasswordActivity();
            }
        });


    }

    public void openProfileActivity(){

        Intent intent = new Intent(this, Profile.class);
        intent.putExtra("loginStatusTrigger", loginStatusTrigger);
        intent.putExtra("username", editTextForUsername.getText().toString());

        startActivity(intent);

    }

    public void openSignUpPage(){
        Intent intent = new Intent(this, SignUp.class);

        startActivity(intent);

    }

    public void openForgetPasswordActivity(){

        Intent intent = new Intent(this, ForgetPassword.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_right_anim, R.anim.slide_out_left);

    }

}
