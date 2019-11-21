package com.example.btasinktask;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Activity_lista_pacientes extends AppCompatActivity {

    AlertDialog.Builder dialogo; AlertDialog.Builder dialog;

    String[] version = {"Aestro","Blender","CupCake","Donut","Eclair","Froyo","GingerBread","HoneyComb","IceCream Sandwich",
            "Jelly Bean","Kitkat","Lolipop","Marshmallow","Nought","Oreo","Pie"};

    String elementos[] = {"Uno", "Dos", "Tres", "Cuatro", "Cinco"};


    ListView listViewPacientes;
    ArrayAdapter adaptador;

    SearchView searchView;
    private EditText et_search;

    ListView listView;
    ArrayList<String> list;
    ArrayAdapter adapter;

    db_SQLite conexion = new db_SQLite(this);
    dto datos = new dto();
    int conta = 0;

    boolean status_documento = false; boolean status_nombres = false; boolean status_apellidos = false;
    boolean status_direccion = false; boolean status_telefono = false; boolean status_estado = false;
    boolean status_comentario = false; boolean status_nombre_contacto_pariente = false;
    boolean status_telefono_contacto_pariente = false; boolean status_direccion_contacto_pariente = false;
    boolean status_documento_especialista  = false;

    String documento = "";
    db_SQLite base = new db_SQLite(this);

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new android.app.AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_close)
                    .setTitle("Advertencia")
                    .setMessage("¿Realmente desea salir?")
                    .setNegativeButton(android.R.string.cancel, null)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //MainActivity.this.finishAffinity();
                            //finish();
                            //confirmacion();
                            goBack();
                        }
                    })
                    .show();
            return true;
        }
        //para las demas cosas, se reenvia el evento al listener habitual
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pacientes);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitleMargin(0, 0, 0, 0);
        toolbar.setSubtitle("HISTORIAL DE PACIENTES");
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.mycolor));
        toolbar.setTitle("REGISTRO DE PACIENTES");
        setSupportActionBar(toolbar);

        //y esto para pantalla completa (oculta incluso la barra de estado)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmacion();
            }
        });

        listViewPacientes = (ListView) findViewById(R.id.listViewPacientes);
        //searchView = (SearchView) findViewById(R.id.searchView);
        et_search = (EditText)findViewById(R.id.et_search);

        adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, version);
        listViewPacientes.setAdapter(adaptador);


        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adaptador.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        listViewPacientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Alabanzas a = (Alabanzas) parent.getItemAtPosition(position);
                //listar.get(position);

                String dato = (String) parent.getItemAtPosition(position);

                AlertDialog.Builder dialog = new AlertDialog.Builder(Activity_lista_pacientes.this);
                dialog.setCancelable(true);
                dialog.setTitle("Contenido Registro");
                dialog.setMessage(dato);
                dialog.show();
            }
        });



    }


    private void confirmacion(){
        String mensaje = "¿Realmente desea cerrar esta pantalla?";
        dialogo = new AlertDialog.Builder(Activity_lista_pacientes.this);
        dialogo.setIcon(R.drawable.ic_close);
        dialogo.setTitle("Warning");
        dialogo.setMessage(mensaje);
        dialogo.setCancelable(false);
        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo, int id) {
                /*Intent intent = new Intent(DashboardLuces.this, luces_control_sms.class);
                startActivity(intent);*/
                //DashboardLuces.this.finishAffinity();

                //MenuPrincipal.this.finish();
                goBack();
            }
        });
        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo, int id) {
                //Toast.makeText(getApplicationContext(), "Operación Cancelada.", Toast.LENGTH_LONG).show();
            }
        });
        dialogo.show();

    }

    public void goBack(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
        //finishAffinity();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_pacientes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button_green_round, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id==R.id.menu_pacientes){
            //Acciones a realizar
            //Toast.makeText(this, "Clic en opción pacientes", Toast.LENGTH_SHORT).show();
            dialogRegisterPacientes();
            return true;
        }else if(id==R.id.menu_salir) {
            /*Intent intent = new Intent(this, SignalsMonitor.class);
            startActivity(intent);*/
            Toast.makeText(this, "Clic en opción salir", Toast.LENGTH_SHORT).show();

            return true;
        /*}else if(id==R.id.menu_monitor1){

            return true;
        }*/

        }

        return super.onOptionsItemSelected(item);

    }

    private void dialogRegisterPacientes(){
        final android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(Activity_lista_pacientes.this);
        //AlertDialog.Builder mBuilder = new AlertDialog.Builder(getApplicationContext());
        //mBuilder.setIcon(R.drawable.ic_servidor);
        //mBuilder.setTitle("<<<UTLA>>>");
        mBuilder.setCancelable(false);
        final View mView = getLayoutInflater().inflate(R.layout.activity__register__pacientes, null);

        final TextInputLayout ti_dui = (TextInputLayout)mView.findViewById(R.id.ti_dui);
        final EditText et_dui = (EditText)mView.findViewById(R.id.et_dui);

        final TextInputLayout ti_nombres = (TextInputLayout)mView.findViewById(R.id.ti_nombres);
        final EditText et_nombres = (EditText)mView.findViewById(R.id.et_nombres);

        final TextInputLayout ti_apellidos = (TextInputLayout)mView.findViewById(R.id.ti_apellidos);
        final EditText et_apellidos = (EditText)mView.findViewById(R.id.et_apellidos);

        final TextInputLayout ti_direccion = (TextInputLayout)mView.findViewById(R.id.ti_direccion);
        final EditText et_direccion = (EditText)mView.findViewById(R.id.et_direccion);

        final TextInputLayout ti_telefono = (TextInputLayout)mView.findViewById(R.id.ti_telefono);
        final EditText et_telefono = (EditText)mView.findViewById(R.id.et_telefono);

        final TextInputLayout ti_estado = (TextInputLayout)mView.findViewById(R.id.ti_estado);
        final EditText et_estado = (EditText)mView.findViewById(R.id.et_estado);

        final TextInputLayout ti_comentario = (TextInputLayout)mView.findViewById(R.id.ti_comentario);
        final EditText et_comentario = (EditText)mView.findViewById(R.id.et_comentario);

        final TextInputLayout ti_nombre_contacto_paciente = (TextInputLayout)mView.findViewById(R.id.ti_nombre_contacto_paciente);
        final EditText et_nombre_contacto_paciente = (EditText)mView.findViewById(R.id.et_nombre_contacto_paciente);

        final TextInputLayout ti_telefono_contacto_paciente = (TextInputLayout)mView.findViewById(R.id.ti_telefono_contacto_paciente);
        final EditText et_telefono_contacto_paciente = (EditText)mView.findViewById(R.id.et_telefono_contacto_paciente);

        final TextInputLayout ti_direccion_contacto_paciente = (TextInputLayout)mView.findViewById(R.id.ti_direccion_contacto_paciente);
        final EditText et_direccion_contacto_paciente = (EditText)mView.findViewById(R.id.et_direccion_contacto_paciente);

        final Spinner sp_especialista = (Spinner)mView.findViewById(R.id.sp_especialista);

        //adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, conexion.consultaEspecialistas());
        adaptador = new ArrayAdapter(this, R.layout.spinnerstyle, conexion.consultaEspecialistas());
        sp_especialista.setAdapter(adaptador);

        sp_especialista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(conta>=1 && sp_especialista.getSelectedItemPosition()>0){
                //if(conta>=1){
                    //Toast.makeText(login.this, combo.getSelectedItem().toString(),Toast.LENGTH_LONG).show();

                    /*Toast toast = Toast.makeText(getApplicationContext(), sp_especialista.getSelectedItem().toString(), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();*/
                    String dato_SP = sp_especialista.getSelectedItem().toString();
                    String s[] = dato_SP.split("~");
                    documento = s[0];
                    String nombres = s[1];
                    /*Toast.makeText(Activity_lista_pacientes.this, "Documento: "+documento+"\n" +
                            "Nombre: "+nombres, Toast.LENGTH_SHORT).show();*/

                }else{

                }
                conta++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ImageView BtnCerrar = (ImageView)mView.findViewById(R.id.BtnCerrar);
        Button btnSalir = (Button)mView.findViewById(R.id.btnSalir);
        Button btnGuardar = (Button)mView.findViewById(R.id.btnGuardar);

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

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!et_dui.getText().toString().isEmpty()) {
                    status_documento = true;
                    ti_dui.setError(null);

                    if(status_documento && !et_nombres.getText().toString().isEmpty()) {
                        status_nombres = true;
                        ti_nombres.setError(null);

                        if(status_nombres && !et_apellidos.getText().toString().isEmpty()) {
                            status_apellidos = true;
                            ti_apellidos.setError(null);

                            //Campo dirección no lo valido porque lo he dejado como campo no obligatorio

                            if(status_apellidos && !et_telefono.getText().toString().isEmpty()) {
                                status_telefono = true;
                                ti_telefono.setError(null);

                                if(status_telefono && !et_estado.getText().toString().isEmpty()) {
                                    status_estado = true;
                                    ti_estado.setError(null);

                                        //Campo fecha, comentario, nombre_cont_p, telefono_cont_p, direccion_cont_p no lo valido porque lo he dejado como campo no obligatorio
                                        if(status_estado && sp_especialista.getSelectedItemPosition() > 0) {
                                            status_documento_especialista = true;

                                            if(status_documento && status_nombres && status_apellidos && status_telefono && status_estado && status_documento_especialista){

                                                dto_pacientes datos = new dto_pacientes();

                                                //OBTENIENDO LA FECHA Y HORA ACTUAL DEL SISTEMA.
                                                DateFormat formatodate = new SimpleDateFormat("yyyy/MM/dd");
                                                String date = formatodate.format(new Date());

                                                DateFormat formatotime = new SimpleDateFormat("HH:mm:ss a");
                                                String time = formatotime.format(new Date());

                                                SimpleDateFormat dateFormat = new SimpleDateFormat(
                                                        "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                                                Date date1 = new Date();
                                                String fecha = dateFormat.format(date1);

                                                datos.setDui(et_dui.getText().toString());
                                                datos.setNombres(et_nombres.getText().toString());
                                                datos.setApellidos(et_apellidos.getText().toString());
                                                datos.setDireccion(et_direccion.getText().toString());
                                                datos.setTelefono(et_telefono.getText().toString());
                                                datos.setEstado1(et_estado.getText().toString());
                                                datos.setFecha1(fecha.toString());
                                                datos.setComentario(et_comentario.getText().toString());
                                                datos.setNombre_cont_p(et_nombre_contacto_paciente.getText().toString());
                                                datos.setTelefono_cont_p(et_telefono_contacto_paciente.getText().toString());
                                                datos.setDireccion_cont_p(et_direccion_contacto_paciente.getText().toString());
                                                datos.setDocumento_especialista(documento);

                                                if(base.addRegisterPaciente(datos)){
                                                    Toast.makeText(getApplicationContext(), "Registro paciente guardado correctamente",Toast.LENGTH_LONG).show();
                                                    et_dui.setText(null);
                                                    et_nombres.setText(null);
                                                    et_apellidos.setText(null);
                                                    et_direccion.setText(null);
                                                    et_telefono.setText(null);
                                                    et_estado.setText(null);
                                                    et_comentario.setText(null);
                                                    et_nombre_contacto_paciente.setText(null);
                                                    et_telefono_contacto_paciente.setText(null);
                                                    et_direccion_contacto_paciente.setText(null);
                                                    sp_especialista.setSelection(0);
                                                    et_dui.requestFocus();
                                                    conta=0;

                                                }else{
                                                    Toast.makeText(getApplicationContext(), "Error. Ya existe un registro con este" +
                                                            " número de DUI: "+et_dui.getText().toString(),Toast.LENGTH_LONG).show();
                                                }

                                            }else{
                                                Toast.makeText(Activity_lista_pacientes.this, "Completo los campo con obligatorios (*)", Toast.LENGTH_SHORT).show();
                                            }

                                        }else{
                                            status_documento_especialista = false;
                                            Toast.makeText(Activity_lista_pacientes.this, "Seleccione el Especialista Responsable", Toast.LENGTH_SHORT).show();
                                        }

                                }else{
                                    status_estado = false;
                                    ti_estado.setError("Campo obligatorio");
                                    et_estado.requestFocus();
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


}
