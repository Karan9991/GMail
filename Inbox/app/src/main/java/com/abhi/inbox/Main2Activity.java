package com.abhi.inbox;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abhi.inbox.gmail.GmailSender;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Main2Activity extends AppCompatActivity implements ValueEventListener {

    EditText edtsend,edtmessage,edtsubject;
    Button send;
    String email,emailbody,emailsub;
    FirebaseDatabase firebaseCancelR = FirebaseDatabase.getInstance();
    DatabaseReference databaseRefCancelR = firebaseCancelR.getReference();
    DatabaseReference cancelRdata = databaseRefCancelR.child("mail");
    String mmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("GMail");
        cancelRdata.addValueEventListener(this);

        edtsend = (EditText)findViewById(R.id.sendedittext);
        edtmessage = (EditText)findViewById(R.id.edtbody);
        edtsubject = (EditText)findViewById(R.id.edtsubject);
        send = (Button)findViewById(R.id.sendbutton);


        final SendEmailTask sendEmailTask = new SendEmailTask();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = edtsend.getText().toString();
                emailbody = edtmessage.getText().toString();
                emailsub = edtsubject.getText().toString();

                sendEmailTask.execute();


            }
        });
    }
    class SendEmailTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("Email sending", "sending start");
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                // email = edtsend.getText().toString();
// enter your gmailid/password and turn on lesssecureapp in your gmail account
                GmailSender sender = new GmailSender("000111karan@gmail.com", "xxxxxxx");
                //subject, body, sender, to
                sender.sendMail(emailsub,
                        emailbody,
                        "000111karan@gmail.com",
                        email);
                cancelRdata.setValue("EMail: "+email+ " Subject: "+emailsub+" Body: "+emailbody);

                //              Toast.makeText(getApplicationContext(), "E-Mail Sent", Toast.LENGTH_SHORT).show();
                Log.i("Email sending", "send");
            } catch (Exception e) {
                Log.i("Email sending", "cannot send");
//                Toast.makeText(getApplicationContext(), "E-Mail failed to Send"+e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if(dataSnapshot.getValue(String.class)!=null){
            String key = dataSnapshot.getKey();
            if (key.equals("passengerpicked")){
             //   data = dataSnapshot.getValue(String.class);
                // Toast.makeText(getApplicationContext(),"firebase "+data,Toast.LENGTH_SHORT).show();
            }
        }

    }
    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
