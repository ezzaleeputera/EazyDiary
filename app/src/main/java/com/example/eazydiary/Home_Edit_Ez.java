package com.example.eazydiary;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Home_Edit_Ez extends AppCompatActivity
{
    EditText subjek,pengajar;
    Button submit,back;
    TextView tvTitleSet, tvDescSet;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit);
        String slot_key=getIntent().getStringExtra("slot_key");
        Objects.requireNonNull(getSupportActionBar()).setTitle("Edit Slot");


        subjek= findViewById(R.id.add_title);
        pengajar= findViewById(R.id.add_desc);
        tvTitleSet = findViewById(R.id.tvTitleSet);
        tvDescSet = findViewById(R.id.tvDescSet);


        FirebaseDatabase.getInstance().getReference().child("Diary").child(slot_key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String TitleSet= Objects.requireNonNull(snapshot.child("title").getValue()).toString();
                    String DescSet= Objects.requireNonNull(snapshot.child("desc").getValue()).toString();

                    tvTitleSet.setText(TitleSet);
                    tvDescSet.setText(DescSet);



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        back= findViewById(R.id.add_back);
        back.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Home_Ez.class);
            startActivity(intent);
            finish();
        });




        submit= findViewById(R.id.add_submit);
        submit.setOnClickListener(view -> {
            String title=subjek.getText().toString().trim();
            String desc=pengajar.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
            String currentDateandTime = sdf.format(new Date());

            if (TextUtils.isEmpty(title)) {
                subjek.setError("Title needed");
                return;
            }if (TextUtils.isEmpty(desc)) {
                pengajar.setError("Desc needed");
                return;
            }

            Map<String,Object> map=new HashMap<>();
            map.put("title",subjek.getText().toString().trim());
            map.put("desc",pengajar.getText().toString());
            map.put("date",currentDateandTime);



            FirebaseDatabase.getInstance().getReference().child("Diary").child(slot_key)
                    .setValue(map)
                    .addOnSuccessListener(aVoid -> {
                        tvTitleSet.setText("");
                        tvDescSet.setText("");


                        Toast.makeText(getApplicationContext(),"Changes Made",Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(getApplicationContext(), Home_Ez.class);
                        startActivity(intent);
                        finish();
                    })
                    .addOnFailureListener(e -> Toast.makeText(getApplicationContext(),"Failed to make changes",Toast.LENGTH_LONG).show());

        });

    }


}