package com.example.alejandro.inventariouabjo3;

import android.icu.text.SimpleDateFormat;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Alejandro on 25/09/2017.
 */

public class Equipo {

    public int id;//id
    public String  nom_dep;//nombre depositario
    public String area;//area
    public String marca;//marca
    public String mod;//modelo
    public String serie;//serie
    public String fac;//factura
    public String rec;//recurso
    public String proveedores;//proveedores
    public String descrip;//descripcion
    public String date;//fecha
    public String observaciones;
    public Boolean status;
    public Boolean scan=false;


    public Equipo(int id, String nom_dep, String area, String marca, String mod, String serie, String fac, String rec, String proveedores, String descrip, String date, String observaciones, Boolean status, Boolean scan) {
        this.id = id;
        this.nom_dep = nom_dep;
        this.area = area;
        this.marca = marca;
        this.mod = mod;
        this.serie = serie;
        this.fac = fac;
        this.rec = rec;
        this.proveedores = proveedores;
        this.descrip = descrip;
        this.date = date;
        this.observaciones = observaciones;
        this.status = status;
        this.scan = scan;
    }

    public Equipo(Equipo n) {
        this.id = n.getId();
        this.nom_dep = n.getNom_dep();
        this.area = n.getArea();
        this.marca = n.getMarca();
        this.mod = n.getMod();
        this.serie = n.getSerie();
        this.fac = n.getFac();
        this.rec = n.getRec();
        this.proveedores = n.getProveedores();
        this.descrip = n.getDescrip();
        this.date = n.getDate();
        this.observaciones=n.getObservaciones();
        this.status=n.getStatus();
        this.scan=n.getScan();

    }
    public Equipo(String cadena)
    {
        String divi[]=cadena.split(",");
        this.id = Integer.parseInt(divi[0]);
        this.nom_dep = divi[1];
        this.area = divi[2];
        this.marca = divi[3];
        this.mod = divi[4];
        this.serie = divi[5];
        this.fac = divi[6];
        this.rec = divi[7];
        this.proveedores = divi[8];
        this.descrip = divi[9];
        this.date = divi[10];
        this.observaciones=divi[11];
        this.status=true;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_dep() {
        return nom_dep;
    }

    public void setNom_dep(String nom_dep) {
        this.nom_dep = nom_dep;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getMod() {
        return mod;
    }

    public void setMod(String mod) {
        this.mod = mod;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getFac() {
        return fac;
    }

    public void setFac(String fac) {
        this.fac = fac;
    }

    public String getRec() {
        return rec;
    }

    public void setRec(String rec) {
        this.rec = rec;
    }

    public String getProveedores() {
        return proveedores;
    }

    public void setProveedores(String proveedores) {
        this.proveedores = proveedores;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {


        this.observaciones =getObservaciones()+"----"+fecha()+"  "+observaciones;
    }
    public void setObservacionesnew(String observaciones) {


        this.observaciones =observaciones;
    }

    public String fecha()
    {
        Calendar cal=Calendar.getInstance();
        int mesmes= cal.get(Calendar.MONTH);

        mesmes=mesmes+1;
        String mes=(String.valueOf(mesmes)).concat("-");
        String dia=(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
        String año=(String.valueOf(cal.get(Calendar.YEAR))).concat("-");
        String fecha=año+mes+dia;
        return fecha;
    }
    public Boolean getScan() {
        return scan;
    }

    public void setScan(Boolean scan) {
        this.scan = scan;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Equipo{" +
                "id=" + id +
                ", nom_dep='" + nom_dep + '\'' +
                ", area='" + area + '\'' +
                ", marca='" + marca + '\'' +
                ", mod='" + mod + '\'' +
                ", serie='" + serie + '\'' +
                ", fac='" + fac + '\'' +
                ", rec='" + rec + '\'' +
                ", proveedores='" + proveedores + '\'' +
                ", descrip='" + descrip + '\'' +
                ", date='" + date + '\'' +
                '}';
    }



    public String update()
    {

        String sql="";
        if(getScan())
        {
            sql="UPDATE equipo SET observaciones='"+getObservaciones()+"Scaneado en  "+fecha()+"' where id="+getId()+";";
        }
        else
        {
            sql="UPDATE equipo SET observaciones='"+getObservaciones()+"Sin Scanear en  "+fecha()+"' where id="+getId()+";";
        }


        return sql;
    }
}
