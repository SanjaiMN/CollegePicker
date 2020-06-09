package com.example.collegepicker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import java.util.ArrayList;
public class GraphActivity extends AppCompatActivity
{
    private float[] yData=new float[4];
    private String[] xData={"Placement","Infrastructure","Academics","Hostel"};
    PieChart pieChart;
    ArrayList<? extends Float> graph=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        Intent intent=getIntent();
        graph=  intent.getParcelableArrayListExtra("graphresults");
        yData[0]=graph.get(0)*100;
        yData[1]=graph.get(1)*100;
        yData[2]=graph.get(2)*100;
        yData[3]=graph.get(3)*100;
        pieChart=findViewById(R.id.piechart);
        pieChart.setRotationEnabled(true);
        pieChart.setExtraOffsets(5,10,5,5);
        pieChart.setUsePercentValues(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawCenterText(true);
        pieChart.setRotationAngle(0);
        Description description=new Description();
        description.setText("Pie chart to show attribute weights");
        description.setTextSize(10);
        pieChart.setDescription(description);
        pieChart.setHoleRadius(55f);
        pieChart.animateY(1000, Easing.EaseInCirc);
        //pieChart.animateX(1000,Easing.EaseInExpo);
        pieChart.setEntryLabelColor(Color.DKGRAY);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setCenterText("For 100%");
        pieChart.setCenterTextSize(50);
        pieChart.setDrawEntryLabels(true);
        final GraphActivity graph1=this;


        addDataSet();
    }

    private void addDataSet() {

        ArrayList<PieEntry> yentries=new ArrayList<>();
        ArrayList<String> xentries=new ArrayList<>();

        for(int i=0;i<yData.length;i++)
        {
            yentries.add(new PieEntry(yData[i],i));
        }
        for(int i=0;i<xData.length;i++)
        {
            xentries.add((xData[i]));
        }
        PieDataSet pieDataSet=new PieDataSet(yentries,"Placement/Infrastructure/Academics/Hostel");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(30);
        ArrayList<Integer> colors=new ArrayList<>();
        colors.add(Color.GREEN);
        colors.add(Color.RED);
        colors.add(Color.BLUE);
        colors.add(Color.YELLOW);
        pieDataSet.setColors(colors);
        Legend legend=pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        PieData pieData=new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}