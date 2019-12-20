package com.example.btasinktask;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class db_SQLite extends SQLiteOpenHelper {

    public db_SQLite(Context context){
        super(context, "db_hospital.db",null, 1);
    }

    ArrayList<String> listaEspecialista;               //Va a representar la información que se va a mostrar en el combo

    String verifico = "";

    ArrayList<dto_pacientes> pacientesList;      //Entidad que representa a los datos de la tabla, en esta caso la tabla Articulos
    ArrayList<String> listaPacientes;            //Va a representar la información que se va a mostrar en el combo

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("create table usuarios(codigo integer not null primary key autoincrement,nombres varchar(50) not null,apellidos varchar(50) not null,usuario varchar(100) not null,clave varchar(10) not null,pregunta varchar(100) not null,respuesta varchar(100) not null, fecha datetime NOT NULL)");
        db.execSQL("create table tb_especialista(documento varchar(100) not null primary key,nombres varchar(50) not null,apellidos varchar(50) not null,direccion varchar(150),telefono varchar(15) not null,especialidad varchar(100),sexo varchar(15) not null, comentario varchar(250) not null, usuario varchar(100) not null, clave varchar(50) not null, pregunta varchar(100) not null,respuesta varchar(100) not null, fecha datetime NOT NULL)");
        db.execSQL("create table tb_pacientes(codigo integer not null primary key autoincrement, dui varchar(15) not null, nombres varchar(50) not null, apellidos varchar(50) not null, direccion varchar(200) not null, telefono varchar(15) not null, estado varchar(255) not null, fecha datetime NOT NULL, comentario text, nombre_contacto_pariente varchar(250), telefono_contacto_pariente varchar(15), direccion_contacto_pariente varchar(250), documento_especialista varchar(15) not null, FOREIGN KEY(documento_especialista) REFERENCES tb_especialista(documento))");


        //db.execSQL("insert into usuarios values(null,'Administrador','admin','admin@utla.edu.sv','admin','¿Cuál es el nombre de tu universidad favorita?','UTLA',datetime())");
        db.execSQL("insert into tb_especialista values('1', 'Manuel de Jesús', 'Gámez López', 'Cas, San Nicolas Lempa, Tecoluca-San Vicente', '+50361107065', 'Docente Investigador', 'Masculino', 'N.A', 'manuel.gamez@itca.edu.sv', '12345', '¿Cuál es el nombre de tu universidad favorita?', 'MEGATEC-ZACATECOLUCA', datetime('now','localtime'))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists tb_especialista");
        db.execSQL("drop table if exists tb_pacientes");
        onCreate(db);
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    //Función para insertar datos.
    //DTO: Data Transfer Object
    public boolean insertardatos(dto datos){
        boolean estado = true;
        int resultado;
        ContentValues registro = new ContentValues();
        try{
            registro.put("documento",datos.getDocumento());
            registro.put("nombres",datos.getNombres());
            registro.put("apellidos",datos.getApellidos());
            registro.put("direccion", datos.getDireccion());
            registro.put("telefono", datos.getTelefono());
            registro.put("especialidad", datos.getEspecialidad());
            registro.put("sexo", datos.getSexo());
            registro.put("comentario", datos.getComentario());
            registro.put("usuario", datos.getEmail());
            registro.put("clave", datos.getClave());
            registro.put("pregunta", datos.getPregunta());
            registro.put("respuesta", datos.getRespuesta());
            registro.put("fecha", datos.getFecha());

            /*adicione estas lineas para verificar sino existe un usuario con el mismo correo
              INICIO.*/
            Cursor fila = this.getWritableDatabase().rawQuery("select usuario from tb_especialista where usuario='"+datos.getEmail()+"'", null);
            if(fila.moveToFirst()==true){
                estado = false;
            }else {
                /*FIN*/
                //estado = (boolean)this.getWritableDatabase().insert("datos","nombre, correo, telefono",registro);
                resultado = (int) this.getWritableDatabase().insert("tb_especialista", "documento,nombres,apellidos,direccion,telefono,especializacion,sexo,comentario,usuario,clave,pregunta,respuesta,fecha", registro);
                if (resultado > 0) estado = true;
                else estado = false;
            }
        }catch (Exception e){
            estado = false;
            Log.e("error.",e.toString());
        }
        return estado;
    }

    public boolean addRegister(dto datos){
        boolean estado = true;
        int resultado;
        try{

            String documento = datos.getDocumento();
            String nombres = datos.getNombres();
            String apellidos = datos.getApellidos();
            String direccion = datos.getDireccion();
            String telefono = datos.getTelefono();
            String especialidad = datos.getEspecialidad();
            String sexo = datos.getSexo();
            String comentario = datos.getComentario();
            String usuario = datos.getEmail();
            String clave = datos.getClave();
            String pregunta = datos.getPregunta();
            String respuesta = datos.getRespuesta();
            //String fecha = datos.getFecha();

            //getting the current time for joining date
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fecha1 = sdf.format(cal.getTime());

            Cursor fila = this.getWritableDatabase().rawQuery("select usuario from tb_especialista where usuario='"+datos.getEmail()+"'", null);
            if(fila.moveToFirst()==true){
                estado = false;
            }else {
                //estado = (boolean)this.getWritableDatabase().insert("datos","nombre, correo, telefono",registro);
                //resultado = (int) this.getWritableDatabase().insert("usuarios", "nombres,apellidos,usuario,clave,pregunta,respuesta", registro);
                String SQL = "INSERT INTO tb_especialista \n" +
                        "(documento,nombres,apellidos,direccion,telefono,especialidad,sexo,comentario,usuario,clave,pregunta,respuesta,fecha)\n" +
                        "VALUES \n" +
                        "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

                //resultado = (int) this.getWritableDatabase().insert("usuarios", "nombres,apellidos,usuario,clave,pregunta,respuesta", registro);
                this.getWritableDatabase().execSQL(SQL, new String[]{documento,nombres,apellidos,direccion,telefono,especialidad,sexo,comentario,usuario,clave,pregunta,respuesta,fecha1});
                //if (resultado > 0) estado = true;
                //else estado = false;
                estado = true;
            }
        }catch (Exception e){
            estado = false;
            Log.e("error.",e.toString());
        }
        return estado;
    }



    public boolean addRegisterPaciente(dto_pacientes datos){
        boolean estado = true;
        int resultado;
        try{
            //codigo, dui, nombres, apellidos, direccion, telefono, estado, fecha, comentario, nombre_contacto_pariente, telefono_contacto_pariente,
            // direccion_contacto_pariente, documento_especialista
            String dui = datos.getDui();
            String nombres = datos.getNombres();
            String apellidos = datos.getApellidos();
            String direccion = datos.getDireccion();
            String telefono = datos.getTelefono();
            String estado1 = datos.getEstado1();
            String fe = datos.getFecha1();
            String comentario = datos.getComentario();
            String nombre_cont_p = datos.getNombre_cont_p();
            String telefono_cont_p = datos.getTelefono_cont_p();
            String direccion_cont_p = datos.getDireccion_cont_p();
            String documento_especialista = datos.getDocumento_especialista();

            //getting the current time for joining date
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fecha1 = sdf.format(cal.getTime());

            Cursor fila = this.getWritableDatabase().rawQuery("select dui from tb_pacientes where dui='"+datos.getDui()+"'", null);
            if(fila.moveToFirst()==true){
                estado = false;
            }else {
                //estado = (boolean)this.getWritableDatabase().insert("datos","nombre, correo, telefono",registro);
                //resultado = (int) this.getWritableDatabase().insert("usuarios", "nombres,apellidos,usuario,clave,pregunta,respuesta", registro);
                String SQL = "INSERT INTO tb_pacientes \n" +
                        "(dui,nombres,apellidos,direccion,telefono,estado,fecha,comentario,nombre_contacto_pariente,telefono_contacto_pariente,direccion_contacto_pariente,documento_especialista)" +
                        "VALUES \n" +
                        "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

                //resultado = (int) this.getWritableDatabase().insert("usuarios", "nombres,apellidos,usuario,clave,pregunta,respuesta", registro);
                this.getWritableDatabase().execSQL(SQL, new String[]{dui,nombres,apellidos,direccion,telefono,estado1,fe,comentario,nombre_cont_p,telefono_cont_p,direccion_cont_p,documento_especialista});
                //if (resultado > 0) estado = true;
                //else estado = false;
                estado = true;
            }
        }catch (Exception e){
            estado = false;
            Log.e("error.",e.toString());
        }
        return estado;
    }






    //Métodos de los usuarios: En este caso los especialistas de atención sanitaria.
    //Función para consultar la existencia del nombre de usuario o dirección de correo.
    public boolean consultaUser(dto datos) {
        boolean estado = false;
        SQLiteDatabase bd = this.getWritableDatabase();
        try {
            String user = datos.getEmail();
            Cursor fila = bd.rawQuery("select usuario from tb_especialista where usuario='"+user+"'", null);
            if(fila.moveToFirst()){
                estado = true;
            }else{
                estado = false;
            }
            bd.close();
        } catch (Exception e) {
            estado = false;
            Log.e("error.",e.toString());
        }
        return estado;
    }

    //Función para consultar la existencia del nombre de usuario o dirección de correo.
    public boolean verificoRespuesta(dto datos) {
        boolean estado = false;
        SQLiteDatabase bd = this.getWritableDatabase();
        try {
            String user = datos.getRespuesta();
            Cursor fila = bd.rawQuery("select respuesta from tb_especialista where respuesta='"+datos.getRespuesta()+"'", null);
            if(fila.moveToFirst()){
                estado = true;
            }else{
                estado = false;
            }
            bd.close();
        } catch (Exception e) {
            estado = false;
            Log.e("error.",e.toString());
        }
        return estado;
    }

    //Función para actualizar datos
    public boolean updateClave(dto datos){
        //GestionSQLiteOpenHelper gestionSQLiteOpenHelper = new GestionSQLiteOpenHelper(this, "gestionn", null, 1);
        //SQLiteDatabase bd = gestionSQLiteOpenHelper.getWritableDatabase();
        boolean estado = true;
        SQLiteDatabase bd = this.getWritableDatabase();
        try{
            String codigo = datos.getDocumento();
            String clave = datos.getClave();
            ContentValues registro = new ContentValues();
            //registro.put("codigo",codigo);
            registro.put("clave",clave);
            int cant = (int)this.getWritableDatabase().update("tb_especialista", registro, "documento='"+codigo+"'", null);
            //int cant = bd.update("usuarios", registro,"codigo="+codigo,null);
            bd.close();

            if (cant > 0) estado = true;
            else estado = false;
            /*if(cant==1){
                return true;
            }else{
                return false;
            }*/
        }catch(Exception e){
            estado = false;
            Log.e("error.",e.toString());
        }

        return estado;
    }


    //Métdo que devuelve la lista de los especialistas registrados.
    //Obtengo datos en un arrayList y los retorno.
    public ArrayList<String> consultaEspecialistas(){
        boolean estado = false;
        //SQLiteDatabase bd = this.getWritableDatabase();
        SQLiteDatabase bd = this.getReadableDatabase();
        //ArrayList<String> listaEspecialista;             //Va a representar la información que se va a mostrar en el combo

        dto especialista = null;                           //Creamos la instancia vacia.

        ArrayList<dto> especialistaList;                   //Entidad que representa a los datos de la tabla, en esta caso la tabla Articulos
        especialistaList = new ArrayList<dto>();

        try{
            //Cursor fila = bd.rawQuery("select * from tb_especialista",null);
            Cursor fila = bd.rawQuery("select documento, nombres, apellidos from tb_especialista",null);
            while (fila.moveToNext()){
                verifico = fila.getString(0);
                if(verifico.equals("1")){

                }else {
                    especialista = new dto();
                    especialista.setDocumento(fila.getString(0));
                    especialista.setNombres(fila.getString(1));
                    especialista.setApellidos(fila.getString(2));
                    especialistaList.add(especialista);
                }
            }

            listaEspecialista = new ArrayList<String>();
            //listaEspecialista = new ArrayList<>();
            listaEspecialista.add("Seleccione Especialista Responsable");

            for(int i=0;i<= especialistaList.size();i++){
                listaEspecialista.add(especialistaList.get(i).getDocumento()+" ~ "+especialistaList.get(i).getNombres()+" "+especialistaList.get(i).getApellidos());
            }

            //bd().close();

        }catch (Exception e){

        }
        return listaEspecialista;
    }


    //Método para cargar datos de consulta base de datos SQLite en ListView
    //public ArrayList<dto_pacientes> consultaListaArticulos(){

    public ArrayList<String> consultaListaPacientes(String duiEspecialista){
        //public ArrayList<String> consultaListaPacientes(){
        boolean estado = false;
        //SQLiteDatabase bd = this.getWritableDatabase();
        SQLiteDatabase bd = this.getReadableDatabase();

        dto_pacientes pacientes = null;                   //Creamos la instancia vacia.
        pacientesList = new ArrayList<dto_pacientes>();

        try{
            //codigo,dui,nombres,apellidos,direccion,telefono,estado,fecha,comentario,nombre_contacto_pariente,telefono_contacto_pariente,direccion_contacto_pariente,documento_especialista
            //Cursor fila = bd.rawQuery("select * from tb_pacientes",null);

            //Cursor fila = bd.rawQuery("select dui,nombres,apellidos,direccion,telefono,estado,fecha,comentario,nombre_contacto_pariente,telefono_contacto_pariente,direccion_contacto_pariente, documento_especialista from tb_pacientes",null);
            //Cursor fila = bd.rawQuery("select dui,nombres,apellidos,direccion,telefono,estado,fecha,comentario,nombre_contacto_pariente,telefono_contacto_pariente,direccion_contacto_pariente, documento_especialista from tb_pacientes where documento_especialista='"+duiEspecialista+"'",null);

            /*Cursor fila = bd.rawQuery("select tb_pacientes.dui,\n" +
                    "tb_pacientes.nombres,\n" +
                    "tb_pacientes.apellidos,\n" +
                    "tb_pacientes.nombre_contacto_pariente,\n" +
                    "tb_pacientes.telefono_contacto_pariente,\n" +
                    "tb_pacientes.direccion_contacto_pariente, \n" +
                    "tb_pacientes.documento_especialista from tb_pacientes INNER JOIN tb_especialista ON tb_pacientes.documento_especialista=tb_especialista.documento where tb_pacientes.documento_especialista = '"+duiEspecialista+"'",null);*/

            Cursor fila = bd.rawQuery("select tb_pacientes.dui,\n" +
                    "tb_pacientes.nombres,\n" +
                    "tb_pacientes.apellidos,\n" +
                    "tb_pacientes.direccion,\n" +
                    "tb_pacientes.telefono,\n" +
                    "tb_pacientes.estado, \n" +
                    "tb_pacientes.documento_especialista from tb_pacientes INNER JOIN tb_especialista ON tb_pacientes.documento_especialista=tb_especialista.documento where tb_pacientes.documento_especialista = '"+duiEspecialista+"'",null);

            while (fila.moveToNext()){
                pacientes = new dto_pacientes();
                pacientes.setDui(fila.getString(0));
                pacientes.setNombres(fila.getString(1));
                pacientes.setApellidos(fila.getString(2));
                pacientes.setDireccion(fila.getString(3));
                pacientes.setTelefono(fila.getString(4));
                pacientes.setEstado1(fila.getString(5));
                pacientes.setDocumento_especialista(fila.getString(6));
                /*pacientes.setFecha1(fila.getString(6));
                pacientes.setComentario(fila.getString(7));
                pacientes.setNombre_cont_p(fila.getString(8));
                pacientes.setTelefono_cont_p(fila.getString(9));
                pacientes.setDireccion_cont_p(fila.getString(10));
                pacientes.setDocumento_especialista(fila.getString(11));*/

                pacientesList.add(pacientes);

                Log.i("Documento: ", String.valueOf(pacientes.getDui()));
                Log.i("Nombres: ", pacientes.getNombres().toString());
                Log.i("Apellidos: ", String.valueOf(pacientes.getApellidos()));
                Log.i("Dirección: ", String.valueOf(pacientes.getDireccion()));
                Log.i("Teléfono: ", String.valueOf(pacientes.getTelefono()));
                Log.i("Estado: ", String.valueOf(pacientes.getEstado1()));
                Log.i("DocumentoEspecialista: ", String.valueOf(pacientes.getDocumento_especialista()));

            }

            listaPacientes = new ArrayList<String>();
            //listaArticulos = new ArrayList<>();
            //listaPacientes.add("Seleccione");
            for(int i=0;i<=pacientesList.size();i++){
                        listaPacientes.add("# Paciente:"+(i+1)+"\n"+
                        pacientesList.get(i).getDui()+"\n"+
                        pacientesList.get(i).getNombres()+"\n"+
                        pacientesList.get(i).getApellidos()+"\n"+
                        pacientesList.get(i).getDireccion()+"\n"+
                        pacientesList.get(i).getTelefono()+"\n"+
                        pacientesList.get(i).getEstado1()+"\n"+
                        pacientesList.get(i).getDocumento_especialista());
                /*listaPacientes.add("Dui: "+pacientesList.get(i).getDui()+"\n"+
                        "Nombre Paciente:"+pacientesList.get(i).getNombres()+"\n"+
                        "Apellidos Paciente:"+pacientesList.get(i).getApellidos());*/
            }
        }catch (Exception e){

        }
        return listaPacientes;
    }



    public ArrayList<dto_pacientes> consultaListaPacientes1(String duiEspecialista){
        //public ArrayList<String> consultaListaPacientes(){
        boolean estado = false;
        //SQLiteDatabase bd = this.getWritableDatabase();
        SQLiteDatabase bd = this.getReadableDatabase();

        dto_pacientes pacientes = null;                   //Creamos la instancia vacia.
        pacientesList = new ArrayList<dto_pacientes>();

        try{

            Cursor fila = bd.rawQuery("select tb_pacientes.dui,\n" +
                    "tb_pacientes.nombres,\n" +
                    "tb_pacientes.apellidos,\n" +
                    "tb_pacientes.direccion,\n" +
                    "tb_pacientes.telefono,\n" +
                    "tb_pacientes.fecha, \n" +
                    "tb_pacientes.documento_especialista from tb_pacientes INNER JOIN tb_especialista ON tb_pacientes.documento_especialista=tb_especialista.documento where tb_pacientes.documento_especialista = '"+duiEspecialista+"'",null);

            while (fila.moveToNext()){
                pacientes = new dto_pacientes();
                pacientes.setDui(fila.getString(0));
                pacientes.setNombres(fila.getString(1));
                pacientes.setApellidos(fila.getString(2));
                pacientes.setDireccion(fila.getString(3));
                pacientes.setTelefono(fila.getString(4));
                pacientes.setFecha1(fila.getString(5));
                pacientes.setDocumento_especialista(fila.getString(6));
                /*pacientes.setFecha1(fila.getString(6));
                pacientes.setComentario(fila.getString(7));
                pacientes.setNombre_cont_p(fila.getString(8));
                pacientes.setTelefono_cont_p(fila.getString(9));
                pacientes.setDireccion_cont_p(fila.getString(10));
                pacientes.setDocumento_especialista(fila.getString(11));*/

                pacientesList.add(pacientes);

                Log.i("Documento: ", String.valueOf(pacientes.getDui()));
                Log.i("Nombres: ", pacientes.getNombres().toString());
                Log.i("Apellidos: ", String.valueOf(pacientes.getApellidos()));
                Log.i("Dirección: ", String.valueOf(pacientes.getDireccion()));
                Log.i("Teléfono: ", String.valueOf(pacientes.getTelefono()));
                Log.i("Estado: ", String.valueOf(pacientes.getEstado1()));
                Log.i("DocumentoEspecialista: ", String.valueOf(pacientes.getDocumento_especialista()));

            }

        }catch (Exception e){

        }
        return pacientesList;
    }



    //Función para actualizar los datos del paciente y contactos del paciente.
    public boolean actualizoPacientes(dto_pacientes datos){
        boolean estado = true;
        int resultado;
        SQLiteDatabase bd = this.getWritableDatabase();
        try{

            int codigo=datos.getCodigo();
            String dui=datos.getDui();
            String nombres=datos.getNombres();
            String apellidos=datos.getApellidos();
            String direccion=datos.getDireccion();
            String telefono=datos.getTelefono();
            String estado1=datos.getEstado1();
            String fecha1=datos.getFecha1();
            String comentario=datos.getComentario();
            String nombre_cont_p=datos.getNombre_cont_p();
            String telefono_cont_p=datos.getTelefono_cont_p();
            String direccion_cont_p=datos.getDireccion_cont_p();
            String documento_especialista=datos.getDocumento_especialista();
            //datos.getNombreEspecialistaResponsable();

            //String[] parametros = {String.valueOf(datos.getCodigo())};

            ContentValues registro = new ContentValues();
            //registro.put("codigo", codigo);
            //registro.put("dui", dui);
            registro.put("nombres", nombres);
            registro.put("apellidos", apellidos);
            registro.put("direccion", direccion);
            registro.put("telefono", telefono);
            registro.put("estado", estado1);
            //registro.put("fecha", fecha1);
            registro.put("comentario", comentario);
            registro.put("nombre_contacto_pariente", nombre_cont_p);
            registro.put("telefono_contacto_pariente", telefono_cont_p);
            registro.put("direccion_contacto_pariente", direccion_cont_p);
            //int cant = (int) this.getWritableDatabase().update("articulos", registro, "codigo=" + codigo, null);
            int cant = (int) bd.update("tb_pacientes", registro, "codigo=" + codigo, null);
            //bd.update("articulos",registro,"codigo=?",parametros);

            bd.close();
            if(cant>0) estado = true;
            else estado = false;
        }catch (Exception e){
            estado = false;
            Log.e("error.",e.toString());
        }
        return estado;
    }


    //Función para actualizar especialista y es llamado desde la ventana modal de "Configuración de Especialísta"
    public boolean actualizoEspecialista(dto datos){
        boolean estado = true;
        int resultado;
        SQLiteDatabase bd = this.getWritableDatabase();
        try{

            String documento=datos.getDocumento();
            //String nombres=datos.getNombres();
            //String apellidos=datos.getApellidos();
            //String direccion=datos.getDireccion();
            String telefono=datos.getTelefono();
            //String especialidad=datos.getEspecialidad();
            //String sexo=datos.getSexo();
            //String comentario=datos.getComentario();
            String email=datos.getEmail();
            //String clave=datos.getClave();
            //String pregunta=datos.getPregunta();
            //String respuesta=datos.getRespuesta();
            //String fecha =datos.getFecha();
            ContentValues registro = new ContentValues();
            //registro.put("documento", documento);
            registro.put("telefono", telefono);
            registro.put("usuario", email);

            //int cant = (int) this.getWritableDatabase().update("articulos", registro, "codigo=" + codigo, null);
            int cant = (int) bd.update("tb_especialista", registro, "documento='" + documento + "'", null);
            //bd.update("articulos",registro,"codigo=?",parametros);

            bd.close();
            if(cant>0) estado = true;
            else estado = false;
        }catch (Exception e){
            estado = false;
            Log.e("error.",e.toString());
        }
        return estado;
    }


    public boolean consultaDatosActualizadoEspecialista(dto datos){
        boolean estado = true;
        int resultado;
        SQLiteDatabase bd = this.getWritableDatabase();
        try {
            String documento = datos.getDocumento();
            String nombres = datos.getNombres();
            String apellidos = datos.getApellidos();
            String telefono = datos.getTelefono();
            String usuario_email = datos.getEmail();

            Cursor fila = bd.rawQuery("select documento, nombres, apellidos, telefono, usuario from tb_especialista where documento ='" + documento + "'", null);
            if (fila.moveToFirst()) {
                datos.setDocumento(fila.getString(0));
                datos.setNombres(fila.getString(1));
                datos.setApellidos(fila.getString(2));
                datos.setTelefono(fila.getString(3));
                datos.setEmail(fila.getString(4));

                estado = true;
            }else {
                estado = false;
            }
            bd.close();

        }catch (Exception e){
            estado = false;
            Log.e("error.",e.toString());
        }
        return estado;
    }


}
