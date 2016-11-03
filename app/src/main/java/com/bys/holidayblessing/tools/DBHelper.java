package com.bys.holidayblessing.tools;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lidroid.xutils.util.LogUtils;

public class DBHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "bysmms2.db";
	public static final int DATABASE_VERSION = 3;
	public static final String TBL_NAME = "dx_list";
	private SQLiteDatabase db;

	public DBHelper(Context paramContext) {
		super(paramContext, "bysmms2.db", null, 3);
		AssetsDBManager.initManager(paramContext);
		this.db = AssetsDBManager.getManager().getDatabase("bysmms2.db");
		LogUtils.e("实例化数据库"+db);
	}

	public void close() {
		if (this.db == null)
			return;
		this.db.close();
	}

	public void del(int paramInt) {
		SQLiteDatabase localSQLiteDatabase = this.db;
		String[] arrayOfString = new String[1];
		arrayOfString[0] = String.valueOf(paramInt);
		localSQLiteDatabase.delete("dx_list", "_id=?", arrayOfString);
	}

	public void execsql(String paramString) {
		this.db.execSQL(paramString);
	}

	public void onCreate(SQLiteDatabase paramSQLiteDatabase) {
	}

	public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1,
			int paramInt2) {
	}

	public Cursor query(String paramString) {
		return this.db.rawQuery(paramString, null);
	}

	public Cursor query(String paramString1, String paramString2,
			String paramString3) {
		return this.db.query(paramString1, paramString2.split(","), null, null,
				null, null, paramString3);
	}
}