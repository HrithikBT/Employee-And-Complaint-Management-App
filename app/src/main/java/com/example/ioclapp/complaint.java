package com.example.ioclapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class complaint extends AppCompatActivity {
    private EditText ComplaintBox;
    private Button Submit,Cancel;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        ComplaintBox = (EditText)findViewById(R.id.etComplaint);
        Submit = (Button)findViewById(R.id.btnSubmit);
        Cancel = (Button)findViewById(R.id.btnCancel);
        String Complaint = ComplaintBox.getText().toString();


        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(complaint.this,Main2Activity.class));
            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                addcomp();
                Toast.makeText(getApplicationContext(),"Complaint registered",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(complaint.this,Main2Activity.class));
            }



        });

    }

    private void addcomp(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String currentDateandTime = sdf.format(new Date());
        DatabaseReference myRef = firebaseDatabase.getReference("Complaints/"+currentDateandTime);
        complt comp = new complt(ComplaintBox.getText().toString(),"OPEN",firebaseAuth.getUid(),currentDateandTime);
        myRef.setValue(comp);
    }
}
