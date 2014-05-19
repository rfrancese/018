package bdsir.passwordaddressbook;

import bdsir.passwordaddressbook.database.DataBaseHelper;
import bdsir.passwordaddressbook.dialog.PersonalDialog;
import bdsir.passwordaddressbook.listener.CloseActivity;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ModifyPassword extends Activity
{
	private DataBaseHelper dataBaseHelper;
	private String servizio;
	private String username;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_modifica_password);
		
		setListener();
		setModifyParameter();
	}
	
	private void setListener()
	{
		Button annulla = (Button) findViewById(R.id.buttAnnula);
		annulla.setOnClickListener(new CloseActivity(this));
	}
	
	private void setModifyParameter()
	{
		Intent intent = getIntent();
		servizio = intent.getStringExtra("servizio");
		username = intent.getStringExtra("username");
		
		((TextView) findViewById(R.id.textModifyService)).setText("- " + servizio);
		((TextView) findViewById(R.id.textModifyUsername)).setText("- " + username);
	}
	
	public void procedi(View view)
	{
		String editPaswword = ((EditText) findViewById(R.id.editModifyPassword)).getText().toString().trim();
		
		if(editPaswword.isEmpty())
			new PersonalDialog(this, "ATTENZIONE", "Il campo \"Password\" non puo' essere vuoto." , "Indietro");
		else
		{
			dataBaseHelper = new DataBaseHelper(this);
			SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
		
			String whereClause = "servizio = ? AND username = ?";
			String[] whereArgs = {servizio, username};
			
			ContentValues values = new ContentValues();
			values.put("password", editPaswword);
			
			if(db.update("rubrica", values, whereClause, whereArgs) < 0)
			{
				db.close();
				new PersonalDialog(this, "ERRORE", "Si e' verificato un errore nel caricare i dati nel DataBase.", "Chiudi");
			}
			else
			{
				db.close();
				new PersonalDialog(this, "DATI SALVATI", "I dati inseriti sono stati salvati nel database.", "Rubrica");
			}
		}
	}

}
