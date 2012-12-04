package com.example.musicstrike;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.channels.AlreadyConnectedException;
import java.util.Timer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class gameClassicActivity extends Activity implements OnGestureListener, OnPreparedListener {

	private MediaPlayer playerBase1;
	private MediaPlayer playerBase2;
	private MediaPlayer player2;
	private MediaPlayer player3;
	private MediaPlayer instructionPlayer;
	private int indexMediaPlayer = 0, indexMediaPlayerBase = 0;
	
	private float pitch;
	private boolean tap, scroll, moveRight, moveLeft, shake = true;
	private GestureDetector gestureScanner;
	private SensorManager mSensorManager;
	private float mAccel; // acceleration apart from gravity
	private float mAccelCurrent; // current acceleration including gravity
	private float mAccelLast; // last acceleration including gravity
	private boolean monitorBool, dalomismo = true;	
	private int currentScore = 0;
	private int highscore = 0;
	private int objectID = -1;
	private ImageView objectView = null;
	private Behavior behavior;
	private boolean alreadyWin = false;
	private int roundsCounter = 0;
	
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getIntent();
        setContentView(R.layout.activity_game_classic);   

        TextView HSView = (TextView) findViewById(R.id.HScore);
        try {
			HSView.setText("High Score: " + getHighScore());
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        
        playerBase1 = MediaPlayer.create(this, R.raw.compastempo120b);       
        playerBase2 = MediaPlayer.create(this, R.raw.compastempo120b); 
        playerBase1.setVolume(100, 100);
        playerBase2.setVolume(100, 100);
        
    	playerBase1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
    	    public void onCompletion(MediaPlayer mp) {
    	        finish(); // finish current activity
    	    }
    	});
    	
    	playerBase1.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
			
			public void onPrepared(MediaPlayer mp) {
				// TODO Auto-generated method stub
				
			}
		});
    	
    	playerBase2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
    	    public void onCompletion(MediaPlayer mp) {
    	        finish(); // finish current activity
    	    }
    	});
        
        
        player2 = MediaPlayer.create(this, R.raw.winsound);
        player3 = MediaPlayer.create(this, R.raw.winsound);
        instructionPlayer = MediaPlayer.create(this, R.raw.winsound);

        player2.setVolume(100, 100);
        player3.setVolume(100, 100);
        instructionPlayer.setVolume(100, 100);
        
        try {
        	player3.prepare();
			player2.prepare();
			instructionPlayer.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        gestureScanner = new GestureDetector(this);
	
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	    mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
	    mAccel = 0.00f;
	    mAccelCurrent = SensorManager.GRAVITY_EARTH;
	    mAccelLast = SensorManager.GRAVITY_EARTH;
	    pitch = 0;
	    
	    //playerBase1.setOnPreparedListener(this);
	    
		// "While" principal
		final Handler handler = new Handler();
		handler.post(new Runnable() {
			@SuppressLint("ParserError")
			public void run() {

				Log.e("Start","Empieza vuelta");
				
				alreadyWin = false;
				tap = false;scroll=false;shake=false;moveRight=false;moveLeft=false;
				
				
				behavior = new Behavior();
				
				//rnd=0; tap = false;
				
				RelativeLayout rl = (RelativeLayout) findViewById(R.id.gameClassic);
				rl.setBackgroundResource(behavior.backgroundImage);
				
				ImageView vh = (ImageView) findViewById(R.id.victoryHands);
				vh.setVisibility(View.INVISIBLE);
				
				prepareLevelSounds();
				playBaseSound();
				
				/*
				Resources resources = getResources();
				AssetFileDescriptor afd = resources.openRawResourceFd(behavior.finalSound);
				try {
					player2.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
					player2.prepare();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				
				
				
				if(objectView != null)
					rl.removeView(objectView);
				
				if(behavior.haveObject){
					
					
					
					objectView = new ImageView(gameClassicActivity.this);
					objectView.setId(9857);
					objectID = objectView.getId();
					
					objectView.setImageResource(behavior.objectInitialSprite);
					RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
					        RelativeLayout.LayoutParams.WRAP_CONTENT,
					        RelativeLayout.LayoutParams.WRAP_CONTENT);
					lp.addRule(RelativeLayout.CENTER_VERTICAL);
					lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
					/*lp.leftMargin = 100;
					lp.topMargin = 100;
					*/
					rl.addView(objectView, lp);
					
				}
				
	            
	            
				if (behavior.behaviorType == 0) {
					if (tap == true) {
						tap = false;
						Log.v("NoTAP", "Ntaped: X");
					} else {
						tap = true;
						TextView tv = (TextView) findViewById(R.id.textView1);
						
						tv.setTextColor(Color.RED);
						tv.setTextSize(80);
						tv.setGravity(Gravity.CENTER);
						tv.setText("Tap now!");					
						
						animateTextView(tv); 
						
						
						
						Log.v("TAP", "Tap Now");
						
					}
				}

				// Caso SCROLL
				else if (behavior.behaviorType == 1){
					
						scroll = true;
						TextView tv = (TextView) findViewById(R.id.textView1);
						tv.setTextColor(Color.RED);
						tv.setTextSize(80);
						tv.setGravity(Gravity.CENTER);
						tv.setText("Scroll now!");				
						animateTextView(tv); 
						
						Log.v("SCROLL", "Scroll Now");
							
						
				}
				// Caso SHAKE
				else if (behavior.behaviorType == 2){
					
						shake = true;
						TextView tv = (TextView) findViewById(R.id.textView1);
						tv.setTextColor(Color.RED);
						tv.setTextSize(80);
						tv.setGravity(Gravity.CENTER);
						tv.setText("Shake now!");				
						animateTextView(tv); 
						
						Log.v("SHAKE", "Shake Now");	
						
				}
				
				// Caso MOVE LEFT
				else if (behavior.behaviorType == 3){
					
						moveLeft = true;
						TextView tv = (TextView) findViewById(R.id.textView1);
						tv.setTextColor(Color.RED);
						tv.setGravity(Gravity.CENTER);
						tv.setTextSize(80);
						tv.setText("Left now!");				
						animateTextView(tv); 
						
						Log.v("LEFT", "Left Now");	
						
				}
				
				// Caso MOVE RIGHT
				else if (behavior.behaviorType == 4){
					
						moveRight = true;
						TextView tv = (TextView) findViewById(R.id.textView1);
						tv.setTextColor(Color.RED);
						tv.setTextSize(80);
						tv.setGravity(Gravity.CENTER);
						tv.setText("Right now!");				
						animateTextView(tv); 
						
						Log.v("RIGHT", "Right Now");
							
						
				}
				
				roundsCounter++;
				
				if(dalomismo)
					handler.postDelayed(this, 2000);
			}
		});      
        
        
    }
    
    
    public void onPrepared(MediaPlayer player1) {
        player1.start();
    }
    
   /* public int generateRandom()
	{
		double rnd = Math.random();
		
		if (rnd < 0.2) //TAP
		{
			rnd = 0;
			scroll = false; shake = false; moveLeft = false; moveRight = false;
		}
		else if (rnd < 0.4) //SCROLL
		{
			rnd = 1;
			tap = false; shake = false; moveLeft = false; moveRight = false;
		}
		else if (rnd < 0.6) //SHAKE
		{
			rnd = 2;
			scroll = false; tap = false; moveLeft = false; moveRight = false;
		}
		else if (rnd < 0.8) //MOVE LEFT
		{
			rnd = 3;
			scroll = false; shake = false; tap = false; moveRight = false;
		}
		else //MOVE RIGHT
		{
			rnd = 4;
			scroll = false; shake = false; moveLeft = false; tap = false;
		}
		
		return (int)rnd;
	}
    */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        		monitorBool = true;
                playerBase1.reset();
                playerBase1.release();
                playerBase2.reset();
                playerBase2.release();
                player2.reset();
                player2.release();
                player3.reset();
                player3.release();
                instructionPlayer.reset();
                instructionPlayer.release();
                dalomismo = false;
                stopService(getIntent());
                finish();
                return false;
        }
    return super.onKeyDown(keyCode, event);
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_game_classic, menu);
        return true;
    }

    @Override
	public boolean onTouchEvent(MotionEvent me) {
		return gestureScanner.onTouchEvent(me);
	}
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    //scroll
    
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) 
	{
		if (scroll) 
		{
			TextView tv = (TextView) findViewById(R.id.textView1);
			tv.setText("Scroll!");
			if(!alreadyWin){	
			updateSpritesAndBackgrounds();
			refreshHighScore();
			}
			alreadyWin = true;
			//scroll = false;
		}
		else
		{
			lose();
		}
		return true;
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) 
	{
		return true;
	}

	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}
	
	public void updateSpritesAndBackgrounds(){
		
		ImageView vh = (ImageView) findViewById(R.id.victoryHands);
		vh.setVisibility(View.VISIBLE);
        vh.bringToFront();
		Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        vh.startAnimation(shake);
        
        playFinalSound();

		if(behavior.haveObject && objectID != -1){
			ImageView iv = (ImageView) findViewById(objectID);
			iv.setImageResource(behavior.objectFinalSprite);
		}
		
		if(behavior.haveFinalBackground){
			RelativeLayout rl = (RelativeLayout) findViewById(R.id.gameClassic);
			rl.setBackgroundResource(behavior.finalBackground);
		}
		
		
		
	}

	public boolean onSingleTapUp(MotionEvent e) 
	{
		if (tap) 
		{
			TextView tv = (TextView) findViewById(R.id.textView1);
			tv.setText("Just on Time!");
			if(!alreadyWin){
			updateSpritesAndBackgrounds();
			refreshHighScore();
			}
			alreadyWin = true;
			tap = false;
			
		} 
		else 
		{
			lose();
		}
		return true;
	}

	private final SensorEventListener mSensorListener = new SensorEventListener() {
		public void onSensorChanged(SensorEvent se) {
			float x = se.values[0];
			float y = se.values[1];
			float z = se.values[2];
			mAccelLast = mAccelCurrent;
			mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
			float delta = mAccelCurrent - mAccelLast;
			mAccel = mAccel * 0.9f + delta;
			pitch = y;
			
			//SHAKE:
			if (mAccel > 5 && shake)
			{
				TextView tv = (TextView) findViewById(R.id.textView1);
				tv.setText("Shaked!");
				if(!alreadyWin){
				updateSpritesAndBackgrounds();
				if(!monitorBool)
					refreshHighScore();
				}
				alreadyWin = true;
				Log.e("shake","shake");
				
			}
			
			else if (mAccel > 5 && (!shake && !moveLeft && !moveRight) ) // Left y Right se confunden con shake a veces. 
			{
				lose();
			}
			//ESTO LOS INVERTI AL FIJAR LA PANTALLA
			//Move Right
			if (pitch < -3 && moveRight)
			{
				Log.e("Right","Right");
				TextView tv = (TextView) findViewById(R.id.textView1);
				tv.setText("Moved right!");
				if(!alreadyWin){
					updateSpritesAndBackgrounds();
					if(!monitorBool)
						refreshHighScore();
					}
				alreadyWin = true;
				
			}
			else if (pitch < -3 && (!shake && !moveRight) ) // Left y Right se confunden con shake a veces. 
			{
				lose();
			}
			
			
			if (pitch > 3 && moveLeft) //Left
			{
				TextView tv = (TextView) findViewById(R.id.textView1);
				tv.setText("Moved left!");
				if(!alreadyWin){
					updateSpritesAndBackgrounds();
					if(!monitorBool)
						refreshHighScore();
					}
				alreadyWin = true;
				Log.e("left","left");
			}	

			else if (pitch > 3 && (!shake && !moveLeft) ) // Left y Right se confunden con shake a veces. 
			{
				lose();
			}
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};

	@Override
	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(mSensorListener,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onStop() {
		mSensorManager.unregisterListener(mSensorListener);
		super.onStop();
	}
	
	private void lose()
	{
		if(dalomismo){
		TextView tv = (TextView) findViewById(R.id.textView1);
		tv.setText("Wrong!");
		monitorBool = true;
        //player.reset();
        playerBase1.release();
        playerBase2.release();
        //player2.reset();
        player2.release();
        player3.release();
        instructionPlayer.release();
        try {
			saveHighScore() ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        dalomismo = false;
        stopService(getIntent());
       
        finish();
        
        Intent intent = new Intent(gameClassicActivity.this,loseWindowActivity.class);
		startActivity(intent);
		}
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
	
	private void gameIntro()
	{
		TextView tv = (TextView) findViewById(R.id.textView1);
		tv.setTextColor(Color.GREEN);
		tv.setTextSize(80);
		tv.setGravity(Gravity.CENTER);
		tv.setText("Are You Ready?");			
		animateIntro(tv);
	}
	//ANIMACION?
	private void animateIntro(TextView tv)
	{
		AnimationSet set = new AnimationSet(true);
		set.setFillAfter(true);
		Animation animation = new AlphaAnimation(0.0f, 1.0f);
	    animation.setDuration(500);
	    set.addAnimation(animation);
	    
	    tv.setText("3");
	    animation = new AlphaAnimation(0.0f, 1.0f);
	    animation.setDuration(500);
	    animation.setStartOffset(500);
	    set.addAnimation(animation);
	    
	    tv.setText("2");
	    animation = new AlphaAnimation(0.0f, 1.0f);
	    animation.setDuration(500);
	    animation.setStartOffset(500);
	    set.addAnimation(animation);
	    
	    tv.setText("1");
	    animation = new AlphaAnimation(0.0f, 1.0f);
	    animation.setDuration(500);
	    animation.setStartOffset(500);
	    set.addAnimation(animation);

	    /*RotateAnimation ranim = (RotateAnimation)AnimationUtils.loadAnimation(this,R.anim.rotate_animation);
	    ranim.setFillAfter(true);
	    set.addAnimation(ranim);    
	    
	    animation = new AlphaAnimation(1.0f, 0.0f);
	    animation.setDuration(500);
	    animation.setStartOffset(500);
	    set.addAnimation(animation);*/
	    
	    tv.startAnimation(set);
	}

	
	private String getHighScore() throws IOException
	{
		
		FileInputStream fis;
		fis = openFileInput("highscore_file");
		StringBuffer fileContent = new StringBuffer("");

		byte[] buffer = new byte[1024];
		int lenght;
		while ((lenght = fis.read(buffer)) != -1) {
		    fileContent.append(new String(buffer));
		}
				
		return fileContent.toString();
	}
	
	private void saveHighScore() throws IOException
	{
		String FILENAME = "highscore_file";
		String scoreToSave;
		String aux = getHighScore();
		aux = aux.replaceAll("[^\\w\\s\\.]", "");
		highscore = Integer.parseInt(aux);
		if(currentScore > highscore)
			scoreToSave = "" + currentScore;
		else
			scoreToSave = "" + highscore;
		
		FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
		fos.write(scoreToSave.getBytes());
		fos.close();
		
		
	}
	
	private void refreshHighScore()
	{
		currentScore++;
		TextView HSView = (TextView) findViewById(R.id.CScore);
        HSView.setText("Score: " + currentScore);
		
	}
	
	private void prepareSounds(int instructionSound, int finalSound, int baseSound){
		
		if(indexMediaPlayer == 0){
			
			player2.release();
			player2 = MediaPlayer.create(gameClassicActivity.this, finalSound);
			try {
				player2.prepare();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		else{
			
			player3.release();
			player3 = MediaPlayer.create(gameClassicActivity.this, finalSound);
			try {
				player3.prepare();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		if(indexMediaPlayerBase == 0){
					
				playerBase1.release();
				playerBase1 = MediaPlayer.create(gameClassicActivity.this, baseSound);
				try {
					playerBase1.prepare();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
								
			}
			else{
				
				playerBase2.release();
				playerBase2 = MediaPlayer.create(gameClassicActivity.this, baseSound);
				try {
					playerBase2.prepare();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		
		instructionPlayer.release();
		instructionPlayer = MediaPlayer.create(gameClassicActivity.this, instructionSound);
		try {
			instructionPlayer.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
	}
	
	private void prepareLevelSounds()
	{
		//if (roundsCounter < 10)
			prepareSounds(behavior.instructionSound, behavior.finalSound, R.raw.compastempo120b);
		//else
			//prepareSounds(behavior.finalSound, R.raw.compastempo125d);
	}
	
	private void playFinalSound(){
		if(indexMediaPlayer == 0){
			player2.start();
			indexMediaPlayer++;
		}
		else{
			player3.start();
			indexMediaPlayer--;	
		}
	}
	private void playBaseSound(){
		if(indexMediaPlayerBase == 0){
			playerBase1.start();
			indexMediaPlayerBase++;
		}
		else{
			playerBase2.start();
			indexMediaPlayerBase--;	
		}
		instructionPlayer.start();
	}
	

	
}
