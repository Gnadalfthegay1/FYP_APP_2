package com.example.fyp_app_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference userRef;
    String qEmail, qQuery, sEmail, sPassword;
    ArrayList<String> alEmail = new ArrayList<>();
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = getIntent();
        String r = i.getStringExtra("role");
        sEmail = i.getStringExtra("email");
        sPassword = i.getStringExtra("password");
        userRef = db.collection(r);
        lv = findViewById(R.id.lv);
        userRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        qQuery = document.getString("query");
                        qEmail = document.getString("email");
                        alEmail.add(qEmail);

                    }
                    ArrayAdapter<String> aa = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, alEmail);
                    lv.setAdapter(aa);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent i1 = new Intent(MainActivity.this, ReplyActivity.class);
                            i1.putExtra("query", qQuery);
                            i1.putExtra("qEmail", qEmail);
                            i1.putExtra("sEmail", sEmail);
                            i1.putExtra("sPassword", sPassword);
                            startActivity(i1);
                        }
                    });
                } else {
                }
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.menu_add) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}