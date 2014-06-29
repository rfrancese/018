package bdsir.passwordaddressbook.dialog;

import bdsir.passwordaddressbook.R;
import bdsir.passwordaddressbook.ViewAddressBook;
import bdsir.passwordaddressbook.listener.DeleteRecordDataBase;
import bdsir.passwordaddressbook.tools.CriptPassword;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class RemoveDialog extends Activity
{
	private String servizio;
	private String username;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_elimina_servizio);
		
		setFinishOnTouchOutside(false);
		
		Intent intent = getIntent();
		servizio = intent.getStringExtra("servizio");
		try
		{
			username = CriptPassword.encrypt(intent.getStringExtra("username"));
		}
		catch (Exception e)
		{
			new ErrorAlert(this);
		}
		
		((TextView) findViewById(R.id.textDialogRemoveServizio)).setText("Sei sicuro di voler eliminare l'account '" + servizio + "' dalla rubrica?");
		procedi();
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
	
	private void procedi()
	{
		((Button) findViewById(R.id.buttProcediRemoveDialog)).setOnClickListener(new DeleteRecordDataBase(this, servizio, username));
	}
	
}
