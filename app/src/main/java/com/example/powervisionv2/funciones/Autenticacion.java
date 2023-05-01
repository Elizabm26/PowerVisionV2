package com.example.powervisionv2.funciones;

import com.example.powervisionv2.datos.Datos;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Autenticacion {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String userID;

    public Autenticacion() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }
    public void userlogin(Datos datos, OnCompleteListener<AuthResult> listener)
    {
        mAuth.signInWithEmailAndPassword(datos.getCorreo(), datos.getContraseña()).addOnCompleteListener(listener);

    }
    public void createuser(Datos datos, OnCompleteListener<AuthResult> listener)
    {
        mAuth.createUserWithEmailAndPassword(datos.getCorreo(),
                datos.getContraseña()).addOnCompleteListener(listener);

    }
    public void saveUser(String id,Datos datos)
    {
        DocumentReference documentReference = db.collection("users").document(id);
        documentReference.set(datos);
    }
    public void update(String plan)
    {
        String userID = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = db.collection("users").document(userID);
        Map<String, Object> updates = new HashMap<>();
        updates.put("plan", plan);
        documentReference.update(updates);
    }
}
