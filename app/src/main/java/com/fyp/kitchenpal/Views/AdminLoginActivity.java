package com.fyp.kitchenpal.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.fyp.kitchenpal.R;
import com.fyp.kitchenpal.Repository.SharedPrefs;

public class AdminLoginActivity extends AppCompatActivity {

    EditText adminEmailET;
    EditText adminPasswordET;
    Button adminLoginBT;

    SharedPrefs sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        sharedPrefs = new SharedPrefs(this);
        adminEmailET = findViewById(R.id.adminLoginEmail);
        adminPasswordET = findViewById(R.id.adminLoginPassword);
        adminLoginBT = findViewById(R.id.adminLoginBT);

        adminLoginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!adminEmailET.getText().toString().equals("admin@gmail.com")) {
                    adminEmailET.setError("Invalid Email");
                    return;
                }

                if (!adminPasswordET.getText().toString().equals("adminPassword")) {
                    adminPasswordET.setError("Invalid Password");
                    return;
                }

                sharedPrefs.loginAsAdmin(true);
                Intent intent = new Intent(AdminLoginActivity.this, AdminActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
