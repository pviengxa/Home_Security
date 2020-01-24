package com.example.lab4;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUp extends AppCompatActivity {

    private EditText editTextForFirstName, editTextForLastName, editTextForEmail, editTextForPhoneNumber,
             editTextForUsername, editTextForPassword, editTextForRePassword;
    private CheckBox checkBoxForAgreement;
    private String firstName, lastName, userName, email, phoneNumber, password;
    private Button buttonForSignUp;
    private TextView textViewForMessage;
    private FirebaseAuth mAuth;
    private DatabaseReference db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextForEmail = findViewById(R.id.editTextForEmail);
        editTextForFirstName = findViewById(R.id.editTextForFirstName);
        editTextForLastName = findViewById(R.id.editTextForLastName);
        editTextForPassword = findViewById(R.id.editTextForPassword);
        editTextForPhoneNumber = findViewById(R.id.editTextForPhoneNumber);
        editTextForUsername = findViewById(R.id.editTextUserName);
        editTextForRePassword = findViewById(R.id.editTextForRePassword);
        checkBoxForAgreement = findViewById(R.id.checkBoxForAgreement);
        textViewForMessage = findViewById(R.id.textViewForRedMessage);

        db = FirebaseDatabase.getInstance().getReference("users");
        mAuth = FirebaseAuth.getInstance();

        LinearLayout constraintLayout = findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();


        buttonForSignUp = findViewById(R.id.buttonForCreate);

        buttonForSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firstName = editTextForFirstName.getText().toString();
                lastName = editTextForLastName.getText().toString();
                email = editTextForEmail.getText().toString();
                userName = editTextForUsername.getText().toString();
                password = editTextForPassword.getText().toString();
                phoneNumber = editTextForPhoneNumber.getText().toString();

                String id = db.push().getKey();

                if(checkForSignUpCorrectness()){

                    textViewForMessage.setVisibility(View.INVISIBLE);

                    Users user = new Users(id, userName, password, firstName, lastName, email, phoneNumber);

                    db.child(id).setValue(user);
                    createAccount(email, password);

                    Toast.makeText(getApplicationContext(), "email: "+id, Toast.LENGTH_LONG).show();

                    openProfile();

                }


            }
        });


    }

    public void createAccount(String email, String password){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Registration was successful", Toast.LENGTH_LONG).show();
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("DEBUG", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d("DEBUG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });

    }

    public void openProfile(){
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);

    }

    public boolean checkForSignUpCorrectness(){

        boolean signupStatus = true;

        if(TextUtils.isEmpty(editTextForUsername.getText().toString()) ||
                TextUtils.isEmpty(editTextForEmail.getText().toString()) ||
                TextUtils.isEmpty(editTextForPassword.getText().toString()) ||
                TextUtils.isEmpty(editTextForRePassword.getText().toString())){

            textViewForMessage.setText(R.string.input_is_empty);
            textViewForMessage.setVisibility(View.VISIBLE);
            eventForEmptyFields();
            signupStatus = false;

        }else if(!checkBoxForAgreement.isChecked()){

            textViewForMessage.setText(R.string.you_must_agree);
            textViewForMessage.setVisibility(View.VISIBLE);
            eventForEmptyFields();
            signupStatus = false;

        }else if(!editTextForPassword.getText().toString().equalsIgnoreCase(editTextForRePassword.getText().toString())){

            textViewForMessage.setText(R.string.password_must_match);
            textViewForMessage.setVisibility(View.VISIBLE);
            eventForEmptyFields();
            signupStatus = false;

        }

        return signupStatus;

    }

    public void eventForEmptyFields(){

        eventForEmptyField(editTextForUsername);
        eventForEmptyField(editTextForEmail);
        eventForEmptyField(editTextForPassword);
        eventForEmptyField(editTextForRePassword);

    }

    public void eventForEmptyField(EditText editText){

        if(TextUtils.isEmpty(editText.getText().toString())){

            editText.setBackgroundResource(R.drawable.missing_field);

        }else editText.setBackgroundResource(R.drawable.edit_text_round_corner);


    }


}
