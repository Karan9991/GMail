package com.abhi.inbox;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Sent extends AppCompatActivity implements  ValueEventListener {
    FirebaseDatabase firebaseCancelR = FirebaseDatabase.getInstance();
    DatabaseReference databaseRefCancelR = firebaseCancelR.getReference();
    DatabaseReference cancelRdata = databaseRefCancelR.child("mail");
    String [] mmail;
    ListView listView;
    int i=0;
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent);
        listView = (ListView)findViewById(R.id.lstsend);
         arrayAdapter = new ArrayAdapter<String>(this, //Context
                android.R.layout.simple_list_item_1, //Layout
                mmail);
       // listView.setAdapter(arrayAdapter);
    }
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if(dataSnapshot.getValue(String.class)!=null){
            String key = dataSnapshot.getKey();
            if (key.equals("mail")){
                   mmail[i] = dataSnapshot.getValue(String.class);
                   listView.setAdapter(arrayAdapter);
                // Toast.makeText(getApplicationContext(),"firebase "+data,Toast.LENGTH_SHORT).show();
            }
        }
i++;
    }
    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
