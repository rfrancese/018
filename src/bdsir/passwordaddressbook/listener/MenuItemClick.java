package bdsir.passwordaddressbook.listener;

import bdsir.passwordaddressbook.ViewAddressBook;
import bdsir.passwordaddressbook.tools.MenuItemId;
import android.view.MenuItem;
import android.widget.PopupMenu.OnMenuItemClickListener;

public class MenuItemClick implements OnMenuItemClickListener
{
	private ViewAddressBook rubrica;
	
	public MenuItemClick(ViewAddressBook rubrica)
	{
		this.rubrica = rubrica;
	}
	
	public boolean onMenuItemClick(MenuItem item)
	{
		return MenuItemId.getMenuItemClick(rubrica, item);
	}
}
