package com.example.powervisionv2;

import static android.service.controls.ControlsProviderService.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.powervisionv2.datos.Datos;
import com.example.powervisionv2.funciones.Autenticacion;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Fragment_planes extends Fragment {

    private FirebaseFirestore db;
    private RadioGroup planRadioGroup;
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
    private TextView plan;
    private Button update;
    private RadioGroup seleccionar;
    private RadioButton rbSelected;
    String nameplan;
    Datos dat = new Datos();
    Autenticacion aut = new Autenticacion();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_planes, container, false);
        txtnombre1 = view.findViewById(R.id.plan1);
        txtprecio1 = view.findViewById(R.id.preciop1);
        txtpower1= view.findViewById(R.id.wattsp1);
        txtnombre2 = view.findViewById(R.id.plan2);
        txtprecio2 = view.findViewById(R.id.preciop2);
        txtpower2= view.findViewById(R.id.wattsp2);
        txtnombre3 = view.findViewById(R.id.plan3);
        txtprecio3 = view.findViewById(R.id.preciop3);
        txtpower3= view.findViewById(R.id.wattsp3);
        txtnombre4 = view.findViewById(R.id.planprueba);
        txtprecio4 = view.findViewById(R.id.preciop4);
        txtpower4= view.findViewById(R.id.wattsp4);
        obtenerDatosPlanes(txtnombre1,txtpower1,txtprecio1, 0);
        obtenerDatosPlanes(txtnombre2,txtpower2,txtprecio2, 1);
        obtenerDatosPlanes(txtnombre3,txtpower3,txtprecio3, 2);
        obtenerDatosPlanes(txtnombre4,txtpower4,txtprecio4, 3);
        plan = view.findViewById(R.id.datosplanes);
        plan.setText("Hola "+ dat.getNombre() +" tu plan selecionado es: "+ dat.getPlan());
        System.out.println("hola");
        seleccionar = view.findViewById(R.id.planes12);
        update = view.findViewById(R.id.planactualizar);
        update.setOnClickListener(new View.OnClickListener() {2
            @Override
            public void onClick(View view) {
                int selectrdId = seleccionar.getCheckedRadioButtonId();
                if(selectrdId == -1){
                    Toast.makeText(getActivity(), "Seleccione un plan", Toast.LENGTH_SHORT).show();
                }else{
                rbSelected = seleccionar.findViewById(selectrdId);
                nameplan = rbSelected.getText().toString();
                    try {
                        aut.update(nameplan);
                        Toast.makeText(getActivity(), "Plan actualizado con éxito", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(getActivity(), "Error al actualizar el plan", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
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
                                    txtpower1.setText(data.get("Cant_Watts").toString());
                                    txtnombre1.setText(data.get("Nombre").toString());
                                    txtprecio1.setText(data.get("Valor_plan").toString());
                                }
                            }
                        } else {

                        }
                    }
                });
    }

    public void marcarPlanSeleccionado(String planId) {
        int numOpcion = 0;
        switch (planId) {
            case "plan1":
                numOpcion = 0;
                break;
            case "plan2":
                numOpcion = 1;
                break;
            case "plan3":
                numOpcion = 2;
                break;
            case "planprueba":
                numOpcion = 3;
                break;
        }

        // Obtener una referencia al botón de opción correspondiente
        RadioButton radioButton = (RadioButton) planRadioGroup.getChildAt(numOpcion);

        // Establecer el botón de opción como seleccionado
        radioButton.setChecked(true);
    }
}