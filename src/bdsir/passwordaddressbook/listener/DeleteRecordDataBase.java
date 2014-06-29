package bdsir.passwordaddressbook.listener;

import bdsir.passwordaddressbook.ListRemoveService;
import bdsir.passwordaddressbook.ViewAddressBook;
import bdsir.passwordaddressbook.database.DataBaseHelper;
import bdsir.passwordaddressbook.dialog.PersonalDialog;
import bdsir.passwordaddressbook.dialog.RemoveDialog;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.View.OnClickListener;

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
	
	public void onClick(View view)
	{
		DataBaseHelper dataBaseHelper = ViewAddressBook.databaseHelper;
		SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
		String whereClause = "servizio = ? AND username = ?";
		String whereArgs[] = {servizio, username};
		if(db.delete("rubrica", whereClause, whereArgs) > 0)
		{
			new PersonalDialog(activity, "Elimina Servizio", "Eliminazione avvenuta con successo.", "OK!");
			
			if(!activity.getClass().getName().equals(RemoveDialog.class.getName()))
				((ListRemoveService) activity).loadService();
		}
		else
			new PersonalDialog(activity, "Errore", "Si e' verificato un errore nella cancellazione dell'account.", "Chiudi");
		
		db.close();
	}

}
