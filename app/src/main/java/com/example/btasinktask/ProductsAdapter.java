package com.example.btasinktask;

import android.content.Context;
//import android.support.v7.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import java.util.List;


public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {


    private Context mCtx;
    private List<Productos> productList;

    public ProductsAdapter(Context mCtx, List<Productos> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_layout, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Productos product = productList.get(position);

        //loading the image
        Glide.with(mCtx)
                .load(product.getImagen())
                .into(holder.imageView);

        holder.textViewCodigo1.setText(product.getCodigo());
        holder.textViewDescripcion1.setText(product.getDescripcion());
        holder.textViewPrecio1.setText(String.valueOf(product.getPrecio()));

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewCodigo1, textViewDescripcion1, textViewPrecio1;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewCodigo1 = itemView.findViewById(R.id.textViewCodigo1);
            textViewDescripcion1 = itemView.findViewById(R.id.textViewDescripcion1);
            textViewPrecio1= itemView.findViewById(R.id.textViewPrecio1);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }


}
