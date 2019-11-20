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
import android.widget.Toast;

import java.util.ArrayList;

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

        ImageView BtnCerrar = (ImageView)mView.findViewById(R.id.BtnCerrar);
        Button btnSalir = (Button)mView.findViewById(R.id.btnSalir);

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


    }


}
