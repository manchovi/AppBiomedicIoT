package com.example.btasinktask;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

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

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MenuPrincipal extends AppCompatActivity {

    private ViewGroup linearLayoutDetails1, linearLayoutDetails2, linearLayoutDetails3, linearLayoutDetails4, linearLayoutDetails5, linearLayoutDetails6, linearLayoutDetails7, linearLayoutDetails8;
    private ImageView imageViewExpand1, imageViewExpand2, imageViewExpand3, imageViewExpand4, imageViewExpand5, imageViewExpand6, imageViewExpand7, imageViewExpand8;


    private LinearLayout linearLayoutCardContent0;
    private ImageView iv_config_server, iv_config_especialista, iv_config_alerta, iv_config_tc, iv_config_fr, iv_config_pa, iv_config_spo2_pulso, iv_config_monitor;



    private CardView config_server,config_especialista,alertaTemprana,config_tc,config_fr,config_pa,config_fc_spo2,config_monitorAll;
    private CardViewActivity config_server1;

    private TextView tv_footer;

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

    String senal;
    String documentoEspecialista;
    String nombreEspecialista;
    String nombrePaciente;
    String tel_especialista;
    String correo_especialista;

    boolean estado_tel = false;
    boolean estado_ema = false;

    dto datos = new dto();
    db_SQLite instancia = new db_SQLite(this);

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

        //Función para evitar la rotación de la pantalla del CELULAR.
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //y esto para pantalla completa (oculta incluso la barra de estado)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        config_server = (CardView)findViewById(R.id.config_server);
        config_especialista = (CardView)findViewById(R.id.config_especialista);
        alertaTemprana = (CardView)findViewById(R.id.config_alertaTemprana);
        config_tc = (CardView)findViewById(R.id.config_tc);
        config_fr = (CardView)findViewById(R.id.config_fr);
        config_pa = (CardView)findViewById(R.id.config_pa);
        config_fc_spo2 = (CardView)findViewById(R.id.config_pa);
        config_monitorAll = (CardView)findViewById(R.id.config_monitorAll);

        tv_footer = findViewById(R.id.tv_footer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitleMargin(0, 0, 0, 0);
        toolbar.setSubtitle("HOSPITAL SANTA TERESA");
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
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


        try {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();

            if (bundle != null) {
                senal = bundle.getString("senal");;
                documentoEspecialista = bundle.getString("documento");
                nombreEspecialista = bundle.getString("nombreEspecialista");
                nombrePaciente = bundle.getString("nombrePaciente");
                tel_especialista = bundle.getString("telefono_especialista");
                correo_especialista = bundle.getString("correo_especialista");

                /*Toast.makeText(this, "Documento Especialista: "+documentoEspecialista + "\n"+
                        "Nombre Especialista: "+nombreEspecialista + "\n" +
                        "Nombre Paciente: "+nombrePaciente, Toast.LENGTH_SHORT).show();*/

                if (senal.equals("1")) {

                }
            }

        }catch (Exception e){

        }

        detallePacienteEspecialista();

        tv_footer.setText("Documento Especialista: "+documentoEspecialista + "\n"+
                "Nombre Especialista: "+nombreEspecialista + "\n" +
                "Nombre Paciente: "+nombrePaciente);


        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages1.length);
        carouselView.setImageListener(imageListener);

        /*
        linearLayoutCardContent0 = (LinearLayout)findViewById(R.id.linearLayoutCardContent0);
        linearLayoutCardContent0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                config_server();
            }
        });

        config_server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                config_server();
            }
        });*/

        iv_config_server = (ImageView)findViewById(R.id.iv_config_server);
        iv_config_especialista = (ImageView)findViewById(R.id.iv_config_especialista);
        iv_config_alerta = (ImageView)findViewById(R.id.iv_config_alerta);
        iv_config_tc = (ImageView)findViewById(R.id.iv_config_tc);
        iv_config_fr = (ImageView)findViewById(R.id.iv_config_fr);
        iv_config_pa = (ImageView)findViewById(R.id.iv_config_pa);
        iv_config_spo2_pulso = (ImageView)findViewById(R.id.iv_config_spo2_pulso);
        iv_config_monitor = (ImageView)findViewById(R.id.iv_config_monitor);


        iv_config_server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                config_server();
            }
        });

        iv_config_especialista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_config_espelialista();
            }
        });

        iv_config_alerta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_config_notificaciones();
            }
        });

        iv_config_tc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*documentoEspecialista
                nombreEspecialista
                nombrePaciente*/
                String comprobacion = conf_Server().trim();
                if (comprobacion.equals("Sin configurar.")) {
                    mensaje1();                                //Estos mensajes los pondré con Toast Personalizado.
                }else {
                    Intent intent = new Intent(MenuPrincipal.this, SignalMonitorTC.class);
                    intent.putExtra("senalTC", "1");
                    intent.putExtra("documento", documentoEspecialista);
                    intent.putExtra("nombreEspecialista", nombreEspecialista);
                    intent.putExtra("nombrePaciente", nombrePaciente);
                    startActivity(intent);
                }
            }
        });

        iv_config_fr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuPrincipal.this, SignalMonitorFR.class);
                intent.putExtra("senalFR", "1");
                intent.putExtra("documento", documentoEspecialista);
                intent.putExtra("nombreEspecialista", nombreEspecialista);
                intent.putExtra("nombrePaciente", nombrePaciente);
                startActivity(intent);
            }
        });

        iv_config_pa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuPrincipal.this, SignalMonitorTA.class);
                intent.putExtra("senalPA", "1");
                intent.putExtra("documento", documentoEspecialista);
                intent.putExtra("nombreEspecialista", nombreEspecialista);
                intent.putExtra("nombrePaciente", nombrePaciente);
                startActivity(intent);
            }
        });

        iv_config_spo2_pulso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuPrincipal.this, SignalMonitorSpo2Pulso.class);
                intent.putExtra("senalSPO2_PULSO", "1");
                intent.putExtra("documento", documentoEspecialista);
                intent.putExtra("nombreEspecialista", nombreEspecialista);
                intent.putExtra("nombrePaciente", nombrePaciente);
                startActivity(intent);
            }
        });

        iv_config_monitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuPrincipal.this, SignalsMonitor.class);
                intent.putExtra("senalMONITOR", "1");
                intent.putExtra("documento", documentoEspecialista);
                intent.putExtra("nombreEspecialista", nombreEspecialista);
                intent.putExtra("nombrePaciente", nombrePaciente);
                startActivity(intent);
            }
        });

        //Codigo del cardview
        linearLayoutDetails1 = (ViewGroup) findViewById(R.id.linearLayoutDetails1);
        imageViewExpand1 = (ImageView) findViewById(R.id.imageViewExpand1);

        linearLayoutDetails2 = (ViewGroup) findViewById(R.id.linearLayoutDetails2);
        imageViewExpand2 = (ImageView) findViewById(R.id.imageViewExpand2);

        linearLayoutDetails3 = (ViewGroup) findViewById(R.id.linearLayoutDetails3);
        imageViewExpand3 = (ImageView) findViewById(R.id.imageViewExpand3);

        linearLayoutDetails4 = (ViewGroup) findViewById(R.id.linearLayoutDetails4);
        imageViewExpand4 = (ImageView) findViewById(R.id.imageViewExpand4);

        linearLayoutDetails5 = (ViewGroup) findViewById(R.id.linearLayoutDetails5);
        imageViewExpand5 = (ImageView) findViewById(R.id.imageViewExpand5);

        linearLayoutDetails6 = (ViewGroup) findViewById(R.id.linearLayoutDetails6);
        imageViewExpand6 = (ImageView) findViewById(R.id.imageViewExpand6);

        linearLayoutDetails7 = (ViewGroup) findViewById(R.id.linearLayoutDetails7);
        imageViewExpand7 = (ImageView) findViewById(R.id.imageViewExpand7);

        linearLayoutDetails8 = (ViewGroup) findViewById(R.id.linearLayoutDetails8);
        imageViewExpand8 = (ImageView) findViewById(R.id.imageViewExpand8);


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



        Toolbar toolbarCard3 = (Toolbar) findViewById(R.id.toolbarCard3);
        toolbarCard3.setTitle(R.string.configurations3);
        toolbarCard3.setSubtitle(R.string.subtitle3);
        toolbarCard3.inflateMenu(R.menu.menu_card);
        toolbarCard3.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
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



        Toolbar toolbarCard4 = (Toolbar) findViewById(R.id.toolbarCard4);
        toolbarCard4.setTitle(R.string.configurations4);
        toolbarCard4.setSubtitle(R.string.subtitle4);
        toolbarCard4.inflateMenu(R.menu.menu_card);
        toolbarCard4.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
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


        Toolbar toolbarCard5 = (Toolbar) findViewById(R.id.toolbarCard5);
        toolbarCard5.setTitle(R.string.configurations5);
        toolbarCard5.setSubtitle(R.string.subtitle5);
        toolbarCard5.inflateMenu(R.menu.menu_card);
        toolbarCard5.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
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


        Toolbar toolbarCard6 = (Toolbar) findViewById(R.id.toolbarCard6);
        toolbarCard6.setTitle(R.string.configurations6);
        toolbarCard6.setSubtitle(R.string.subtitle6);
        toolbarCard6.inflateMenu(R.menu.menu_card);
        toolbarCard6.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
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



        Toolbar toolbarCard7 = (Toolbar) findViewById(R.id.toolbarCard7);
        toolbarCard7.setTitle(R.string.configurations7);
        toolbarCard7.setSubtitle(R.string.subtitle7);
        toolbarCard7.inflateMenu(R.menu.menu_card);
        toolbarCard7.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
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


        Toolbar toolbarCard8 = (Toolbar) findViewById(R.id.toolbarCard8);
        toolbarCard8.setTitle(R.string.configurations8);
        toolbarCard8.setSubtitle(R.string.subtitle8);
        toolbarCard8.inflateMenu(R.menu.menu_card);
        toolbarCard8.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
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

    public void detallePacienteEspecialista(){
        AlertDialog.Builder al = new AlertDialog.Builder(MenuPrincipal.this);
        al.setCancelable(true);
        al.setIcon(R.drawable.ic_info);
        al.setTitle("Información Recibida");
        al.setMessage("Documento Especialista: "+documentoEspecialista + "\n"+
                "\nNombre Especialista: "+nombreEspecialista + "\n\n" +
                "Nombre Paciente: "+nombrePaciente);
        al.show();
    }


    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };


    public void toggleDetails1() {
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


    public void toggleDetails3(View view) {
        if (linearLayoutDetails3.getVisibility() == View.GONE) {
            ExpandAndCollapseViewUtil.expand(linearLayoutDetails3, DURATION);
            imageViewExpand3.setImageResource(R.mipmap.more);
            rotate3(-180.0f);
        } else {
            ExpandAndCollapseViewUtil.collapse(linearLayoutDetails3, DURATION);
            imageViewExpand3.setImageResource(R.mipmap.less);
            rotate3(180.0f);
        }
    }


    public void toggleDetails4(View view) {
        if (linearLayoutDetails4.getVisibility() == View.GONE) {
            ExpandAndCollapseViewUtil.expand(linearLayoutDetails4, DURATION);
            imageViewExpand4.setImageResource(R.mipmap.more);
            rotate4(-180.0f);
        } else {
            ExpandAndCollapseViewUtil.collapse(linearLayoutDetails4, DURATION);
            imageViewExpand4.setImageResource(R.mipmap.less);
            rotate4(180.0f);
        }
    }


    public void toggleDetails5(View view) {
        if (linearLayoutDetails5.getVisibility() == View.GONE) {
            ExpandAndCollapseViewUtil.expand(linearLayoutDetails5, DURATION);
            imageViewExpand5.setImageResource(R.mipmap.more);
            rotate5(-180.0f);
        } else {
            ExpandAndCollapseViewUtil.collapse(linearLayoutDetails5, DURATION);
            imageViewExpand5.setImageResource(R.mipmap.less);
            rotate5(180.0f);
        }
    }


    public void toggleDetails6(View view) {
        if (linearLayoutDetails6.getVisibility() == View.GONE) {
            ExpandAndCollapseViewUtil.expand(linearLayoutDetails6, DURATION);
            imageViewExpand6.setImageResource(R.mipmap.more);
            rotate6(-180.0f);
        } else {
            ExpandAndCollapseViewUtil.collapse(linearLayoutDetails6, DURATION);
            imageViewExpand6.setImageResource(R.mipmap.less);
            rotate6(180.0f);
        }
    }


    public void toggleDetails7(View view) {
        if (linearLayoutDetails7.getVisibility() == View.GONE) {
            ExpandAndCollapseViewUtil.expand(linearLayoutDetails7, DURATION);
            imageViewExpand7.setImageResource(R.mipmap.more);
            rotate7(-180.0f);
        } else {
            ExpandAndCollapseViewUtil.collapse(linearLayoutDetails7, DURATION);
            imageViewExpand7.setImageResource(R.mipmap.less);
            rotate7(180.0f);
        }
    }


    public void toggleDetails8(View view) {
        if (linearLayoutDetails8.getVisibility() == View.GONE) {
            ExpandAndCollapseViewUtil.expand(linearLayoutDetails8, DURATION);
            imageViewExpand8.setImageResource(R.mipmap.more);
            rotate8(-180.0f);
        } else {
            ExpandAndCollapseViewUtil.collapse(linearLayoutDetails8, DURATION);
            imageViewExpand8.setImageResource(R.mipmap.less);
            rotate8(180.0f);
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

    private void rotate3(float angle) {
        Animation animation = new RotateAnimation(0.0f, angle, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setFillAfter(true);
        animation.setDuration(DURATION);
        imageViewExpand3.startAnimation(animation);
    }

    private void rotate4(float angle) {
        Animation animation = new RotateAnimation(0.0f, angle, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setFillAfter(true);
        animation.setDuration(DURATION);
        imageViewExpand4.startAnimation(animation);
    }


    private void rotate5(float angle) {
        Animation animation = new RotateAnimation(0.0f, angle, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setFillAfter(true);
        animation.setDuration(DURATION);
        imageViewExpand5.startAnimation(animation);
    }


    private void rotate6(float angle) {
        Animation animation = new RotateAnimation(0.0f, angle, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setFillAfter(true);
        animation.setDuration(DURATION);
        imageViewExpand6.startAnimation(animation);
    }


    private void rotate7(float angle) {
        Animation animation = new RotateAnimation(0.0f, angle, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setFillAfter(true);
        animation.setDuration(DURATION);
        imageViewExpand7.startAnimation(animation);
    }


    private void rotate8(float angle) {
        Animation animation = new RotateAnimation(0.0f, angle, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setFillAfter(true);
        animation.setDuration(DURATION);
        imageViewExpand8.startAnimation(animation);
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


    private void dialog_config_notificaciones() {
        final android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(MenuPrincipal.this);
        //AlertDialog.Builder mBuilder = new AlertDialog.Builder(getApplicationContext());
        //mBuilder.setIcon(R.drawable.ic_servidor);
        //mBuilder.setTitle("<<<UTLA>>>");

        mBuilder.setCancelable(false);
        //final View mView = getLayoutInflater().inflate(R.layout.dialog_server, null);
        final View mView = getLayoutInflater().inflate(R.layout.dialog_config_notificaciones, null);

        //Aca colocar el mapeo o referencia de cada control en el Layout.
        //Button btnSave = (Button)mView.findViewById(R.id.btnSave);                                //BOTONES DEL DIALOG CONFIGURACION.
        //TextView txtclose = (TextView)mView.findViewById(R.id.txtclose);
        ImageView BtnCerrar = (ImageView) mView.findViewById(R.id.BtnCerrar);

        mBuilder.setView(mView);
        //final AlertDialog dialog = mBuilder.create();
        final android.app.AlertDialog dialog = mBuilder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //myDialog.show();
        dialog.show();

        BtnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private void dialog_config_espelialista() {
        final android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(MenuPrincipal.this);
        //AlertDialog.Builder mBuilder = new AlertDialog.Builder(getApplicationContext());
        //mBuilder.setIcon(R.drawable.ic_servidor);
        //mBuilder.setTitle("<<<UTLA>>>");

        mBuilder.setCancelable(false);
        //final View mView = getLayoutInflater().inflate(R.layout.dialog_server, null);
        final View mView = getLayoutInflater().inflate(R.layout.dialog_config_especialista, null);

        //Aca colocar el mapeo o referencia de cada control en el Layout.
        //Button btnSave = (Button)mView.findViewById(R.id.btnSave);      //BOTONES DEL DIALOG CONFIGURACION.

        //TextView txtclose = (TextView)mView.findViewById(R.id.txtclose);
        ImageView BtnCerrar = (ImageView)mView.findViewById(R.id.BtnCerrar);

        final LinearLayout ll_gr1 = (LinearLayout)mView.findViewById(R.id.ll_gr1);
        Button btnEdit = (Button)mView.findViewById(R.id.btnEdit);
        Button btnClose = (Button)mView.findViewById(R.id.btnClose);

        final LinearLayout ll_gr2 = (LinearLayout)mView.findViewById(R.id.ll_gr2);
        Button btnEdit1 = (Button)mView.findViewById(R.id.btnEdit1);
        Button btnCancelar = (Button)mView.findViewById(R.id.btnCancelar);
        Button btnClose1 = (Button)mView.findViewById(R.id.btnClose1);

        final TextView tv_doc_dr1 = (TextView)mView.findViewById(R.id.tv_doc_dr1);
        TextView tv_nombre_dr1 = (TextView)mView.findViewById(R.id.tv_nombre_dr1);

        final TextView tv_tel1 = (TextView)mView.findViewById(R.id.tv_tel1);
        final EditText et_tel1 = (EditText)mView.findViewById(R.id.et_tel1);
        final TextView tv_email1 = (TextView)mView.findViewById(R.id.tv_email1);
        final EditText et_email1 = (EditText)mView.findViewById(R.id.et_email1);

        final TextInputLayout tiTelefono = (TextInputLayout) mView.findViewById(R.id.tiTelefono) ;
        final TextInputLayout tiEmail = (TextInputLayout)mView.findViewById(R.id.tiEmail);

        //senal, documentoEspecialista, nombreEspecialista, nombrePaciente, tel_especialista, correo_especialista
        /*tv_doc_dr1.setText(documentoEspecialista);
        tv_nombre_dr1.setText(nombreEspecialista);
        tv_tel1.setText(tel_especialista);
        tv_email1.setText(correo_especialista);*/

        datos.setDocumento(documentoEspecialista);
        //Lo que se me ocurre es hacer una consulta a la base de datos para mostrar esos datos traidos desde la bd.
        if(instancia.consultaDatosActualizadoEspecialista(datos)){
            tv_doc_dr1.setText(datos.getDocumento());
            tv_nombre_dr1.setText(datos.getNombres() + " " + datos.getApellidos());
            tv_tel1.setText(datos.getTelefono());
            tv_email1.setText(datos.getEmail());
        }else{
            Toast.makeText(this, "No se encontrarón resultados para la busqueda especificada", Toast.LENGTH_SHORT).show();
        }

        //tv_tel1.setText(et_tel1.getText().toString());
        //tv_email1.setText(et_email1.getText().toString());

        mBuilder.setView(mView);
        //final AlertDialog dialog = mBuilder.create();
        final android.app.AlertDialog dialog = mBuilder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //myDialog.show();
        dialog.show();

        BtnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnClose1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_gr1.setVisibility(View.GONE);
                ll_gr2.setVisibility(View.VISIBLE);

                //tv_tel1, et_tel1, tv_email1, et_email1
                tv_tel1.setVisibility(View.GONE);
                tv_email1.setVisibility(View.GONE);

                et_tel1.setVisibility(View.VISIBLE);
                et_email1.setVisibility(View.VISIBLE);
                tiEmail.setVisibility(View.VISIBLE);
                tiTelefono.setVisibility(View.VISIBLE);

                //Aca me hace falta analisis para que funcione bien la lógica que deseo.
                et_tel1.setText(tv_tel1.getText().toString());
                et_email1.setText(tv_email1.getText().toString());

            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_gr1.setVisibility(View.VISIBLE);
                ll_gr2.setVisibility(View.GONE);

                //tv_tel1, et_tel1, tv_email1, et_email1
                tv_tel1.setVisibility(View.VISIBLE);
                tv_email1.setVisibility(View.VISIBLE);
                et_tel1.setVisibility(View.GONE);
                et_email1.setVisibility(View.GONE);
                tiEmail.setVisibility(View.GONE);
                tiTelefono.setVisibility(View.GONE);

            }
        });

        btnEdit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //tv_tel1, et_tel1, tv_email1, et_email1

                if (Patterns.PHONE.matcher(et_tel1.getText().toString()).matches()==false){
                    et_tel1.setText(null);
                    et_tel1.requestFocus();
                    tiTelefono.setError("Teléfono invalido.");
                    estado_tel = false;

                }else{
                    tiTelefono.setError(null);
                    estado_tel = true;

                }

                if(estado_tel) {
                    if (Patterns.EMAIL_ADDRESS.matcher(et_email1.getText().toString()).matches() == false) {
                        //mEmail.setBackgroundColor(Color.GREEN);
                        et_email1.setText(null);
                        et_email1.requestFocus();
                        tiEmail.setError("Correo invalido.");
                        estado_ema = false;

                    } else {
                        tiEmail.setError(null);
                        estado_ema = true;
                    }

                }else{

                }

                if(estado_tel && estado_ema){
                    ll_gr1.setVisibility(View.VISIBLE);
                    ll_gr2.setVisibility(View.GONE);

                    tv_tel1.setVisibility(View.VISIBLE);
                    tv_email1.setVisibility(View.VISIBLE);
                    et_tel1.setVisibility(View.GONE);
                    et_email1.setVisibility(View.GONE);
                    tiEmail.setVisibility(View.GONE);
                    tiTelefono.setVisibility(View.GONE);

                    String tele = et_tel1.getText().toString();
                    String correo = et_email1.getText().toString();

                    if(!tele.isEmpty() && !correo.isEmpty()){
                        //Toast.makeText(getApplicationContext(), "Vamos caminando con el señor...", Toast.LENGTH_SHORT).show();
                        //datos.setDocumento(documentoEspecialista);                 //tv_tel1
                        datos.setDocumento(tv_doc_dr1.getText().toString());         //
                        datos.setTelefono(et_tel1.getText().toString());
                        datos.setEmail(et_email1.getText().toString());

                        if (instancia.actualizoEspecialista(datos)){
                            tv_tel1.setText(et_tel1.getText().toString());
                            tv_email1.setText(et_email1.getText().toString());

                            Toast.makeText(MenuPrincipal.this, "Datos Actualizados Correctamente!!!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MenuPrincipal.this, "Se encontrarón problemas. No se pudo actualizar su información.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }
    //Fin de procedimiento aca estoy de momento.


    private void config_server() {
        final android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(MenuPrincipal.this);
        //AlertDialog.Builder mBuilder = new AlertDialog.Builder(getApplicationContext());
        //mBuilder.setIcon(R.drawable.ic_servidor);
        //mBuilder.setTitle("<<<UTLA>>>");

        mBuilder.setCancelable(false);
        //final View mView = getLayoutInflater().inflate(R.layout.dialog_server, null);
        final View mView = getLayoutInflater().inflate(R.layout.alert_server, null);

        //controles donde muestro informacion del archivo credenciales.xml creado con
        //SharedPreferences.
        final Spinner sprotocolo = (Spinner)mView.findViewById(R.id.sprotocolo);
        final TextView tv_encabezado = (TextView)mView.findViewById(R.id.tv_encabezado);
        final TextView tv_encabezado1 = (TextView)mView.findViewById(R.id.tv_encabezado1);  //et_portal
        final TextView tv_servidor = (TextView)mView.findViewById(R.id.tv_servidor);
        final TextView tv_directorio = (TextView)mView.findViewById(R.id.tv_directorio);
        final TextView tv_directorioPortal = (TextView)mView.findViewById(R.id.tv_directorioPortal);
        final TextView tv_fecha = (TextView)mView.findViewById(R.id.tv_fecha);
        final TextView tv_hora = (TextView)mView.findViewById(R.id.tv_hora);

        final EditText dominio = (EditText) mView.findViewById(R.id.et_dominio);
        final EditText directorio = (EditText) mView.findViewById(R.id.et_directorio);
        final EditText portal = (EditText) mView.findViewById(R.id.et_portal); //et_portal
        final TextInputLayout ti_dominio=(TextInputLayout)mView.findViewById(R.id.ti_dominio);

        Button btnSave = (Button)mView.findViewById(R.id.btnSave);      //BOTONES DEL DIALOG CONFIGURACION.
        Button btnCancel = (Button)mView.findViewById(R.id.btnCancel);

        //TextView txtclose = (TextView)mView.findViewById(R.id.txtclose);
        ImageView BtnCerrar = (ImageView)mView.findViewById(R.id.BtnCerrar);

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

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //myDialog.show();
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

                //if(directorio.getText().toString().length()==0 || !directorio.getText().equals("Sin configurar.")){
                /*if(directorio.getText().equals("Sin configurar.")){
                    directorio.setError("Campo opcional. Dejar en Blanco");
                    directorio.setText(null);
                    directorio.requestFocus();
                    estado_dir_api = false;
                }else{
                    estado_dir_api = true;
                }
                //portal.getText().toString().length()==0 &&
                //if(portal.getText().equals("Sin configurar.") && portal.getText().toString().length()==0){
                if(portal.getText().equals("Sin configurar.")){
                    portal.setError("Campo Obligadorio");
                    portal.setText(null);
                    portal.requestFocus();
                    estado_dir_portal = false;
                }else{
                    estado_dir_portal = true;
                }*/


                if(estado_nombre){
                //if(estado_nombre && estado_dir_api && estado_dir_portal){
                    SharedPreferences preferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
                    //OBTENIENDO LA FECHA Y HORA ACTUAL DEL SISTEMA.
                    DateFormat formatodate= new SimpleDateFormat("yyyy/MM/dd");
                    String date= formatodate.format(new Date());
                    DateFormat formatotime= new SimpleDateFormat("HH:mm:ss a");
                    String time= formatotime.format(new Date());

                    String servidor = dominio.getText().toString();

                    String folderSevice="";
                    if(directorio.getText().toString().equals("Sin configurar.")){   //Esta es la forma correcta de comparar el string
                        //Toast.makeText(MenuPrincipal.this, "o.k", Toast.LENGTH_SHORT).show();
                        folderSevice = "";
                    }else{
                        //Toast.makeText(MenuPrincipal.this, "not", Toast.LENGTH_SHORT).show();
                        folderSevice = directorio.getText().toString();
                    }
                    //String folder = directorio.getText().toString();


                    String folderPortal = "";
                    if(portal.getText().toString().equals("Sin configurar.")){   //Esta es la forma correcta de comparar el string
                        folderPortal = "";
                    }else{
                        folderPortal = portal.getText().toString();
                    }
                    //String folder1 = portal.getText().toString();

                    SharedPreferences.Editor editor = preferences.edit();
                    //editor.putString("servidor", sprotocolo.getSelectedItem().toString() + servidor + "/");
                    editor.putString("servidor", sprotocolo.getSelectedItem().toString() + servidor);
                    //editor.putString("folder", folder);
                    editor.putString("folder", folderSevice);
                    editor.putString("portal", folderPortal);
                    editor.putString("fecha", date);
                    editor.putString("hora", time);
                    editor.commit();

                    tv_encabezado.setVisibility(mView.VISIBLE);
                    //tv_encabezado.setText("Server API-Web Service: "+ servidor +"/" + folderSevice);
                    tv_encabezado.setText("Server API-Web Service: "+ servidor + folderSevice);

                    tv_encabezado1.setVisibility(mView.VISIBLE);
                    tv_encabezado1.setText("Server Portal Web: "+ servidor + folderPortal);
                    //tv_encabezado1.setText("Server Portal Web: "+ servidor +"/" + folder1);

                    tv_servidor.setVisibility(mView.VISIBLE);
                    tv_directorio.setVisibility(mView.VISIBLE);
                    tv_fecha.setVisibility(mView.VISIBLE);
                    tv_hora.setVisibility(mView.VISIBLE);

                    tv_servidor.setText("Server: " + servidor);
                    tv_directorio.setText("Directorios: " + folderSevice + ", " + folderPortal);
                    //tv_directorio.setText("Directorios: " +folder+", "+folder1);
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


        BtnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button_green_round, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id==R.id.menu_lateral){
            //Acciones a realizar
            Toast.makeText(this, "Clic en opción llamada al menú lateral", Toast.LENGTH_SHORT).show();

            return true;
        }else if(id==R.id.menu_portal) {
            //Intent intent = new Intent(this, MainActivity.class);

            /*Intent intent = new Intent(this, SignalsMonitor.class);
            startActivity(intent);*/
            GoPortal();
            
            return true;
        }else if(id==R.id.menu_detallesPacienteEspecialista){
            detallePacienteEspecialista();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    private String conf_Server() {
        //Buscando datos en archivo credenciales.xml
        SharedPreferences preferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        String server = preferences.getString("servidor", "Sin configurar.");
        return server;
    }

    private String conf_Server1() {
        //Buscando datos en archivo credenciales.xml
        SharedPreferences preferences = getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        String server = preferences.getString("servidor", "Sin configurar aun.");
        String folder = preferences.getString("folder", "Sin configurar aun.");
        return server + folder;
    }

    private void mensaje1() {
        Toast.makeText(this, "Opción deshabilitada. \nServidor Destino no Configurado Aún.\nConfigurar para habilitar opción.", Toast.LENGTH_SHORT).show();
    }

    public void GoPortal() {
        String comprobacion = conf_Server();
        if (comprobacion.equals("Sin configurar.")) {
            mensaje1();
        } else {
            Intent i = new Intent(getApplicationContext(), VisorWeb.class);
            //i.putExtra("usu", mEmail.getText().toString());
            startActivity(i);
            finish();
        }
    }


}


