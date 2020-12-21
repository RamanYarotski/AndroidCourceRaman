package com.homework.hw4_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String NEW_CONTACT = "NewContact";
    public static final String CONTACT_FOR_CHANGES = "ContactForChanges";
    public static final String CONTACT_NUMBER = "ContactNumber";
    public static final String MODIFIED_CONTACT = "ModifiedContact";
    private List<Contact> contactList = new ArrayList<>();  //
    private MenuItem menuItem;
    private SearchView searchView;
    private ContactListAdapter adapter;
    private RecyclerView recyclerView;

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        menuItem = menu.findItem(R.id.search_on_menu);
//        searchView = (SearchView) menuItem.getActionView();

//        searchView.findViewById(R.id.searchContact);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                adapter.getFilter().filter(newText);
//                return false;
//            }
//        });
//        return true;
//    }

    public interface ListContactActionListener {
        void onContactClicked(int number);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setAdapter(new ContactListAdapter(contactList, new ListContactActionListener() {
            @Override
            public void onContactClicked(int number) {
                Intent intent = new Intent(
                        MainActivity.this, ActivityEditContact.class);
                intent.putExtra(CONTACT_FOR_CHANGES, contactList.get(number));
                intent.putExtra(CONTACT_NUMBER, number);
                startActivityForResult(intent, 456);

            }
        }));
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        findViewById(R.id.buttonAddContact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(
                        new Intent(MainActivity.this, ActivityAddContact.class),
                        123);
            }
        });

        searchView.findViewById(R.id.searchContact);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 123 && resultCode == Activity.RESULT_OK && data != null) {
            contactList.add((Contact) data.getSerializableExtra(NEW_CONTACT));
            adapter.notifyItemChanged(contactList.size() - 1);
        } else if (requestCode == 456 && resultCode == Activity.RESULT_OK && data != null) {
            contactList.set(data.getIntExtra(CONTACT_NUMBER, 0),
                    (Contact) data.getSerializableExtra(MODIFIED_CONTACT));
            adapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

// как убрать ошибку "Dependent features configured but no package ID was set." ?