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
import android.widget.Toast;

import com.ekn.gruzer.gaugelibrary.ArcGauge;
import com.example.powervisionv2.grafico.FireDatos;
import com.example.powervisionv2.grafico.Medidor;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import androidx.core.app.NotificationCompat;



public class Fragment_inicio extends Fragment {

    ArcGauge IntesidadGauge;
    ArcGauge CorrienteGauge;
    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICACION_ID = 0;
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

                      Double intensidad = IntesidadGauge.getValue();
                      Double corriente = CorrienteGauge.getValue();


                      if(intensidad >= 4.0 || corriente >= 400){
                         /*// Toast.makeText(getActivity(), " ¡Cuidado! tu limite establecido te estas pasando", Toast.LENGTH_SHORT).show();
                          // Crear el canal de notificación
                          NotificationChannel channel = new NotificationChannel("CHANNEL_ID", "Nombre del canal", NotificationManager.IMPORTANCE_DEFAULT);
                          channel.setDescription("Descripción del canal");
                          // Registrar el canal en el NotificationManager
                          NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                          notificationManager.createNotificationChannel(channel);

                          NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "CHANNEL_ID")
                                  .setSmallIcon(R.drawable.powervision)
                                  .setContentTitle("¡Cuidado!")
                                  .setContentText("Estás superando el límite de tu plan de consumo");

                          Log.i("Notificación", "Mostrando notificación");
                          NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                          manager.notify(0, builder.build());*/
                      }



                      //Double amperio = Double.parseDouble(dataSnapshot.child("Amperios").getValue().toString());
                      //Double corriente = Double.parseDouble(dataSnapshot.child("Corriente").getValue().toString());

                      //textView.setText(amperio + " - " + corriente);
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

    /*private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Noticacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }*/
}