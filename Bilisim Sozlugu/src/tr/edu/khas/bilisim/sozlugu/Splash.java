package tr.edu.khas.bilisim.sozlugu;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class Splash extends Activity {
	MediaPlayer ourSong;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		
		
		Thread timer = new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try{
					sleep(2000);
				}catch(InterruptedException e){
					e.printStackTrace();
				}finally{
					Intent openStartingPoint = new Intent(getApplicationContext(),MainActivity.class);
					startActivity(openStartingPoint);
				}
			}
						
		};
		timer.start();
	}

	@Override
	
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	
	
	
}
