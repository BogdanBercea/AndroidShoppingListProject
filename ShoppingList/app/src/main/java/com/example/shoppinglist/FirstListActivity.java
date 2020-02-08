package com.example.shoppinglist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class FirstListActivity extends AppCompatActivity {

    String item;
    EditText editTextInput;
    Button addButton;
    Button deleteAllButton;
    ListView mList;
    ImageView addImage;
    ImageView deleteAllImage;
    ArrayList<String> items = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_list);

        mList = (ListView) findViewById(R.id.TheListView);
        editTextInput = (EditText) findViewById(R.id.EditText1);
        addImage = (ImageView) findViewById(R.id.AddButtonImage);
        deleteAllImage = (ImageView) findViewById(R.id.DeleteAllBUttonImage);

        items = getArrayVal(getApplicationContext());


        //items.add("Penny");
        //items.add("Lidl");
        //items.add("Koafland");

        final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);
        mList.setAdapter(arrayAdapter);

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = editTextInput.getText().toString();
                item = transformUpperCase(item);

                if(items.contains(item))
                {
                    Toast.makeText(getBaseContext(), "This item is already added", Toast.LENGTH_LONG).show();
                }
                else if(item == null || item.trim().equals(""))
                {
                    Toast.makeText(getBaseContext(), "Input field is empty", Toast.LENGTH_LONG).show();
                }
                else {
                    items.add(item);
                    Collections.sort(items);
                    storeArrayVal(items, getApplicationContext());
                    mList.setAdapter(arrayAdapter);
                    editTextInput.setText("");
                }
            }
        });

        deleteAllImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(items, mList, arrayAdapter, getApplicationContext());
                storeArrayVal(items,getApplicationContext());
            }
        });

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = ((TextView)view).getText().toString();
                if (selectedItem.trim().equals(items.get(position).trim()))
                {
                    deleteItem(selectedItem, position, arrayAdapter);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Failed to delete this item", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public static String transformUpperCase(String item)
    {
        if(item.isEmpty())
            return item;
        return item.substring(0, 1).toUpperCase() + item.substring(1).toLowerCase();
    }

    public void openDialog(ArrayList<String> strings, ListView lv, ArrayAdapter<String> arrAdapter, Context context)
    {
        DeleteAllDialog deleteAllDialog = new DeleteAllDialog(strings, lv, arrAdapter, context);
        deleteAllDialog.show(getSupportFragmentManager(), "delete all items dialog");
    }


    public static void storeArrayVal(ArrayList inArrayList, Context context)
    {
        Set WhatToWrite = new HashSet(inArrayList);
        SharedPreferences WordSearchPutPrefs = context.getSharedPreferences("dbArrayValues", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = WordSearchPutPrefs.edit();
        prefEditor.putStringSet("myArray", WhatToWrite);
        prefEditor.commit();
    }

    public static ArrayList getArrayVal(Context bogdy)
    {
        SharedPreferences WordSearchGetPref = bogdy.getSharedPreferences("dbArrayValues", Activity.MODE_PRIVATE);
        Set tempSet = new HashSet();
        tempSet = WordSearchGetPref.getStringSet("myArray", tempSet);
        return new ArrayList<>(tempSet);
    }

    public void deleteItem(String selectedItem, final int position, final ArrayAdapter<String> arrAdapter)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + selectedItem + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                items.remove(position);
                Collections.sort(items);
                storeArrayVal(items, getApplicationContext());
                mList.setAdapter(arrAdapter);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
