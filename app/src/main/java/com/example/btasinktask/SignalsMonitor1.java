package com.example.btasinktask;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

public class SignalsMonitor1 extends AppCompatActivity {

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
    private CheckBox cb_spo2, cb_fc, cb_ta, cb_fr, cb_tc;
    Switch swDataStream;
    boolean a = false;boolean b=false;boolean c=false;boolean d=false;boolean e=false;

    boolean tutoCheckBox=false;

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
        dataSeries.setDataPointsRadius(5);
        dataSeries.setThickness(8);

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
        dataSeries1.setDataPointsRadius(5);
        dataSeries1.setThickness(8);
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
        dataSeries2 .setTitle("Tensión Arterial");
        //dataSeries2 .setColor(Color.GREEN);
        int color3 = this.getResources().getColor(R.color.color_ta);
        dataSeries2.setColor(color3);
        dataSeries2 .setDrawDataPoints(true);
        dataSeries2 .setDataPointsRadius(5);
        dataSeries2 .setThickness(8);
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
        dataSeries3 .setTitle("Frecuencia Respiratoria");
        //dataSeries3 .setColor(Color.RED);
        int color4 = this.getResources().getColor(R.color.color_fr);
        dataSeries3.setColor(color4);
        dataSeries3 .setDrawDataPoints(true);
        dataSeries3 .setDataPointsRadius(5);
        dataSeries3 .setThickness(8);
        graphPlot.addSeries(dataSeries3);
        /**************************************************************/

        graphPlot.getLegendRenderer().setVisible(true);
        graphPlot.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

        graphPlot.getLegendRenderer().setBackgroundColor(Color.parseColor("#ffffcc"));
        graphPlot.getLegendRenderer().setTextColor(Color.parseColor("#000099"));


        swDataStream.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean status) {
                if(status){
                    ResetGraph();
                    ResetGraph1();
                    ResetGraph2();
                    ResetGraph3();
                }else{

                }
            }
        });

        dataSeries.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {

            }
        });


    }//Fin de método OnCreate




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

    void plotValue(double xVal, double yVal, String spo2, String trama){
        if(a){
            //@SuppressLint("DefaultLocale") String xd_alarma = String.format("%3.3f", alarma);
            @SuppressLint("DefaultLocale") String fyVal = String.format("%3.2f", yVal);
            @SuppressLint("DefaultLocale") String fxVal = String.format("%3.2f", xVal);

            //dataSeries.resetData(new DataPoint[]{});  //Con esta línea logro el efecto de mostrar unicamente
            //un punto con el dato que se recibe dentro de la grafica

            dataSeries.appendData(new DataPoint(xVal, yVal), false, (int) plotSize);
            //dataSeries.appendData(new DataPoint(xVal, yVal), true, (int) plotSize);
            //dataSeries.appendData(new DataPoint(xVal, yVal), false, 1);
            txtXval.setText(fxVal);
            txtYval.setText(fyVal);
            //int color1 = getContext().getResources().getColor(R.color.color_spo2);
            int color1 = this.getResources().getColor(R.color.color_spo2);
            vd_spo2.setTextColor(color1);
            vd_spo2.setText(spo2);
            tvTrama.setText(trama);
        }
    }


    void plotValue(double xVal, double yVal, String spo2)
    {
        if(a){
            //@SuppressLint("DefaultLocale") String xd_alarma = String.format("%3.3f", alarma);
            @SuppressLint("DefaultLocale") String fyVal = String.format("%3.2f", yVal);
            @SuppressLint("DefaultLocale") String fxVal = String.format("%3.2f", xVal);

            //dataSeries.resetData(new DataPoint[]{});  //Con esta línea logro el efecto de mostrar unicamente
            //un punto con el dato que se recibe dentro de la grafica

            dataSeries.appendData(new DataPoint(xVal, yVal), false, (int) plotSize);
            //dataSeries.appendData(new DataPoint(xVal, yVal), true, (int) plotSize);
            //dataSeries.appendData(new DataPoint(xVal, yVal), false, 1);
            txtXval.setText(fxVal);
            txtYval.setText(fyVal);
            //int color1 = getContext().getResources().getColor(R.color.color_spo2);
            int color1 = this.getResources().getColor(R.color.color_spo2);
            vd_spo2.setTextColor(color1);
            vd_spo2.setText(spo2);
            //tvTrama.setText(tramaReceiver);
        }
    }



    void plotValue1(double xVal1, double yVal1, String fc)         //x = time y = value
    {
        if(b) {
            @SuppressLint("DefaultLocale") String fyVal = String.format("%3.2f", yVal1);
            @SuppressLint("DefaultLocale") String fxVal = String.format("%3.2f", xVal1);
            dataSeries1.appendData(new DataPoint(xVal1, yVal1), false, (int) plotSize);

            int color2 = this.getResources().getColor(R.color.color_fc);
            vd_fc.setTextColor(color2);
            vd_fc.setText(fc);
        }
    }

    void plotValue2(double xVal1, double yVal1, String ta)         //x = time y = value
    {
        if(c) {
            @SuppressLint("DefaultLocale") String fyVal = String.format("%3.2f", yVal1);
            @SuppressLint("DefaultLocale") String fxVal = String.format("%3.2f", xVal1);
            dataSeries2.appendData(new DataPoint(xVal1, yVal1), false, (int) plotSize);

            int color3 = this.getResources().getColor(R.color.color_ta);
            vd_ta.setTextColor(color3);
            vd_ta.setText(ta);
        }
    }

    void plotValue3(double xVal1, double yVal1, String fr)         //x = time y = value
    {
        @SuppressLint("DefaultLocale") String fyVal = String.format("%3.2f", yVal1);
        @SuppressLint("DefaultLocale") String fxVal = String.format("%3.2f", xVal1);
        dataSeries3.appendData(new DataPoint(xVal1, yVal1), false, (int) plotSize);

        int color4 = this.getResources().getColor(R.color.color_fr);
        vd_fr.setTextColor(color4);
        vd_fr.setText(fr);
    }


    //Reset Grafica Saturación Parcial del Oxígeno (SPO2)
    void ResetGraph() {
        dataSeries.resetData(new DataPoint[]{});
        //dataSeries1.resetData(new DataPoint[]{});
        //dataSeries2.resetData(new DataPoint[]{});
        //dataSeries3.resetData(new DataPoint[]{});
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


    void SetCheckBoxAll(boolean chb1, boolean chb2, boolean chb3, boolean chb4, boolean chb5){
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




}
