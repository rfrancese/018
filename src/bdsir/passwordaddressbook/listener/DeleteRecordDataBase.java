package bdsir.passwordaddressbook.listener;

import bdsir.passwordaddressbook.ListRemoveService;
import bdsir.passwordaddressbook.ViewAddressBook;
import bdsir.passwordaddressbook.database.DataBaseHelper;
import bdsir.passwordaddressbook.dialog.PersonalDialog;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.sqlite.SQLiteDatabase;

public class DeleteRecordDataBase implements OnClickListener
{
	private String servizio;
	private String username;
	private Activity activity;
	
	public DeleteRecordDataBase(Activity activity, String servizio, String username)
	{
		this.activity = activity;
		this.servizio = servizio;
		this.username = username;
	}
	
	public void onClick(DialogInterface dialog, int which)
	{
		DataBaseHelper dataBaseHelper = new DataBaseHelper(activity);
		SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
		String whereClause = "servizio = ? AND username = ?";
		String whereArgs[] = {servizio, username};
		if(db.delete("rubrica", whereClause, whereArgs) > 0)
		{
			db.close();
			new PersonalDialog(activity, "Elimina Servizio", "Eliminazione avvenuta con successo.", "Indietro");
			
			if(activity.getClass().getName().equals(ViewAddressBook.class.getName()))
				((ViewAddressBook) activity).loadDatabase();
			else
				((ListRemoveService) activity).loadService();
		}
		else
		{
			db.close();
			new PersonalDialog(activity, "Errore", "Si e' verificato un errore nella cancellazione del servizio.", "Chiudi");
		}
	}

}
