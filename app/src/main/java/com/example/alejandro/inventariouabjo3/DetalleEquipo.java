package com.example.alejandro.inventariouabjo3;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DetalleEquipo extends AppCompatActivity {
    TextView desID;
    TextView desNom;
    TextView desArea;
    TextView desMarca;
    TextView desMod;
    TextView desSerie;
    TextView desFac;
    TextView desRec;
    TextView desProv;
    TextView desDes;
    TextView desDate;
    ImageView img_status;
    TextView desStatus;
    TextView desObs;

    Intent intent;
    Bundle bundlei;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_detalle_equipo);
        desID=(TextView)findViewById(R.id.des_id);
        desNom=(TextView)findViewById(R.id.des_nom);
        desArea=(TextView)findViewById(R.id.des_area);
        desMarca=(TextView)findViewById(R.id.des_marca);
        desMod=(TextView)findViewById(R.id.des_mod);
        desSerie=(TextView)findViewById(R.id.des_serie);
        desFac=(TextView)findViewById(R.id.des_fac);
        desRec=(TextView)findViewById(R.id.des_rec);
        desProv=(TextView)findViewById(R.id.des_prov);
        desDes=(TextView)findViewById(R.id.des_des);
        desDate=(TextView)findViewById(R.id.des_date);
        img_status=(ImageView)findViewById(R.id.img_status);
        desStatus=(TextView)findViewById(R.id.statDes);
        desObs=(TextView)findViewById(R.id.des_obs);

        intent=getIntent();
        bundlei=intent.getExtras();
        recuperar();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                finish();

            }
        });
    }

    public void recuperar()
    {


        desID.setText(bundlei.get("id").toString());
        desNom.setText(bundlei.get("Nom_dep").toString());
        desArea.setText(bundlei.get("Area").toString());
        desMarca.setText(bundlei.get("Marca").toString());
        desMod.setText(bundlei.get("Mod").toString());
        desSerie.setText(bundlei.get("Serie").toString());
        desFac.setText(bundlei.get("Fac").toString());
        desRec.setText(bundlei.get("Rec").toString());
        desProv.setText(bundlei.get("Proveedores").toString());
        desDes.setText(bundlei.get("Descrip").toString());
        desDate.setText(bundlei.get("date").toString());

        if(bundlei.getBoolean("status"))
        {
            img_status.setImageResource(R.drawable.ic_ok_black_24dp);
            desStatus.setText("Correcto");
        }
        else
        {
            img_status.setImageResource(R.drawable.ic_incorrect_black_24dp);
            desStatus.setText("Con Observaciones");
        }
        desObs.setText((bundlei.get("Obs").toString()).replace("----","\n"));



    }

}
