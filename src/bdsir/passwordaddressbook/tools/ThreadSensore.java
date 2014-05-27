package bdsir.passwordaddressbook.tools;

import bdsir.passwordaddressbook.ViewAddressBook;

public class ThreadSensore implements Runnable
{	
	private ViewAddressBook addressBook;
	
	public ThreadSensore(ViewAddressBook addressBook)
	{
		this.addressBook = addressBook;
	}
	
	public void run()
	{
		try
		{
			while(true)
			{	
				Thread.sleep(1500);
								
				if(!ViewAddressBook.stateShowPassword)
				{	
					ProximitySensor.sensorOFF(addressBook);
					break;
				}
			}
		}
		catch (InterruptedException e)
		{
			addressBook.finish();
		}
	}

}