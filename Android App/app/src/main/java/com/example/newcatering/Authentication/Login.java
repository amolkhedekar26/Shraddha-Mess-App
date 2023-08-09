package com.example.newcatering.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.newcatering.MainActivity;
import com.example.newcatering.R;
import com.example.newcatering.api.cateringapi;
import com.example.newcatering.datapackages.Token;
import com.thekhaeng.pushdownanim.PushDownAnim;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.newcatering.Constants.Constant.BASE_NAME;
import static com.example.newcatering.Constants.Constant.MY_PREF_NAME;
import static com.example.newcatering.Constants.Constant.SERVER_NAME;
import static com.example.newcatering.Constants.Constant.USER_TOKEN;
import static com.example.newcatering.Constants.Constant.retrofit;
import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class Login extends AppCompatActivity {
    Button login;
    EditText email, password;
    SharedPreferences SP;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.login);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        SP = getSharedPreferences(MY_PREF_NAME, MODE_PRIVATE);
        progressDialog = new ProgressDialog(Login.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Logging in");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        if (SP.contains(USER_TOKEN)) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }
        PushDownAnim.setPushDownAnimTo(login)
                .setScale(MODE_SCALE, 1.03f)
                .setDurationPush(80)
                .setDurationRelease(80)
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
                        } else {
                            progressDialog.show();
                            final cateringapi cateringapi = retrofit.create(cateringapi.class);
                            Call<Token> call = cateringapi.getToken(email.getText().toString(), password.getText().toString());
                            call.enqueue(new Callback<Token>() {
                                @Override
                                public void onResponse(@NonNull Call<Token> call, @NonNull Response<Token> response) {
                                    if (!response.isSuccessful()) {
                                        progressDialog.dismiss();
                                        email.setText("");
                                        password.setText("");
                                        email.setError("Invalid email/password");
                                        password.setError("Invalid email/password");
                                        email.requestFocus();
                                        return;
                                    }

                                    Token token = response.body();
                                    assert token != null;
                                    String mytoken = token.getToken();
                                    if (mytoken != null) {
                                        SharedPreferences.Editor editor = SP.edit();
                                        editor.putString(USER_TOKEN, token.getToken());
                                        editor.putBoolean("haslogged", true);
                                        editor.apply();
                                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                }

                                @Override
                                public void onFailure(@NonNull Call<Token> call, Throwable t) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Server unreachable", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                });
    }
}
