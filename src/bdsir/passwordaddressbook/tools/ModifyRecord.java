package bdsir.passwordaddressbook.tools;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import bdsir.passwordaddressbook.ModifyPassword;
import bdsir.passwordaddressbook.ViewAddressBook;

public class ModifyRecord
{
	public static void modifyRecord(Activity activity, View view, int idService, int idUsername)
	{
		Intent modifyPassword = new Intent(activity.getApplicationContext(), ModifyPassword.class);

		modifyPassword.putExtra("servizio", ((TextView) view.findViewById(idService)).getText().toString());
		modifyPassword.putExtra("username", ((TextView) view.findViewById(idUsername)).getText().toString());
		
		activity.startActivity(modifyPassword);
		
		if(!activity.getClass().getName().equals(ViewAddressBook.class.getName()))
			activity.finish();
	}
}
