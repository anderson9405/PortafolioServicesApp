package com.example.citasuamdos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    Button btn_signup;
    EditText et_name, et_email, et_age, et_program, et_password;

    private FirebaseFirestore mfirestore;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mfirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        btn_signup = findViewById(R.id.btn_signup);
        et_name = (EditText) findViewById(R.id.et_name);
        et_email = (EditText) findViewById(R.id.et_email);
        et_age = (EditText) findViewById(R.id.et_phone);
        et_program = (EditText) findViewById(R.id.et_name_user);
        et_password = (EditText) findViewById(R.id.et_password);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignupActivity.this, "Si soy el botón", Toast.LENGTH_SHORT).show();

                String name = et_name.getText().toString().trim();
                String email = et_email.getText().toString().trim();
                String age = et_age.getText().toString().trim() + " años";
                String program = et_program.getText().toString().trim();
                String password = et_password.getText().toString().trim();

                if (name.isEmpty() || email.isEmpty() || age.isEmpty() || program.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Ingresa todos los datos", Toast.LENGTH_SHORT).show();
                } else {
                    postUser(name, email, age, program, password);
                }


            }
        });

    }

    private void postUser(String name, String email, String age, String program, String password) {

        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("email", email);
        map.put("age", age);
        map.put("program", program);
        map.put("password", password);

        mfirestore.collection("users").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(SignupActivity.this, "Registrado exitosamente", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignupActivity.this, "Error al registrarse", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private void registerUser(String name, String age, String program, String email, String password) {
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful() && task.getResult() != null) {
//                            String id = task.getResult().getUser().getUid();
//                            Map<String, Object> map = new HashMap<>();
//                            map.put("id", id);
//                            map.put("name", name);
//                            map.put("age", age);
//                            map.put("program", program);
//                            map.put("email", email);
//                            map.put("password", password);
//
//                            mfirestore.collection("usuarios").document(id).set(map)
//                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                        @Override
//                                        public void onSuccess(Void unused) {
//                                            finish();
//                                            startActivity(new Intent(SignupActivity.this, MainActivity.class));
//                                            Toast.makeText(SignupActivity.this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
//                                        }
//                                    })
//                                    .addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            Toast.makeText(SignupActivity.this, "Error al guardar", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                        } else {
//                            Toast.makeText(SignupActivity.this, "Error al registrar", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }

}