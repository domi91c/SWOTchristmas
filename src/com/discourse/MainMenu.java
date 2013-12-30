package com.discourse;

import SQL.SQLiteHelper;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends Activity {

    Button newChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);


        newChart = (Button)findViewById(R.id.bNew);


        newChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String testone = "2poo";
                SQLiteHelper sqLiteHelper = new SQLiteHelper(getApplicationContext());
                sqLiteHelper.setNameId(testone);
                Intent i = new Intent(MainMenu.this, Outline.class);
                startActivity(i);

            }
        });

    }






}
