package com.example.fernanda.ollainteligente.bl;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.fernanda.ollainteligente.R;
import com.example.fernanda.ollainteligente.dto.Comida;
import com.example.fernanda.ollainteligente.dto.Datos;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.DateTime;
import org.joda.time.Minutes;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.NOTIFICATION_SERVICE;

public class Comenzar {
    Comida comida;
    Context context;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    Datos datos = new Datos(0, 0, 0, 0);
    DateTime tiempo_inicial = new DateTime();
    boolean agua_hervida = false;
    int tiempo_cambio=0;

    public Comenzar(Comida comida, Context context) {
        this.comida = comida;
        this.context = context;
        star();

    }

    public void star() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        int time = 35;
        if (comida.getTiempo()!= 0) {
            time = comida.getTiempo();
        }
        CountDownTimer countDownTimer = new CountDownTimer(time * 60000, 30000) { // cada 30 segundos
            @Override
            public void onTick(long millisUntilFinished) {
                if (notificacion()) {
                    actualizar_comida();
                    cancel();
                }
            }

            @Override
            public void onFinish() {
                notificar("La comida esta lista!");
                actualizar_comida();
            }
        }.start();

    }

    private void notificar(String mensaje) {
        NotificationCompat.Builder notificacion;
        final int idUnica = 51623;

        notificacion = new NotificationCompat.Builder(context);
        notificacion.setAutoCancel(true);
        notificacion.setColor(Color.GREEN);
        notificacion.setVibrate(new long[]{0, 1000, 500, 1000});
        notificacion.setSmallIcon(R.drawable.ollanoti);
        notificacion.setTicker(mensaje);
        notificacion.setPriority(Notification.PRIORITY_HIGH);
        notificacion.setWhen(System.currentTimeMillis());
        notificacion.setContentTitle("Cocina Inteligente");
        notificacion.setContentText(mensaje);
        notificacion.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.mus_noti));

        NotificationManager nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        nm.notify(idUnica, notificacion.build());


    }

    private boolean notificacion() {
        leer_datos_firebase();
        int pir = datos.getPir();
        int gas = datos.getGas();
        float temp = datos.getTemp();
        int time = datos.getTime();
        boolean notificado = false;
        if (pir == 0) // esta ausente
        {
            if (gas >= 400) // el gas esta por encima de la normal
            {
                notificar("El gas esta por encima de lo normal!");
            } else {
                if (temp >= 83) { //la temperatura es mayor a 83C
                    if (agua_hervida) {// el agua esta hervida
                        if (time >= 10) { //15 paso mas de 15 minutos
                            notificar("La comida esta lista!");
                            notificado = true;
                            tiempo_cambio+=time;
                        }
                    }else {
                        agua_hervida = true;
                        tiempo_cambio=time;
                        tiempo_inicial = new DateTime();
                        notificar("El agua esta hervida!");
                    }

                }

            }

        }
        return notificado;
    }

    private void leer_datos_firebase() {
        Query query = databaseReference.child("accion").limitToLast(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    int gas = Integer.parseInt(singleSnapshot.child("gas").getValue().toString());
                    int pir = Integer.parseInt(singleSnapshot.child("pir").getValue().toString());
                    float temp = Float.parseFloat(singleSnapshot.child("temp").getValue().toString());
                    int time = calcular_tiempo();
                    datos = new Datos(pir, gas, temp, time);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private int calcular_tiempo() {
        DateTime tiempo_actual = new DateTime();
        int restaM = Minutes.minutesBetween(tiempo_inicial, tiempo_actual).getMinutes();
        return restaM;
    }


    private void actualizar_comida() {
        String id = comida.getId();
        if(!TextUtils.isEmpty(id))
        {
            Map<String, Object> comidaMap = new HashMap<>();
            comidaMap.put("id", comida.getId());
            comidaMap.put("cantidad", comida.getId());
            comidaMap.put("medida", comida.getMedida());
            comidaMap.put("tiempo", tiempo_cambio);
            comidaMap.put("titulo", comida.getTitulo());
            databaseReference.child("Comida1").child(comida.getId()).updateChildren(comidaMap);
        }
    }

}




