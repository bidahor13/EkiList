package com.ekilist.ekilist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Babatunde on 3/28/2016.
 */
public class NewListActivity extends Activity {

    private ListView mListView;
    private ArrayAdapter<String> mAdapter;
    private List<String> mItems;
    private EditText mItemEt;

    //Database instance
    DatabaseHelper helper = new DatabaseHelper(this);
    String listName = "default";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_list_activity);
        String username = getIntent().getStringExtra("Username");

        TextView tv = (TextView)findViewById(R.id.textView_newList);
        tv.setText(username);
        //TODO: Get the items from the SQLiteDb
        mListView = (ListView)findViewById(R.id.listViewToDo);

        mItems = new ArrayList<>();
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, mItems);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String toRemove = mItems.remove(position);
                helper.removeItem(toRemove, listName); //remove item from database
                mAdapter = new ArrayAdapter<String>(NewListActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, mItems);
                mListView.setAdapter(mAdapter);
                return true;
            }
        });

        mItemEt = (EditText)findViewById(R.id.editTextToDo);

        //Get all items for list (listName) and add them to adapter
        ArrayList<String> itemsForList = helper.getItemsForList(this.listName);
        for(String item : itemsForList)
            mAdapter.add(item);

    }


    public void onOpenMapClick(View v)
    {
        Intent mapsActivity = new Intent(NewListActivity.this, MapsActivity.class);
        startActivity(mapsActivity);
    }

    public void onAddItemClick(View v)
    {
        String item = mItemEt.getText().toString();
        mAdapter.insert(item,0);

        mItemEt.setText("");

        //Insert item into database
        helper.insertListItem(item, this.listName);

    }
}
