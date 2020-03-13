package edu.msu.project1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class DummyActivity extends AppCompatActivity {

    private String userId;
    private DatabaseReference mDatabase;

    private boolean joinedLobby = false;
    private boolean ownLobby = false;
    private boolean playing = false;

    public static boolean newTurn = true;

    @IgnoreExtraProperties
    public static class Lobby {

        public String userId1;
        public String userId2;
        public String status;
        public GameStatus gamestatus;

        public Lobby() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public Lobby(String userId1, GameStatus gamestatus) {
            this.userId1 = userId1;
            this.status = "open";
            this.userId2 = "";
            this.gamestatus = gamestatus;
        }
    }

    public static class GameStatus {
        public Integer whosTurn;
        public Integer turnCount;
        public String currentGrid;

        public GameStatus() {
            whosTurn = 0;
            turnCount = 1;
        }
    }

    public Lobby currentLobby;
    public String key;
    public GameStatus currentGameStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);
    }

    private MainView getMainView() {
        return (MainView) findViewById(R.id.gridView);
    }

    private void setPlayers(){
        final TextView changingText = (TextView)findViewById(R.id.currentPlayer);

        if(currentGameStatus.whosTurn == 1){
            String text = currentLobby.userId1 + "'s turn (Green)";
            changingText.setText(text);
        }else{
            String text = currentLobby.userId2 + "'s turn (White)";
            changingText.setText(text);
        }
    }

    public void changeTurn(View view) {
        currentGameStatus.turnCount++;
        currentGameStatus.whosTurn = (currentGameStatus.whosTurn == 1) ? 2 : 1;

        setPlayers();
        updateFirebase();
        String grid = "";
        for (int[] a: getMainView().getGrid().params.currentGrid) {
            grid += Arrays.toString(a);
        }
        currentGameStatus.currentGrid = grid;
        getMainView().getGrid().updateMatrix();
        if (!getMainView().getGrid().invalidMove) {
            newTurn = true;
            setPlayers();
            if(currentGameStatus.whosTurn==0){
                currentGameStatus.turnCount = 0;
                newTurn = true;
                getMainView().getGrid().hasWon = true;
                Log.i("WINNER", "WINNER");
//                Intent myIntent = new Intent(MainActivity.this, Result.class);
//                myIntent.putExtra("winner", currentGameStatus.whosTurn == 1 ? mPlayer1 : mPlayer2);
//                MainActivity.this.startActivity(myIntent);
            }
        } else {
            currentGameStatus.turnCount--;
            currentGameStatus.whosTurn = (currentGameStatus.whosTurn == 1) ? 2 : 1;
        }
        view.invalidate();
    }

    private void joinLobby(Lobby l) {
        l.userId2 = userId;
        l.status = "playing";
        currentLobby = l;
        currentGameStatus = l.gamestatus;
        mDatabase.child(key).setValue(l);
        Log.i("Joining lobby", "join");
    }

    private void createLobby() {
        Lobby newLobby = new Lobby(userId, new GameStatus());
        currentLobby = newLobby;
        currentGameStatus = newLobby.gamestatus;
        key = mDatabase.push().getKey();
        mDatabase.child(key).setValue(newLobby);
    }

    private void resumeGame(Lobby l) {
        currentLobby = l;
        currentGameStatus = l.gamestatus;
        Log.i("Resuming game", "Resume");
    }

    private void updateFirebase() {

        currentLobby.gamestatus = currentGameStatus;

        mDatabase.child(key).setValue(currentLobby);
    }
}
