package com.example.fernanda.ollainteligente.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fernanda.ollainteligente.R;
import com.example.fernanda.ollainteligente.dto.Comida;

import java.util.ArrayList;

public class AdapterComida extends RecyclerView.Adapter<AdapterComida.ViewHolderComida> implements View.OnClickListener{

    ArrayList<Comida> lista;
    Context context;
    private View.OnClickListener listener;


    public AdapterComida(ArrayList<Comida> lista, Context context) {
        this.lista = lista;
        this.context =context;
    }

    public class ViewHolderComida extends ViewHolder {
        TextView TTitulo,TTiempo,TCantidad;

        public ViewHolderComida(@NonNull View itemView) {
            super(itemView);
            TTitulo = (TextView)itemView.findViewById(R.id.TTitulo);
            TTiempo = (TextView)itemView.findViewById(R.id.TTiempo);
            TCantidad = (TextView)itemView.findViewById(R.id.TCantidad);

        }
    }

    @NonNull
    @Override
    public ViewHolderComida onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comida_list, null, false);
        view.setOnClickListener(this);
        return new ViewHolderComida(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderComida viewHolderComida, int i) {
        viewHolderComida.TTitulo.setText(lista.get(i).getTitulo());
        viewHolderComida.TTiempo.setText(lista.get(i).getTiempo()+"min.");
        viewHolderComida.TCantidad.setText(lista.get(i).getCantidad() +"("+lista.get(i).getMedida()+")");

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;

    }

}
