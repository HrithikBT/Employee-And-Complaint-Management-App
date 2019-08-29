package com.example.ioclapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ManageComplaints extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private ListView lv;
    private ArrayList<complt> Comptags = new ArrayList<>();
    private ArrayList<String> CompString = new ArrayList<>();
    private complt temp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_complaints);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        lv = findViewById(R.id.lvMC);
        final ArrayAdapter<complt> arrayAdapter = new ArrayAdapter<complt>(getApplicationContext(),android.R.layout.simple_list_item_1,Comptags);
        final ArrayAdapter<String> sarray = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,CompString);

        DatabaseReference databaseReference = firebaseDatabase.getReference("Complaints/");

        lv.setAdapter(sarray);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                temp = Comptags.get(i);
                temp.setStatus("CLOSED");
                Comptags.remove(i);
                CompString.remove(i);
                DatabaseReference dr = firebaseDatabase.getReference("Assigned/"+temp.getTs());
                DatabaseReference kr = firebaseDatabase.getReference("Complaints/"+temp.getTs());
                dr.setValue(temp);
                kr.setValue(temp);
                sarray.notifyDataSetChanged();
                arrayAdapter.notifyDataSetChanged();

                Toast.makeText(getApplicationContext(),"Complaint Assigned",Toast.LENGTH_SHORT).show();
            }
        });
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                complt Comp = dataSnapshot.getValue(complt.class);
                if(Comp.getStatus().equals("OPEN")){
                Comptags.add(Comp);
                CompString.add(Comp.getComplainttext());}
                sarray.notifyDataSetChanged();
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                complt Comp = dataSnapshot.getValue(complt.class);
                Comptags.remove(Comp);
                sarray.notifyDataSetChanged();
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
