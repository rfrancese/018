package bdsir.passwordaddressbook.database;

public class RecordPassword
{
	private String servizio;
	private String username;
	private String password;
	
	public RecordPassword(String servizio, String username, String password) 
	{
		this.servizio = servizio;
		this.username = username;
		this.password = password;
	}
	
	public String getServizio()
	{
		return servizio;
	}

	public String getUsername()
	{
		return username;
	}
	
	public String getPassword()
	{
		return password;
	}
}
