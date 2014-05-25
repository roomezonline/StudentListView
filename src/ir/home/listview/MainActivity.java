package ir.home.listview;

import java.util.ArrayList;
import java.util.HashMap;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MainActivity extends Activity {

	private final String NAMESPACE = "http://fpkaroon.com";
	private final String URL = "http://fpkaroon.com/Service.asmx";
	private final String SOAP_ACTION = "http://fpkaroon.com/read_msg";
	private final String METHOD_NAME = "read_msg";
	public static Button next, refresh;
	public LinearLayout linlaHeaderProgress;

	ListView lst_data;
	SQLiteDatabase db;
	listviewAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		lst_data = (ListView) findViewById(R.id.listView1);

		adapter = new listviewAdapter(this,
				new ArrayList<HashMap<String, String>>());
		lst_data.setAdapter(adapter);

		StudentTask studentTask = new StudentTask();

		studentTask.execute();

		refresh = (Button) findViewById(R.id.button2);
		next = (Button) findViewById(R.id.button1);
		next.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {

				Intent myIntent = new Intent(view.getContext(), reg_user.class);
				startActivityForResult(myIntent, 0);
			}
		});
	}

	public ArrayList<HashMap<String, String>> retrieveStudents() {

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;

		envelope.setOutputSoapObject(request);

		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {

			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject response = (SoapObject) envelope.getResponse();

			return parseSOAPResponse(response);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<HashMap<String, String>> parseSOAPResponse(
			SoapObject studentsSoap) {

		ArrayList<HashMap<String, String>> DataLList=
				new ArrayList<HashMap<String, String>>();
		
		if (studentsSoap != null) {
			int count = studentsSoap.getPropertyCount();

			for (int i = 0; i < count; i++) {

				HashMap<String, String> map = new HashMap<String, String>();

				SoapObject stu = (SoapObject) studentsSoap.getProperty(i);
				String sender = stu
						.getPrimitivePropertySafelyAsString("Sender");
				String msg = stu.getPrimitivePropertySafelyAsString("Msg");
				String time = stu.getPrimitivePropertySafelyAsString("Time");

				map.put("Sender", sender);
				map.put("Msg", msg);
				map.put("Time", time);

				DataLList.add(map);
			}
		}

		return DataLList;

	}

	private class StudentTask extends
			AsyncTask<String, Void, ArrayList<HashMap<String, String>>> {

		private ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			dialog = ProgressDialog.show(MainActivity.this, "", "Loading...");
		}

		@Override
		protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
			super.onPostExecute(result);

			adapter.setDataList(result);
			adapter.notifyDataSetChanged();
			dialog.dismiss();				
		}

		@Override
		protected ArrayList<HashMap<String, String>> doInBackground(
				String... params) {
			return retrieveStudents();
		}

	}

}