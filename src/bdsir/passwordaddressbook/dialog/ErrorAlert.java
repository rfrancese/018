package bdsir.passwordaddressbook.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class ErrorAlert
{
	private AlertDialog.Builder alert;
	
	public ErrorAlert(final Activity activity)
	{
		alert = new AlertDialog.Builder(activity);
		alert.setTitle("Errore");
		alert.setMessage("Si e' verificato un errore nella crittografia della password");
		alert.setCancelable(false);
		alert.setPositiveButton("Indietro!", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				activity.finish();
			}
		});
		alert.create();
		alert.show();
	}
}
