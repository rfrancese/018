package bdsir.passwordaddressbook.listener;

import bdsir.passwordaddressbook.ViewAddressBook;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class ProximitySensor implements SensorEventListener
{
	private ViewAddressBook addressBook;
	
	public ProximitySensor(ViewAddressBook addressBook)
	{
		this.addressBook = addressBook;
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy)
	{
		
	}

	public void onSensorChanged(SensorEvent event)
	{
		addressBook.hidePassword();
	}

}
