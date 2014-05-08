package bdsir.passwordaddressbook;

import bdsir.listener.CloseActivity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddService extends Activity
{
	private Intent menu;
	private Button annulla;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_aggiungi_servizio);
		
		setPersonalFont();
		setListener();
		
		menu = new Intent(getApplicationContext(), MenuAddressBook.class);
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

		annulla = (Button) findViewById(R.id.buttAnnula);
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
	
	private void setListener()
	{
		annulla.setOnClickListener(new CloseActivity(this));
	}
	
	public void procedi(View view)
	{
		EditText servizio = (EditText) findViewById(R.id.editServiceType);
		EditText username = (EditText) findViewById(R.id.editUsername);
		EditText password = (EditText) findViewById(R.id.editPassword);
		
		Bundle bundle = new Bundle();
		bundle.putString("servizio", servizio.getText().toString());
		bundle.putString("username", username.getText().toString());
		bundle.putString("password", password.getText().toString());
		
		Intent test = new Intent(getApplicationContext(), Test.class);
		test.putExtras(bundle);
		startActivity(test);
	}
}
