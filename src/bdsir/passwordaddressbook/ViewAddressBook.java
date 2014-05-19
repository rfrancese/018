package bdsir.passwordaddressbook;

import java.util.ArrayList;
import java.util.HashMap;

import bdsir.passwordaddressbook.database.DataBaseHelper;
import bdsir.passwordaddressbook.database.RecordPassword;
import bdsir.passwordaddressbook.dialog.PersonalDialog;
import bdsir.passwordaddressbook.listener.MenuItemClick;
import bdsir.passwordaddressbook.listener.ProximitySensor;
import bdsir.passwordaddressbook.listener.RecordClick;
import bdsir.passwordaddressbook.tools.EmptyControllRecord;
import bdsir.passwordaddressbook.tools.MenuItemId;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleAdapter;

public class ViewAddressBook extends Activity
{
	public static boolean stateShowPassword = false;
	
	private DataBaseHelper databseHelper;
	private ArrayList<HashMap<String, Object>> data;
	private SensorManager sensorManager;
	private ProximitySensor prossimita;
	private Sensor sensor;
	private ListView viewAddressBok;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_list_visualizza_password);

		databseHelper = new DataBaseHelper(this);
		loadDatabase();
		
		prossimita = new ProximitySensor(this);		
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
		sensorManager.registerListener(prossimita, sensor, SensorManager.SENSOR_DELAY_UI);
	}
	
	protected void onRestart()
	{
		super.onRestart();
		loadDatabase();
	}
	
	protected void onResume()
	{
		super.onResume();
		sensorManager.registerListener(prossimita, sensor, SensorManager.SENSOR_DELAY_UI);
	}
	
	protected void onPause()
    {
        super.onPause();
        sensorManager.unregisterListener(prossimita, sensor);
        hidePassword();
    }
    
    protected void onStop()
    {
        super.onStop();
        sensorManager.unregisterListener(prossimita, sensor);
        hidePassword();
    }
    
    protected void onDestroy()
    {
        super.onDestroy();
        sensorManager.unregisterListener(prossimita, sensor);
        hidePassword();
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
	
	private void loadDatabase()
	{
		SQLiteDatabase db = databseHelper.getReadableDatabase();
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
		
        databseHelper.close();
        viewAddressBok = (ListView) findViewById(R.id.listRubricaPassword);
        hidePassword();
	}
	
	public void hidePassword()
	{
		String[] from = {"servizio", "username"};
        int[] to = {R.id.textDbServizio, R.id.textDbUsername};

        SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), data, R.layout.layout_rubrica, from, to);
        
        viewAddressBok.setAdapter(adapter);
        viewAddressBok.setOnItemClickListener(new RecordClick(this));
        
        stateShowPassword = false;
	}
	
	public void showPassword()
	{
		String[] from = {"servizio", "username", "password"};
        int[] to = {R.id.textDbServizio, R.id.textDbUsername, R.id.textDbPassword};

        SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), data, R.layout.layout_rubrica, from, to);

        viewAddressBok.setAdapter(adapter);
        viewAddressBok.setOnItemClickListener(new RecordClick(this));
        
        stateShowPassword = true;
	}
	
	public void addServizio(View view)
	{
		if(ViewAddressBook.stateShowPassword)
		{
			startActivity(new Intent(getApplicationContext(), AddService.class));
		}
		else
			new PersonalDialog(this, "Attenzione", "Devi prima effettuare l'accesso al sistema!", "Indietro");
	}
}