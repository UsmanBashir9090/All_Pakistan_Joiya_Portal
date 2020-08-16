package com.example.membership;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

public class pending_list_details extends AppCompatActivity {

    TextView fullName, Email, uid, Phone, fathersName, CNIC, address, city, profession, designation, education, status, timestamp, dob;
    FirebaseUser user;
    DatabaseReference database, ref;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_list_details);



        Phone = findViewById(R.id.textPhone);
        fullName = findViewById(R.id.textName);
        Email = findViewById(R.id.textEmail);
        fathersName = findViewById(R.id.profileFathersName);
        CNIC = findViewById(R.id.profileCNIC);
        address = findViewById(R.id.profileAddress);
        city = findViewById(R.id.profileCity);
        profession = findViewById(R.id.profileProfession);
        designation = findViewById(R.id.profileDesignation);
        education = findViewById(R.id.profileEducation);
        fathersName = findViewById(R.id.fathersname);
        status = findViewById(R.id.status);
        timestamp = findViewById(R.id.timestamp);
        dob = findViewById(R.id.dob);
        uid = findViewById(R.id.uid);

        final String key = getIntent().getExtras().get("key").toString();

        fullName.setText(getIntent().getStringExtra("name"));
        Email.setText(getIntent().getStringExtra("email"));
        city.setText(getIntent().getStringExtra("city"));
        Phone.setText(getIntent().getStringExtra("phone"));
        address.setText(getIntent().getStringExtra("address"));
        designation.setText(getIntent().getStringExtra("designation"));
        education.setText(getIntent().getStringExtra("education"));
        status.setText(getIntent().getStringExtra("status"));
        uid.setText(key);
        timestamp.setText(getIntent().getStringExtra("timestamp"));
        profession.setText(getIntent().getStringExtra("profession"));
        dob.setText(getIntent().getStringExtra("dob"));
        CNIC.setText(getIntent().getStringExtra("cnic"));
        fathersName.setText(getIntent().getStringExtra("fathername"));
    }



    public void buttonaccept(View view) {
        final String key = getIntent().getExtras().get("key").toString();
        ref = FirebaseDatabase.getInstance().getReference().child("users").child(key);
        ref.child("status1").setValue("Approved");
        ref.child("status").setValue("You are now a Member");
        Toast.makeText(getApplicationContext(), "Application Approved", Toast.LENGTH_SHORT).show();
        finish();
    }

}