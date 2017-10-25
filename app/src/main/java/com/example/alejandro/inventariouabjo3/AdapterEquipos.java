package com.example.alejandro.inventariouabjo3;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Alejandro on 03/10/2017.
 */

public class AdapterEquipos extends BaseAdapter {
    private ArrayList<Equipo> lista= new ArrayList<Equipo>();
    private Context context;

    public AdapterEquipos(ArrayList<Equipo> pro,Context o)
    {
        lista=pro;
        context=o;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int i) {
        return lista.get(i);
    }

    @Override
    public long getItemId(int i) {
        return lista.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        // permite utilizar un archivo xml para incluirlo en otro component
        LayoutInflater fila = LayoutInflater.from(context);
        //Crear los elementos individuales de la fila al principio lo ponemos como nulo luego lo ocuparemos
        View elementofila= null;
        elementofila=fila.inflate(R.layout.adapter ,null);
        //extraer cada element individual
        ImageView imagen=(ImageView)elementofila.findViewById(R.id.imagenadapter);
        TextView NombreDepo=(TextView) elementofila.findViewById(R.id.depositarioadapter);
        TextView Descripcion=(TextView)elementofila.findViewById(R.id.descripcionadapter);
        ImageView imagescan=(ImageView) elementofila.findViewById(R.id.imagescan);
        //obtenemos exactamen los elementos con las referencias

        if(lista.get(i).getStatus()) {
            imagen.setImageResource(R.drawable.ic_ok_black_24dp);
        }
        else
        {
            imagen.setImageResource(R.drawable.ic_incorrect_black_24dp);
        }
        NombreDepo.setText(lista.get(i).getNom_dep().toString());
        Descripcion.setText(lista.get(i).getDescrip().toString());
        //retorna el view que ya contiene cada uno de los elementos


        if(lista.get(i).getScan())
        {
         imagescan.setImageResource(R.drawable.ic_assigment_ok_48dp);

        }
        else
        {
            imagescan.setImageResource(R.drawable.ic_assigment_error_48dp);
        }

        return elementofila;



    }


}
