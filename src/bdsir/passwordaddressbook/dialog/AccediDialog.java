package bdsir.passwordaddressbook.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import bdsir.passwordaddressbook.R;
import bdsir.passwordaddressbook.ViewAddressBook;
import bdsir.passwordaddressbook.database.DataBaseHelper;

public class AccediDialog
{	
	private Dialog dialog;
	private Activity activity;
	private DataBaseHelper dataBaseHelper;
		
	public AccediDialog(Activity activity)
	{
		this.activity = activity;
		dataBaseHelper = new DataBaseHelper(activity.getApplicationContext());
		
		dialog = new Dialog(activity);
    	dialog.setTitle("Accedi al Sistema");
    	dialog.setContentView(R.layout.dialog_accedi);
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
				activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			}
		});
    	
    	((Button) dialog.findViewById(R.id.buttProcedi)).setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				String password = ((EditText) dialog.findViewById(R.id.editAccedi)).getText().toString();
				
				SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
				String query = "SELECT * FROM utente WHERE password = ?";
				String[] whereArgs = {password};
				
				if(db.rawQuery(query, whereArgs).getCount() > 0)
				{
					dialog.dismiss();
					((ViewAddressBook) activity).showPassword();
				}
				else
				{
					new PersonalDialog(activity, "Errore", "La password inserita non e' corretta", "Indietro");
				}
				
				db.close();
				activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
			}
		});
	}
}
