package com.example.collegepicker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AhpCollegeInput extends AppCompatActivity
{
    EditText college1,college2,college3,college4;
    String cs1=null,cs2=null,cs3=null,cs4=null;
    Button ahpnext;
    List<Collegedetails> list,finallist;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ahp_college_input);
        college1=findViewById(R.id.college1);
        college2=findViewById(R.id.college2);
        college3=findViewById(R.id.college3);
        college4=findViewById(R.id.college4);
        ahpnext=findViewById(R.id.ahpnext);
        finallist=new ArrayList<>();
        final Intent intent=getIntent();
        list=intent.getParcelableArrayListExtra("forahplist");
        ahpnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finallist.clear();
                if (checknull())
                {
                    if(checksame()) {
                        for (Collegedetails collegedetails : list) {

                            if ((collegedetails.code + "").equals(cs1) || (collegedetails.code + "").equals(cs2) || (collegedetails.code + "").equals(cs3) || (collegedetails.code + "").equals(cs4)) {
                                finallist.add(collegedetails);
                            }
                        }
                        if(finallist.size()==4)
                        {
                            Intent intent1=new Intent(AhpCollegeInput.this, AhpProcessing.class);
                            intent1.putExtra("matrix", (Serializable) finallist);
                            startActivity(intent1);
                        }
                        else
                            Toast.makeText(AhpCollegeInput.this,"invalid counseling code detected", Toast.LENGTH_LONG).show();
                    }
                    else
                        Toast.makeText(AhpCollegeInput.this,"Same Colleges Specified Multiple times!!!", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(AhpCollegeInput.this,"null values!!!", Toast.LENGTH_LONG).show();
            }
        });

    }
    boolean checknull()
    {
        try {
            cs1 = college1.getText().toString();
            cs2 = college2.getText().toString();
            cs3 = college3.getText().toString();
            cs4 = college4.getText().toString();
        }
         catch (Exception e) {
        Toast.makeText(AhpCollegeInput.this,e.getMessage(), Toast.LENGTH_SHORT).show();
    }
        boolean i=false;
        if(cs1.isEmpty() || cs2.isEmpty() || cs3.isEmpty() || cs4.isEmpty())
        {
            if(cs1.isEmpty())
                college1.setError("Can't be empty");
            if(cs2.isEmpty())
                college2.setError("Can't be empty");
            if(cs3.isEmpty())
                college3.setError("Can't be empty");
            if(cs4.isEmpty())
                college4.setError("Can't be empty");
            i=false;
        }
        else
            i=true;
        return i;
    }
    boolean checksame()
    {
        if(!cs1.contains(cs2) && !cs2.contains(cs3) && !cs3.contains(cs4) && !cs2.contains(cs4) && !cs1.contains(cs3) &&!cs1.contains(cs4))
        {
            return true;
        }
        return false;
    }
}
