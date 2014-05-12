package bdsir.passwordaddressbook.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper
{
	private static final String DB_NAME = "rubrica_password";
	private static final int DB_VERSION = 1;
	
	public DataBaseHelper(Context context)
	{
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		String sql = "";
		
		//Creazione della tabella
		sql += "CREATE TABLE rubrica";
		sql += "(";
		sql += "_id INTEGER PRIMARY KEY,";	//attributo id come chiave primaria
		sql += "servizio TEXT NOT NULL,";	//attributo servizio tipo testo e non null
		sql += "username TEXT NOT NULL,";	//attributo username tipo testo e non null
		sql += "password TEXT NOT NULL";	//attributo password tipo testo e non null
		sql += ")";
		
		db.execSQL(sql);					//quary al database
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		
	}

}
