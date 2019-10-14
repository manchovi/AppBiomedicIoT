package com.example.btasinktask;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialog;


//public class SuperClase implements SignalsMonitor.LegendInterface{
public class SuperClase {

    Context mCtx;

    AlertDialog.Builder dialogo, dialogo1;
    private ProgressDialog pd;
    //Variable para crear mis propios cuadros de dialogo.
    Dialog myDialog;
    private CheckBox cb_top,cb_bottom,cb_middle,cb_visibleoculto;
    boolean estadoCheck = false;


    boolean estadoCheckBoxTop;
    boolean estadoCheckBoxBottom;
    boolean estadoCheckBoxMiddle;
    boolean estadoCheckBoxVisibleoculto;

    boolean estadoTop = false;
    boolean estadoBottom = false;
    boolean estadoMiddle = false;
    boolean estadoVisibleoculto = false;


    public SuperClase() {
    }

    public SuperClase(Context mCtx) {
        this.mCtx = mCtx;
    }

    public boolean isEstadoCheckBoxTop() {
        return estadoCheckBoxTop;
    }

    public void setEstadoCheckBoxTop(boolean estadoCheckBoxTop) {
        this.estadoCheckBoxTop = estadoCheckBoxTop;
    }

    public boolean isEstadoCheckBoxBottom() {
        return estadoCheckBoxBottom;
    }

    public void setEstadoCheckBoxBottom(boolean estadoCheckBoxBottom) {
        this.estadoCheckBoxBottom = estadoCheckBoxBottom;
    }

    public boolean isEstadoCheckBoxMiddle() {
        return estadoCheckBoxMiddle;
    }

    public void setEstadoCheckBoxMiddle(boolean estadoCheckBoxMiddle) {
        this.estadoCheckBoxMiddle = estadoCheckBoxMiddle;
    }

    public boolean isEstadoCheckBoxVisibleoculto() {
        return estadoCheckBoxVisibleoculto;
    }

    public void setEstadoCheckBoxVisibleoculto(boolean estadoCheckBoxVisibleoculto) {
        this.estadoCheckBoxVisibleoculto = estadoCheckBoxVisibleoculto;
    }

    /*
        public SuperClase(LegendInterface estadoCheckBox) {
            this.estadoCheckBox = estadoCheckBox;
        }
    */



    /*
    public LegendInterface estadoCheckBox;
    interface LegendInterface {
        //void onSetEstadoCheckBoxLegends(boolean estado);
        void onSetEstadoCheckBoxTop(boolean a);
        void onSetEstadoCheckBoxBottom(boolean b);
        void onSetEstadoCheckBoxMiddle(boolean c);
        void onSetEstadoCheckBoxVisibleoculto(boolean d);
    }*/

    public DatosCheckBox estadosCB;
    public interface DatosCheckBox {
        //void onTemperaturaUpdate(Temperatura p);
        void onEstados(boolean estadoCB1);
    }



    private void VentanaDialog2() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        final View view = inflater.inflate(R.layout.configraphview, null);
        builder.setView(view);

        cb_top = (CheckBox)myDialog.findViewById(R.id.cb_top);
        cb_bottom = (CheckBox)myDialog.findViewById(R.id.cb_bottom);
        cb_middle = (CheckBox)myDialog.findViewById(R.id.cb_middle);
        cb_visibleoculto = (CheckBox)myDialog.findViewById(R.id.cb_visibleoculto);

        Button btnCancel = (Button)myDialog.findViewById(R.id.btnCancel);
        Button btnAplicar = (Button)myDialog.findViewById(R.id.btnAplicar);

        final AlertDialog dialog = builder.create();
        dialog.show();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Toast.makeText(mCtx, "Clic Cancelar", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
            }
        });

        btnAplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Toast.makeText(mCtx, "Clic en Aplicar", Toast.LENGTH_SHORT).show();
                    setEstadoCheckBoxTop(true);
                    /*
                    Intent intent = new Intent(mCtx, SignalsMonitor.class);
                    intent.putExtra("senal", "yo");
                    mCtx.startActivity(intent);
                    */
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
                        //estadoCheckBox.onSetEstadoCheckBoxTop(true);
                        setEstadoCheckBoxTop(true);
                        estadoTop = true;
                        Toast.makeText(mCtx, "Activo Top", Toast.LENGTH_SHORT).show();
                        cb_bottom.setEnabled(false);
                        cb_middle.setEnabled(false);
                }else{
                        setEstadoCheckBoxTop(false);
                        estadoTop = false;
                        Toast.makeText(mCtx, "Desactivo Top", Toast.LENGTH_SHORT).show();
                        cb_bottom.setEnabled(true);
                        cb_middle.setEnabled(true);
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





    }






}
