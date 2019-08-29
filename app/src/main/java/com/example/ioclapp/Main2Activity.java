package com.example.ioclapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class Main2Activity extends AppCompatActivity {

    private TextView EmpName,Department,Floor,Asset1,Asset2,Asset3;
    private Button LogComplaint,Logout,EditInfo,AdminBtn;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private ImageView profilePic;
    String access;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        SetupUIViews();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child(firebaseAuth.getUid()).child("Images/Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(profilePic);
            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserDetails userDetails = dataSnapshot.getValue(UserDetails.class);
                EmpName.setText(userDetails.getEmpname());
                Floor.setText(userDetails.getFloor());
                Department.setText(userDetails.getDepartment());
                Asset1.setText(userDetails.getAsset1());
                Asset2.setText(userDetails.getAsset2());
                Asset3.setText(userDetails.getAsset3());
                access = userDetails.getAccess();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getCode(),Toast.LENGTH_SHORT).show();

            }
        });


        LogComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main2Activity.this, complaint.class);
                startActivity(intent);
            }
        });
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                finish();
                Toast.makeText(getApplicationContext(),"Signing Out",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Main2Activity.this,MainActivity.class));
            }
        });
        EditInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),UpdateProfile.class));
            }
        });

        AdminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(access.toLowerCase()=="admin"){
                    startActivity(new Intent(getApplicationContext(),Admin.class));
                }else{
                    Toast.makeText(getApplicationContext(),"Not an Admin",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }



    private void SetupUIViews(){
        EmpName = (TextView)findViewById(R.id.tvEname);
        Department = (TextView)findViewById(R.id.tvDept);
        Floor = (TextView)findViewById(R.id.tvFloor);
        Asset1 = (TextView)findViewById(R.id.tvA1);
        Asset2 = (TextView)findViewById(R.id.tvA2);
        Asset3 = (TextView)findViewById(R.id.tvA3);
        LogComplaint = (Button)findViewById(R.id.btnLogComplaint);
        Logout = (Button)findViewById(R.id.btnLogout);
        EditInfo = (Button)findViewById(R.id.btnEditInfo);
        AdminBtn = (Button)findViewById(R.id.btnAdmin);
        profilePic = findViewById(R.id.ivEmppic);


    }
}
