package com.example.fyp_app_2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ReplyActivity extends AppCompatActivity {
    TextView tvFrom, tvTo, tvQuery;
    String role, fromEmail, fromPassword;
    EditText etSubject, etMsg;
    Button btSubmit;
    Query q;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference userRef;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply2);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int colorCodeDark = Color.parseColor("#8b0000");
        window.setStatusBarColor(colorCodeDark);
        ActionBar actionBar;
        actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#8b0000"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);
        setTitle("Reply to parent");
        tvFrom = findViewById(R.id.fromEmaIl);
        tvTo = findViewById(R.id.email);
        tvQuery = findViewById(R.id.query);
        etSubject = findViewById(R.id.etSubject);
        etMsg = findViewById(R.id.etWrite);
        btSubmit = findViewById(R.id.btSend);
        Intent i = getIntent();
        q = (Query) i.getSerializableExtra("query");
        fromEmail = i.getStringExtra("sEmail");
        fromPassword = i.getStringExtra("sPassword");
        role = i.getStringExtra("role");
        tvFrom.setText(fromEmail);
        tvTo.setText(q.getEmail());
        tvQuery.setText(q.getQuery());
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String msg = etMsg.getText().toString();
                String subject = etSubject.getText().toString();
                if(msg.isEmpty()){
                    etMsg.setError("You have not entered a Message");
                    etMsg.requestFocus();
                }
                else if(subject.isEmpty()){
                    etSubject.setError("You have not entered a Subject");
                    etSubject.requestFocus();
                }
                else {
                    JavaMailAPI m = new JavaMailAPI(getApplicationContext(), q.getEmail(), subject, msg, fromEmail, fromPassword);
                    m.execute();
                    db.collection(role).document(q.getId())
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(), "Deltion Successful", Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Deltion failed", Toast.LENGTH_LONG).show();
                                }
                            });
                }


            }
        });
    }
}