package bdsir.passwordaddressbook.tools;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import bdsir.passwordaddressbook.dialog.RemoveDialog;

public class RemoveRecord
{
	public static void removeRecord(Activity activity, View view, int idService, int idUsername)
	{
		String servizio = ((TextView) view.findViewById(idService)).getText().toString();
		String username = ((TextView) view.findViewById(idUsername)).getText().toString();
		
		new RemoveDialog(activity, servizio, username);
	}
}
