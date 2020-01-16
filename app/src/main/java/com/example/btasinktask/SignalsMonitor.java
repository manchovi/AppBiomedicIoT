package com.example.btasinktask;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

public class SignalsMonitor extends AppCompatActivity implements MiAsyncTask.MiCallback{

    //Instancia de la clase MiAsyncTask.
    private MiAsyncTask tareaAsincrona;
    SuperClase instanciaSuper = new SuperClase();
    Conexion_volley volleyBD = new Conexion_volley();

    //hiloAsyncTask thread = new hiloAsyncTask();
    private hiloAsyncTask thread;
    private Handler mHandler = new Handler();


    boolean estado_leyendaGraph = false;

    boolean estado_sw = false;
    private double prev, prev1, prev2, prev3, prev4, prev5;

    private double plotCount = 1;
    private double plotCount1 = 1;
    private double plotCount2 = 1;
    private double plotCount3 = 1;
    private double plotCount4 = 1;
    private double plotlen = 121;
    private double plotlen1 = 121;
    private double plotlen2 = 121;
    private double plotlen3 = 121;
    private double plotlen4 = 121;
    private double plotRes = 1;
    private double plotRes1 = 1;
    private double plotRes2 = 1;
    private double plotRes3 = 1;
    private double plotRes4 = 1;

    private boolean aviso = false;

    public static Handler btHandler;
    final int handlerState = 0;        				 //used to identify handler message

    private int conta1=0;
    private LineGraphSeries<DataPoint> dataSeries;
    //dataSeries = new LineGraphSeries<>();

    //LineGraphSeries<DataPoint> dataSeries1 = new LineGraphSeries<>(points);
    private LineGraphSeries<DataPoint> dataSeries1;
    private LineGraphSeries<DataPoint> dataSeries2;
    private LineGraphSeries<DataPoint> dataSeries3;
    private LineGraphSeries<DataPoint> dataSeries4;
    private LineGraphSeries<DataPoint> dataSeries5;

    //private PlotInterface plotter;

    private double plotSize = 121;
    private GraphView graphPlot;
    private TextView txtXval, txtYval, tvTrama;
    private TextView vd_spo2, vd_fc, vd_ta, vd_fr, vd_tc, vd_alarma;
    private CheckBox cb_spo2, cb_fc, cb_ta, cb_fr, cb_tc, cb_legends, cb_send, cb_time;
    Switch swDataStream;
    boolean a = false;boolean b=false;boolean c=false;boolean d=false;boolean e=false;

    boolean tutoCheckBox=false;

    String v0="0";
    String Spo2="";
    String Frec_cardiaca="";
    String Tension_arterial="";
    String Frec_respiratoria="";
    String Temp_corporal="";
    int Alarma=0;

    private boolean getStatusCheckBoxTop = false;
    private boolean getStatusCheckBoxBottom = false;
    private boolean getStatusCheckBoxMiddle = false;
    private boolean getStatusCheckBoxVisibleoculto = false;

    int contador = 0;
    String senal = "";


    AlertDialog.Builder dialogo, dialogo1;
    private ProgressDialog pd;
    //Variable para crear mis propios cuadros de dialogo.
    Dialog myDialog;
    private CheckBox cb_top,cb_bottom,cb_middle,cb_visibleoculto;
    boolean estadoCheck = false;
    boolean estadoTop = false;
    boolean estadoBottom = false;
    boolean estadoMiddle = false;
    boolean estadoVisibleoculto = false;


    boolean estadoSendDataServer = false;
    boolean ultimoEstado = false;
    int totalSegundos = 0;

    /*
    @Override
    public void onSetEstadoCheckBoxLegends(boolean estado) {
        if(estado){
            estado_leyendaGraph = true;
            cb_legends.setChecked(estado_leyendaGraph);
        }else{
            estado_leyendaGraph = false;
            cb_legends.setChecked(estado_leyendaGraph);
        }
    }
    */


    /*
    @Override
    public void onSetEstadoCheckBoxLegends(Context context, boolean estado) {
        Toast.makeText(context, "estado checkbox: "+estado, Toast.LENGTH_SHORT).show();
    }
     */




    /*
    @Override
    public void onSetEstadoCheckBoxTop(boolean a) {
        if (a) {
            getStatusCheckBoxTop = true;
            //Toast.makeText(getBaseContext(), "Activo CheckBox Top", Toast.LENGTH_SHORT).show();
        } else {
            getStatusCheckBoxTop = false;
            //Toast.makeText(getBaseContext(), "Desactivo CheckBox Top", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSetEstadoCheckBoxBottom(boolean b) {
        if (b) {
            getStatusCheckBoxBottom = true;
            Toast.makeText(getBaseContext(), "Activo CheckBox Bottom", Toast.LENGTH_SHORT).show();
        } else {
            getStatusCheckBoxBottom = false;
            Toast.makeText(getBaseContext(), "Desactivo CheckBox Bottom", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSetEstadoCheckBoxMiddle(boolean c) {
        if (c) {
            getStatusCheckBoxMiddle = true;
            Toast.makeText(getBaseContext(), "Activo CheckBox Middle", Toast.LENGTH_SHORT).show();
        } else {
            getStatusCheckBoxMiddle = false;
            Toast.makeText(getBaseContext(), "Desactivo CheckBox Middle", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSetEstadoCheckBoxVisibleoculto(boolean d) {
        if (d) {
            getStatusCheckBoxVisibleoculto = true;
            Toast.makeText(getBaseContext(), "Activo CheckBox Visibleoculto", Toast.LENGTH_SHORT).show();
        } else {
            getStatusCheckBoxVisibleoculto = false;
            Toast.makeText(getBaseContext(), "Desactivo CheckBox Visibleoculto", Toast.LENGTH_SHORT).show();
        }
    }
    */


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new android.app.AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_close)
                    .setTitle("Warning")
                    .setMessage("¿Confirme que desea salir de la ventana monitor?")
                    .setNegativeButton(android.R.string.cancel, null)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            stopRepeating();
                            finish();
                        }
                    })
                    .show();
            // Si el listener devuelve true, significa que el evento esta procesado, y nadie debe hacer nada mas
            return true;
        }
        //para las demas cosas, se reenvia el evento al listener habitual
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signals_monitor);

        graphPlot = (GraphView) findViewById(R.id.plotview);
        tvTrama = (TextView)findViewById(R.id.tvTrama);
        swDataStream = (Switch) findViewById(R.id.swLogEn);
        txtXval = (TextView) findViewById(R.id.txtXval);
        txtYval = (TextView) findViewById(R.id.txtYval);

        vd_spo2 = (TextView) findViewById(R.id.vd_spo2);
        vd_fc = (TextView) findViewById(R.id.vd_fc);
        vd_ta = (TextView) findViewById(R.id.vd_ta);
        vd_fr = (TextView) findViewById(R.id.vd_fr);
        vd_tc = (TextView) findViewById(R.id.vd_tc);
        vd_alarma = (TextView) findViewById(R.id.vd_alarma);

        cb_spo2 = (CheckBox) findViewById(R.id.cb_spo2);
        cb_fc = (CheckBox) findViewById(R.id.cb_fc);
        cb_ta = (CheckBox) findViewById(R.id.cb_ta);
        cb_fr = (CheckBox) findViewById(R.id.cb_fr);
        cb_tc = (CheckBox) findViewById(R.id.cb_tc);
        cb_legends = (CheckBox)findViewById(R.id.cb_legends);
        cb_send = (CheckBox)findViewById(R.id.cb_send);
        cb_time = (CheckBox)findViewById(R.id.cb_time);

        //y esto para pantalla completa (oculta incluso la barra de estado)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        ultimoEstado = obtenerEstadoCbox();
        if(ultimoEstado){
            startRepeating();
            cb_send.setChecked(ultimoEstado);
            //cb_send.setChecked(true);
            cb_send.setEnabled(false);
        }else{
            //stopRepeating();
            cb_send.setChecked(false);
            cb_send.setEnabled(false);
        }

        //cb_legends.setChecked(false);
        cb_legends.setEnabled(false);

        //Detengo el hilo que envia los datos de los sensores a la base de datos en el servidor
        //Base de datos MyQSL.

        cb_time.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    VentanaDialog2(SignalsMonitor.this);
                }else{
                    cb_time.setChecked(false);
                }
            }
        });



        /*
        cb_send.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean estad) {
                if(estad){
                    //iniciarHiloAsyncTask();
                    //hilo();            //Funciona tambien. Es de investigar como deterner el hilo de esta manera.
                    startRepeating();
                }else{
                    //detenreHiloAsyncTask();
                    stopRepeating();
        */


                    /*
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignalsMonitor.this);
                    builder.setIcon(R.drawable.ic_info);
                    builder.setTitle("Información");
                    builder.setMessage("¿Esta seguro? \nClic en Aceptar para confirmar\nClic en Cancelar para continuar con la acción.");
                    builder.setCancelable(false);
                    builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            stopRepeating();
                        }
                    });
                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //cb_send.setChecked(true);
                            //Toast.makeText(SignalsMonitor.this, "Acción Cancelada Correctamente.", Toast.LENGTH_SHORT).show();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();*/



            /*
                }
            }
        });*/


        cb_spo2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    a=true;
                }else{
                    a=false;
                }
            }
        });


        /*
        cb_spo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(cb_spo2.isChecked()) {
                        //Toast.makeText(getContext(), "CheckBox Saturación Parcial del Oxígeno (SPO2)", Toast.LENGTH_SHORT).show();
                        //tvTrama.setText("Check");
                        a=true;
                    }else{
                        a=false;
                    }
                }catch (Exception e){

                }
            }
        });
        */


        cb_fc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b1) {
                if(b1){
                    b=true;
                }else{
                    b=false;
                }
            }
        });

        /*
        cb_fc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(cb_fc.isChecked()){
                        //Toast.makeText(getContext(), "CheckBox Frecuencia Cardiaca", Toast.LENGTH_SHORT).show();
                        b=true;
                    }else{
                        b=false;
                    }
                }catch (Exception e){

                }
            }
        });
        */


        cb_ta.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b1) {
                if(b1){
                    c=true;
                }else{
                    c=false;
                }
            }
        });


        /*
        cb_ta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    if(cb_ta.isChecked()){
                        //Toast.makeText(getContext(), "CheckBox Tensión Arterial", Toast.LENGTH_SHORT).show();
                        c=true;
                    }else{
                        c=false;
                    }

                }catch (Exception e){

                }
            }
        });
        */


        cb_fr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b1) {
                if(b1){
                    d=true;
                }else{
                    d=false;
                }
            }
        });

        /*
        cb_fr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cb_fr.isChecked()){
                    //Toast.makeText(getActivity(), "CheckBox Frecuencia Respiratoria", Toast.LENGTH_SHORT).show();
                    //cb_spo2.setChecked(false);
                    d=true;
                }else{
                    d=false;
                }
            }
        });
       */

        cb_tc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b1) {
                if(b1){
                    e=true;
                }else{
                    e=false;
                }
            }
        });

        /*
        cb_tc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cb_tc.isChecked()){
                    //Toast.makeText(getActivity(), "CheckBox Temperatura Corporal", Toast.LENGTH_SHORT).show();
                    e=true;
                }else{
                    e=false;
                }
            }
        });
        */

        setCheckBoxAll(true, true, true, true, true);

        /*
        cb_legends.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    estadoCheckBoxLegends.onSetEstadoCheckBoxLegends(true);
                }else{
                    estadoCheckBoxLegends.onSetEstadoCheckBoxLegends(false);
                }
            }
        });
        */


        cb_legends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cb_legends.isChecked()){

                    VentanaDialog1(SignalsMonitor.this);
                    //estadoCheckBoxLegends.onSetEstadoCheckBoxLegends(false);

                    //cb_legends.setChecked(false);

                    /*
                    estado_leyendaGraph = true;
                    graphPlot.getLegendRenderer().setVisible(estado_leyendaGraph);
                    graphPlot.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                    //graphPlot.getLegendRenderer().setBackgroundColor(Color.parseColor("#ffffcc"));
                    //graphPlot.getLegendRenderer().setTextColor(Color.parseColor("#000099"));
                    graphPlot.getLegendRenderer().setBackgroundColor(Color.parseColor("#5a5a5a"));
                    graphPlot.getLegendRenderer().setTextColor(Color.parseColor("#ffffff"));
                    Toast.makeText(SignalsMonitor.this, "Activado", Toast.LENGTH_SHORT).show();
                    */


                }else{

                    legendsHidden();

                }
            }
        });

        setValueDefaultGraph();

        /*
        graphPlot.getLegendRenderer().setVisible(true);
        graphPlot.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        //graphPlot.getLegendRenderer().setBackgroundColor(Color.parseColor("#ffffcc"));
        //graphPlot.getLegendRenderer().setTextColor(Color.parseColor("#000099"));
        graphPlot.getLegendRenderer().setBackgroundColor(Color.parseColor("#5a5a5a"));
        graphPlot.getLegendRenderer().setTextColor(Color.parseColor("#ffffff"));
        */

        swDataStream.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean status) {
                if(status){
                    estado_sw = true;
                    ResetGraph();
                    ResetGraph1();
                    ResetGraph2();
                    ResetGraph3();
                    ResetGraph4();
                    //Estas líneas hacen que las gráficas activas por el usuario por medio de los checkbox
                    //de la parte superior de la pantalla se reinicialicen a cero. Se evita que se continue dibujando
                    //la gráfica a partir de donde se dejo. Mejor provocamos que se reinicie la pintada del dato en
                    //la gráfica iniciando desde cero. Si se desea que se continue pintando la gráfica desde donde
                    //se quedo es cuestión de eliminar estas líneas de codigo.
                    plotCount = 0; plotCount1 = 0; plotCount2 = 0; plotCount3 = 0; plotCount4 = 0;

                    setCheckBoxAll(true, true, true, true, true);
                    //cb_legends.setChecked(true);
                    cb_legends.setEnabled(true);
                }else{
                    estado_sw = false;
                    setCheckBoxAll(false, false, false, false, false);
                    //cb_legends.setChecked(false);
                    cb_legends.setEnabled(false);


                    //tareaAsincrona.cancel(true);
                }
            }
        });

        dataSeries.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {

                //dataPointInterface has square backet
                // by default and should be removed when parsing the data of X and Y axis
                String strDataPoint = String.valueOf(dataPoint).replaceAll("\\[","").replaceAll("\\]","");

                String strSplit[];
                Double yVal, xVal;

                if (strDataPoint.contains("/")) {
                    strSplit = strDataPoint.split("/");
                    yVal =  Double.parseDouble(strSplit[1]);
                    xVal =  Double.parseDouble(strSplit[0]);

                    @SuppressLint("DefaultLocale")   String fyVal = String.format("%3.3f", yVal);
                    @SuppressLint("DefaultLocale")   String fxVal = String.format("%3.3f", xVal);
                    //txtXval.setText(fxVal);
                    //txtYval.setText(fyVal);
                }
            }
        });

    }//Fin de método OnCreate

    public void legendsHidden(){
        graphPlot.getLegendRenderer().setVisible(false);
        graphPlot.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        //graphPlot.getLegendRenderer().setBackgroundColor(Color.parseColor("#ffffcc"));
        //graphPlot.getLegendRenderer().setTextColor(Color.parseColor("#000099"));
        graphPlot.getLegendRenderer().setBackgroundColor(Color.parseColor("#5a5a5a"));
        graphPlot.getLegendRenderer().setTextColor(Color.parseColor("#ffffff"));
        //Toast.makeText(SignalsMonitor.this, "Desactivado", Toast.LENGTH_SHORT).show();
        estadoTop = false;
        estadoMiddle = false;
        estadoBottom = false;
        estadoVisibleoculto = false;
        cb_legends.setChecked(false);
    }


    public void VentanaDialog1(final Context context){
        myDialog = new Dialog(context);
        myDialog.setContentView(R.layout.configraphview);
        myDialog.setTitle("App Creado por,");
        myDialog.setCancelable(false);

        cb_top = (CheckBox)myDialog.findViewById(R.id.cb_top);
        cb_bottom = (CheckBox)myDialog.findViewById(R.id.cb_bottom);
        cb_middle = (CheckBox)myDialog.findViewById(R.id.cb_middle);
        cb_visibleoculto = (CheckBox)myDialog.findViewById(R.id.cb_visibleoculto);

        cb_top.setChecked(false);
        cb_bottom.setChecked(false);
        cb_middle.setChecked(false);

        cb_visibleoculto.setChecked(false);
        cb_visibleoculto.setEnabled(false);   //Aqui voy papa chovi

        Button btnCancel = (Button)myDialog.findViewById(R.id.btnCancel);
        Button btnAplicar = (Button)myDialog.findViewById(R.id.btnAplicar);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //Toast.makeText(context, "Clic Cancelar", Toast.LENGTH_SHORT).show();
                    //verificarLegendsActive(estadoTop, estadoMiddle, estadoBottom, estadoVisibleoculto);
                    legendsHidden();
                    //AQui va...
                }catch (Exception e){

                }
                myDialog.dismiss();
            }
        });

        btnAplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //AQui va...
                /*
                Toast.makeText(context, "Chovi = EstadoTop:"+estadoTop+"\n"+
                        "EstadoMiddle:"+estadoMiddle+"\n"+
                        "EstadoBottom:"+estadoBottom+"\n"+
                        "EstadoVisibleoculto:"+estadoVisibleoculto, Toast.LENGTH_SHORT).show();
                 */
                verificarLegendsActive(estadoTop, estadoMiddle, estadoBottom, estadoVisibleoculto);
                myDialog.dismiss();
            }
        });


        cb_top.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b1) {
                if(b1){
                    estadoTop = true;
                    //Toast.makeText(context, "Activo Top", Toast.LENGTH_SHORT).show();
                    cb_bottom.setEnabled(false);
                    cb_middle.setEnabled(false);
                    cb_visibleoculto.setChecked(true);
                }else{
                    estadoTop = false;
                    //Toast.makeText(context, "Desactivo Top", Toast.LENGTH_SHORT).show();
                    cb_bottom.setEnabled(true);
                    cb_middle.setEnabled(true);
                    cb_visibleoculto.setChecked(false);
                }
            }
        });


        /*
        cb_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cb_top.isChecked()){
                    cb_bottom.setEnabled(false);
                    cb_middle.setEnabled(false);
                    //Toast.makeText(context, "Activo CheckBox TOP", Toast.LENGTH_SHORT).show();
                }else{
                    cb_bottom.setEnabled(true);
                    cb_middle.setEnabled(true);
                    //Toast.makeText(context, "Desactivo CheckBox TOP", Toast.LENGTH_SHORT).show();
                }
            }
        });
        */


        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();

        /*
        cb_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cb_bottom.isChecked()){
                    cb_top.setEnabled(false);
                    cb_middle.setEnabled(false);
                }else{
                    cb_top.setEnabled(true);
                    cb_middle.setEnabled(true);
                }
            }
        });
        */

        cb_bottom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b2) {
                if(b2){
                    estadoBottom=true;
                    //Toast.makeText(context, "Activo Bottom", Toast.LENGTH_SHORT).show();
                    cb_top.setEnabled(false);
                    cb_middle.setEnabled(false);
                    cb_visibleoculto.setChecked(true);
                }else{
                    estadoBottom=false;
                    //Toast.makeText(context, "Desactivo Bottom", Toast.LENGTH_SHORT).show();
                    cb_top.setEnabled(true);
                    cb_middle.setEnabled(true);
                    cb_visibleoculto.setChecked(false);
                }
            }
        });

        /*
        cb_middle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cb_middle.isChecked()){
                    cb_bottom.setEnabled(false);
                    cb_top.setEnabled(false);
                }else{
                    cb_bottom.setEnabled(true);
                    cb_top.setEnabled(true);
                }
            }
        });
        */

        cb_middle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b3) {
                if(b3){
                    estadoMiddle=true;
                    //Toast.makeText(context, "Activo Middle", Toast.LENGTH_SHORT).show();
                    cb_bottom.setEnabled(false);
                    cb_top.setEnabled(false);
                    cb_visibleoculto.setChecked(true);
                }else{
                    estadoMiddle=false;
                    //Toast.makeText(context, "Desactivo Middle", Toast.LENGTH_SHORT).show();
                    cb_bottom.setEnabled(true);
                    cb_top.setEnabled(true);
                    cb_visibleoculto.setChecked(false);
                }
            }
        });


        cb_visibleoculto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b4) {
                if(b4){
                    estadoVisibleoculto=true;
                    //Toast.makeText(context, "Activo Visible Oculto", Toast.LENGTH_SHORT).show();
                }else{
                    estadoVisibleoculto=false;
                    //Toast.makeText(context, "Desactivo Visible Oculto", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public void VentanaDialog2(final Context context){
        myDialog = new Dialog(context);
        myDialog.setContentView(R.layout.configtime);
        myDialog.setTitle("Time Send Server");
        myDialog.setCancelable(false);

        String valorTime = "";
        //estadoSendDataServer=false;

        CheckBox cb_enabledSend = (CheckBox)myDialog.findViewById(R.id.cb_enabledSend);
        //cb_enabledSend.setChecked(false);

        final EditText etTiempo = (EditText)myDialog.findViewById(R.id.etTiempo);

        Button btnCancelar = (Button)myDialog.findViewById(R.id.btnCancelar);
        Button btnAplica = (Button)myDialog.findViewById(R.id.btnAplica);

        ultimoEstado = obtenerEstadoCbox();
        valorTime = obtenerTiempo();
        etTiempo.setText(valorTime);
        cb_enabledSend.setChecked(ultimoEstado);

        cb_enabledSend.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean estado) {
                if(estado){
                    //estadoSendDataServer=true;
                    ultimoEstado = true;
                }else{
                    //estadoSendDataServer=false;
                    ultimoEstado = false;
                }
            }
        });

        btnAplica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etTiempo.getText().length() == 0) {
                    etTiempo.setError("Ingrese el tiempo en segundos.");
                    etTiempo.requestFocus();
                }else{
                    String t = etTiempo.getText().toString();
                    //createfiletime(estadoSendDataServer, t);
                    createfiletime(ultimoEstado, t);

                    if(obtenerEstadoCbox()){
                        stopRepeating();
                        startRepeating();
                        cb_send.setChecked(true);cb_send.setEnabled(false);
                    }else{
                        stopRepeating();
                        cb_send.setChecked(false);cb_send.setEnabled(false);
                    }

                    //Toast.makeText(SignalsMonitor.this, "Se guardo correctamente su configuración.", Toast.LENGTH_SHORT).show();
                }
                cb_time.setChecked(false);
                myDialog.dismiss();

            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cb_time.setChecked(false);
                myDialog.dismiss();
            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();



        /*cb_visibleoculto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b4) {
                if(b4){
                    estadoVisibleoculto=true;
                    //Toast.makeText(context, "Activo Visible Oculto", Toast.LENGTH_SHORT).show();
                }else{
                    estadoVisibleoculto=false;
                    //Toast.makeText(context, "Desactivo Visible Oculto", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

    }



    public void createfiletime(boolean estadocbox, String tiempo){
        SharedPreferences preferences = getSharedPreferences("filetime", Context.MODE_PRIVATE);
        //OBTENIENDO LA FECHA Y HORA ACTUAL DEL SISTEMA.
        DateFormat formatodate= new SimpleDateFormat("yyyy/MM/dd");
        String date= formatodate.format(new Date());
        DateFormat formatotime= new SimpleDateFormat("HH:mm:ss a");
        String time= formatotime.format(new Date());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("tiempo", tiempo);
        editor.putBoolean("estadocbox", estadocbox);
        editor.commit();
    }



    public String obtenerTiempo() {
        SharedPreferences preferences = getSharedPreferences("filetime", MODE_PRIVATE);
        String tiempo = preferences.getString("tiempo","5");
        return tiempo;   //return preferences.getString("tiempo", "Sin configurar.");
    }


    public boolean obtenerEstadoCbox() {
        SharedPreferences preferences = getSharedPreferences("filetime", MODE_PRIVATE);
        boolean estado = preferences.getBoolean("estadocbox",false);
        return estado;   //return preferences.getString("tiempo", "Sin configurar.");
    }



    private void verificarLegendsActive(boolean estadoTop, boolean estadoMiddle, boolean estadoBottom, boolean estadoVisibleoculto) {
        if(estadoTop && estadoVisibleoculto) {
            graphPlot.getLegendRenderer().setVisible(estadoVisibleoculto);
            graphPlot.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
            //graphPlot.getLegendRenderer().setBackgroundColor(Color.parseColor("#ffffcc"));
            //graphPlot.getLegendRenderer().setTextColor(Color.parseColor("#000099"));
            graphPlot.getLegendRenderer().setBackgroundColor(Color.parseColor("#5a5a5a"));
            graphPlot.getLegendRenderer().setTextColor(Color.parseColor("#ffffff"));
        }

        if(estadoMiddle && estadoVisibleoculto) {
            graphPlot.getLegendRenderer().setVisible(estadoVisibleoculto);
            graphPlot.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.MIDDLE);
            //graphPlot.getLegendRenderer().setBackgroundColor(Color.parseColor("#ffffcc"));
            //graphPlot.getLegendRenderer().setTextColor(Color.parseColor("#000099"));
            graphPlot.getLegendRenderer().setBackgroundColor(Color.parseColor("#5a5a5a"));
            graphPlot.getLegendRenderer().setTextColor(Color.parseColor("#ffffff"));
        }

        if(estadoBottom && estadoVisibleoculto) {
            graphPlot.getLegendRenderer().setVisible(estadoVisibleoculto);
            graphPlot.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.BOTTOM);
            //graphPlot.getLegendRenderer().setBackgroundColor(Color.parseColor("#ffffcc"));
            //graphPlot.getLegendRenderer().setTextColor(Color.parseColor("#000099"));
            graphPlot.getLegendRenderer().setBackgroundColor(Color.parseColor("#5a5a5a"));
            graphPlot.getLegendRenderer().setTextColor(Color.parseColor("#ffffff"));
        }

        if(!estadoTop && !estadoBottom && !estadoMiddle && !estadoVisibleoculto){
            legendsHidden();
        }

        /*Toast.makeText(this, "Estado Top: "+estadoTop + "\n" +
                "Estado Bottom: "+estadoBottom + "\n" +
                "Estado Middle: "+estadoMiddle + "\n" +
                "Estado VisibleOculto: "+estadoVisibleoculto, Toast.LENGTH_LONG).show();*/

        estadoBottom = false;
        estadoMiddle = false;
        estadoTop = false;
        estadoVisibleoculto = false;

    }

    void setValueDefaultGraph(){
        //Envio el etado de todos los checkbox
        //plotter.AllStatusCheckBox(a, b, c, d, e);
        graphPlot.setPadding(15, 15, 15, 15);
        /********************************************************************************************/
        /*        URL para colores: www.w3schools.com/colors/colors_picker.asp
         *                           www.color-hex.com
         */
        /********************************************************************************************/

        graphPlot.getGridLabelRenderer().setHighlightZeroLines(true);
        //Me da error: graphPlot.getGridLabelRenderer().getGridStyle(GridLabelRenderer.GridStyle.BOTH);

        //Color verde oscuro: #005904. Define el color de la grilla/cuadricula donde se muestra el gráfico.
        graphPlot.getGridLabelRenderer().setGridColor(Color.parseColor("#005904"));
        //Color verde más claro: #008c07. Define el color del texto o etiquetas del gráfico en vertical.
        graphPlot.getGridLabelRenderer().setVerticalLabelsColor(Color.parseColor("#ffffff"));
        //Color verde más claro: #008c07. Define el color del texto o etiquetas del gráfico en horizontal.
        //graphPlot.getGridLabelRenderer().setHorizontalLabelsColor(Color.parseColor("#008c07"));
        graphPlot.getGridLabelRenderer().setHorizontalLabelsColor(Color.parseColor("#ffffff"));

        dataSeries = new LineGraphSeries<>();
        //Color amarillo claro: #e2ea00. Define el color que tendrá la línea que se traza con los datos en el gráfico.
        //dataSeries.setColor(Color.parseColor("#e2ea00"));

        int color1 = this.getResources().getColor(R.color.color_spo2);
        dataSeries.setColor(color1);
        //dataSeries.setColor(Color.parseColor("#1a8cff"));

        //Activando leyendas:
        dataSeries.setTitle("SPO2");
        dataSeries.setDrawDataPoints(true);
        dataSeries.setDataPointsRadius(3);
        dataSeries.setThickness(2);

        /*
        SetGraphParam(121, -0.5, 4.1, -4.1);
        setPlotParamBound();
        plotSize = 122;
        for (int i = 0; i < 121; i++) {*/

        //SetGraphParam(150, -0.5, 1023.0, -10.1);
        SetGraphParam(125, 0.0, 6.0, 0.0);
        setPlotParamBound();
        plotSize = 151;
        for (int i = 0; i < 150; i++) {
            //dataSeries.appendData(new DataPoint(i, 2.0 * Math.sin(i / 2.5)), false, (int) plotSize);
            dataSeries.appendData(new DataPoint(i, 1.0), false, (int) plotSize);
        }
        graphPlot.addSeries(dataSeries);

        /***************************************************************/


        //ADICIONNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNES DEMÁS GRÁFICAS
        //Variable Frecuencia Cardiaca
        SetGraphParam(125, 2.5, 6.0, 0.0);
        setPlotParamBound();
        DataPoint[] points = new DataPoint[140];
        for (int i = 0; i < 140; i++) {
            //points[i] = new DataPoint(i, 5.0 * Math.sin(i/4.5));
            points[i] = new DataPoint(i, 2.0);
        }

        dataSeries1 = new LineGraphSeries<>(points);
        //LineGraphSeries<DataPoint> dataSeries1 = new LineGraphSeries<>(points);

        // styling series
        dataSeries1.setTitle("Frecuencia Cardiaca");
        //dataSeries1.setColor(Color.GRAY);
        int color2 = this.getResources().getColor(R.color.color_fc);
        dataSeries1.setColor(color2);
        dataSeries1.setDrawDataPoints(true);
        dataSeries1.setDataPointsRadius(3);
        dataSeries1.setThickness(2);
        graphPlot.addSeries(dataSeries1);
        /**************************************************************/


        /***************************************************************/
        //Variable Tensión Arterial
        SetGraphParam(125, 2.5, 6.0, 0.0);
        setPlotParamBound();
        points = new DataPoint[140];
        for (int i = 0; i < 140; i++) {
            //points[i] = new DataPoint(i, 5.0 * Math.sin(i/4.5));
            points[i] = new DataPoint(i, 3.0);
        }
        dataSeries2 = new LineGraphSeries<>(points);
        //LineGraphSeries<DataPoint> dataSeries2 = new LineGraphSeries<>(points);
        // styling series
        dataSeries2.setTitle("Tensión Arterial");
        //dataSeries2 .setColor(Color.GREEN);
        int color3 = this.getResources().getColor(R.color.color_ta);
        dataSeries2.setColor(color3);
        dataSeries2.setDrawDataPoints(true);
        dataSeries2.setDataPointsRadius(3);
        dataSeries2.setThickness(2);
        graphPlot.addSeries(dataSeries2);
        /**************************************************************/


        /***************************************************************/
        //Variable Frecuencia Respiratoria
        SetGraphParam(125, 2.5, 6.0, 0.0);
        setPlotParamBound();
        points = new DataPoint[140];
        for (int i = 0; i < 140; i++) {
            //points[i] = new DataPoint(i, 5.0 * Math.sin(i/4.5));
            points[i] = new DataPoint(i, 4.0);
        }

        dataSeries3 = new LineGraphSeries<>(points);
        //LineGraphSeries<DataPoint> dataSeries3 = new LineGraphSeries<>(points);

        // styling series
        dataSeries3.setTitle("Frecuencia Respiratoria");
        //dataSeries3 .setColor(Color.RED);
        int color4 = this.getResources().getColor(R.color.color_fr);
        dataSeries3.setColor(color4);
        dataSeries3.setDrawDataPoints(true);
        dataSeries3.setDataPointsRadius(3);
        dataSeries3.setThickness(2);
        graphPlot.addSeries(dataSeries3);
        /**************************************************************/


        /***************************************************************/
        //Variable Temperatura Corporal
        SetGraphParam(125, 2.5, 6.0, 0.0);
        setPlotParamBound();
        points = new DataPoint[140];
        for (int i = 0; i < 140; i++) {
            //points[i] = new DataPoint(i, 5.0 * Math.sin(i/4.5));
            points[i] = new DataPoint(i, 5.0);
        }

        dataSeries4 = new LineGraphSeries<>(points);
        //LineGraphSeries<DataPoint> dataSeries3 = new LineGraphSeries<>(points);

        // styling series
        dataSeries4.setTitle("Temperatura Corporal");
        //dataSeries3 .setColor(Color.RED);
        int color5 = this.getResources().getColor(R.color.color_tc);
        dataSeries4.setColor(color5);
        dataSeries4.setDrawDataPoints(true);
        dataSeries4.setDataPointsRadius(3);
        dataSeries4.setThickness(2);
        graphPlot.addSeries(dataSeries4);
        /**************************************************************/
    }


    void setCheckBoxAll(boolean chb1, boolean chb2, boolean chb3, boolean chb4, boolean chb5){
        try {
            cb_spo2.setEnabled(chb1);
            cb_fc.setEnabled(chb2);
            cb_ta.setEnabled(chb3);
            cb_fr.setEnabled(chb4);
            cb_tc.setEnabled(chb5);
        }catch (Exception e){

        }
    }

    public void SetSwitch(boolean estatus){
        swDataStream.setChecked(estatus);
    }

    @Override
    protected void onResume() {
        /* El metodo on resume es el adecuado para inicialzar todos aquellos procesos que actualicen la interfaz de usuario
        Por lo tanto invocamos aqui al método que activa el BT y crea la tarea asincrona que recupera los datos*/
        super.onResume();
        descubrirDispositivosBT();
    }


    private void descubrirDispositivosBT() {
        /*
        Este método comprueba si nuestro dispositivo dispone de conectividad bluetooh.
        En caso afirmativo, si estuviera desctivada, intenta activarla.
        En caso negativo presenta un mensaje al usuario y sale de la aplicación.
        */
        //Comprobamos que el dispositivo tiene adaptador bluetooth
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        tvTrama.setText("Comprobando bluetooth");

        if (mBluetoothAdapter != null) {
            //El dispositivo tiene adapatador BT. Ahora comprobamos que bt esta activado.
            if (mBluetoothAdapter.isEnabled()) {
                //Esta activado. Obtenemos la lista de dispositivos BT emparejados con nuestro dispositivo android.

                //tvInformacion.setText("Obteniendo dispositivos emparejados, espere...");

                //tvTrama.setText("Search Bluetooth...");
                tvTrama.setText("X-Axis: 0 ");
                Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
                //Si hay dispositivos emparejados
                if (pairedDevices.size() > 0) {
                /*
                Recorremos los dispositivos emparejados hasta encontrar el
                adaptador BT del arduino, en este caso se llama HC-06
                */
                    BluetoothDevice arduino = null;

                    for (BluetoothDevice device : pairedDevices) {
                        //if (device.getName().equalsIgnoreCase(NOMBRE_DISPOSITIVO_BT)) {
                        if (device.getName().equalsIgnoreCase(Config.NOMBRE_DISPOSITIVO_BT)) {
                            arduino = device;
                        }
                    }

                    if (arduino != null) {
                        //tareaAsincrona = new MiAsyncTask(this);
                        tareaAsincrona = new MiAsyncTask(this, arduino);
                        //tareaAsincrona = new MiAsyncTask(SignalsMonitor.this);
                        tareaAsincrona.execute(arduino);
                    } else {
                        //No hemos encontrado nuestro dispositivo BT, es necesario emparejarlo antes de poder usarlo.
                        //No hay ningun dispositivo emparejado. Salimos de la app.
                        Toast.makeText(this, "No hay dispositivos emparejados, por favor, empareje el arduino", Toast.LENGTH_LONG).show();
                        finish();
                    }
                } else {
                    //No hay ningun dispositivo emparejado. Salimos de la app.
                    Toast.makeText(this, "No hay dispositivos emparejados, por favor, empareje el arduino", Toast.LENGTH_LONG).show();
                    finish();
                }
            } else {
                muestraDialogoConfirmacionActivacion();
            }
        } else {
            // El dispositivo no soporta bluetooth. Mensaje al usuario y salimos de la app
            Toast.makeText(this, "El dispositivo no soporta comunicación por Bluetooth", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onStop() {
    /*Cuando la actividad es destruida, se ejecuta este método.
    Es el lugar adecuado para terminar todos aquellos procesos que se ejecutan en segundo plano, como es el caso de
    nuestra tarea asíncrona que actualiza la interfaz de usuario.
    */
        super.onStop();
        if (tareaAsincrona != null) {
            tareaAsincrona.cancel(true);
        }
    }

    /*Los métodos onTaskCompleted, onCancelled, onTemperaturaUpdate, son nuestros "callback".
    Java es un lenguaje en el que no se puede pasar una función como argumento. De tal manera, que no podemos
    pasarle a la tarea asincrona la función que tendria que ejecutar para actualizar la interfaz de usuario.
    Esto se soluciona usando el interfaz "MiCallback". Ese interfaz obliga a declarar estos tres métodos en la clase que lo implemeta, en este caso, esta
    actividad. De tal manera que podemos pasar como parametro esta clase a la tarea asincrona, y la tarea asincrona podrá invocar a estos métodos cuando
    considere necesario.
    */

    @Override
    public void onTaskCompleted() {

    }

    @Override
    public void onCancelled() {

    }

    @Override
    public void onDatosBiomedicos(Dto_variables p) {
        //int color1 = getContext().getResources().getColor(R.color.color_spo2);
        int color1 = this.getResources().getColor(R.color.color_spo2);
        vd_spo2.setTextColor(color1);
        Spo2 = p.getSaturacion_parcial_oxigeno_SPO2();
        //vd_spo2.setText(p.getSaturacion_parcial_oxigeno_SPO2());
        vd_spo2.setText(Spo2);
        //GRAFICA I: Saturación Parcial del Oxígeno (SPO2)
        try {
            prev = scaler(Double.parseDouble(vd_spo2.getText().toString()), 0, 1023, 0.0, 5.0);  // change this value depending on your application
            if (estado_sw) {
                if (a) {  //Verifico el estado del checkbox.
                    if (plotCount <= plotlen) {
                        plotValue(plotCount, prev, p.getSaturacion_parcial_oxigeno_SPO2());  //Aqui sigue mi analisis LUEGO :-(setPlot1(plotCount, prev1, c_Frec_cardiaca);
                        plotCount = plotCount + plotRes;
                    } else {
                        //clearGraph();
                        ResetGraph();
                        plotCount = 0;
                    }
                    //aqui estaba
                }
            }
        }catch (NumberFormatException nfe)
        {
            prev = 0;
        }//FIN GRÁFICA I


        int color2 = this.getResources().getColor(R.color.color_fc);
        vd_fc.setTextColor(color2);
        Frec_cardiaca = p.getFrecuencia_cardiaca_o_pulso();
        vd_fc.setText(Frec_cardiaca);
        //GRAFICA II: Frecuencia Cardiaca
        try {
            prev1 = scaler(Double.parseDouble(vd_fc.getText().toString()), 0, 1023, 0.0, 5.0);  // change this value depending on your application
            if (estado_sw) {
                if (b) {  //Verifico el estado del checkbox.
                    if (plotCount1 <= plotlen1) {
                        plotValue1(plotCount1, prev1, p.getFrecuencia_cardiaca_o_pulso());  //Aqui sigue mi analisis LUEGO :-(setPlot1(plotCount, prev1, c_Frec_cardiaca);
                        plotCount1 = plotCount1 + plotRes1;
                    } else {
                        ResetGraph1();
                        plotCount1 = 0;
                    }
                    //aqui estaba
                }
            }
        }catch (NumberFormatException nfe)
        {
            prev1 = 0;
        }//FIN GRÁFICA II



        int color3 = this.getResources().getColor(R.color.color_ta);
        vd_ta.setTextColor(color3);
        //Tension_arterial = p.getPresion_arterial();
        Tension_arterial = p.getDiastolic();
        vd_ta.setText(Tension_arterial);
        //GRAFICA III: Tensión Arterial
        try {
            prev2 = scaler(Double.parseDouble(vd_ta.getText().toString()), 0, 1023, 0.0, 5.0);  // change this value depending on your application
            if (estado_sw) {
                if (c) {  //Verifico el estado del checkbox.
                    if (plotCount2 <= plotlen2) {
                        plotValue2(plotCount2, prev2, p.getDiastolic());  //Aqui sigue mi analisis LUEGO :-(setPlot1(plotCount, prev1, c_Frec_cardiaca);
                        plotCount2 = plotCount2 + plotRes2;
                    } else {
                        ResetGraph2();
                        plotCount2 = 0;
                    }
                    //aqui estaba
                }
            }
        }catch (NumberFormatException nfe)
        {
            prev2 = 0;
        }//FIN GRÁFICA III


        int color4 = this.getResources().getColor(R.color.color_fr);
        vd_fr.setTextColor(color4);
        Frec_respiratoria = p.getFrecuencia_respiratoria();
        vd_fr.setText(Frec_respiratoria);
        //GRAFICA IV: Frecuencia Respiratoria
        try {
            prev3 = scaler(Double.parseDouble(vd_fr.getText().toString()), 0, 1023, 0.0, 5.0);  // change this value depending on your application
            if (estado_sw) {
                if (d) {  //Verifico el estado del checkbox.
                    if (plotCount3 <= plotlen3) {
                        plotValue3(plotCount3, prev3, p.getFrecuencia_respiratoria());  //Aqui sigue mi analisis LUEGO :-(setPlot1(plotCount, prev1, c_Frec_cardiaca);
                        plotCount3 = plotCount3 + plotRes3;
                    } else {
                        ResetGraph3();
                        plotCount3 = 0;
                    }
                    //aqui estaba
                }
            }
        }catch (NumberFormatException nfe)
        {
            prev3 = 0;
        }//FIN GRÁFICA IV


        int color5 = this.getResources().getColor(R.color.color_tc);
        vd_tc.setTextColor(color5);
        Temp_corporal = p.getTemperatura_corporal();
        vd_tc.setText(Temp_corporal);
        //GRAFICA IV: Frecuencia Respiratoria
        try {
            prev4 = scaler(Double.parseDouble(vd_tc.getText().toString()), 0, 1023, 0.0, 5.0);  // change this value depending on your application
            if (estado_sw) {
                if (e) {  //Verifico el estado del checkbox.
                    if (plotCount4 <= plotlen4) {
                        plotValue4(plotCount4, prev4, p.getTemperatura_corporal());  //Aqui sigue mi analisis LUEGO :-(setPlot1(plotCount, prev1, c_Frec_cardiaca);
                        plotCount4 = plotCount4 + plotRes4;
                    } else {
                        ResetGraph4();
                        plotCount4 = 0;
                    }
                    //aqui estaba
                }
            }
        }catch (NumberFormatException nfe)
        {
            prev4 = 0;
        }//FIN GRÁFICA IV


        vd_alarma.setText(p.getAlarma());

        //tvTrama.setText("Trama: 0");
    }




    private void muestraDialogoConfirmacionActivacion() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Activar Bluetooth")
                .setMessage("BT esta desactivado. ¿Desea activarlo?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Intentamos activarlo con el siguiente intent.
                        tvTrama.setText("Activando BT");
                        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBtIntent, Config.REQUEST_ENABLE_BT);
                        //startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                    }

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Salimos de la app
                        finish();
                    }
                })
                .show();
    }



    // scale raw input from arduino to whatever value you want, this was taken directly from
    // map function in arduino and implemented to this android application as method
    private double scaler(double x, double in_min, double in_max, double out_min, double out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    private void SetGraphParam(double max_X, double min_X, double max_Y, double min_Y) {
        graphPlot.getViewport().setMinX(min_X);
        graphPlot.getViewport().setMaxX(max_X);
        graphPlot.getViewport().setMinY(min_Y);
        graphPlot.getViewport().setMaxY(max_Y);
    }

    private void setPlotParamBound() {
        graphPlot.getViewport().setXAxisBoundsManual(true);
        graphPlot.getViewport().setYAxisBoundsManual(true);
        graphPlot.getViewport().setScalable(true);           //Enables horizontal zooming and scrolling
        graphPlot.getViewport().setScrollable(true);         //Enables horizontal scrolling

        //Adicione estos otros dos métodos.
        graphPlot.getViewport().setScrollableY(true);        //Enables vertical scrolling
        graphPlot.getViewport().setScalableY(true);          //Enables vertical zooming and scrolling
    }


    public void tramaReceiver(String trama, int contador) {
        tvTrama.setText("Muestras:"+contador+ " Trama="+trama);
    }

    void plotValue(double xVal, double yVal, String spo2){
            @SuppressLint("DefaultLocale") String fyVal = String.format("%3.2f", yVal);
            @SuppressLint("DefaultLocale") String fxVal = String.format("%3.2f", xVal);
            dataSeries.appendData(new DataPoint(xVal, yVal), false, (int) plotSize);
            tvTrama.setText("X-Axis: "+xVal+" ");
    }


    void plotValue1(double xVal1, double yVal1, String fc){
        //if(b) {
            @SuppressLint("DefaultLocale") String fyVal = String.format("%3.2f", yVal1);
            @SuppressLint("DefaultLocale") String fxVal = String.format("%3.2f", xVal1);
            dataSeries1.appendData(new DataPoint(xVal1, yVal1), false, (int) plotSize);

            /*
            int color2 = this.getResources().getColor(R.color.color_fc);
            vd_fc.setTextColor(color2);
            vd_fc.setText(fc);
             */
        //}
    }

    void plotValue2(double xVal1, double yVal1, String ta)         //x = time y = value
    {
        //if(c) {
            @SuppressLint("DefaultLocale") String fyVal = String.format("%3.2f", yVal1);
            @SuppressLint("DefaultLocale") String fxVal = String.format("%3.2f", xVal1);
            dataSeries2.appendData(new DataPoint(xVal1, yVal1), false, (int) plotSize);
            /*
            int color3 = this.getResources().getColor(R.color.color_ta);
            vd_ta.setTextColor(color3);
            vd_ta.setText(ta);
             */
        //}
    }

    void plotValue3(double xVal1, double yVal1, String fr)         //x = time y = value
    {
        @SuppressLint("DefaultLocale") String fyVal = String.format("%3.2f", yVal1);
        @SuppressLint("DefaultLocale") String fxVal = String.format("%3.2f", xVal1);
        dataSeries3.appendData(new DataPoint(xVal1, yVal1), false, (int) plotSize);

        /*
        int color4 = this.getResources().getColor(R.color.color_fr);
        vd_fr.setTextColor(color4);
        vd_fr.setText(fr);
         */
    }


    void plotValue4(double xVal1, double yVal1, String fr)         //x = time y = value
    {
        @SuppressLint("DefaultLocale") String fyVal = String.format("%3.2f", yVal1);
        @SuppressLint("DefaultLocale") String fxVal = String.format("%3.2f", xVal1);
        dataSeries4.appendData(new DataPoint(xVal1, yVal1), false, (int) plotSize);

        /*
        int color5 = this.getResources().getColor(R.color.color_tc);
        vd_fr.setTextColor(color5);
        vd_fr.setText(fr);
         */
    }


    //Reset Grafica Saturación Parcial del Oxígeno (SPO2)
    void ResetGraph() {
        dataSeries.resetData(new DataPoint[]{});
        //dataSeries1.resetData(new DataPoint[]{});
        //dataSeries2.resetData(new DataPoint[]{});
        //dataSeries3.resetData(new DataPoint[]{});
        //dataSeries4.resetData(new DataPoint[]{});
    }

    //Reset Grafica Frecuencia Cardiaca
    void ResetGraph1() {
        dataSeries1.resetData(new DataPoint[]{});
    }

    //Reset Grafica Tensión Arterial
    void ResetGraph2() {
        dataSeries2.resetData(new DataPoint[]{});
    }

    //Reset Grafica frecuencia respiratoria
    void ResetGraph3() {
        dataSeries3.resetData(new DataPoint[]{});
    }

    //Reset Grafica frecuencia respiratoria
    void ResetGraph4() {
        dataSeries4.resetData(new DataPoint[]{});
    }




    //CREACIÓN DE UN NUEVO HILO PARA ENVIO DE DATOS A LA NUBE POP INTERNET
    void hilo(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //Aca se colocan las acciones que se ejecutarán en segundo plano.
                //Toast.makeText(SignalsMonitor.this, "Start Thread", Toast.LENGTH_SHORT).show();
                for (int i=1;i<=10;i++){
                    demora();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //vd_alarma.setText(i);
                        Toast.makeText(SignalsMonitor.this, "Fin Thread", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }).start();
    }


    private void demora(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void iniciarHiloAsyncTask(){
        //hiloAsyncTask thread = new hiloAsyncTask();
        thread = new hiloAsyncTask();
        thread.execute();
    }

    public void detenreHiloAsyncTask(){
        thread.cancel(true);
    }


    //Me funcionó perfectamente la funcionalidad de esta clave Java para tareas asincronas.
    //Pero no la ocupé debido a que la estoy probando con dos AsynTask al mismo tiempo
    //Y encontré el gran problema que dos AsyncTask no pueden estar corriente al mismo tiempo
    //Una AsynTask lo utulizó para las lecturas de los datos de los sensores que llegan por
    //comunicación Bluetooth.  Y esta (2da. AsyncTask la quise ocupar para estar enviando los
    //datos de los sensores a una base de datos en Internet duránte cierto tiempo. No ocupe
    //esta opción debido al problema que ya comente. Opte por otras....
    /*Las opciones son: */
    public class hiloAsyncTask extends AsyncTask<Void, Integer, Boolean>{

        /*
        public hiloAsyncTask() {
            super();
        }
         */

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressBar.setMax(5);
            //progressBar.setProgress(0);
            Toast.makeText(getBaseContext(), "Hilo Inciado.", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            //Aca se colocan las acciones que se ejecutarán en segundo plano.
            for (int i=1;i<=5;i++){
                demora();

                publishProgress(i);

                if(isCancelled()){
                    break;
                }
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //Toast.makeText(getBaseContext(), "Contando: "+values[0], Toast.LENGTH_SHORT).show();

            //vd_alarma.setText(""+values[0]);

            //progressBar.setProgress(values[0].intValue());
        }

        @Override
        protected void onPostExecute(Boolean resultado) {
            //super.onPostExecute(aBoolean);
            if(resultado) {
                Toast.makeText(getBaseContext(), "Tarea Larga Finalizada.", Toast.LENGTH_SHORT).show();
                iniciarHiloAsyncTask();
            }
        }

        /*
            @Override
            protected void onCancelled(Boolean aBoolean) {
                super.onCancelled(aBoolean);
            }
        */

        @Override
        protected void onCancelled() {
            super.onCancelled();
            //vd_alarma.setText("0");
            //progressBar.setProgress(0);
            Toast.makeText(getBaseContext(), "Proceso Cancelado o Detenido", Toast.LENGTH_LONG).show();
        }
    }     //FinHiloAsyncTask


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //thread.cancel(true);
        //Toast.makeText(this, "App Destroy...", Toast.LENGTH_SHORT).show();
    }





    //Ocupando clase Handler
    public void startRepeating() {
    //public void startRepeating(View v) {
        //mHandler.postDelayed(mToastRunnable, 5000);

        /*if(estadoSendDataServer){
            mToastRunnable.run();
        }else{
            Toast.makeText(this, "Debe habilitar la opción antes.", Toast.LENGTH_SHORT).show();
        }*/

        mToastRunnable.run();

    }

    public void stopRepeating() {
    //public void stopRepeating(View v) {
        mHandler.removeCallbacks(mToastRunnable);
        contador=0;
        //Toast.makeText(this, "Se detuvo el envio de datos al servidor.", Toast.LENGTH_SHORT).show();
    }

    private Runnable mToastRunnable = new Runnable() {
        @Override
        public void run() {
            contador++;

            if(contador>=2) {
                volleyBD.sendInfoServer(SignalsMonitor.this,
                        "Pruebas Finales Del Prototipo Biomédico # 1",
                        "0",
                        "0",
                        "0",
                        "0",
                        "0",
                        "0",
                        "0",
                        vd_alarma.getText().toString(),
                        volleyBD.getDate(),
                        volleyBD.getTime(),
                        "28227838");
            }

            //OJO SR. Gámez...,
            //Tendré que COLOCAR una ventana de configuración de tiempo de envio de datos a la base de datos remota (MySQL), para cambiar la constante de 5000 ms = 5 Seg.
            //Además, colocare a esta misma ventana de configuración si se desea enviar los datos o registros de signos vitales de un paciente cada vez que se cumpla
            //el intervalo de tiempo definido en esta ventana, o solo cuando se cumpla un umbral seteado a cada variable en la ventana de configuración de alerta temprana.

            //mHandler.postDelayed(this, 5000);

            totalSegundos = Integer.parseInt(obtenerTiempo());
            totalSegundos = totalSegundos * 1000;
            mHandler.postDelayed(this, totalSegundos);
        }
    };
}
