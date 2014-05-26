package bdsir.passwordaddressbook.listener;

import bdsir.passwordaddressbook.ListModifyPassword;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;

public class ClosePersonalDialog implements OnClickListener
{
	private Activity activity;
	
	public ClosePersonalDialog(Activity activity)
	{
		this.activity = activity;
	}
	
	public void onClick(DialogInterface dialog, int which)
	{
		dialog.dismiss();
		activity.finish();
		if(ListModifyPassword.activityListModificyPassword)
			activity.startActivity(new Intent(activity.getApplicationContext(), ListModifyPassword.class));
	}
	
}