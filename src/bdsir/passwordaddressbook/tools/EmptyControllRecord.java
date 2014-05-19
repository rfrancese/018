package bdsir.passwordaddressbook.tools;

import android.view.View;
import bdsir.passwordaddressbook.R;

public class EmptyControllRecord
{
	public static void controllRecord(boolean flag, View view, int drawable)
	{
		if(!flag)
			view.setBackgroundResource(drawable);
		else
			view.setBackgroundResource(R.color.trasparente);
	}
}
