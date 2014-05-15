package bdsir.passwordaddressbook;

import java.util.ArrayList;
import java.util.HashMap;

import bdsir.passwordaddressbook.database.DataBaseHelper;
import bdsir.passwordaddressbook.listener.CloseActivity;
import bdsir.passwordaddressbook.listener.ListItemModifica;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ListModifyService extends Activity
{
	private DataBaseHelper databseHelper;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_list_modifica_servizio);
		
		setListener();
		databseHelper = new DataBaseHelper(this);
		loadService();
	}
	
	private void setListener()
	{
		Button annulla = (Button) findViewById(R.id.buttAnnula);
		annulla.setOnClickListener(new CloseActivity(this));
	}
	
	private void loadService()
	{
		SQLiteDatabase db = databseHelper.getReadableDatabase();
		String[] columns = {"servizio", "username"};
		String orderBy = "servizio ASC";
		Cursor cursor = db.query("rubrica", columns, null, null, null, null, orderBy);
		
		ArrayList<HashMap<String,Object>> data = new ArrayList<HashMap<String,Object>>();
		
		while(cursor.moveToNext())
		{
			HashMap<String,Object> serviceMap = new HashMap<String, Object>();
			serviceMap.put("servizio", cursor.getString(cursor.getColumnIndex("servizio")));
			serviceMap.put("username", cursor.getString(cursor.getColumnIndex("username")));
			
			data.add(serviceMap);
		}

        databseHelper.close();
        
		String[] from = {"servizio", "username"};
        int[] to = {R.id.modifyTextService, R.id.modifyTextUsername};

        SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), data, R.layout.layout_modifica, from, to);

        ListView listView = ((ListView) findViewById(R.id.listModificaPassword));
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new ListItemModifica(this));
	}
}
