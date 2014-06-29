package bdsir.passwordaddressbook.tools;

import bdsir.passwordaddressbook.ViewAddressBook;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ProximitySensor implements SensorEventListener
{	
	private ViewAddressBook addressBook;
	
	public ProximitySensor(ViewAddressBook addressBook)
	{
		this.addressBook = addressBook;
	}
	
	public void onAccuracyChanged(Sensor sensor, int accuracy) {	}

	public void onSensorChanged(SensorEvent event)
	{
		if(ViewAddressBook.activityForeground)
			addressBook.hidePassword();
	}
	
	public static void sensorON(ViewAddressBook addressBook)
	{
		SensorManager sensorManager = (SensorManager) addressBook.getSystemService(Context.SENSOR_SERVICE);
		Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
		
		sensorManager.registerListener(ViewAddressBook.sensorListener, sensor, SensorManager.SENSOR_DELAY_UI);
	}
	
	public static void sensorOFF(ViewAddressBook addressBook)
	{
		SensorManager sensorManager = (SensorManager) addressBook.getSystemService(Context.SENSOR_SERVICE);
		Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
		
		sensorManager.unregisterListener(ViewAddressBook.sensorListener, sensor);
	}
}
