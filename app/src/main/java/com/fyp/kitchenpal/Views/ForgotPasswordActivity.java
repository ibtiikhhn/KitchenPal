package com.fyp.kitchenpal.Views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.fyp.kitchenpal.R;
import com.fyp.kitchenpal.Repository.SharedPrefs;

public class ForgotPasswordActivity extends AppCompatActivity {


    EditText newPasswordET;
    Button savePasswordBT;
    SharedPrefs sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        newPasswordET = findViewById(R.id.newPasswordET);
        savePasswordBT = findViewById(R.id.savePassword);
        sharedPrefs = new SharedPrefs(this);


        savePasswordBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = newPasswordET.getText().toString();
                sharedPrefs.savePassword(password);
                finish();
            }
        });

    }
}
