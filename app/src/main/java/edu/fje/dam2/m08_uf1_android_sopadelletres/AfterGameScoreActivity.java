package edu.fje.dam2.m08_uf1_android_sopadelletres;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class AfterGameScoreActivity extends AppCompatActivity {

    public Button backToMainMenu;
    public TextView scoreView;
    public String username;
    public int score;
    public Date gameDuration;
    DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_game_score);


        //Getting data (username, score and game duration) from PlayActivity
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        score = intent.getIntExtra("score", 0);

        try {
            gameDuration = timeFormat.parse(intent.getStringExtra("gameDuration"));
        } catch (ParseException e){
            Log.i("AfterGameScoreActivity", "Cannot parse game duration String to Date formatted as \"HH:mm:ss\"");
        }


        // Showing score into textView (id: scoreView)
        scoreView = findViewById(R.id.scoreView);
        scoreView.setText(score);


        // Calling function saveScoreToDatabase()
        saveScoreToDatabase();


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
        now.getTime();


        try {
            // Creating the table "SopaDeLletres"
            database = this.openOrCreateDatabase("SopaDeLletres", MODE_PRIVATE, null);
            // Insert new score record into "SopaDeLletres" (String username, INT score, Date game_duration and Date date(current time))
            database.execSQL("INSERT INTO SopaDeLletres(username, score, game_duration, date) VALUES (" + username + ", " + score + " , " + gameDuration + ", " + dateFormat.format(now) + ");");

            Log.i("AfterGameScoreActivity","Score saved successfully!");

            // Creating a toast
            Toast toast = Toast.makeText(getApplicationContext(), "Puntuació enregistrada!", Toast.LENGTH_SHORT);
            toast.show();

        } finally {
            if (database != null) {
                database.close();
            }
        }
    }
}
