package com.example.btasinktask;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class MiAsyncTask extends AsyncTask<BluetoothDevice, Dto_variables, Void> {
    //public class MiAsyncTask extends AsyncTask<BluetoothDevice, Temperatura, Void> {

    private static final String TAG = "MiAsyncTask";

    //Identificador unico universal del puerto bluetooth en android (UUID)
    private static final String UUID_SERIAL_PORT_PROFILE = "00001101-0000-1000-8000-00805F9B34FB";
    //private Temperatura temperatura = new Temperatura();
    private Dto_variables datos = new Dto_variables();

    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    private boolean recibiendo = false;


    private BluetoothSocket mSocket = null;
    private BufferedReader mBufferedReader = null;
    private InputStreamReader aReader = null;
    // btInputStream = btSocket.getInputStream();
    // btOutputStream = btSocket.getOutputStream();
    private InputStream aStream = null;
    private OutputStream oStream = null;

    private int contadorConexiones = 0;

    private MiCallback callback;
    public interface MiCallback {

        void onTaskCompleted();

        void onCancelled();

        //void onTemperaturaUpdate(Temperatura p);
        void onDatosBiomedicos(Dto_variables p);

    }



   /* BlueToothThread(BluetoothSocket btSocket) {

        try {
            btInputStream = btSocket.getInputStream();
            btOutputStream = btSocket.getOutputStream();
        } catch (IOException iOe) {
            btInputStream = null;
        }
    }*/



   /* public MiAsyncTask(BluetoothDevice device) throws IOException {
        mSocket = device.createRfcommSocketToServiceRecord(getSerialPortUUID());
        mSocket.connect();

        aStream = mSocket.getInputStream();
        oStream = mSocket.getOutputStream();
    }*/



   /* public MiAsyncTask(MiCallback CALLBACK) {
        callback = CALLBACK;
    }*/

    public MiAsyncTask(MiCallback CALLBACK, BluetoothDevice device) {

        try{
            mSocket = device.createRfcommSocketToServiceRecord(getSerialPortUUID());
            mSocket.connect();

            aStream = mSocket.getInputStream();
            oStream = mSocket.getOutputStream();

        } catch (IOException iOe) {
            aStream = null;
        }

        callback = CALLBACK;

    }



    private void demora(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Void doInBackground(BluetoothDevice... devices) {

        final BluetoothDevice device = devices[0];


        //Nuevo
        /*for (int i=1;i<=5;i++){
            demora();*/

            //publishProgress(i);

            //if(isCancelled()){
            //    break;
            //}
        /*}*/
        //Fin nuevo




        //Realizamos la conexion al disp.blueetoth. A veces la conexion falla aunque el dispositivo
        //este presente. Asi que si falla, y la tarea no ha sido cancelada, lo reintentamos.
        while (!isCancelled()) {
            if (!recibiendo) {
                recibiendo = conectayRecibeBT(device);
            }
        }

        cierra();
        return null;
    }


    private boolean conectayRecibeBT(BluetoothDevice device) {
        //Abrimos la conexión con el dispositivo.
        boolean ok = true;

        try {
            contadorConexiones++;

            /*mSocket = device.createRfcommSocketToServiceRecord(getSerialPortUUID());
            mSocket.connect();
            aStream = mSocket.getInputStream();*/
            //oStream = mSocket.getOutputStream();

            aReader = new InputStreamReader(aStream);
            mBufferedReader = new BufferedReader(aReader);

            datos.setInformacion("Sin datos...");
            publishProgress(datos);
            /*Mientras no se cancele la tarea asincrona (cuando se destruya la actividad)
            se interroga al canal de comunicación por la temperatura*/

            while (!isCancelled()) {
                try {

                    String aString = mBufferedReader.readLine();
                    if ((aString != null) && (!aString.isEmpty())) {
                    //Instante de tiempo en que recuperamos un dato.
                        datos.setInformacion(sdf.format(new Date()));

                            //Recibimos la información en una cadena de la forma NombreDispositivo,XX
                            //donde XX es la temperatura.
                        try {
                            String s[] = aString.split(",");
                            if(s[6].equals("~")) {
                                datos.setSaturacion_parcial_oxigeno_SPO2(s[0]);
                                datos.setFrecuencia_cardiaca_o_pulso(s[1]);
                                datos.setPresion_arterial(s[2]);
                                datos.setFrecuencia_respiratoria(s[3]);
                                datos.setTemperatura_corporal(s[4]);
                                datos.setAlarma(s[5]);
                                datos.setDataStream(s[0]+","+s[1]+","+s[2]+","+s[3]+","+s[4]+","+s[5]);
                                publishProgress(datos);
                            }else{
                                datos.setInformacion("Error. Problemas de recepción de datos");
                            }
                        } catch (Exception e) {
                            //Si falla el formateo de los datos, no hacemos nada. Mostramos la excepción en la consola para
                            //observar el error.
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    //aStream = null;     //Tengo que probar esta línea de código haber que función cumple.........................................................
                }

            }

            //Una vez la tarea se ha cancelado, cerramos la conexión con el dispositivo bluetooth.
            datos.setInformacion("Cerrando conexion BT");

        //} catch (IOException e) {
        } catch (Exception e) {
            ok = false;
            e.printStackTrace();
            datos.setInformacion("Error conectando con dispositivo bt, reintento " + contadorConexiones + "... Si este error se repite, reinicie el arduino.");
            publishProgress(datos);
            cierra();

        }
        return ok;
    }


    //Función nuevo 2020.
    /* Call this from the main activity to send data to the remote device */
    void SendData(String message) {
        byte[] msgBuffer = message.getBytes();
        try {
            oStream.write(msgBuffer);
        } catch (IOException e) {
            Log.d(" ", "...Error data send: " + e.getMessage() + "...");
            //Toast.makeText(this, "error.", Toast.LENGTH_SHORT).show();
        }
    }


    private void cierra() {
        close(mBufferedReader);
        close(aReader);
        close(aStream);
        close(mSocket);
    }

    private UUID getSerialPortUUID() {
        return UUID.fromString(UUID_SERIAL_PORT_PROFILE);
    }

    private void close(Closeable aConnectedObject) {
        if (aConnectedObject == null) return;
        try {
            aConnectedObject.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        aConnectedObject = null;
    }

    @Override
    protected void onProgressUpdate(Dto_variables... values) {
        super.onProgressUpdate(values);
        callback.onDatosBiomedicos(values[0]);
    }

    @Override
    protected void onCancelled() {
        callback.onCancelled();

    }

}
