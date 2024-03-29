package bdsir.passwordaddressbook.listener;

import bdsir.passwordaddressbook.R;
import bdsir.passwordaddressbook.tools.ModifyRecord;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ListItemModifica implements OnItemClickListener
{
	private Activity activity;
	
	public ListItemModifica(Activity activity)
	{
		this.activity = activity;
	}
	
	public void onItemClick(AdapterView<?> adattatore, View view, int pos, long id)
	{
		ModifyRecord.modifyRecord(activity, view, R.id.modifyTextService, R.id.modifyTextUsername);
	}

}
