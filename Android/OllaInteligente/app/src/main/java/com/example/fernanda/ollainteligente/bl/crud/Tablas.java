package com.example.fernanda.ollainteligente.bl.crud;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.fernanda.ollainteligente.R;
import com.example.fernanda.ollainteligente.adapters.AdapterComida;
import com.example.fernanda.ollainteligente.bl.Comenzar;
import com.example.fernanda.ollainteligente.dto.Comida;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Tablas extends AppCompatActivity {

    ArrayList<Comida> lista= new ArrayList<Comida>();
    AdapterComida adapterComida;
    RecyclerView recyclerComidas;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceP,databaseReferenceG ;
    private ProgressDialog progressDialog;

    Comenzar comenzar;
    String opcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablas);
        getSupportActionBar().hide();
        inicializarFirebase();
        opcion=getIntent().getExtras().getString("opcion");


        recyclerComidas = (RecyclerView) findViewById(R.id.recyclerComidas);
        recyclerComidas.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapterComida = new AdapterComida(lista,getApplicationContext());
        recyclerComidas.setAdapter(adapterComida);

        llenar_lista();

        adapterComida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accion(v);

            }
        });
    }


    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceP= FirebaseDatabase.getInstance().getReference("Comida1");
        databaseReferenceG= FirebaseDatabase.getInstance().getReference("ComidaG");
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Espere a la carga de datos");
        progressDialog.show();

    }

    private void llenar_lista() {
        lista.clear();
        ValueEventListener valueEventListenerG = databaseReferenceG.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    String id =singleSnapshot.child("id").getValue().toString();
                    String tit =singleSnapshot.child("titulo").getValue().toString();
                    String tim =singleSnapshot.child("tiempo").getValue().toString();
                    String can =singleSnapshot.child("cantidad").getValue().toString();
                    String med =singleSnapshot.child("medida").getValue().toString();
                    Comida comida = new Comida(id,tit,Integer.parseInt(tim),can,med);
                    lista.add(comida);
                    adapterComida.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        ValueEventListener valueEventListenerP = databaseReferenceP.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    String id =singleSnapshot.child("id").getValue().toString();
                    String tit =singleSnapshot.child("titulo").getValue().toString();
                    String tim =singleSnapshot.child("tiempo").getValue().toString();
                    String can =singleSnapshot.child("cantidad").getValue().toString();
                    String med =singleSnapshot.child("medida").getValue().toString();
                    Comida comida = new Comida(id,tit,Integer.parseInt(tim),can,med);
                    lista.add(comida);
                    adapterComida.notifyDataSetChanged();

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

    }

    private void accion(View v) {
        Comida n= lista.get(recyclerComidas.getChildAdapterPosition(v));
        if(opcion.equals("ver"))
        {
            comenzar = new Comenzar(n,getApplicationContext());
            Toast.makeText(getApplicationContext(),n.getTitulo()+ " en accion!",Toast.LENGTH_LONG).show();
        }
        if(opcion.equals("borrar"))
        {
           databaseReferenceP.child(n.getId()).removeValue();
           Toast.makeText(getApplicationContext(),n.getTitulo()+ " borrado",Toast.LENGTH_LONG).show();
            lista.clear();
            adapterComida.notifyDataSetChanged();
        }


    }

}
