package com.homework.hw4_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ActivityEditContact extends MainActivity {
    private TextView nameView;
    private TextView infoView;
    private Contact contact;
    private int contactNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        nameView = findViewById(R.id.nameEditTextView);
        infoView = findViewById(R.id.editTextViewInfo);

        final Intent intent = getIntent();
        if (intent != null) {
            contact = (Contact) intent.getSerializableExtra(CONTACT_FOR_CHANGES);
            nameView.setText(contact.getName());
            infoView.setText(contact.getInfo());
            contactNumber = intent.getIntExtra(CONTACT_NUMBER, 0);
        }

        findViewById(R.id.toolbarSaveButton).setOnClickListener(v -> {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(MODIFIED_CONTACT,
                        new Contact(nameView.getText().toString(),
                                infoView.getText().toString(),
                                contact.getInfoType()));
                resultIntent.putExtra(CONTACT_NUMBER, contactNumber);
                nameView.setText("");
                infoView.setText("");
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
        });

        findViewById(R.id.toolbarBackButton).setOnClickListener(v -> {
            setResult(Activity.RESULT_CANCELED);
            finish();
        });

        findViewById(R.id.removeButton).setOnClickListener(v -> {
            nameView.setText("");
            infoView.setText("");
        });
    }

}

