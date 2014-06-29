package bdsir.passwordaddressbook;

import java.util.ArrayList;
import java.util.HashMap;

import bdsir.passwordaddressbook.database.DataBaseHelper;
import bdsir.passwordaddressbook.dialog.ErrorAlert;
import bdsir.passwordaddressbook.listener.ListItemModifica;
import bdsir.passwordaddressbook.tools.CriptPassword;
import bdsir.passwordaddressbook.tools.EmptyControllRecord;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ListModifyPassword extends Activity
{
	public static boolean activityListModificyPassword = false;
	
	private PowerManager pm;
	private DataBaseHelper databseHelper;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_list_modifica_password);
		
		activityListModificyPassword = true;
		pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		databseHelper = new DataBaseHelper(this);
		loadService();
	}
	
	protected void onRestart()
	{
		super.onRestart();
		ViewAddressBook.stateShowPassword = false;
		finish();
	}
		
	protected void onPause()
	{
		super.onPause();
		if(!pm.isScreenOn())
		{
			activityListModificyPassword = false;
			ViewAddressBook.stateShowPassword = false;
			finish();
		}
	}
	
	protected void onStop()
	{
		super.onStop();
		if(!pm.isScreenOn())
		{
			activityListModificyPassword = false;
			ViewAddressBook.stateShowPassword = false;
			finish();
		}
	}

	protected void onDestroy()
	{
		super.onDestroy();
		if(!pm.isScreenOn())
		{
			activityListModificyPassword = false;
			ViewAddressBook.stateShowPassword = false;
			finish();
		}
	}
	
	private void loadService()
	{
		try
		{
			SQLiteDatabase db = databseHelper.getReadableDatabase();
			String[] columns = {"servizio", "username"};
			String orderBy = "servizio ASC";
			Cursor cursor = db.query("rubrica", columns, null, null, null, null, orderBy);
			
			ArrayList<HashMap<String,Object>> data = new ArrayList<HashMap<String,Object>>();
			
			boolean flag = false;
			while(cursor.moveToNext())
			{
				HashMap<String,Object> serviceMap = new HashMap<String, Object>();
				serviceMap.put("servizio", cursor.getString(cursor.getColumnIndex("servizio")));
				serviceMap.put("username", CriptPassword.decrypt(cursor.getString(cursor.getColumnIndex("username"))));
				
				data.add(serviceMap);
				
				flag = true;
			}
			
			EmptyControllRecord.controllRecord(flag, (LinearLayout) findViewById(R.id.linearLayoutModifica), R.drawable.empty_password); 
			
	        databseHelper.close();
	        
			String[] from = {"servizio", "username"};
	        int[] to = {R.id.modifyTextService, R.id.modifyTextUsername};
	
	        SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), data, R.layout.layout_modifica, from, to);
	
	        ListView listView = ((ListView) findViewById(R.id.listModificaPassword));
	        listView.setAdapter(adapter);
	        listView.setOnItemClickListener(new ListItemModifica(this));
		}
		catch(Exception e)
		{
			new ErrorAlert(this);
		}
	}
}
