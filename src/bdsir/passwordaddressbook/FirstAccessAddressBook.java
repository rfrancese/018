package bdsir.passwordaddressbook;

import bdsir.passwordaddressbook.listener.CloseActivity;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class FirstAccessAddressBook extends Activity
{
	private Button annulla;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_primo_accesso);
		
//		setPersonalFont();
		setListener();
	}
	
	private void setPersonalFont()
	{
		Typeface nothingYouCouldSay = Typeface.createFromAsset(getAssets(), "fonts/NothingYouCouldSay.ttf");
		
		TextView help1 = (TextView) findViewById(R.id.help1);
		TextView help2 = (TextView) findViewById(R.id.help2);
		TextView insPass = (TextView) findViewById(R.id.textInsPassword);
		TextView reinsPass = (TextView) findViewById(R.id.textReinsPassword);

		annulla = (Button) findViewById(R.id.buttAnnula);
		Button procedi = (Button) findViewById(R.id.buttProcedi);
		
		help1.setTypeface(nothingYouCouldSay);
		help2.setTypeface(nothingYouCouldSay);
		insPass.setTypeface(nothingYouCouldSay);
		reinsPass.setTypeface(nothingYouCouldSay);
		annulla.setTypeface(nothingYouCouldSay);
		procedi.setTypeface(nothingYouCouldSay);
	}
	
	private void setListener()
	{
		annulla.setOnClickListener(new CloseActivity(this));
	}
}
