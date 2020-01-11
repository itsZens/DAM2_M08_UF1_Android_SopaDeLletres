package edu.fje.dam2.m08_uf1_android_sopadelletres;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class ScoreboardsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboards);
    }

    protected void saveScoreToDatabase(){
        // Database SQLite connection
        SQLiteDatabase database = null;

        try {
            // Creating the table "SopaDeLletres"
            database = this.openOrCreateDatabase("SopaDeLletres", MODE_PRIVATE, null);

            database.execSQL("INSERT INTO SopaDeLletres() ;");

            Log.i("MainActivity","Database created successfully");


        } finally {
            if (database != null) {
                database.close();
            }
        }
    }
}
