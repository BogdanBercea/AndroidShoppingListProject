package com.example.shoppinglist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DeleteAllDialog extends AppCompatDialogFragment {

    ArrayList<String> elements;
    ArrayAdapter<String> arrayAdapter;
    ListView listView;
    Context context;

    public DeleteAllDialog(ArrayList<String> elements, ListView listView, ArrayAdapter<String> arrayAdapter, Context context)
    {
        this.elements = elements;
        this.listView = listView;
        this.arrayAdapter = arrayAdapter;
        this.context = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete All Elements");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                elements.clear();
                storeArrayVal(elements, context);
                listView.setAdapter(arrayAdapter);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        return builder.create();
    }

    public static void storeArrayVal(ArrayList inArrayList, Context context)
    {
        Set WhatToWrite = new HashSet(inArrayList);
        SharedPreferences WordSearchPutPrefs = context.getSharedPreferences("dbArrayValues", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = WordSearchPutPrefs.edit();
        prefEditor.putStringSet("myArray", WhatToWrite);
        prefEditor.commit();
    }
}
