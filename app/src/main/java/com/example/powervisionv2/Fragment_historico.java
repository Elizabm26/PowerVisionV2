package com.example.powervisionv2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.powervisionv2.grafico.Medidor;
import com.github.mikephil.charting.charts.LineChart;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.cert.PolicyNode;
import java.util.HashMap;
import java.util.Map;

public class Fragment_historico extends Fragment {

    private LineChart lineChart;
    private DatabaseReference databaseRef;
    private FirebaseDatabase firebaseDatabase;
    Medidor medidor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_historico, container, false);
        lineChart = view.findViewById(R.id.histLinechart);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseRef = firebaseDatabase.getReference();
        loadDataFromFirebase();

        return view;
    }
    private void loadDataFromFirebase() {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Sensor").child("Lecturas");
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Float> data = new HashMap<>();
                int n = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (n < 20) {
                        data.put(snapshot.getKey(), snapshot.getValue(Float.class));
                        n++;
                    } else {
                        break;
                    }
                }
                // Usa el objeto "medidor" que has creado para generar el gráfico
                medidor = new Medidor();
                lineChart = medidor.GeneratorGraphicsHistory(lineChart, data, "Consumo");

                // Actualiza el gráfico
                lineChart.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("MainActivity", "Error al cargar datos de Firebase", databaseError.toException());
            }
        });
    }


    public void doSomething() {
    }
}