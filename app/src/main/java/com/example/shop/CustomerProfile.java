package com.example.shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class CustomerProfile extends AppCompatActivity {
    TextView textView,textView1;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);
        textView = findViewById(R.id.textView13);
        textView1 = findViewById(R.id.textView15);
        logout=findViewById(R.id.customerlogoutbutton);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("mySharedPref", Context.MODE_PRIVATE);
        String data = sp.getString("customerPhoneNumber","");

        FirebaseDatabase root = FirebaseDatabase.getInstance();
        DatabaseReference reference = root.getReference("customers");
        Query query = reference.orderByChild("customerNumber").equalTo(data);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                textView.setText(snapshot.child(data).child("customerName").getValue(String.class));
                textView1.setText(snapshot.child(data).child("customerNumber").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        logout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("customerPhoneNumber","");
            editor.commit();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        });
    }
}