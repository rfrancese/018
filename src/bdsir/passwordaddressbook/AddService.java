package bdsir.passwordaddressbook;

import bdsir.passwordaddressbook.database.DataBaseHelper;
import bdsir.passwordaddressbook.database.RecordPassword;
import bdsir.passwordaddressbook.dialog.ErrorAlert;
import bdsir.passwordaddressbook.dialog.PersonalDialog;
import bdsir.passwordaddressbook.listener.CloseActivity;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class AddService extends Activity
{
	private PowerManager pm;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_aggiungi_servizio);
		
		pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		setListener();
	}
	
	protected void onRestart()
	{
		super.onRestart();
		ViewAddressBook.stateShowPassword = false;
		finish();
	}
	
	protected void onPause()
	{
		super.onPause();
		if(!pm.isScreenOn())
		{
			ViewAddressBook.stateShowPassword = false;
			finish();
		}
	}
	
	protected void onStop()
	{
		super.onStop();
		if(!pm.isScreenOn())
		{
			ViewAddressBook.stateShowPassword = false;
			finish();
		}
	}

	protected void onDestroy()
	{
		super.onDestroy();
		if(!pm.isScreenOn())
		{
			ViewAddressBook.stateShowPassword = false;
			finish();
		}
	}
	
	private void setListener()
	{
		Button annulla = (Button) findViewById(R.id.buttAnnula);
		annulla.setOnClickListener(new CloseActivity(this));
	}
	
	public void procedi(View view)
	{
		DataBaseHelper dataBase = new DataBaseHelper(this); 
		
		String servizio = ((EditText) findViewById(R.id.editServiceType)).getText().toString().trim();
		String username = ((EditText) findViewById(R.id.editUsername)).getText().toString().trim();
		String password = ((EditText) findViewById(R.id.editPassword)).getText().toString().trim();
		
		if(servizio.isEmpty())
		{
			new PersonalDialog(this, "ATTENZIONE", "Il campo \"Nome Account\" e' vuoto!\nInserisci un account.", "Indietro");
		}
		else if(username.isEmpty())
		{
			new PersonalDialog(this, "ATTENZIONE", "Il campo \"Nome Utente\" e' vuoto!\nInserisci un nome utente", "Indietro");
		}
		else if(password.isEmpty())
		{
			new PersonalDialog(this, "ATTENZIONE", "Il campo \"Password\" e' vuoto!\nInserisci una password.", "Indietro");
		}
		else
		{
			//primo carattere della stringa 'Tipo Servizio' maiuscolo
			servizio = ((String) servizio.subSequence(0, 1)).toUpperCase() + "" + servizio.substring(1);
			
			SQLiteDatabase db = dataBase.getReadableDatabase();
			
			String query = "SELECT * FROM rubrica WHERE servizio = ? AND username = ?";
			String selectionArg[] = {servizio, username};
			
			if(db.rawQuery(query, selectionArg).getCount() > 0)
			{
				db.close();
				new PersonalDialog(this, "Parametri non validi", "Nome Account e Nome Utente gia' presente nel Database.", "Indietro");	
			}	
			else
			{
				db.close();
				db = dataBase.getWritableDatabase();
				ContentValues values = new ContentValues();
				RecordPassword r = null;
				
				try
				{
					r = new RecordPassword(servizio, username, password);
				}
				catch (Exception e)
				{
					new ErrorAlert(this);
				}
				
				values.put("servizio", r.getServizio());
				values.put("username", r.getCriptUsername());
				values.put("password", r.getCriptPassword());
				
				if(db.insert("rubrica", null, values) < 0)
				{
					new PersonalDialog(this, "ERRORE", "Si e' verificato un errore nel salvataggio dei dati.", "Chiudi");
					finish();
				}
				else
					new PersonalDialog(this, "DATI SALVATI", "I dati inseriti sono stati salvati.", "Chiudi");

				db.close();
			}
		}
	}
}