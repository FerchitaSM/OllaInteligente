package com.example.fernanda.ollainteligente.bl.crud;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fernanda.ollainteligente.R;
import com.example.fernanda.ollainteligente.bl.Comenzar;
import com.example.fernanda.ollainteligente.dto.Comida;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class Nuevo extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button btnSi,btnNo;
    EditText EdTitulo,EdTiempo, EdCantidad;
    Spinner SpMedida;
    String titulo,cantidad,medida;
    int tiempo;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReferenceP;

    Comenzar comenzar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);
        getSupportActionBar().hide();
        inicializarFirebase();

        btnSi = (Button) findViewById(R.id.btnSi);
        btnNo = (Button) findViewById(R.id.btnNo);
        EdTitulo = (EditText) findViewById(R.id.EdTitulo);
        EdTiempo = (EditText) findViewById(R.id.EdTiempo);
        EdCantidad = (EditText) findViewById(R.id.EdCantidad);

        SpMedida = (Spinner) findViewById(R.id.SpMedida);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.medidas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpMedida.setAdapter(adapter);
        SpMedida.setOnItemSelectedListener(this);

        btnSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validar())
                {
                    Comida n = new Comida( UUID.randomUUID().toString(),titulo,tiempo,cantidad,medida);
                    databaseReferenceP.child("Comida1").child(n.getId()).setValue(n);
                    Toast.makeText(getApplicationContext(),n.getTitulo()+" en accion!",Toast.LENGTH_LONG).show();
                    comenzar = new Comenzar(n,getApplicationContext());
                }
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validar())
                {
                    Comida n = new Comida( UUID.randomUUID().toString(),titulo,tiempo,cantidad,medida);
                    Toast.makeText(getApplicationContext(),n.getTitulo() +" en accion!",Toast.LENGTH_LONG).show();
                    comenzar = new Comenzar(n,getApplicationContext());
                }
            }
        });



    }

    private boolean validar(){

        titulo=EdTitulo.getText().toString().trim();
        tiempo= Integer.parseInt(EdTiempo.getText().toString().trim());
        cantidad=EdCantidad.getText().toString().trim();
        if (TextUtils.isEmpty(titulo)|| TextUtils.isEmpty(cantidad)) { // validar que los espacios esten llenos
            Toast.makeText(getApplicationContext(), "no debe existir campos vacios"+tiempo, Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceP = firebaseDatabase.getReference();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        medida = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }
}
