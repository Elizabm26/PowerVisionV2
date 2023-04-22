package com.example.powervisionv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private Button btnlogin;
    private TextView txtregistro;
    private EditText txtemail;
    private EditText txtpass;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtemail = findViewById(R.id.email);
        txtpass = findViewById(R.id.password);
        btnlogin = findViewById(R.id.loginbtn);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        btnlogin.setOnClickListener( view ->  {
            userlogin();
        });

        txtregistro=findViewById(R.id.registro);
        txtregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent txtregistro=new Intent(Login.this, Registro.class);
                startActivity(txtregistro);
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    public void userlogin(){
        String mail = txtemail.getText().toString();
        String pass = txtpass.getText().toString();
        if(TextUtils.isEmpty(mail)){
            txtemail.setError("Ingrese un correo");
            txtemail.requestFocus();
        } else if(TextUtils.isEmpty(pass)){
            Toast.makeText(Login.this, "Ingresar una contraseña", Toast.LENGTH_SHORT).show();
            txtpass.requestFocus();
        } else {
            mAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Login.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this, MainActivity.class));
                    }else {
                        Log.w("TAG","Error: ", task.getException());
                    }
                }
            });
        }
    }

}