package com.example.newcatering.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.newcatering.R;
import com.thekhaeng.pushdownanim.PushDownAnim;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class Signup extends AppCompatActivity {
    EditText email,password,password2;
    Button signup;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        email=findViewById(R.id.signup_email);
        password=findViewById(R.id.signup_password);
        password2=findViewById(R.id.signup_confirmpassword);
        signup=findViewById(R.id.signup);
        progressDialog = new ProgressDialog(Signup.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Signing up");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        PushDownAnim.setPushDownAnimTo(signup)
                .setScale(MODE_SCALE, 1.03f)
                .setDurationPush(60)
                .setDurationRelease(60)
                .setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR)
                .setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog.show();
                        if (email.getText() == null || email.getText().toString().equals("")) {
                            progressDialog.dismiss();
                            email.setError("Please enter email");
                        } else if (password.getText() == null || password.getText().toString().equals("")) {
                            progressDialog.dismiss();
                            password.setError("Please enter password");
                        }
                        else if (password2.getText() == null || password2.getText().toString().equals("")) {
                            progressDialog.dismiss();
                            password2.setError("Please enter confirm password");
                        }
                        if (!password.getText().equals(password2.getText())) {
                            progressDialog.dismiss();
                            password.setError("Passwords didn't match");
                        }

                    }
                });
    }
}
