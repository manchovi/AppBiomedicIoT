package com.example.btasinktask;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;


//public class SuperClase implements SignalsMonitor.LegendInterface{
public class SuperClase1 {

    AlertDialog.Builder dialogo, dialogo1;
    private ProgressDialog pd;
    //Variable para crear mis propios cuadros de dialogo.
    Dialog myDialog;
    private CheckBox cb_top,cb_bottom,cb_middle,cb_visibleoculto;
    boolean estadoCheck = false;


    public LegendInterface estadoCheckBox;
    interface LegendInterface {
        //void onSetEstadoCheckBoxLegends(boolean estado);
        void onSetEstadoCheckBoxTop(boolean a);
        void onSetEstadoCheckBoxBottom(boolean b);
        void onSetEstadoCheckBoxMiddle(boolean c);
        void onSetEstadoCheckBoxVisibleoculto(boolean d);
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

        Button btnCancel = (Button)myDialog.findViewById(R.id.btnCancel);
        Button btnAplicar = (Button)myDialog.findViewById(R.id.btnAplicar);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Toast.makeText(context, "Clic Cancelar", Toast.LENGTH_SHORT).show();
                    //estadoCheckBoxLegends.onSetEstadoCheckBoxLegends(context,false);
                }catch (Exception e){

                }
                myDialog.dismiss();
            }
        });

        btnAplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Toast.makeText(context, "Clic en Aplicar", Toast.LENGTH_SHORT).show();
                    //estadoCheckBoxLegends.onSetEstadoCheckBoxLegends(context,false);
                }catch (Exception t){

                }

                myDialog.dismiss();
            }
        });


        cb_top.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    try {
                        estadoCheckBox.onSetEstadoCheckBoxTop(true);
                        Toast.makeText(context, "Activo Top", Toast.LENGTH_SHORT).show();
                        cb_bottom.setEnabled(false);
                        cb_middle.setEnabled(false);
                    }catch (Exception e){

                    }
                }else{
                    try {
                        estadoCheckBox.onSetEstadoCheckBoxTop(false);
                        Toast.makeText(context, "Desactivo Top", Toast.LENGTH_SHORT).show();
                        cb_bottom.setEnabled(true);
                        cb_middle.setEnabled(true);
                    }catch (Exception ex){

                    }
                }
            }
        });



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
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    estadoCheckBox.onSetEstadoCheckBoxBottom(true);
                    cb_top.setEnabled(false);
                    cb_middle.setEnabled(false);
                }else{
                    estadoCheckBox.onSetEstadoCheckBoxBottom(false);
                    cb_top.setEnabled(true);
                    cb_middle.setEnabled(true);
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
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    estadoCheckBox.onSetEstadoCheckBoxMiddle(true);
                    cb_bottom.setEnabled(false);
                    cb_top.setEnabled(false);
                }else{
                    estadoCheckBox.onSetEstadoCheckBoxMiddle(false);
                    cb_bottom.setEnabled(true);
                    cb_top.setEnabled(true);
                }
            }
        });


        cb_visibleoculto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    estadoCheckBox.onSetEstadoCheckBoxVisibleoculto(true);
                }else{
                    estadoCheckBox.onSetEstadoCheckBoxVisibleoculto(false);
                }
            }
        });
    }

    /*
    public SuperClase(LegendInterface estadoCheckBox) {
        this.estadoCheckBox = estadoCheckBox;
    }
     */



}
