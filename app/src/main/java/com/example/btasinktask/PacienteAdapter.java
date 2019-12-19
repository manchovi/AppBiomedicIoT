package com.example.btasinktask;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PacienteAdapter extends ArrayAdapter<dto_pacientes> {


    Context mCtx;
    int listLayoutRes;
    List<dto_pacientes> UsersList;

    private ArrayList<dto_pacientes> PacienteList;
    private LayoutInflater inflater;
    private Activity activity;


    public PacienteAdapter(@NonNull Activity activity, int listLayoutRes, @NonNull ArrayList<dto_pacientes> PacienteList) {
    //public PacienteAdapter(@NonNull Context mCtx, int listLayoutRes, @NonNull List<dto_pacientes> PacienteList) {
        super(activity, listLayoutRes, PacienteList);

        this.mCtx = activity;
        this.listLayoutRes = listLayoutRes;
        this.UsersList = PacienteList;

    }


    @Override
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
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);

        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null) {
            //convertView = inflater.inflate(R.layout.activity_lista_pacientes, null);
            convertView = inflater.inflate(listLayoutRes, null);
        }

        final dto_pacientes datos=UsersList.get(position);
        Toast.makeText(mCtx, "datos: ", Toast.LENGTH_SHORT).show();

       /* ArrayList<String> listaEspecialista;               //Va a representar la información que se va a mostrar en el combo
        listaEspecialista = new ArrayList<String>();
        //listaEspecialista = new ArrayList<>();
        //listaEspecialista.add("Seleccione Especialista Responsable");

        String a, b = null, c = null;
        for(int i=0;i<= UsersList.size();i++){
            //listaEspecialista.add(UsersList.get(i).getDui()+" ~ "+UsersList.get(i).getNombres()+" "+UsersList.get(i).getApellidos());
            a = UsersList.get(i).getDui();
            b = UsersList.get(i).getNombres();
            c = UsersList.get(i).getApellidos();
        }*/


        //Toast.makeText(mCtx, ""+position, Toast.LENGTH_SHORT).show();

        //final TextView tvCodigo = (TextView)view.findViewById(R.id.tvCodigo);
        final TextView tvNombres = (TextView)convertView.findViewById(R.id.tvNombres);
        final TextView tvDui = (TextView)convertView.findViewById(R.id.tvDui);
        final TextView tvTelefono = (TextView)convertView.findViewById(R.id.tvTelefono);
        final TextView tvDireccion = (TextView)convertView.findViewById(R.id.tvDireccion);
        final TextView tvFechaHora = (TextView)convertView.findViewById(R.id.tvFechaHora);
        final TextView tvDrResponsable = (TextView)convertView.findViewById(R.id.tvDrResponsable);

        tvNombres.setText(null);
        tvDui.setText(null);
        tvTelefono.setText(null);
        tvDireccion.setText(null);
        tvFechaHora.setText(null);
        tvDrResponsable.setText(null);

        ImageView btnEdit = (ImageView) convertView.findViewById(R.id.btnEdit);
        ImageView btnDelete = (ImageView) convertView.findViewById(R.id.btnDelete);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mCtx, "Clic en botón editar.", Toast.LENGTH_SHORT).show();
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mCtx, "Clic en botón borrar.", Toast.LENGTH_SHORT).show();
            }
        });


        String p = UsersList.get(position).getNombres() + " " + UsersList.get(position).getApellidos();
        //Toast.makeText(mCtx, "Nombre: "+p, Toast.LENGTH_SHORT).show();

        tvNombres.setText(p);
        //tvNombres.setText(datos.getNombres() + " " + datos.getApellidos());
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

        return convertView;

    }

    private void limpiarDatos(){

    }
}
