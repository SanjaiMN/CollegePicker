package com.example.collegepicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class passwordreset extends AppCompatActivity {
EditText passwordresetemail;
Button reset;
FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwordreset);
        passwordresetemail=findViewById(R.id.resetpassmail);
        reset=findViewById(R.id.resetpassbt);
        firebaseAuth=FirebaseAuth.getInstance();
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String mailid = passwordresetemail.getText().toString().trim();
                if(mailid.isEmpty())
                {
                    passwordresetemail.setError("Can't be empty");
                }
                else {
                    firebaseAuth.sendPasswordResetEmail(mailid).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(passwordreset.this,"link sent to email",Toast.LENGTH_SHORT).show();
                               finish();
                            }
                            else {
                                Toast.makeText(passwordreset.this, "Error,invalid email!!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}
