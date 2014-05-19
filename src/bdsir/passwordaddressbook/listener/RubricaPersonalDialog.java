package bdsir.passwordaddressbook.listener;

import bdsir.passwordaddressbook.ViewAddressBook;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;


public class RubricaPersonalDialog implements OnClickListener
{
	private Activity activity;
	
	public RubricaPersonalDialog(Activity activity)
	{
		this.activity = activity;
	}
	
	public void onClick(DialogInterface dialog, int which)
	{
		Intent viewAddressBook = new Intent(activity.getApplicationContext(), ViewAddressBook.class);
		activity.startActivity(viewAddressBook);
		activity.finish();
	}

}
