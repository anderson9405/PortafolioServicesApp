package com.example.citasuamdos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;

public class CreateService extends AppCompatActivity {

    Button btn_public;
    EditText et_name, et_phone, et_description;

    private FirebaseFirestore mfirestore;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_service);

        spinner = findViewById(R.id.spinner);

        String[] opciones = { "Contador", "Carpintero", "Mecánico", "Médico", "Electricista", "Plomero", "Diseñador Gráfico",
                "Programador", "Jardinero", "Chef", "Fotógrafo",  "Abogado", "Profesor", "Pintor", "Celador", "Niñera", "Aseador", "Cocinero",
                "Mesero"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        mfirestore = FirebaseFirestore.getInstance();

        btn_public = findViewById(R.id.btn_signup);
        et_name = (EditText) findViewById(R.id.et_name_user);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_description = (EditText) findViewById(R.id.et_description);

        btn_public.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = spinner.getSelectedItem().toString();
                String name = et_name.getText().toString().trim();
                String phone = et_phone.getText().toString().trim();
                String description = et_description.getText().toString().trim();

                if (title.isEmpty() || name.isEmpty() || phone.isEmpty() || description.isEmpty()) {
                    Toast.makeText(CreateService.this, "Ingresa todos los datos", Toast.LENGTH_SHORT).show();
                } else {
                    postService(title, name, phone, description);
                }


            }
        });
    }


    private void postService(String title, String name, String phone, String description) {
        // Obtén el lowerTitle usando la función convertToLowerCaseWithoutAccents
        String lowerTitle = convertToLowerCaseWithoutAccents(title);

        Map<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("name", name);
        map.put("phone", phone);
        map.put("description", description);
        map.put("lowerTitle", lowerTitle);

        mfirestore.collection("services").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(CreateService.this, "Registrado exitosamente", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreateService.this, "Error al registrarse", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String convertToLowerCaseWithoutAccents(String input) {
        String normalizedString = Normalizer.normalize(input, Normalizer.Form.NFD);
        return normalizedString.replaceAll("[^\\p{ASCII}]", "").toLowerCase();
    }

}