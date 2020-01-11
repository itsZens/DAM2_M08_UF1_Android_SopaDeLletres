package edu.fje.dam2.m08_uf1_android_sopadelletres;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;
import android.text.InputType;
import android.util.Log;
import java.time.Instant;
import java.time.Duration;


public class PlayActivity extends AppCompatActivity {

    // Input player name inserted on an AlertDialog
    public String username;
    public int score;
    public Instant startGame;
    public Instant finishGame;
    public long gameDurationSeconds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        //Asking for username to the user
        // Input username on start a game
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Nom del jugador");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        alertDialog.setView(input);

        // Set up the buttons
        alertDialog.setPositiveButton("Acceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                username = input.getText().toString();
                gameSopaDeLletres();
            }
        });

        alertDialog.setNegativeButton("Cancel·lar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = new Intent(PlayActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        alertDialog.setCancelable(false);
        alertDialog.show();

    }

    public void gameSopaDeLletres(){
        // Get time when starts the game
        startGame = Instant.now();



        /* Game code here */


        // When all words are found
        // Get time when finishes the game
        finishGame = Instant.now();

        // Calculating the duration of the game in seconds
        gameDurationSeconds = Duration.between(startGame, finishGame).getSeconds();
        Log.i("PlayActivity", "Duration: " + gameDurationSeconds);

        // Call function goToAfterGameScoreActivity() to show the final score and go to AfterGameScoreActivity
        goToAfterGameScoreActivity();

    }

    public void goToAfterGameScoreActivity(){
        // Passing username, score and game duration to AfterGameScoreActivity
        Intent intent = new Intent(PlayActivity.this, AfterGameScoreActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("score", score);
        intent.putExtra("gameDuration", gameDurationSeconds);
        startActivity(intent);
        finish();
    }

}
