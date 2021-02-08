package com.homework.hw8mvp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsActivity extends AppCompatActivity {

    private SwitchMaterial aSwitch;
    private boolean temperatureCheck;
    private SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        aSwitch = findViewById(R.id.switchTemperature);

        boolean b = loadBoolean();
        aSwitch.setChecked(loadBoolean());
        aSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                temperatureCheck = true;
                Toast.makeText(SettingsActivity.this, "Fahrenheit mode is on", Toast.LENGTH_SHORT).show();
                saveBoolean(temperatureCheck);
            } else {
                temperatureCheck = false;
                Toast.makeText(SettingsActivity.this, "Included Celsius mode", Toast.LENGTH_SHORT).show();
                saveBoolean(temperatureCheck);
            }
        });

        findViewById(R.id.backButton).setOnClickListener(v -> {
            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
            intent.putExtra("TEMPCHECK", temperatureCheck);
            startActivity(intent);
            finish();
        });
    }

    private void saveBoolean(boolean temperatureCheck) {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putBoolean("ISCHEKED", temperatureCheck);
        ed.apply();
    }

    private boolean loadBoolean() {
        sPref = getPreferences(MODE_PRIVATE);
        this.temperatureCheck = sPref.getBoolean("ISCHEKED", false);
        return temperatureCheck;
    }

}