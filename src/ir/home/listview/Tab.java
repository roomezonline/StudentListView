package ir.home.listview;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class Tab extends TabActivity {
	TabHost tabHost;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab);


         // create the TabHost that will contain the Tabs
         final TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);

         tabHost.setOnTabChangedListener(new OnTabChangeListener() {

             public void onTabChanged(String arg0) {
            	// TODO Auto-generated method stub
           	    for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
           		{
           			tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#FF195A"));
           		}

           		tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#F3CAD6")); 
             }
         });
         
         
         
         
         
         
         
         
         

         TabSpec tab1 = tabHost.newTabSpec("First Tab");
         TabSpec tab2 = tabHost.newTabSpec("Second Tab");
         TabSpec tab3 = tabHost.newTabSpec("Third tab");

        // Set the Tab name and Activity
        // Typeface font = Typeface.createFromAsset(this.getAssets(), "trado.ttf");  
         
        // that will be opened when particular Tab will be selected
         tab1.setIndicator("ورود");

         
         tab1.setContent(new Intent(this,reg_user.class));
        
         tab2.setIndicator("ذخیره شده");
         tab2.setContent(new Intent(this,MainActivity.class));

         tab3.setIndicator("انلاین");
         tab3.setContent(new Intent(this,reg_user.class));
        
         /** Add the tabs  to the TabHost to display. */
         tabHost.addTab(tab1);
         tabHost.addTab(tab2);
         tabHost.addTab(tab3);
         
         
         
         //aligne tab
      // setup your tabs

         RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
         rllp.addRule(RelativeLayout.CENTER_IN_PARENT);

         for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
             tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title).setLayoutParams(rllp);
         
         }
         
         
         //change tab font size
         for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
             TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
             tv.setTextSize(23);
            
         }
         
         //change tab color
         for(int i=0;i<tabHost.getTabWidget().getChildCount();i++)
 		{
         	tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#FF195A"));
 		}
         tabHost.getTabWidget().setCurrentTab(0);
         tabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.parseColor("#F3CAD6"));
      }
	
	

 }
 