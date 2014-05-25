package ir.home.listview;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;



import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

public class reg_user extends Activity {
	private final String NAMESPACE = "http://fpkaroon.com";
	private final String URL = "http://fpkaroon.com/Service.asmx";
	private final String SOAP_ACTION = "http://fpkaroon.com/insert_msg";
	private final String METHOD_NAME = "insert_msg";
	private String TAG = "PGGURU";
	private static String msg_sender;
	private static String msg_msg;
	private static int msg_like;
	private static String state;
	Button b;
	TextView tv;
	EditText et,et1;
	
	
		 
		  //  static final String[] letters = new String[] {reg_pass_s};


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reg);
		
		
		  
			 
		//Celcius Edit Control
		et = (EditText) findViewById(R.id.editText1);
		et1 = (EditText) findViewById(R.id.editText2);
		//Fahrenheit Text control
		tv = (TextView) findViewById(R.id.tv_result);
		//Button to trigger web service invocation
		b = (Button) findViewById(R.id.button1);
		//Button Click Listener
		b.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//Check if Celcius text control is not empty
				
				if (et.getText().length() != 0 && et.getText().toString() != "") {
					//Get the text control value
					msg_sender = et.getText().toString();
					msg_msg = et1.getText().toString();
					msg_like=0;
					//Create instance for AsyncCallWS
					//Log.i("onClick", reg_id_s+reg_user_s+reg_pass_s);
					AsyncCallWS task = new AsyncCallWS();
					//Call execute 
					task.execute();
				//If text control is empty
					

			        

				} else {
					tv.setText("اطلاعات رو کامل وارد کنید");
				}
			}
		});
	}

	public void getFahrenheit(String msg_sender,String msg_msg,Integer msg_like) {
		//Create request
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		//Property which holds input parameters
		PropertyInfo msg_l = new PropertyInfo();
		PropertyInfo msg_s = new PropertyInfo();
		PropertyInfo msg_m = new PropertyInfo();
		//Set Name
		msg_l.setName("lik");
		msg_s.setName("sender");
		msg_m.setName("msg");
		//Set Value
		msg_l.setValue(msg_like);
		msg_s.setValue(msg_sender);
		msg_m.setValue(msg_msg);
		//Log.i("fara", reg_id_s+reg_user_s+reg_pass_s);
		//Set dataType
		msg_l.setType(double.class);
		msg_s.setType(double.class);
		msg_m.setType(double.class);
		//Add the property to request object
		request.addProperty(msg_l);
		request.addProperty(msg_s);
		request.addProperty(msg_m);
		
		
		//Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		//Set output SOAP object
		envelope.setOutputSoapObject(request);
		//Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			//Invole web service
			androidHttpTransport.call(SOAP_ACTION, envelope);
			//Get the response
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			//Assign it to fahren static variable
			state = response.toString();
			
			
			        

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class AsyncCallWS extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			Log.i(TAG, "doInBackground");
			//Log.i("AsyncCallWS", reg_id_s+reg_user_s+reg_pass_s);
			getFahrenheit(msg_sender,msg_msg,msg_like);
			
		
			        
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			Log.i(TAG, "onPostExecute");
			tv.setText(state);
			
		}

		@Override
		protected void onPreExecute() {
			Log.i(TAG, "onPreExecute");
			tv.setText("Calculating...");
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			Log.i(TAG, "onProgressUpdate");
		}

	}

}
