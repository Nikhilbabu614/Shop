package com.example.shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    Button seller,shop,deliver;
    ImageButton home , bag , customerprofile;
    SharedPreferences sp;

    RecyclerView recyclerView;
    DatabaseReference sdatabase;
    MainShopAdapter myadapter;
    ArrayList<SellerHelper> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seller=findViewById(R.id.seller);
        shop=findViewById(R.id.shop);
        home = findViewById(R.id.home1);
        bag=findViewById(R.id.imageButton2);
        customerprofile=findViewById(R.id.imageButton3);

        seller.setOnClickListener((View v) -> { chooseSellerActivty(); });
        customerprofile.setOnClickListener(v -> chooseActivity());

        sp = getApplicationContext().getSharedPreferences("mySharedPref", Context.MODE_PRIVATE);

        bag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        recyclerView = findViewById(R.id.mainrecyclerView);
        String data = sp.getString("sellerPhoneNumber","");
        sdatabase = FirebaseDatabase.getInstance().getReference("sellers");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myadapter = new MainShopAdapter(this,list);
        recyclerView.setAdapter(myadapter);

        sdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    SellerHelper is = dataSnapshot.getValue(SellerHelper.class);
                    list.add(is);
                }
                myadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void chooseSellerActivty() {
        if("".equals(sp.getString("sellerPhoneNumber",""))){
            Intent intent = new Intent(MainActivity.this,SellerActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(MainActivity.this,SellerProfile.class);
            startActivity(intent);
        }
    }

    private void chooseDeliveryActivty() {
        if("".equals(sp.getString("deliverPhoneNumber",""))){
            Intent intent = new Intent(MainActivity.this,DeliverActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(MainActivity.this,DeliverProfile.class);
            startActivity(intent);
        }
    }

    private void chooseActivity() {
        if("".equals(sp.getString("customerPhoneNumber",""))){
            Intent intent = new Intent(MainActivity.this,CustomerActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(MainActivity.this,CustomerProfile.class);
            startActivity(intent);
        }
    }
}