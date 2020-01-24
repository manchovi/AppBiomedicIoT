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

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

public class SignalMonitorTA extends AppCompatActivity implements MiAsyncTask.MiCallback{

    //Instancia de la clase MiAsyncTask.
    private MiAsyncTask tareaAsincrona;
    SuperClase instanciaSuper = new SuperClase();
    Conexion_volley volleyBD = new Conexion_volley();

    private SignalsMonitor.hiloAsyncTask thread;
    private Handler mHandler = new Handler();

    boolean estado_leyendaGraph = false;
    boolean estado_sw = false;

    private double prev, prev1, prev2;
    private double plotCount = 1;
    private double plotCount1 = 1;
    private double plotCount2 = 1;
    private double plotlen = 6;  //51
    private double plotlen1 = 6;
    private double plotlen2 = 6;
    private double plotRes = 1;
    private double plotRes1 = 1;
    private double plotRes2 = 1;

    private double plotSize = 61;

    String v0="0";
    String Tension_arterial="";

    String Diastolic_pressure="";
    String Systolic_pressure="";
    String Heart_rate="";

    int Alarma=0; int contax=0;

    private boolean aviso = false;
    public static Handler btHandler;
    final int handlerState = 0;        				 //used to identify handler message

    private int conta1=0;
    private LineGraphSeries<DataPoint> dataSeries;
    //dataSeries = new LineGraphSeries<>();

    //LineGraphSeries<DataPoint> dataSeries1 = new LineGraphSeries<>(points);
    private LineGraphSeries<DataPoint> dataSeries1;
    private LineGraphSeries<DataPoint> dataSeries2;

    private GraphView graphPlot;
    private TextView txtXval, txtYval, tvTrama;

    private TextView tv_diastolic, tv_systolic, tv_pulse_min;
    private TextView tv_diastolic1, tv_systolic1, tv_pulse_min1;

    private TextView vd_ta, vd_alarma;
    private CheckBox cb_ta, cb_legends, cb_send, cb_time;

    Switch swDataStream;
    boolean a = false;boolean b=false;boolean c=false;boolean d=false;boolean e=false;
    boolean tutoCheckBox=false;

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
        setContentView(R.layout.activity_signal_monitor_ta);

        //y esto para pantalla completa (oculta incluso la barra de estado)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        graphPlot = (GraphView) findViewById(R.id.plotview);
        tvTrama = (TextView)findViewById(R.id.tvTrama);
        swDataStream = (Switch) findViewById(R.id.swLogEn);
        txtXval = (TextView) findViewById(R.id.txtXval);
        txtYval = (TextView) findViewById(R.id.txtYval);

        vd_ta = (TextView) findViewById(R.id.vd_ta);

        tv_diastolic = (TextView)findViewById(R.id.tv_diastolic);
        tv_systolic = (TextView)findViewById(R.id.tv_systolic);
        tv_pulse_min = (TextView)findViewById(R.id.tv_pulse_min);

        tv_diastolic1 = (TextView)findViewById(R.id.tv_diastolic1);
        tv_systolic1 = (TextView)findViewById(R.id.tv_systolic1);
        tv_pulse_min1 = (TextView)findViewById(R.id.tv_pulse_min1);

        vd_alarma = (TextView) findViewById(R.id.vd_alarma);

        cb_ta = (CheckBox) findViewById(R.id.cb_ta);
        cb_legends = (CheckBox)findViewById(R.id.cb_legends);
        cb_send = (CheckBox)findViewById(R.id.cb_send);
        cb_time = (CheckBox)findViewById(R.id.cb_time);

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

        cb_time.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    VentanaDialog2(SignalMonitorTA.this);
                }else{
                    cb_time.setChecked(false);
                }
            }
        });


        cb_ta.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b1) {
                if(b1){
                    e=true;
                }else{
                    e=false;
                }
            }
        });

        setCheckBoxAll(true);

        cb_legends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cb_legends.isChecked()){
                    VentanaDialog1(SignalMonitorTA.this);
                }else{
                    legendsHidden();
                }
            }
        });

        setValueDefaultGraph();

        swDataStream.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean status) {
                if(status){
                    estado_sw = true;
                    ResetGraph();
                    ResetGraph1();
                    ResetGraph2();
                    plotCount = 0;
                    plotCount1 = 0;
                    plotCount2 = 0;
                    setCheckBoxAll(true);
                    //cb_legends.setChecked(true);
                    cb_legends.setEnabled(true);
                }else{
                    estado_sw = false;
                    setCheckBoxAll(false);
                    //cb_legends.setChecked(false);
                    cb_legends.setEnabled(false);
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
                    /*Toast.makeText(SignalMonitorTA.this, "Eje x:"+ xVal + "\n" +
                            "Eje y:"+ yVal, Toast.LENGTH_SHORT).show();*/
                    mensaje_valores_ejey(yVal,0,0);
                }
            }
        });


        dataSeries1.setOnDataPointTapListener(new OnDataPointTapListener() {
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
                    /*Toast.makeText(SignalMonitorTA.this, "Eje x:"+ xVal + "\n" +
                            "Eje y:"+ yVal, Toast.LENGTH_SHORT).show();*/
                    mensaje_valores_ejey(0,yVal,0);
                }
            }
        });

        dataSeries2.setOnDataPointTapListener(new OnDataPointTapListener() {
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
                    /*Toast.makeText(SignalMonitorTA.this, "Eje x:"+ xVal + "\n" +
                            "Eje y:"+ yVal, Toast.LENGTH_SHORT).show();*/
                    mensaje_valores_ejey(0, 0,yVal);
                }
            }
        });


    }  //FIn onCreate

    public void mensaje_valores_ejey(double diastolic, double systolic, double pulsemin){
        Toast.makeText(this, "Diastolic: "+diastolic+".\n"+
                "Systolic: "+systolic+".\n"+
                "Pulse min: "+pulsemin, Toast.LENGTH_SHORT).show();
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

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();


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
    }  //Fin Método VentanaDialog1



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

    }  //Fin del Método VentanaDialog2



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

    } //Fin del Método


    void setValueDefaultGraph(){
        graphPlot.setPadding(15, 15, 15, 15);
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

        //SetGraphParam(150, -0.5, 1023.0, -10.1);
        //SetGraphParam(125, 2.5, 6.0, 0.0);
        SetGraphParam(10.0, 0.0, 160.0, 20.0);
        setPlotParamBound();
        //plotSize = 151;
        for (int i = 0; i < 10; i++) {
            //dataSeries.appendData(new DataPoint(i, 2.0 * Math.sin(i / 2.5)), false, (int) plotSize);
            dataSeries.appendData(new DataPoint(i, 120.0), false, (int) plotSize);
        }

        //Activando leyendas:
        //dataSeries.setTitle("Tensión Arterial");
        dataSeries.setTitle("Diastolic pressure");
        int color1 = this.getResources().getColor(R.color.color_ta);
        dataSeries.setColor(color1);
        //dataSeries.setColor(Color.parseColor("#1a8cff"));
        dataSeries.setDrawDataPoints(true);
        dataSeries.setDataPointsRadius(3);
        dataSeries.setThickness(2);
        graphPlot.addSeries(dataSeries);

        /***************************************************************/



        //ADICIONNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNES DEMÁS GRÁFICAS
        //Variable Frecuencia Cardiaca
        //SetGraphParam(125, 2.5, 6.0, 0.0);
        SetGraphParam(10.0, 0.0, 160.0, 20.0);
        setPlotParamBound();
        //DataPoint[] points = new DataPoint[140];
        DataPoint[] points = new DataPoint[10];
        for (int i = 0; i < 10; i++) {
            //points[i] = new DataPoint(i, 5.0 * Math.sin(i/4.5));
            //points[i] = new DataPoint(i, 4.0);
            points[i] = new DataPoint(i, 100);
        }

        dataSeries1 = new LineGraphSeries<>(points);
        //LineGraphSeries<DataPoint> dataSeries1 = new LineGraphSeries<>(points);

        // styling series
        dataSeries1.setTitle("Systolic Pressure");
        //dataSeries1.setColor(Color.GRAY);
        int color2 = this.getResources().getColor(R.color.color_fc);
        dataSeries1.setColor(color2);
        dataSeries1.setDrawDataPoints(true);
        dataSeries1.setDataPointsRadius(3);
        dataSeries1.setThickness(2);
        graphPlot.addSeries(dataSeries1);
        /**************************************************************/



        /*************************************************************/
        //SetGraphParam(125, 2.5, 6.0, 0.0);
        SetGraphParam(10.0, 0.0, 160.0, 20.0);
        setPlotParamBound();
        //DataPoint[] points = new DataPoint[140];
        DataPoint[] points1 = new DataPoint[10];
        for (int i = 0; i < 10; i++) {
            //points[i] = new DataPoint(i, 5.0 * Math.sin(i/4.5));
            //points[i] = new DataPoint(i, 4.0);
            points1[i] = new DataPoint(i, 80);                    //REFERENCIA DONDE SE MOSTRARÁ LA LINEA QUE PARTE DESDE EL EJE Y HACIA LA HORIZONTAL EN EL EJE X.
        }

        dataSeries2 = new LineGraphSeries<>(points1);
        //LineGraphSeries<DataPoint> dataSeries1 = new LineGraphSeries<>(points);

        // styling series
        dataSeries2.setTitle("Heart Rate");
        //dataSeries1.setColor(Color.GRAY);
        int color3 = this.getResources().getColor(R.color.color_ta1);
        dataSeries2.setColor(color3);
        dataSeries2.setDrawDataPoints(true);
        dataSeries2.setDataPointsRadius(3);
        dataSeries2.setThickness(2);
        graphPlot.addSeries(dataSeries2);
        /**************************************************************/

        graphPlot.getLegendRenderer().setVisible(true);
        graphPlot.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        //graphPlot.getLegendRenderer().setBackgroundColor(Color.parseColor("#ffffcc"));
        //graphPlot.getLegendRenderer().setTextColor(Color.parseColor("#000099"));
        graphPlot.getLegendRenderer().setBackgroundColor(Color.parseColor("#5a5a5a"));
        graphPlot.getLegendRenderer().setTextColor(Color.parseColor("#ffffff"));
    }


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


    void setCheckBoxAll(boolean chb){
        try {
            cb_ta.setEnabled(chb);
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
                        /* try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }*/
                        tareaAsincrona.SendData("P");

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
    }  //Fin del Método...


    @Override
    protected void onStop() {
    /*Cuando la actividad es destruida, se ejecuta este método.
    Es el lugar adecuado para terminar todos aquellos procesos que se ejecutan en segundo plano, como es el caso de
    nuestra tarea asíncrona que actualiza la interfaz de usuario.
    */
        super.onStop();
        if (tareaAsincrona != null) {
            tareaAsincrona.cancel(true);
            tareaAsincrona.SendData("A");
        }
    }


    @Override
    public void onTaskCompleted() {

    }

    @Override
    public void onCancelled() {

    }


    @Override
    public void onDatosBiomedicos(Dto_variables p) {

        int color5 = this.getResources().getColor(R.color.color_ta);
        vd_ta.setTextColor(color5);
        //Tension_arterial = p.getPresion_arterial();
        Diastolic_pressure = p.getDiastolic();
        Systolic_pressure = p.getSystolic();
        Heart_rate = p.getPulse_min();

        //vd_ta.setText(Tension_arterial);
        tv_diastolic1.setText(Diastolic_pressure);
        tv_systolic1.setText(Systolic_pressure);
        tv_pulse_min1.setText(Heart_rate);
            /*
             tv_diastolic.setText(Diastolic_pressure);
             tv_systolic.setText(Systolic_pressure);
             tv_pulse_min.setText(Heart_rate);

            */

        //if (Spo2.equals("0") || Frec_cardiaca.equals("0")) {
        if (tv_diastolic1.getText().equals("0") || tv_systolic1.getText().equals("0") || tv_pulse_min1.getText().equals("0")) {
            //Toast.makeText(this, "Se ha detectado que el sensor ha sido desconectado o ha retirado su dedo indice de el.", Toast.LENGTH_SHORT).show();
            if(conta1 == 0){
                dialogoError();
                conta1++;
            }

        } else {//}

            //Gráfica de Tensión Arterial. Dato 1. Diastolic.
            try {
                //prev = scaler(Double.parseDouble(tv_diastolic.toString()), 0, 1023, 0.0, 5.0);  // change this value depending on your application
                prev = Double.parseDouble(tv_diastolic1.getText().toString());
                if (estado_sw) {
                    if (e) {  //Verifico el estado del checkbox.
                        if (plotCount <= plotlen) {
                            plotValue(plotCount, prev, p.getDiastolic());  //Aqui sigue mi analisis LUEGO :-(setPlot1(plotCount, prev1, c_Frec_cardiaca);
                            plotCount = plotCount + plotRes;
                        } else {
                            ResetGraph();
                            plotCount = 0;
                        }
                    }
                }
            } catch (NumberFormatException nfe) {
                prev = 0;
            }         //Fin Gráfica 1.

            //Gráfica de Tensión Arterial. Dato 2. Systolic.
            try {
                //prev1 = scaler(Double.parseDouble(vd_fc.getText().toString()), 0, 1023, 0.0, 5.0);  // change this value depending on your application
                prev1 = Double.parseDouble(tv_systolic1.getText().toString());  // change this value depending on your application
                if (estado_sw) {
                    if (e) {  //Verifico el estado del checkbox.
                        if (plotCount1 <= plotlen1) {
                            plotValue1(plotCount1, prev1, p.getSystolic());  //Aqui sigue mi analisis LUEGO :-(setPlot1(plotCount, prev1, c_Frec_cardiaca);
                            plotCount1 = plotCount1 + plotRes1;
                        } else {
                            ResetGraph1();
                            plotCount1 = 0;
                        }
                    }
                }
            } catch (NumberFormatException nfe) {
                prev1 = 0;
            }  //Fin Gráfica 2.

            //Gráfica de Tensión Arterial. Dato 3. Pulse Min.
            try {
                //prev1 = scaler(Double.parseDouble(vd_fc.getText().toString()), 0, 1023, 0.0, 5.0);  // change this value depending on your application
                prev2 = Double.parseDouble(tv_pulse_min1.getText().toString());  // change this value depending on your application
                if (estado_sw) {
                    if (e) {  //Verifico el estado del checkbox.
                        if (plotCount2 <= plotlen2) {
                            plotValue2(plotCount2, prev2, p.getPulse_min());  //Aqui sigue mi analisis LUEGO :-(setPlot1(plotCount, prev1, c_Frec_cardiaca);
                            plotCount2 = plotCount2 + plotRes2;
                        } else {
                            ResetGraph2();
                            plotCount2 = 0;
                        }
                    }
                }
            } catch (NumberFormatException nfe) {
                prev2 = 0;
            }  //Fin Gráfica 2.

            vd_alarma.setText(p.getAlarma());
            //tvTrama.setText("Trama: 0");

        }
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
        graphPlot.getViewport().setScalable(false);           //Enables horizontal zooming and scrolling
        graphPlot.getViewport().setScrollable(false);         //Enables horizontal scrolling

        //Adicione estos otros dos métodos.
        graphPlot.getViewport().setScrollableY(false);        //Enables vertical scrolling
        graphPlot.getViewport().setScalableY(false);          //Enables vertical zooming and scrolling
    }


    public void tramaReceiver(String trama, int contador) {
        tvTrama.setText("Muestras:"+contador+ " Trama="+trama);
    }

    void plotValue(double xVal, double yVal, String diastolic_pressure){
        @SuppressLint("DefaultLocale") String fyVal = String.format("%3.2f", yVal);
        @SuppressLint("DefaultLocale") String fxVal = String.format("%3.2f", xVal);
        dataSeries.appendData(new DataPoint(xVal, yVal), false, (int) plotSize);
        tvTrama.setText("X-Axis: "+xVal+" ");
    }

    void plotValue1(double xVal1, double yVal1, String systolic_pressure){
        @SuppressLint("DefaultLocale") String fyVal = String.format("%3.2f", yVal1);
        @SuppressLint("DefaultLocale") String fxVal = String.format("%3.2f", xVal1);
        dataSeries1.appendData(new DataPoint(xVal1, yVal1), false, (int) plotSize);
    }

    void plotValue2(double xVal1, double yVal1, String heart_rate){
        @SuppressLint("DefaultLocale") String fyVal = String.format("%3.2f", yVal1);
        @SuppressLint("DefaultLocale") String fxVal = String.format("%3.2f", xVal1);
        dataSeries2.appendData(new DataPoint(xVal1, yVal1), false, (int) plotSize);
    }

    //Reset Grafica Saturación Parcial del Oxígeno (SPO2)
    void ResetGraph() {
        dataSeries.resetData(new DataPoint[]{});
    }

    void ResetGraph1() {
        dataSeries1.resetData(new DataPoint[]{});
    }

    void ResetGraph2() {
        dataSeries2.resetData(new DataPoint[]{});
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    //Ocupando clase Handler
    public void startRepeating() {
        mToastRunnable.run();
    }


    public void stopRepeating() {
        //public void stopRepeating(View v) {
        mHandler.removeCallbacks(mToastRunnable);
        contador=0;
    }


    private Runnable mToastRunnable = new Runnable() {
        @Override
        public void run() {
            contador++;

            //sendInfoServer(final Context context,
            // final String descripcion,
            // final String spo2,
            // final String frec_cardiaca,
            // final String diastolic,
            // final String systolic ,
            // final String pulse_min,
            // final String frec_respiratoria,
            // final String temp_corporal,
            // final String alarma,
            // final String fecha,
            // final String hora,
            // final String responsable_especialista)
            contax = 0;
            if(contador>=2) {
                if(tv_diastolic1.getText().equals("0") || tv_diastolic1.getText().equals("")  || tv_systolic1.getText().equals("0") || tv_systolic1.getText().equals("") || tv_pulse_min1.getText().equals("0") || tv_pulse_min1.getText().equals("")){
                    //Toast.makeText(SignalMonitorSpo2Pulso.this, "nada", Toast.LENGTH_SHORT).show();
                }else{
                    /*tv_diastolic.setText(Diastolic_pressure);
                     tv_systolic.setText(Systolic_pressure);
                     tv_pulse_min.setText(Heart_rate);*/

                    if(contax==0){
                         tv_diastolic.setText(tv_diastolic1.getText().toString());
                         tv_systolic.setText(tv_systolic1.getText().toString());
                         tv_pulse_min.setText(tv_pulse_min1.getText().toString());
                         contax=1;
                     }

                    volleyBD.sendInfoServer(SignalMonitorTA.this,
                            "Pruebas Finales Del Prototipo Biomédico # 1",
                            "0",
                            "0",
                            tv_diastolic.getText().toString(),
                            tv_systolic.getText().toString(),
                            tv_pulse_min.getText().toString(),
                            "0",
                            "0",
                            vd_alarma.getText().toString(),
                            volleyBD.getDate(),
                            volleyBD.getTime(),
                            "28227838");

                            dialogoFinal();
                }
            }

            totalSegundos = Integer.parseInt(obtenerTiempo());
            totalSegundos = totalSegundos * 1000;
            mHandler.postDelayed(this, totalSegundos);
        }
    };


    private void dialogoError(){

        new android.app.AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_error)
                .setTitle("Info Sensor!!!")
                .setMessage("Revice lo Siguiente:\n" +
                        "1. Sensor este conectado a la tarjeta MySignals." + "\n" +
                        "2. Para realizar una medición, una vez conectado debe encenderse desde el botón correspondiente." +"\n" +
                        "3. Para efectuar diferentes mediciones es necesario que por medición se encienda el sensor." +"\n\n" +
                        "Gracias!!!.")
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();

    }

    private void dialogoFinal(){
        new android.app.AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_info)
                .setTitle("Completado!!!")
                .setMessage("Se ha completado la medición. Press Aceptar para verificar la lecturas obtenidas." +
                        "\n\nEn caso de querer realizar otra medición, encienda el sensor.")
                //.setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();

    }


}
