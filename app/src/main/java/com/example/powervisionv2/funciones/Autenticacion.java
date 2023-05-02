package com.example.powervisionv2.funciones;

import androidx.annotation.NonNull;

import com.example.powervisionv2.datos.Datos;
import com.example.powervisionv2.datos.PlanUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Autenticacion {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String userID;

    PlanUser pln;
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
    public void PlanUser(String plan)
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("planes").whereEqualTo("Nombre", plan)
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
                                    String cantWattsStr = data.get("Cant_Watts").toString(); // Obtener el valor como un String
                                    int cantWatts = Integer.parseInt(cantWattsStr); // Convertir el String a int
                                    pln.setWatts(cantWatts);
                                    //pln.setWatts(data.get("Cant_Watts"));
                                    pln.setNombre(data.get("Nombre").toString());
                                    pln.setValor(data.get("Valor_plan").toString());






                                }
                            }
                        } else {

                        }
                    }
                });
    }

    public interface PlanCallback {
        void onPlanLoaded(String plan);
    }
    public void PlanUser_(String plan, PlanCallback callback) {
        pln = new PlanUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("planes").whereEqualTo("Nombre", plan)
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
                                    String cantWattsStr = data.get("Cant_Watts").toString();
                                    int cantWatts = Integer.parseInt(cantWattsStr);
                                    pln.setWatts(cantWatts);
                                    pln.setNombre(data.get("Nombre").toString());
                                    pln.setValor(data.get("Valor_plan").toString());

                                    // Llama al método de la interfaz de devolución de llamada con el valor deseado
                                    callback.onPlanLoaded(cantWattsStr);
                                }
                            }
                        } else {

                        }
                    }
                });
    }
}
