package bdsir.passwordaddressbook.listener;

import bdsir.passwordaddressbook.R;
import bdsir.passwordaddressbook.tools.RemoveRecord;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ListItemElimina implements OnItemClickListener
{
	private Activity activity;
	
	public ListItemElimina(Activity activity)
	{
		this.activity = activity;
	}
	
	public void onItemClick(AdapterView<?> adattatore, View view, int pos, long id)
	{
		RemoveRecord.removeRecord(activity, view, R.id.modifyTextService, R.id.modifyTextUsername);
	}

}
