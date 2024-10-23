package com.example.hw5;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private ArrayAdapter<Attraction> adapter;
    private List<Attraction> attractionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        ListView listViewAttractions = findViewById(R.id.listViewAttractions);
        Button buttonAddAttraction = findViewById(R.id.buttonAddAttraction);

        attractionList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, attractionList);
        listViewAttractions.setAdapter(adapter);

        loadAttractions();

        buttonAddAttraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AttractionActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        listViewAttractions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Attraction attraction = attractionList.get(position);
                Intent intent = new Intent(MainActivity.this, AttractionActivity.class);
                intent.putExtra("attractionId", attraction.getId());
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            loadAttractions();
        }
    }

    private void loadAttractions() {
        attractionList.clear();
        attractionList.addAll(databaseHelper.getAllAttractions());
        adapter.notifyDataSetChanged();
    }
}
