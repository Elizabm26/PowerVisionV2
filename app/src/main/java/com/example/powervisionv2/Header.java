package com.example.powervisionv2;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.powervisionv2.datos.Datos;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;

public class Header extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header);
    }
    public void obtenerDatosPlanes(TextView txtnombre1,String cor, TextView correo) {
        correo.setText(cor);
       FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").whereEqualTo("correo", cor)
                .limit(1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> documents = task.getResult().getDocuments();
                            if (documents.size() >= 1) {
                                DocumentSnapshot firstDocument = documents.get(0);
                                Map<String, Object> data = firstDocument.getData();
                                if (data != null) {
                                    txtnombre1.setText(data.get("nombre").toString());
                                }
                            }
                        } else {

                        }
                    }
                });
    }
}
