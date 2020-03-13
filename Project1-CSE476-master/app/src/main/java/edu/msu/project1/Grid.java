package edu.msu.project1;

import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;



public class Grid {
    final static float SCALE_IN_VIEW = 1f;

    private boolean first = true;

    public static int gridSize;

    /**
     * one section of grid
     */
    private Bitmap singleGridSpace;

    /**
     * Left margin in pixels
     */
    private int marginX;

    /**
     * The current parameters
     */
    public static Parameters params = new Parameters();

    /**
     * Top margin in pixels
     */
    private int marginY;
    /*
     * Canvas declaration
     */
    private Canvas canvas;

    public float getScaleFactor() {
        return scaleFactor;
    }

    /*
     * Get where user clicked the screen
     */
    private float clickX;
    private float clickY;

    private static View mView;


    /**
     * how much we scale pieces and grid
     */
    private float scaleFactor;
    /**
     * The name of the bundle keys to save the puzzle
     */
    private final static String LOCATIONS = "Grid.locations";
    private final static String IDS = "Grid.ids";

    /**
     * This variable is set to a piece we are dragging. If
     * we are not dragging, the variable is null.
     */
    private GamePiece dragging = null;

   /* public float getX() {
        return x;
    }*/

    private float x = .072f;

   /* public float getY() {
        return y;
    }*/

    private float y = .072f;
    private static Context mContext;

    public boolean invalidMove = false;


    /**
     * Local class to handle the touch status for one touch.
     * We will have one object of this type for each of the
     * two possible touches.
     */
    private class Touch {
        /**
         * Touch id
         */
        public int id = -1;

        /**
         * Current x location
         */
        public float x = 0;

        /**
         * Current y location
         */
        public float y = 0;

        /**
         * Previous x location
         */
        public float lastX = 0;

        /**
         * Previous y location
         */
        public float lastY = 0;
        /**
         * Change in x value from previous
         */
        public float dX = 0;

        /**
         * Change in y value from previous
         */
        public float dY = 0;
        /**
         * Copy the current values to the previous values
         */
        public void copyToLast() {
            lastX = x;
            lastY = y;
        }
        /**
         * Compute the values of dX and dY
         */
        public void computeDeltas() {
            dX = x - lastX;
            dY = y - lastY;
        }
    }
    /**
     * First touch status
     */
    private Touch touch1 = new Touch();

    /**
     * Second touch status
     */
    private Touch touch2 = new Touch();



    public Grid(View gridView, Context context) {
        singleGridSpace = BitmapFactory.decodeResource(context.getResources(), R.drawable.empty_space);
        mContext = context;
        mView = gridView;
        hasWon = false;
        MainActivity.newTurn = true;

    }

    public boolean hasWon = false;
    /**
     * Vars to save in event of activity destroyed
     *
     */
    public static class Parameters implements Serializable {
        int[][] currentGrid = new int[6][7];
        ArrayList<GamePiece> pieces = new ArrayList<GamePiece>();
    }

    public void putToBundle(String key, Bundle bundle) {
        if(!hasWon) {
            bundle.putSerializable(key, params);
        }
    }

    public void getFromBundle(String key, Bundle bundle){
        MainActivity.newTurn = false;
        params = (Parameters)bundle.getSerializable(key);
        setGrid(params.currentGrid);
        setPieces(params.pieces);

    }

    public static void setGrid(int[][] grid){
        params.currentGrid = grid;
        // idk if the above code will work.. might need a for loop to do it?
        mView.invalidate();
    }

    public static void setPieces(ArrayList<GamePiece> p) {
        params.pieces = p;
        mView.invalidate();
    }


    public void draw(Canvas canvas) {
        this.canvas = canvas;
        int wid = canvas.getWidth();
        int hit = canvas.getHeight();

       // canvas.scale(gridScale,gridScale);

        //min of two dim
        int minDim = wid < hit ? wid : hit;
       // int orientation = mContext.getResources().getConfiguration().orientation;
        //if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            // In landscape
            //gridSize = (int) (wid * 0.8f * SCALE_IN_VIEW);
//
       // } //else {
//            // In portrait
//
      //  }
        gridSize = (int) (minDim * SCALE_IN_VIEW);


        marginX = (minDim - gridSize) / 2;
        marginY = (minDim - gridSize) / 2;
        if (minDim == wid) {
            scaleFactor = (float) gridSize / ((float) singleGridSpace.getWidth() * 7);
        } else {
            scaleFactor = (float) gridSize / ((float) singleGridSpace.getHeight() * 7);
        }


        canvas.save();
        canvas.translate(marginX, marginY);
        canvas.scale(scaleFactor, scaleFactor);
        canvas.restore();

        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                drawGridPiece(canvas,
                        i * (gridSize / 7), j * (gridSize / 7), gridSize, scaleFactor);
            }
        }

        instantiatePieces(canvas);
    }


    public void updateMatrix() {

        GamePiece piece = params.pieces.get(params.pieces.size()-1);
        Log.i("POSITION", Integer.toString(piece.posX) + "x"+Integer.toString(piece.posY) + "y");
        for(int i = params.currentGrid.length-1; i >= 0; i--) {
            Log.i("PIECE", Integer.toString(params.currentGrid[i][piece.posX]));
            if (params.currentGrid[i][piece.posX] == 0) {
                piece.increaseY(i-piece.posY, gridSize);
                Log.i("AFTER INCREASE", Integer.toString(piece.posX) + "x"+Integer.toString(piece.posY) + "y");
                mView.invalidate();
                invalidMove = false;
                break;
            }
            if (i == 0 || MainActivity.newTurn) {
                Log.i("INVALID", "INVALID");
                invalidMove = true;
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(mContext, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(mContext);
                }
                builder.setTitle("Invalid Move.")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        }

        if (!invalidMove) {
            params.currentGrid[piece.posY][piece.posX] = MainActivity.whosTurn;
            if(isWinner(MainActivity.whosTurn, piece.posX, piece.posY)){
                Log.i("WINNER", "WINNER");
                MainActivity.whosTurn = 0 - MainActivity.whosTurn;
                hasWon = true;
            }
            mView.invalidate();
        }
    }

    /**
     * Get the positions for the two touches and put them
     * into the appropriate touch objects.
     * @param event the motion event
     */
    private float marginLeft = 0;
    private float marginTop = 0;
    private float imageScale = 1;

    private void getPositions(MotionEvent event){
        for(int i = 0; i<event.getPointerCount();i++){
            int id = event.getPointerId(i);
            float x = (event.getX(i) - marginLeft) / imageScale;
            float y = (event.getY(i) - marginTop) / imageScale;
            if(id == touch1.id) {
                touch1.copyToLast();

                touch1.x = x;
                touch1.y = y;
            } else if(id == touch2.id) {
                touch2.copyToLast();

                touch2.x = x;
                touch2.y = y;
            }
        }
        mView.invalidate();
    }

    public boolean onTouchEvent(View view, MotionEvent event) {
        int id = event.getPointerId(event.getActionIndex());

        switch(event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                touch1.id = id;
                touch2.id = -1;
                getPositions(event);
                touch1.copyToLast();
                this.insertPiece(MainActivity.whosTurn, event.getX(), event.getY());
                view.invalidate();
                return true;

            case MotionEvent.ACTION_POINTER_DOWN:

                if(touch1.id >= 0 && touch2.id < 0) {
                    touch2.id = id;
                    getPositions(event);
                    touch2.copyToLast();
                    return true;
                }

                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:

                touch1.id = -1;
                touch2.id = -1;

                view.invalidate();

                return true;

            case MotionEvent.ACTION_POINTER_UP:

                if(id == touch2.id) {
                    touch2.id = -1;
                } else if(id == touch1.id) {
                    // Make what was touch2 now be touch1 by
                    // swapping the objects.
                    Touch t = touch1;
                    touch1 = touch2;
                    touch2 = t;
                    touch2.id = -1;
                }
                view.invalidate();

                return true;

            case MotionEvent.ACTION_MOVE:
                this.insertPiece(MainActivity.whosTurn, event.getX(), event.getY());
//                getPositions(event);
//                move();
                view.invalidate();
                return true;
        }

        return false;
    }

    /*
     * Draw game piece when user clicks on screen
     */
    public void insertPiece(int whosTurn, float x, float y) {
            this.clickX = x;
            this.clickY = y;
    }

    public void scale(float scale){
        scale = scale / 100;
        gridScale = gridScale + scale;
    }


    private float distance(float x1, float y1, float x2, float y2){
        float pX = (float) Math.pow((x2 - x1),2);
        float pY = (float) Math.pow((y2 - y1), 2);
        return (float) Math.sqrt(pX+pY);
    }


    public float gridScale = 1;


    public void loadPiecesFromMatrix(int[][] matrix) {
        params.currentGrid = matrix;
    }

    public void instantiatePieces(Canvas canvas) {

        for (GamePiece p : params.pieces) {
            p.draw(canvas, marginX, marginY, gridSize, scaleFactor);
        }

        if (MainActivity.newTurn) {
            if (MainActivity.whosTurn != MainActivity.whoamI) {
                MainActivity.doneButton.setText(R.string.waiting);
                MainActivity.doneButton.setEnabled(false);
                MainActivity.undoButton.setEnabled(false);
                MainActivity.surrenderButton.setEnabled(false);
                return;
            }

            MainActivity.doneButton.setText(R.string.donebutton);
            MainActivity.doneButton.setEnabled(true);
            MainActivity.undoButton.setEnabled(true);
            MainActivity.surrenderButton.setEnabled(true);

            GamePiece piece;
            if (MainActivity.whosTurn == 1) {
                piece = new GamePiece(mContext, R.drawable.spartan_green, 1, gridSize/2, 0);
            } else {
                piece = new GamePiece(mContext, R.drawable.spartan_white, 1, gridSize/2, 0);
            }

            piece.draw(canvas, marginX, marginY, gridSize, scaleFactor);
            params.pieces.add(piece);
            MainActivity.newTurn = false;
        } else {
            if (params.pieces != null && params.pieces.size()  > 0) {
                params.pieces.get(params.pieces.size()-1).trySnap(clickX, clickY, gridSize);
            }
        }


    }

    /**
     * Draw the puzzle piece
     *
     * @param canvas      Canvas we are drawing on
     * @param marginX     Margin x value in pixels
     * @param marginY     Margin y value in pixels
     * @param scaleFactor Amount we scale the puzzle pieces when we draw them
     */
    public void drawGridPiece(Canvas canvas, int marginX, int marginY,
                              int gridSize, float scaleFactor) {

        canvas.save();

        // Convert x,y to pixels and add the margin, then draw
        canvas.translate(marginX + x * gridSize, marginY + y * gridSize);

        // Scale it to the right size
        canvas.scale(scaleFactor, scaleFactor);

        // This magic code makes the center of the piece at 0, 0
        canvas.translate(-singleGridSpace.getWidth() / 2, -singleGridSpace.getHeight() / 2);

        // Draw the bitmap
        canvas.drawBitmap(singleGridSpace, 0, 0, null);
        canvas.restore();
    }

    /**
     * counts the amount of same color pieces in a certain direction around the last placement.
     * @param color player color 1 or 2
     * @param x last move x pos in grid
     * @param y last move y pos in grid
     * @param directionx denotes the x direction to move while checking c4
     * @param directiony denotes the y direction to move while checking c4
     * @return the number of consecutive pieces found of the same color
     */
    int count(int color, int x, int y, int directionx, int directiony) {
        int total = 0;
        x += directionx;  //need to skip current piece
        y += directiony;
        while (x >= 0 && x < 7 && y >= 0 && y < 6 && params.currentGrid[y][x] == color) {
            total++;
            x += directionx;  // Move in the direction denoted by (dy, dx)
            y += directiony;
        }
        return total;

    }

    public void undo() {
        if (params.pieces.size() > 0) {
//            GamePiece removedPiece = params.pieces.get(params.pieces.size()-1);
//            params.currentGrid[removedPiece.posY][removedPiece.posX] = 0;
            params.pieces.remove(params.pieces.size() - 1);
            mView.invalidate();
        }
    }

    /**
     * call this function to check all directions around last move
     * @param color color 1 or 2
     * @param x last move x pos in grid
     * @param y last move y pos in grid
     * @return true or false
     */
    boolean isWinner(int color, int x, int y) {
        return (count(color, x, y, -1, 0) + 1 + count(color, x, y, 1, 0) == 4  // horiz check
                || count(color, x, y, 0, -1) + 1 + count(color, x, y, 0, 1) == 4  // vert check
                || count(color, x, y, -1, -1) + 1 + count(color, x, y, 1, 1) == 4  // diag check
                || count(color, x, y, -1, 1) + 1 + count(color, x, y, 1, -1) == 4); // diag check

    }


    public static void loadFirebasepieces(String pieces, String grid) {

        if (pieces.equals("")) return;

        int[][] newGrid = new int[6][7];
        String[] gridArray = grid.split("-", 0);
        int c = 0;
        for (String element : gridArray) {
            Log.i("element", element);
            int d = 0;
            for (int i = 1; i < element.length()-1; i+=3) {
                newGrid[c][d] = element.charAt(i) - '0';
                d+=1;
            }
            c+=1;
        }
        params.currentGrid = newGrid;

        String[] array = pieces.split(",");

        ArrayList<GamePiece> loadedpieces = new ArrayList<GamePiece>();

        for (String i : array) {
            GamePiece piece;
            int posX = i.charAt(0) - '0';
            int posY = i.charAt(2) - '0';
            if (params.currentGrid[posY][posX] == 2) {
                piece = new GamePiece(mContext, R.drawable.spartan_green, 1, gridSize/2, 0);
            } else {
                piece = new GamePiece(mContext, R.drawable.spartan_white, 1, gridSize/2, 0);
            }
            piece.setPos(posX, posY, gridSize);
            loadedpieces.add(piece);
        }
        for (GamePiece p : loadedpieces) {
            Log.i("PIECES2", Integer.toString(p.posX) + Integer.toString(p.posY));
        }
        setPieces(loadedpieces);
    }
}
