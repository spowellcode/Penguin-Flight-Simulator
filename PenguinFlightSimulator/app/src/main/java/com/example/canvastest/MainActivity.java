package com.example.canvastest;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    //private RelativeLayout myLayout = null;

    MyCanvas myCanvas;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        myCanvas = new MyCanvas(this);
        myCanvas.setBackgroundColor(Color.RED);
        setContentView(myCanvas);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


        myCanvas.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Globals.touchx = event.getX();
                Globals.touchy = event.getY();

                return false;
            }
        });
    }
    @Override
    protected void onPause(){
        super.onPause();
        myCanvas.musicon = false;
    }
    @Override
    protected void onResume(){
        super.onResume();
        myCanvas.musicon = true;
    }
    @Override
    protected void onStart(){
        super.onStart();
        myCanvas.musicon = true;
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        myCanvas.musicon = false;
    }
}
