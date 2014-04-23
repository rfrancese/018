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
		
//		setPersonalFont();
	}
	
//	private void setPersonalFont()
//	{
//		Typeface nothingYouCouldSay = Typeface.createFromAsset(getAssets(), "fonts/NothingYouCouldSay.ttf");
//		
//		TextView help1 = (TextView) findViewById(R.id.help1);
//		TextView help2 = (TextView) findViewById(R.id.help2);
//		TextView insPass = (TextView) findViewById(R.id.textInsPassword);
//		TextView reinsPass = (TextView) findViewById(R.id.textReinsPassword);
//
//		Button annulla = (Button) findViewById(R.id.buttAnnula);
//		Button procedi = (Button) findViewById(R.id.buttProcedi);
//		
//		help1.setTypeface(nothingYouCouldSay);
//		help2.setTypeface(nothingYouCouldSay);
//		insPass.setTypeface(nothingYouCouldSay);
//		reinsPass.setTypeface(nothingYouCouldSay);
//		annulla.setTypeface(nothingYouCouldSay);
//		procedi.setTypeface(nothingYouCouldSay);
//	}
}
