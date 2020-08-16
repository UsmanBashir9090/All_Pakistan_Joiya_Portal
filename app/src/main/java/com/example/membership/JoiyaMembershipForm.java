package com.example.membership;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class JoiyaMembershipForm extends AppCompatActivity {
    public static final String TAG = "TAG";
    public static final String TAG1 = "TAG";
    public boolean form = false;
    EditText profileFullName, profileEmail, profilePhone, profileFathersName, profileCNIC, profileEducation, profileProfession, profileDesignation, profileAddress, profileCity;
    ImageView profileImageView;
    Button saveBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    StorageReference storageReference;
    String userId;
    ProgressBar saveProgressBar;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener onDateSetListener,mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joiya_membership_form);
        Toast.makeText(this, "Form opened.", Toast.LENGTH_SHORT).show();

        Intent data = getIntent();
        final String fullName = data.getStringExtra("fName");
        String email = data.getStringExtra("email");
        final String phone = data.getStringExtra("phone");

        mDisplayDate = (TextView) findViewById(R.id.profileDOB);
        profileProfession = findViewById(R.id.profileProfession);
        profileFathersName = findViewById(R.id.profileFathersName);
        profileCNIC = findViewById(R.id.profileCNIC);
        profileEducation = findViewById(R.id.profileEducation);
        profileDesignation = findViewById(R.id.profileDesignation);
        profileAddress = findViewById(R.id.profileAddress);
        profileCity = findViewById(R.id.profileCity);
        profileFullName = findViewById(R.id.profileFullName);
        profileEmail = findViewById(R.id.profileEmailAddress);
        profilePhone = findViewById(R.id.profilePhoneNo);
        saveProgressBar = findViewById(R.id.progressBarForm);
        saveBtn = findViewById(R.id.saveProfileInfo);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dialog = new DatePickerDialog(JoiyaMembershipForm.this, android.R.style.Theme_Black, mDateSetListener, year,month,day );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

            mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month = month + 1;
                    Log.d(TAG, "onDateSet: mm/dd/yyy: " + day + "/" + month + "/" + year);

                    String date = month +"/" + day +"/" + year ;
                    mDisplayDate.setText(date);
                }
            };

        final DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                profilePhone.setText(documentSnapshot.getString("phone"));
                profileFullName.setText(documentSnapshot.getString("fName"));
                profileEmail.setText(documentSnapshot.getString("email"));
            }
        });



        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              final String fathersName = profileFathersName.getText().toString();
              final String CNIC = profileCNIC.getText().toString().trim();
              final String education = profileEducation.getText().toString().trim();
              final String profession = profileProfession.getText().toString();
              final String designation = profileDesignation.getText().toString().trim();
              final String address = profileAddress.getText().toString().trim();
              final String city = profileCity.getText().toString().trim();
              final String dob = mDisplayDate.getText().toString();


              if(TextUtils.isEmpty(fathersName) ){
                  profileFathersName.setError("Father's Name is required");
              }
                if(TextUtils.isEmpty(dob) ){
                    mDisplayDate.setError("Date of Birth is required");
                }
              if(TextUtils.isEmpty(CNIC) ){
                  profileCNIC.setError("CNIC is required");
              }
              if(CNIC.length() != 13){
                  profileCNIC.setError("CNIC length should be 13 digits");
              }
              if(TextUtils.isEmpty(education)){
                  profileEducation.setError("Education is required");
              }
              if(TextUtils.isEmpty(profession) ){
                  profileProfession.setError("Profession is required");
              }
              if(TextUtils.isEmpty(designation)){
                  profileDesignation.setError("Designation is required");
              }
              if(TextUtils.isEmpty(address)){
                  profileAddress.setError("Address is required");
              }
              if(TextUtils.isEmpty(city)){
                  profileCity.setError("City is required");
              }
                else {
                  saveProgressBar.setVisibility(View.VISIBLE);

                  userId = fAuth.getCurrentUser().getUid();
                  DocumentReference documentReference = fStore.collection("users").document(userId);
                  Map<String, Object> user = new HashMap<>();
                  user.put("fName", profileFullName.getText().toString());
                  user.put("email", profileEmail.getText().toString());
                  user.put("phone", profilePhone.getText().toString());
                  user.put("fatherName", fathersName);
                  user.put("CNIC", CNIC);
                  user.put("education", education);
                  user.put("profession", profession);
                  user.put("designation", designation);
                  user.put("address", address);
                  user.put("city", city);
                  user.put("date_of_birth", dob);

                  documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                      @Override
                      public void onSuccess(Void aVoid) {
                          Toast.makeText(JoiyaMembershipForm.this, "Successfully Submitted Form.", Toast.LENGTH_SHORT).show();
                          Log.d(TAG, "onSuccess: Form successfully created " + userId);
                          saveProgressBar.setVisibility(View.GONE);

                      }
                  }).addOnFailureListener(new OnFailureListener() {
                      @Override
                      public void onFailure(@NonNull Exception e) {
                          Toast.makeText(JoiyaMembershipForm.this, "Form Submission Failed.", Toast.LENGTH_SHORT).show();
                          Log.d(TAG1, "onFailure: " + e.toString());
                      }
                  });
                    
                  startActivity(new Intent(getApplicationContext(), MainActivity.class));



              }
            }
        });

    }
}