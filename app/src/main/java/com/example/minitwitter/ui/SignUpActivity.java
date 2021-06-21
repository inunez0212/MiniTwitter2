package com.example.minitwitter.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.minitwitter.R;
import com.example.minitwitter.common.SharedPreferencesManager;
import com.example.minitwitter.request.RequestSignUp;
import com.example.minitwitter.response.ResponseAuth;
import com.example.minitwitter.retrofit.MiniTwitterClient;
import com.example.minitwitter.retrofit.MiniTwitterService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSignUp;
    TextView textGoToLogin;
    EditText userNameEditText;
    EditText emailEditText;
    EditText passwordEditText;
    MiniTwitterClient miniTwitterClient;
    MiniTwitterService miniTwitterService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        miniTwitterClient = MiniTwitterClient.getInstance();
        miniTwitterService = miniTwitterClient.getMiniTwitterService();

        this.findViews();
        this.setEvents();


    }

    private void signUp(){
        if(this.userNameEditText.getText().toString().isEmpty()){
            this.userNameEditText.setError("El nombre de usuario es requerido");
        }else if (this.emailEditText.getText().toString().isEmpty()){
            this.emailEditText.setError("El email es requerido");
        }else if(this.passwordEditText.getText().toString().isEmpty()){
            this.passwordEditText.setError("La constraseña es requerida");
        }else{
            RequestSignUp requestSignUp = new RequestSignUp();
            requestSignUp.setUsername(userNameEditText.getText().toString());
            requestSignUp.setEmail(emailEditText.getText().toString());
            requestSignUp.setPassword(passwordEditText.getText().toString());
            requestSignUp.setCode("UDEMYANDROID");

            Call<ResponseAuth> callSingUp = miniTwitterService.signUp(requestSignUp);

            callSingUp.enqueue(new Callback<ResponseAuth>() {
                @Override
                public void onResponse(Call<ResponseAuth> call, Response<ResponseAuth> response) {
                    if(response.isSuccessful()){

                        SharedPreferencesManager.setLoginSharedPreferences(response);

                        Toast.makeText(SignUpActivity.this,
                                "Sesion iniciada correctamente", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SignUpActivity.this,
                                DashboardActivity.class );
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(SignUpActivity.this,
                                "Error al registrarse, intentelo de nuevo", Toast.LENGTH_SHORT)
                                .show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseAuth> call, Throwable t) {
                    Toast.makeText(SignUpActivity.this,
                            "Error al registrarse, intentelo de nuevo", Toast.LENGTH_SHORT)
                            .show();
                }
            });
        }
    }

    private void setEvents() {
        btnSignUp.setOnClickListener(this);
        textGoToLogin.setOnClickListener(this);
    }

    private void findViews() {
        btnSignUp = findViewById(R.id.buttonSignUp);
        textGoToLogin = findViewById(R.id.textViewGoLogin);
        userNameEditText = findViewById(R.id.editTextNombreUsuario);
        emailEditText = findViewById(R.id.editTextMail);
        passwordEditText = findViewById(R.id.editTextPasswordSignUp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonSignUp : {
                this.signUp();
                break;
            }
            case R.id.textViewGoLogin : {
                this.textGoToLogin();
                break;
            }
        }
    }

    private void textGoToLogin(){
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
    }
}