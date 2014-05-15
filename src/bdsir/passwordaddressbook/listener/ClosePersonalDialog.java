package bdsir.passwordaddressbook.listener;

import bdsir.passwordaddressbook.MenuAddressBook;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;

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
	}
	
}
