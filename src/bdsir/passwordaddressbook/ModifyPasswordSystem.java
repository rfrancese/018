package bdsir.passwordaddressbook;

import bdsir.passwordaddressbook.database.DataBaseHelper;
import bdsir.passwordaddressbook.dialog.ErrorAlert;
import bdsir.passwordaddressbook.dialog.PersonalDialog;
import bdsir.passwordaddressbook.tools.CriptPassword;
import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

public class ModifyPasswordSystem extends Activity
{
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_modifica_password);
		
		setFinishOnTouchOutside(false);
	}
	
	protected void onRestart()
	{
		super.onRestart();
		ViewAddressBook.stateShowPassword = false;
		finish();
	}
	
	public void annulla(View view)
	{
		finish();
	}
	
	public void procedi(View view)
	{
		try
		{
			String vecchiaPassword = CriptPassword.encrypt(((EditText) findViewById(R.id.editVecchiaPass)).getText().toString().trim());
			String nuovaPassord = ((EditText) findViewById(R.id.editNuovaPass)).getText().toString().trim();
			String reNuovaPassord = ((EditText) findViewById(R.id.editReinsPass)).getText().toString().trim();
			
			DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
			SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
			String query = "SELECT * FROM utente WHERE password = ?";
			String[] valueArgs = {vecchiaPassword};
			
			if(vecchiaPassword.isEmpty() || nuovaPassord.isEmpty() || reNuovaPassord.isEmpty())
				new PersonalDialog(this, "Attenzione", "Inserisci tutti i dati richiesti!", "Indietro");
			else if(db.rawQuery(query, valueArgs).getCount() == 0)
				new PersonalDialog(this, "Errore", "La vecchia password inserita non e' corretta", "Indietro");
			else if(!nuovaPassord.equals(reNuovaPassord))
				new PersonalDialog(this, "Errore", "Le password inserite non corrispondono", "Indietro");
			else
			{
				db.close();
				db = dataBaseHelper.getWritableDatabase();
				
				String whereClause = "password = ?";
				String[] whereArgs = {vecchiaPassword};
				
				ContentValues values = new ContentValues();
				values.put("password", CriptPassword.encrypt(nuovaPassord));
				
				if(db.update("utente", values, whereClause, whereArgs) < 0)
					new PersonalDialog(this, "ERRORE", "Si e' verificato un errore nel caricare i dati nel DataBase.", "Chiudi");
				else
					new PersonalDialog(this, "DATI SALVATI", "I dati inseriti sono stati salvati nel database.", "OK!");
	
				db.close();
				getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			}
		}
		catch (Exception e)
		{
			new ErrorAlert(this);
		}
	}
}
