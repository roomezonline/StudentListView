package ir.home.listview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;


public class MainActivity extends Activity {

    private final String         NAMESPACE   = "http://fpkaroon.com";
    private final String         URL         = "http://fpkaroon.com/Service.asmx";
    private final String SOAP_ACTION = "http://fpkaroon.com/read_msg";
    private final String         METHOD_NAME = "read_msg";
    private String TAG = "PGGURU";
    public static Button next,refresh;
    public LinearLayout linlaHeaderProgress;
   // private ListView             studentsListView;

   // private ArrayAdapter<String> adapter;
    ListView lst_data;
	//DatabaseHelper dbh;
	SQLiteDatabase db;
	ListAdapter adapter;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
       
        
        lst_data=(ListView)findViewById(R.id.listView1);
       
        
        ListAdapter adapter;
      	 ArrayList<HashMap<String, String>> students = retrieveStudents();
      	 
      	 
       
	
         
         
        
         refresh=(Button)findViewById(R.id.button2);
        next=(Button)findViewById(R.id.button1);
		next.setOnClickListener(new OnClickListener() {
			 

			@Override
			public void onClick(View view) {
			 
				Intent myIntent = new Intent(view.getContext(), reg_user.class);
                startActivityForResult(myIntent, 0);
				
              
			    
			}
			
			
			
			}
	);
       
	    

    }


    public ArrayList<HashMap<String, String>> retrieveStudents() {
    	
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.dotNet = true;

        envelope.setOutputSoapObject(request);

        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        
        
        try {

           // androidHttpTransport.call(NAMESPACE + METHOD_NAME, envelope);
        	androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapObject response = (SoapObject) envelope.getResponse();

            return parseSOAPResponse(response);
            

        }
        catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
    
    
    
    
    
    


    public ArrayList<HashMap<String, String>> parseSOAPResponse(SoapObject studentsSoap) {

        if (studentsSoap != null) {

        	ArrayList<HashMap<String, String>> DataLList;
    		DataLList = new ArrayList<HashMap<String, String>>();
        	
            int count = studentsSoap.getPropertyCount();
            List<String> students = new ArrayList<String>();

            for (int i = 0; i < count; i++) {
            	
            	HashMap<String, String> map = new HashMap<String, String>();
            	
                SoapObject stu = (SoapObject) studentsSoap.getProperty(i);
               // String id = stu.getPrimitivePropertySafelyAsString("Id");
                String sender = stu.getPrimitivePropertySafelyAsString("Sender");
                String msg = stu.getPrimitivePropertySafelyAsString("Msg");
                String time = stu.getPrimitivePropertySafelyAsString("Time");
                students.add(sender);
                students.add(msg);
                students.add(time);
                
               // map.put("ID", id);
    			map.put("Sender", sender);
    			map.put("Msg", msg);
    			map.put("Time", time);
    			
    			DataLList.add(map);
            }

            //return students.toArray(new String[students.size()]);
            adapter = new listviewAdapter(this, DataLList);
			lst_data.setAdapter(adapter);
				
        }

        return null;

    }
    
    
}