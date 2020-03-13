package edu.msu.xiazhuol.examxiazhuol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class result extends AppCompatActivity {

    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Bundle bundle = getIntent().getExtras();
        if(bundle!= null) {
            int resid = bundle.getInt("resid");
            image = (ImageView) findViewById(R.id.imageView);
            image.setImageResource(resid);

            //String name = bundle.get();


        }
        Intent intent = getIntent();
        String myname = intent.getStringExtra("name");
        TextView name = findViewById(R.id.Myname);
        name.setText(myname);


        String GuessT = intent.getStringExtra("give");
        TextView guess = findViewById(R.id.time);
        guess.setText(GuessT);


    }

    public void newGame(View view){
        Intent myIntent = new Intent(this, NameGuessingGame.class);
        startActivity(myIntent);
    }

}
