package com.example.collegepicker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class InsideRecyclerView extends AppCompatActivity {
TextView collegename,code,location,placement,infrastructure,academics,hostel,mincutoff,link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside_recycler_view);
        collegename=findViewById(R.id.insidecollegename);
        code=findViewById(R.id.insidecode);
        location=findViewById(R.id.insidelocation);
        placement=findViewById(R.id.insideplacement);
        infrastructure=findViewById(R.id.insideinfra);
        academics=findViewById(R.id.insideacademics);
        hostel=findViewById(R.id.insidehostel);
        mincutoff=findViewById(R.id.insidemincutoff);
        link=findViewById(R.id.websitelink);
        Intent intent=getIntent();
        Collegedetails collegedetails=intent.getParcelableExtra("collegeclick");
        collegename.setText(collegedetails.name);
        code.setText(String.valueOf(collegedetails.code));
        location.setText(collegedetails.city);
        placement.setText("Placement: "+collegedetails.placment);
        infrastructure.setText("Infrastructure: "+collegedetails.infrastructure);
        academics.setText("academics: "+collegedetails.academics);
        hostel.setText("hostel: "+collegedetails.hostel);
        mincutoff.setText("Min.cutoff: "+collegedetails.mincutoff);
        link.setText(collegedetails.link);
    }
}
