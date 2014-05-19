package bdsir.passwordaddressbook;

import java.util.Timer;
import java.util.TimerTask;

import bdsir.passwordaddressbook.database.DataBaseHelper;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class SplashActivity extends Activity
{
	private Timer timer;
	private DataBaseHelper dataBaseHelper;
	private SQLiteDatabase db;
	private String query;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash_activity);
		
		dataBaseHelper = new DataBaseHelper(this);
		setVersionText();
		animation();
	}
	
	protected void onPause()
    {
        super.onPause();
        timer.cancel();
        finish();
    }
    
    protected void onStop()
    {
        super.onStop();
        timer.cancel();
        finish();
    }
    
    protected void onDestroy()
    {
        super.onDestroy();
        timer.cancel();
        finish();
    }
	
	private void animation()
	{
		db = dataBaseHelper.getReadableDatabase();
		query = "SELECT * FROM utente";
		
		TimerTask task = new TimerTask()
		{
			public void run()
			{
				if(db.rawQuery(query, null).getCount() > 0)
				{
					db.close();
					Intent viewAddressBook = new Intent(getApplicationContext(), ViewAddressBook.class);
					startActivity(viewAddressBook);
					finish();
				}
				else
				{
					db.close();
					Intent firstAccess = new Intent(getApplicationContext(), FirstAccessAddressBook.class);
					startActivity(firstAccess);
					finish();
				}
			}
		};
		
		timer = new Timer();
		timer.schedule(task, 2500);
	}
	
	private void setVersionText()
	{
		TextView splashVersionName = (TextView) findViewById(R.id.splashVersionName);
				
		try
		{
			String version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
			splashVersionName.setText("V " + version);
		}
		catch (NameNotFoundException e)
		{
			finish();
		}
	}
}
