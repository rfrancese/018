package bdsir.passwordaddressbook.dialog;

import bdsir.passwordaddressbook.listener.*;
import android.app.Activity;
import android.app.AlertDialog;

public class RemoveDialog
{
	private AlertDialog.Builder alert;
	
	public RemoveDialog(Activity activity, String servizio, String username)
	{
		alert = new AlertDialog.Builder(activity);

		alert.setTitle("Elimina Servizio");
		alert.setMessage("Sei sicuro di voler eliminare l'account '" + servizio + "' dalla rubrica?");
		alert.setCancelable(false);
		alert.setNegativeButton("Annulla", null);
		alert.setPositiveButton("Elimina", new DeleteRecordDataBase(activity, servizio, username));
		alert.create();
		alert.show();
	}
	
}
