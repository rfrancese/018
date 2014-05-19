package bdsir.passwordaddressbook;

import bdsir.passwordaddressbook.database.DataBaseHelper;
import bdsir.passwordaddressbook.dialog.PersonalDialog;
import bdsir.passwordaddressbook.listener.CloseActivity;
import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class FirstAccessAddressBook extends Activity
{	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_primo_accesso);
		
		setListener();
	}
	
	private void setListener()
	{
		((Button) findViewById(R.id.buttAnnula)).setOnClickListener(new CloseActivity(this));
	}
	
	public void procedi(View view)
	{
		String insPassword = ((EditText) findViewById(R.id.editInsPassword)).getText().toString();
		String reinsPassword = ((EditText) findViewById(R.id.editReinsPassword)).getText().toString();
		String cellulare = ((EditText) findViewById(R.id.editNumCell)).getText().toString();
		
		if(insPassword.isEmpty() || reinsPassword.isEmpty() || cellulare.isEmpty())
		{
			new PersonalDialog(this, "Attenzione", "Inserisci tutti i dati richiesti!", "Indietro");
		}
		else if(insPassword.equals(reinsPassword))
		{
			DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
			SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			
			values.put("_cellulare", cellulare.trim());
			values.put("password", insPassword.trim());
			
			if(db.insert("utente", null, values) < 0)
			{
				db.close();
				new PersonalDialog(this, "ERRORE", "Si e' verificato un errore nel caricare i dati nel DataBase.", "Chiudi");
				finish();
			}
			else
			{
				db.close();
				new PersonalDialog(this, "Password salvata", "Dati salvati con successo ora puoi accedere a Rubrica Password", "Rubrica");
			}
		}
		else
			new PersonalDialog(this, "Attenzione", "Le password inserite non corrispondono.\nReinserisci le password", "Indietro");
		
	}
}
