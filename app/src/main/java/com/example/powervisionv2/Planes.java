package com.example.powervisionv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.powervisionv2.datos.Datos;
import com.example.powervisionv2.funciones.Autenticacion;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;

public class Planes extends AppCompatActivity {
    private Button btnregistro;
    private FirebaseAuth mAuth;
    private RadioGroup seleccionar;
    private RadioButton rbSelected;
    private String nameplan;
    private TextView txtnombre1;
    private TextView txtprecio1;
    private TextView txtpower1;
    private TextView txtnombre2;
    private TextView txtprecio2;
    private TextView txtpower2;
    private TextView txtnombre3;
    private TextView txtprecio3;
    private TextView txtpower3;
    private TextView txtnombre4;
    private TextView txtprecio4;
    private TextView txtpower4;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planes);
        txtnombre1 = findViewById(R.id.plan1);
        txtprecio1 = findViewById(R.id.preciop1);
        txtpower1= findViewById(R.id.wattsp1);
        txtnombre2 = findViewById(R.id.plan2);
        txtprecio2 = findViewById(R.id.preciop2);
        txtpower2= findViewById(R.id.wattsp2);
        txtnombre3 = findViewById(R.id.plan3);
        txtprecio3 = findViewById(R.id.preciop3);
        txtpower3= findViewById(R.id.wattsp3);
        txtnombre4 = findViewById(R.id.planprueba);
        txtprecio4 = findViewById(R.id.preciop4);
        txtpower4= findViewById(R.id.wattsp4);
        obtenerDatosPlanes(txtnombre1,txtpower1,txtprecio1, 0);
        obtenerDatosPlanes(txtnombre2,txtpower2,txtprecio2, 1);
        obtenerDatosPlanes(txtnombre3,txtpower3,txtprecio3, 2);
        obtenerDatosPlanes(txtnombre4,txtpower4,txtprecio4, 3);
        Datos datos = new Datos();
        btnregistro = findViewById(R.id.planregistro);
        seleccionar = findViewById(R.id.plan);
        mAuth = FirebaseAuth.getInstance();
        Autenticacion aut = new Autenticacion();
       btnregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectrdId = seleccionar.getCheckedRadioButtonId();
                if (selectrdId==-1) {
                    Toast.makeText(Planes.this, "Seleccione un plan", Toast.LENGTH_SHORT).show();
                } else {
                    rbSelected = findViewById(selectrdId);
                    nameplan = rbSelected.getText().toString();
                    datos.setPlan(nameplan);
                    aut.createuser(datos, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
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
            }
        });
    }
    public void obtenerDatosPlanes(TextView txtnombre1, TextView txtpower1, TextView txtprecio1, int num) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("planes")
                .limit(4)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> documents = task.getResult().getDocuments();
                            if (documents.size() >= 1) {
                                DocumentSnapshot firstDocument = documents.get(num);
                                Map<String, Object> data = firstDocument.getData();
                                if (data != null) {
                                    txtpower1.setText(data.get("Cant_Watts").toString() + " Watts");
                                    txtnombre1.setText(data.get("Nombre").toString());
                                    txtprecio1.setText("$ " + data.get("Valor_plan").toString());
                                }
                            }
                        } else {

                        }
                    }
                });
    }
}