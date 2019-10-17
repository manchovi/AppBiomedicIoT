package com.example.btasinktask;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Patterns;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MenuPrincipal extends AppCompatActivity {

    private ViewGroup linearLayoutDetails1, linearLayoutDetails2, linearLayoutDetails3, linearLayoutDetails4;
    private ImageView imageViewExpand1, imageViewExpand2, imageViewExpand3, imageViewExpand4;

    private LinearLayout linearLayoutCardContent;

    private static final int DURATION = 250;

    boolean estado_nombre;
    boolean estado_dir_api;
    boolean estado_dir_portal;

    CarouselView carouselView;
    AlertDialog.Builder dialogo; AlertDialog.Builder dialog;

    int[] sampleImages = {R.drawable.image_1, R.drawable.image_2, R.drawable.image_3, R.drawable.image_4, R.drawable.image_5, R.drawable.image_6,
                          R.drawable.image_7, R.drawable.image_8, R.drawable.image_9, R.drawable.image_10, R.drawable.image_11, R.drawable.image_12,
                          R.drawable.image_13, R.drawable.image_14, R.drawable.image_15};

    int[] sampleImages1 = {R.drawable.image_1, R.drawable.image_2, R.drawable.image_3, R.drawable.image_4, R.drawable.image_5};

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new android.app.AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_close)
                    .setTitle("Warning")
                    .setMessage("¿Realmente desea cerrar esta actividad?")
                    .setNegativeButton(android.R.string.cancel, null)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {//un listener que al pulsar, cierre la aplicacion
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            /*Intent intent = new Intent(DashboardLuces.this, luces_control_sms.class);
                            startActivity(intent);*/
                            //MainActivity.this.finishAffinity();

                            goBack();
                            //finish();
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
        setContentView(R.layout.activity_menu_principal);

        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbar.setTitleTextColor(getResources().getColor(R.color.mycolor1));
        toolbar.setTitleMargin(0, 0, 0, 0);
        toolbar.setSubtitle("HOSPITAL SANTA TERESA");
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.mycolor));
        toolbar.setTitle("APP-BIOMEDIC IoT");
        setSupportActionBar(toolbar);

        //y esto para pantalla completa (oculta incluso la barra de estado)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmacion();
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages1.length);
        carouselView.setImageListener(imageListener);

        linearLayoutCardContent = (LinearLayout)findViewById(R.id.linearLayoutCardContent);
        linearLayoutCardContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                config_server();
            }
        });


        //Codigo del cardview
        linearLayoutDetails1 = (ViewGroup) findViewById(R.id.linearLayoutDetails1);
        imageViewExpand1 = (ImageView) findViewById(R.id.imageViewExpand1);

        linearLayoutDetails2 = (ViewGroup) findViewById(R.id.linearLayoutDetails2);
        imageViewExpand2 = (ImageView) findViewById(R.id.imageViewExpand2);

        Toolbar toolbarCard1 = (Toolbar) findViewById(R.id.toolbarCard1);
        toolbarCard1.setTitle(R.string.configurations1);
        toolbarCard1.setSubtitle(R.string.subtitle1);
        toolbarCard1.inflateMenu(R.menu.menu_card);
        toolbarCard1.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_option1:
                        Toast.makeText(MenuPrincipal.this, R.string.option1, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_option2:
                        Toast.makeText(MenuPrincipal.this, R.string.option2, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_option3:
                        Toast.makeText(MenuPrincipal.this, R.string.option3, Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });


        Toolbar toolbarCard2 = (Toolbar) findViewById(R.id.toolbarCard2);
        toolbarCard2.setTitle(R.string.configurations2);
        toolbarCard2.setSubtitle(R.string.subtitle2);
        toolbarCard2.inflateMenu(R.menu.menu_card);
        toolbarCard2.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_option1:
                        Toast.makeText(MenuPrincipal.this, R.string.option1, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_option2:
                        Toast.makeText(MenuPrincipal.this, R.string.option2, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_option3:
                        Toast.makeText(MenuPrincipal.this, R.string.option3, Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });


    }


    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };



    public void toggleDetails1(View view) {
        if (linearLayoutDetails1.getVisibility() == View.GONE) {
            ExpandAndCollapseViewUtil.expand(linearLayoutDetails1, DURATION);
            imageViewExpand1.setImageResource(R.mipmap.more);
            rotate1(-180.0f);
        } else {
            ExpandAndCollapseViewUtil.collapse(linearLayoutDetails1, DURATION);
            imageViewExpand1.setImageResource(R.mipmap.less);
            rotate1(180.0f);
        }
    }

    public void toggleDetails2(View view) {
        if (linearLayoutDetails2.getVisibility() == View.GONE) {
            ExpandAndCollapseViewUtil.expand(linearLayoutDetails2, DURATION);
            imageViewExpand2.setImageResource(R.mipmap.more);
            rotate2(-180.0f);
        } else {
            ExpandAndCollapseViewUtil.collapse(linearLayoutDetails2, DURATION);
            imageViewExpand2.setImageResource(R.mipmap.less);
            rotate2(180.0f);
        }
    }

    private void rotate1(float angle) {
        Animation animation = new RotateAnimation(0.0f, angle, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setFillAfter(true);
        animation.setDuration(DURATION);
        imageViewExpand1.startAnimation(animation);
    }

    private void rotate2(float angle) {
        Animation animation = new RotateAnimation(0.0f, angle, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setFillAfter(true);
        animation.setDuration(DURATION);
        imageViewExpand2.startAnimation(animation);
    }



    private void confirmacion(){
        String mensaje = "¿Realmente desea cerrar esta pantalla?";
        dialogo = new AlertDialog.Builder(MenuPrincipal.this);
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
        //finish();
        finishAffinity();
    }




    private void config_server() {
        final android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(MenuPrincipal.this);
        //AlertDialog.Builder mBuilder = new AlertDialog.Builder(getApplicationContext());
        //mBuilder.setIcon(R.drawable.ic_servidor);
        //mBuilder.setTitle("<<<UTLA>>>");

        mBuilder.setCancelable(false);
        final View mView = getLayoutInflater().inflate(R.layout.dialog_server, null);

        //controles donde muestro informacion del archivo credenciales.xml creado con
        //SharedPreferences.
        final Spinner sprotocolo = (Spinner)mView.findViewById(R.id.sprotocolo);
        final TextView tv_encabezado = (TextView)mView.findViewById(R.id.tv_encabezado);
        final TextView tv_encabezado1 = (TextView)mView.findViewById(R.id.tv_encabezado1);
        final TextView tv_servidor = (TextView)mView.findViewById(R.id.tv_servidor);
        final TextView tv_directorio = (TextView)mView.findViewById(R.id.tv_directorio);
        final TextView tv_directorioPortal = (TextView)mView.findViewById(R.id.tv_directorioPortal);
        final TextView tv_fecha = (TextView)mView.findViewById(R.id.tv_fecha);
        final TextView tv_hora = (TextView)mView.findViewById(R.id.tv_hora);

        final EditText dominio = (EditText) mView.findViewById(R.id.et_dominio);
        final EditText directorio = (EditText) mView.findViewById(R.id.et_directorio);
        final EditText portal = (EditText) mView.findViewById(R.id.et_portal);
        final TextInputLayout ti_dominio=(TextInputLayout)mView.findViewById(R.id.ti_dominio);

        Button btnSave = (Button)mView.findViewById(R.id.btnSave);      //BOTONES DEL DIALOG CONFIGURACION.
        Button btnCancel = (Button)mView.findViewById(R.id.btnCancel);
        TextView txtclose = (TextView)mView.findViewById(R.id.txtclose);

        final String[] lista =new String[]{
                "http://",
                "https://",
        };
        ArrayAdapter<String> adaptador1 =new ArrayAdapter<String> (this,android.R.layout.simple_spinner_item, lista);
        sprotocolo.setAdapter(adaptador1);

        Toast toast = Toast.makeText(getApplicationContext(), "Settings Your Server PC", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        //Buscando datos en archivo credenciales.xml
        SharedPreferences preferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        String server = preferences.getString("servidor","Sin configurar.");
        String folder = preferences.getString("folder","Sin configurar.");
        String porta = preferences.getString("portal","Sin configurar.");
        String fec = preferences.getString("fecha", "NA.");
        String hor = preferences.getString("hora", "NA.");
        if (server.equals("Sin configurar.")){
            tv_servidor.setText("Servidor: " +server);
            tv_directorio.setText("API-WebService: " + folder);
            tv_directorioPortal.setText("Portal Web: " + porta);
            tv_fecha.setText("Fecha de Registro: "+fec);
            tv_hora.setText("Hora de Registro: "+hor);
        }else{
            tv_encabezado.setVisibility(mView.VISIBLE);
            tv_encabezado.setText("Server API/Web service: "+ server + folder);
            tv_encabezado1.setText("Server Portal Web: "+ server + porta);
            tv_servidor.setVisibility(mView.GONE);
            tv_directorio.setVisibility(mView.GONE);
            tv_directorioPortal.setVisibility(mView.GONE);
            tv_fecha.setVisibility(mView.GONE);
            tv_hora.setVisibility(mView.GONE);
        }

        dominio.setText(server);
        directorio.setText(folder);
        portal.setText(porta);
        ////////////////////////////////////////////////////////////////////////////////////
        /*mBuilder.setPositiveButton("GUARDAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(),"JAJAJA",Toast.LENGTH_LONG).show();

            }
        });
        //mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
        mBuilder.setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });*/
        ////////////////////////////////////////////////////////////////////////////////////
        mBuilder.setView(mView);
        //final AlertDialog dialog = mBuilder.create();
        final android.app.AlertDialog dialog = mBuilder.create();
        dialog.show();

        //btnVerificarRespuesta.setOnClickListener(new View.OnClickListener() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estado_nombre = false;
                estado_dir_api = false;
                estado_dir_portal = false;
                //if (Patterns.DOMAIN_NAME.matcher(dominio.getText().toString()).matches()==false){
                if (Patterns.DOMAIN_NAME.matcher(dominio.getText().toString()).matches() == false &&
                        Patterns.IP_ADDRESS.matcher(dominio.getText().toString()).matches() == false) {
                    dominio.setText(null);
                    dominio.setError("Nombre de dominio o IP inválido");
                    ti_dominio.setError("Nombre de dominio o IP inválido");
                    estado_nombre = false;
                    dominio.requestFocus();
                    //ti_dominio.setVisibility(View.VISIBLE);
                } else {
                    estado_nombre = true;
                    ti_dominio.setError(null);
                    //ti_dominio.setVisibility(View.GONE);
                }

                if(directorio.getText().toString().length()==0){
                    directorio.setError("Campo Obligadorio");
                    directorio.requestFocus();
                    estado_dir_api = false;
                }else{
                    estado_dir_api = true;
                }

                if(portal.getText().toString().length()==0){
                    portal.setError("Campo Obligadorio");
                    portal.requestFocus();
                    estado_dir_portal = false;
                }else{
                    estado_dir_portal = true;
                }


                if(estado_nombre && estado_dir_api && estado_dir_portal){
                    SharedPreferences preferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
                    //OBTENIENDO LA FECHA Y HORA ACTUAL DEL SISTEMA.
                    DateFormat formatodate= new SimpleDateFormat("yyyy/MM/dd");
                    String date= formatodate.format(new Date());
                    DateFormat formatotime= new SimpleDateFormat("HH:mm:ss a");
                    String time= formatotime.format(new Date());
                    String servidor = dominio.getText().toString();
                    String folder = directorio.getText().toString();
                    String folder1 = portal.getText().toString();

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("servidor", sprotocolo.getSelectedItem().toString() + servidor + "/");
                    editor.putString("folder", folder);
                    editor.putString("portal", folder1);
                    editor.putString("fecha", date);
                    editor.putString("hora", time);
                    editor.commit();

                    tv_encabezado.setVisibility(mView.VISIBLE);
                    tv_encabezado.setText("Server API-Web Service: "+ servidor +"/" + folder);

                    tv_encabezado1.setVisibility(mView.VISIBLE);
                    tv_encabezado1.setText("Server Portal Web: "+ servidor +"/" + folder1);

                    tv_servidor.setVisibility(mView.VISIBLE);
                    tv_directorio.setVisibility(mView.VISIBLE);
                    tv_fecha.setVisibility(mView.VISIBLE);
                    tv_hora.setVisibility(mView.VISIBLE);

                    tv_servidor.setText("Server: " +servidor);
                    tv_directorio.setText("Directorios: " +folder+", "+folder1);
                    tv_fecha.setText("Fecha de Registro: "+date);
                    tv_hora.setText("Hora de Registro: "+time);

                    //CON ESTE CODIGO LOGRE CERRAR EL DIAGO1-INICIO-FIN
                    /*mBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            dialog.cancel();
                        }
                    });
                    dialog.cancel();*/

                    Toast.makeText(getApplicationContext(),"Registro creado correctamente!!!",Toast.LENGTH_LONG).show();
                }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CON ESTE CODIGO LOGRE CERRAR EL DIAGO1-INICIO-FIN
                mBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        dialog.cancel();
                    }
                });
                dialog.cancel();
            }
        });


        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

    }




}


