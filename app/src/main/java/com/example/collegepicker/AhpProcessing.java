package com.example.collegepicker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AhpProcessing extends AppCompatActivity {
   float a[][] = new float[4][4];
   float old[][] = new float[4][4];
    float matrixarr[][]=new float[4][4];
    List<Collegedetails> matrixlist,answerlist;
    List<Float> graph;
    HashMap<Collegedetails,Float> answer,hm1;
    Spinner spinner1, spinner2, spinner3, spinner4, spinner5, spinner6;
    Button result;
    float values[] = new float[6];
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ahp_processing);
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);
        spinner4 = findViewById(R.id.spinner4);
        spinner5 = findViewById(R.id.spinner5);
        spinner6 = findViewById(R.id.spinner6);
        matrixlist= new ArrayList<>();
        answerlist=new ArrayList<>();
        graph=new ArrayList<>();
        answer=new HashMap<>();
        hm1=new HashMap<>();
        result = findViewById(R.id.result);
        final Intent intent = getIntent();
        matrixlist=intent.getParcelableArrayListExtra("matrix");
        initialize(matrixlist);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.Ahpvalues, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(arrayAdapter);
        spinner2.setAdapter(arrayAdapter);
        spinner3.setAdapter(arrayAdapter);
        spinner4.setAdapter(arrayAdapter);
        spinner5.setAdapter(arrayAdapter);
        spinner6.setAdapter(arrayAdapter);
        result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                switchcase();
                initializepairwisematrix(values);
                float first=0,second=0,third=0 ,fourth=0;
                for(int i=0;i<4;i++)
                {
                    first+=a[i][0];
                    second+=a[i][1];
                    third+=a[i][2];
                    fourth+=a[i][3];
                }
                for(int i=0; i<a.length; i++)
                {
                    for(int j=0; j<a.length;j++)
                    {
                        if(j==0)
                            a[i][j]=a[i][j]/first;
                        if(j==1)
                            a[i][j]=a[i][j]/second;
                        if(j==2)
                            a[i][j]=a[i][j]/third;
                        if(j==3)
                            a[i][j]=a[i][j]/fourth;
                    }
                }
                float pairwise1=0,pairwise2=0,pairwise3=0,pairwise4=0;
                for(int i=0; i<a.length; i++)
                {
                    for(int j=0; j<a.length;j++)
                    {
                        if(i==0)
                            pairwise1+=a[i][j];
                        if(i==1)
                            pairwise2+=a[i][j];
                        if(i==2)
                            pairwise3+=a[i][j];
                        if(i==3)
                            pairwise4+=a[i][j];
                    }
                }
                pairwise1=pairwise1/4;
                pairwise2=pairwise2/4;
                pairwise3=pairwise3/4;
                pairwise4=pairwise4/4;
                for(int i=0; i<old.length; i++)
                {
                    for(int j=0; j<old.length;j++)
                    {
                        if(j==0)
                            old[i][j]=old[i][j]*pairwise1;
                        if(j==1)
                            old[i][j]=old[i][j]*pairwise2;
                        if(j==2)
                            old[i][j]=old[i][j]*pairwise3;
                        if(j==3)
                            old[i][j]=old[i][j]*pairwise4;
                    }
                }
                float wsv1=0,wsv2=0,wsv3=0,wsv4=0;
                for(int i=0; i<old.length; i++)
                {
                    for(int j=0; j<old.length;j++)
                    {
                        if(i==0)
                            wsv1+=old[i][j];
                        if(i==1)
                            wsv2+=old[i][j];
                        if(i==2)
                            wsv3+=old[i][j];
                        if(i==3)
                            wsv4+=old[i][j];
                    }
                }
                float lambda1,lambda2,lambda3,lambda4;
                lambda1=wsv1/pairwise1;
                lambda2=wsv2/pairwise2;
                lambda3=wsv3/pairwise3;
                lambda4=wsv4/pairwise4;
                float lambdamax=(lambda1+lambda2+lambda3+lambda4)/4;
                float ci,cr;
                ci=(lambdamax-4)/3;
                cr=(float) (ci/0.90);
                float one=0,two=0,three=0,four=0;
                if(cr<0.10)
                {
                    initialize(matrixlist);
                    for(int i=0; i<matrixarr.length; i++)
                    {
                        for(int j=0; j<matrixarr.length;j++)
                        {
                            if(j==0)
                                matrixarr[i][j]=matrixarr[i][j]*pairwise1;
                            if(j==1)
                                matrixarr[i][j]=matrixarr[i][j]*pairwise2;
                            if(j==2)
                                matrixarr[i][j]=matrixarr[i][j]*pairwise3;
                            if(j==3)
                                matrixarr[i][j]=matrixarr[i][j]*pairwise4;
                        }
                    }
                    for(int i=0; i<matrixarr.length; i++)
                    {
                        for(int j=0; j<matrixarr.length;j++)
                        {
                            if(i==0)
                                one+=matrixarr[i][j];
                            if(i==1)
                                two+=matrixarr[i][j];
                            if(i==2)
                                three+=matrixarr[i][j];
                            if(i==3)
                                four+=matrixarr[i][j];
                        }
                    }
                    answer.put(matrixlist.get(0),one);
                    answer.put(matrixlist.get(1),two);
                    answer.put(matrixlist.get(2),three);
                    answer.put(matrixlist.get(3),four);
                    hm1 = sortByValue(answer);
                    graph.add(pairwise1);
                    graph.add(pairwise2);
                    graph.add(pairwise3);
                    graph.add(pairwise4);
                    for (Map.Entry<Collegedetails, Float> en : hm1.entrySet())
                    {
                       answerlist.add(en.getKey());
                    }
                    Intent intent1 =new Intent(AhpProcessing.this,AhpResult.class);
                    intent1.putExtra("finalresults", (Serializable) answerlist);
                    intent1.putExtra("graphresults",(Serializable) graph);
                    startActivity(intent1);
                // Toast.makeText(AhpProcessing.this,answerlist.get(0).name+" "+answerlist.get(1).name+" "+answerlist.get(2).name,Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(AhpProcessing.this,"Inconistent selection change your inputs",Toast.LENGTH_SHORT).show();
            }
        });

    }
     void initializepairwisematrix(float p[]) {
        int k = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == j) {
                    a[i][j] = 1;
                    old[i][j] = 1;
                } else if (i < j) {
                    a[i][j] = p[k];
                    old[i][j] = p[k];
                    k++;
                } else if (i > j) {
                    old[i][j] = 1 / old[j][i];
                    a[i][j] = 1 / a[j][i];
                }
            }
        }
    }
    void initialize(List<Collegedetails> matrixlist)
    {
        matrixarr[0][0]=matrixlist.get(0).placment;
        matrixarr[0][1]=matrixlist.get(0).infrastructure;
        matrixarr[0][2]=matrixlist.get(0).academics;
        matrixarr[0][3]=matrixlist.get(0).hostel;
        matrixarr[1][0]=matrixlist.get(1).placment;
        matrixarr[1][1]=matrixlist.get(1).infrastructure;
        matrixarr[1][2]=matrixlist.get(1).academics;
        matrixarr[1][3]=matrixlist.get(1).hostel;
        matrixarr[2][0]=matrixlist.get(2).placment;
        matrixarr[2][1]=matrixlist.get(2).infrastructure;
        matrixarr[2][2]=matrixlist.get(2).academics;
        matrixarr[2][3]=matrixlist.get(2).hostel;
        matrixarr[3][0]=matrixlist.get(3).placment;
        matrixarr[3][1]=matrixlist.get(3).infrastructure;
        matrixarr[3][2]=matrixlist.get(3).academics;
        matrixarr[3][3]=matrixlist.get(3).hostel;
        float one,two,three,four;
        one=matrixarr[0][0];
        two=matrixarr[0][1];
        three=matrixarr[0][2];
        four=matrixarr[0][3];
        for(int i=1;i<matrixarr.length;i++)
        {
           if(one<matrixarr[i][0])
           {
               one=matrixarr[i][0];
           }
            if(two<matrixarr[i][1])
            {
                two=matrixarr[i][1];
            }
            if(three<matrixarr[i][2])
            {
                three=matrixarr[i][2];
            }
            if(four<matrixarr[i][3])
            {
                four=matrixarr[i][3];
            }
        }
        for(int i=0; i<matrixarr.length; i++)
        {
            for(int j=0; j<matrixarr.length;j++)
            {
                if(j==0)
                    matrixarr[i][j]=matrixarr[i][j]/one;
                if(j==1)
                    matrixarr[i][j]=matrixarr[i][j]/two;
                if(j==2)
                    matrixarr[i][j]=matrixarr[i][j]/three;
                if(j==3)
                    matrixarr[i][j]=matrixarr[i][j]/four;
            }
        }
    }
    public void  switchcase()
    {
        values[0] = spinnervalue(spinner1.getSelectedItem().toString());
        values[1] = spinnervalue(spinner2.getSelectedItem().toString());
        values[2] = spinnervalue(spinner3.getSelectedItem().toString());
        values[3] = spinnervalue(spinner4.getSelectedItem().toString());
        values[4] = spinnervalue(spinner5.getSelectedItem().toString());
        values[5] = spinnervalue(spinner6.getSelectedItem().toString());
    }
    float spinnervalue(String name)
    {
        float ans=0;
        switch (name)
        {
            case "Equal Importance(1)": {
                ans = 1;
                break;
            }
            case "Moderate Importance(3)": {
                ans = 3;
                break;
            }
            case "Strong Importance(5)": {
                ans = 5;
                break;
            }
            case "Very Strong Importance(7)": {
                ans = 7;
                break;
            }
            case "Extreme Importance(9)": {
                ans = 9;
                break;
            }
            case "Inverse Moderate Importance(1/3)": {
                ans = (float) 0.33;
                break;
            }
            case "Inverse Strong Importance(1/5)": {
                ans = (float) 0.20;
                break;
            }
            case "Inverse Very Strong Importance(1/7)": {
                ans = (float) 0.14;
                break;
            }
            case "Inverse Extreme Importance(1/9)": {
                ans = (float) 0.11;
                break;
            }
        }
        return ans;
    }
    public static HashMap<Collegedetails, Float> sortByValue(HashMap<Collegedetails, Float> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<Collegedetails, Float> > list =
                new LinkedList<Map.Entry<Collegedetails,  Float> >(hm.entrySet());
        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<Collegedetails, Float> >() {
            public int compare(Map.Entry<Collegedetails,  Float> o1,
                               Map.Entry<Collegedetails,  Float> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<Collegedetails,  Float> temp = new LinkedHashMap<Collegedetails,  Float>();
        for (Map.Entry<Collegedetails, Float> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}
