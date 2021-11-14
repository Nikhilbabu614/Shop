package com.example.shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainProductsActivity extends AppCompatActivity {
    TextView shopname,noOfp;
    RecyclerView recyclerView;
    ImageButton cart;

    DatabaseReference sdatabase,odatabase;
    MainProductsAdapter myadapter;
    ArrayList<ItemsHelper> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_products);

        shopname = findViewById(R.id.textView21formp);
        noOfp = findViewById(R.id.textView22);
        cart = findViewById(R.id.imageButtonforcart);
        recyclerView = findViewById(R.id.recyclerViewForMainProducts);

        Intent intent = getIntent();
        String data = intent.getStringExtra("shop_key");

        shopname.setText(intent.getStringExtra("shop_num_key"));

        sdatabase = FirebaseDatabase.getInstance().getReference("shopItems").child(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myadapter = new MainProductsAdapter(this,list);
        recyclerView.setAdapter(myadapter);

        sdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ItemsHelper is = dataSnapshot.getValue(ItemsHelper.class);
                    list.add(is);
                }
                myadapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        String data1="";
//        sdatabase = FirebaseDatabase.getInstance().getReference("Orders").child(data1);
//        odatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        cart.setOnClickListener(v -> {
            Intent intent2 = new Intent(this,CheckoutActivity.class);
            startActivity(intent2);
        });
    }
}