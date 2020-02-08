package com.example.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView playImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playImageButton = (ImageView) findViewById(R.id.PlayImageButton) ;

        playImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                openFirstListActivity();
            }
        });
    }

    public void openFirstListActivity(){
        Intent intent = new Intent(this, FirstListActivity.class);
        startActivity(intent);
    }
}
