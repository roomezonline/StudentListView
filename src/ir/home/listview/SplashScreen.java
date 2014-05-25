package ir.home.listview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class SplashScreen extends Activity {
    private int _splashTime = 2000;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				 WindowManager.LayoutParams.FLAG_FULLSCREEN);
       
        new Handler().postDelayed(new Thread(){
       	   @Override
       	   public void run(){
       		 Intent mainMenu = new Intent(SplashScreen.this, Tab.class);
       		 SplashScreen.this.startActivity(mainMenu);
       		 SplashScreen.this.finish();
       		 overridePendingTransition(R.drawable.fadein, R.drawable.fadeout);
       	   }
        }, _splashTime);
    }
}