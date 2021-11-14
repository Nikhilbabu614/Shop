package com.example.shop;

import android.content.Context;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ItemShopAdapter extends RecyclerView.Adapter<ItemShopAdapter.MyViewHolder>{

    Context context;

    ArrayList<ItemShopHelper>   list;

    public ItemShopAdapter(Context context, ArrayList<ItemShopHelper> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_inshop,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ItemShopHelper it = list.get(position);
        holder.proname.setText(it.getTitle());
        Glide.with(holder.imageproduct.getContext()).load(it.getImageUrl()).into(holder.imageproduct);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageproduct;
        TextView proname;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageproduct = itemView.findViewById(R.id.itemshopimageset);
            proname = itemView.findViewById(R.id.itemproductname);
        }
    }
}
