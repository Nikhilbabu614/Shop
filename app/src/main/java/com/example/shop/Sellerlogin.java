package com.example.shop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Sellerlogin extends Fragment {
    TextInputEditText phone , pwd;
    Button submit;
    SharedPreferences sp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sellerlogin, container, false);
        phone = view.findViewById(R.id.sellerloginnumber);
        pwd = view.findViewById(R.id.sellerloginpassword);
        submit = view.findViewById(R.id.sellerloginbutton);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isUser();
            }
        });


        return view;
    }

    private void isUser() {
        String sn = phone.getText().toString();
        String spwd = pwd.getText().toString();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("sellers");

        sp = this.getActivity().getSharedPreferences("mySharedPref", Context.MODE_PRIVATE);

        Query checkuser = reference.orderByChild("shopNum").equalTo(sn);
        checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String passwordDB = snapshot.child(sn).child("shopNum").getValue(String.class);
                    if(passwordDB.equals(spwd)){
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("sellerPhoneNumber",sn);
                        editor.commit();
                        Intent intent = new Intent(getContext(),MainActivity.class);
                        startActivity(intent);
                    }
                }else{
                    Toast.makeText(getContext(), "Password is incorrect", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}