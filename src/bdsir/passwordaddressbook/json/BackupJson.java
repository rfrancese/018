package bdsir.passwordaddressbook.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import bdsir.passwordaddressbook.R;
import bdsir.passwordaddressbook.SplashActivity;
import bdsir.passwordaddressbook.ViewAddressBook;
import bdsir.passwordaddressbook.database.RecordPassword;
import bdsir.passwordaddressbook.tools.CriptPassword;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class BackupJson extends Activity
{
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_backup_json);
		        
		setFinishOnTouchOutside(false);
		        
		if(isConnected())
			new HttpAsyncTask().execute("http://192.168.1.102:8888/TestJson/backup.php");
		else
			createAlert();
	}
 
	protected void onPause()
	{
		super.onPause();
		finish();
	}
	
	private class HttpAsyncTask extends AsyncTask<String, Void, String>
	{
		protected String doInBackground(String... urls)
		{
			ArrayList<RecordPassword> listRecord = ViewAddressBook.list;
			
			return POST(urls[0], listRecord);
		}
		
		protected void onPostExecute(String result)
		{
			TextView textView = (TextView) findViewById(R.id.attendiProgressBar);
			textView.setText(R.string.msgBackupDatiInviati);

			setContentView(R.layout.dialog_backup_confirm);
			if(result.equals("errno1"))
				((TextView) findViewById(R.id.backupView)).setText("Errore nel caricamento dati!");
			else if(result.equals("errno2"))
				((TextView) findViewById(R.id.backupView)).setText("Errore: BackUp non eseguito!");
			else
				((TextView) findViewById(R.id.backupView)).setText(result);
		}
	}
	
	public void procedi(View view)
	{
		finish();
	}
 
	public static String POST(String url, ArrayList<RecordPassword> list)
	{
		InputStream inputStream = null;
		String result = "";
		 
		try
		{
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			String json = "";
			  
			JSONArray jsonArray = new JSONArray();
			for(RecordPassword rp : list)
			{
				JSONObject rpObj = new JSONObject();
				rpObj.put("servizio", rp.getServizio());
				rpObj.put("username", rp.getCriptUsername());
				rpObj.put("password", rp.getCriptPassword());
				
				jsonArray.put(rpObj);
			}
				 
			JSONObject rubrica = new JSONObject();
			rubrica.put("rubrica",jsonArray);
			 
			json = rubrica.toString(); 
			
			json = CriptPassword.fromStringToHex(json).toUpperCase();
			
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("data", json));
			nameValuePairs.add(new BasicNameValuePair("directory", CriptPassword.fromStringToHex(SplashActivity.account.substring(0, SplashActivity.account.indexOf("@")))));
			
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			
			HttpResponse httpResponse = httpClient.execute(httpPost);
			
			inputStream = httpResponse.getEntity().getContent();
			
			if(inputStream != null)
				result = convertInputStreamToString(inputStream);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return result;
	}
     
	private static String convertInputStreamToString(InputStream inputStream) throws IOException
	{
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		
		while((line = bufferedReader.readLine()) != null)
			result += line;
		
		inputStream.close();
		
		return result;
	}	 

	private void createAlert()
	{
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Errore");
		alert.setMessage("Connessione assente!");
		alert.setCancelable(false);
		alert.setPositiveButton("Indietro", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				finish();
			}
		});
			
		alert.create();
		alert.show();
	}
		 
	
	public boolean isConnected()
	{
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected())
			return true;
		else
			return false;    
	}
}
