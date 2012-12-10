package com.example.musicstrike;

import com.example.musicstrike.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;


public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new GetTask(this).execute();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        //GifMovieView view = new GifMovieView(this, "file:///android_asset/homescreen3.gif");
        //setContentView(view);
 
        setContentView(R.layout.activity_main);       
        
//		ImageView footballPlayer = (ImageView) findViewById(R.id.footballPlayer);
//		footballPlayer.setVisibility(View.VISIBLE);
//		footballPlayer.bringToFront();
		//Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
		//shake.setRepeatCount(-1);
		//shake.setRepeatMode(2);
		//footballPlayer.startAnimation(shake);
		
		ImageView musicStrike = (ImageView) findViewById(R.id.musicStrike);
		musicStrike.setVisibility(View.VISIBLE);
		musicStrike.bringToFront();
		//shake.setRepeatCount(2);
		//shake.setRepeatMode(2);
		//Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate_animation);
		//musicStrike.startAnimation(rotate);
		
		/*final Handler handler = new Handler();
		handler.post(new Runnable() {
			@SuppressLint("ParserError")
			public void run() {
			
			RelativeLayout rl = (RelativeLayout)findViewById(R.id.menuBackgroundImage);
			if (count == 0)
			rl.setBackgroundResource(R.drawable.menu_1);
			if (count == 1)
				rl.setBackgroundResource(R.drawable.menu_2);
			if (count == 2)
				rl.setBackgroundResource(R.drawable.menu_3);
			if (count == 3)
				rl.setBackgroundResource(R.drawable.menu_4);
			if (count == 4)
				rl.setBackgroundResource(R.drawable.menu_5);
			if (count == 5)
				rl.setBackgroundResource(R.drawable.menu_6);
			if (count == 6)
				rl.setBackgroundResource(R.drawable.menu_7);
			
			count++;
			if (count == 7)
				count = 0;
			handler.postDelayed(this, 200);}
		}); */
		
		ImageView myAnimation = (ImageView)findViewById(R.id.animatedStart);
		final AnimationDrawable myAnimationDrawable = (AnimationDrawable)myAnimation.getDrawable();
		
		myAnimation.post(
		new Runnable(){

		  public void run() {
		   myAnimationDrawable.start();
		  }
		});
        
       
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
				//myAnimationDrawable.stop();
				startActivity(intent);
				
			}
		});
        
        ImageButton tutButton = (ImageButton) findViewById(R.id.tutorialButton);
        tutButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
							
				Intent intent = new Intent(MainActivity.this,TutorialActivity.class);
				//myAnimationDrawable.stop();
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
