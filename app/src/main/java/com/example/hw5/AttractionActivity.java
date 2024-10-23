package com.example.hw5;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AttractionActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private EditText editTextName, editTextDescription;
    private int attractionId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction);

        databaseHelper = new DatabaseHelper(this);
        editTextName = findViewById(R.id.editTextName);
        editTextDescription = findViewById(R.id.editTextDescription);
        Button buttonSave = findViewById(R.id.buttonSave);

        attractionId = getIntent().getIntExtra("attractionId", -1);
        if (attractionId != -1) {
            Attraction attraction = databaseHelper.getAllAttractions().get(attractionId);
            editTextName.setText(attraction.getName());
            editTextDescription.setText(attraction.getDescription());
        }

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String description = editTextDescription.getText().toString();
                if (attractionId == -1) {
                    databaseHelper.addAttraction(new Attraction(0, name, description));
                } else {

                }
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}

