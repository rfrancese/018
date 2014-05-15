package bdsir.passwordaddressbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

public class MenuAddressBook extends Activity
{
	private Intent addService;
	private Intent addressBook;
	private Intent modifyService;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_menu);
		
		addService = new Intent(getApplicationContext(), AddService.class);
		addressBook = new Intent(getApplicationContext(), ViewAddressBook.class);
		modifyService = new Intent(getApplicationContext(), ListModifyService.class);
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
    
	public void addService(View view)
	{
		startActivity(addService);
	}
	
	public void viewAddressBook(View view)
	{
		startActivity(addressBook);
	}
	
	public void modifyService(View view)
	{
		startActivity(modifyService);
	}
}