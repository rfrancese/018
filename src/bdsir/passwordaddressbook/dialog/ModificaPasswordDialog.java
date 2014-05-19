package bdsir.passwordaddressbook.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import bdsir.passwordaddressbook.R;
import bdsir.passwordaddressbook.database.DataBaseHelper;

public class ModificaPasswordDialog
{	
	private Dialog dialog;
	private Activity activity;
	
	public ModificaPasswordDialog(Activity activity)
	{
		this.activity = activity;
		
		dialog = new Dialog(activity);
    	dialog.setTitle("Modifica password sistema");
    	dialog.setContentView(R.layout.dialog_modifica_password);
    	dialog.setCancelable(false);
    	dialog.show();
    	
    	setListener();
	}
	
	private void setListener()
	{
		((Button) dialog.findViewById(R.id.buttAnnula)).setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				dialog.dismiss();
			}
		});
    	
    	((Button) dialog.findViewById(R.id.buttProcedi)).setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				String vecchiaPassword = ((EditText) dialog.findViewById(R.id.editVecchiaPass)).getText().toString();
				String nuovaPassord = ((EditText) dialog.findViewById(R.id.editNuovaPass)).getText().toString();
				String reNuovaPassord = ((EditText) dialog.findViewById(R.id.editReinsPass)).getText().toString();
				
				DataBaseHelper dataBaseHelper = new DataBaseHelper(activity.getApplicationContext());
				SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
				String query = "SELECT * FROM utente WHERE password = ?";
				String[] valueArgs = {vecchiaPassword};
				
				if(vecchiaPassword.isEmpty() || nuovaPassord.isEmpty() || reNuovaPassord.isEmpty())
					new PersonalDialog(activity, "Attenzione", "Inserisci tutti i dati richiesti!", "Indietro");
				else if(db.rawQuery(query, valueArgs).getCount() == 0)
					new PersonalDialog(activity, "Errore", "La vecchia password inserita non e' corretta", "Indietro");
				else if(!nuovaPassord.equals(reNuovaPassord))
					new PersonalDialog(activity, "Errore", "Le password inserite non corrispondono", "Indietro");
				else
				{
					db.close();
					db = dataBaseHelper.getWritableDatabase();
					
					String whereClause = "password = ?";
					String[] whereArgs = {vecchiaPassword};
					
					ContentValues values = new ContentValues();
					values.put("password", nuovaPassord);
					
					if(db.update("utente", values, whereClause, whereArgs) < 0)
					{
						db.close();
						new PersonalDialog(activity, "ERRORE", "Si e' verificato un errore nel caricare i dati nel DataBase.", "Chiudi");
					}
					else
					{
						db.close();
						new PersonalDialog(activity, "DATI SALVATI", "I dati inseriti sono stati salvati nel database.", "Rubrica");
					}
					
					dialog.dismiss();
					activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
				}
			}
		});
	}
}
