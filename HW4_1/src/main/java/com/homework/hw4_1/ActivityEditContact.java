package com.homework.hw4_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityEditContact extends AppCompatActivity {
    private TextView nameView;
    private TextView infoView;
    private int itemNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        nameView = findViewById(R.id.nameEditTextView);
        infoView = findViewById(R.id.editTextViewInfo);

        final Intent intent = getIntent();
        if (intent != null) {
            nameView.setText(intent.getStringExtra("nameView"));
            infoView.setText(intent.getStringExtra("infoView"));
            itemNumber = intent.getIntExtra("ItemNumber",0);
        }

        findViewById(R.id.toolbarSaveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("Name", nameView.getText().toString());
                resultIntent.putExtra("Phone or email", infoView.getText().toString());
                resultIntent.putExtra("ItemNumber", itemNumber);
                nameView.setText("");
                infoView.setText("");
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });

        findViewById(R.id.toolbarBackButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

        findViewById(R.id.removeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameView.setText("");
                infoView.setText("");
            }
        });
    }
}

