package edu.msu.project1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventListener;

public class MainActivity extends AppCompatActivity {

    private String userId;
    private DatabaseReference mDatabase;

    private boolean joinedLobby = false;
    private boolean ownLobby = false;
    private boolean playing = false;



    public static boolean newTurn = true;
    public static int whosTurn;
    public static int whoamI;
    public static int turnCount;
    public static int winnerInt;

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
        public String pieces;

        public GameStatus() {
            whosTurn = 1;
            turnCount = 0;
            currentGrid = "";
            pieces = "";
        }
    }

    public static Lobby currentLobby;
    public static String key;
    public static GameStatus currentGameStatus;

    public static Button doneButton;
    public static Button undoButton;
    public static Button surrenderButton;
    public static AlertDialog alertDialog;
    public ValueEventListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        if(savedInstanceState != null) {
//            getMainView().getGrid().getFromBundle(PARAMETERS, savedInstanceState);
//            return;
//        }

        Intent myIntent = getIntent(); // gets the previously created intent
        userId = myIntent.getStringExtra("userId");

        doneButton = (Button)findViewById(R.id.doneButton);
        undoButton = (Button)findViewById(R.id.undoButton);
        surrenderButton = (Button)findViewById(R.id.surrenderButton);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle(R.string.joinwaiting);

        // set dialog message
        alertDialogBuilder.setMessage("Just wait.").setCancelable(false);

        alertDialog = alertDialogBuilder.create();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Read from the database
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // This method is called once with the initial value
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    Lobby l = singleSnapshot.getValue(Lobby.class);
                    if ((l.userId1.equals(userId) || l.userId2.equals(userId)) && l.status.equals("playing")) {
                        playing = true;
                        key = singleSnapshot.getKey();
                        resumeGame(l);
                    } else if (l.status.equals("open") && !l.userId1.equals(userId)) {
                        key = singleSnapshot.getKey();
                        joinLobby(l);
                        joinedLobby = true;
                        playing = true;
                        break;
                    } else if (l.userId1.equals(userId) && l.status.equals("open")) {
                        key = singleSnapshot.getKey();
                        ownLobby = true;
                        ownLobby(l);
                    }
                }

                if (!joinedLobby && !ownLobby && !playing) {
                    playing = true;
                    createLobby();
                }
                whoamI = (currentLobby.userId1.equals(userId)) ? 1 : 2;

                listenToChanges();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG2", "Failed to read value.", error.toException());
            }
        });


    }


    private void listenToChanges() {
        listener = mDatabase.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("LISTENING", key);
                Lobby l = dataSnapshot.getValue(Lobby.class);
                if (l.status.equals("playing")) {
                    alertDialog.dismiss();
                    resumeGame(l);
                } else {
                    alertDialog.show();
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG2", "Failed to read value.", error.toException());
            }
        });
    }

    private MainView getMainView() {
        return (MainView) findViewById(R.id.gridView);
    }

    private static final String PARAMETERS = "parameters";

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        getMainView().getGrid().putToBundle(PARAMETERS, outState);
//    }


    private void setPlayers(){
        final TextView changingText = (TextView)findViewById(R.id.currentPlayer);

        if(whosTurn == 1){
            String text = currentLobby.userId1 + "'s turn (Green)";
            changingText.setText(text);
        }else{
            String text = currentLobby.userId2 + "'s turn (White)";
            changingText.setText(text);
        }
    }

    public void changeTurn(View view) {
        turnCount++;
        if (!getMainView().getGrid().invalidMove) {
            whosTurn = (whosTurn == 1) ? 2 : 1;
            getMainView().getGrid().updateMatrix();
            newTurn = true;
            setPlayers();
            updateFirebase();
        } else {
            turnCount--;
        }
        view.invalidate();
    }

    public void undo(View view) {
        if (turnCount > 0) {
            newTurn = true;
            getMainView().getGrid().undo();
            view.invalidate();
        }
    }

    /**
     * determine winner
     */
    public void onSurrender(View view){
//        winnerInt = whosTurn;
        whosTurn = 0 - whosTurn;
        endGame(currentLobby);
    }

    private void ownLobby(Lobby l) {
        alertDialog.show();
        currentLobby = l;
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
        Log.i("Create Lobby", "CREATE");
        Lobby newLobby = new Lobby(userId, new GameStatus());
        currentLobby = newLobby;
        currentGameStatus = newLobby.gamestatus;
        key = mDatabase.push().getKey();
        mDatabase.child(key).setValue(newLobby);
    }

    private void resumeGame(Lobby l) {
        currentLobby = l;
        currentGameStatus = l.gamestatus;
        whosTurn = currentGameStatus.whosTurn;
        if (whosTurn <= 0) {
            endGame(currentLobby);
        }
        Grid.loadFirebasepieces(l.gamestatus.pieces, l.gamestatus.currentGrid);
        turnCount = currentGameStatus.turnCount;
        newTurn = true;
        setPlayers();
        Log.i("Resuming game", currentLobby.gamestatus.currentGrid);
    }

    private void endGame(Lobby l){
        //pass key of table into the intent
        String winnerName = whosTurn == (-1) ? l.userId2 : l.userId1;

        currentLobby.gamestatus.currentGrid = "";
        currentLobby.gamestatus.pieces = "";
//        currentLobby.gamestatus.whosTurn = 0;

        Grid.setPieces(new ArrayList<GamePiece>());
        Grid.setGrid(new int[6][7]);

        mDatabase.child(key).setValue(l);

        mDatabase.child(key).removeEventListener(listener);

        Intent myIntent = new Intent(MainActivity.this, Result.class);

        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        myIntent.putExtra("key", key);  //key of firebase child.
        myIntent.putExtra("winner", winnerName);

        MainActivity.this.startActivity(myIntent);
    }

    private void updateFirebase() {

        currentGameStatus.turnCount = turnCount;
        currentGameStatus.whosTurn = whosTurn;

        String grid = "";
        for (int[] a: getMainView().getGrid().params.currentGrid) {
            grid += Arrays.toString(a) + "-";
        }
        currentGameStatus.currentGrid = grid;

        String pieces = "";
        int numOfPieces = 0;
        for (GamePiece piece: getMainView().getGrid().params.pieces) {
            int[] pos = new int[] {piece.posX, piece.posY};
            if (numOfPieces == 0 && piece.posY == 0) {
                numOfPieces++;
                continue;
            }
            pieces += Integer.toString(piece.posX) + "x" + Integer.toString(piece.posY) + "y,";
        }

        currentGameStatus.pieces = pieces;
        currentLobby.gamestatus = currentGameStatus;

        mDatabase.child(key).setValue(currentLobby);
    }
}
