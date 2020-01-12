package edu.fje.dam2.m08_uf1_android_sopadelletres;




import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.*;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;
import android.util.Log;


/**
 * Classe que hereta de la classe Activity i que mostra com
 * utilitzar un giny GridView per a mostrar diverses opcions en forma de graella
 * Implementa la interfície  AdapterView.OnItemSelectedListener
 * Es defineix un descriptor XML per cadascuna de les cel·les, el qual és un TextView
 * Utilitza un ArrayAdapter que permet utilitzar el patró MVC
 * @author sergi.grau@fje.edu
 * @version 1.0, 22/11/2012
 */

public class M17_CellesActivity extends Activity  {
    GridView gridview;

    public   String[] items = {String.valueOf('A'), String.valueOf('B'), String.valueOf('C'), String.valueOf('D'),  String.valueOf('E'), String.valueOf('F'),  String.valueOf('G'),  String.valueOf('H'),  String.valueOf('I'),  String.valueOf('J'),  String.valueOf('K'),
            String.valueOf('L'), String.valueOf('M'),  String.valueOf('N'), String.valueOf('O'), String.valueOf('P'),  String.valueOf('Q'),  String.valueOf('R'),  String.valueOf('S'),  String.valueOf('T'),  String.valueOf('U'), String.valueOf('V'),  String.valueOf('W'),
            String.valueOf('X'),  String.valueOf('Y'),  String.valueOf('Z')};
    String item_clicked;

    public String [] words = { "Ferran", "Android", "Computer", "keyboard", "Huawei", "Mouse", "RaspBerry", "Sergi", "Camera", "Mobile", "Jazz"};
    public String [][] matriu = new String[10][10];
    public ArrayList<String> matriu_Mostrar = new ArrayList<String>();
    Random numAleatori = new Random();
    public boolean isLenght = false;
    public boolean isHoritzontal = false;
    public boolean isVertical = false;
    public boolean isDiagonal = false;
    public Hashtable<Integer, String> solucio_matriu = new Hashtable<Integer, String>();
    int valor =0;
    String verificarParaula = null;
    String paraulaSolucio  = null;




    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_m17_celles);

        gridview = (GridView)findViewById(R.id.grid);


            for (int i = 0; i < 10; i++) {

                for (int j = 0; j < 10; j++) {
                    matriu[i][j] = i + "" + j;
                    matriu_Mostrar.add(matriu[i][j]);

                }
            }

            for(int z = 0; z < words.length ; z++) {
                isVertical = false;
                isDiagonal = false;
                isHoritzontal = false;
                isLenght = false;

                verificarInputWord(words[z]);
                 if(isVertical) {
                        Log.v("He entrat", "n0");
                     solucio_matriu.put(valor, words[z]);
                        for (int x = 0; x < words[z].length() ; x++) {

                            if (x == 0) {
                                matriu_Mostrar.set(valor, String.valueOf(words[z].charAt(x)).toUpperCase());
                                Log.v("M17_CellesActivity", matriu_Mostrar.get(valor));

                            } else {
                                matriu_Mostrar.set(valor + (x * 10), String.valueOf(words[z].charAt(x)).toUpperCase());
                                Log.v("M17_CellesActivity", matriu_Mostrar.get((valor + (x * 10))));

                            }


                        }


                 }else if(isHoritzontal) {
                        Log.v("He entrat", "n1");
                     solucio_matriu.put(valor, words[z]);

                        for (int x = 0; x < words[z].length(); x++) {

                            if( x == 0) {

                                matriu_Mostrar.set(valor, String.valueOf(words[z].charAt(x)).toUpperCase());
                                Log.v("M17_CellesActivity", matriu_Mostrar.get(valor));


                            }else {
                                matriu_Mostrar.set(valor + x , String.valueOf(words[z].charAt(x)).toUpperCase());
                                Log.v("M17_CellesActivity", matriu_Mostrar.get(valor + x));

                            }

                        }

                 } else if( isDiagonal) {
                     Log.v("M17_CellesActivity", "Diagonal");
                     solucio_matriu.put(valor, words[z]);
                     for (int x = 0; x < words[z].length(); x++) {

                         if( x == 0) {

                             matriu_Mostrar.set(valor, String.valueOf(words[z].charAt(x)).toUpperCase());
                             Log.v("M17_CellesActivity", matriu_Mostrar.get(valor));


                         }else {
                             matriu_Mostrar.set(valor + (x*11) , String.valueOf(words[z].charAt(x)).toUpperCase());
                             Log.v("M17_CellesActivity", matriu_Mostrar.get(valor + x));

                         }

                     }


                 }




            }








        List<String> ROW_LIST = new ArrayList<String>(matriu_Mostrar);

        gridview.setAdapter(new ArrayAdapter<String>(M17_CellesActivity.this, android.R.layout.simple_list_item_1,ROW_LIST));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                try {
                    paraulaSolucio = solucio_matriu.get(position);
                    verificarParaula += parent.getItemAtPosition(position).toString();
                    Log.v("M17_CellesActivity",parent.getItemAtPosition(position).toString());
                    Log.v("M17_CellesActivity", solucio_matriu.get(position));

                }catch (Exception ex) {

                    if(paraulaSolucio.length() != verificarParaula.length()) {
                        for(int i = 0; i < verificarParaula.length(); i++) {
                            if(String.valueOf(verificarParaula.charAt(i)).toUpperCase() != String.valueOf(paraulaSolucio.charAt(i)).toUpperCase()){
                                verificarParaula = "";
                                break;
                            }
                        }
                    }else if( paraulaSolucio.length() == verificarParaula.length()){

                        if(paraulaSolucio.toUpperCase() == verificarParaula.toUpperCase()) {
                            Log.v("Iguals", "Iguals");
                        }

                    }

                }



               // item_clicked = parent.getItemAtPosition(position).toString();

              // Toast.makeText(M17_CellesActivity.this, item_clicked, Toast.LENGTH_LONG).show();

            }
        });
    }

    public void verificarInputWord(String Word) {
            isVertical = false;
            isHoritzontal = false;
            isDiagonal = false;
            valor = numAleatori.nextInt(100);
            int n0 = 0;
            int n1 = valor;

            if( valor >= 10) {

                n0 = Integer.parseInt(String.valueOf(String.valueOf(valor).charAt(0)));
                n1 = Integer.parseInt(String.valueOf(String.valueOf(valor).charAt(1)));


            }


            if( Word.length() + n0 <= 9) {
                isVertical = true;

                verificarCasellaMatriu(valor, Word);

            }else {
                if (Word.length() + n1 <= 9) {
                    isHoritzontal = true;
                    verificarCasellaMatriu(valor, Word);

                }else {
                    isDiagonal = true;
                    Log.v("M17_CellesActivity", "Diagonal");
                    verificarCasellaMatriu(valor, Word);
                    //verificarInputWord(Word);
                }

            }


    }

    public void verificarCasellaMatriu(int valor, String Word) {
                if(isHoritzontal){
                    int nLetters = 0;
                    int x = 0;
                    for (x = 0; x < Word.length(); x++) {


                       if( x == 0) {
                           if(!Character.isLetter(matriu_Mostrar.get(valor).charAt(0))){
                               nLetters++;
                           }


                        }else {
                           if(!Character.isLetter(matriu_Mostrar.get(valor + x).charAt(0))){
                               nLetters++;
                           }


                        }

                    }
                    Log.v("M17_CellesActivity", "nLetters: "+nLetters + " X: "+ x);
                    if(nLetters != x){
                        Log.v("M17_CellesActivity", "false");

                        isLenght = false;
                        verificarInputWord(Word);

                    }else {
                        Log.v("M17_CellesActivity", "true");

                        isLenght = true;
                    }
                }else{
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
                        Log.v("M17_CellesActivity", "nLetters: "+nLetters + "X: "+ x);

                        if(nLetters != x){
                            Log.v("M17_CellesActivity", "false");

                            isLenght = false;
                            verificarInputWord(Word);
                        }else {
                            Log.v("M17_CellesActivity", "true");

                            isLenght = true;
                        }
                    }else {
                        if( isDiagonal) {
                            int x = 0;
                            int nLetters = 0;
                            for (x = 0; x < Word.length(); x++) {

                                if( x == 0) {
                                    if(!Character.isLetter(matriu_Mostrar.get(valor).charAt(0))){
                                        nLetters++;

                                   //matriu_Mostrar.set(valor, String.valueOf(words[z].charAt(x)).toUpperCase());
                                    //Log.v("M17_CellesActivity", matriu_Mostrar.get(valor));


                                }else {
                                        if(!Character.isLetter(matriu_Mostrar.get(valor + (x *11)).charAt(0))){
                                            nLetters++;
                                        }
                                    //matriu_Mostrar.set(valor + (x*11) , String.valueOf(words[z].charAt(x)).toUpperCase());
                                    //Log.v("M17_CellesActivity", matriu_Mostrar.get(valor + x));
                                    }

                                }
                            }
                            if(nLetters != x){
                                Log.v("M17_CellesActivity", "false");

                                isLenght = false;
                                verificarInputWord(Word);
                            }else {
                                Log.v("M17_CellesActivity", "true");

                                isLenght = true;
                            }

                        }else{
                            verificarInputWord(Word);

                        }

                    }
                }

    }
}