package bdsir.passwordaddressbook.tools;

import android.content.Intent;
import android.view.MenuItem;
import bdsir.passwordaddressbook.ListModifyPassword;
import bdsir.passwordaddressbook.ListRemoveService;
import bdsir.passwordaddressbook.ModifyPasswordSystem;
import bdsir.passwordaddressbook.R;
import bdsir.passwordaddressbook.ViewAddressBook;
import bdsir.passwordaddressbook.dialog.AccediDialog;
import bdsir.passwordaddressbook.dialog.SendSMSDialog;
import bdsir.passwordaddressbook.json.BackupJson;
import bdsir.passwordaddressbook.json.RipristinoJson;

public class MenuItemId
{
	public static boolean getMenuItemClick(ViewAddressBook rubrica, MenuItem item)
	{
		switch(item.getItemId())
		{
			case R.id.subMenuRecupera:
				new SendSMSDialog(rubrica);
	        	return true;
			case R.id.subMenuBackUpRipristino:
				return true;
			case R.id.subMenuExit:
				rubrica.finish();
				return true;
		}
		
		if(ViewAddressBook.stateShowPassword)
		{
			ViewAddressBook.activityForeground = false;
			
			switch(item.getItemId())
		    {
		        case R.id.subMenuModifica:
		        	rubrica.startActivity(new Intent(rubrica.getApplicationContext(), ListModifyPassword.class));
		        	return true;
		        case R.id.subMenuElimina:
		        	rubrica.startActivity(new Intent(rubrica.getApplicationContext(), ListRemoveService.class));
		        	return true;
		        case R.id.subMenuPassSistema:
		        	rubrica.startActivity(new Intent(rubrica.getApplicationContext(), ModifyPasswordSystem.class));
		        	return true;
		        case R.id.subMenuBackUp:
		        	rubrica.startActivity(new Intent(rubrica.getApplicationContext(), BackupJson.class));
		        	return true;
		        case R.id.subMenuRipristina:
		        	rubrica.startActivity(new Intent(rubrica.getApplicationContext(), RipristinoJson.class));
		        	return true;
		    }
		}
		else
			new AccediDialog(rubrica, "" + item.getItemId(), null);
		
	    return false;
	}
}
