package com.example.fernanda.ollainteligente;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.fernanda.ollainteligente.bl.crud.Nuevo;
import com.example.fernanda.ollainteligente.bl.crud.Tablas;

import net.danlew.android.joda.JodaTimeAndroid;


public class MainActivity extends AppCompatActivity {
    Button btnNuevo;
    Button btnVer;
    Button btnBorrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        JodaTimeAndroid.init(this);

        btnNuevo = (Button) findViewById(R.id.btnNuevo);
        btnVer = (Button) findViewById(R.id.btnVer);
        btnBorrar = (Button) findViewById(R.id.btnBorrar);

        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Nuevo.class);
                startActivity(intent);
            }
        });

        btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Tablas.class);
                intent.putExtra("opcion","ver");
                startActivity(intent);
            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Tablas.class);
                intent.putExtra("opcion","borrar");
                startActivity(intent);
            }
        });

    }
}
