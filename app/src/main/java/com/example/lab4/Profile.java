package com.example.lab4;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

public class Profile extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView textViewToolBar;
    private FirebaseUser currentUser;
    private AppCompatImageButton lock_btn;
    private AppCompatImageButton button;
    private int img;

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth auth;
    private DatabaseReference ref = database.getReference("users");


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = findViewById(R.id.toolBar_profile);
        setSupportActionBar(toolbar);
        setTitle(null);

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();

        ConstraintLayout constraintLayout = findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        if(Build.VERSION.SDK_INT >= 23) {
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 5);
            }
        }

        textViewToolBar = findViewById(R.id.toolbar_profile_name);
        setToolbarName(textViewToolBar);

        lock_btn = findViewById(R.id.buttonForLock);
        lock_btn.setOnClickListener(
                    new View.OnClickListener(){
                        private RequestParams params;

                        @Override
                        public void onClick(View view){
                            img++;
                            img = img % 2;
                            if(img == 0) {
                                lock_btn.setBackgroundResource(R.drawable.unlocked_icon);
                                Toast.makeText(getApplicationContext(), "We recommend you Lock your door when away from home", Toast.LENGTH_LONG).show();


                                AsyncHttpClient client = new AsyncHttpClient();
                                client.post("http://10.100.116.68/lock.php", params, new AsyncHttpResponseHandler() {

                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                        Toast.makeText(getApplicationContext(), "The Door is unlock", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                        Toast.makeText(getApplicationContext(), "You are not connected this lock.", Toast.LENGTH_SHORT).show();
                                    }
                                });


                            }
                            else {
                                lock_btn.setBackgroundResource(R.drawable.lock_icon);

                                AsyncHttpClient client = new AsyncHttpClient();
                                Toast.makeText(getApplicationContext(), "The Door is lock", Toast.LENGTH_SHORT).show();
                                client.post("http://10.100.116.68/unlock.php", params, new AsyncHttpResponseHandler() {

                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                        Toast.makeText(getApplicationContext(), "The Door is lock", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                        Toast.makeText(getApplicationContext(), "You are not connected this lock.", Toast.LENGTH_SHORT).show();
                                    }
                                });


                            }
                        }
                    }
        );


        //loginStatusTrigger = getIntent().getExtras().getBoolean("loginStatusTrigger") == null ? true : getIntent().getExtras().getBoolean("loginStatusTrigger");
        //username = getIntent().getStringExtra("username");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_for_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.accountInfo){

            Toast.makeText(getApplicationContext(), "account info"+currentUser.getDisplayName(), Toast.LENGTH_SHORT).show();

        }else if(id == R.id.contactUs){

            Toast.makeText(getApplicationContext(), "contact us", Toast.LENGTH_SHORT).show();

        }else if(id == R.id.addLock){

            Toast.makeText(getApplicationContext(), "add lock", Toast.LENGTH_SHORT).show();

        }else if(id == R.id.signOut){

            openLoginActivity();

        }

        return super.onOptionsItemSelected(item);
    }

    public void openLoginActivity(){

        Intent intent = new Intent(this, Login.class);
        auth.signOut();
        finish();
        startActivity(intent);

    }

    public void setToolbarName(TextView textView){

        Calendar currentTime = Calendar.getInstance();

        if(currentTime.get(Calendar.HOUR_OF_DAY) < 12){

            textView.setText(getResources().getString(R.string.good_morning) + ", " + currentUser.getDisplayName());

        }else if(currentTime.get(Calendar.HOUR_OF_DAY) < 17){

            textView.setText(getResources().getString(R.string.good_afternoon) + ", " + currentUser.getDisplayName());

        }else{

            textView.setText(getResources().getString(R.string.good_evening) + ", " + currentUser.getDisplayName());

        }

    }

}



