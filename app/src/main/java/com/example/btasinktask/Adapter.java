package com.example.btasinktask;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ProductosViewHolder> {
    private String[] mDataset;

    private Context mCtx;
    private List<Productos> productosList;


    public Adapter(Context mCtx, List<Productos> productosList) {
        this.mCtx = mCtx;
        this.productosList = productosList;
    }


    @Override
    public ProductosViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // create a new view
        /*
        View v = LayoutInflater.from(mCtx).inflate(R.layout.list_layout, viewGroup, false) ;
        ProductosViewHolder pvh = new ProductosViewHolder((TextView) v);
        return pvh;
        */



        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_layout, viewGroup, false);
        return new ProductosViewHolder(v);


        /*
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_layout, null);
        return new ProductosViewHolder(view);*/

    }


    @Override
    public void onBindViewHolder(ProductosViewHolder holder, int position) {

        Productos productos = productosList.get(position);
        //Carga de imagenes.
        Glide.with(mCtx)
                .load(productos.getImagen())
                .into(holder.imageView);
        holder.textViewCodigo1.setText(productos.getCodigo());
        holder.textViewDescripcion1.setText(productos.getDescripcion());
        holder.textViewPrecio1.setText(String.valueOf(productos.getPrecio()));
    }



    public class ProductosViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textViewCodigo1, textViewDescripcion1, textViewPrecio1;
        public ImageView imageView;

        public ProductosViewHolder(View view) {
            super(view);

            imageView = (ImageView)view.findViewById(R.id.imageView);
            textViewCodigo1 = (TextView)view.findViewById(R.id.textViewCodigo1);
            textViewDescripcion1 = (TextView)view.findViewById(R.id.textViewDescripcion1);
            textViewPrecio1 = (TextView)view.findViewById(R.id.textViewPrecio1);

        }
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return productosList.size();
    }
}
