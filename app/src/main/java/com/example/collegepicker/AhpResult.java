package com.example.collegepicker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class AhpResult extends AppCompatActivity
{
TextView college1,college2,college3,college4,green,red;
List<Collegedetails> list=new ArrayList<>();
ImageButton imageButton;
ArrayList<? extends Float> graph = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ahp_result);
        college1=findViewById(R.id.college1);
        college2=findViewById(R.id.college2);
        college3=findViewById(R.id.college3);
        college4=findViewById(R.id.college4);
        green=findViewById(R.id.green);
        red=findViewById(R.id.red);
        green.setTextColor(Color.GREEN);
        red.setTextColor(Color.RED);
        imageButton=findViewById(R.id.graph);
        final Intent intent=getIntent();
        list=intent.getParcelableArrayListExtra("finalresults");
        graph=intent.getParcelableArrayListExtra("graphresults");
        SharedPreferences sharedPreferences = getSharedPreferences("Mycuttoffprefs",MODE_PRIVATE);
        Float cutoff = sharedPreferences.getFloat("cutoffprefs",0);
        college1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AhpResult.this,InsideRecyclerView.class);
                intent1.putExtra("collegeclick",list.get(3));
                startActivity(intent1);
            }
        });
        college2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AhpResult.this,InsideRecyclerView.class);
                intent1.putExtra("collegeclick",list.get(2));
                startActivity(intent1);
            }
        });
        college3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AhpResult.this,InsideRecyclerView.class);
                intent1.putExtra("collegeclick",list.get(1));
                startActivity(intent1);
            }
        });
        college4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AhpResult.this,InsideRecyclerView.class);
                intent1.putExtra("collegeclick",list.get(0));
                startActivity(intent1);
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent1 = new Intent(AhpResult.this,GraphActivity.class);
                intent1.putExtra("graphresults",graph);
                startActivity(intent1);
            }
        });
        if(cutoff<list.get(3).mincutoff)
        {
            college1.setText(list.get(3).name);
            college1.setTextColor(Color.RED);
        }
        else
        {
            college1.setText(list.get(3).name);
            college1.setTextColor(Color.GREEN);
        }
        if(cutoff<list.get(2).mincutoff)
        {
            college2.setText(list.get(2).name);
            college2.setTextColor(Color.RED);
        }
        else {
            college2.setText(list.get(2).name);
            college2.setTextColor(Color.GREEN);
        }
        if(cutoff<list.get(1).mincutoff) {
            college3.setText(list.get(1).name);
            college3.setTextColor(Color.RED);
        }
        else {
            college3.setText(list.get(1).name);
            college3.setTextColor(Color.GREEN);
        }
        if(cutoff<list.get(0).mincutoff)
        {
            college4.setText(list.get(0).name);
            college4.setTextColor(Color.RED);
        }
        else {
            college4.setText(list.get(0).name);
            college4.setTextColor(Color.GREEN);
        }
    }
}
