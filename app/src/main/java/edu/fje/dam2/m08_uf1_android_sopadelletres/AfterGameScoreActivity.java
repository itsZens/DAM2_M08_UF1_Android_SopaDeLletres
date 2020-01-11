package edu.fje.dam2.m08_uf1_android_sopadelletres;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class AfterGameScoreActivity extends AppCompatActivity {

    private Button backToMainMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_game_score);

        // Button "Menú principal"
        backToMainMenu = findViewById(R.id.backToMainMenu);
        backToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                // Start a new activity onClick the button
                Intent intent = new Intent(AfterGameScoreActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void saveScoreToDatabase(){
        // Database SQLite connection
        SQLiteDatabase database = null;

        // Get current time
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date now = new Date();


        try {
            // Creating the table "SopaDeLletres"
            database = this.openOrCreateDatabase("SopaDeLletres", MODE_PRIVATE, null);
            database.execSQL("INSERT INTO SopaDeLletres(username, score, game_duration, date) VALUES (, , , " + dateFormat.format(now) + ");");

            Log.i("AfterGameScoreActivity","Score saved successfully!");

        } finally {
            if (database != null) {
                database.close();
            }
        }
    }
}
