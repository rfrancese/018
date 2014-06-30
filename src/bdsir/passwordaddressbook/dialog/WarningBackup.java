package bdsir.passwordaddressbook.dialog;

import bdsir.passwordaddressbook.json.RipristinoJson;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

public class WarningBackup
{	
	private AlertDialog.Builder alert;
	
	public WarningBackup(final Activity activity)
	{
		alert = new AlertDialog.Builder(activity);
		alert.setTitle("Attenzione!");
		alert.setMessage("Se verra' effettuato il ripristino tutte le password in rubrica verranno sostituite con le password del ripristino."
				+ "\nSicuro di voler continuare?");
		alert.setCancelable(false);
		alert.setPositiveButton("Si", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				activity.startActivity(new Intent(activity.getApplicationContext(), RipristinoJson.class));;
			}
		});
		alert.setNegativeButton("Indietro", null);
		alert.create();
		alert.show();
	}
	
}
