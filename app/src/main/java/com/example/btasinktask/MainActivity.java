package com.example.btasinktask;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Set;

public class MainActivity extends AppCompatActivity implements MiAsyncTask.MiCallback{

    //private static final String TAG = "InicioActivity";

    //private static final int REQUEST_ENABLE_BT = 1;
    //private static final String NOMBRE_DISPOSITIVO_BT = "HC-05";//Nombre de neustro dispositivo bluetooth.

    private TextView tvTemperatura;
    private TextView tvInformacion;
    private TextView tvClic, texto_data;

    //Instancia de la clase MiAsyncTask.
    private MiAsyncTask tareaAsincrona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Obtenemos las referencias a los dos text views que usaremos para "pintar" la temperatura*/
        tvTemperatura = (TextView) findViewById(R.id.texto_temp);           //Mostrará la temperatura
        tvInformacion = (TextView) findViewById(R.id.textView_estado_BT);   //Mostrará la hora a la que fue registrada.
        tvClic = (TextView) findViewById(R.id.tvClic);
        texto_data = (TextView)findViewById(R.id.texto_data);

        tvClic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "EN MARCHA...", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onResume() {
        /* El metodo on resume es el adecuado para inicialzar todos aquellos procesos que actualicen la interfaz de usuario
        Por lo tanto invocamos aqui al método que activa el BT y crea la tarea asincrona que recupera los datos*/
        super.onResume();
        descubrirDispositivosBT();
    }

    private void descubrirDispositivosBT() {
        /*
        Este método comprueba si nuestro dispositivo dispone de conectividad bluetooh.
        En caso afirmativo, si estuviera desctivada, intenta activarla.
        En caso negativo presenta un mensaje al usuario y sale de la aplicación.
        */
        //Comprobamos que el dispositivo tiene adaptador bluetooth
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        tvInformacion.setText("Comprobando bluetooth");

        if (mBluetoothAdapter != null) {
            //El dispositivo tiene adapatador BT. Ahora comprobamos que bt esta activado.
            if (mBluetoothAdapter.isEnabled()) {
                //Esta activado. Obtenemos la lista de dispositivos BT emparejados con nuestro dispositivo android.

                //tvInformacion.setText("Obteniendo dispositivos emparejados, espere...");
                tvInformacion.setText("Search...Please wait");
                Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
                //Si hay dispositivos emparejados
                if (pairedDevices.size() > 0) {
                /*
                Recorremos los dispositivos emparejados hasta encontrar el
                adaptador BT del arduino, en este caso se llama HC-06
                */
                    BluetoothDevice arduino = null;

                    for (BluetoothDevice device : pairedDevices) {
                        //if (device.getName().equalsIgnoreCase(NOMBRE_DISPOSITIVO_BT)) {
                        if (device.getName().equalsIgnoreCase(Config.NOMBRE_DISPOSITIVO_BT)) {
                            arduino = device;
                        }
                    }

                    if (arduino != null) {
                        //tareaAsincrona = new MiAsyncTask(this);
                        tareaAsincrona = new MiAsyncTask(this, arduino);
                        tareaAsincrona.execute(arduino);
                    } else {
                        //No hemos encontrado nuestro dispositivo BT, es necesario emparejarlo antes de poder usarlo.
                        //No hay ningun dispositivo emparejado. Salimos de la app.
                        Toast.makeText(this, "No hay dispositivos emparejados, por favor, empareje el arduino", Toast.LENGTH_LONG).show();
                        finish();
                    }
                } else {
                    //No hay ningun dispositivo emparejado. Salimos de la app.
                    Toast.makeText(this, "No hay dispositivos emparejados, por favor, empareje el arduino", Toast.LENGTH_LONG).show();
                    finish();
                }
            } else {
                muestraDialogoConfirmacionActivacion();
            }
        } else {
            // El dispositivo no soporta bluetooth. Mensaje al usuario y salimos de la app
            Toast.makeText(this, "El dispositivo no soporta comunicación por Bluetooth", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStop() {
    /*Cuando la actividad es destruida, se ejecuta este método.
    Es el lugar adecuado para terminar todos aquellos procesos que se ejecutan en segundo plano, como es el caso de
    nuestra tarea asíncrona que actualiza la interfaz de usuario.
    */
        super.onStop();
        if (tareaAsincrona != null) {
            tareaAsincrona.cancel(true);
        }
    }

    /*Los métodos onTaskCompleted, onCancelled, onTemperaturaUpdate, son nuestros "callback".
    Java es un lenguaje en el que no se puede pasar una función como argumento. De tal manera, que no podemos
    pasarle a la tarea asincrona la función que tendria que ejecutar para actualizar la interfaz de usuario.
    Esto se soluciona usando el interfaz "MiCallback". Ese interfaz obliga a declarar estos tres métodos en la clase que lo implemeta, en este caso, esta
    actividad. De tal manera que podemos pasar como parametro esta clase a la tarea asincrona, y la tarea asincrona podrá invocar a estos métodos cuando
    considere necesario.
    */

    @Override
    public void onTaskCompleted() {

    }

    @Override
    public void onCancelled() {

    }

    @Override
    public void onDatosBiomedicos(Dto_variables p) {
        //tvTemperatura.setText(p.getSaturacion_parcial_oxigeno_SPO2());
        tvTemperatura.setText(p.getTemperatura_corporal());
        tvInformacion.setText(p.getInformacion());
        texto_data.setText(p.getDataStream());
    }

    private void muestraDialogoConfirmacionActivacion() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Activar Bluetooth")
                .setMessage("BT esta desactivado. ¿Desea activarlo?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    //Intentamos activarlo con el siguiente intent.
                        tvInformacion.setText("Activando BT");
                        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBtIntent, Config.REQUEST_ENABLE_BT);
                        //startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                    }

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    //Salimos de la app
                        finish();
                    }
                })
                .show();
    }

}
