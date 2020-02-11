package com.example.btasinktask;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DecimalFormat;
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

    String EmailOrigen_Hospital = Config.EMAIL;
    String asunto_Email = Config.ASUNTO;

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


    public void enviarSMS(final Context context) {
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



    public void sendInfo_SMS_TC(Context context, String temperatura, String telefonoEspecialista, String nombreEspecialista, String nombrePaciente){
        DecimalFormat df = new DecimalFormat("#.00");
        String datosCompletos =
                        "*************************************************************\n" +
                        "¡NOTIFICACIÓN TEMPERATURA CORPORAL!\n\n" +
                        "*************************************************************\n" +
                        "*Temperatura Corporal: " + temperatura + " °C ~ " + df.format(((((Double.parseDouble(String.valueOf(temperatura)))*1.8)+32.0))) + " °F.\n" +
                        "\nNombre del Paciente: " + nombrePaciente +"\n" +
                        "\nNombre del Especialísta: " + nombreEspecialista +"\n" +
                        "---------------------------------------------------------------------" + "\n" +
                        //"\t\tMensaje Generado: " + date + " ~ " + time + "\n" +
                        "\t\tSMS Generado: " + fecha() + " ~ " + hora() + "\n" +
                        "---------------------------------------------------------------------" + "\n" +
                        "Copyright(c) HOSPITAL 2019~2020. " +
                        "\nAll rights reserved.";

        sendSMS(context, datosCompletos, telefonoEspecialista);

    }


    //public void sendInfo_SMS_FR(Context context, String frec_respiratoria, String telefono){
    public void sendInfo_SMS_FR(Context context, String frec_respiratoria, String telefonoEspecialista, String nombreEspecialista, String nombrePaciente){

        String datosCompletos =
        "*************************************************************\n" +
        "¡NOTIFICACIÓN FRECUENCIA RESPIRATORIA!\n" +
        "*************************************************************\n\n" +
        "*Frecuencia Respiratoria: " + frec_respiratoria +"\n\n" +
        "Nombre del Paciente: " + nombrePaciente +"\n" +
        "Nombre del Especialísta: " + nombreEspecialista +"\n\n" +
        "---------------------------------------------------------------------" + "\n" +
        //"\t\tMensaje Generado: " + date + " ~ " + time + "\n" +
        "\t\tSMS Generado: " + fecha() + " ~ " + hora() + "\n" +
        "---------------------------------------------------------------------" + "\n" +
        "Copyright(c) HOSPITAL 2019~2020. " +
        "\nAll rights reserved.";

        sendSMS(context, datosCompletos, telefonoEspecialista);
    }



    public void sendInfo_SMS_TA(Context context, String diastolic, String systolic, String pulsemin, String telefonoEspecialista, String nombreEspecialista, String nombrePaciente){


        String datosCompletos =
        "*************************************************************\n" +
        "¡NOTIFICACIÓN TENSIÓN ARTERIAL!\n\n" +
        "*************************************************************\n" +
        "*Diastolic Pressure: " + diastolic +" mmHg.\n" +
        "*Systolic Pressure: " + systolic +" mmHg.\n" +
        "*Heart Rate: " + pulsemin +" bmp\n\n" +
        "\nNombre del Paciente: " + nombrePaciente +"\n" +
        "\nNombre del Especialísta: " + nombreEspecialista +"\n" +
        "---------------------------------------------------------------------" + "\n" +
        //"\t\tMensaje Generado: " + date + " ~ " + time + "\n" +
        "\t\tSMS Generado: " + fecha() + " ~ " + hora() + "\n" +
        "---------------------------------------------------------------------" + "\n" +
        "Copyright(c) HOSPITAL 2019~2020. " +
        "\nAll rights reserved.";

        sendSMS(context, datosCompletos, telefonoEspecialista);
    }


    public void sendInfo_SMS_OXIMETRIA(Context context, String spo2, String frecuencia_cardiaca, String telefonoEspecialista, String nombreEspecialista, String nombrePaciente){

        String datosCompletos =
        "*************************************************************\n" +
        "¡NOTIFICACIÓN OXIMETRÍA!\n" +
        "*************************************************************\n" +
        "*Saturación Parcial del Oxígeno (SpO2): " + spo2 +" %\n" +
        "*Frecuencia Cardíaca: " + frecuencia_cardiaca +" PRbpm\n\n" +
        "Nombre del Paciente: " + nombrePaciente +"\n" +
        "Nombre del Especialísta: " + nombreEspecialista +"\n\n" +
        "---------------------------------------------------------------------" + "\n" +
        //"\t\tMensaje Generado: " + date + " ~ " + time + "\n" +
        "\t\tSMS Generado: " + fecha() + " ~ " + hora() + "\n" +
        "---------------------------------------------------------------------" + "\n" +
        "Copyright(c) HOSPITAL 2019~2020. " +
        "\nAll rights reserved.";

        sendSMS(context, datosCompletos, telefonoEspecialista);
    }



    public void sendSMS(Context context, String datosCompletos, String numeroEspecialista){
        try {
            SmsManager sms = SmsManager.getDefault();
            //sms.sendTextMessage(numTel, null, datosCompletos, null,null);  //FUNCION LIMITADO A MENOS CARACTERES POR SMS
            ArrayList msgTexts = sms.divideMessage(datosCompletos);
            //sms.sendMultipartTextMessage(infoConfDestinatarioTel2(context), null, msgTexts, null, null);
            sms.sendMultipartTextMessage(numeroEspecialista, null, msgTexts, null, null);

            //Toast.makeText(getApplicationContext(), "Mensaje Enviado.", Toast.LENGTH_LONG).show();
            //Toast toast = Toast.makeText(context, "MENSAJE ENVIADO A MÓVIL: " + infoConfDestinatarioTel2(context), Toast.LENGTH_LONG);
            Toast toast = Toast.makeText(context, "MENSAJE ENVIADO A ESPECIALÍSTA: " + numeroEspecialista, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        } catch (Exception e) {
            Toast.makeText(context, "Mensaje no enviado, datos incorrectos." + e.getMessage().toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


   //FIN DE FUNCIONES DE ENVIO DE SMS A ESPECIALÍSTAS.










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
                    //configuracion();
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


    public void sesion_EmailOrigenHospital() {
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


    public String fecha(){
        DateFormat formatodate = new SimpleDateFormat("yyyy/MM/dd");
        String date = formatodate.format(new Date());
        return date;
    }

    public String hora(){
        DateFormat formatotime = new SimpleDateFormat("HH:mm:ss a");
        String time = formatotime.format(new Date());
        return time;
    }

    /********************************************************************/
    /****************Sección de Envio de Correos*************************/
    /********************************************************************/

    //Función para enviar notificación de variable biométrica Temperatura Corporal.
    public void sendInfo_Email_TC(Context context, String email, String subject, String temperatura, String nombreEspecialista, String nombrePaciente){

        //sesion_EmailOrigenHospital();             //Evaluaré en esta App si es necesario llamar esta función para enviar el correo.
                                                  //Esto debido a que he observado que en la clase java "SendMail" ya viene por defecto esta configuración.

        /*Para estas dos variables estoy pensando hacer un inner join o simplemente mando los valores tomandolos de las variables de inicio de sesión*/
        /*La otra opción que pienso, es que al iniciar sesión setear un archivo xml con sharedpreferenced con los datos que necesito y luego solo llamarlos
        * donde se necesiten desde las diferentes actividades de la aplicación. Ya veré cual a la hora de las horas.*/
        //Esperando que todo salga de marivilla con el next code...

        DecimalFormat df = new DecimalFormat("#.00");

        String message =
                "*************************************************************\n" +
                "¡NOTIFICACIÓN TEMPERATURA CORPORAL!\n" +
                "*************************************************************\n\n" +
                "* Temperatura Corporal: " + temperatura  + " °C ~ " + df.format(((((Double.parseDouble(String.valueOf(temperatura)))*1.8)+32.0))) + " °F.\n\n" +
                "* Nombre del Paciente: " + nombrePaciente +"\n" +
                "* Nombre del Especialísta: " + nombreEspecialista +"\n\n" +
                "---------------------------------------------------------------------" + "\n" +
                //"\t\tMensaje Generado: " + date + " ~ " + time + "\n" +
                "\t\tE-mail Generado: " + fecha() + " ~ " + hora() + "\n" +
                "---------------------------------------------------------------------" + "\n" +
                "Copyright(c) HOSPITAL 2019~2020. " +
                "\nAll rights reserved.";

        //String message = temperatura;          //Dato a enviar.

        //String message = "aca formaré todo el cuerpo del mensaje";
        //sendEmail(infoConfDestinatarioEmail1(), asunto, temperatura, humedad, l1, l2, l3, l4, l5, l6, l7, l8, fecha, hora);
        //sendEmail(infoConfDestinatarioEmail1(), asunto, temperatura);

        //Toast.makeText(context, "Correoo:"+email, Toast.LENGTH_SHORT).show();

        //Creating SendMail object
        SendMail sm = new SendMail(context, email, subject, message);
        //Executing sendmail to send email
        sm.execute();


        /*SendMail1 sm = new SendMail1(context, email, subject, message);
        if(sm.sendEmail()){
            Toast.makeText(context, "E-mail enviado correctamente!!!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Problemas. No se puedo enviar el correo.", Toast.LENGTH_SHORT).show();
        }*/


    }


    //Función para enviar notificación de variable biométrica Frecuencia Respiratoria.
    public void sendInfo_Email_FR(Context context, String email, String subject, String frec_respiratoria, String nombreEspecialista, String nombrePaciente){

        //sesion_EmailOrigenHospital();             //Evaluaré en esta App si es necesario llamar esta función para enviar el correo.
        //Esto debido a que he observado que en la clase java "SendMail" ya viene por defecto esta configuración.

        String message =
                "*************************************************************\n" +
                        "¡NOTIFICACIÓN FRECUENCIA RESPIRATORIA!\n" +
                        "*************************************************************\n\n" +
                        "*Frecuencia Respiratoria: " + frec_respiratoria +"\n" +
                        "\nNombre del Paciente: " + nombrePaciente +"\n" +
                        "Nombre del Especialísta: " + nombreEspecialista +"\n\n" +
                        "---------------------------------------------------------------------" + "\n" +
                        //"\t\tMensaje Generado: " + date + " ~ " + time + "\n" +
                        "\t\tE-mail Generado: " + fecha() + " ~ " + hora() + "\n" +
                        "---------------------------------------------------------------------" + "\n" +
                        "Copyright(c) HOSPITAL 2019~2020. " +
                        "\nAll rights reserved.";

        //String message = fc;          //Dato a enviar.

        //Creating SendMail object
        SendMail sm = new SendMail(context, email, subject, message);

        //Executing sendmail to send email
        sm.execute();
    }



    //Función para enviar notificación de variable biométrica Oximetría.
    public void sendInfo_Email_Oximetria(Context context, String email, String subject, String spo2, String frecuencia_cardiaca, String nombreEspecialista, String nombrePaciente){

        //sesion_EmailOrigenHospital();             //Evaluaré en esta App si es necesario llamar esta función para enviar el correo.
        //Esto debido a que he observado que en la clase java "SendMail" ya viene por defecto esta configuración.

        String message =
                "*************************************************************\n" +
                        "¡NOTIFICACIÓN OXIMETRÍA!\n" +
                        "*************************************************************\n" +
                        "*Saturación Parcial del Oxígeno (SpO2): " + spo2 +" %\n" +
                        "*Frecuencia Cardíaca: " + frecuencia_cardiaca +" PRbpm\n\n" +
                        "\nNombre del Paciente: " + nombrePaciente +"\n" +
                        "Nombre del Especialísta: " + nombreEspecialista +"\n\n" +
                        "---------------------------------------------------------------------" + "\n" +
                        //"\t\tMensaje Generado: " + date + " ~ " + time + "\n" +
                        "\t\tE-mail Generado: " + fecha() + " ~ " + hora() + "\n" +
                        "---------------------------------------------------------------------" + "\n" +
                        "Copyright(c) HOSPITAL 2019~2020. " +
                        "\nAll rights reserved.";

        //String message = diastolic, systolic, pulsemin;                                           //Dato a enviar.

        //Creating SendMail object
        SendMail sm = new SendMail(context, email, subject, message);

        //Executing sendmail to send email
        sm.execute();
    }







    //Función para enviar notificación de variable biométrica Tensión / Presión Arterial.
    public void sendInfo_Email_TA(Context context, String email, String subject, String diastolic, String systolic, String pulsemin){

        //sesion_EmailOrigenHospital();             //Evaluaré en esta App si es necesario llamar esta función para enviar el correo.
                                                  //Esto debido a que he observado que en la clase java "SendMail" ya viene por defecto esta configuración.

        String nombrePaciente = "xxxxxx";
        String nombreEspecialista = "++++++++";

        String message =
                "*************************************************************\n" +
                "¡NOTIFICACIÓN TENSIÓN ARTERIAL!\n\n" +
                "*************************************************************\n" +
                "*Diastolic Pressure: " + diastolic +"\n" +
                "*Systolic Pressure: " + systolic +"\n" +
                "*Heart Rate: " + pulsemin +"\n\n" +
                "\nNombre del Paciente: " + nombrePaciente +"\n" +
                "\nNombre del Especialísta: " + nombreEspecialista +"\n" +
                "---------------------------------------------------------------------" + "\n" +
                //"\t\tMensaje Generado: " + date + " ~ " + time + "\n" +
                "\t\tE-mail Generado: " + fecha() + " ~ " + hora() + "\n" +
                "---------------------------------------------------------------------" + "\n" +
                "Copyright(c) HOSPITAL 2019~2020. " +
                "\nAll rights reserved.";

        //String message = diastolic, systolic, pulsemin;                                           //Dato a enviar.

        //Creating SendMail object
        SendMail sm = new SendMail(context, email, subject, message);

        //Executing sendmail to send email
        sm.execute();
    }











    //OBtengo nombres y apellidos del usuario admin
    public String getNameBD(Context context, String id){
        db_SQLite admin = new db_SQLite(context);

        int codigo=0;
        String name="";String apellidos="";
        SQLiteDatabase db = admin.getWritableDatabase();
        Cursor fila = db.rawQuery("select nombres,apellidos from usuarios where codigo=1", null);
        if (fila.moveToFirst()) {
            codigo = fila.getInt(0);
            name = fila.getString(1);
            apellidos = fila.getString(2);
        }
        return name +" "+apellidos;
    }


    //Función para recuperar de la base de datos SQLite el/la dirección de correo del Especialista.
    public String getEmail_db(Context context, String documento){

        db_SQLite admin = new db_SQLite(context);
        int codigo=0;
        String correo="";
        SQLiteDatabase db = admin.getWritableDatabase();
        //Cursor fila = db.rawQuery("select usuario from tb_especialista where documento=1", null);
        Cursor fila = db.rawQuery("select usuario from tb_especialista where documento='"+documento+"'", null);

        if (fila.moveToFirst()) {
            correo = fila.getString(0);
        }

        return correo;

    }


    //Función para recuperar de la base de datos SQLite el número de teléfono del especialísta.
    public String getTelefo_db(Context context, String documento){

        db_SQLite admin = new db_SQLite(context);

        String telefono="";
        SQLiteDatabase db = admin.getWritableDatabase();
        //Cursor fila = db.rawQuery("select usuario from tb_especialista where documento=1", null);
        Cursor fila = db.rawQuery("select telefono from tb_especialista where documento='"+documento+"'", null);

        if (fila.moveToFirst()) {
            telefono = fila.getString(0);
        }

        return telefono;
    }




    private void sendEmail(Context context, String asunto, String temp_corporal, String frec_respiratoria, String tens_arterial, String spo2, String frec_cardiaca, String doctor, String paciente) {
    }

    public void capturar_info_biomedia(Context context, String asunto, String tc, String fr, String ta, String spo2, String fc, String especialista, String paciente) {
        sendEmail(context, asunto, tc, fr, ta, spo2, fc, especialista, paciente);
    }



    /*HARÉ MEJOR QUE AL INICIAR SESIÓN SE MANDEN LOS VAROLES Y YO LOS CAPTURA Y GUARDO EN UN FICHERO XML.
    * LUEGO DESDE ESTE FICHERO SOLO MANDO A PEDIR LOS DATOS QUE VAYA NECESITANDO.                       */

    /*
    * ::::::::::::::::::::::::::::::::::::::::::::::::::::::OJO::::::::::::::::::::::::::::::::::::::::::::::::::
    * Datos que nesito son:
    * 1. Para enviar notificaciones via E-MAIL necesito captura el email del especialísta.
    * 2. Para envair sms via GSM necesito capturar el número de teléfono del especialísta.
    * 3. Para que el usuario destino de aviso o notificación activada a traves de un sms o e-mail
    *    sepa de que paciente es la alerta activada y que especialísta está acargo en ese momento necesito tener:
    *    NOMBRE DEL PACIENTE de la tabla tb_pacinente y NOMBRE ESPECIALÍSTA de la tabla tb_especialísta.
     */

    //PDFVIEW.

}
