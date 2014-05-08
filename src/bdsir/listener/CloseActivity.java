package bdsir.listener;

import android.app.Activity;
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
	}
}
