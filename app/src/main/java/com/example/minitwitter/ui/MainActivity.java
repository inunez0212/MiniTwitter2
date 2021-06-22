package com.example.minitwitter.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.minitwitter.R;
import com.example.minitwitter.common.SharedPreferencesManager;
import com.example.minitwitter.request.RequestLogin;
import com.example.minitwitter.response.ResponseAuth;
import com.example.minitwitter.retrofit.MiniTwitterClient;
import com.example.minitwitter.retrofit.MiniTwitterService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnLogin;
    TextView textViewSignUp;
    EditText email;
    EditText pass;
    MiniTwitterClient miniTwitterClient;
    MiniTwitterService miniTwitterService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        this.retrofitInit();
        this.findIds();
        this.setEvents();

    }

    private void retrofitInit() {
        miniTwitterClient = MiniTwitterClient.getInstance();
        miniTwitterService = miniTwitterClient.getMiniTwitterService();
    }

    private void setEvents() {
        btnLogin.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);
    }

    private void findIds() {
        btnLogin = findViewById(R.id.buttonLogin);
        textViewSignUp = findViewById(R.id.textViewGoSignUp);
        email = findViewById(R.id.editTextMail);
        pass = findViewById(R.id.editTextPasswordSignUp);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonLogin :{
              //  doLogin();
                Intent intent = new Intent(MainActivity.this,
                        DashboardActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.textViewGoSignUp:{
                this.goToSignUp();
                break;
            }
        }
    }

    private void doLogin() {
        String email = this.email.getText().toString();
        String password = this.pass.getText().toString();

        if(email.isEmpty()){
            this.email.setError("Email es requerido");
        }else  if(password.isEmpty()){
            this.pass.setError("Contraseña es requerida");
        }else{
            RequestLogin requestLogin = new RequestLogin();
            requestLogin.setEmail(email);
            requestLogin.setPassword(password);

           Call<ResponseAuth> responseAuthCall = miniTwitterService.doLogin(requestLogin);

           responseAuthCall.enqueue(new Callback<ResponseAuth>() {
               @Override
               public void onResponse(Call<ResponseAuth> call, Response<ResponseAuth> response) {
                   if(response.isSuccessful()){

                       SharedPreferencesManager.setLoginSharedPreferences(response);

                       Toast.makeText(MainActivity.this,
                               "Sesion iniciada correctamente", Toast.LENGTH_LONG).show();

                       Intent intent = new Intent(MainActivity.this,
                               DashboardActivity.class);
                       startActivity(intent);
                       finish();

                   }else{
                       Toast.makeText(MainActivity.this,
                               "Error al iniciar, Revise usuario y contraseña",
                               Toast.LENGTH_LONG).show();
                   }
               }

               @Override
               public void onFailure(Call<ResponseAuth> call, Throwable t) {
                   Toast.makeText(MainActivity.this, "Problemas en la conexión", Toast.LENGTH_SHORT);
                   Log.e("Error app",t.getMessage());
               }
           });
        }


    }

    private void goToSignUp(){
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
    }
}