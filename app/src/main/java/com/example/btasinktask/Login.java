package com.example.btasinktask;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.Gravity;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Login extends AppCompatActivity implements View.OnClickListener{

    Dialog myDialog;

    EditText etEmail, etClave;
    TextInputLayout tiEmail, tiClave;
    Button btnLogin, btnRegistrar, btnOlvidoClave;

    boolean v1 = false;
    boolean v2 = false;
    boolean estado_correo = false;
    boolean estado_password = false;

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

    boolean status_documento = false; boolean status_nombres = false; boolean status_apellidos = false;
    boolean status_direccion = false; boolean status_telefono = false; boolean status_especialidad = false;
    boolean status_sexo = false; boolean status_comentario = false; boolean status_usuario = false;
    boolean status_clave = false; boolean status_clave1 = false; boolean status_pregunta = false; boolean status_respuesta = false;

    String sexo = "";

    //private Cursor fila;
    db_SQLite base = new db_SQLite(this);

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
        btnRegistrar.setPaintFlags(btnRegistrar.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        btnOlvidoClave = (Button)findViewById(R.id.btnOlvidoClave);
        btnOlvidoClave.setPaintFlags(btnOlvidoClave.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

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

                if(v1 && v2) {
                    String user = etEmail.getText().toString();
                    String clave = etClave.getText().toString();

                    if (Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches() == false) {
                        //mEmail.setBackgroundColor(Color.GREEN);
                        etEmail.setText(null);
                        tiEmail.setError("Correo invalido.");
                        etEmail.requestFocus();
                        estado_correo = false;
                    } else {
                        estado_correo = true;
                        tiEmail.setError(null);
                    }

                    if (estado_correo == false && (user.length() == 0 || clave.length() == 0)) {
                        Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_LONG).show();
                        et_usuario.requestFocus();

                    } else {

                        db_SQLite admin=new db_SQLite(this);
                        SQLiteDatabase db=admin.getWritableDatabase();
                        Cursor fila=db.rawQuery("select * from tb_especialista where usuario='"+user+"' and clave='"+clave+"'",null);


                        if(fila.moveToFirst()){
                            //capturamos los valores del cursos y lo almacenamos en variable
                            String documento = fila.getString(0);
                            String nombres = fila.getString(1);
                            String apellidos = fila.getString(2);
                            String direccion = fila.getString(3);
                            String telefono = fila.getString(4);
                            String especialidad = fila.getString(5);
                            String sexo = fila.getString(6);
                            String comentario = fila.getString(7);
                            String usuario = fila.getString(8);
                            String pass = fila.getString(9);
                            String pregunta = fila.getString(10);
                            String respuesta = fila.getString(11);

                            //preguntamos si los datos ingresados son iguales
                            if (user.equals(usuario) && clave.equals(pass)) {
                                //OBTENIENDO LA FECHA Y HORA ACTUAL DEL SISTEMA.
                                DateFormat formatodate= new SimpleDateFormat("yyyy/MM/dd");
                                String date= formatodate.format(new Date());
                                DateFormat formatotime= new SimpleDateFormat("HH:mm:ss a");
                                String time= formatotime.format(new Date());

                                //Archivo para variables de inicio de sesión
                                SharedPreferences preferences = getSharedPreferences("variablesesion", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();

                                editor.putString("tipo", documento);
                                editor.putString("nombrecompleto", nombres +" "+apellidos);
                                editor.putString("usuario", usuario);
                                editor.putString("fechahora", date +" "+ time);

                                if(documento.equals("1")){
                                    editor.putBoolean("habilitaOpciones", true);
                                }else{
                                    editor.putBoolean("habilitaOpciones", false);
                                }

                                editor.commit();

                                //Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                //Intent menuPrincipal = new Intent(this, MenuPrincipal.class);
                                Intent menuPrincipal = new Intent(this, Activity_lista_pacientes.class);
                                //menuPrincipal.putExtra("usuario", usuario.toString());
                                menuPrincipal.putExtra("senal", "1");
                                menuPrincipal.putExtra("tipo", documento);
                                menuPrincipal.putExtra("username", nombres + " " + apellidos);
                                startActivity(menuPrincipal);
                                finish();
                                //limpiamos las las cajas de texto
                                etEmail.setText("");
                                etClave.setText("");
                            }
                        }else {
                                /*Toast toast = Toast.makeText(getApplicationContext(), "Campo respuesta es obligatorio", Toast.LENGTH_SHORT);
                                  toast.setGravity(Gravity.CENTER, 0, 0);
                                  toast.show();*/
                            //limpiamos las las cajas de texto
                            etEmail.setText("");
                            etClave.setText("");
                            etEmail.requestFocus();
                            Toast.makeText(getApplicationContext(), "Sorry. Usuario desconocido. \nVuelta a intentarlo nuevamente.", Toast.LENGTH_LONG).show();
                        }
                    }
                    /*if(user.equals("manuel") && clave.equals("123")){
                        Intent intent = new Intent(this, MenuPrincipal.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(this, "Usuario o contraseña incorrectos.", Toast.LENGTH_SHORT).show();
                        limpiarDatos();
                    }*/
                }

                break;

            case R.id.btnRegistrar:
                //Toast.makeText(this, "Registrar Especialista", Toast.LENGTH_SHORT).show();
                registroEspecialista();
                break;

            case R.id.btnOlvidoClave:
                //Toast.makeText(this, "Recuperar Contraseña", Toast.LENGTH_SHORT).show();
                recuperarPassword();
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


        sexo = "Masculino";


        cb_masculino.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean estado_masculino) {
                if(estado_masculino){
                    cb_femenino.setChecked(false);
                    sexo = "Masculino";
                }else{
                    cb_femenino.setChecked(true);
                    sexo = "Femenino";
                }
            }
        });

        cb_femenino.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean estado_femenino) {
                if(estado_femenino){
                    cb_masculino.setChecked(false);
                    sexo = "Femenino";
                }else{
                    cb_masculino.setChecked(true);
                    sexo = "Masculino";
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


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

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

                /*if(!et_dui.getText().toString().isEmpty()){
                    status_documento = true;
                    ti_dui.setError(null);
                    //ti_dui.setVisibility(View.GONE);
                }else{
                    status_documento = false;
                    ti_dui.setError("Campo obligatorio");
                    et_dui.requestFocus();
                }

                if(status_documento && !et_nombres.getText().toString().isEmpty()){
                   status_nombres = true;
                   ti_nombres.setError(null);
                }else{
                    status_nombres = false;
                    ti_nombres.setError("Campo obligatorio");
                    et_nombres.requestFocus();
                }

                if(status_nombres && !et_apellidos.getText().toString().isEmpty()){
                    status_apellidos = true;
                    ti_apellidos.setError(null);
                }else{
                    status_apellidos = false;
                    ti_apellidos.setError("Campo obligatorio");
                    et_apellidos.requestFocus();
                }

                //Campo dirección no lo valido porque lo he dejado como campo no obligatorio

                if(status_apellidos && !et_telefono.getText().toString().isEmpty()){
                    status_telefono = true;
                    ti_telefono.setError(null);
                }else{
                    status_telefono = false;
                    ti_telefono.setError("Campo obligatorio");
                    et_telefono.requestFocus();
                }

                //Campo especialidad no lo valido porque lo he dejado como campo no obligatorio

                //Campo comentario no lo valido porque lo he dejado como campo no obligatorio

                *//*if(status_telefono && (!et_usuario.getText().toString().isEmpty())){
                    status_usuario = true;
                    ti_usuario.setError(null);
                } else{
                    status_usuario = false;
                    ti_usuario.setError("Campo obligatorio");
                    et_usuario.requestFocus();

                }*//*

                if(status_telefono && Patterns.EMAIL_ADDRESS.matcher(et_usuario.getText().toString()).matches() == true){
                    status_usuario = true;
                    ti_usuario.setError(null);
                }else{
                    ti_usuario.setError("Correo Inválido");
                    et_usuario.setText(null);
                    et_usuario.requestFocus();
                    status_usuario = false;
                }


                if(status_usuario && !et_password1.getText().toString().isEmpty()){
                    status_clave = true;
                    ti_password1.setError(null);
                }else{
                    status_clave = false;
                    ti_password1.setError("Campo obligatorio");
                    et_password1.requestFocus();
                }


                if(status_clave && !et_password2.getText().toString().isEmpty()){
                    status_clave1 = true;
                    ti_password2.setError(null);
                }else{
                    status_clave1 = false;
                    ti_password2.setError("Campo obligatorio");
                    et_password2.requestFocus();
                }


                if (status_clave && status_clave1) {
                    if(Objects.equals(et_password1.getText().toString(), et_password2.getText().toString())){
                        status_clave = true;
                        status_clave1 = true;
                    }else{
                        status_clave = false;
                        status_clave1 = false;
                        Toast.makeText(getApplicationContext(), "La contraseña ingresada no coincide.\n", Toast.LENGTH_LONG).show();
                        et_password1.setText(null);
                        et_password2.setText(null);
                        et_password1.requestFocus();
                    }

                }else { }

                if(status_clave1 && sp_questions.getSelectedItemPosition() > 0){
                    status_pregunta = true;
                }else{
                    status_pregunta = false;
                    Toast.makeText(Login.this, "Campo Pregunta de Seguridad Obligatorio\nDebe Seleccionar una Opción", Toast.LENGTH_SHORT).show();
                }

                if(status_pregunta && !et_respuesta.getText().toString().isEmpty()){
                    status_respuesta = true;
                    ti_respuesta.setError(null);
                    //ti_respuesta.setVisibility(View.GONE);
                }else{
                    status_respuesta = false;
                    ti_respuesta.setError("Campo obligatorio");
                    //ti_respuesta.setVisibility(View.VISIBLE);
                    et_respuesta.requestFocus();
                }

                if(status_documento && status_nombres && status_apellidos && status_telefono && status_usuario && status_clave && status_clave1 && status_pregunta && status_respuesta){
                    Toast.makeText(Login.this, "Hoy si, a guardar...", Toast.LENGTH_SHORT).show();
                }*/




                if(!et_dui.getText().toString().isEmpty()){
                    status_documento = true;
                    ti_dui.setError(null);

                    if(status_documento && !et_nombres.getText().toString().isEmpty()){
                        status_nombres = true;
                        ti_nombres.setError(null);

                        if(status_nombres && !et_apellidos.getText().toString().isEmpty()){
                            status_apellidos = true;
                            ti_apellidos.setError(null);

                            //Campo dirección no lo valido porque lo he dejado como campo no obligatorio

                            if(status_apellidos && !et_telefono.getText().toString().isEmpty()){
                                status_telefono = true;
                                ti_telefono.setError(null);

                                //Campo especialidad no lo valido porque lo he dejado como campo no obligatorio
                                //Campo comentario no lo valido porque lo he dejado como campo no obligatorio
                                /*if(status_telefono && (!et_usuario.getText().toString().isEmpty())){
                                    status_usuario = true;
                                    ti_usuario.setError(null);
                                } else{
                                    status_usuario = false;
                                    ti_usuario.setError("Campo obligatorio");
                                    et_usuario.requestFocus();

                                }*/

                                if(status_telefono && Patterns.EMAIL_ADDRESS.matcher(et_usuario.getText().toString()).matches() == true){
                                    status_usuario = true;
                                    ti_usuario.setError(null);

                                    if(status_usuario && !et_password1.getText().toString().isEmpty()){
                                        status_clave = true;
                                        ti_password1.setError(null);


                                        if(status_clave && !et_password2.getText().toString().isEmpty()){
                                            status_clave1 = true;
                                            ti_password2.setError(null);

                                            if (status_clave && status_clave1) {
                                                if(Objects.equals(et_password1.getText().toString(), et_password2.getText().toString())){
                                                    status_clave = true;
                                                    status_clave1 = true;

                                                    if(status_clave1 && sp_questions.getSelectedItemPosition() > 0){
                                                        status_pregunta = true;

                                                        if(status_pregunta && !et_respuesta.getText().toString().isEmpty()){
                                                            status_respuesta = true;
                                                            ti_respuesta.setError(null);

                                                            if(status_documento && status_nombres && status_apellidos && status_telefono && status_usuario && status_clave && status_clave1 && status_pregunta && status_respuesta){
                                                                //Toast.makeText(Login.this, "Hoy si, a guardar...", Toast.LENGTH_SHORT).show();

                                                                dto datos = new dto();

                                                                //OBTENIENDO LA FECHA Y HORA ACTUAL DEL SISTEMA.
                                                                DateFormat formatodate = new SimpleDateFormat("yyyy/MM/dd");
                                                                String date = formatodate.format(new Date());

                                                                DateFormat formatotime = new SimpleDateFormat("HH:mm:ss a");
                                                                String time = formatotime.format(new Date());

                                                                SimpleDateFormat dateFormat = new SimpleDateFormat(
                                                                        "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                                                                Date date1 = new Date();
                                                                String fecha = dateFormat.format(date1);

                                                                datos.setDocumento(et_dui.getText().toString());
                                                                datos.setNombres(et_nombres.getText().toString());
                                                                datos.setApellidos(et_apellidos.getText().toString());
                                                                datos.setDireccion(et_direccion.getText().toString());
                                                                datos.setTelefono(et_telefono.getText().toString());
                                                                datos.setEspecialidad(et_especialidad.getText().toString());
                                                                datos.setSexo(sexo.toString());
                                                                datos.setComentario(et_comentario.getText().toString());
                                                                datos.setEmail(et_usuario.getText().toString());
                                                                datos.setClave(et_password1.getText().toString());
                                                                datos.setPregunta(sp_questions.getSelectedItem().toString());
                                                                datos.setRespuesta(et_respuesta.getText().toString());
                                                                datos.setFecha(fecha.toString());

                                                                if(base.addRegister(datos)){
                                                                    Toast.makeText(getApplicationContext(), "Registro creado correctamente",Toast.LENGTH_LONG).show();
                                                                    et_dui.setText(null);
                                                                    et_nombres.setText(null);
                                                                    et_apellidos.setText(null);
                                                                    et_direccion.setText(null);
                                                                    et_telefono.setText(null);
                                                                    et_especialidad.setText(null);
                                                                    sexo = "";
                                                                    et_comentario.setText(null);
                                                                    et_usuario.setText(null);
                                                                    et_password1.setText(null);
                                                                    et_password2.setText(null);
                                                                    sp_questions.setSelection(0);
                                                                    et_respuesta.setText(null);
                                                                    cb_masculino.setChecked(true);cb_femenino.setChecked(false);
                                                                    et_dui.requestFocus();
                                                                    conta=0;

                                                                }else{
                                                                    Toast.makeText(getApplicationContext(), "Error. Ya existe un registro con este" +
                                                                            " nombre de usuario: "+et_usuario.getText().toString(),Toast.LENGTH_LONG).show();
                                                                }

                                                            }

                                                        }else{
                                                            status_respuesta = false;
                                                            ti_respuesta.setError("Campo obligatorio");
                                                            //ti_respuesta.setVisibility(View.VISIBLE);
                                                            et_respuesta.requestFocus();
                                                        }

                                                    }else{
                                                        status_pregunta = false;
                                                        Toast.makeText(Login.this, "Campo Pregunta de Seguridad Obligatorio\nDebe Seleccionar una Opción", Toast.LENGTH_SHORT).show();
                                                    }

                                                }else{
                                                    status_clave = false;
                                                    status_clave1 = false;
                                                    Toast.makeText(getApplicationContext(), "Las claves ingresadas no coincide.", Toast.LENGTH_LONG).show();
                                                    et_password1.setText(null);
                                                    et_password2.setText(null);
                                                    et_password1.requestFocus();
                                                }

                                            }else { }

                                        }else{
                                            status_clave1 = false;
                                            ti_password2.setError("Campo obligatorio");
                                            et_password2.requestFocus();
                                        }



                                    }else{
                                        status_clave = false;
                                        ti_password1.setError("Campo obligatorio");
                                        et_password1.requestFocus();
                                    }

                                }else{
                                    ti_usuario.setError("Correo Inválido");
                                    et_usuario.setText(null);
                                    et_usuario.requestFocus();
                                    status_usuario = false;
                                }


                            }else{
                                status_telefono = false;
                                ti_telefono.setError("Campo obligatorio");
                                et_telefono.requestFocus();
                            }



                        }else{
                            status_apellidos = false;
                            ti_apellidos.setError("Campo obligatorio");
                            et_apellidos.requestFocus();
                        }

                    }else{
                        status_nombres = false;
                        ti_nombres.setError("Campo obligatorio");
                        et_nombres.requestFocus();
                    }

                }else{
                    status_documento = false;
                    ti_dui.setError("Campo obligatorio");
                    et_dui.requestFocus();
                }





            }
        });

    }

    private void recuperarPassword(){
        final android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(Login.this);
        //AlertDialog.Builder mBuilder = new AlertDialog.Builder(getApplicationContext());
        //mBuilder.setIcon(R.drawable.ic_servidor);
        //mBuilder.setTitle("<<<UTLA>>>");

        mBuilder.setCancelable(false);
        final View mView = getLayoutInflater().inflate(R.layout.activity__recambiar_clave, null);

        final TextInputLayout ti_usuario = (TextInputLayout)mView.findViewById(R.id.ti_usuario);
        final EditText et_usuario = (EditText)mView.findViewById(R.id.et_usuario);
        ImageView BtnCerrar = (ImageView)mView.findViewById(R.id.BtnCerrar);
        Button btnAceptar = (Button)mView.findViewById(R.id.btnAceptar);

        //y esto para pantalla completa (oculta incluso la barra de estado)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        mBuilder.setView(mView);
        final android.app.AlertDialog dialog = mBuilder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        BtnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Login.this, "O.K.", Toast.LENGTH_SHORT).show();

                if(!et_usuario.getText().toString().isEmpty()){
                    if (Patterns.EMAIL_ADDRESS.matcher(et_usuario.getText().toString()).matches() == false) {
                        ti_usuario.setError("Correo invalido.");
                        ti_usuario.requestFocus();
                        estado_correo = false;
                    } else {
                        estado_correo = true;
                        ti_usuario.setError(null);
                    }

                    if(estado_correo==true){
                        dto datos = new dto();
                        datos.setEmail(et_usuario.getText().toString());
                        if(base.consultaUser(datos)){
                            String correo = et_usuario.getText().toString();
                            Cursor fila = base.getWritableDatabase().rawQuery("select documento,nombres,apellidos,direccion,telefono,especialidad,sexo,comentario,usuario,clave,pregunta,respuesta,fecha from tb_especialista where usuario='"+correo+"'", null);
                            if (fila.moveToFirst()) {
                                String documento = fila.getString(0);
                                String nombres = fila.getString(1);
                                String apellidos = fila.getString(2);
                                String direccion = fila.getString(3);
                                String telefono = fila.getString(4);
                                String especialidad = fila.getString(5);
                                String sexo = fila.getString(6);
                                String comentario = fila.getString(7);
                                String email = fila.getString(8);
                                String clave = fila.getString(9);
                                String pregunta = fila.getString(10);
                                String respuesta = fila.getString(11);
                                String fecha = fila.getString(12);


                                //Toast.makeText(getApplicationContext(),"Usuario encontrado: " + nombres + " " + apellidos,Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                                recuperarPassword1(documento, nombres + " "+ apellidos, pregunta);

                            } else {
                                //Toast.makeText(getApplicationContext(), "No se encontrarón resultados que mostrar para la busqueda especificada", Toast.LENGTH_SHORT).show();
                            }
                            base.close();

                        }else{
                            Toast.makeText(getApplicationContext(), "No se han encontrado resultados en la busqueda especificada.",Toast.LENGTH_LONG).show();
                            et_usuario.setText(null);
                            et_usuario.requestFocus();
                        }

                    }else{
                        et_usuario.setText(null);
                        et_usuario.requestFocus();
                        Toast.makeText(getApplicationContext(), "El nombre de usuario ingresado no es válido. Debe ser una dirección de e-mail.", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Es obligatorio que ingrese su nombre de usuario.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void recuperarPassword1(final String documento, String nombres, String pregunta){
        final android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(Login.this);
        //AlertDialog.Builder mBuilder = new AlertDialog.Builder(getApplicationContext());
        //mBuilder.setIcon(R.drawable.ic_servidor);
        //mBuilder.setTitle("<<<UTLA>>>");

        mBuilder.setCancelable(false);
        final View mView = getLayoutInflater().inflate(R.layout.activity_recambiar_clave1, null);

        /*Toast.makeText(this, "Información Recibida: "+ "\n" +
                "Documento: "+documento+"\n" +
                "Nombre Completo: "+nombres+"\n" +
                "Pregutna: "+pregunta, Toast.LENGTH_SHORT).show();*/
        TextView tv_mensaje = mView.findViewById(R.id.tv_mensaje);

        ImageView ivclose = (ImageView)mView.findViewById(R.id.ivclose);

        Button btnAceptar = (Button)mView.findViewById(R.id.btnAceptar);
        Button btnCompleteOperation = (Button)mView.findViewById(R.id.btnCompleteOperation);

        TextView tv_question_bd = (TextView)mView.findViewById(R.id.tv_question_bd);
        final TextView tv_id = (TextView)mView.findViewById(R.id.tv_id);                    //En este campo envio el id del registro a verificar.
        //tv_id.setText(""+id);

        final EditText et_respuesta = (EditText)mView.findViewById(R.id.et_respuesta);
        //final TextInputLayout tiCorreo = (TextInputLayout)myDialog.findViewById(R.id.tiCorreo);

        tv_question_bd.setText(nombres +"\n"+pregunta);

        tv_mensaje.setText("FELICIDADES!!! " + nombres +"\n YA PUEDE RESTABLECER SU CLAVE");

        final LinearLayout bloque1 = (LinearLayout)mView.findViewById(R.id.bloque1);
        final LinearLayout bloque2 = (LinearLayout)mView.findViewById(R.id.bloque2);
        bloque2.setEnabled(false);
        bloque2.setVisibility(View.INVISIBLE);
        bloque2.setVisibility(View.GONE);

        final EditText et_pass1 = (EditText)mView.findViewById(R.id.et_pass1);
        final EditText et_pass2 = (EditText)mView.findViewById(R.id.et_pass2);
        et_pass1.setEnabled(false);
        et_pass2.setEnabled(false);


        mBuilder.setView(mView);
        final android.app.AlertDialog dialog = mBuilder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        ivclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_respuesta.getText().toString().isEmpty()) {
                    //Toast.makeText(getApplicationContext(),"Debe ingresar los datos de su cuenta.",Toast.LENGTH_LONG).show();
                    Toast toast = Toast.makeText(getApplicationContext(), "Campo respuesta es obligatorio.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }else{
                    //Toast.makeText(getApplicationContext(), "Vamos bien...", Toast.LENGTH_LONG).show();
                    dto datos = new dto();
                    datos.setRespuesta(et_respuesta.getText().toString());
                    if(base.verificoRespuesta(datos)) {
                        Toast toast = Toast.makeText(getApplicationContext(), "EXCELENTE. \nAhora puede ingresar una nueva clave para su cuenta.", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        et_respuesta.setText(null);
                        bloque2.setVisibility(View.VISIBLE);
                        bloque2.setEnabled(true);
                        et_pass1.setEnabled(true);
                        et_pass2.setEnabled(true);
                        bloque1.setVisibility(View.GONE);
                    }else{
                        Toast toast = Toast.makeText(getApplicationContext(), "La respuesta ingresada es incorrecta. \n\nRespuesta: " + et_respuesta.getText().toString(), Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        et_respuesta.setText(null);
                        et_respuesta.requestFocus();
                    }
                }
            }
        });


        btnCompleteOperation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!et_pass1.getText().toString().isEmpty()) && (!et_pass2.getText().toString().isEmpty())) {
                    if (Objects.equals(et_pass1.getText().toString(), et_pass2.getText().toString())) {
                        String clave=et_pass1.getText().toString();
                        String codigo=documento;
                        //tv_id.setText(codigo);

                        dto datos = new dto();
                        datos.setDocumento(codigo);
                        datos.setClave(clave);
                        if(base.updateClave(datos)) {
                            Toast toast = Toast.makeText(getApplicationContext(), "FELICIDADES!!!.\nSu clave ha sido restablecida correctamente.", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                            //myDialog.cancel();
                            dialog.dismiss();
                        }else{
                            Toast.makeText(getApplicationContext(), "Error al intentar actualizar su clave.\n" +
                                    "Intentelo mas tarde...", Toast.LENGTH_LONG).show();
                        }

                    }else{
                        Toast toast = Toast.makeText(getApplicationContext(), "Las contraseñas ingresadas no coinciden", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        et_pass1.requestFocus();
                        et_pass1.setText(null);
                        et_pass2.setText(null);
                    }
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Debe ingresar y confirmar clave nueva", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });




    }


}
