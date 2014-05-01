package bdsir.passwordaddressbook;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class AddService extends Activity
{
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aggiungi_servizio);
		
		setPersonalFont();
	}
	
	private void setPersonalFont()
	{
		Typeface nothingYouCouldSay = Typeface.createFromAsset(getAssets(), "fonts/NothingYouCouldSay.ttf");
		
		TextView serviceType = (TextView) findViewById(R.id.serviceType);
		TextView esServiceType = (TextView) findViewById(R.id.esServiceType);
		
		TextView username = (TextView) findViewById(R.id.username);
		TextView esUsername = (TextView) findViewById(R.id.esUsername);
		
		TextView servicePassword = (TextView) findViewById(R.id.servicePassword);
		TextView esServicePassword = (TextView) findViewById(R.id.esServicePassword);

		Button annulla = (Button) findViewById(R.id.buttAnnula);
		Button procedi = (Button) findViewById(R.id.buttProcedi);
		
		serviceType.setTypeface(nothingYouCouldSay);
		esServiceType.setTypeface(nothingYouCouldSay);
		username.setTypeface(nothingYouCouldSay);
		esUsername.setTypeface(nothingYouCouldSay);
		servicePassword.setTypeface(nothingYouCouldSay);
		esServicePassword.setTypeface(nothingYouCouldSay);
		
		annulla.setTypeface(nothingYouCouldSay);
		procedi.setTypeface(nothingYouCouldSay);
	}
}
