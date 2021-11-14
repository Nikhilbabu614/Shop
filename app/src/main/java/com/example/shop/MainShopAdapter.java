package com.example.shop;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.nio.file.DirectoryIteratorException;
import java.util.ArrayList;

public class MainShopAdapter extends RecyclerView.Adapter<MainShopAdapter.MyViewHolder> {

    Context context;

    ArrayList<SellerHelper> list;

    public MainShopAdapter(Context context, ArrayList<SellerHelper> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.mainshopitems,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SellerHelper it = list.get(position);
        holder.proname.setText(it.getShopName());
        Glide.with(holder.imageproduct.getContext()).load(it.getImglink()).into(holder.imageproduct);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MainProductsActivity.class);
                intent.putExtra("shop_key",it.getShopNum());
                intent.putExtra("shop_num_key",it.getShopName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageproduct;
        TextView proname;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageproduct = itemView.findViewById(R.id.mainrecyclerViewimage);
            proname = itemView.findViewById(R.id.mainrecyclerViewText);
        }
    }
}
