package com.homework.hw4_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static List<Item> itemList = new ArrayList<>();
    static List<Item> itemListFull;
    public RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_on_menu);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText.toString()); ///???? Почему не работает метод getFilter?
                return false;
            }
        });
        return true;
    }

    interface ListItemActionListener {
        void onItemClicked(int number);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setAdapter(new ItemListAdapter(itemList, new ListItemActionListener() {
            @Override
            public void onItemClicked(int number) {
                Log.d("LISTENER", String.valueOf(number));
            }
        }));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        findViewById(R.id.buttonAddContact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(
                        new Intent(MainActivity.this, ActivityAddContact.class),
                        123);
            }
        });
    }

    static class ItemListAdapter extends
            RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> implements Filterable {
        private List<Item> itemList;
        private ListItemActionListener listItemActionListener;

        public ItemListAdapter(List<Item> itemList, ListItemActionListener listItemActionListener) {
            this.itemList = itemList;
            this.listItemActionListener = listItemActionListener;
            itemListFull = new ArrayList<>(itemList);
        }

        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_of_recycler, parent, false);
            return new ItemViewHolder(view, listItemActionListener);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
            Item item = itemList.get(position);
            holder.imageView.setImageResource(item.getImage());
            holder.nameView.setText(item.getName());
            holder.infoView.setText(item.getInfo());
//            holder.bind(itemList.get(position)); ??? что делает этот метод?
        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }

        @Override
        public Filter getFilter() {
            return filter;
        }

        private Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Item> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(itemListFull);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Item item : itemListFull) {
                        if (item.getName().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                itemList.clear();
                itemList.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };

        public static class ItemViewHolder extends RecyclerView.ViewHolder {
            private ImageView imageView;
            private TextView nameView, infoView;
            private ListItemActionListener listItemActionListener;

            public ItemViewHolder(View itemView, ListItemActionListener listItemActionListener) {
                super(itemView);
                imageView = itemView.findViewById(R.id.contactImage);
                nameView = itemView.findViewById(R.id.contactName);
                infoView = itemView.findViewById(R.id.contactInfo);
                this.listItemActionListener = listItemActionListener;
            }

            public void bind(final int number) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listItemActionListener != null) {
                            listItemActionListener.onItemClicked(number);
                            Intent intent = new Intent(
                                    MainActivity.this, ActivityEditContact.class);
                            intent.putExtra("nameView", nameView.getText());
                            intent.putExtra("infoView", infoView.getText());
//                            intent.putExtra("imageView", String.valueOf(imageView.getDrawable())); //??? как передать изображение?
                            intent.putExtra("ItemNumber", number);
                            startActivityForResult(intent, 456);        // Где разместить эту логику?
                        }
                    }
                });
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 123 && resultCode == Activity.RESULT_OK && data != null) {
            itemList.add(new Item(data.getStringExtra("Name"),
                    data.getStringExtra("Phone or email"),
                    data.getIntExtra("Contact image", 0)));
            adapter.notifyItemChanged(itemList.size() - 1);
        } else if (requestCode == 456 && resultCode == Activity.RESULT_OK && data != null) {
            itemList.set(data.getIntExtra("ItemNumber", 0), new Item(
                    data.getStringExtra("Name"),
                    data.getStringExtra("Phone or email"),
                    data.getIntExtra("Contact image", 0)));     // как получить изображение?
            adapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

// как убрать ошибку "Dependent features configured but no package ID was set." ?