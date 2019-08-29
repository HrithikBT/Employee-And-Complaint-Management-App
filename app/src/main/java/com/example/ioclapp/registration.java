package com.example.ioclapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class registration extends AppCompatActivity {
    private EditText Ename,Email,Password,Age,Dept,Floor,Asset1,Asset2,Asset3,Access;
    private Button Register,Back;
    private FirebaseAuth firebaseAuth;
    private ImageView userProfilePic;
    String email, name, age, password,dept,floor ,asset1,asset2,asset3,access;
    private FirebaseStorage firebaseStorage;
    private static int PICK_IMAGE = 123;
    private StorageReference storageReference;
    Uri imagePath;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null){
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                userProfilePic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Supply();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        userProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("images/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
            }
        });
        //StorageReference myref1 = storageReference.child(firebaseAuth.getUid());
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if( validate()){
                   String user_email = Email.getText().toString().trim();
                   String user_password = Password.getText().toString().trim();

                   firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            sendUserData();

                            Toast.makeText(getApplicationContext(),"Registration Successful",Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(registration.this,Admin.class));
                            //firebaseAuth.signOut();
                    }
                        else {
                            Toast.makeText(getApplicationContext(),"Registration Failed",Toast.LENGTH_SHORT).show();

                        }
                    }
                });


            }}
        });
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(registration.this,Admin.class));
                Toast.makeText(getApplicationContext(),"Cancelling",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void Supply(){
        Ename = (EditText)findViewById(R.id.etRegName);
        Age = (EditText)findViewById(R.id.etage) ;
        Password = (EditText)findViewById(R.id.etRegPassword);
        Email = (EditText)findViewById(R.id.etRegEmail);
        Register = (Button)findViewById(R.id.btnRegister);
        Back = (Button)findViewById((R.id.btnBack));
        userProfilePic = (ImageView)findViewById(R.id.ivProfileic);
        Dept = (EditText)findViewById(R.id.etRegDept);
        Floor = (EditText)findViewById(R.id.etRegFloor);
        Asset1 = (EditText)findViewById(R.id.etRegAsset1);
        Asset2 = (EditText)findViewById(R.id.etRegAsset2);
        Asset3 = (EditText)findViewById(R.id.etRegAsset3);
        Access = (EditText)findViewById(R.id.etAP);

    }
    private Boolean validate(){
        Boolean result = false;

        name = Ename.getText().toString();
        password = Password.getText().toString();
        email = Email.getText().toString();
        age = Age.getText().toString();
        floor = Floor.getText().toString();
        dept = Dept.getText().toString();
        asset1 = Asset1.getText().toString();
        asset2 = Asset2.getText().toString();
        asset3 = Asset3.getText().toString();
        access = Access.getText().toString();




        if(name.isEmpty() || password.isEmpty() || email.isEmpty() || age.isEmpty()||imagePath==null){
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }

        return result;
    }


    private void sendUserData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
        StorageReference imageReference = storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Pic");  //User id/Images/Profile Pic.jpg
        UploadTask uploadTask = imageReference.putFile(imagePath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Upload failed!", Toast.LENGTH_SHORT).show();
            }
        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                Toast.makeText(getApplicationContext(), "Upload successful!", Toast.LENGTH_SHORT).show();
            }
        });
        UserDetails userDetails = new UserDetails(name,email,age,dept,floor,asset1,asset2,asset3,access);
        myRef.setValue(userDetails);
        //UserProfile userProfile = new UserProfile(name,email,age);
        //myRef.setValue(userProfile);


    }


}


