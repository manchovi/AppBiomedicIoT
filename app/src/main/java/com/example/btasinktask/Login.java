package com.example.btasinktask;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {

    Dialog myDialog;

    EditText etEmail, etClave;
    TextInputLayout tiEmail, tiClave;
    Button btnLogin;

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
                            finishAffinity();
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

        myDialog = new Dialog(this);


        handler.postDelayed(runnable, 0); //2000 is the timeout for the splash


    }
}
