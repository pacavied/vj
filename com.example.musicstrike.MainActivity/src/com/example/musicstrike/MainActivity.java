package com.example.musicstrike;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
        
		ImageView footballPlayer = (ImageView) findViewById(R.id.footballPlayer);
		footballPlayer.setVisibility(View.VISIBLE);
		footballPlayer.bringToFront();
		Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
		shake.setRepeatCount(-1);
		shake.setRepeatMode(2);
		footballPlayer.startAnimation(shake);
		
		ImageView musicStrike = (ImageView) findViewById(R.id.musicStrike);
		musicStrike.setVisibility(View.VISIBLE);
		musicStrike.bringToFront();
		shake.setRepeatCount(2);
		shake.setRepeatMode(2);
		Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate_animation);
		musicStrike.startAnimation(rotate);
       
        ImageButton bib = (ImageButton) findViewById(R.id.imagePlayButton);
        bib.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
						
				Intent intent;
				if (IsFirstTimePlay())
					intent = new Intent(MainActivity.this, TutorialActivity.class);
				else
				{
					overridePendingTransition(R.anim.fadeout, R.anim.fadein);
					intent = new Intent(MainActivity.this,InitialCountDownActivity.class);
				}
				startActivity(intent);
				
			}
		});
        
        Button tutButton = (Button) findViewById(R.id.tutorialButton);
        tutButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
							
				Intent intent = new Intent(MainActivity.this,TutorialActivity.class);
				startActivity(intent);
				
			}
		});
        
        
      
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        
        
        return true;       
        
    }
    
    private boolean IsFirstTimePlay()
    {
		SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
		int ft = prefs.getInt("FirstTime", 0); //0 is the default value		
		
		if (ft == 0)
			return true;
		
		return false;
	}
    
}
