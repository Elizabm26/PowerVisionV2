package com.example.powervisionv2;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ekn.gruzer.gaugelibrary.ArcGauge;
import com.example.powervisionv2.grafico.FireDatos;
import com.example.powervisionv2.grafico.Medidor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Fragment_inicio extends Fragment {

    ArcGauge IntesidadGauge;
    ArcGauge CorrienteGauge;

    private DatabaseReference databaseRef;
    private FirebaseDatabase firebaseDatabase;

      @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
          Context context = getActivity().getApplicationContext();
        // Inflate the layout for this fragment
          View view = inflater.inflate(R.layout.fragment_inicio, container, false);
          // obtener la raferiancia de la grafica
          IntesidadGauge = view.findViewById(R.id.Itensidad);
          CorrienteGauge = view.findViewById(R.id.Corriente);
          Double intensidad = IntesidadGauge.getValue();
          Double corriente = CorrienteGauge.getValue();


          //llamar la clase medidor
          Medidor medidor= new Medidor();
          medidor.GeneratorGraphicsIntensidad(IntesidadGauge);
          medidor.GeneratorGraphicsCorriente(CorrienteGauge);

          // Obtiene una referencia a la base de datos
          firebaseDatabase = FirebaseDatabase.getInstance();
          databaseRef = firebaseDatabase.getReference();

          // Agrega un ValueEventListener para escuchar los cambios en los datos
          databaseRef.child("Sensor").addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  TextView textView = view.findViewById(R.id.txtDatos);

                  for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){

                      //Asignamos datos a los garficos
                      FireDatos fireDatos= new FireDatos();

                        fireDatos.setINTENSIDAD(Double.parseDouble(dataSnapshot.child("Amperios").getValue().toString()));
                        fireDatos.setCORRIENTE(Double.parseDouble(dataSnapshot.child("Corriente").getValue().toString()));

                        IntesidadGauge.setValue(fireDatos.getINTENSIDAD());
                        CorrienteGauge.setValue(fireDatos.getCORRIENTE());


                      //Double amperio = Double.parseDouble(dataSnapshot.child("Amperios").getValue().toString());
                      //Double corriente = Double.parseDouble(dataSnapshot.child("Corriente").getValue().toString());

                      //textView.setText(amperio + " - " + corriente);
                  }
                  if(intensidad >= 4 || corriente >= 400){
                      NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                              .setSmallIcon(R.drawable.powervision)
                              .setContentTitle("Cuidado !!!!")
                              .setContentText("Estas superando el limite de tu plan de consumo");
                      NotificationManager manager = (NotificationManager)
                              getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                      manager.notify(0,builder.build());
                  }
              }

              @Override
              public void onCancelled(@NonNull DatabaseError databaseError) {
                  // El método onCancelled se ejecuta si hay un error al obtener los datos
                  // Puedes mostrar un mensaje de error o realizar otra acción aquí
                  // ...
              }
          });

          return view;
    }
    public void doSomething() {
    }
}