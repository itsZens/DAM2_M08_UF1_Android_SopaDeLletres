package edu.fje.dam2.m08_uf1_android_sopadelletres;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import android.util.Log;


public class MainActivity extends AppCompatActivity {

    private Button playButton;
    private Button scoresButton;
    private Button instructionsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Button "Jugar"
        playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                // Start a new activity onClick the button
                Intent intent = new Intent(MainActivity.this, M17_CellesActivity.class);
                startActivity(intent);
            }
        });

        // Button "Puntuacions"
        scoresButton = findViewById(R.id.scoresButton);
        scoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                // Start a new activity onClick the button
                Intent intent = new Intent(MainActivity.this, ScoreboardsActivity.class);
                startActivity(intent);
            }
        });

        // Button "Instructions"
        instructionsButton = findViewById(R.id.instructionsButton);
        instructionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                // Start a new activity onClick the button
                Intent intent = new Intent(MainActivity.this, InstructionsActivity.class);
                startActivity(intent);
            }
        });


        // Database SQLite connection
        SQLiteDatabase database = null;

        try {
            // Creating the table "SopaDeLletres"
            database = this.openOrCreateDatabase("SopaDeLletres", MODE_PRIVATE, null);

            database.execSQL("CREATE TABLE IF NOT EXISTS Scoreboards(username VARCHAR, score INT, game_duration TEXT, date TEXT);");

            Log.i("MainActivity","Database created successfully");


        } finally {
            if (database != null) {
                database.close();
            }
        }

    }

}
