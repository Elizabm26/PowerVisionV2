package com.example.powervisionv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.powervisionv2.datos.Datos;
import com.example.powervisionv2.funciones.Autenticacion;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Planes extends AppCompatActivity {
    private Button btnregistro;
    private FirebaseAuth mAuth;
    private RadioGroup seleccionar;
    private RadioButton rbSelected;
    private String nameplan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planes);
        Datos datos = new Datos();
        btnregistro = findViewById(R.id.planregistro);
        seleccionar = findViewById(R.id.plan);
        mAuth = FirebaseAuth.getInstance();
        Autenticacion aut = new Autenticacion();
        btnregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectrdId = seleccionar.getCheckedRadioButtonId();
                rbSelected = findViewById(selectrdId);
                nameplan = rbSelected.getText().toString();
                datos.setPlan(nameplan);
                aut.createuser(datos, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                            String userId = firebaseUser.getUid();
                            aut.saveUser(userId, datos);
                            Toast.makeText(Planes.this, "Registro exitoso.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Planes.this, Login.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Planes.this, "Usuario no registardo" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}