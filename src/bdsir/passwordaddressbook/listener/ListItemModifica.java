package bdsir.passwordaddressbook.listener;

import bdsir.passwordaddressbook.ModifyService;
import bdsir.passwordaddressbook.R;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class ListItemModifica implements OnItemClickListener
{
	private Activity activity;
	
	public ListItemModifica(Activity activity)
	{
		this.activity = activity;
	}
	
	public void onItemClick(AdapterView<?> adattatore, View componente, int pos, long id)
	{
		TextView servizio = (TextView) componente.findViewById(R.id.modifyTextService);
		TextView username = (TextView) componente.findViewById(R.id.modifyTextUsername);

		Intent modifyService = new Intent(activity.getApplicationContext(), ModifyService.class);

		modifyService.putExtra("servizio", servizio.getText().toString());
		modifyService.putExtra("username", username.getText().toString());
		
		activity.startActivity(modifyService);
	}

}
