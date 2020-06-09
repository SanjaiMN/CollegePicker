package com.example.collegepicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity
{
    private EditText username;
    private EditText password;
    private TextView admin;
    private Button login;
    private TextView forgotpassword;
    private TextView newuser;
    FirebaseUser User;
    private FirebaseAuth mAuth;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username=(EditText)findViewById(R.id.emailLogin);
        password=(EditText)findViewById(R.id.passLogin);
        login=(Button)findViewById(R.id.LoginB);
        forgotpassword=(TextView)findViewById(R.id.forgotpassword);
        newuser=(TextView)findViewById(R.id.switchsignup);
        mAuth = FirebaseAuth.getInstance();
        User= mAuth.getCurrentUser();
        if (User!= null) {
            startActivity(new Intent(MainActivity.this, collegelist.class));
        }
        pd=new ProgressDialog(this);
        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this,afterlogin.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Loading...");
                pd.show();
                String email = username.getText().toString();
                String pass = password.getText().toString();
                if (email.isEmpty() || pass.isEmpty()) {
                    pd.dismiss();
                    if (email.isEmpty())
                        username.setError("cant be empty");
                    if (pass.isEmpty())
                        password.setError("cant be empty");
                } else {
                    mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                pd.dismiss();
                                emailverification();
                            } else {
                                pd.dismiss();
                                Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this,passwordreset.class));
            }
        });
    }
    private void emailverification()
    {
        FirebaseUser firebaseUser = mAuth.getInstance().getCurrentUser();
        if(firebaseUser.isEmailVerified())
        {
            Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, collegelist.class));
        }
        else
        {
            Toast.makeText(MainActivity.this, "Email not verified", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
        }
    }

    @Override
    public void onBackPressed()
    {
        moveTaskToBack(true);
    }
}
