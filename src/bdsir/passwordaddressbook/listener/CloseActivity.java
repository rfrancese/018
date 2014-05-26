package bdsir.passwordaddressbook.listener;

import bdsir.passwordaddressbook.ListModifyPassword;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class CloseActivity implements OnClickListener
{
	private Activity activity;
	
	public CloseActivity(Activity activity)
	{
		this.activity = activity;
	}

	public void onClick(View v)
	{
		activity.finish();
		
		if(ListModifyPassword.activityListModificyPassword)
			activity.startActivity(new Intent(activity.getApplicationContext(), ListModifyPassword.class));
	}
}