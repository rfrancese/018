package bdsir.passwordaddressbook;

import java.util.ArrayList;
import java.util.HashMap;

import bdsir.passwordaddressbook.database.DataBaseHelper;
import bdsir.passwordaddressbook.listener.ListItemElimina;
import bdsir.passwordaddressbook.tools.EmptyControllRecord;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ListRemoveService extends Activity
{
	private DataBaseHelper databseHelper;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_list_elimina_servizio);
		
		databseHelper = new DataBaseHelper(this);
		loadService();
	}
	
	protected void onRestart()
	{
		super.onRestart();
		loadService();
	}
	
	protected void onResume()
	{
		super.onResume();
		loadService();
	}
	
	protected void onPause()
    {
        super.onPause();
        loadService();
    }
	
	private void loadService()
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
			serviceMap.put("username", cursor.getString(cursor.getColumnIndex("username")));
			
			data.add(serviceMap);
			
			flag = true;
		}
		
		EmptyControllRecord.controllRecord(flag, (LinearLayout) findViewById(R.id.linearLayoutElimina), R.drawable.empty_password); 

        databseHelper.close();
        
		String[] from = {"servizio", "username"};
        int[] to = {R.id.modifyTextService, R.id.modifyTextUsername};

        SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), data, R.layout.layout_modifica, from, to);

        ListView listView = ((ListView) findViewById(R.id.listEliminaPassword));
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new ListItemElimina(this));
	}
}
