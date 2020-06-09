package com.example.collegepicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity {
    TextView promail, proaddress, promobile, profname, procity, procuttoff, pro10grade, pro12grade, progender, propincode,prodep;
    FirebaseAuth firebaseAuth;
    String userid;
    SharedPreferences sharedPreferences;
    ProgressDialog pd;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        pd = new ProgressDialog(profile.this);
        pd.setMessage("loading...");
        pd.show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        promail = findViewById(R.id.mailpro);
        proaddress = findViewById(R.id.addresspro);
        promobile = findViewById(R.id.mobilepro);
        profname = findViewById(R.id.fnamepro);
        procity = findViewById(R.id.citypro);
        procuttoff = findViewById(R.id.Cutoffpro);
        pro10grade = findViewById(R.id.per10pro);
        pro12grade = findViewById(R.id.per12pro);
        progender = findViewById(R.id.Genderpro);
        propincode = findViewById(R.id.pincodepro);
        prodep=findViewById(R.id.deppro);
        firebaseAuth = FirebaseAuth.getInstance();
        userid = firebaseAuth.getUid();
        try {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            DatabaseReference reference = databaseReference.child("users").child(userid);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    FullDetails fullDetails = dataSnapshot.getValue(FullDetails.class);
                    if (fullDetails != null)
                    {

                        pd.dismiss();
                        promail.setText("Mail:" + fullDetails.email);
                        proaddress.setText("Address:" + fullDetails.Address);
                        promobile.setText("Mobile:" + fullDetails.Mobilenumber);
                        profname.setText("Name:" + fullDetails.fname + " " + fullDetails.lastname);
                        procity.setText("City:" + fullDetails.city);
                        procuttoff.setText("cutoff:" + fullDetails.cuttoff);
                        pro10grade.setText("10th:" + fullDetails.tenth);
                        pro12grade.setText("12th:" + fullDetails.twelfth);
                        progender.setText("Gender:" + fullDetails.sex);
                        propincode.setText("Pincode:" + fullDetails.pincode);
                        prodep.setText("Department:"+fullDetails.department);
                    } else {
                        pd.dismiss();
                        Toast.makeText(profile.this, "can't able to load data", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    pd.dismiss();
                    Toast.makeText(profile.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e)
        {
            startActivity(new Intent(profile.this,MainActivity.class));
        }
    }

}
