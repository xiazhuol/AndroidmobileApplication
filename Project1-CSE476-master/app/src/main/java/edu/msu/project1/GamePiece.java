package edu.msu.project1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class GamePiece {
    /**
     * image for actual piece
     */
    private Bitmap piece;


    /*
     * image id
     */
    private int id;

    /**
     * player/color indicator
     */
    private int playerColor;

    /**
     * We use relative x locations in the range 0-1 for the center of puzzle piece
     */
    private float x;
    private float y;

    /* Location in 7x6 Matrix*/
    public int posX = 0;
    public int posY = 0;

    private float pieceWidth;
//    private float pieceHeight;


    /**
     * GamePiece constructor with color attribute which denotes player 1 or 2 as well
     * @param context to get resources
     * @param playerColor 1 or 2 for GREEN or WHITE respectively. Player1 is GREEN Player2 is WHITE
     * @param id spartan_green or spartan_white
     */
    public GamePiece(Context context, int id, int playerColor,  float x, float y) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.playerColor = playerColor;
        piece = BitmapFactory.decodeResource(context.getResources(), id);
    }

    public Bitmap getPiece() {
        return this.piece;
    }

    public int getId() { return id; }

    public float getX() { return x; }

    public void setX(float x) { this.x = x; }

    public float getY() { return y; }

    public void setY(float y) { this.y = y; }

    public int getPlayerColor(){ return playerColor; }

    public void setPos(int posX, int posY, float gridSize) {
        this.posX = posX;
        this.posY = posY;
        trySnap(((posX + 1)*gridSize)/7, ((posY)*gridSize)/7, gridSize);
    }

    public void increaseY(int value, float gridSize) {
        this.y += (pieceWidth*(value))/gridSize;
        posY += value;
    }


    /**
     * Snap each piece into correct position in grid
     */
    public void trySnap(float clickX, float clickY, float gridSize) {
        float pieceWidth = gridSize/7;
        float pieceHeight = pieceWidth;

        this.pieceWidth = pieceWidth;


        /* Getting closest squares to click*/
        float smallX = pieceWidth;
        float smallY = pieceHeight;
        float biggerX = 0;
        float biggerY = 0;

        /* Adjusting click to be in the midle of game piece */
        clickX -= pieceWidth/(4*gridSize);
        clickY -= pieceHeight/(4*gridSize);

        for (int i = (int)pieceWidth; i <= gridSize; i+=pieceWidth ) {
            if (i <= clickX) {
                smallX = i;
            } else {
                biggerX = i;
                break;
            }
        }
        for (int i = 1; i <= gridSize-2*pieceHeight; i+=pieceHeight ) {
            if (i <= clickY) {
                smallY = i;
            } else {
                biggerY = i;
                break;
            }
        }

        if (Math.abs(clickX-smallX) <= Math.abs(clickX-biggerX)) {
            this.x = smallX/gridSize;
        } else {
            this.x = biggerX / gridSize;
        }

        this.x -= (pieceWidth/2)/gridSize;

        if (Math.abs(clickY-smallY) <= Math.abs(clickY-biggerY)) {
//            Log.i("Small", Float.toString(smallY));
            this.y = smallY/gridSize;
        } else {
//            Log.i("Small", Float.toString(biggerY));
            this.y = biggerY/gridSize;
        }

        this.y += (pieceHeight/2)/(gridSize);

        posX = Math.round(this.x*7);
        posY = Math.round(this.y*6);

    }

    /**
     * Draw the puzzle piece
     * @param canvas Canvas we are drawing on
     * @param marginX Margin x value in pixels
     * @param marginY Margin y value in pixels
     * @param puzzleSize Size we draw the puzzle in pixels
     * @param scaleFactor Amount we scale the puzzle pieces when we draw them
     */
    public void draw(Canvas canvas, int marginX, int marginY,
                     int puzzleSize, float scaleFactor) {
        canvas.save();

        // Convert x,y to pixels and add the margin, then draw
        canvas.translate(marginX + x * puzzleSize, marginX + y * puzzleSize);

        // Scale it to the right size
        canvas.scale(scaleFactor, scaleFactor);

        // This magic code makes the center of the piece at 0, 0
        canvas.translate(-piece.getWidth() / 2, -piece.getHeight() / 2);

        // Draw the bitmap
        canvas.drawBitmap(piece, 0, 0, null);
        canvas.restore();
    }
}
