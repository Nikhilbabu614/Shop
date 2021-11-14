package com.example.shop;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class DeliverJoin extends Fragment {
    TextInputEditText phone;
    EditText pwd;
    Button bjoin;

    FirebaseDatabase root;
    DatabaseReference reference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_deliver_join, container, false);

        phone = view.findViewById(R.id.djoinnumber);
        pwd = view.findViewById(R.id.djoinpassword);
        bjoin = view.findViewById(R.id.djoinbutton);

        bjoin.setOnClickListener(v -> {
            root = FirebaseDatabase.getInstance();
            reference = root.getReference("deliveryBoys");

            String p = phone.getText().toString();
            String pw = pwd.getText().toString();

            phone.setText("");
            pwd.setText("");

            Intent intent = new Intent(getContext(),verifyOTP2.class);
            intent.putExtra("phoneNo",p);
            startActivity(intent);

            DeliverHelper c = new DeliverHelper(p,pw);
            reference.child(p).setValue(c);

        });

        return  view;
    }
}