package com.example.membership;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.membership.R;
import com.example.membership.memberData;
import com.example.membership.pending_list_details;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class pending_list extends AppCompatActivity {
    ListView lv;
    FirebaseListAdapter adapter2;
    DatabaseReference databaseReference, db;
    String itemKey;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_list);
        this.setTitle("Pending Requests");

        lv=(ListView) findViewById(R.id.LV);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        Query query = databaseReference.orderByChild("status1").equalTo("pending");

        FirebaseListOptions<memberData> options= new FirebaseListOptions.Builder<memberData>()
                .setLayout(R.layout.allmemberdata)
                .setQuery(query, memberData.class)
                .build();




        adapter2= new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                DatabaseReference itemRef = getRef(position);
                itemKey = itemRef.getKey();
                TextView name= v.findViewById(R.id.name);
                TextView email= v.findViewById(R.id.status);
                TextView phone= v.findViewById(R.id.phone);


                memberData user = (memberData) model;
                name.setText("Member Name: " + user.getName());
                phone.setText("Contact No. : " + user.getPhone());
                email.setText("Status: " + user.getStatus1());

            }
        };
        lv.setAdapter(adapter2);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position,
                                    long id) {
                Intent Deletepdate=  new Intent(pending_list.this, pending_list_details.class);
                memberData user= (memberData) adapterView.getItemAtPosition(position);
                DatabaseReference itemRef = adapter2.getRef(position);
                String itemKe = itemRef.getKey();
                Deletepdate.putExtra("email", user.getEmail());
                Deletepdate.putExtra("name", user.getName());
                Deletepdate.putExtra("key", itemKe);
                Deletepdate.putExtra("phone", user.getPhone());
                Deletepdate.putExtra("address", user.getAddress());
                Deletepdate.putExtra("city", user.getCity());
                Deletepdate.putExtra("fathername", user.getFatherName());
                Deletepdate.putExtra("cnic", user.getCNIC());
                Deletepdate.putExtra("designation", user.getDesignation());
                Deletepdate.putExtra("dob", user.getProfileDob());
                Deletepdate.putExtra("status", user.getStatus1());
                Deletepdate.putExtra("education", user.getEducation());
                Deletepdate.putExtra("profession", user.getProfession());
                Deletepdate.putExtra("timestamp", user.getTimestamp());


                startActivity(Deletepdate);
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter2.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter2.stopListening();
    }



}