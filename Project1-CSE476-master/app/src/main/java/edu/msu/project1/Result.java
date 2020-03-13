package edu.msu.project1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Result extends AppCompatActivity {

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Intent intent = getIntent();
        String key = (String)intent.getStringExtra("key");
        String winner = (String)intent.getStringExtra("winner");
        setWinner(winner);
        mDatabase.child(key).setValue(null);
    }

    private void setWinner(String winner){
        TextView changingText = (TextView)findViewById(R.id.winnerText);
        changingText.setText(winner);
    }

    public void onRestart(View view){
        Intent myIntent = new Intent(Result.this, InitialActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(myIntent);


    }
}
