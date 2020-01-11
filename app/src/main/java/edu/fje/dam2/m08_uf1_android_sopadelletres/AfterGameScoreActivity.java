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
    public long gameDuration;
    public DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    public DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_game_score);


        //Getting data (username, score and game duration) from PlayActivity
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        score = intent.getIntExtra("score", 0);
        gameDuration = intent.getLongExtra("gameDuration", 0);


        // Showing score into textView (id: scoreView)
        scoreView = findViewById(R.id.scoreView);
        scoreView.setText(String.valueOf(score));


        // Button "Menú principal"
        backToMainMenu = findViewById(R.id.backToMainMenu);
        backToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                // Calling function saveScoreToDatabase()
                saveScoreToDatabase();

                // Return to MainActivity
                Intent intent = new Intent(AfterGameScoreActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    protected void saveScoreToDatabase(){
        // Database SQLite connection
        SQLiteDatabase database = null;

        // Get current time
        Date now = new Date();


        try {
            // Creating the table "SopaDeLletres"
            database = this.openOrCreateDatabase("SopaDeLletres", MODE_PRIVATE, null);
            // Insert new score record into "SopaDeLletres" (String username, INT score, Date game_duration and Date date(current time))
            try{
                database.execSQL("INSERT INTO SopaDeLletres(username, score, game_duration, date) VALUES (" + username + ", " + score + " , " + gameDuration + ", " + dateFormat.format(now) + ");");
            } catch (Exception e){
                String message = "Values: " + username + " " + score + " " + gameDuration + " " + dateFormat.format(now);
                Log.i("AfterGameScoreActivity", message);
            }

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
