package com.example.musicstrike;

import com.example.musicstrike.R;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class loseWindowActivity extends Activity{
	
	private String score = "";

	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	  
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	        Intent intent = getIntent();
	        score = intent.getStringExtra(gameClassicActivity.SCORE_MESSAGE);
	        setContentView(R.layout.lose_layout);
	        
	        
	        TextView tv = (TextView) findViewById(R.id.scoreView);
	        tv.setText("" + score);
	       
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
					Intent intent = new Intent(loseWindowActivity.this,InitialCountDownActivity.class);
					startActivity(intent);
				}
			}); 
	        
	 }

}
