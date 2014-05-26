package bdsir.passwordaddressbook.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import bdsir.passwordaddressbook.R;
import bdsir.passwordaddressbook.database.DataBaseHelper;

public class SendSMSDialog
{
	private Dialog dialog;
	
	public SendSMSDialog(Activity activity)
	{
		DataBaseHelper dataBaseHelper = new DataBaseHelper(activity);
		SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
		String query = "SELECT * FROM utente";
		
		Cursor cursor = db.rawQuery(query, null);
		cursor.moveToNext();
		String number = cursor.getString(0);
		String password = cursor.getString(1);
		int count = cursor.getCount();
		db.close();
		
		if(count == 0)
			new PersonalDialog(activity, "Errore", "Si e' verificato un errore nel database", "Chiudi");
		else
		{
			dialog = new Dialog(activity);
	    	dialog.setTitle("Recupero password");
	    	dialog.setContentView(R.layout.dialog_recupero_password);
	    	
			SmsManager manager = SmsManager.getDefault();
			manager.sendTextMessage("" + number, null, "RUBRICA PASSWORD: la password da lei richiesta e': '" + password + "'.", null, null);
			
			TextView textView = (TextView) dialog.findViewById(R.id.textDescriptionRecupero);
			String txt = textView.getText().toString();
			txt += " " + number;
			textView.setText(txt);
			
			
	    	dialog.setCancelable(false);
	    	dialog.show();
	    	
	    	setListener();
		}
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
	}
}
