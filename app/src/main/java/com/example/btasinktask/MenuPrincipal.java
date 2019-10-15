package com.example.btasinktask;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class MenuPrincipal extends AppCompatActivity {
    CarouselView carouselView;
    AlertDialog.Builder dialogo;

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



    }


    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };



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


}


