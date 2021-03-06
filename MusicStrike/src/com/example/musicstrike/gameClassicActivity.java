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
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class gameClassicActivity extends Activity implements OnGestureListener, OnPreparedListener {

	private MediaPlayer player;
	private MediaPlayer player2;
	private float pitch;
	private boolean tap, scroll, moveRight, moveLeft, shake = true;
	private GestureDetector gestureScanner;
	private SensorManager mSensorManager;
	private float mAccel; // acceleration apart from gravity
	private float mAccelCurrent; // current acceleration including gravity
	private float mAccelLast; // last acceleration including gravity
	private boolean monitorBool, readyToStart = false;	
	private int currentScore = 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Intent intent = getIntent();
        setContentView(R.layout.activity_game_classic);          
        
        //SManager = new ScoreManager();
        TextView HSView = (TextView) findViewById(R.id.HScore);
        try {
			HSView.setText("High Score: " + getHighScore());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        //gameIntro();        

        //setTimer();
        player = MediaPlayer.create(this, R.raw.compastempo120b4seg);
        //player = MediaPlayer.create(this, R.raw.hola);
        //player.setLooping(true);
        
       /* try {
			player.setDataSource("../res/raw/tempo140largo.mid");
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalStateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
        
        player.setVolume(100, 100);
        player.start();
        
        player2 = MediaPlayer.create(this, R.raw.winsound);
        //player.setLooping(true);
        player2.setVolume(300, 300);
        try {
			player2.prepare();
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
	    
	    

		AnimationSet set = new AnimationSet(true);
		set.setFillAfter(true);
		Animation anim = (Animation)AnimationUtils.loadAnimation(this, R.anim.tocenter_animation);
        anim.setFillAfter(true);
        set.addAnimation(anim);
        
        ImageView iv = new ImageView(this);
        iv.setImageResource(R.drawable.nota);
        
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.gameClassic);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.WRAP_CONTENT,
        RelativeLayout.LayoutParams.WRAP_CONTENT);
        rl.addView(iv, lp);
        
        iv.startAnimation(set);	            
        
	    
	    
	    player.setOnPreparedListener(this);
		// "While" principal
		final Handler handler = new Handler();
		handler.post(new Runnable() {
			@SuppressLint("ParserError")
			public void run() {

				int rnd = generateRandom();
				//tv1.setText("algo"+rnd);
				// Caso en que toca TAP
				//rnd=0; tap = false;
				
	            
	            
				if (rnd == 0) {
					if (tap == true) {
						tap = false;
					} else {
						tap = true;
						TextView tv = (TextView) findViewById(R.id.textView1);
						tv.setTextColor(Color.RED);
						tv.setTextSize(80);
						tv.setGravity(Gravity.CENTER);
						tv.setText("Tap now!");					
						
						animateTextView(tv); 
						
						if(!monitorBool)
						player.seekTo(0);
						
						//if(!monitorBool && !player.isPlaying()){
							//player.reset();
							//player.prepareAsync();
							/*try {
								player.prepare();
							} catch (IllegalStateException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							player.start();*/
							
						//}
						//else if(!monitorBool)
							//player.seekTo(0);
						
						/*if(!monitorBool && !player.isPlaying()){
							try {
								player.setDataSource("../res/raw/tempocompas120.mid");
							} catch (IllegalArgumentException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (SecurityException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IllegalStateException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							player.start();
							}
						//waitAndClose(600, tv);
						else if(!monitorBool)
						player.seekTo(0);*/
					}
				}

				// Caso SCROLL
				else if (rnd == 1){
					if (scroll == true) {
						scroll = false;
					} else {
						scroll = true;
						TextView tv = (TextView) findViewById(R.id.textView1);
						tv.setTextColor(Color.RED);
						tv.setTextSize(80);
						tv.setGravity(Gravity.CENTER);
						tv.setText("Scroll now!");				
						animateTextView(tv); 
						if(!monitorBool)
						player.seekTo(0);
							
						}
				}
				// Caso SHAKE
				else if (rnd == 2){
					if (shake == true) {
						shake = false;
					} else {
						shake = true;
						TextView tv = (TextView) findViewById(R.id.textView1);
						tv.setTextColor(Color.RED);
						tv.setTextSize(80);
						tv.setGravity(Gravity.CENTER);
						tv.setText("Shake now!");				
						animateTextView(tv); 
						if(!monitorBool)
						player.seekTo(0);
							
						}
				}
				
				// Caso MOVE LEFT
				else if (rnd == 3){
					if (moveLeft == true) {
						moveLeft = false;
					} else {
						moveLeft = true;
						TextView tv = (TextView) findViewById(R.id.textView1);
						tv.setTextColor(Color.RED);
						tv.setGravity(Gravity.CENTER);
						tv.setTextSize(80);
						tv.setText("Left now!");				
						animateTextView(tv); 
						if(!monitorBool)
						player.seekTo(0);
							
						}
				}
				
				// Caso MOVE RIGHT
				else if (rnd == 4){
					if (moveRight == true) {
						moveRight = false;
					} else {
						moveRight = true;
						TextView tv = (TextView) findViewById(R.id.textView1);
						tv.setTextColor(Color.RED);
						tv.setTextSize(80);
						tv.setGravity(Gravity.CENTER);
						tv.setText("Right now!");				
						animateTextView(tv); 
						if(!monitorBool)
						player.seekTo(0);
						
							
						}
				}
				handler.postDelayed(this, 2000);
			}
		});
        
        
        
        
        
        
    }
    
    
    public void onPrepared(MediaPlayer player1) {
        player.start();
    }
    
    public int generateRandom()
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
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        		monitorBool = true;
                player.reset();
                player.release();
                player2.reset();
                player2.release();
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
    
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) 
	{
		if (scroll) 
		{
			TextView tv = (TextView) findViewById(R.id.textView1);
			tv.setText("Scroll!");
			player2.start();
			refreshHighScore();
			//waitAndClose(600, layout, view);
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

	// Metodo que borra vista tras X segundos
	public void waitAndClose(int ms, final LinearLayout layout, final TextView tv) 
	{
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() 
		{
			public void run() 
			{
				layout.removeView(tv);
			}
		}, ms);
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

	public boolean onSingleTapUp(MotionEvent e) 
	{
		if (tap) 
		{
			TextView tv = (TextView) findViewById(R.id.textView1);
			tv.setText("Just on Time!");
			player2.start();
			refreshHighScore();
			
			//waitAndClose(600, layout, tv);
		} 
		else 
		{
			lose();
            //waitAndClose(600, layout, tv);
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
				//waitAndClose(600, layout, tv);
				if(!monitorBool)
				player2.start();
				refreshHighScore();
			}
			
			else if (mAccel > 5 && (!shake && !moveLeft && !moveRight) ) // Left y Right se confunden con shake a veces. 
			{
				lose();
			}
			//ESTO LOS INVERTI AL FIJAR LA PANTALLA
			//Move Right
			if (pitch < -3 && moveRight)
			{
				TextView tv = (TextView) findViewById(R.id.textView1);
				tv.setText("Moved right!");
				//waitAndClose(600, layout, tv);
				if(!monitorBool)
				player2.start();
				refreshHighScore();
			}
			else if (pitch < -3 && (!shake && !moveRight) ) // Left y Right se confunden con shake a veces. 
			{
				lose();
			}
			
			
			if (pitch > 3 && moveLeft) //Left
			{
				TextView tv = (TextView) findViewById(R.id.textView1);
				tv.setText("Moved left!");
				//waitAndClose(600, layout, tv);
				if(!monitorBool)
				player2.start();
				refreshHighScore();
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
		TextView tv = (TextView) findViewById(R.id.textView1);
		tv.setText("Wrong!");
		monitorBool = true;
        //player.reset();
        player.release();
        //player2.reset();
        player2.release();
        try {
			saveHighScore() ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        finish();
	}
	
	private void animateTextView(TextView tv)
	{
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

	/*private void setTimer()
	{
		final long mStartTime = System.currentTimeMillis();
		final TextView timer = (TextView) findViewById(R.id.timer);
		
		final Handler handler = new Handler();
		handler.post(new Runnable() {
			   public void run() {
			       final long start = mStartTime;
			       long millis = System.currentTimeMillis() - start;
			       int seconds = (int) (millis / 1000);
			       int minutes = seconds / 60;
			       seconds     = seconds % 60;

			       if (seconds < 10) {
			           timer.setText("" + minutes + ":0" + seconds);
			       } else {
			           timer.setText("" + minutes + ":" + seconds);            
			       }
			       
			       handler.postDelayed(this, 500);
			     
			   }
			});
	}*/
	
	private String getHighScore() throws IOException
	{
		
		FileInputStream fis;
		fis = openFileInput("highscore_file");
		StringBuffer fileContent = new StringBuffer("");

		byte[] buffer = new byte[1024];
		int length;
		while ((length = fis.read(buffer)) != -1) {
		    fileContent.append(new String(buffer));
		}
		
		return fileContent.toString();
	}
	
	private void saveHighScore() throws IOException
	{
		String FILENAME = "highscore_file";
		String string = "" + currentScore;

		FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
		fos.write(string.getBytes());
		fos.close();
		
		
	}
	
	private void refreshHighScore()
	{
		currentScore++;
		TextView HSView = (TextView) findViewById(R.id.CScore);
        HSView.setText("Score: " + currentScore);
		
	}
	
}
