package com.example.btasinktask;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;


//import java.util.ArrayList;
import java.util.List;

public class PacienteAdapter1 extends ArrayAdapter<dto_pacientes> {


    Context mCtx;
    int listLayoutRes;
    List<dto_pacientes> UsersList;
    //ArrayList<dto_pacientes> PacienteList;
    int total;
    String user;
    Cursor fila;


    //public PacienteAdapter1(@NonNull Context mCtx, int listLayoutRes, @NonNull ArrayList<dto_pacientes> PacienteList,int total, String user) {

    public PacienteAdapter1(Context mCtx, int listLayoutRes,List<dto_pacientes> PacienteList, int total, String user) {
        //public PacienteAdapter1(@NonNull Context mCtx, int listLayoutRes, @NonNull List<dto_pacientes> PacienteList, int total, String user) {
        super(mCtx, listLayoutRes, PacienteList);

        this.mCtx = mCtx;
        this.listLayoutRes = listLayoutRes;
        this.UsersList = PacienteList;
        this.total = total;
        this.user = user;
    }




   /* @Override
    public int getCount() {
        return PacienteList.size();
    }


    @Override
    public dto_pacientes getItem(int position) {
        return PacienteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }*/


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(listLayoutRes, null);

        final dto_pacientes datos=UsersList.get(position);

        //Toast.makeText(mCtx, "Datos: "+datos, Toast.LENGTH_SHORT).show();

        //final TextView tvCodigo = (TextView)view.findViewById(R.id.tvCodigo);
        final TextView tvNombres = (TextView)view.findViewById(R.id.tvNombres);
        final TextView tvDui = (TextView)view.findViewById(R.id.tvDui);
        final TextView tvTelefono = (TextView)view.findViewById(R.id.tvTelefono);
        final TextView tvDireccion = (TextView)view.findViewById(R.id.tvDireccion);
        final TextView tvFechaHora = (TextView)view.findViewById(R.id.tvFechaHora);
        final TextView tvDrResponsable = (TextView)view.findViewById(R.id.tvDrResponsable);

        tvNombres.setText(null);
        tvDui.setText(null);
        tvTelefono.setText(null);
        tvDireccion.setText(null);
        tvFechaHora.setText(null);
        tvDrResponsable.setText(null);

        ImageView btnEdit = (ImageView) view.findViewById(R.id.btnEdit);
        ImageView btnDelete = (ImageView) view.findViewById(R.id.btnDelete);


        if(user.trim().equals("1")){
            if(datos.getDui().equals("1")){
                btnDelete.setVisibility(View.INVISIBLE);
            }else {
                btnDelete.setVisibility(View.VISIBLE);
            }
        }else{
            btnDelete.setVisibility(View.GONE);
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mCtx, "Clic en botón editar.", Toast.LENGTH_SHORT).show();
                detallesPacientes(datos);
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mCtx, "Clic en botón borrar.", Toast.LENGTH_SHORT).show();
                deleteRegiserPacientes(datos);
            }
        });


        String p = UsersList.get(position).getNombres() + " " + UsersList.get(position).getApellidos();
        //Toast.makeText(mCtx, "Nombre: "+p, Toast.LENGTH_SHORT).show();


        //Toast.makeText(mCtx, "Total:"+UsersList.size(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(mCtx, "Total:"+UsersList.get(position), Toast.LENGTH_SHORT).show();

        tvNombres.setText(datos.getNombres() + " " + datos.getApellidos());
        tvDui.setText(datos.getDui());
        tvTelefono.setText(datos.getTelefono());
        tvDireccion.setText(datos.getDireccion());
        tvFechaHora.setText(datos.getFecha1());
        tvDrResponsable.setText(datos.getDocumento_especialista());

        /*tvNombres.setText(datos.getNombres() + " "+datos.getApellidos());
        tvDui.setText(datos.getDui());
        tvTelefono.setText(datos.getTelefono());
        tvDireccion.setText(datos.getDireccion());
        tvFechaHora.setText(datos.getFecha1());
        tvDrResponsable.setText(datos.getDocumento_especialista());*/


        return view;

    }

    private void deleteRegiserPacientes(final dto_pacientes datos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
        //builder.setTitle("¿Esta seguro de borrar el registro? "+tvNombres.getText().toString());
        builder.setIcon(R.drawable.ic_delete1);
        builder.setCancelable(false);
        builder.setTitle("Warning");
        builder.setMessage("¿Esta seguro de borrar el registro? \n\nDocumento Paciente: " + datos.getDui() + "\n" +
                "Nombre: " + datos.getNombres() + " " + datos.getApellidos());

        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                boolean estado = false;
                db_SQLite admin = new db_SQLite(mCtx);
                SQLiteDatabase db = admin.getWritableDatabase();
                //Cursor fila=db.rawQuery("select codigo from tb_pacientes where codigo='" + String.valueOf(datos.getCodigo()) + "'",null);
                //Cursor fila=db.rawQuery("select codigo from tb_pacientes where codigo=" + datos.getCodigo(),null);


                /*Cursor fila=db.rawQuery("select documento from tb_especialista where documento=1",null);
                //Cursor fila=db.rawQuery("select codigo from usuarios where codigo='"+datos.getCodigo()+"'",null);
                //if(fila.moveToFirst()==true) {
                if(fila.moveToFirst()){
                    String documento = fila.getString(0);
                    String id = String.valueOf(documento);
                    int valor = datos.getCodigo();
                    String valor1=String.valueOf(valor);
                    if (valor1.equals(id)) {
                        estado = true;
                    }
                }else{
                    estado = false;
                }*/


                /*
                String sql = "DELETE FROM tb_pacientes WHERE codigo = ?";
                db.execSQL(sql, new Integer[]{datos.getCodigo()});
                */

                int codigo = datos.getCodigo();
                int cantidad = db.delete("tb_pacientes", "codigo=" + datos.getCodigo(), null);
                if (cantidad > 0) {
                    Toast.makeText(mCtx, "Registro Paciente Eliminado Correctamente!", Toast.LENGTH_SHORT).show();
                }else{

                }

                //reloadUsersFromDatabase(datos);
                reloadUsersFromDatabase(user);

            }
        });


        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {



            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }


    //private void detallesPacientes(final String codigo, final String documento, final String nombres, final String apellidos, final String direccion, final String telefono, final String estado, final String fecha, final String comentario, final String nombre_cont_p, final String telefono_cont_p, final String direccion_cont_p,final String doc_especialista,String nombre_especialista){
    private void detallesPacientes(final dto_pacientes datos){

        //final android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(mCtx);
        //mBuilder.setCancelable(false);
        //AlertDialog.Builder mBuilder = new AlertDialog.Builder(getApplicationContext());
        //mBuilder.setIcon(R.drawable.ic_servidor);
        //mBuilder.setTitle("<<<UTLA>>>");
        final AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
        builder.setCancelable(false);
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        final View mView =  inflater.inflate(R.layout.detalles_paciente, null);
        builder.setView(mView);


        final TextView tv_codigo1 = (TextView) mView.findViewById(R.id.tv_codigo1);
        final TextView tvdui1 = (TextView) mView.findViewById(R.id.tvdui1);

        final TextView tvnombres1 = (TextView) mView.findViewById(R.id.tvnombres1);
        final TextView tvapellidos1 = (TextView) mView.findViewById(R.id.tvapellidos1);

        final LinearLayout ll_names = (LinearLayout)mView.findViewById(R.id.ll_names);
        final LinearLayout ll_name = (LinearLayout)mView.findViewById(R.id.ll_name);

        final EditText et_nombres1 = (EditText)mView.findViewById(R.id.et_nombres1);
        final EditText et_apellidos1 = (EditText)mView.findViewById(R.id.et_apellidos1);

        final TextView tvdireccion1 = (TextView) mView.findViewById(R.id.tvdireccion1);
        final EditText et_direccion1 = (EditText) mView.findViewById(R.id.et_direccion1);

        final TextView tvtelefono1 = (TextView) mView.findViewById(R.id.tvtelefono1);
        final EditText et_telefono1 = (EditText) mView.findViewById(R.id.et_telefono1);

        final TextView tvfecha = (TextView) mView.findViewById(R.id.tvfecha);

        final TextView tvestado1 = (TextView) mView.findViewById(R.id.tvestado1);
        final EditText et_estado1 = (EditText) mView.findViewById(R.id.et_estado1);

        final TextView  tvcomentario1 = (TextView) mView.findViewById(R.id.tvcomentario1);
        final EditText  et_comentario1 = (EditText) mView.findViewById(R.id.et_comentario1);

        final TextView  tvnombrepariente1 = (TextView) mView.findViewById(R.id.tvnombrepariente1);
        final EditText  et_nombrepariente1 = (EditText) mView.findViewById(R.id.et_nombrepariente1);

        final TextView  tvtelefonopariente1 = (TextView) mView.findViewById(R.id.tvtelefonopariente1);
        final EditText  et_telefonopariente1 = (EditText) mView.findViewById(R.id.et_telefonopariente1);

        final TextView  tvdireccionpariente1 = (TextView) mView.findViewById(R.id.tvdireccionpariente1);
        final EditText  et_direccionpariente1 = (EditText) mView.findViewById(R.id.et_direccionpariente1);

        final TextView  tvespecialistaresponsable1 = (TextView) mView.findViewById(R.id.tvespecialistaresponsable1);

        final ImageView BtnCerrar = (ImageView)mView.findViewById(R.id.BtnCerrar);

        final LinearLayout ll_botones1 = (LinearLayout) mView.findViewById(R.id.ll_botones1);
        final Button btnClose = (Button)mView.findViewById(R.id.btnClose);
        final Button btnClose1 = (Button)mView.findViewById(R.id.btnClose1);
        final Button btnEdit = (Button)mView.findViewById(R.id.btnEdit);

        final LinearLayout ll_botones2 = (LinearLayout) mView.findViewById(R.id.ll_botones2);
        final Button btnCancel= (Button)mView.findViewById(R.id.btnCancelar);
        final Button btnEdit1 = (Button)mView.findViewById(R.id.btnEdit1);

        tv_codigo1.setText(String.valueOf(datos.getCodigo()));
        tvdui1.setText(datos.getDui());
        tvnombres1.setText(datos.getNombres());
        tvapellidos1.setText(datos.getApellidos());
        tvdireccion1.setText(datos.getDireccion());
        tvtelefono1.setText(datos.getTelefono());
        tvestado1.setText(datos.getEstado1());
        tvfecha.setText(datos.getFecha1());
        tvcomentario1.setText(datos.getComentario());
        tvnombrepariente1.setText(datos.getNombre_cont_p());
        tvtelefonopariente1.setText(datos.getTelefono_cont_p());
        tvdireccionpariente1.setText(datos.getDireccion_cont_p());
        tvespecialistaresponsable1.setText("Documento: "+ datos.getDocumento_especialista() + "\nNombre: " +datos.getNombreEspecialistaResponsable());

        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

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


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_botones1.setVisibility(View.VISIBLE);
                ll_botones2.setVisibility(View.GONE);

                ll_name.setVisibility(View.VISIBLE);
                tvnombres1.setVisibility(View.VISIBLE);
                tvapellidos1.setVisibility(View.VISIBLE);

                ll_names.setVisibility(View.GONE);
                et_nombres1.setVisibility(View.GONE);
                et_apellidos1.setVisibility(View.GONE);

                tvdireccion1.setVisibility(View.VISIBLE);
                et_direccion1.setVisibility(View.GONE);

                tvtelefono1.setVisibility(View.VISIBLE);
                et_telefono1.setVisibility(View.GONE);

                tvestado1.setVisibility(View.VISIBLE);
                et_estado1.setVisibility(View.GONE);

                tvcomentario1.setVisibility(View.VISIBLE);
                et_comentario1.setVisibility(View.GONE);

                tvnombrepariente1.setVisibility(View.VISIBLE);
                et_nombrepariente1.setVisibility(View.GONE);

                tvtelefonopariente1.setVisibility(View.VISIBLE);
                et_telefonopariente1.setVisibility(View.GONE);

                tvdireccionpariente1.setVisibility(View.VISIBLE);
                et_direccionpariente1.setVisibility(View.GONE);

            }
        });

        BtnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Activity_lista_pacientes.this, "Clic en editar.", Toast.LENGTH_SHORT).show();
                ll_botones1.setVisibility(View.GONE);
                ll_botones2.setVisibility(View.VISIBLE);

                ll_name.setVisibility(View.GONE);
                tvnombres1.setVisibility(View.GONE);
                tvapellidos1.setVisibility(View.GONE);

                ll_names.setVisibility(View.VISIBLE);
                et_nombres1.setVisibility(View.VISIBLE);
                et_apellidos1.setVisibility(View.VISIBLE);

                tvdireccion1.setVisibility(View.GONE);
                et_direccion1.setVisibility(View.VISIBLE);

                tvtelefono1.setVisibility(View.GONE);
                et_telefono1.setVisibility(View.VISIBLE);

                tvestado1.setVisibility(View.GONE);
                et_estado1.setVisibility(View.VISIBLE);

                tvcomentario1.setVisibility(View.GONE);
                et_comentario1.setVisibility(View.VISIBLE);

                tvnombrepariente1.setVisibility(View.GONE);
                et_nombrepariente1.setVisibility(View.VISIBLE);

                tvtelefonopariente1.setVisibility(View.GONE);
                et_telefonopariente1.setVisibility(View.VISIBLE);

                tvdireccionpariente1.setVisibility(View.GONE);
                et_direccionpariente1.setVisibility(View.VISIBLE);


                et_nombres1.setText(tvnombres1.getText().toString());
                et_apellidos1.setText(tvapellidos1.getText().toString());
                et_direccion1.setText(tvdireccion1.getText().toString());
                et_telefono1.setText(tvtelefono1.getText().toString());
                et_estado1.setText(tvestado1.getText().toString());
                et_comentario1.setText(tvcomentario1.getText().toString());

                et_nombrepariente1.setText(tvnombrepariente1.getText().toString());
                et_telefonopariente1.setText(tvtelefonopariente1.getText().toString());
                et_direccionpariente1.setText(tvdireccionpariente1.getText().toString());

            }
        });

        btnEdit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ll_botones1.setVisibility(View.VISIBLE);
                ll_botones2.setVisibility(View.GONE);

                ll_name.setVisibility(View.VISIBLE);
                tvnombres1.setVisibility(View.VISIBLE);
                tvapellidos1.setVisibility(View.VISIBLE);

                ll_names.setVisibility(View.GONE);
                et_nombres1.setVisibility(View.GONE);
                et_apellidos1.setVisibility(View.GONE);

                tvdireccion1.setVisibility(View.VISIBLE);
                et_direccion1.setVisibility(View.GONE);

                tvtelefono1.setVisibility(View.VISIBLE);
                et_telefono1.setVisibility(View.GONE);

                tvestado1.setVisibility(View.VISIBLE);
                et_estado1.setVisibility(View.GONE);

                tvcomentario1.setVisibility(View.VISIBLE);
                et_comentario1.setVisibility(View.GONE);

                tvnombrepariente1.setVisibility(View.VISIBLE);
                et_nombrepariente1.setVisibility(View.GONE);

                tvtelefonopariente1.setVisibility(View.VISIBLE);
                et_telefonopariente1.setVisibility(View.GONE);

                tvdireccionpariente1.setVisibility(View.VISIBLE);
                et_direccionpariente1.setVisibility(View.GONE);

                db_SQLite base = new db_SQLite(mCtx);

                datos.setCodigo(datos.getCodigo());
                datos.setDui(datos.getDui());
                datos.setNombres(et_nombres1.getText().toString());
                datos.setApellidos(et_apellidos1.getText().toString());
                datos.setDireccion(et_direccion1.getText().toString());
                datos.setTelefono(et_telefono1.getText().toString());
                datos.setEstado1(et_estado1.getText().toString());
                datos.setComentario(et_comentario1.getText().toString());
                datos.setNombre_cont_p(et_nombrepariente1.getText().toString());
                datos.setTelefono_cont_p(et_telefonopariente1.getText().toString());
                datos.setDireccion_cont_p(et_direccionpariente1.getText().toString());

                if(base.actualizoPacientes(datos)){
                    //tvnombres1.setText(et_nombres1.getText().toString() + " " + et_apellidos1.getText().toString());
                    tvnombres1.setText(et_nombres1.getText().toString());
                    tvapellidos1.setText (et_apellidos1.getText().toString());
                    tvdireccion1.setText(et_direccion1.getText().toString());
                    tvtelefono1.setText(et_telefono1.getText().toString());
                    tvestado1.setText(et_estado1.getText().toString());
                    tvcomentario1.setText(et_comentario1.getText().toString());
                    tvnombrepariente1.setText(et_nombrepariente1.getText().toString());
                    tvtelefonopariente1.setText(et_telefonopariente1.getText().toString());
                    tvdireccionpariente1.setText(et_direccionpariente1.getText().toString());

                    reloadUsersFromDatabase(user);

                    Toast.makeText(mCtx, "Cambios aplicados correctamente!!!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(mCtx, "No se han encontrado resultados para la busqueda especificada.", Toast.LENGTH_SHORT).show();
                }



            }
        });

    }



    private void reloadUsersFromDatabase(final String id) {
        //private void reloadUsersFromDatabase(final dto_pacientes datos) {
        db_SQLite admin=new db_SQLite(mCtx);
        SQLiteDatabase db=admin.getWritableDatabase();

        //Toast.makeText(mCtx, ""+id, Toast.LENGTH_SHORT).show();

        if(id.equals("1")){
            fila = db.rawQuery("select * from tb_pacientes INNER JOIN tb_especialista ON tb_pacientes.documento_especialista=tb_especialista.documento", null);
        }else{
            //fila = db.rawQuery("select * from tb_pacientes INNER JOIN tb_especialista ON tb_pacientes.documento_especialista=tb_especialista.documento where tb_pacientes.documento_especialista = '" + datos.getDocumento_especialista() + "'", null);
            fila = db.rawQuery("select * from tb_pacientes INNER JOIN tb_especialista ON tb_pacientes.documento_especialista=tb_especialista.documento where tb_pacientes.documento_especialista = '" + id + "'", null);
        }

        if (fila.moveToFirst()) {

            UsersList.clear();
            do {
                UsersList.add(new dto_pacientes(
                            fila.getInt(0),
                            fila.getString(1),
                            fila.getString(2),
                            fila.getString(3),
                            fila.getString(4),
                            fila.getString(5),
                            fila.getString(6),
                            fila.getString(7),
                            fila.getString(8),
                            fila.getString(9),
                            fila.getString(10),
                            fila.getString(11),
                            fila.getString(12),
  fila.getString(14) + " " + fila.getString(15)
                ));

                /*dto_pacientes pacientes = new dto_pacientes();
                pacientes.setCodigo(fila.getInt(0));
                pacientes.setDui(fila.getString(1));
                pacientes.setNombres(fila.getString(2));
                pacientes.setApellidos(fila.getString(3));
                pacientes.setDireccion(fila.getString(4));
                pacientes.setTelefono(fila.getString(5));
                pacientes.setEstado1(fila.getString(6));
                pacientes.setFecha1(fila.getString(7));
                pacientes.setComentario(fila.getString(8));
                pacientes.setNombre_cont_p(fila.getString(9));
                pacientes.setTelefono_cont_p(fila.getString(10));
                pacientes.setDireccion_cont_p(fila.getString(11));
                pacientes.setDocumento_especialista(fila.getString(12));  //Dui especialista
                pacientes.setNombreEspecialistaResponsable(fila.getString(14) + " " + fila.getString(15));*/
                //pacienteLista.add(pacientes);

            } while (fila.moveToNext());
        }
        fila.close();

        //Esta líne o métod de abajo permite actualizar la vista en el listview.
        notifyDataSetChanged();

    }


    private void limpiarDatos(){

    }


}
