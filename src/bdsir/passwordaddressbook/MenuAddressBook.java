package bdsir.passwordaddressbook;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class MenuAddressBook extends Activity
{
	private Intent addService;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_menu);
		
		setPersonalFont();
		
		addService = new Intent(getApplicationContext(), AddService.class);
	}
	
	private void setPersonalFont()
	{
		Typeface nothingYouCouldSay = Typeface.createFromAsset(getAssets(), "fonts/NothingYouCouldSay.ttf");
		
		Button visualizzaRubrica = (Button) findViewById(R.id.buttViewAddressBook);
		Button aggiungiServizio = (Button) findViewById(R.id.buttAddService);
		Button modificaServizio = (Button) findViewById(R.id.buttModifyService);
		Button rimuoviServizio = (Button) findViewById(R.id.buttRemoveService);
		Button modificaPasswordSistema = (Button) findViewById(R.id.buttModifyPasswordSystem);
		Button recuperaPassword = (Button) findViewById(R.id.buttRecoverPassword);
		Button backUp = (Button) findViewById(R.id.buttBackUp);
		
		TextView menu = (TextView) findViewById(R.id.textMenu);
		
		visualizzaRubrica.setTypeface(nothingYouCouldSay);
		aggiungiServizio.setTypeface(nothingYouCouldSay);
		modificaServizio.setTypeface(nothingYouCouldSay);
		rimuoviServizio.setTypeface(nothingYouCouldSay);
		modificaPasswordSistema.setTypeface(nothingYouCouldSay);
		recuperaPassword.setTypeface(nothingYouCouldSay);
		backUp.setTypeface(nothingYouCouldSay);
		
		menu.setTypeface(nothingYouCouldSay);
	}
	
	public void viewAddService(View view)
	{
		startActivity(addService);
	}
}