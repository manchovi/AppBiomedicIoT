package com.example.btasinktask;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class config_sms_social_email {

    AlertDialog.Builder dialogo, dialogo1;
    private ProgressDialog pd;
    AlertDialog.Builder dialog;
    AlertDialog.Builder dialogo2;

    Session session = null;           //variable utilizada para envio de correo automatico.

    private String conf_Server(Context context) {
        //Buscando datos en archivo credenciales.xml
        SharedPreferences preferences = context.getSharedPreferences("Credenciales", Context.MODE_PRIVATE);
        String server = preferences.getString("servidor", "Sin configurar.");
        return server;
    }


    private String infoConfDestinatarioTel2(Context context){
        SharedPreferences preferences = context.getSharedPreferences("Destinatarios", Context.MODE_PRIVATE);
        String t2 = preferences.getString("telefono2","Sin configurar.");
        return t2;
    }

    private String infoConfDestinatarioEmail1(Context context){
        SharedPreferences preferences = context.getSharedPreferences("Destinatarios", Context.MODE_PRIVATE);
        String e1 = preferences.getString("email1","");
        return e1;
    }


    public void EnviarSMS(final Context context) {
        pd = new ProgressDialog(context);
        pd.setMessage("Procesando, por favor espere...");
        pd.show();

        try {
            SmsManager sms = SmsManager.getDefault();
            //sms.sendTextMessage(numTel, null, datosCompletos, null,null);  //FUNCION LIMITADO A MENOS CARACTERES POR SMS
            ArrayList msgTexts = sms.divideMessage("datosCompletos");
            sms.sendMultipartTextMessage(infoConfDestinatarioTel2(context), null, msgTexts, null, null);
            //Toast.makeText(getApplicationContext(), "Mensaje Enviado.", Toast.LENGTH_LONG).show();
            Toast toast = Toast.makeText(context, "MENSAJE ENVIADO A MÓVIL: " + infoConfDestinatarioTel2(context), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } catch (Exception e) {
            Toast.makeText(context, "Mensaje no enviado, datos incorrectos." + e.getMessage().toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void mensaje1(Context context) {
        Toast.makeText(context, "No se ha configurado.", Toast.LENGTH_SHORT).show();
    }




    //Funciones para envio de correo electrónico con datos preconfigurados.
    public void email_automatico(final Context context) {
        String comprobacion = conf_Server(context);
        if (comprobacion.equals("Sin configurar.")) {
            mensaje1(context);
        } else {
            dialogo2 = new AlertDialog.Builder(context);
            dialogo2.setIcon(R.drawable.ic_email);
            //dialog.setTitle("COCESNA.");
            dialogo2.setTitle("Message to E-mail");
            dialogo2.setMessage("¿Realmente desea enviar el email?\n\n" +
                    "Recuerde: La información se enviará a la dirección e-mail que se ha especificado en la opción configuración.");
            dialogo2.setCancelable(false);
            dialogo2.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo2, int id) {
                    //aca pondre la funcion para envio de email automatico.
                    configuracion();
                    //capturar_info_biomedia();
                }
            });
            dialogo2.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogo2, int id) {
                    Toast toast = Toast.makeText(context, "CANCELADO.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            });
            dialogo2.show();
        }
    }     //fin de funcion email_automatico.


    public void configuracion() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        session = Session.getDefaultInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                //return new PasswordAuthentication("cocesna1718@gmail.com", "cocesna_2018_");
                return new PasswordAuthentication(Config.EMAIL, Config.PASSWORD);
                //return new PasswordAuthentication("testfrom354@gmail.com", "p1234p1234");
            }
        });
    }  //Fin función configuración.


    private void capturar_info_biomedia(Context context, String asunto, String tc, String fr, String ta, String spo2, String fc, String especialista, String paciente) {
        sendEmail(context, asunto, tc, fr, ta, spo2, fc, especialista, paciente);
    }


    private void sendEmail(Context context, String asunto, String temp_corporal, String frec_respiratoria, String tens_arterial, String spo2, String frec_cardiaca, String doctor, String paciente) {

        //OBTENIENDO LA FECHA Y HORA ACTUAL DEL SISTEMA.
        DateFormat formatodate = new SimpleDateFormat("yyyy/MM/dd");
        String date = formatodate.format(new Date());

        DateFormat formatotime = new SimpleDateFormat("HH:mm:ss a");
        String time = formatotime.format(new Date());

        String message = "aca formaré todo el cuerpo del mensaje";

    }





}
