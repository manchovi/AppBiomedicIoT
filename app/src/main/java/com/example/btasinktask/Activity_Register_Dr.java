package com.example.btasinktask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Activity_Register_Dr extends AppCompatActivity {

    String elementos[] = {"Uno", "Dos", "Tres", "Cuatro", "Cinco"};

    private Spinner sp_questions;
    private EditText et_respuesta;

    private CheckBox cb_masculino, cb_femenino;

    int conta = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__register__dr);

        //y esto para pantalla completa (oculta incluso la barra de estado)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sp_questions = (Spinner)findViewById(R.id.sp_questions);  //Aca tomo la pregunta se seguridad seleccionada.
        et_respuesta = (EditText)findViewById(R.id.et_respuesta);

        cb_masculino = (CheckBox)findViewById(R.id.cb_masculino);
        cb_femenino = (CheckBox)findViewById(R.id.cb_femenino);

        final String[] lista =new String[]{
                "* Seleccione Pregunta Seguridad",
                "¿Cuál es el nombre de tu materia favorita?",
                "¿Cuál es el nombre de tu mascota favorita?",
                "¿Cuál es el nombre de tu mejor amig@?",
                "¿Cuál es el número de tu primer celular?",
                "¿Cuál es el nombre de tu primer novi@?",
                "¿Cuál es el lugar de tu nacimiento?",
                "¿Cuál es tu color favorito?",
                "¿Cuál es la fecha de tu nacimiento?",
        };
        //ArrayAdapter<String> adaptador =new ArrayAdapter<String> (this,android.R.layout.simple_spinner_item, lista);
        ArrayAdapter<String> adaptador =new ArrayAdapter<String> (this,R.layout.spinnerstyle, lista);
        sp_questions.setAdapter(adaptador);

        sp_questions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(conta>=1 && sp_questions.getSelectedItemPosition()>0){
                    //Toast.makeText(login.this, combo.getSelectedItem().toString(),Toast.LENGTH_LONG).show();

                    /*Toast toast = Toast.makeText(getApplicationContext(), sp_questions.getSelectedItem().toString(), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();*/
                    et_respuesta.setVisibility(View.VISIBLE);
                }else{
                    et_respuesta.setVisibility(View.GONE);
                }
                conta++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        cb_masculino.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean estado_masculino) {
                if(estado_masculino){
                    cb_femenino.setChecked(false);
                }else{
                    cb_femenino.setChecked(true);
                }
            }
        });

        cb_femenino.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean estado_femenino) {
                if(estado_femenino){
                    cb_masculino.setChecked(false);
                }else{
                    cb_masculino.setChecked(true);
                }
            }
        });


    }
}
