package com.example.fyp_app_2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private String email;
    private String password;
    private String Role;
    private boolean successful;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userRef = db.collection("User");
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        int colorCodeDark = Color.parseColor("#002366");
        window.setStatusBarColor(colorCodeDark);
        ActionBar actionBar;
        actionBar = getSupportActionBar();

        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#002366"));

        // Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);
        setTitle("Login");
        etUsername = findViewById(R.id.editTextUsername);
        etPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);
        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etUsername.getText().toString();
                password = etPassword.getText().toString();
                if(email.isEmpty()){
                    etUsername.setError("You have not entered an Email");
                    etUsername.requestFocus();
                }
                else if(password.isEmpty()){
                    etPassword.setError("You have not entered a Password");
                    etPassword.requestFocus();
                }
                else if(password.isEmpty() && email.isEmpty()){
                    Toast.makeText(getApplicationContext(), "You have not entered your Email and Password", Toast.LENGTH_LONG).show();
                }
                else if(!password.isEmpty() && !email.isEmpty()){
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Login error, please login again.", Toast.LENGTH_LONG).show();
                            }
                            else{
                                userRef.whereEqualTo("Email", email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Role = document.getString("Role");
                                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                                i.putExtra("role", Role);
                                                i.putExtra("email", email);
                                                i.putExtra("password", password);
                                                startActivity(i);
                                                Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_LONG).show();
                                            }
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Login unsuccessful", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                            }
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        etUsername.setText("");
        etPassword.setText("");
    }
    // TODO (2) Using AsyncHttpClient, check if the user has been authenticated successfully
    // If the user can log in, extract the id and API Key from the JSON object, set them into Intent and start MainActivity Intent.
    // If the user cannot log in, display a toast to inform user that login has failed.

}