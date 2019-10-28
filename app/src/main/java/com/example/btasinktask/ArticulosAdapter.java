package com.example.btasinktask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ArticulosAdapter extends RecyclerView.Adapter<ArticulosAdapter.ViewHolder> {

    private Context mCtx;
    private List<Productos> productList;

    public ArticulosAdapter(Context mCtx, List<Productos> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    // Creamos el ViewHolder con la vista de un elemento sin personalizar
    @NonNull
    @Override
    public ArticulosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflamos la vista desde el xml
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_layout, parent, false);
        return new ViewHolder(v);
    }

    // Usando como base el ViewHolder y lo personalizamos
    @Override
    public void onBindViewHolder(@NonNull ArticulosAdapter.ViewHolder holder, int position) {
        Productos product = productList.get(position);
        //loading the image
        Glide.with(mCtx)
                .load(product.getImagen())
                .into(holder.imageView);

        holder.textViewCodigo1.setText(String.valueOf(product.getCodigo()));  //Que barbaridad, ME COSTO DAR CON ESTE SIMPLE ERROR DE CONVERSIÓN.
        holder.textViewDescripcion1.setText(product.getDescripcion());
        holder.textViewPrecio1.setText(String.valueOf(product.getPrecio()));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    //Creamos nuestro ViewHolder, con los tipos de elementos a modificar
    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewCodigo1, textViewDescripcion1, textViewPrecio1;
        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewCodigo1 = (TextView)itemView.findViewById(R.id.textViewCodigo1);
            textViewDescripcion1 = (TextView)itemView.findViewById(R.id.textViewDescripcion1);
            textViewPrecio1= (TextView) itemView.findViewById(R.id.textViewPrecio1);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);

        }
    }


}
