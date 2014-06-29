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
import bdsir.passwordaddressbook.database.DataBaseHelper;
import bdsir.passwordaddressbook.database.RecordPassword;
import bdsir.passwordaddressbook.dialog.ErrorAlert;
import bdsir.passwordaddressbook.dialog.PersonalDialog;
import bdsir.passwordaddressbook.tools.CriptPassword;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RipristinoJson extends Activity
{
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_backup_json);
		        
		setFinishOnTouchOutside(false);
		        
		if(isConnected())
			new HttpAsyncTask(this).execute("http://192.168.1.102:8888/TestJson/ripristino.php");
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
		private Activity activity;
		
		public HttpAsyncTask(Activity activity)
		{
			this.activity = activity;
		}
		
		protected String doInBackground(String... urls)
		{	
			return POST(urls[0]);
		}
		
		protected void onPostExecute(String result)
		{
			TextView textView = (TextView) findViewById(R.id.attendiProgressBar);
			textView.setText(R.string.msgRipristinoDatiInviati);

			setContentView(R.layout.dialog_backup_confirm);
			if(result.equals("errno1"))
				((TextView) findViewById(R.id.backupView)).setText("Errore nel caricamento dati!");
			else if(result.equals("errno2"))
				((TextView) findViewById(R.id.backupView)).setText("Non hai ancora effettuato un BackUp!");
			else
			{
				try
				{
					DataBaseHelper dataBaseHelper = new DataBaseHelper(activity);
					SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
					db.delete("rubrica", null, null);
					
					ContentValues values = new ContentValues();
					
					result = CriptPassword.fromHexToString(result);
					
					JSONObject json = new JSONObject(result);
					JSONArray jsonArray = json.getJSONArray("rubrica");
					for(int i = 0; i < jsonArray.length(); i++)
					{
						RecordPassword r = new RecordPassword();
						JSONObject jsonOBJ = (JSONObject) jsonArray.get(i);
						r.setServizio(jsonOBJ.getString("servizio"));
						r.setCriptUsername(jsonOBJ.getString("username"));
						r.setCriptPassword(jsonOBJ.getString("password"));
						
						values.put("servizio", r.getServizio());
						values.put("username", r.getCriptUsername());
						values.put("password", r.getCriptPassword());
						
						if(db.insert("rubrica", null, values) < 0)
						{
							new PersonalDialog(activity, "ERRORE", "Si e' verificato un errore nel salvataggio dei dati.", "Chiudi");
							finish();
						}
						else
							((TextView) findViewById(R.id.backupView)).setText("Ripristino completato!");
					}
					
					db.close();
				}
				catch(Exception exception)
				{
					
					new ErrorAlert(activity);
				}
			}
		}
	}
	
	public void procedi(View view)
	{
		finish();
	}
 
	public static String POST(String url)
	{
		InputStream inputStream = null;
		String result = "";
		
		try
		{
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
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
