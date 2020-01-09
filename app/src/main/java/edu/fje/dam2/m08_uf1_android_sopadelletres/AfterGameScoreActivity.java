package edu.fje.dam2.m08_uf1_android_sopadelletres;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

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
}
