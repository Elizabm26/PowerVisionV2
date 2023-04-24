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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {

    private TextView txtregresar;
    private EditText nombre;
    private EditText pais;
    private EditText email;
    private EditText password;
    private RadioGroup seleccionar;
    private RadioButton rbSelected;
    private Button btnregistro;
    private FirebaseAuth mAuth;
    private String userID;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nombre = findViewById(R.id.name);
        pais = findViewById(R.id.pais);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        seleccionar = findViewById(R.id.rol);
        btnregistro = findViewById(R.id.btnregistro);



        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnregistro.setOnClickListener( view ->  {
            createuser();
        });


        txtregresar=findViewById(R.id.regresar);
        txtregresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent txtregresar=new Intent(Registro.this, Login.class);
                startActivity(txtregresar);
            }
        });

    }
    public  void createuser(){
        String nombres= nombre.getText().toString();
        String contry= pais.getText().toString();
        String mail= email.getText().toString();
        String pass= password.getText().toString();
        //obtener referencia del radiobuton seleccionado
        int selectrdId= seleccionar.getCheckedRadioButtonId();
        rbSelected= findViewById(selectrdId);

        //obtener el valor de radiobutton seleccionado
        String namerol = rbSelected.getText().toString();

        if(TextUtils.isEmpty(nombres)){
            nombre.setError("Ingresar sus nombres");
            nombre.requestFocus();
        } else if(TextUtils.isEmpty(contry)){
            pais.setError("Ingresar sus nombres");
            pais.requestFocus();
        } else if(TextUtils.isEmpty(mail)){
            email.setError("Ingresar sus nombres");
            email.requestFocus();
        } else if(TextUtils.isEmpty(pass)){
            password.setError("Ingresar sus nombres");
            password.requestFocus();
        }else if(TextUtils.isEmpty(namerol)){
            rbSelected.setError("Ingresar rol");
            rbSelected.requestFocus();
        }else {
            mAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        userID = mAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = db.collection("users").document(userID);

                        Map<String, Object> user = new HashMap<>();
                        user.put("Nombres",nombres);
                        user.put("Pais",contry);
                        user.put("Email",mail);
                        user.put("Password",pass);
                        user.put("Rol", namerol);

                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("TAG","onSuccess: Datos registrados"+userID);
                            }
                        });
                        Toast.makeText(Registro.this, "Usuario Registrado", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Registro.this, Login.class));
                    }else {
                        Toast.makeText(Registro.this, "Usuario no registardo"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

}