package bdsir.passwordaddressbook.listener;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class OkPersonalDialog implements OnClickListener
{
	private Activity activity;
	
	public OkPersonalDialog(Activity activity)
	{
		this.activity = activity;
	}
	
	public void onClick(DialogInterface dialog, int which)
	{
		dialog.dismiss();
		activity.finish();
	}
}
