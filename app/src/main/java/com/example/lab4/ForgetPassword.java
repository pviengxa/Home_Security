package com.example.lab4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {


    private Toolbar toolbar;
    private Button buttonForOptions, buttonForContinue;
    private EditText editTextForEmail;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        toolbar = findViewById(R.id.toolbarOnForGetPassword);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        auth = FirebaseAuth.getInstance();
        buttonForContinue = findViewById(R.id.buttonForContinue);
        buttonForOptions = findViewById(R.id.buttonForForgetPasswordOption);
        editTextForEmail = findViewById(R.id.editTextForForgetPasswordEmail);


        buttonForContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextForEmail.getText().toString();
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){

                                    Toast.makeText(getApplicationContext(), "Email sent", Toast.LENGTH_SHORT).show();
                                    openActivity();

                                }
                            }

                        });

            }

        });




    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_left_anim, R.anim.slide_out_right);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_for_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.returnToLogin){

            openActivity();

        }
        return super.onOptionsItemSelected(item);
    }

    public void openActivity(){

        Intent intent = new Intent(this, Login.class);
        startActivity(intent);

    }


}
