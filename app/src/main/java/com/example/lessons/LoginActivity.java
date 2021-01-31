package com.example.lessons;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText loginUsername , loginPassword;
    private Button loginButton;
    CheckBox remember;
    private Databasehelper myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginUsername = findViewById(R.id.loginusername);
        loginPassword = findViewById(R.id.loginpassword);
        loginButton = findViewById(R.id.loginbutton);
        remember = findViewById(R.id.remember);

        myDb = new Databasehelper(this);

        loginUser();

    }

    private void loginUser(){
        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "");

        if(checkbox.equals("true")){
            Intent intent = new Intent(LoginActivity.this , HomeActivity.class);
            startActivity(intent);
        }else if(checkbox.equals("false")){
            Toast.makeText(this,"Please Sign In. ",Toast.LENGTH_SHORT).show();
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean var = myDb.checkUser(loginUsername.getText().toString() , loginPassword.getText().toString());
                if (var){
                    Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this , HomeActivity.class));
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "Login Failed !!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()){

                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember","true");
                    editor.apply();
                    Toast.makeText(LoginActivity.this, "Checked", Toast.LENGTH_SHORT).show();

                }else if (compoundButton.isChecked()){

                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember","false");
                    editor.apply();
                    Toast.makeText(LoginActivity.this, "Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}