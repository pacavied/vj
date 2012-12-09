package com.example.musicstrike;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

public class InitialCountDownActivity extends Activity{

	public int counter = 0;
	private boolean stopRunnable = false;
	
	@Override
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        Log.v("CountDown","Comienza CountDown");
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	        getIntent();
	        setContentView(R.layout.activity_initialcountdown); 
	        
	        final Handler handler = new Handler();
			handler.post(new Runnable() {
				@SuppressLint("ParserError")
				public void run() {
				
					
					if(counter < 3){
						
						TextView tv = (TextView) findViewById(R.id.textViewCounter);
						tv.setVisibility(View.INVISIBLE);
						String countNumber = "0";
						if(counter == 0)
							countNumber = "3";
						else if(counter == 1)
							countNumber = "2";
						else
							countNumber = "1";
						tv.setText(countNumber);
						Log.v("CountDown","Conteo actual: "+countNumber);
						//animateTextView(tv);
						tv.setVisibility(View.VISIBLE);
						Animation rotate = AnimationUtils.loadAnimation(InitialCountDownActivity.this, R.anim.rotate_animation);
						tv.startAnimation(rotate);
						
						counter++;
						if(!stopRunnable)
							handler.postDelayed(this, 1000);
					}
					
					else{
						stopService(getIntent());
						finish();
						Intent intent = new Intent(InitialCountDownActivity.this,gameClassicActivity.class);
						startActivity(intent);
					}
						
					
				}	
		
			});
	 }
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        		stopRunnable = true;
                stopService(getIntent());
                finish();
                
                return false;
        }
    return super.onKeyDown(keyCode, event);
}
	
	private void animateTextView(TextView tv)
	{
		tv.bringToFront();
		AnimationSet set = new AnimationSet(true);
		set.setFillAfter(true);
		Animation animation = new AlphaAnimation(0.0f, 1.0f);
	    animation.setDuration(500);
	    set.addAnimation(animation);

	    RotateAnimation ranim = (RotateAnimation)AnimationUtils.loadAnimation(this,R.anim.rotate_animation);
	    ranim.setFillAfter(true);
	    set.addAnimation(ranim);    
	    
	    animation = new AlphaAnimation(1.0f, 0.0f);
	    animation.setDuration(500);
	    animation.setStartOffset(500);
	    set.addAnimation(animation);
	    
	    tv.startAnimation(set);
	}
	
}
