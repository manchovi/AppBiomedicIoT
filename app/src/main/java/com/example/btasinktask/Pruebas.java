package com.example.btasinktask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Pruebas extends AppCompatActivity {
    private Button btnSave;

    private EditText et_d, etfc, etsp, etpa, etfr, ettc, eta, etf, eth, eti;

    Conexion_volley volleyBD = new Conexion_volley();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pruebas);

        Toast.makeText(this, "Welcome...", Toast.LENGTH_SHORT).show();
        et_d = (EditText) findViewById(R.id.et_d);
        etfc = (EditText) findViewById(R.id.etfc);
        etsp = (EditText) findViewById(R.id.etsp);
        etpa = (EditText) findViewById(R.id.etpa);
        etfr = (EditText) findViewById(R.id.etfr);
        ettc = (EditText) findViewById(R.id.ettc);
        eta = (EditText) findViewById(R.id.eta);
        etf = (EditText) findViewById(R.id.etf);
        eth = (EditText) findViewById(R.id.eth);
        eti = (EditText) findViewById(R.id.eti);

        etf.setText(volleyBD.getDate());
        eth.setText(volleyBD.getTime());

        btnSave = (Button)findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //volleyBD.sendInfoServer(Pruebas.this, "PRUEBAS SEND DATA TREAM", "50", "51", "52", "53", "54", "0", "2019-10-01", "12:26:02", "28227838");  //02822783-8
                volleyBD.sendInfoServer(Pruebas.this,
                        "Pruebas Finales Del Prototipo Biomédico # 1",
                        "0",
                        "0",
                        "0",
                        "0",
                        "0",
                        "0",
                        "0",
                        "0",
                        volleyBD.getDate(),
                        volleyBD.getTime(),
                        "28227838");
            }
        });
    }


    public void sendInfoServer(final Context context, final String descripcion, final String frec_cardiaca, final String spo2, final String tension_arterial, final String frec_respiratoria, final String temp_corporal, final String alarma, final String fecha, final String hora, final String responsable_especialista) {
        //String pc = obtenerServer(context);

        //Toast.makeText(context, "entreeeee....", Toast.LENGTH_SHORT).show();

        //String url_guardar_destinatarios = pc + "/service/registrophone.php";
        //String url_guardar_destinatarios = "http://" + pc + "/service/registrophone.php";
        //String url_guardar_destinatarios = "http://mjgl.com.sv/UTLA/service/registrophone.php";
        //String url_guardar_destinatarios = pc + "/registrophone.php";
        //String url_guardar_destinatarios = pc + "/registrarInfo.php";  //registrar_sensores.php

        String url_guardar_destinatarios = "http://mjgl.com.sv/HOSPITAL/service/registrar_sensores.php";

        //inicio
        //ACTUALIZO DATOS INGRESADOS EN LA ACTIVITY EN LA BD.
        /*StringRequest request = new StringRequest(Request.Method.POST, url_guardar_destinatarios, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {*/

        StringRequest request = new StringRequest(Request.Method.POST,
                url_guardar_destinatarios,
                new Response.Listener<String>() {
                    //@RequiresApi(api = Build.VERSION_CODES.O)
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

                            Toast.makeText(Pruebas.this, "Dato: "+resultJSON, Toast.LENGTH_SHORT).show();
                            //if (resultJSON == "1") {
                            if (resultJSON.equals("1")) {

                                Toast.makeText(getApplicationContext(), "Datos Guardados...", Toast.LENGTH_SHORT).show();
                                //Intent intent = new Intent(context, SignalsMonitor.class);
                                //context.startActivity(intent);

                                //Toast.makeText(getApplicationContext(), resultJSON + "-" + result_msj, Toast.LENGTH_LONG).show();
                                //System.out.println("RESPUESTA DE SERVIDOR : "+response.toString());

                        /*
                        Toast toast = Toast.makeText(context, "Datos guardados correctamente. \n\nGRACIAS!!!", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();*/

                            } else if (resultJSON == "2") {
                        /*
                        Toast toast = Toast.makeText(context, "--> UTLA." +
                                "\nNo se ha guardado nada. Ya existe.", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        */
                                Toast.makeText(getApplicationContext(), "No hay nada que actualizar." +
                                        "\nNo ha realizado ningún cambio.", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Sin Internet...", Toast.LENGTH_LONG).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("Content-Type", "application/json; charset=utf-8");
                map.put("Accept", "application/json");
                map.put("descripcion", descripcion.toString().trim());
                map.put("frec_cardiaca", frec_cardiaca.toString().trim());
                map.put("spo2", spo2.toString().trim());
                map.put("tension_arterial", tension_arterial.toString().trim());
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
