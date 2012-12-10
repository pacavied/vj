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
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
import android.view.View.OnTouchListener;
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

public class gameClassicActivity extends Activity implements OnGestureListener,
		OnPreparedListener, OnCompletionListener, OnErrorListener,
		OnVideoSizeChangedListener {

	public final static String SCORE_MESSAGE = "Score";

	private MediaPlayer playerBase1;
	private MediaPlayer player2;
	private MediaPlayer player3;
	private MediaPlayer instructionPlayer;
	private MediaPlayer initialPlayer;
	private MediaPlayer handsPlayer;
	private int indexMediaPlayer = 0;

	private float pitch;
	private boolean tap, scroll, moveRight, moveLeft, shake = true;
	private GestureDetector gestureScanner;
	private SensorManager mSensorManager;
	private float mAccel; // acceleration apart from gravity
	private float mAccelCurrent; // current acceleration including gravity
	private float mAccelLast; // last acceleration including gravity
	private boolean monitorBool, continueRunnable = true;
	private int currentScore = 0;
	private int highscore = 0;
	private int objectID = -1;
	private ImageView objectView = null;
	private Behavior behavior;
	private boolean alreadyWin = false;
	private int roundsCounter = 0;
	private boolean loseGame = false;
	private int tapCounter = 0;
	private Level level = new Level();

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

		playerBase1 = MediaPlayer.create(this, R.raw.tempo120);
		player2 = MediaPlayer.create(this, R.raw.winsound);
		player3 = MediaPlayer.create(this, R.raw.winsound);
		instructionPlayer = MediaPlayer.create(this, R.raw.winsound);
		handsPlayer = MediaPlayer.create(this, R.raw.yeeee);
		initialPlayer = MediaPlayer.create(this, R.raw.winsound);

		playerBase1.setVolume(100, 100);
		player2.setVolume(100, 100);
		player3.setVolume(100, 100);
		instructionPlayer.setVolume(100, 100);
		handsPlayer.setVolume(100, 100);
		initialPlayer.setVolume(50, 50);

		player3.setOnCompletionListener(this);
		player3.setOnErrorListener(this);
		player3.setOnPreparedListener(this);
		player3.setOnVideoSizeChangedListener(this);

		player2.setOnCompletionListener(this);
		player2.setOnErrorListener(this);
		player2.setOnPreparedListener(this);
		player2.setOnVideoSizeChangedListener(this);

		playerBase1.setOnCompletionListener(this);
		playerBase1.setOnErrorListener(this);
		playerBase1.setOnPreparedListener(this);
		playerBase1.setOnVideoSizeChangedListener(this);

		instructionPlayer.setOnCompletionListener(this);
		instructionPlayer.setOnErrorListener(this);
		instructionPlayer.setOnPreparedListener(this);
		instructionPlayer.setOnVideoSizeChangedListener(this);

		initialPlayer.setOnCompletionListener(this);
		initialPlayer.setOnErrorListener(this);
		initialPlayer.setOnPreparedListener(this);
		initialPlayer.setOnVideoSizeChangedListener(this);

		handsPlayer.setOnCompletionListener(this);
		handsPlayer.setOnErrorListener(this);
		handsPlayer.setOnPreparedListener(this);
		handsPlayer.setOnVideoSizeChangedListener(this);

		gestureScanner = new GestureDetector(this);

		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mSensorManager.registerListener(mSensorListener,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
		mAccel = 0.00f;
		mAccelCurrent = SensorManager.GRAVITY_EARTH;
		mAccelLast = SensorManager.GRAVITY_EARTH;
		pitch = 0;

		// playerBase1.setOnPreparedListener(this);

		// "While" principal
		final Handler handler = new Handler();
		handler.post(new Runnable() {
			@SuppressLint("ParserError")
			public void run() {

				Log.v("Start", "Empieza vuelta");
				
				if (alreadyWin == false && roundsCounter != 0)
				lose();

				alreadyWin = false;
				tap = false;
				scroll = false;
				shake = false;
				moveRight = false;
				moveLeft = false;
				tapCounter = 0;

				if (roundsCounter == 0)
					behavior = new Behavior(0, false);
				else
					behavior = new Behavior();

				RelativeLayout rl = (RelativeLayout) findViewById(R.id.gameClassic);
				rl.setBackgroundResource(behavior.backgroundImage);

				ImageView vh = (ImageView) findViewById(R.id.victoryHands);
				vh.setVisibility(View.INVISIBLE);

				prepareLevelSounds();

				if (roundsCounter % 10 == 0)
					playBaseSound();

				if (loseGame == false) {
					instructionPlayer.start();
					initialPlayer.start();
				}

				if (objectView != null)
					rl.removeView(objectView);

				if (behavior.behaviorType == 0) {
					if (tap == true) {
						tap = false;
						Log.v("NoTAP", "Ntaped: X");
					} else {
						tap = true;
						/*TextView tv = (TextView) findViewById(R.id.textView1);

						tv.setTextColor(Color.RED);
						tv.setTextSize(80);
						tv.setGravity(Gravity.CENTER);
						tv.setText("Tap now!");

						animateTextView(tv);*/
						
						ImageView iv = (ImageView) findViewById(R.id.instructionView);
						iv.setBackgroundResource(R.drawable.tapit);
						iv.setVisibility(View.VISIBLE);
						Animation rotate = AnimationUtils.loadAnimation(gameClassicActivity.this, R.anim.rotate_animation);
						iv.startAnimation(rotate);

						Log.v("TAP", "Tap Now");

					}
				}

				// Caso SCROLL
				else if (behavior.behaviorType == 1) {

					scroll = true;
					/*TextView tv = (TextView) findViewById(R.id.textView1);
					tv.setTextColor(Color.RED);
					tv.setTextSize(80);
					tv.setGravity(Gravity.CENTER);
					tv.setText("Scroll now!");
					animateTextView(tv);*/
					
					ImageView iv = (ImageView) findViewById(R.id.instructionView);
					iv.setBackgroundResource(R.drawable.scrollnow);
					iv.setVisibility(View.VISIBLE);
					Animation rotate = AnimationUtils.loadAnimation(gameClassicActivity.this, R.anim.rotate_animation);
					iv.startAnimation(rotate);

					Log.v("SCROLL", "Scroll Now");

				}
				// Caso SHAKE
				else if (behavior.behaviorType == 2) {

					shake = true;
					/*TextView tv = (TextView) findViewById(R.id.textView1);
					tv.setTextColor(Color.RED);
					tv.setTextSize(80);
					tv.setGravity(Gravity.CENTER);
					tv.setText("Shake now!");
					animateTextView(tv);*/
					
					ImageView iv = (ImageView) findViewById(R.id.instructionView);
					iv.setBackgroundResource(R.drawable.shakeit);
					iv.setVisibility(View.VISIBLE);
					Animation rotate = AnimationUtils.loadAnimation(gameClassicActivity.this, R.anim.rotate_animation);
					iv.startAnimation(rotate);

					Log.v("SHAKE", "Shake Now");

				}

				// Caso MOVE LEFT
				else if (behavior.behaviorType == 3) {

					moveLeft = true;
					/*TextView tv = (TextView) findViewById(R.id.textView1);
					tv.setTextColor(Color.RED);
					tv.setGravity(Gravity.CENTER);
					tv.setTextSize(80);
					tv.setText("Left now!");
					animateTextView(tv);*/
					
					ImageView iv = (ImageView) findViewById(R.id.instructionView);
					iv.setBackgroundResource(R.drawable.moveleft);
					iv.setVisibility(View.VISIBLE);
					Animation rotate = AnimationUtils.loadAnimation(gameClassicActivity.this, R.anim.rotate_animation);
					iv.startAnimation(rotate);
					Log.v("LEFT", "Left Now");

				}

				// Caso MOVE RIGHT
				else if (behavior.behaviorType == 4) {

					moveRight = true;
					/*TextView tv = (TextView) findViewById(R.id.textView1);
					tv.setTextColor(Color.RED);
					tv.setTextSize(80);
					tv.setGravity(Gravity.CENTER);
					tv.setText("Right now!");
					animateTextView(tv);*/
					
					ImageView iv = (ImageView) findViewById(R.id.instructionView);
					iv.setBackgroundResource(R.drawable.moveright);
					iv.setVisibility(View.VISIBLE);
					Animation rotate = AnimationUtils.loadAnimation(gameClassicActivity.this, R.anim.rotate_animation);
					iv.startAnimation(rotate);

					Log.v("RIGHT", "Right Now");

				}

				if (behavior.haveObject) {

					objectView = new ImageView(gameClassicActivity.this);
					objectView.setId(9857);
					objectID = objectView.getId();

					objectView.setImageResource(behavior.objectInitialSprite);

					RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
					lp.addRule(RelativeLayout.CENTER_VERTICAL);
					lp.addRule(RelativeLayout.CENTER_HORIZONTAL);

					if (tap) {

						objectView.setOnTouchListener(new OnTouchListener() {
							public boolean onTouch(View v, MotionEvent event) {

								Log.v("Tap", "Touch event");
								Log.v("Tap", "" + event.getAction());

								if (tap
										&& MotionEvent.ACTION_DOWN == event.getAction() && tapCounter == 0) {
									Log.v("Tap", "Aprete por primera vez");
									//TextView tv = (TextView) findViewById(R.id.textView1);
									//tv.setText("Just on Time!");
									if (!alreadyWin) {
										updateSpritesAndBackgrounds();
										refreshHighScore();
									}
									alreadyWin = true;
									// tap = false;
									tapCounter++;
								} else if (MotionEvent.ACTION_DOWN == event
										.getAction() && tapCounter == 2) {
									Log.v("Tap", "Perdi");
									lose();
								}

								return false;
							}
						});

					}

					rl.addView(objectView, lp);

				}

				roundsCounter++;
				level.IncreaseCounter();

				if (continueRunnable)
					handler.postDelayed(this, level.roundTime);
			}
		});

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			monitorBool = true;
			playerBase1.reset();
			playerBase1.release();
			player2.reset();
			player2.release();
			player3.reset();
			player3.release();
			instructionPlayer.reset();
			instructionPlayer.release();
			continueRunnable = false;
			loseGame = true;
			stopService(getIntent());
			finish();

			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	
	// Metodo que borra vista tras X segundos
	public void waitAndClose(int ms, final RelativeLayout layout, final ImageView tv) 
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

	// scroll

	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		if (scroll) {
			//TextView tv = (TextView) findViewById(R.id.textView1);
			//tv.setText("Scroll!");
			if (!alreadyWin) {
				updateSpritesAndBackgrounds();
				refreshHighScore();
			}
			alreadyWin = true;
			// scroll = false;
		} else {
			lose();
		}
		return true;
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
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

	public void updateSpritesAndBackgrounds() {

		ImageView vh = (ImageView) findViewById(R.id.victoryHands);
		vh.setVisibility(View.VISIBLE);
		vh.bringToFront();
		Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
		vh.startAnimation(shake);
		
		ImageView iv1 = (ImageView) findViewById(R.id.instructionView);
		iv1.setVisibility(View.INVISIBLE);

		playFinalSound();

		if (behavior.haveObject && objectID != -1) {
			ImageView iv = (ImageView) findViewById(objectID);
			iv.setImageResource(behavior.objectFinalSprite);

		}

		if (behavior.haveFinalBackground) {
			RelativeLayout rl = (RelativeLayout) findViewById(R.id.gameClassic);
			rl.setBackgroundResource(behavior.finalBackground);
		}

	}

	// public boolean onSingleTapUp(MotionEvent e)
	// {
	//
	//
	// //if (tap && e.getRawX() - iv.getWidth()/2 < iv.getLeft() && e.getRawX()
	// + iv.getWidth()/2 > iv.getLeft() )
	// if (tap)
	// {
	// TextView tv = (TextView) findViewById(R.id.textView1);
	// tv.setText("Just on Time!");
	// if(!alreadyWin){
	// updateSpritesAndBackgrounds();
	// refreshHighScore();
	// }
	// alreadyWin = true;
	// tap = false;
	//
	// }
	// else
	// {
	// lose();
	// }
	// return true;
	// }

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

			// SHAKE:
			if (mAccel > 5 && shake) {
				//TextView tv = (TextView) findViewById(R.id.textView1);
				//tv.setText("Shaked!");
				if (!alreadyWin) {
					updateSpritesAndBackgrounds();
					if (!monitorBool)
						refreshHighScore();
				}
				alreadyWin = true;
				Log.e("shake", "shake");

			}

			else if (mAccel > 7 && (!shake && !moveLeft && !moveRight)) // Left
																		// y
																		// Right
																		// se
																		// confunden
																		// con
																		// shake
																		// a
																		// veces.
			{
				lose();
			}
			// ESTO LOS INVERTI AL FIJAR LA PANTALLA
			// Move Right
			if (pitch < -5 && moveRight) {
				Log.e("Right", "Right");
				//TextView tv = (TextView) findViewById(R.id.textView1);
				//tv.setText("Moved right!");
				if (!alreadyWin) {
					updateSpritesAndBackgrounds();
					if (!monitorBool)
						refreshHighScore();
				}
				alreadyWin = true;

			} else if (pitch < -3 && (!shake && !moveRight)) // Left y Right se
																// confunden con
																// shake a
																// veces.
			{
				lose();
			}

			if (pitch > 5 && moveLeft) // Left
			{
				//TextView tv = (TextView) findViewById(R.id.textView1);
				//tv.setText("Moved left!");
				if (!alreadyWin) {
					updateSpritesAndBackgrounds();
					if (!monitorBool)
						refreshHighScore();
				}
				alreadyWin = true;
				Log.e("left", "left");
			}

			else if (pitch > 5 && (!shake && !moveLeft)) // Left y Right se
															// confunden con
															// shake a veces.
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

	private void lose() {
		if (continueRunnable) {
			//TextView tv = (TextView) findViewById(R.id.textView1);
			//tv.setText("Wrong!");
			monitorBool = true;

			playerBase1.release();

			player2.release();
			player3.release();
			instructionPlayer.release();
			initialPlayer.release();
			handsPlayer.release();
			loseGame = true;

			try {
				saveHighScore();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			continueRunnable = false;
			stopService(getIntent());

			finish();

			Intent intent = new Intent(gameClassicActivity.this,
					loseWindowActivity.class);
			String message = String.valueOf(currentScore);
			intent.putExtra(SCORE_MESSAGE, message);
			startActivity(intent);
		}
	}

	private void animateTextView(TextView tv) {
		tv.bringToFront();
		AnimationSet set = new AnimationSet(true);
		set.setFillAfter(true);
		Animation animation = new AlphaAnimation(0.0f, 1.0f);
		animation.setDuration(500);
		set.addAnimation(animation);

		RotateAnimation ranim = (RotateAnimation) AnimationUtils.loadAnimation(
				this, R.anim.rotate_animation);
		ranim.setFillAfter(true);
		set.addAnimation(ranim);

		animation = new AlphaAnimation(1.0f, 0.0f);
		animation.setDuration(500);
		animation.setStartOffset(500);
		set.addAnimation(animation);

		tv.startAnimation(set);
	}

	
	// ANIMACION?
	private void animateIntro(TextView tv) {
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

		/*
		 * RotateAnimation ranim =
		 * (RotateAnimation)AnimationUtils.loadAnimation(
		 * this,R.anim.rotate_animation); ranim.setFillAfter(true);
		 * set.addAnimation(ranim);
		 * 
		 * animation = new AlphaAnimation(1.0f, 0.0f);
		 * animation.setDuration(500); animation.setStartOffset(500);
		 * set.addAnimation(animation);
		 */

		tv.startAnimation(set);
	}

	private int getHighScore() throws IOException {

		/*
		 * FileInputStream fis; fis = openFileInput("highscore_file");
		 * 
		 * StringBuffer fileContent = new StringBuffer("");
		 * 
		 * byte[] buffer = new byte[1024]; int length; while ((length =
		 * fis.read(buffer)) != -1) { fileContent.append(new String(buffer)); }
		 */
		SharedPreferences prefs = this.getSharedPreferences("myPrefsKey",
				Context.MODE_PRIVATE);
		int score = prefs.getInt("HighScore", 0); // 0 is the default value
		return score;
	}

	private void saveHighScore() throws IOException {
		/*
		 * String FILENAME = "highscore_file"; String scoreToSave; String aux =
		 * getHighScore(); aux = aux.replaceAll("[^\\w\\s\\.]", ""); highscore =
		 * Integer.parseInt(aux); if(currentScore > highscore) scoreToSave = ""
		 * + currentScore; else scoreToSave = "" + highscore;
		 * 
		 * FileOutputStream fos = openFileOutput(FILENAME,
		 * Context.MODE_PRIVATE); fos.write(scoreToSave.getBytes());
		 * fos.close();
		 */
		String scoreToSave;
		int highscore = getHighScore();
		// aux = aux.replaceAll("[^\\w\\s\\.]", "");
		// highscore = Integer.parseInt(aux);
		if (currentScore > highscore)
			scoreToSave = "" + currentScore;
		else
			scoreToSave = "" + highscore;

		SharedPreferences prefs = this.getSharedPreferences("myPrefsKey",
				Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putInt("HighScore", Integer.parseInt(scoreToSave));
		editor.putInt("FirstTime", 1);
		editor.commit();

	}

	private void refreshHighScore() {
		currentScore++;
		TextView HSView = (TextView) findViewById(R.id.CScore);
		HSView.setText("Score: " + currentScore);

	}

	private void prepareSounds(int instructionSound, int initialSound,
			int finalSound, int baseSound) {

		instructionPlayer.release();
		instructionPlayer = MediaPlayer.create(gameClassicActivity.this,
				instructionSound);
		instructionPlayer.setOnCompletionListener(this);
		instructionPlayer.setOnErrorListener(this);
		instructionPlayer.setOnPreparedListener(this);
		instructionPlayer.setOnVideoSizeChangedListener(this);

		initialPlayer.release();
		initialPlayer = MediaPlayer.create(gameClassicActivity.this,
				initialSound);
		initialPlayer.setOnCompletionListener(this);
		initialPlayer.setOnErrorListener(this);
		initialPlayer.setOnPreparedListener(this);
		initialPlayer.setOnVideoSizeChangedListener(this);

	}

	private void prepareLevelSounds() {
		// if (roundsCounter < 10)
		prepareSounds(behavior.instructionSound, behavior.initialSound,
				behavior.finalSound, level.roundTime);
		// else
		// prepareSounds(behavior.finalSound, R.raw.compastempo125d);
	}

	private void playFinalSound() {

		if (indexMediaPlayer == 0) {

			player2.release();
			player2 = MediaPlayer.create(gameClassicActivity.this,
					behavior.finalSound);
			player2.setOnCompletionListener(this);
			player2.setOnErrorListener(this);
			player2.setOnPreparedListener(this);
			player2.setOnVideoSizeChangedListener(this);
			player2.start();
			indexMediaPlayer++;

		} else {

			player3.release();
			player3 = MediaPlayer.create(gameClassicActivity.this,
					behavior.finalSound);
			player3.setOnCompletionListener(this);
			player3.setOnErrorListener(this);
			player3.setOnPreparedListener(this);
			player3.setOnVideoSizeChangedListener(this);
			player3.start();
			indexMediaPlayer--;
		}

		handsPlayer.release();
		handsPlayer = MediaPlayer.create(gameClassicActivity.this, R.raw.yeeee);
		handsPlayer.setOnCompletionListener(this);
		handsPlayer.setOnErrorListener(this);
		handsPlayer.setOnPreparedListener(this);
		handsPlayer.setOnVideoSizeChangedListener(this);
		handsPlayer.start();
	}

	private void playBaseSound() {

		if (loseGame == false) {

			playerBase1.release();
			playerBase1 = MediaPlayer.create(gameClassicActivity.this,
					level.backgroundMusic);
			playerBase1.setOnCompletionListener(this);
			playerBase1.setOnErrorListener(this);
			playerBase1.setOnPreparedListener(this);
			playerBase1.setOnVideoSizeChangedListener(this);
			playerBase1.start();

			// instructionPlayer.start();

			// initialPlayer.start();
		}
	}

	public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
		// TODO Auto-generated method stub

	}

	public boolean onError(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		return false;
	}

	public void onPrepared(MediaPlayer player1) {
		// player1.start();
	}

	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub

	}

	public boolean onSingleTapUp(MotionEvent e) {
		if (!tap) {
			Log.v("Tap", "Perdiste por tapear en lugar equivocado");
			lose();
		}
		return false;
	}

}
