package bdsir.passwordaddressbook;

import bdsir.passwordaddressbook.database.DataBaseHelper;
import bdsir.passwordaddressbook.dialog.PersonalDialog;
import bdsir.passwordaddressbook.listener.CloseActivity;
import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddService extends Activity
{
	private Button annulla;
	private DataBaseHelper dataBase;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_aggiungi_servizio);
		dataBase = new DataBaseHelper(this);
		
		setPersonalFont();
		setListener();
	}
	
	private void setPersonalFont()
	{
		Typeface nothingYouCouldSay = Typeface.createFromAsset(getAssets(), "fonts/NothingYouCouldSay.ttf");
		
		TextView serviceType = (TextView) findViewById(R.id.serviceType);
		TextView esServiceType = (TextView) findViewById(R.id.esServiceType);
		
		TextView username = (TextView) findViewById(R.id.username);
		TextView esUsername = (TextView) findViewById(R.id.esUsername);
		
		TextView servicePassword = (TextView) findViewById(R.id.servicePassword);
		TextView esServicePassword = (TextView) findViewById(R.id.esServicePassword);

		annulla = (Button) findViewById(R.id.buttAnnula);
		Button procedi = (Button) findViewById(R.id.buttProcedi);
		
		serviceType.setTypeface(nothingYouCouldSay);
		esServiceType.setTypeface(nothingYouCouldSay);
		username.setTypeface(nothingYouCouldSay);
		esUsername.setTypeface(nothingYouCouldSay);
		servicePassword.setTypeface(nothingYouCouldSay);
		esServicePassword.setTypeface(nothingYouCouldSay);
		
		annulla.setTypeface(nothingYouCouldSay);
		procedi.setTypeface(nothingYouCouldSay);
	}
	
	private void setListener()
	{
		annulla.setOnClickListener(new CloseActivity(this));
	}
	
	public void procedi(View view)
	{
		String servizio = ((EditText) findViewById(R.id.editServiceType)).getText().toString();
		String username = ((EditText) findViewById(R.id.editUsername)).getText().toString();
		String password = ((EditText) findViewById(R.id.editPassword)).getText().toString();
		
		if(servizio.isEmpty())
		{
			new PersonalDialog(this, "ATTENZIONE", "Il campo \"Tipo Servio\" e' vuoto!\nInserisci un servizio.", "Indietro");
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
			SQLiteDatabase db = dataBase.getWritableDatabase();
			ContentValues values = new ContentValues();
			
			values.put("servizio", servizio);
			values.put("username", username);
			values.put("password", password);
			
			if(db.insert("rubrica", null, values) < 0)
			{
				db.close();
				new PersonalDialog(this, "ERRORE", "Si e' verificato un errore nel caricare i dati nel DataBase.", "Chiudi");
			}
			else
			{
				db.close();
				new PersonalDialog(this, "DATI SALVATI", "I dati inseriti sono stati salvati nel database.", "Chiudi");
			}
		}
	}
}