package com.example.musicstrike;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class loseWindowActivity extends Activity{
	
	private String score = "";

	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	  
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	        Intent intent = getIntent();
	        score = intent.getStringExtra(gameClassicActivity.SCORE_MESSAGE);
	        setContentView(R.layout.lose_layout);
	        	        
	        
	        ImageButton goToMenu = (ImageButton) findViewById(R.id.goToMenuButton);
	        goToMenu.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
								
					finish();
					
				}
			}); 
	        
	        
	        ImageButton tryAgain = (ImageButton) findViewById(R.id.tryAgainButton);
	        tryAgain.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
								
					finish();
					Intent intent = new Intent(loseWindowActivity.this,gameClassicActivity.class);
					startActivity(intent);
				}
			}); 
	        
	 }
}
