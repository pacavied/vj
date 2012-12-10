package application.followandfun;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class RescameLabs extends Activity implements OnCompletionListener, OnErrorListener, OnPreparedListener, OnVideoSizeChangedListener{

	public int counter = 0;
	private MediaPlayer rescamePlayer;
	
	@Override
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);	        
	        setContentView(R.layout.rescame_layout);  
	        /*
			rescamePlayer = MediaPlayer.create(this, R.raw.rescame);  
			rescamePlayer.setOnCompletionListener(this);
			rescamePlayer.setOnErrorListener(this);
			rescamePlayer.setOnPreparedListener(this);
			rescamePlayer.setOnVideoSizeChangedListener(this);
			rescamePlayer.start();*/
	        Uri url = Uri.parse("android.resource://application.followandfun/" + R.raw.rescame);
	        VideoView myVideoView = (VideoView)findViewById(R.id.videoView1);
	        myVideoView.setVideoURI(url);
	        myVideoView.setMediaController(new MediaController(this));
	        myVideoView.requestFocus();
	        myVideoView.setOnCompletionListener(this);
	        myVideoView.start();
	        
	}

	@Override
	public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		finish();
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		
	}

}
