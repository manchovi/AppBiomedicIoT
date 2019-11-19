package com.example.btasinktask;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class db_SQLite extends SQLiteOpenHelper {

    public db_SQLite(Context context){
        super(context, "db_hospital.db",null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("create table usuarios(codigo integer not null primary key autoincrement,nombres varchar(50) not null,apellidos varchar(50) not null,usuario varchar(100) not null,clave varchar(10) not null,pregunta varchar(100) not null,respuesta varchar(100) not null, fecha datetime NOT NULL)");
        db.execSQL("create table tb_especialista(documento varchar(100) not null primary key,nombres varchar(50) not null,apellidos varchar(50) not null,direccion varchar(150),telefono varchar(15) not null,especialidad varchar(100),sexo varchar(15) not null, comentario varchar(250) not null, usuario varchar(100) not null, clave varchar(50) not null, pregunta varchar(100) not null,respuesta varchar(100) not null, fecha datetime NOT NULL)");
        //db.execSQL("insert into usuarios values(null,'Administrador','admin','admin@utla.edu.sv','admin','¿Cuál es el nombre de tu universidad favorita?','UTLA',datetime())");
        db.execSQL("insert into tb_especialista values('1', 'Manuel de Jesús', 'Gámez López', 'Cas, San Nicolas Lempa, Tecoluca-San Vicente', '+50361107065', 'Docente Investigador', 'Masculino', 'N.A', 'manuel.gamez@itca.edu.sv', '12345', '¿Cuál es el nombre de tu universidad favorita?', 'MEGATEC-ZACATECOLUCA', datetime('now','localtime'))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists tb_especialista");
        onCreate(db);
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    //Función para insertar datos.
    //DTO: Data Transfer Object
    public boolean insertardatos(dto datos){
        boolean estado = true;
        int resultado;
        ContentValues registro = new ContentValues();
        try{
            registro.put("documento",datos.getDocumento());
            registro.put("nombres",datos.getNombres());
            registro.put("apellidos",datos.getApellidos());
            registro.put("direccion", datos.getDireccion());
            registro.put("telefono", datos.getTelefono());
            registro.put("especialidad", datos.getEspecialidad());
            registro.put("sexo", datos.getSexo());
            registro.put("comentario", datos.getComentario());
            registro.put("usuario", datos.getEmail());
            registro.put("clave", datos.getClave());
            registro.put("pregunta", datos.getPregunta());
            registro.put("respuesta", datos.getRespuesta());
            registro.put("fecha", datos.getFecha());

            /*adicione estas lineas para verificar sino existe un usuario con el mismo correo
              INICIO.*/
            Cursor fila = this.getWritableDatabase().rawQuery("select usuario from tb_especialista where usuario='"+datos.getEmail()+"'", null);
            if(fila.moveToFirst()==true){
                estado = false;
            }else {
                /*FIN*/
                //estado = (boolean)this.getWritableDatabase().insert("datos","nombre, correo, telefono",registro);
                resultado = (int) this.getWritableDatabase().insert("tb_especialista", "documento,nombres,apellidos,direccion,telefono,especializacion,sexo,comentario,usuario,clave,pregunta,respuesta,fecha", registro);
                if (resultado > 0) estado = true;
                else estado = false;
            }
        }catch (Exception e){
            estado = false;
            Log.e("error.",e.toString());
        }
        return estado;
    }

    public boolean addRegister(dto datos){
        boolean estado = true;
        int resultado;
        try{

            String documento = datos.getDocumento();
            String nombres = datos.getNombres();
            String apellidos = datos.getApellidos();
            String direccion = datos.getDireccion();
            String telefono = datos.getTelefono();
            String especialidad = datos.getEspecialidad();
            String sexo = datos.getSexo();
            String comentario = datos.getComentario();
            String usuario = datos.getEmail();
            String clave = datos.getClave();
            String pregunta = datos.getPregunta();
            String respuesta = datos.getRespuesta();
            //String fecha = datos.getFecha();

            //getting the current time for joining date
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fecha1 = sdf.format(cal.getTime());

            Cursor fila = this.getWritableDatabase().rawQuery("select usuario from tb_especialista where usuario='"+datos.getEmail()+"'", null);
            if(fila.moveToFirst()==true){
                estado = false;
            }else {
                //estado = (boolean)this.getWritableDatabase().insert("datos","nombre, correo, telefono",registro);
                //resultado = (int) this.getWritableDatabase().insert("usuarios", "nombres,apellidos,usuario,clave,pregunta,respuesta", registro);
                String SQL = "INSERT INTO tb_especialista \n" +
                        "(documento,nombres,apellidos,direccion,telefono,especialidad,sexo,comentario,usuario,clave,pregunta,respuesta,fecha)\n" +
                        "VALUES \n" +
                        "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

                //resultado = (int) this.getWritableDatabase().insert("usuarios", "nombres,apellidos,usuario,clave,pregunta,respuesta", registro);
                this.getWritableDatabase().execSQL(SQL, new String[]{documento,nombres,apellidos,direccion,telefono,especialidad,sexo,comentario,usuario,clave,pregunta,respuesta,fecha1});
                //if (resultado > 0) estado = true;
                //else estado = false;
                estado = true;
            }
        }catch (Exception e){
            estado = false;
            Log.e("error.",e.toString());
        }
        return estado;
    }





    //Métodos de los usuarios: En este caso los especialistas de atención sanitaria.

    //Función para consultar la existencia del nombre de usuario o dirección de correo.
    public boolean consultaUser(dto datos) {
        boolean estado = false;
        SQLiteDatabase bd = this.getWritableDatabase();
        try {
            String user = datos.getEmail();
            Cursor fila = bd.rawQuery("select usuario from tb_especialista where usuario='"+user+"'", null);
            if(fila.moveToFirst()){
                estado = true;
            }else{
                estado = false;
            }
            bd.close();
        } catch (Exception e) {
            estado = false;
            Log.e("error.",e.toString());
        }
        return estado;
    }

    //Función para consultar la existencia del nombre de usuario o dirección de correo.
    public boolean verificoRespuesta(dto datos) {
        boolean estado = false;
        SQLiteDatabase bd = this.getWritableDatabase();
        try {
            String user = datos.getRespuesta();
            Cursor fila = bd.rawQuery("select respuesta from tb_especialista where respuesta='"+datos.getRespuesta()+"'", null);
            if(fila.moveToFirst()){
                estado = true;
            }else{
                estado = false;
            }
            bd.close();
        } catch (Exception e) {
            estado = false;
            Log.e("error.",e.toString());
        }
        return estado;
    }

    //Función para actualizar datos
    public boolean updateClave(dto datos){
        //GestionSQLiteOpenHelper gestionSQLiteOpenHelper = new GestionSQLiteOpenHelper(this, "gestionn", null, 1);
        //SQLiteDatabase bd = gestionSQLiteOpenHelper.getWritableDatabase();
        boolean estado = true;
        SQLiteDatabase bd = this.getWritableDatabase();
        try{
            String codigo = datos.getDocumento();
            String clave = datos.getClave();
            ContentValues registro = new ContentValues();
            //registro.put("codigo",codigo);
            registro.put("clave",clave);
            int cant = (int)this.getWritableDatabase().update("tb_especialista", registro, "documento='"+codigo+"'", null);
            //int cant = bd.update("usuarios", registro,"codigo="+codigo,null);
            bd.close();

            if (cant > 0) estado = true;
            else estado = false;
            /*if(cant==1){
                return true;
            }else{
                return false;
            }*/
        }catch(Exception e){
            estado = false;
            Log.e("error.",e.toString());
        }

        return estado;
    }




}
