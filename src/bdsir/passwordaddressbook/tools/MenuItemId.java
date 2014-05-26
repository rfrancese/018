package bdsir.passwordaddressbook.tools;

import android.content.Intent;
import android.view.MenuItem;
import bdsir.passwordaddressbook.ListModifyPassword;
import bdsir.passwordaddressbook.ListRemoveService;
import bdsir.passwordaddressbook.R;
import bdsir.passwordaddressbook.ViewAddressBook;
import bdsir.passwordaddressbook.dialog.AccediDialog;
import bdsir.passwordaddressbook.dialog.ModificaPasswordDialog;
import bdsir.passwordaddressbook.dialog.SendSMSDialog;

public class MenuItemId
{
	public static boolean getMenuItemClick(ViewAddressBook rubrica, MenuItem item)
	{
		switch(item.getItemId())
		{
			case R.id.subMenuRecupera:
				new SendSMSDialog(rubrica);
	        	return true;
			case R.id.subMenuExit:
				rubrica.finish();
				return true;
		}
		
		if(ViewAddressBook.stateShowPassword)
		{
			switch(item.getItemId())
		    {
		        case R.id.subMenuModifica:
		        	rubrica.startActivity(new Intent(rubrica.getApplicationContext(), ListModifyPassword.class));
		        	return true;
		        case R.id.subMenuElimina:
		        	rubrica.startActivity(new Intent(rubrica.getApplicationContext(), ListRemoveService.class));
		        	return true;
		        case R.id.subMenuPassSistema:
		        	new ModificaPasswordDialog(rubrica);
		        	return true;
		        case R.id.subMenuBackUp:
		        	return true;
		    }
		}
		else
			new AccediDialog(rubrica, "" + item.getItemId(), null);
		
	    return false;
	}
}
