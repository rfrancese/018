package bdsir.passwordaddressbook;

import java.util.ArrayList;
import java.util.HashMap;

import bdsir.passwordaddressbook.database.DataBaseHelper;
import bdsir.passwordaddressbook.database.RecordPassword;
import bdsir.passwordaddressbook.dialog.AccediDialog;
import bdsir.passwordaddressbook.listener.MenuItemClick;
import bdsir.passwordaddressbook.listener.RecordClick;
import bdsir.passwordaddressbook.tools.EmptyControllRecord;
import bdsir.passwordaddressbook.tools.MenuItemId;
import bdsir.passwordaddressbook.tools.ProximitySensor;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ViewAddressBook extends Activity
{
	public static boolean stateShowPassword = false;
	public static boolean activityForeground = true;
	public static ProximitySensor sensorListener;

	private PowerManager pm;
	private ArrayList<HashMap<String, Object>> data;
	private ListView listAddressBok;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_list_visualizza_password);

		loadDatabase();
		pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		listAddressBok.setOnItemClickListener(new RecordClick(this));
		sensorListener = new ProximitySensor(this);
	}
	
	protected void onPause()
	{
		super.onPause();
		if(!pm.isScreenOn())
			hidePassword();
		
		if(activityForeground)
			hidePassword();
	}
	
    protected void onStop()
    {
        super.onStop();
        if(!pm.isScreenOn())
        	hidePassword();
    }
    
	protected void onRestart()
	{
		super.onRestart();
		loadDatabase();
		activityForeground = true;
	}
    
    protected void onDestroy()
    {
        super.onDestroy();
        hidePassword();
        ProximitySensor.sensorOFF(this);
    }
	
	public boolean onCreateOptionsMenu(Menu menu)
	{
	    super.onCreateOptionsMenu(menu);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.home_menu, menu);
	    return true;
	}
	
	public void showPopup(View view)
	{
		PopupMenu popup = new PopupMenu (this , view); 
	    MenuInflater inflater = popup.getMenuInflater(); 
	    inflater.inflate(R.menu.home_menu, popup.getMenu());
	    popup.setOnMenuItemClickListener(new MenuItemClick(this));
	    popup.show(); 
	}
	
	public boolean onOptionsItemSelected(MenuItem item)
	{
	   return MenuItemId.getMenuItemClick(this, item);
	}
	
	public void loadDatabase()
	{
		DataBaseHelper databaseHelper = new DataBaseHelper(this);
		SQLiteDatabase db = databaseHelper.getReadableDatabase();
		String[] columns = {"servizio", "username", "password"};
		String orderBy = "servizio ASC";
		Cursor cursor = db.query("rubrica", columns, null, null, null, null, orderBy);
		
		ArrayList<RecordPassword> list = new ArrayList<RecordPassword>();
		
		boolean flag = false;
		while(cursor.moveToNext())
		{
			RecordPassword record = new RecordPassword
			(
				cursor.getString(cursor.getColumnIndex("servizio")),
				cursor.getString(cursor.getColumnIndex("username")),
				cursor.getString(cursor.getColumnIndex("password"))
			);
			
			list.add(record);
			
			flag = true;
		}
		
		EmptyControllRecord.controllRecord(flag, (LinearLayout) findViewById(R.id.linearLayoutRubrica), R.drawable.empty_password_rubrica);
		
		data = new ArrayList<HashMap<String,Object>>();
		
		for(int i = 0; i < list.size(); i++)
		{
            RecordPassword r = list.get(i);

            HashMap<String,Object> recordMap = new HashMap<String, Object>();
            
            recordMap.put("servizio", r.getServizio());
            recordMap.put("username", r.getUsername());
            recordMap.put("password", r.getPassword());
            
            data.add(recordMap);
		}
		
        databaseHelper.close();
        listAddressBok = (ListView) findViewById(R.id.listRubricaPassword);
        
        if(!stateShowPassword)
        	hidePassword();
        else
        	showPassword();
	}
	
	public void hidePassword()
	{
		String[] from = {"servizio", "username"};
        int[] to = {R.id.textDbServizio, R.id.textDbUsername};

        SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), data, R.layout.layout_rubrica_hide, from, to);
        
        listAddressBok.setAdapter(adapter);
        
        stateShowPassword = false;
        
        findViewById(R.id.imgLogSystem).setBackgroundResource(R.drawable.background_logout);
        ((TextView) findViewById(R.id.logSystem)).setText(R.string.login);
	}
	
	public void showPassword()
	{
		String[] from = {"servizio", "username", "password"};
        int[] to = {R.id.textDbServizio, R.id.textDbUsername, R.id.textDbPassword};

        SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), data, R.layout.layout_rubrica_show, from, to);
        
        listAddressBok.setAdapter(adapter);
        
        stateShowPassword = true;
        
        findViewById(R.id.imgLogSystem).setBackgroundResource(R.drawable.background_login);
        ((TextView) findViewById(R.id.logSystem)).setText(R.string.logout);
	}
	
	public void addServizio(View view)
	{
		if(ViewAddressBook.stateShowPassword)
		{
			activityForeground = false;
			startActivity(new Intent(getApplicationContext(), AddService.class));
		}
		else
			new AccediDialog(this, "addService", null);
	}

	public void login(View view)
	{
		if(!stateShowPassword)
		{
			ProximitySensor.sensorON(this);
			new AccediDialog(this, null, null);
		}
		else
		{
			ProximitySensor.sensorOFF(this);
			hidePassword();
		}
	}
}