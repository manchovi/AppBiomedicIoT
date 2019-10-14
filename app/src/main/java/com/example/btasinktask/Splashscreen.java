package com.example.btasinktask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.Task;

public class Splashscreen extends AppCompatActivity {

    //private GifImageView gifImageView;
    private ProgressBar progressBar;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        //Función para evitar la rotación de la pantalla del CELULAR.
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //y esto para pantalla completa (oculta incluso la barra de estado)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        handler = new Handler();
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setMax(5);
        progressBar.setProgress(0);

        startProgress();

        /*
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Splashscreen.this.startActivity(new Intent(Splashscreen.this, MenuPrincipal.class));
                finish();
            }
        },5000);
        */

    }

    public void startProgress() {
        new Thread(new Task()).start();
    }


    class Task implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i <= 5; i++) {
                final int value = i;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setProgress(value);
                        if(value == 5) {
                            Splashscreen.this.startActivity(new Intent(Splashscreen.this, Login.class));
                            finish();
                        }
                    }
                });
            }
        }
    }




}
