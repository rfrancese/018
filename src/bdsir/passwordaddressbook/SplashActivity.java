package bdsir.passwordaddressbook;

import android.app.Activity;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class SplashActivity extends Activity
{
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash_activity);
		
		setVersionText();
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
