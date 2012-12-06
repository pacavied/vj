package com.example.musicstrike;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class InitialCountDownActivity extends Activity{

	public int counter = 0;
	
	@Override
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	        getIntent();
	        setContentView(R.layout.activity_initialcountdown); 
	        
	        final Handler handler = new Handler();
			handler.post(new Runnable() {
				@SuppressLint("ParserError")
				public void run() {
				
					
					if(counter < 3){
						
						TextView tv = (TextView) findViewById(R.id.textViewCounter);
						String countNumber = String.valueOf(counter+1);
						tv.setText(countNumber);
						counter++;
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
	
}
