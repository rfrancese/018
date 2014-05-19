package bdsir.passwordaddressbook.dialog;

import bdsir.passwordaddressbook.listener.*;
import android.app.Activity;
import android.app.AlertDialog;

public class PersonalDialog
{
	private AlertDialog.Builder alert;
	
	public PersonalDialog(Activity activity, String title, String message, String textButton)
	{
		alert = new AlertDialog.Builder(activity);
		alert.setTitle(title);
		alert.setMessage(message);
		alert.setCancelable(false);
		
		if(textButton.equals("Indietro"))
			alert.setPositiveButton(textButton, new BackPersonalDialog());
		else if(textButton.equals("Chiudi"))
			alert.setPositiveButton(textButton, new ClosePersonalDialog(activity));
		else if(textButton.endsWith("Rubrica"))
			alert.setPositiveButton(textButton, new RubricaPersonalDialog(activity));
		
		alert.create();
		alert.show();
	}
	
}
