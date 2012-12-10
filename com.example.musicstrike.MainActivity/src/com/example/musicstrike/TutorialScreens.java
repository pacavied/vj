package com.example.musicstrike;

import com.example.musicstrike.R;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class TutorialScreens extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getIntent();
		setContentView(R.layout.end_tutorial);
		
		ImageView myAnimation = (ImageView)findViewById(R.id.myanimation);
		final AnimationDrawable myAnimationDrawable = (AnimationDrawable)myAnimation.getDrawable();

		myAnimation.post(
		new Runnable(){

		  public void run() {
		   myAnimationDrawable.start();
		  }
		});
		
		
		 ImageButton bib = (ImageButton) findViewById(R.id.buttonPlay);
	        bib.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					stopService(getIntent());
					finish();							
					Intent intent = new Intent(TutorialScreens.this, InitialCountDownActivity.class);
					startActivity(intent);
					
				}
			});
	        
	        ImageButton tutButton = (ImageButton) findViewById(R.id.goToMenuButton);
	        tutButton.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					stopService(getIntent());
					finish();								
					Intent intent = new Intent(TutorialScreens.this, MainActivity.class);
					startActivity(intent);
					
				}
			});
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			stopService(getIntent());
			finish();

			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}
	
}
