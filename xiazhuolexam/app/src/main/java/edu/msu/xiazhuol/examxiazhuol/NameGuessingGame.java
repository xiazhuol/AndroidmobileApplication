package edu.msu.xiazhuol.examxiazhuol;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class NameGuessingGame extends AppCompatActivity {


    public static final String GUESS = "guess";
    public static final String COUNT = "count";
    public static final String GENDER = "gender";
    private Names names = new Names();
    private String Guessname;
    private boolean gender;
    private int count = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_guessing_game);


        names.choose();


        boolean girl = names.isGirl();
        gender = girl;
        String name = names.getName();
        Guessname = name;
        if(savedInstanceState != null){
            Guessname = savedInstanceState.getString(GUESS);
            count =savedInstanceState.getInt(COUNT);
            gender = savedInstanceState.getBoolean(GENDER);
        }
        Log.i(Guessname,"name");

        final ImageView image = (ImageView)findViewById(R.id.imageView2);
        if(gender == true){
            image.setImageResource(R.drawable.girl);
            //Log.i(Guessname,"name");

        }
        else{
            image.setImageResource(R.drawable.boy);
            //Log.i(Guessname,"name");
        }


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(COUNT,count);
        outState.putString(GUESS,Guessname);
        outState.putBoolean(GENDER,gender);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Guessname = savedInstanceState.getString(GUESS);
        count = savedInstanceState.getInt(COUNT);
        gender = savedInstanceState.getBoolean(GENDER);

    }


    @SuppressLint("SetTextI18n")
    public void GuessButton(View view){


        EditText Name = findViewById(R.id.NameText);
        String n1 = Name.getText().toString();

        int l_guess  = Guessname.length();
        int l_name = n1.length();
        int comp = n1.compareToIgnoreCase(Guessname);

       // boolean result = checkName(n1,Guessname);
        if(comp == 0){
            if(gender == true) {
                Intent myIntent = new Intent(this, result.class);
                String myname = "My name is "+Guessname;
                String time = "Guessed in " + Integer.toString(count)+" Guesses";
                //myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                myIntent.putExtra("resid", R.drawable.girl);
                myIntent.putExtra("name",myname.toString());
                myIntent.putExtra("give",time.toString());
                startActivity(myIntent);
            }
            else{
                Intent myIntent = new Intent(this, result.class);
                String time = "Guessed in " + Integer.toString(count)+" Guesses";
                //myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                myIntent.putExtra("resid", R.drawable.boy);
                String myname = "My name is "+Guessname;
                myIntent.putExtra("name",myname.toString());
                myIntent.putExtra("give",time.toString());
                startActivity(myIntent);
            }
        }
        else if(comp < 0){
            if(l_name < l_guess){
                TextView wrong = findViewById(R.id.GuessWrong);
                wrong.setText(R.string.lowshort);
                count +=1;
            }
            else if(l_name > l_guess){
                TextView wrong = findViewById(R.id.GuessWrong);
                wrong.setText(R.string.lowlong);
                count +=1;
            }
            else{
                TextView wrong = findViewById(R.id.GuessWrong);
                wrong.setText(R.string.low);
                count +=1;
            }


        }
        else{
            if(l_name < l_guess){
                TextView wrong = findViewById(R.id.GuessWrong);
                wrong.setText(R.string.highshort);
                count +=1;
            }
            else if(l_name > l_guess){
                TextView wrong = findViewById(R.id.GuessWrong);
                wrong.setText(R.string.highlong);
                count +=1;
            }
            else{
                TextView wrong = findViewById(R.id.GuessWrong);
                wrong.setText(R.string.high);
                count +=1;
            }


        }

    }
    public void GiveupButton(View view){
        if(gender == true) {
            Intent myIntent = new Intent(this, result.class);
            String myname = "My name is "+Guessname;
            String giveup = "You gave up!";
            myIntent.putExtra("resid", R.drawable.girl);
            myIntent.putExtra("name",myname.toString());
            myIntent.putExtra("give",giveup);
            startActivity(myIntent);
        }
        else{
            Intent myIntent = new Intent(this, result.class);
            //myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            String myname = "My name is "+Guessname;
            String giveup = "You gave up!";
            myIntent.putExtra("resid", R.drawable.boy);
            myIntent.putExtra("name",myname.toString());
            myIntent.putExtra("give",giveup);
            startActivity(myIntent);
        }
    }

}

