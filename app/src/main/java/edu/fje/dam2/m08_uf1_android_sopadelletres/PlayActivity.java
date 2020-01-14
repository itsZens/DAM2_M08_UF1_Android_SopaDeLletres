package edu.fje.dam2.m08_uf1_android_sopadelletres;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;
import android.text.InputType;
import android.util.Log;
import java.time.Instant;
import java.time.Duration;
import java.util.ArrayList;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Random;
import java.util.Hashtable;
import android.os.Handler;
import android.view.View;
import java.util.List;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.graphics.Color;
import android.graphics.Paint;

import android.provider.ContactsContract;
import android.provider.Settings;
import android.net.Uri;




public class PlayActivity extends AppCompatActivity {

    // Input player name inserted on an AlertDialog
    public String username;

    public int score;
    public Instant startGame;
    public Instant finishGame;
    public long gameDurationSeconds;


    // Variables for the generation of the game grid
    GridView gridview;

    public   String[] items = {String.valueOf('A'), String.valueOf('B'), String.valueOf('C'), String.valueOf('D'),  String.valueOf('E'), String.valueOf('F'),  String.valueOf('G'),  String.valueOf('H'),  String.valueOf('I'),  String.valueOf('J'),  String.valueOf('K'),
            String.valueOf('L'), String.valueOf('M'),  String.valueOf('N'), String.valueOf('O'), String.valueOf('P'),  String.valueOf('Q'),  String.valueOf('R'),  String.valueOf('S'),  String.valueOf('T'),  String.valueOf('U'), String.valueOf('V'),  String.valueOf('W'),
            String.valueOf('X'),  String.valueOf('Y'),  String.valueOf('Z'), String.valueOf('A'), String.valueOf('B'), String.valueOf('C'), String.valueOf('D'),  String.valueOf('E'), String.valueOf('F')};
    String item_clicked;

    //public String [] words = {"Android", "Computer", "keyboard", "Huawei", "Mouse", "RaspBerry", "Sergi", "Camera", "Mobile", "Jazz"};
    public String [] words;

    public String [][] matriu = new String[10][10];
    public ArrayList<String> matriu_Mostrar = new ArrayList<String>();
    Random numAleatori = new Random();
    public boolean isLenght = false;
    public boolean isHoritzontal = false;
    public boolean isVertical = false;
    public Hashtable<Integer, String> solucio_matriu = new Hashtable<Integer, String>();
    int valor =0;
    String verificarParaula = "";
    String paraulaSolucio  = null;
    public ArrayList<Integer> pocicio = new ArrayList<Integer>();
    public LinearLayout linearLayout;
    public TextView txV = null;
    public int num = 0;
    final Handler handler = new Handler();


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

        //Start game function
        gridview = findViewById(R.id.grid);
        txV = findViewById(R.id.timeTextView);
        linearLayout = findViewById(R.id.linearLayout);

        gameSopaDeLletres();

    }

    public void gameSopaDeLletres(){
        // Get time when starts the game
        startGame = Instant.now();

        try{
            // Getting 8 contacts' name into an ArrayList<String> (max length of the name => 7)
            ArrayList<String> contacts = new ArrayList<String>();
            int numContacts = 0;
            Cursor cursorContacts = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
            while (cursorContacts.moveToNext()){
                String name = cursorContacts.getString(cursorContacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                if(numContacts != 8){
                    if(name.length() <= 7){
                        String[] nameSplit = name.split(" ");
                        contacts.add(nameSplit[0].toUpperCase());
                        numContacts++;
                    }
                }
            }

            cursorContacts.close();

            words = new String[contacts.size()];
            for(int i = 0; i < contacts.size(); i++){
                words[i] = contacts.get(i);
            }

        } catch (SecurityException e){
            // AlertDialog asking for access permissions to contacts
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setMessage(R.string.permissionsContactsDialog);

            // Set up the buttons
            alertDialog.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                    finish();
                }
            });

            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
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


        /* Game code here */

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                chrono(startGame);
                handler.postDelayed(this, 1000);
            }
        }, 1000);

        for (int z = 0; z < words.length; z ++){
            TextView tv = new TextView(this);
            tv.setText(words[z]);
            tv.setId(z);

            linearLayout.addView(tv);

        }


        for (int i = 0; i < 10; i++) {

            for (int j = 0; j < 10; j++) {
                matriu[i][j] = i + "" + j;
                matriu_Mostrar.add(matriu[i][j]);
            }
        }

        for(int z = 0; z < words.length ; z++) {
            isVertical = false;
            isHoritzontal = false;
            isLenght = false;

            verificarInputWord(words[z]);
            if(isVertical) {
                Log.v("He entrat", "n0");
                solucio_matriu.put(valor, words[z]);
                for (int x = 0; x < words[z].length() ; x++) {

                    if (x == 0) {
                        matriu_Mostrar.set(valor, String.valueOf(words[z].charAt(x)).toUpperCase());
                    } else {
                        matriu_Mostrar.set(valor + (x * 10), String.valueOf(words[z].charAt(x)).toUpperCase());
                    }
                }

            } else if(isHoritzontal) {
                Log.v("He entrat", "n1");
                solucio_matriu.put(valor, words[z]);

                for (int x = 0; x < words[z].length(); x++) {

                    if( x == 0) {
                        matriu_Mostrar.set(valor, String.valueOf(words[z].charAt(x)).toUpperCase());
                        Log.v("M17_CellesActivity", matriu_Mostrar.get(valor));

                    } else {
                        matriu_Mostrar.set(valor + x , String.valueOf(words[z].charAt(x)).toUpperCase());

                    }
                }

            } else {

                verificarInputWord(words[z]);
            }

            final List<String> ROW_LIST = new ArrayList<String>(matriu_Mostrar);

            gridview.setAdapter(new ArrayAdapter<String>(PlayActivity.this, android.R.layout.simple_list_item_1,ROW_LIST));

            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    view.setBackgroundColor(Color.parseColor("#ff0000"));

                    if (solucio_matriu.get(position) != null){
                        paraulaSolucio = solucio_matriu.get(position).toUpperCase();
                        int x = 0;
                        while (words[x] != solucio_matriu.get(position)) x++;
                        num = x;

                    } if(parent.getItemAtPosition(position).toString() != null) {
                        verificarParaula += parent.getItemAtPosition(position).toString();
                        pocicio.add(position);
                    }

                    if(paraulaSolucio.length() != verificarParaula.length()) {
                        //Log.v("M17_CellesActivity","ParaulaV: "+paraulaSolucio.length()+" VerficaP: "+verificarParaula.length());

                        for(int i = 0; i < verificarParaula.length(); i++) {
                            Log.v("M17_CellesActivity","ParaulaV: "+paraulaSolucio.charAt(i)+" VerficaP: "+verificarParaula.charAt(i));

                            if(verificarParaula.charAt(i) != paraulaSolucio.charAt(i)){
                                Log.v("M17_CellesActivity","Reset");

                                verificarParaula = "";
                                pocicio.clear();
                            }
                        }
                    }else  if( paraulaSolucio.length() == verificarParaula.length()){
                        int i = 0;
                        for(int x = 0; x< paraulaSolucio.length(); x++){
                            if(paraulaSolucio.charAt(x) == verificarParaula.charAt(x)) i++;
                        } if(i == paraulaSolucio.length()){
                            Log.v("M17_CellesActivity", "Iguals");
                            for(int x = 0; x< paraulaSolucio.length(); x++){
                                gridview.getChildAt(pocicio.get(x)).setBackgroundColor(Color.parseColor("#0cce6d"));
                            }

                            Log.v("M17_CellesActivity", "ParaulaT: "+paraulaSolucio);

                            //while (paraulaSolucio.toLowerCase() != words[num].toLowerCase()) num++;
                            RecuperarTextView(num);
                            // txV =  txV.findViewWithTag(paraulaSolucio);
                        }

                        verificarParaula = "";
                        pocicio.clear();

                    }
                }
            });
        }

        // When all words are found
        // Get time when finishes the game
        finishGame = Instant.now();

        // Calculating the duration of the game in seconds
        gameDurationSeconds = Duration.between(startGame, finishGame).getSeconds();
        Log.i("PlayActivity", "Duration: " + gameDurationSeconds);

        // Call function goToAfterGameScoreActivity() to show the final score and go to AfterGameScoreActivity
        //goToAfterGameScoreActivity();

    }


    public void verificarInputWord(String Word) {
        isVertical = false;
        isHoritzontal = false;

        valor = numAleatori.nextInt(100);
        int n0 = 0;
        int n1 = valor;

        if(valor >= 10) {

            n0 = Integer.parseInt(String.valueOf(String.valueOf(valor).charAt(0)));
            n1 = Integer.parseInt(String.valueOf(String.valueOf(valor).charAt(1)));
        }


        if( Word.length() + n0 <= 9) {
            isVertical = true;
            verificarCasellaMatriu(valor, Word);

        } else {
            if (Word.length() + n1 <= 9) {
                isHoritzontal = true;
                verificarCasellaMatriu(valor, Word);

            } else {
                verificarInputWord(Word);
            }

        }

    }

    public void RecuperarTextView(Integer num ) {
        TextView tx = this.findViewById(num);
        tx.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }

    public void verificarCasellaMatriu(int valor, String Word) {
        if(isHoritzontal){
            int nLetters = 0;
            int x = 0;
            for (x = 0; x < Word.length(); x++) {


                if(x == 0) {
                    if(!Character.isLetter(matriu_Mostrar.get(valor).charAt(0))){
                        nLetters++;
                    }

                } else {
                    if(!Character.isLetter(matriu_Mostrar.get(valor + x).charAt(0))){
                        nLetters++;
                    }
                }
            }

            if(nLetters != x){
                isLenght = false;
                verificarInputWord(Word);

            } else {
                isLenght = true;
            }

        } else {
            if (isVertical) {
                int x = 0;
                int nLetters = 0;
                for (x = 0; x < Word.length() ; x++) {

                    if (x == 0) {
                        if(!Character.isLetter(matriu_Mostrar.get(valor).charAt(0))){
                            nLetters++;
                        }

                    } else {
                        if(!Character.isLetter(matriu_Mostrar.get(valor + (x *10)).charAt(0))){
                            nLetters++;
                        }
                    }
                }

                if(nLetters != x){
                    isLenght = false;
                    verificarInputWord(Word);

                }else {
                    isLenght = true;
                }

            } else {
                verificarInputWord(Word);
            }
        }

    }


    public void chrono(Instant start) {
        Instant now = Instant.now();
        long seconds = Duration.between(start,now).getSeconds();

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
