package com.example.musicstrike;


import android.content.Context;
import android.webkit.WebView;

public class GifMovieView extends WebView{
//	
//	private Movie mMovie;
//	private InputStream mStream;
//	private long mMoviestart;

    public GifMovieView(Context context, String path) {
        super(context);
        loadUrl(path);
     }
    
    /*@Override
    protected void onDraw(Canvas canvas) {
       canvas.drawColor(Color.TRANSPARENT);
       super.onDraw(canvas);
       final long now = SystemClock.uptimeMillis();
       if (mMoviestart == 0) {
          mMoviestart = now;
       }
       final int relTime = (int)((now - mMoviestart) % mMovie.duration());
       mMovie.setTime(relTime);
       mMovie.draw(canvas, 10, 10);
       this.invalidate();
    }*/

}
