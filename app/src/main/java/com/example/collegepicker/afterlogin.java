package com.example.collegepicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class afterlogin extends AppCompatActivity
{
    FirebaseDatabase mfire;
    DatabaseReference mdatabase;
    FirebaseUser user;
    FirebaseAuth mAuth;
    String userId;
    Button register;
    EditText firstname,lastname,mobile,address,city,pincode,cutoff,tenth,twelfth,email,password,reenterpass;
    Spinner spinner;
    String sfirstname,slastname,smobile,saddress,dep,scity,spincode,scuttoff,sex,mail,pass,rpass;
    float ten,twelve;
    RadioGroup radioGroup;
    RadioButton radioButton;
    ProgressDialog pd;
    SharedPreferences sharedPreferences;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afterlogin);
        register=findViewById(R.id.register);
        firstname=findViewById(R.id.fname);
        lastname=findViewById(R.id.lname);
        mobile=findViewById(R.id.mobile);
        address=findViewById(R.id.address);
        city=findViewById(R.id.city);
        cutoff=findViewById(R.id.cuttoff);
        pincode=findViewById(R.id.pincode);
        tenth=findViewById(R.id.ten);
        twelfth=findViewById(R.id.twelve);
        spinner=findViewById(R.id.spinner);
        email=findViewById(R.id.email1);
        password=findViewById(R.id.password);
        reenterpass=findViewById(R.id.reenter1);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,R.array.department,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        mfire= FirebaseDatabase.getInstance();
        mdatabase= mfire.getReference();
        mAuth=FirebaseAuth.getInstance();
        radioGroup=findViewById(R.id.rg);
        pd = new ProgressDialog(afterlogin.this);
        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                pd.setMessage("Loading...");
                pd.show();
                id= radioGroup.getCheckedRadioButtonId();
                radioButton=findViewById(id);
                if(valid())
                {
                    sharedPreferences=getSharedPreferences("Mycuttoffprefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor  editor= sharedPreferences.edit();
                    editor.putFloat("cutoffprefs",Float.parseFloat(scuttoff));
                    editor.commit();
                    mAuth.createUserWithEmailAndPassword(mail, rpass)
                            .addOnCompleteListener(afterlogin.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful())
                                    {
                                       // startActivity(new Intent(afterlogin.this, MainActivity.class));
                                        mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            if(task.isSuccessful())
                                            {
                                                try {
                                                    userId = mAuth.getCurrentUser().getUid();
                                                    FullDetails fullDetails = new FullDetails(sfirstname, slastname, saddress, smobile, sex, mail, dep, scity, spincode, scuttoff, ten, twelve);
                                                    mdatabase.child("users").child(userId).setValue(fullDetails);
                                                    mAuth.signOut();
                                                    finish();
                                                    pd.dismiss();
                                                    Toast.makeText(afterlogin.this, "registered successfully,email verification send", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(afterlogin.this, MainActivity.class));
                                                }
                                                catch (Exception e)
                                                {
                                                    Toast.makeText(afterlogin.this, "email verification not sent", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                            else {
                                                Toast.makeText(afterlogin.this, "email verification not sent", Toast.LENGTH_SHORT).show();
                                            }

                                            }
                                        });
                                    }
                                    else
                                    {
                                        pd.dismiss();
                                        try {
                                            throw task.getException();
                                        }  catch (FirebaseAuthWeakPasswordException weakPassword)
                                        {
                                            password.setError("weak password");
                                        }
                                        catch (FirebaseAuthInvalidCredentialsException malformedEmail)
                                        {
                                            Toast.makeText(afterlogin.this,malformedEmail.getMessage()+"",Toast.LENGTH_LONG).show();
                                        }
                                        catch (FirebaseAuthUserCollisionException existEmail)
                                        {
                                            email.setError("email already exists");
                                        }
                                        catch (Exception e)
                                        {
                                            Toast.makeText(afterlogin.this,e.getMessage()+"",Toast.LENGTH_LONG).show();
                                        }

                                    }
                                }
                            });
                }
                else {
                    pd.dismiss();
                    Toast.makeText(afterlogin.this,"Cant upload information check if anything empty or wrong",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    boolean valid()
    {
        try {
            sfirstname = firstname.getText().toString();
            slastname = lastname.getText().toString();
            saddress = address.getText().toString();
            smobile = mobile.getText().toString();
            sex = radioButton.getText().toString();
            mail = email.getText().toString();
            dep = spinner.getSelectedItem().toString();
            scity = city.getText().toString();
            spincode = pincode.getText().toString();
            scuttoff = cutoff.getText().toString();
            ten = Float.parseFloat(tenth.getText().toString());
            twelve = Float.parseFloat(twelfth.getText().toString());
            pass = password.getText().toString();
            rpass = reenterpass.getText().toString();
        }
        catch (Exception e) {
            Toast.makeText(afterlogin.this, "null vales!!!", Toast.LENGTH_SHORT).show();
        }
        boolean i=false;
        if(sfirstname.isEmpty() || slastname.isEmpty()|| saddress.isEmpty() || smobile.isEmpty()  || mail.isEmpty() || dep.isEmpty() || scity.isEmpty() || spincode.isEmpty() || scuttoff.isEmpty() ||tenth.getText().toString().isEmpty() || twelfth.getText().toString().isEmpty() || mail.isEmpty() || pass.isEmpty() || rpass.isEmpty())
        {
          i= false;
        }
        else if(!pass.equals(rpass))
        {
            reenterpass.setError("Not matching");
            i=false;
        }
        else {
           i=true;
        }
        return i;
    }
}
