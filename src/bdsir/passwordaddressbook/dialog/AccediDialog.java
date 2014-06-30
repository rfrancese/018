package bdsir.passwordaddressbook.dialog;

import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import bdsir.passwordaddressbook.AddService;
import bdsir.passwordaddressbook.ListModifyPassword;
import bdsir.passwordaddressbook.ListRemoveService;
import bdsir.passwordaddressbook.ModifyPasswordSystem;
import bdsir.passwordaddressbook.R;
import bdsir.passwordaddressbook.ViewAddressBook;
import bdsir.passwordaddressbook.database.DataBaseHelper;
import bdsir.passwordaddressbook.json.BackupJson;
import bdsir.passwordaddressbook.json.RipristinoJson;
import bdsir.passwordaddressbook.tools.CriptPassword;
import bdsir.passwordaddressbook.tools.ModifyRecord;
import bdsir.passwordaddressbook.tools.ProximitySensor;
import bdsir.passwordaddressbook.tools.RemoveRecord;
import bdsir.passwordaddressbook.tools.ThreadSensore;

public class AccediDialog
{	
	private Dialog dialog;
	private ViewAddressBook rubrica;
	private DataBaseHelper dataBaseHelper;
	private String type;
	private View view;
		
	public AccediDialog(ViewAddressBook rubrica, String type, View view)
	{
		this.view = view;
		this.type = type;
		this.rubrica = rubrica;
		dataBaseHelper = new DataBaseHelper(rubrica.getApplicationContext());
		
		dialog = new Dialog(rubrica);
    	dialog.setTitle("Accedi al Sistema");
    	dialog.setContentView(R.layout.dialog_accedi);
    	dialog.setCancelable(false);
    	dialog.show();
    	
    	setListener();
	}
	
	private void setListener()
	{
		//Listener del button annulla
		((Button) dialog.findViewById(R.id.buttAnnula)).setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				dialog.dismiss();
				rubrica.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
				
				if(!ViewAddressBook.stateShowPassword)
					ProximitySensor.sensorOFF(rubrica);
			}
		});
    	
		//Listener del button procedi
    	((Button) dialog.findViewById(R.id.buttProcedi)).setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				try
				{
					String password = CriptPassword.encrypt(((EditText) dialog.findViewById(R.id.editAccedi)).getText().toString());
				
					SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
					String query = "SELECT * FROM utente WHERE password = ?";
					String[] whereArgs = {password};
					
					if(db.rawQuery(query, whereArgs).getCount() > 0)
					{	
						if(type != null)
						{	
							if(type.equals("addService"))
								rubrica.startActivity(new Intent(rubrica.getApplicationContext(), AddService.class));
							else if(type.equals("" + R.id.popupModifica))
								ModifyRecord.modifyRecord(rubrica, view, R.id.textDbServizio, R.id.textDbUsername);
							else if(type.equals("" + R.id.popupElimna))
								RemoveRecord.removeRecord(rubrica, view, R.id.textDbServizio, R.id.textDbUsername);
							else if(type.equals("" + R.id.subMenuModifica))
								rubrica.startActivity(new Intent(rubrica.getApplicationContext(), ListModifyPassword.class));
							else if(type.equals("" + R.id.subMenuElimina))
								rubrica.startActivity(new Intent(rubrica.getApplicationContext(), ListRemoveService.class));
							else if(type.equals("" + R.id.subMenuPassSistema))
								rubrica.startActivity(new Intent(rubrica.getApplicationContext(), ModifyPasswordSystem.class));
							else if(type.equals("" + R.id.subMenuBackUp))
								rubrica.startActivity(new Intent(rubrica.getApplicationContext(), BackupJson.class));
							else if(type.equals("" + R.id.subMenuRipristina))
								new WarningBackup(rubrica);
						}
						else
						{
							rubrica.showPassword();
							new Thread(new ThreadSensore(rubrica)).start();
						}
						
						dialog.dismiss();
					}
					else
						new PersonalDialog(rubrica, "Errore", "La password inserita non e' corretta", "Indietro");
					
					db.close();
					rubrica.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
				}
				catch(Exception e)
				{
					new ErrorAlert(rubrica);
				}
			}
		});
	}
}
