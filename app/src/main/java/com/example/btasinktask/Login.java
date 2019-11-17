package com.example.btasinktask;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Login extends AppCompatActivity implements View.OnClickListener{

    Dialog myDialog;

    EditText etEmail, etClave;
    TextInputLayout tiEmail, tiClave;
    Button btnLogin, btnRegistrar, btnOlvidoClave;

    boolean v1 = false;
    boolean v2 = false;

    RelativeLayout rellay0, rellay1, rellay2, rellay3;
    //Button btnOlvidoClave;
    ImageView imgView_logo;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            rellay1.setVisibility(View.VISIBLE);
            rellay2.setVisibility(View.VISIBLE);
            rellay3.setVisibility(View.VISIBLE);
        }
    };


    //Inicio: Variables para registro de especialista
    String elementos[] = {"Uno", "Dos", "Tres", "Cuatro", "Cinco"};
    private Spinner sp_questions;

    private CheckBox cb_masculino, cb_femenino;
    private TextInputLayout ti_dui,ti_nombres,ti_apellidos,ti_direccion,ti_telefono,ti_especialidad,ti_comentario,ti_usuario,ti_password1,ti_password2,ti_respuesta;
    private EditText et_dui,et_nombres,et_apellidos,et_direccion,et_telefono,et_especialidad,et_comentario,et_usuario,et_password1,et_password2,et_respuesta;

    private ImageView BtnCerrar;
    private Button btnCancel, btnSave;
    int conta = 0;

    //Fin: Variables registro de especialistar


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new android.app.AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Cerrar")
                    .setMessage("Confirme que realmente desea cerrar la App.")
                    .setNegativeButton(android.R.string.cancel, null)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Salir
                            //login.this.finish();

                            goBack();


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
        setContentView(R.layout.activity_login);

        //Función para evitar la rotación de la pantalla del CELULAR.
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //y esto para pantalla completa (oculta incluso la barra de estado)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        rellay1 = (RelativeLayout) findViewById(R.id.rellay1);
        rellay2 = (RelativeLayout) findViewById(R.id.rellay2);
        rellay3 = (RelativeLayout) findViewById(R.id.rellay3);

        etEmail = (EditText)findViewById(R.id.etEmail);
        etClave = (EditText)findViewById(R.id.etClave);
        tiEmail= (TextInputLayout)findViewById(R.id.tiEmail);
        tiClave= (TextInputLayout)findViewById(R.id.tiClave);
        btnLogin = (Button)findViewById(R.id.btnLogin);

        btnRegistrar = (Button)findViewById(R.id.btnRegistrar);
        btnOlvidoClave = (Button)findViewById(R.id.btnOlvidoClave);

       limpiarDatos();

        myDialog = new Dialog(this);

        handler.postDelayed(runnable, 0); //2000 is the timeout for the splash

        btnLogin.setOnClickListener(this);
        btnRegistrar.setOnClickListener(this);
        btnOlvidoClave.setOnClickListener(this);


    }

    private void limpiarDatos() {
        etEmail.setText(null);
        etClave.setText(null);
        etEmail.requestFocus();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogin:
                //Acciones a realizar por el botón clic.
                if(etEmail.getText().toString().length()==0){
                    etEmail.setError("Debe Ingresar su e-mail");
                    etEmail.requestFocus();
                    v1 = false;
                }else{
                    v1 = true;
                }

                if(v1 && etClave.getText().toString().length()==0){
                    etClave.setError("Debe Ingresar Clave");
                    etClave.requestFocus();
                    v2 = false;
                }else{
                    v2 = true;
                }

                if(v1 && v2){
                    String user = etEmail.getText().toString();
                    String clave = etClave.getText().toString();
                    if(user.equals("manuel") && clave.equals("123")){
                        Intent intent = new Intent(this, MenuPrincipal.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(this, "Usuario o contraseña incorrectos.", Toast.LENGTH_SHORT).show();
                        limpiarDatos();
                    }
                }

                break;

            case R.id.btnRegistrar:
                //Toast.makeText(this, "Registrar Especialista", Toast.LENGTH_SHORT).show();
                registroEspecialista();
                break;

            case R.id.btnOlvidoClave:
                //Toast.makeText(this, "Recuperar Contraseña", Toast.LENGTH_SHORT).show();

                break;

            default:

                break;
        }
    }


    public void goBack(){
        //Intent intent = new Intent(this, Login.class);
        //startActivity(intent);

        //finish();

        finishAffinity();
    }

    private void registroEspecialista(){
        final android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(Login.this);
        //AlertDialog.Builder mBuilder = new AlertDialog.Builder(getApplicationContext());
        //mBuilder.setIcon(R.drawable.ic_servidor);
        //mBuilder.setTitle("<<<UTLA>>>");
        mBuilder.setCancelable(false);
        final View mView = getLayoutInflater().inflate(R.layout.activity__register__dr, null);


        //OBTENIENDO LA FECHA Y HORA ACTUAL DEL SISTEMA.
        DateFormat formatodate = new SimpleDateFormat("yyyy/MM/dd");
        String date = formatodate.format(new Date());

        DateFormat formatotime = new SimpleDateFormat("HH:mm:ss a");
        String time = formatotime.format(new Date());


        //y esto para pantalla completa (oculta incluso la barra de estado)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sp_questions = (Spinner)mView.findViewById(R.id.sp_questions);  //Aca tomo la pregunta se seguridad seleccionada.
        et_respuesta = (EditText)mView.findViewById(R.id.et_respuesta);

        cb_masculino = (CheckBox)mView.findViewById(R.id.cb_masculino);
        cb_femenino = (CheckBox)mView.findViewById(R.id.cb_femenino);

        BtnCerrar = (ImageView)mView.findViewById(R.id.BtnCerrar);
        btnSave = (Button)mView.findViewById(R.id.btnSave);
        btnCancel = (Button)mView.findViewById(R.id.btnCancel);

        ti_dui = (TextInputLayout)mView.findViewById(R.id.ti_dui);
        ti_nombres = (TextInputLayout)mView.findViewById(R.id.ti_nombres);
        ti_apellidos = (TextInputLayout)mView.findViewById(R.id.ti_apellidos);
        ti_direccion = (TextInputLayout)mView.findViewById(R.id.ti_direccion);
        ti_telefono = (TextInputLayout)mView.findViewById(R.id.ti_telefono);
        ti_especialidad = (TextInputLayout)mView.findViewById(R.id.ti_especialidad);
        ti_comentario = (TextInputLayout)mView.findViewById(R.id.ti_comentario);
        ti_usuario = (TextInputLayout)mView.findViewById(R.id.ti_usuario);
        ti_password1 = (TextInputLayout)mView.findViewById(R.id.ti_password1);
        ti_password2 = (TextInputLayout)mView.findViewById(R.id.ti_password2);
        ti_respuesta = (TextInputLayout)mView.findViewById(R.id.ti_respuesta);

        et_dui = (EditText)mView.findViewById(R.id.et_dui);
        et_nombres = (EditText)mView.findViewById(R.id.et_nombres);
        et_apellidos = (EditText)mView.findViewById(R.id.et_apellidos);
        et_direccion = (EditText)mView.findViewById(R.id.et_direccion);
        et_telefono = (EditText)mView.findViewById(R.id.et_telefono);
        et_especialidad = (EditText)mView.findViewById(R.id.et_especialidad);
        et_comentario = (EditText)mView.findViewById(R.id.et_comentario);
        et_usuario = (EditText)mView.findViewById(R.id.et_usuario);
        et_password1 = (EditText)mView.findViewById(R.id.et_password1);
        et_password2 = (EditText)mView.findViewById(R.id.et_password2);
        et_respuesta = (EditText)mView.findViewById(R.id.et_respuesta);


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
                    ti_respuesta.setVisibility(View.VISIBLE);
                }else{
                    et_respuesta.setVisibility(View.GONE);
                    ti_respuesta.setVisibility(View.GONE);
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


        mBuilder.setView(mView);
        //final AlertDialog dialog = mBuilder.create();
        final android.app.AlertDialog dialog = mBuilder.create();

        BtnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Aca tengo que meter todo el código para guardar datos en la base de datos SQLite Native.
                Toast.makeText(Login.this, "Guardar", Toast.LENGTH_SHORT).show();

            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }

    private void recuperarPassword(){

    }


}
