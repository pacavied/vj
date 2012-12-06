package com.example.musicstrike;

import com.example.musicstrike.Behavior;
import com.example.musicstrike.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TutorialActivity extends Activity implements OnGestureListener, OnPreparedListener,
OnCompletionListener, OnErrorListener, OnVideoSizeChangedListener {
	
	private MediaPlayer player;
	private Behavior behavior;
	private float pitch;
	private boolean tap, scroll, moveRight, moveLeft, shake = false;
	private GestureDetector gestureScanner;
	private SensorManager mSensorManager;
	private float mAccel; // acceleration apart from gravity
	private float mAccelCurrent; // current acceleration including gravity
	private float mAccelLast; // last acceleration including gravity
	private ImageView objectView = null;
	private boolean alreadyWin = false;
	private int objectID = -1;
	
	/* Orden Tutorial:
	 * TAP
	 * SCROLL
	 * SHAKE
	 * MOVE LEFT
	 * MOVE RIGHT
	 * 
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getIntent();
		setContentView(R.layout.tutorial_activity);
		
		player = MediaPlayer.create(this, R.raw.yeeee);
		player.setVolume(100, 100);		
		
        gestureScanner = new GestureDetector(this);
    	
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	    mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
	    mAccel = 0.00f;
	    mAccelCurrent = SensorManager.GRAVITY_EARTH;
	    mAccelLast = SensorManager.GRAVITY_EARTH;
	    pitch = 0;

		
	    

		behavior = new Behavior(0);
		
		// "While" principal
		final Handler handler = new Handler();
		handler.post(new Runnable() {
			@SuppressLint("ParserError")
			public void run() {

				Log.v("Start","Empieza vuelta");
				//if (alreadyWin == false && roundsCounter != 0)
				//	lose();
					
				scroll=false;shake=false;moveRight=false;moveLeft=false;tap = false;
				
				alreadyWin = false;	
				RelativeLayout rl = (RelativeLayout) findViewById(R.id.tutorial);
				rl.setBackgroundResource(behavior.backgroundImage);
				
				
				if(objectView != null)
					rl.removeView(objectView);
				
				if(behavior.haveObject){
					
					
					
					objectView = new ImageView(TutorialActivity.this);
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
				
				handler.postDelayed(this, 2000);
			}
		});      
        
        
	    
	    
		
	}
	
	

    @Override
	public boolean onTouchEvent(MotionEvent me) {
		return gestureScanner.onTouchEvent(me);
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
				}
				alreadyWin = true;
				Log.v("shake","shake");

				shake = false;
				moveLeft = true;
				behavior = new Behavior(1);
				
			}
			
			else if (mAccel > 5 && (!shake && !moveLeft && !moveRight) ) // Left y Right se confunden con shake a veces. 
			{
				wrongMovement();
			}
			//ESTO LOS INVERTI AL FIJAR LA PANTALLA
			//Move Right
			if (pitch < -3 && moveRight)
			{
				// FIN TUTORIAL
				Log.v("Right","Right");
				TextView tv = (TextView) findViewById(R.id.textView1);
				tv.setText("Moved right!");
				if(!alreadyWin){
					updateSpritesAndBackgrounds();
					}
				alreadyWin = true;
				
			}
			else if (pitch < -3 && (!shake && !moveRight) ) // Left y Right se confunden con shake a veces. 
			{
				wrongMovement();
			}
			
			
			if (pitch > 3 && moveLeft) //Left
			{
				
				TextView tv = (TextView) findViewById(R.id.textView1);
				tv.setText("Moved left!");
				if(!alreadyWin){
					updateSpritesAndBackgrounds();
					}
				alreadyWin = true;
				Log.e("left","left");

				moveLeft = false;
				moveRight = true;
				behavior = new Behavior(6);
			}	

			else if (pitch > 3 && (!shake && !moveLeft) ) // Left y Right se confunden con shake a veces. 
			{
				wrongMovement();
			}
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};
	
	public boolean onSingleTapUp(MotionEvent e) 
	{
		
		if (tap)
		{
			TextView tv = (TextView) findViewById(R.id.textView1);
			tv.setText("Just on Time!");
			if(!alreadyWin){
			updateSpritesAndBackgrounds();
			}
			alreadyWin = true;
			tap = false;
			scroll = true;
			behavior = new Behavior(3);
			
		} 
		else 
		{
			wrongMovement();
		}
		return true;
	}
	
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) 
	{
		if (scroll) 
		{
			TextView tv = (TextView) findViewById(R.id.textView1);
			tv.setText("Scroll!");
			if(!alreadyWin){	
			updateSpritesAndBackgrounds();
			}
			alreadyWin = true;
			scroll = false;
			moveLeft = true;
			behavior = new Behavior(1);
			//scroll = false;
		}
		else
		{
			wrongMovement();
		}
		return true;
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
	
	public void updateSpritesAndBackgrounds(){
        
        playFinalSound();

		if(behavior.haveObject && objectID != -1){
			ImageView iv = (ImageView) findViewById(objectID);
			iv.setImageResource(behavior.objectFinalSprite);
		}
		
		if(behavior.haveFinalBackground){
			RelativeLayout rl = (RelativeLayout) findViewById(R.id.tutorial);
			rl.setBackgroundResource(behavior.finalBackground);
		}
		
		
		
	}
	
	public void wrongMovement()
	{
		// TRY AGAIN MESSAGE
	}
	
	
	private void playFinalSound(){
		
		player.release();
		player = MediaPlayer.create(TutorialActivity.this, R.raw.yeeee);
		player.setOnCompletionListener(this);
		player.setOnErrorListener(this);
		player.setOnPreparedListener(this);
		player.setOnVideoSizeChangedListener(this); 
		player.start();
		
	}


	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}



	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}


	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void onVideoSizeChanged(MediaPlayer arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	public void onCompletion(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onPrepared(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		
	}

}
