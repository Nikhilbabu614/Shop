package com.example.shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SellerProfile extends AppCompatActivity {
    ImageButton home,profile,edit,add;
    TextView sname;
    ImageView myimagefor;

    RecyclerView sellerre;
    DatabaseReference sdatabase;
    ItemShopAdapter myadapter;
    ArrayList<ItemShopHelper> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_profile);

        home = findViewById(R.id.sellerhome);
        profile = findViewById(R.id.sellerimageButton3);
        edit = findViewById(R.id.shopeditbtn);
        add = findViewById(R.id.sellershopaddbutton);
        sname = findViewById(R.id.sellershopprofilename);

        myimagefor = findViewById(R.id.shopimage);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("mySharedPref", Context.MODE_PRIVATE);
        String data = sp.getString("sellerPhoneNumber","");

        FirebaseDatabase root = FirebaseDatabase.getInstance();
        DatabaseReference reference = root.getReference("sellers");

        Query query = reference.orderByChild("shopNum").equalTo(data);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sname.setText(snapshot.child(data).child("shopName").getValue(String.class));
                Glide.with(getApplicationContext()).load(snapshot.child(data).child("imglink").getValue(String.class)).into(myimagefor);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Sellerlogout.class);
                startActivity(intent);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ShopDetails.class);
                startActivity(intent);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ShopADD.class);
                startActivity(intent);
            }
        });

        sellerre = findViewById(R.id.sellerRecycle);
        sdatabase = FirebaseDatabase.getInstance().getReference("shopItems").child(data);
        sellerre.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myadapter = new ItemShopAdapter(this,list);
        sellerre.setAdapter(myadapter);

        sdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ItemShopHelper is = dataSnapshot.getValue(ItemShopHelper.class);
                    list.add(is);
                }
                myadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}