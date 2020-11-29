package com.homework.hw4_1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static final List<Item> itemList = new ArrayList<>();
    final RecyclerView.Adapter adapter = new ItemListAdapter(this.itemList);
    private RecyclerView recyclerView;
    private Button buttonAddContact;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(
                this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    static final class ItemListAdapter extends
            RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {
        private List<Item> itemList;

        public ItemListAdapter(List<Item> itemList) {
            this.itemList = itemList;
        }

        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_of_recycler, parent, false);
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            Item item = itemList.get(position);
            holder.imageView.setImageResource(item.getImage());
            holder.nameView.setText(item.getName());
            holder.infoView.setText(item.getInfo());
        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }

        public static class ItemViewHolder extends RecyclerView.ViewHolder {
            final ImageView imageView;
            final TextView nameView, infoView;

            ItemViewHolder(View view) {
                super(view);
                imageView = view.findViewById(R.id.contactImage);
                nameView = view.findViewById(R.id.contactName);
                infoView = view.findViewById(R.id.contactInfo);
            }
        }
    }
}

//        buttonAddContact = findViewById(R.id.buttonAddContact);
//        searchView = findViewById(R.id.search_bar);
//        buttonAddContact.setOnClickListener(this);


//    @Override
//    public void onClick(View v) {
//        Intent intent = new Intent(this, ActivityAddContact.class);
//        startActivity(intent);
//    }
