package com.bys.holidayblessing.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lidroid.xutils.util.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AssetsDBManager {
    private static String databasepath;
    private static AssetsDBManager mInstance;
    private static String tag = "AssetsDatabase";
    private Context context = null;
    private Map<String, SQLiteDatabase> databases = new HashMap<String, SQLiteDatabase>();

    static {
        databasepath = "/data/data/%s/database";
        mInstance = null;
    }

    private AssetsDBManager(Context paramContext) {
        this.context = paramContext;
    }

    public static void closeAllDatabase() {
        Log.i(tag, "closeAllDatabase");
        if (mInstance != null)
            ;
        for (int i = 0; ; ++i) {
            if (i >= mInstance.databases.size()) {
                mInstance.databases.clear();
                return;
            }
            if (mInstance.databases.get(Integer.valueOf(i)) == null)
                continue;
            ((SQLiteDatabase) mInstance.databases.get(Integer.valueOf(i)))
                    .close();
        }
    }

    /**
     * 复制数据库
     *
     * @param paramString1 文件名
     * @param paramString2 地址
     * @return
     */
    private boolean copyAssetsToFilesystem(String paramString1,
                                           String paramString2) {
        Log.i(tag, "Copy " + paramString1 + " to " + paramString2);
        InputStream localInputStream = null;
        FileOutputStream localFileOutputStream2 = null;
        try {
            localInputStream = this.context.getAssets().open(paramString1);
            localFileOutputStream2 = new FileOutputStream(paramString2);
            byte[] arrayOfByte = new byte[1024];
            int count = 0;
            while ((count = localInputStream.read(arrayOfByte)) > 0) {
                localFileOutputStream2.write(arrayOfByte, 0, count);
            }
            localFileOutputStream2.close();
            localInputStream.close();
        } catch (Exception localException2) {
            return false;
        }
        return true;
    }

    private String getDatabaseFile(String paramString) {
        return getDatabaseFilepath() + "/" + paramString;
    }

    private String getDatabaseFilepath() {
        String str = databasepath;
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = this.context.getApplicationInfo().packageName;
        return String.format(str, arrayOfObject);
    }

    public static AssetsDBManager getManager() {
        return mInstance;
    }

    public static void initManager(Context paramContext) {
        if (mInstance != null)
            return;
        mInstance = new AssetsDBManager(paramContext);
    }

    public boolean closeDatabase(String paramString) {
        if (this.databases.get(paramString) != null) {
            ((SQLiteDatabase) this.databases.get(paramString)).close();
            this.databases.remove(paramString);
            return true;
        }
        return false;
    }

    public SQLiteDatabase getDatabase(String paramString) {
        SQLiteDatabase localSQLiteDatabase1;
        Context localContext;
        System.out.println("数据库名称———————————————>" + paramString);
        do {
            localContext = this.context;
            localSQLiteDatabase1 = null;
            if (this.databases.get(paramString) != null) {
                Log.i(tag, String.format("Return a database copy of %s",
                        new Object[]{paramString}));
                localSQLiteDatabase1 = (SQLiteDatabase) this.databases
                        .get(paramString);
                return localSQLiteDatabase1;
            } else {
                Log.i(tag, String.format("Create database %s",
                        new Object[]{paramString}));
                String str1 = getDatabaseFilepath();
                LogUtils.e(str1);
                String str2 = getDatabaseFile(paramString);
                LogUtils.e(str2);
                File localFile1 = new File(str2);
                SharedPreferences localSharedPreferences = this.context
                        .getSharedPreferences(AssetsDBManager.class.toString(),
                                0);
                LogUtils.e(!localSharedPreferences.getBoolean(paramString,
                        false) + "");
                LogUtils.e((!localFile1.exists()) + "");
                if ((!localSharedPreferences.getBoolean(paramString, false))
                        || (!localFile1.exists())) {
                    File localFile2 = new File(str1);
                    if ((!localFile2.exists()) && (!localFile2.mkdirs())) {
                        LogUtils.e("Create \"" + str1 + "\" fail!");
                        return null;
                    }
                    if (!copyAssetsToFilesystem(paramString, str2)) {
                        LogUtils.e(String.format("Copy %s to %s fail!",
                                new Object[]{paramString, str2}));
                        return null;
                    }
                    localSharedPreferences.edit().putBoolean(paramString, true)
                            .commit();
                }
                SQLiteDatabase localSQLiteDatabase2 = SQLiteDatabase
                        .openOrCreateDatabase(str2, null);
                if (localSQLiteDatabase2 != null)
                    this.databases.put(paramString, localSQLiteDatabase2);
                return localSQLiteDatabase2;
            }
        } while (localContext == null);
    }
}