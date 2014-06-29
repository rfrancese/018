package bdsir.passwordaddressbook.database;

import bdsir.passwordaddressbook.tools.CriptPassword;

public class RecordPassword
{
	private String servizio;
	private String username;
	private String password;
	
	public RecordPassword()
	{
		servizio = "";
		username = "";
		password = "";
	}
	
	public RecordPassword(String servizio, String username, String password) throws Exception 
	{
		this.servizio = servizio;
		this.username = CriptPassword.encrypt(username);
		this.password = CriptPassword.encrypt(password);;
	}
	
	public void setServizio(String servizio)
	{
		this.servizio = servizio;
	}
	
	public void setCriptPassword(String password)
	{
		this.password = password;
	}
	
	public void setCriptUsername(String username)
	{
		this.username = username;
	}
	
	public String getServizio()
	{
		return servizio;
	}
	
	public String getCriptUsername()
	{
		return username;
	}
	
	public String getCriptPassword()
	{
		return password;
	}

	public String getDecriptUsername() throws Exception
	{
		return CriptPassword.decrypt(username);
	}
	
	public String getDecriptPassword() throws Exception
	{
		return CriptPassword.decrypt(password);
	}
}
