package com.homework.hw4_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityAddContact extends AppCompatActivity {
    private RadioButton phoneButton;
    private RadioButton emailButton;
    private TextView nameView;
    private TextView infoView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        phoneButton = findViewById(R.id.PhoneButton);
        phoneButton.setOnClickListener(radioButtonClickListener);

        emailButton = findViewById(R.id.EmailButton);
        emailButton.setOnClickListener(radioButtonClickListener);

        nameView = findViewById(R.id.nameEditTextView);
        infoView = findViewById(R.id.editTextViewInfo);

        findViewById(R.id.toolbarBackButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

        findViewById(R.id.toolbarSaveButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("Name", nameView.getText().toString());
                resultIntent.putExtra("Phone or email", infoView.getText().toString());

                if (phoneButton.isChecked() & !emailButton.isChecked()) {
                    resultIntent.putExtra(
                            "Contact image", R.drawable.ic_baseline_contact_phone_24);
                } else if (!phoneButton.isChecked() & emailButton.isChecked()) {
                    resultIntent.putExtra(
                            "Contact image", R.drawable.ic_baseline_contact_mail_24);
                }
                nameView.setText("");
                infoView.setText("");
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RadioButton rb = (RadioButton) v;
            if (rb.getId() == R.id.PhoneButton) {
                infoView.setHint(R.string.contact_name);
            } else if (rb.getId() == R.id.EmailButton){
                infoView.setHint(R.string.Phone_number);
            }
            }
        };
}
