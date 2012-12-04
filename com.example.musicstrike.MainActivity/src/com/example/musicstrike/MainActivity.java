package com.example.musicstrike;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends Activity {
	private float mx =0, my=0;
	private float downX, downY;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new GetTask(this).execute();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        //GifMovieView view = new GifMovieView(this, "file:///android_asset/homescreen3.gif");
        //setContentView(view);
        
        setContentView(R.layout.activity_main);
        
        final ImageView switcherView = (ImageView) this.findViewById(R.id.img);
        switcherView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent event) {

                float curX, curY;

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        mx = event.getX();
                        my = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //curX = event.getX();
                        curY = event.getY();
                        switcherView.scrollBy((int) (0), (int) (my - curY));
                        //mx = curX;
                        my = curY;
                        break;
                    case MotionEvent.ACTION_UP:
                        //curX = event.getX();
                        curY = event.getY();
                        switcherView.scrollBy((int) (0), (int) (my - curY));
                        break;
                }

                return true;
            }
        });
        
        ImageButton bib = (ImageButton) findViewById(R.id.imageButton1);
        bib.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
							
				Intent intent = new Intent(MainActivity.this,gameClassicActivity.class);
				startActivity(intent);
				
			}
		});
        
        
      
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        
        
        return true;
        
        
    }
    
}
