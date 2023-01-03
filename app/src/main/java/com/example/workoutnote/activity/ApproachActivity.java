package com.example.workoutnote.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.workoutnote.R;
import com.example.workoutnote.model.ApproachWeight;
import com.example.workoutnote.model.Exercise;

import java.util.Objects;

public class ApproachActivity extends AppCompatActivity {
    private ApproachWeight approachWeight;
    private EditText editWeight;
    private EditText editCountRepeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approach);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        editWeight = findViewById(R.id.editWeight);
        editCountRepeat = findViewById(R.id.editCountRepeat);
        approachWeight = (ApproachWeight) getIntent().getParcelableExtra("approachWeight");
        String strWieght = "";
        if (approachWeight.getWeight() > 0)
            strWieght = String.valueOf(approachWeight.getWeight());
        editWeight.setText(strWieght);
        String strRepeat = "";
        if (approachWeight.getCountRepeat()>0)
            strRepeat = String.valueOf(approachWeight.getCountRepeat());
        editCountRepeat.setText(strRepeat);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menu_main_save:
                String strWieght = String.valueOf(editWeight.getText());
                if (strWieght.isEmpty())
                    strWieght = "0";
                String strRepeat = String.valueOf(editCountRepeat.getText());
                if (strRepeat.isEmpty())
                    strRepeat = "0";
                int weight = Integer.parseInt(strWieght);
                int countRepeat = Integer.parseInt(strRepeat);
                Intent data = new Intent();
                data.putExtra("approachWeight", new ApproachWeight(weight, countRepeat));
                data.putExtra("position", getIntent().getIntExtra("position", 0));
                setResult(Activity.RESULT_OK, data);
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}