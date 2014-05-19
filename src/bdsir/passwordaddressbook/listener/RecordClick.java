package bdsir.passwordaddressbook.listener;

import bdsir.passwordaddressbook.R;
import bdsir.passwordaddressbook.ViewAddressBook;
import bdsir.passwordaddressbook.dialog.PersonalDialog;
import bdsir.passwordaddressbook.tools.ModifyRecord;
import bdsir.passwordaddressbook.tools.RemoveRecord;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupMenu;

public class RecordClick implements OnItemClickListener
{
	private ViewAddressBook rubrica;
	
	public RecordClick(ViewAddressBook rubrica)
	{
		this.rubrica = rubrica;
	}
	
	public void onItemClick(AdapterView<?> adapter, View view, int pos, long id)
	{
		final View itemView = view;
		PopupMenu popup = new PopupMenu(rubrica, view);
	    MenuInflater inflater = popup.getMenuInflater(); 
	    inflater.inflate(R.menu.popup_menu, popup.getMenu());
	    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
		{
			public boolean onMenuItemClick(MenuItem item)
			{
				if(ViewAddressBook.stateShowPassword)
				{
					switch (item.getItemId())
					{
						case R.id.popupModifica:
							ModifyRecord.modifyRecord(rubrica, itemView, R.id.textDbServizio, R.id.textDbUsername);
							return true;
							
						case R.id.popupElimna:
							RemoveRecord.removeRecord(rubrica, itemView, R.id.textDbServizio, R.id.textDbUsername);
							return true;
					}
				}
				else
					new PersonalDialog(rubrica, "Attenzione", "Devi prima effettuare l'accesso al sistema!", "Indietro");
				
				return false;
			}
		});
	    popup.show();
	}

}
