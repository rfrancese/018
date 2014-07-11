package bdsir.passwordaddressbook;

import java.util.ArrayList;
import java.util.HashMap;

import bdsir.passwordaddressbook.database.DataBaseHelper;
import bdsir.passwordaddressbook.database.RecordPassword;
import bdsir.passwordaddressbook.dialog.AccediDialog;
import bdsir.passwordaddressbook.dialog.ErrorAlert;
import bdsir.passwordaddressbook.listener.MenuItemClick;
import bdsir.passwordaddressbook.listener.RecordClick;
import bdsir.passwordaddressbook.tools.EmptyControllRecord;
import bdsir.passwordaddressbook.tools.MenuItemId;
import bdsir.passwordaddressbook.tools.ProximitySensor;
import android.accounts.Account;
import android.accounts.AccountManager;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.SearchView.OnCloseListener;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.SearchView.OnQueryTextListener;

public class ViewAddressBook extends Activity implements OnQueryTextListener, OnCloseListener
{
	public static boolean stateShowPassword = false;
	public static boolean activityForeground = true;
	public static ProximitySensor sensorListener;
	public static ArrayList<RecordPassword> list;
	public static DataBaseHelper databaseHelper;
	
	private PowerManager pm;
	private ArrayList<HashMap<String, Object>> data;
	private ListView listAddressBok;
	private boolean flag;
	private SimpleAdapter defalutAdapter;
	private SQLiteDatabase db;
	private SearchView searchView;
	private static String account = SplashActivity.account;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_list_visualizza_password);

		loadDatabase();
		pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		listAddressBok.setOnItemClickListener(new RecordClick(this));
		sensorListener = new ProximitySensor(this);
		
		setSearchView();
	}
	
	public void setSearchView()
	{
		searchView = (SearchView) findViewById(R.id.searchView);
		searchView.setOnQueryTextListener(this);
		searchView.setOnCloseListener(this);
	}
	
	@Override
	public boolean onClose()
	{
		listAddressBok.setAdapter(defalutAdapter);
		return false;
	}

	@Override
	public boolean onQueryTextChange(String inputText)
	{
		if (!inputText.isEmpty())
		{
            displayResults(inputText);
        }
		else
		{
            listAddressBok.setAdapter(defalutAdapter);
        }
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String inputText)
	{
		displayResults(inputText);
		return false;
	}
	
	protected void onPause()
	{
		super.onPause();
		if(!pm.isScreenOn())
			hidePassword();
		
		if(activityForeground)
			hidePassword();
		
		searchView.clearFocus();
		searchView.setQuery("", false);
		db.close();
	}
	
    protected void onStop()
    {
        super.onStop();
        if(!pm.isScreenOn())
        	hidePassword();
    }
    
	protected void onResume()
	{
		super.onResume();
		loadDatabase();
		activityForeground = true;
		ListModifyPassword.activityListModificyPassword = false;
		
		AccountManager manager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
		Account[] list = manager.getAccountsByType("com.google");
		SplashActivity.account = list[0].name;
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
		try
		{
			databaseHelper = new DataBaseHelper(this);
			db = databaseHelper.getReadableDatabase();
			String[] columns = {"servizio", "username", "password"};
			String orderBy = "servizio ASC";
			Cursor cursor = db.query("rubrica", columns, null, null, null, null, orderBy);
			
			list = new ArrayList<RecordPassword>();
			
			flag = false;
			while(cursor.moveToNext())
			{
				RecordPassword record = new RecordPassword();
				record.setServizio(cursor.getString(cursor.getColumnIndex("servizio")));
				record.setCriptUsername(cursor.getString(cursor.getColumnIndex("username")));
				record.setCriptPassword(cursor.getString(cursor.getColumnIndex("password")));
				
				list.add(record);
				
				flag = true;
			}
					
			data = new ArrayList<HashMap<String,Object>>();
			
			for(int i = 0; i < list.size(); i++)
			{
	            RecordPassword r = list.get(i);
	
	            HashMap<String,Object> recordMap = new HashMap<String, Object>();
	            
	            recordMap.put("servizio", r.getServizio());
	            recordMap.put("username", r.getDecriptUsername());
	            recordMap.put("password", r.getDecriptPassword());
	            
	            data.add(recordMap);
			}
			
	        databaseHelper.close();
	        listAddressBok = (ListView) findViewById(R.id.listRubricaPassword);
	        
	        if(!stateShowPassword)
	        	hidePassword();
	        else
	        	showPassword();
		}
		catch(Exception e)
		{
			new ErrorAlert(this);
		}
	}
	
	public void hidePassword()
	{
		String[] from = {"servizio", "username"};
        int[] to = {R.id.textDbServizio, R.id.textDbUsername};

        defalutAdapter = new SimpleAdapter(getApplicationContext(), data, R.layout.layout_rubrica_hide, from, to);
        
        listAddressBok.setAdapter(defalutAdapter);
        
        stateShowPassword = false;
        
        findViewById(R.id.imgLogSystem).setBackgroundResource(R.drawable.background_logout);
        ((TextView) findViewById(R.id.logSystem)).setText(R.string.login);
		((Button) findViewById(R.id.logSystem)).setBackgroundResource(R.drawable.background_logout);
		
		EmptyControllRecord.controllRecord(flag, (LinearLayout) findViewById(R.id.linearLayoutRubrica), R.drawable.empty_password_rubrica_1);
	}
	
	public void showPassword()
	{
		String[] from = {"servizio", "username", "password"};
        int[] to = {R.id.textDbServizio, R.id.textDbUsername, R.id.textDbPassword};

        defalutAdapter = new SimpleAdapter(getApplicationContext(), data, R.layout.layout_rubrica_show, from, to);
        
        listAddressBok.setAdapter(defalutAdapter);
        
        stateShowPassword = true;
        
        findViewById(R.id.imgLogSystem).setBackgroundResource(R.drawable.background_login);
        ((TextView) findViewById(R.id.logSystem)).setText("Nascondi tutto!");
		((Button) findViewById(R.id.logSystem)).setBackgroundResource(R.drawable.background_login);
		
		EmptyControllRecord.controllRecord(flag, (LinearLayout) findViewById(R.id.linearLayoutRubrica), R.drawable.empty_password_rubrica_2);
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
		
		searchView.clearFocus();
		searchView.setQuery("", false);
	}
	
	public boolean isEmpty()
	{
		return flag;
	}

	public static String getAccount()
	{
		return account;
	}
	
	private void displayResults(String inputText)
	{
		try
		{
			SimpleAdapter adapter;
			db = databaseHelper.getReadableDatabase();
	
	        String query = "SELECT * FROM rubrica WHERE servizio LIKE '" + inputText + "%' ORDER BY servizio ASC;";
	 
	        Cursor cursor = db.rawQuery(query, null);
	        
	        if(cursor.getCount() != 0)
	        {
	        	ArrayList<RecordPassword> listTMP = new ArrayList<RecordPassword>();
	            ArrayList<HashMap<String, Object>> dataTMP = new ArrayList<HashMap<String,Object>>();
	
	        	cursor.moveToFirst();        
	
				do
				{
					RecordPassword record = new RecordPassword();
					
					record.setServizio(cursor.getString(cursor.getColumnIndex("servizio")));
					record.setCriptUsername(cursor.getString(cursor.getColumnIndex("username")));
					record.setCriptPassword(cursor.getString(cursor.getColumnIndex("password")));
					
					listTMP.add(record);
					
				}
				while(cursor.moveToNext());
			
				for(int i = 0; i < listTMP.size(); i++)
				{
		            RecordPassword r = listTMP.get(i);
		
		            HashMap<String,Object> recordMap = new HashMap<String, Object>();
		            
		            recordMap.put("servizio", r.getServizio());
		            recordMap.put("username", r.getDecriptUsername());
		            recordMap.put("password", r.getDecriptPassword());
		            
		            dataTMP.add(recordMap);
				}
				
	        	if(stateShowPassword)
	        	{
	        		String[] from = {"servizio", "username", "password"};
	                int[] to = {R.id.textDbServizio, R.id.textDbUsername, R.id.textDbPassword};
	                adapter = new SimpleAdapter(getApplicationContext(), dataTMP, R.layout.layout_rubrica_show, from, to);
	        	}
	        	else
	        	{
	        		String[] from = {"servizio", "username"};
	                int[] to = {R.id.textDbServizio, R.id.textDbUsername};                
	                adapter = new SimpleAdapter(getApplicationContext(), dataTMP, R.layout.layout_rubrica_hide, from, to);
	        	}
	 
	            listAddressBok.setAdapter(adapter);
	        }
	        else
	        {
	        	String[] from = {"empty"};
	        	int[] to = {R.id.textEmpty};
	        	
	            ArrayList<HashMap<String, Object>> dataTMP = new ArrayList<HashMap<String,Object>>();
	            HashMap<String, Object> hashTMP = new HashMap<String, Object>();
	            
	            hashTMP.put("empty", getString(R.string.textEmpty));
	            dataTMP.add(hashTMP);
	            
	        	adapter = new SimpleAdapter(getApplicationContext(), dataTMP, R.layout.layout_rubrica_empty, from, to);
	        	listAddressBok.setAdapter(adapter);
	        }
	
	        db.close();
		}
		catch(Exception e)
		{
			new ErrorAlert(this);
		}
    }
}