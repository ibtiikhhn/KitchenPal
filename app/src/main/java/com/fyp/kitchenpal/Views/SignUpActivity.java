package com.fyp.kitchenpal.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.fyp.kitchenpal.R;
import com.fyp.kitchenpal.Repository.SharedPrefs;

public class SignUpActivity extends AppCompatActivity {

    EditText fullnametext,emailtext, passwordtext, mobiletext;
    TextView openlogin;
    Button signUpBT;
    SharedPrefs sharedPrefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        sharedPrefs = new SharedPrefs(this);
        fullnametext = ((EditText) findViewById(R.id.editTextname));
        emailtext    = ((EditText) findViewById(R.id.editTextemail));
        passwordtext = ((EditText) findViewById(R.id.editTextpassword));
        mobiletext   = ((EditText) findViewById(R.id.editTextmobile));

        openlogin = ((TextView)findViewById(R.id.logintext));
        signUpBT = ((Button) findViewById(R.id.btnRegister));

        signUpBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fullname = fullnametext.getText().toString();
                String email    = emailtext.getText().toString().trim();
                String password = passwordtext.getText().toString();
                String mobile   = mobiletext.getText().toString();

                if (fullname.isEmpty() || fullname.length() < 3) {
                    fullnametext.setError("at least 3 characters");
                    return;
                }

                if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailtext.setError("enter a valid email address");
                    return;
                }

                if (mobile.isEmpty() || mobile.length()!=11) {
                    mobiletext.setError("Enter Valid Mobile Number");
                    return;
                }

                if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
                    passwordtext.setError("between 4 and 10 characters");
                    return;
                }

                sharedPrefs.saveEmail(email);
                sharedPrefs.savePassword(password);
                sharedPrefs.saveName(fullname);
                sharedPrefs.loginAsUser(true);

                Intent intent = new Intent(SignUpActivity.this, Dashboard.class);
                startActivity(intent);
                finish();
            }
        });

        openlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
