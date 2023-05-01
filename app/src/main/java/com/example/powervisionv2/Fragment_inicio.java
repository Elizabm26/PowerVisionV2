package com.example.powervisionv2;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
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


public class Fragment_inicio extends Fragment {
    public static final String MY_CHANNEL_ID = "myChannel";
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

                    if(intensidad >= 4 || corriente >= 400){
                        createChannel();
                        createSimpleNotification();
                    }



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
    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    MY_CHANNEL_ID,
                    "MySuperChannel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            channel.setDescription("SUSCRIBETE");

            NotificationManager notificationManager = requireActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    @SuppressLint("MissingPermission")
    private void createSimpleNotification() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        int flag = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) ? PendingIntent.FLAG_IMMUTABLE : 0;
        PendingIntent pendingIntent = PendingIntent.getActivity(requireContext(), 0, intent, flag);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(requireContext(), MY_CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_delete)
                .setContentTitle("EXCESO DE CONSUMO")
                .setContentText("Estás susperando el límite de consumo")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Recuerda el plan que haz contratado, y no sobrepases el límite... desconecta dispositivos que estén consumiendo energía"))
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(requireContext());
        notificationManager.notify(1, builder.build());
    }
}