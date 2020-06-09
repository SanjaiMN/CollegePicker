package com.example.collegepicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class collegelist extends AppCompatActivity  implements  RecyclerviewInterface,SearchView.OnQueryTextListener
{
    FirebaseAuth firebaseAuth;
    ProgressDialog pd;
    RecyclerView recyclerView;
    RecyclerAdaptor recyclerAdaptor;
    FloatingActionButton floatingActionButton;
    List<Collegedetails> list,refreshlist,selectionlist;
    SwipeRefreshLayout swipeRefreshLayout;
    DatabaseReference databaseReference,reference;
    DatabaseReference databaseReference1,reference1;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collegelist);
        pd=new ProgressDialog(this);
        floatingActionButton=findViewById(R.id.floatingActionButton);
        pd.setMessage("Loading Colleges...");
        pd.show();
        list= new ArrayList<>();
        refreshlist=new ArrayList<>();
        recyclerView=findViewById(R.id.recyclerview);
        swipeRefreshLayout=findViewById(R.id.swiperefresh);
        firebaseAuth=FirebaseAuth.getInstance();
        recyclerView.setHasFixedSize(true);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        reference = databaseReference.child("College");
        reference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                list.clear();
                refreshlist.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    Collegedetails collegedetails = dataSnapshot1.getValue(Collegedetails.class);
                    collegedetails.code = Integer.parseInt(dataSnapshot1.getKey());
                    list.add(collegedetails);
                    refreshlist.add(collegedetails);
                }
                recyclerAdaptor.notifyDataSetChanged();
                pd.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
        recyclerAdaptor=new RecyclerAdaptor(list,collegelist.this,context);
        recyclerView.setAdapter(recyclerAdaptor);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                databaseReference1=FirebaseDatabase.getInstance().getReference();
                reference1=databaseReference1.child("College");
                recyclerAdaptor=new RecyclerAdaptor(list,collegelist.this,context);
                reference1.addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        list.clear();
                        refreshlist.clear();
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                        {
                            Collegedetails collegedetails = dataSnapshot1.getValue(Collegedetails.class);
                            collegedetails.code = Integer.parseInt(dataSnapshot1.getKey());
                            list.add(collegedetails);
                            refreshlist.add(collegedetails);
                        }
                        recyclerAdaptor.notifyDataSetChanged();
                        pd.dismiss();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {

                    }
                });
                recyclerAdaptor=new RecyclerAdaptor(list,collegelist.this,context);
                recyclerView.setAdapter(recyclerAdaptor);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(collegelist.this,AhpCollegeInput.class);
                intent.putExtra("forahplist", (Serializable) list);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
       getMenuInflater().inflate(R.menu.optionsmenu,menu);
       MenuItem item=menu.findItem(R.id.search);
       SearchView searchView = (SearchView) item.getActionView();
       searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public void onBackPressed()
    {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface arg0, int arg1)
                    {
                        moveTaskToBack(true);
                    }
                }).create().show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.logoutoption:
            {
                new AlertDialog.Builder(this)
                        .setTitle("Really logout?")
                        .setMessage("Are you sure you want to logout?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface arg0, int arg1)
                            {
                                firebaseAuth= FirebaseAuth.getInstance();
                                firebaseAuth.signOut();
                                finish();
                                Toast.makeText(collegelist.this,"Logout successful",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(collegelist.this,MainActivity.class));
                            }
                        }).create().show();
                break;
            }
            case R.id.profile:
            {
                startActivity(new Intent(collegelist.this,profile.class));
                break;
            }
        }
        return true;
    }

    @Override
    public void onItemClick(int position)
    {
        Intent i =new Intent(collegelist.this,InsideRecyclerView.class);
        i.putExtra("collegeclick",refreshlist.get(position));
        startActivity(i);
    }
    @Override
    public void onLongItemClick(int position)
    {

    }
    @Override
    public boolean onQueryTextSubmit(String query)
    {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText)
    {
        if(newText==null)
            recyclerAdaptor.updatelist(list);
        else {
            String userinput = newText.toLowerCase().trim();
            List<Collegedetails> newList = new ArrayList<>();
            for (Collegedetails string : list) {
                if (string.name.toLowerCase().contains(userinput)|| String.valueOf(string.code).contains(newText))
                {
                    newList.add(string);
                }
            }
            recyclerAdaptor.updatelist(newList);
            refreshlist.clear();
            refreshlist.addAll(newList);
        }
        return false;
    }
}
