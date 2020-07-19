package com.example.fyp_app_2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    String toEmail, fromEmail, fromPassword;
    EditText etSubject, etMsg;
    Button btSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply2);
        tvFrom = findViewById(R.id.fromEmaIl);
        tvTo = findViewById(R.id.email);
        tvQuery = findViewById(R.id.query);
        etSubject = findViewById(R.id.etSubject);
        etMsg = findViewById(R.id.etWrite);
        btSubmit = findViewById(R.id.btSend);
        Intent i = getIntent();
        toEmail = i.getStringExtra("qEmail");
        fromEmail = i.getStringExtra("sEmail");
        fromPassword = i.getStringExtra("sPassword");
        tvFrom.setText(fromEmail);
        tvTo.setText(toEmail);
        tvQuery.setText(i.getStringExtra("query"));
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = etMsg.getText().toString();
                String subject = etSubject.getText().toString();
                JavaMailAPI m = new JavaMailAPI(getApplicationContext(), toEmail, subject, msg, fromEmail, fromPassword);
                m.execute();

                finish();
            }
        });
    }
}