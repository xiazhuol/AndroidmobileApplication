package edu.msu.project1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.io.Serializable;


//TODO: figure out onTouchEvent, how we will display the pieces on grid
//TODO: make grid on landscape look better
public class MainView extends View {




    private Grid grid;

    public MainView getView() { return this; }
    public Grid getGrid() {return grid; }




    public MainView(Context context) {
        super(context);
        init(null, 0);
    }

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public MainView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }


    private void init(AttributeSet attrs, int defStyle) {

        grid = new Grid(this, getContext());

    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return grid.onTouchEvent(this, event);
    }

    /**
     * Handle a touch event
     * @param event The touch event
     */


    /**
     * Handle a draw event
     * @param canvas canvas to draw on.
     */
    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
        //grid.draw(canvas);

        grid.draw(canvas);


    }






}
