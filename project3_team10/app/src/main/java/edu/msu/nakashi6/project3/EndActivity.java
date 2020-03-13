package edu.msu.nakashi6.project3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class EndActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
    }

    public void onNewGame(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
