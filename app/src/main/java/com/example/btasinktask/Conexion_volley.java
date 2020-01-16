package com.example.btasinktask;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Conexion_volley {
    /*
    public Conexion_volley() {
    }*/

    AlertDialog.Builder dialogo, dialogo1;
    private ProgressDialog pd;
    //Variable para crear mis propios cuadros de dialogo.
    Dialog myDialog;

    /*
    public String getTelPropietario(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("Destinatarios", Context.MODE_PRIVATE);
        return preferences.getString("telefono0", "Sin configurar.");
    }

    public String getTelReceptor(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("Destinatarios", Context.MODE_PRIVATE);
        return preferences.getString("telefono1", "Sin configurar.");
    }

    public static String obtenerTelPropietario(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("Destinatarios", Context.MODE_PRIVATE);
        return preferences.getString("telefono0", "Sin configurar.");
    }

    public static String obtenerTelReceptor(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("Destinatarios", Context.MODE_PRIVATE);
        return preferences.getString("telefono1", "Sin configurar.");
    }

    public static String obtenerServer(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        String server = preferences.getString("servidor", "Sin configurar.");
        String folder = preferences.getString("folder", "Sin configurar.");
        return server + folder;
    }*/

    public static String getServer(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        String server = preferences.getString("servidor", "Sin configurar.");
        String folder = preferences.getString("folder", "Sin configurar.");
        return server + folder;
    }

    public String getDate(){
        //Función para obtener la fecha.
        //DateFormat formatodate = new SimpleDateFormat("yyyy/MM/dd");
        DateFormat formatodate = new SimpleDateFormat("yyyy-MM-dd");
        String date = formatodate.format(new Date());
        return date;
    }

    public String getTime(){
        //Función para obtener la hora.
        //DateFormat formatotime = new SimpleDateFormat("HH:mm:ss a");
        //DateFormat formatotime = new SimpleDateFormat("HH:mm:ss");
        DateFormat formatotime = new SimpleDateFormat("hh:mm:ss");
        String time = formatotime.format(new Date());
        return time;
    }


    public void sendInfoServer(final Context context, final String descripcion, final String spo2, final String frec_cardiaca, final String diastolic, final String systolic, final String pulse_min, final String frec_respiratoria, final String temp_corporal, final String alarma, final String fecha, final String hora, final String responsable_especialista) {
        //Toast.makeText(context, "entreeeee....", Toast.LENGTH_SHORT).show();
        //String url_guardar_destinatarios = pc + "/service/registrophone.php";
        //String url_guardar_destinatarios = "http://" + pc + "/service/registrophone.php";
        //String url_guardar_destinatarios = "http://mjgl.com.sv/UTLA/service/registrophone.php";
        //String url_guardar_destinatarios = pc + "/registrophone.php";
        //String url_guardar_destinatarios = pc + "/registrarInfo.php";  //registrar_sensores.php

        String pc = getServer(context).trim();
        //String url_guardar_destinatarios = "http://mjgl.com.sv/HOSPITAL/service/registrar_sensores.php";
        String url_guardar_destinatarios = pc + "/registrar_sensores.php";

        //inicio
        //ACTUALIZO DATOS INGRESADOS EN LA ACTIVITY EN LA BD.
        /*StringRequest request = new StringRequest(Request.Method.POST, url_guardar_destinatarios, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {*/

        StringRequest request = new StringRequest(Request.Method.POST,
                url_guardar_destinatarios,
                new Response.Listener<String>() {
                    //@RequiresApi(api = Build.VERSION_CODES.M)
                    //@SuppressLint("ResourceType")
                    @Override
                    public void onResponse(String response) {
                        Log.d("string",response);
                    try{
                    //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                    JSONObject respuestaJSON = new JSONObject(response.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                    //Accedemos al vector de resultados
                    String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON
                    String result_msj = respuestaJSON.getString("mensaje");   // estado es el nombre del campo en el JSON
                    //if (resultJSON == "1") {
                    if (resultJSON.equals("1")) {
                        Toast.makeText(context, "Datos Guardados Satisfactoriamente", Toast.LENGTH_SHORT).show();
                    } else if (resultJSON.equals("2")) {
                        /*
                        Toast toast = Toast.makeText(context, "--> UTLA." +
                                "\nNo se ha guardado nada. Ya existe.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        */
                        Toast.makeText(context, "No hay nada que actualizar." +
                                "\nNo ha realizado ningún cambio.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Sin Internet...", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("Content-Type", "application/json; charset=utf-8");
                map.put("Accept", "application/json");
                map.put("descripcion", descripcion.toString().trim());
                map.put("spo2", spo2.toString().trim());
                map.put("frec_cardiaca", frec_cardiaca.toString().trim());
                //map.put("tension_arterial", tension_arterial.toString().trim());
                map.put("diastolic", diastolic.toString().trim());
                map.put("systolic", systolic.toString().trim());
                map.put("pulsemin", pulse_min.toString().trim());
                map.put("frec_respiratoria", frec_respiratoria.toString().trim());
                map.put("temp_corporal", temp_corporal.toString().trim());
                map.put("alarma",alarma.toString().trim());
                map.put("fecha", fecha.toString().trim());
                map.put("hora", hora.toString().trim());
                map.put("responsable_especialista", responsable_especialista.toString().trim());
                return map;
            }
        };
        //requestQueue.add(request);
        MySingleton.getInstance(context).addToRequestQueue(request);
    }



}
