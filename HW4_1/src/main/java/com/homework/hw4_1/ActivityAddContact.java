package com.homework.hw4_1;

import android.os.Bundle;
import android.view.Menu;
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        setTitle(R.string.contact_add);
        return true;
    }

    View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RadioButton rb = (RadioButton) v;
            switch (rb.getId()) {
                case R.id.PhoneButton:
                    infoView.setHint(R.string.contact_name);
                    break;
                case R.id.EmailButton:
                    infoView.setHint(R.string.Phone_number);
                    break;
                default:
                    break;
            }
        }
    };

    public void addContact(View view) {
        if (phoneButton.isChecked() & !emailButton.isChecked()) {
            itemList.add(new Item(nameView.getText().toString(),
                    infoView.getText().toString(),
                    R.drawable.ic_baseline_contact_phone_24));
            nameView.setText("");
            infoView.setText("");
        } else if (!phoneButton.isChecked() & emailButton.isChecked()) {
            itemList.add(new Item(nameView.getText().toString(),
                    infoView.getText().toString(),
                    R.drawable.ic_baseline_contact_mail_24));
            nameView.setText("");
            infoView.setText("");
            adapter.notifyItemChanged(itemList.size() - 1);
        }
    }

}
