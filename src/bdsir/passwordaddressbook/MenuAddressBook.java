package bdsir.passwordaddressbook;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class MenuAddressBook extends Activity
{
	private Intent addService;
	private Intent addressBook;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_menu);
		
		setPersonalFont();
		
		addService = new Intent(getApplicationContext(), AddService.class);
		addressBook = new Intent(getApplicationContext(), ViewAddressBook.class);
	}
	
	public boolean onCreateOptionsMenu(Menu menu)
	{
	    super.onCreateOptionsMenu(menu);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.home_menu, menu);
	    return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item)
	{
	    switch ( item.getItemId() )
	    {
	        case R.id.subMenuLogin:
	        	startActivity(new Intent(this, AddService.class));
	        	return true;
	        case R.id.subMenuExit:
	        	finish();
	        	return true;
	    }
	    return false;
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
	
	public void viewAddressBook(View view)
	{
		startActivity(addressBook);
	}
}