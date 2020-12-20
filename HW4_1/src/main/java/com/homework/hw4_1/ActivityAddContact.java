package com.homework.hw4_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

public class ActivityAddContact extends MainActivity {
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

        findViewById(R.id.toolbarBackButton).setOnClickListener(v -> {
            setResult(Activity.RESULT_CANCELED);
            finish();
        });

        findViewById(R.id.toolbarSaveButton).setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            if (phoneButton.isChecked() & !emailButton.isChecked()) {
                resultIntent.putExtra(NEW_CONTACT,
                        new Contact(nameView.getText().toString(),
                                infoView.getText().toString(),
                                InfoType.PHONE_NUMBER));
            } else if (!phoneButton.isChecked() & emailButton.isChecked()) {
                resultIntent.putExtra(NEW_CONTACT,
                        new Contact(nameView.getText().toString(),
                                infoView.getText().toString(),
                                InfoType.EMAIL));
            }
            nameView.setText("");
            infoView.setText("");
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });
    }

    View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RadioButton rb = (RadioButton) v;
            if (rb.getId() == R.id.PhoneButton) {
                infoView.setHint(R.string.contact_name);
            } else if (rb.getId() == R.id.EmailButton) {
                infoView.setHint(R.string.Phone_number);
            }
        }
    };
}
