package com.example.powervisionv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.powervisionv2.datos.Datos;
import com.example.powervisionv2.funciones.Autenticacion;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private String namerol;
    private int selectedCountryPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nombre = findViewById(R.id.name);
        //pais = findViewById(R.id.pais);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        seleccionar = findViewById(R.id.rol);
        btnregistro = findViewById(R.id.btnregistro);
        Spinner countrySpinner = findViewById(R.id.country_spinner);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.country_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(adapter);
        countrySpinner.setSelection(0);
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                // Almacenar la posición del elemento seleccionado
                selectedCountryPosition = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // No hacer nada
            }
        });


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        //Llamamos la clase con la función de login
        Autenticacion aut = new Autenticacion();

        btnregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombres= nombre.getText().toString();
                String mail= email.getText().toString();
                String pass= password.getText().toString();
                //obtener referencia del radiobuton seleccionado
                int selectrdId= seleccionar.getCheckedRadioButtonId();
                if(TextUtils.isEmpty(nombres)){
                    nombre.setError("Ingresar sus nombres");
                    nombre.requestFocus();
                } else if (selectedCountryPosition == 0) {
                    // Si no se ha seleccionado un país, mostrar error
                    Toast.makeText(Registro.this, "Por favor, seleccione un país.", Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(mail)){
                    email.setError("Ingresar correo");
                    email.requestFocus();
                } else if(TextUtils.isEmpty(pass)){
                    password.setError("Ingresar contraseña");
                    password.requestFocus();
                }else if(selectrdId == -1){
                    rbSelected.setError("Ingresar rol");
                    rbSelected.requestFocus();
                }else {
                    rbSelected= findViewById(selectrdId);
                    //obtener el valor de radiobutton seleccionado
                    namerol = rbSelected.getText().toString();
                    // Obtener el país seleccionado en el Spinner
                    String country = countrySpinner.getSelectedItem().toString();
                    Datos datos = new Datos(nombres,country,mail,pass,namerol);
                    aut.createuser(datos, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                String userId = firebaseUser.getUid();
                                aut.saveUser(userId, datos);
                                Toast.makeText(Registro.this, "Registro exitoso.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Registro.this, Login.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Registro.this, "Usuario no registardo" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
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
}