package com.example.shop;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MainProductsAdapter extends RecyclerView.Adapter<MainProductsAdapter.MyViewHolder> {

    Context context;

    ArrayList<ItemsHelper> list;

    public MainProductsAdapter(Context context, ArrayList<ItemsHelper> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.main_products_recycler,parent,false);
        return new MyViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull MainProductsAdapter.MyViewHolder holder, int position) {

        SharedPreferences sp = context.getSharedPreferences("mySharedPref", Context.MODE_PRIVATE);

        ItemsHelper it = list.get(position);
        holder.proname.setText(it.getTitle());
        holder.Originalprice.setText(it.getOriginal());
        holder.Originalprice.setPaintFlags(holder.Originalprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.offerPrice.setText(it.getOffer());
        Glide.with(holder.imageproduct.getContext()).load(it.getImageUrl()).into(holder.imageproduct);

        holder.removeproduct.setOnClickListener(v -> {
            OrdersHelper helper = new OrdersHelper(sp.getString("sellerPhoneNumber",""),"","",null,false);
        });
        holder.addproduct.setOnClickListener(v -> {
            OrdersHelper helper = new OrdersHelper(sp.getString("sellerPhoneNumber",""),"","",null,false);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageproduct;
        TextView proname,offerPrice,Originalprice;
        ImageButton removeproduct,addproduct;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageproduct = itemView.findViewById(R.id.mainrecyclerViewimageformainproducts);
            proname = itemView.findViewById(R.id.mainrecyclerViewTextformainproducts);
            Originalprice = itemView.findViewById(R.id.textView23formainprod);
            offerPrice = itemView.findViewById(R.id.textView21formainp);
            removeproduct = itemView.findViewById(R.id.removeimagebtnformp);
            addproduct = itemView.findViewById(R.id.imageButtonaddimgbtnformmp);
        }
    }
}
