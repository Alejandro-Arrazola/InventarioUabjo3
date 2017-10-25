package com.example.alejandro.inventariouabjo3;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
//
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    public final int value = 123;
    private Equipo equipo;
    private ArrayList<Equipo> lista;
    private ListView listView;
    public String observacionesm;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_save:
                    try
                    {
                        new PostgreSqlUPDATE().execute();
                    }
                    catch (Exception e)
                    {
                        Log.e("error","Error de Actualizacion");
                        Toast.makeText(getApplicationContext(),"Error de Actualizacion",Toast.LENGTH_SHORT).show();
                    }

                    return true;
                case R.id.menu_scan:
                    Intent intent = new Intent(getApplicationContext(), barcode.class);
                    startActivityForResult(intent, value);
                    return true;
                case R.id.menu_exportar:
                    try
                    {
                        new PostgreSqlJDBC().execute();
                    }catch (Exception e)
                    {
                        Log.e("error","Error de Actualizacion");
                        Toast.makeText(getApplicationContext(),"Error de Conexion",Toast.LENGTH_SHORT).show();
                    }

                    //Intent intent1=new Intent();
                    //intent1.setClass(getApplicationContext(),DetalleEquipo.class);
                    //startActivity(intent1);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=new Intent();
        intent.setClass(getApplicationContext(),splash.class);
        startActivity(intent);
        lista=new ArrayList<Equipo>();
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        listView=(ListView)findViewById(R.id.inventariolista);
        listView.setOnItemClickListener(itemclick);
        //sqlThread.start();

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (resultCode == RESULT_OK) {
            final String mensaje= data.getStringExtra("resultado");
            equipo=new Equipo(mensaje);
            //prueba.setText(data.getStringExtra("resultado"));
            Toast.makeText(getApplicationContext(), "INFORMACION RECIBIDA", Toast.LENGTH_SHORT).show();
            LayoutInflater layoutInflater = LayoutInflater.from(this);
            View promptView = layoutInflater.inflate(R.layout.dialog, null);
            final AlertDialog alertD = new AlertDialog.Builder(this).create();
            final TextView userInput = (TextView) promptView.findViewById(R.id.text);
            userInput.setText(mensaje);
            Button btnAdd1 = (Button) promptView.findViewById(R.id.DarAlta);
            Button btnAdd2 = (Button) promptView.findViewById(R.id.visualizar);
            Button btnadd3 = (Button) promptView.findViewById(R.id.editar);
            btnAdd1.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View view) {
                                                       equipo.setStatus(true);
                                                       userInput.setText(mensaje);
                                                       busqueda(equipo,"");
                                                       //lista.add(new Equipo(equipo));
                                                       listView.setAdapter(new AdapterEquipos(lista,getApplicationContext()));
                                                       alertD.cancel();
                                                   }
                                               });
            btnAdd2.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent();
                    intent.putExtra("id",equipo.getId());
                    intent.putExtra("Nom_dep",equipo.getNom_dep());
                    intent.putExtra("Area",equipo.getArea());
                    intent.putExtra("Marca",equipo.getMarca());
                    intent.putExtra("Mod",equipo.getMod());
                    intent.putExtra("Serie",equipo.getSerie());
                    intent.putExtra("Fac",equipo.getFac());
                    intent.putExtra("Rec",equipo.getRec());
                    intent.putExtra("Proveedores",equipo.getProveedores());
                    intent.putExtra("Descrip",equipo.getDescrip());
                    intent.putExtra("date",equipo.getDate());
                    intent.putExtra("Obs",equipo.getObservaciones());
                    equipo.setStatus(true);
                    intent.putExtra("status",equipo.getStatus());
                    intent.setClass(getApplicationContext(),DetalleEquipo.class);
                    startActivity(intent);
                }
            });

            btnadd3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    observacion();
                    alertD.cancel();
                }
            });
            alertD.setView(promptView);
            alertD.show();


        }
        if (resultCode == RESULT_CANCELED) {

            Toast.makeText(getApplicationContext(), "ESCANEO CANCELADO", Toast.LENGTH_SHORT).show();

            //mTextMessage.setText(data.getStringExtra("resultado"));
        }


    }
    public String observacion()
    {
        final String[] Observaciones = {""};
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.observaciones, null);
        final AlertDialog alertD = new AlertDialog.Builder(this).create();
        final EditText userInput = (EditText)promptView.findViewById(R.id.textobser);
        Button AgregarOb = (Button) promptView.findViewById(R.id.agregarob);
        Button cancelarob = (Button) promptView.findViewById(R.id.cancelarob);
        AgregarOb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                observacionesm =userInput.getText().toString();
                equipo.setStatus(false);
                busqueda(equipo,observacionesm);
                //mTextMessage.setText("Bienvenidididni");
                //lista.add(new Equipo(equipo));
                listView.setAdapter(new AdapterEquipos(lista,getApplicationContext()));
                alertD.cancel();
            }
        });
        cancelarob.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertD.cancel();
            }
        });
        alertD.setView(promptView);
        alertD.show();
        mTextMessage.setText(observacionesm);
        return observacionesm;
    }

    public AdapterView.OnItemClickListener itemclick=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent=new Intent();
            intent.putExtra("id",lista.get(i).getId());
            intent.putExtra("Nom_dep",lista.get(i).getNom_dep());
            intent.putExtra("Area",lista.get(i).getArea());
            intent.putExtra("Marca",lista.get(i).getMarca());
            intent.putExtra("Mod",lista.get(i).getMod());
            intent.putExtra("Serie",lista.get(i).getSerie());
            intent.putExtra("Fac",lista.get(i).getFac());
            intent.putExtra("Rec",lista.get(i).getRec());
            intent.putExtra("Proveedores",lista.get(i).getProveedores());
            intent.putExtra("Descrip",lista.get(i).getDescrip());
            intent.putExtra("date",lista.get(i).getDate());
            intent.putExtra("Obs",lista.get(i).getObservaciones());

            intent.putExtra("status",lista.get(i).getStatus());

            //Toast.makeText(getApplicationContext(),lista.get(i).toString(),Toast.LENGTH_SHORT).show();
             intent.setClass(getApplicationContext(),DetalleEquipo.class);
            startActivity(intent);
        }
    };
    public AdapterView.OnItemLongClickListener itemlongclick=new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent=new Intent();
            intent.putExtra("id",lista.get(i).getId());
            intent.putExtra("Nom_dep",lista.get(i).getNom_dep());
            intent.putExtra("Area",lista.get(i).getArea());
            intent.putExtra("Marca",lista.get(i).getMarca());
            intent.putExtra("Mod",lista.get(i).getMod());
            intent.putExtra("Serie",lista.get(i).getSerie());
            intent.putExtra("Fac",lista.get(i).getFac());
            intent.putExtra("Rec",lista.get(i).getRec());
            intent.putExtra("Proveedores",lista.get(i).getProveedores());
            intent.putExtra("Descrip",lista.get(i).getDescrip());
            intent.putExtra("date",lista.get(i).getDate());
            intent.putExtra("status",lista.get(i).getStatus());
            //Toast.makeText(getApplicationContext(),lista.get(i).toString(),Toast.LENGTH_SHORT).show();
            intent.setClass(getApplicationContext(),DetalleEquipo.class);
            startActivity(intent);
            return true;
        }
    };


    public class PostgreSqlJDBC extends AsyncTask<Void, Void, Void> {
        String mensajess="";
        Equipo postgres;
        ArrayList<Equipo> consulta;
        @Override
        protected void onPostExecute(Void aVoid) {
            try
            {
                lista=consulta;
                listView.setAdapter(new AdapterEquipos(lista,getApplicationContext()));
            }
            catch (Exception e)
            {
                System.out.println("ERROR   ");
            }

        }
        @Override
        public Void doInBackground(Void ... params) {
            Connection conexion = null;
            try {
                Class.forName("org.postgresql.Driver");
                //String url="jdbc:postgresql://"+   params[0]/*192.168.137.1*/+":5432/InventarioLab";
                String url="jdbc:postgresql://192.168.137.1:5432/InventarioLab";
                conexion = DriverManager.getConnection(url, "postgres", "ADMIN");
                System.out.println("Connection Successfull");
                String stsql = "select * from equipo";
                Statement st = conexion.createStatement();
                ResultSet rs = st.executeQuery(stsql);
                //rs.next();
                consulta=new ArrayList<Equipo>();
                while(rs.next())
                {
                    mensajess="";
                    for(int i = 1;i<12;i++)
                    {
                        mensajess=mensajess+rs.getString(i)+",";
                    }
                    mensajess=mensajess+rs.getString(12);
                    consulta.add(new Equipo(mensajess));
                }
                conexion.close();
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println(e.getMessage());
                System.err.println("Error: Cant connect!");
                System.err.println("----- PostgreSQL query ends correctly!-----");
                return null;
            }
            return null;
        }
    }
    ////////////////////////


    public class PostgreSqlUPDATE extends AsyncTask<Void, Void, Void> {
        String sqlupdate="";
        Equipo postgres;
        ArrayList<Equipo> arrayrec;
        @Override
        protected void onPostExecute(Void aVoid) {
//            mTextMessage.setText(sqlupdate);
        }
        @Override
        public Void doInBackground(Void ... params) {
            try {

            arrayrec=lista;
            for(int i=0;i<arrayrec.size();i++)
            {
                postgres=arrayrec.get(i);
                sqlupdate=sqlupdate+postgres.update();

            }
            Connection conexion = null;

                Class.forName("org.postgresql.Driver");

                String url="jdbc:postgresql://192.168.137.1:5432/InventarioLab";
                conexion = DriverManager.getConnection(url, "postgres", "ADMIN");
                System.out.println("Connection Successfull");
                Statement st = conexion.createStatement();
                String sqlupdates[]=sqlupdate.split(";");
                for(int i=0;i<sqlupdates.length;i++)
                {

                    st.executeUpdate(sqlupdates[i]);
                }

                conexion.close();
            }
            catch (Exception e) {
                e.printStackTrace();
                System.err.println(e.getMessage());
                System.err.println("Error: Cant connect!");
                System.err.println("----- PostgreSQL query ends correctly!-----");


            }




            return null;
        }
    }
    /////////////////



    public String busqueda(Equipo x,String o)
    {
        boolean status;
        String obss;
        String ll="no encontrado";
        for(int i=0;i<lista.size();i++)
        {
            if(x.getId()==lista.get(i).getId())
            {
                status=equipo.getStatus();
                equipo=lista.remove(i);
                equipo.setScan(true);
                equipo.setStatus(status);
                if(o!="")
                {
                    equipo.setObservaciones(o);
                }
                lista.add(i,new Equipo(equipo
                ));
            }
        }
        return ll;
    }


}
