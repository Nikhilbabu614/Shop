package com.example.shop;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class customer_join extends Fragment {

    TextInputEditText name ,number,pwd;
    Button csubmit;

    FirebaseDatabase root;
    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_join, container, false);

        name = view.findViewById(R.id.name12);
        number = view.findViewById(R.id.number12);
        pwd = view.findViewById(R.id.password12);
        csubmit = view.findViewById(R.id.createbutton12);

        csubmit.setOnClickListener(v -> {
            root = FirebaseDatabase.getInstance();
            reference = root.getReference("customers");

            String n = name.getText().toString();
            String num =  number.getText().toString();
            String pw = pwd.getText().toString();

            name.setText("");
            number.setText("");
            pwd.setText("");

            Intent intent = new Intent(getContext(),VerifyOTP.class);
            intent.putExtra("phoneNo",num);
            startActivity(intent);

            CustomerHelper c = new CustomerHelper(n,num,pw);
            reference.child(num).setValue(c);
        });
        return view;
    }
}