package com.bys.holidayblessing.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.bys.holidayblessing.R;
import com.bys.holidayblessing.tools.Common;
import com.bys.holidayblessing.tools.DBHelper;
import com.bys.holidayblessing.tools.DBHelper1;
import com.bys.holidayblessing.tools.Lunar;
import com.bys.holidayblessing.tools.NongLiHelper;
import com.lidroid.xutils.util.LogUtils;

public class WelcomeActivity extends Activity {

    private final static String TAG = "WelcomeActvity";
    private DBHelper db;
    private String updateserverurl;
    private String versionfilename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appstart);
        this.updateserverurl = getString(R.string.updateserverurl);
        this.versionfilename = getString(R.string.versionfilename);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Log.e("TAG", "run_start");
                db = new DBHelper(WelcomeActivity.this);
                try {
                    setJrrl();
                    Log.e("TAG", "setJrrl()");
                    updateDB();
                    Intent localIntent = new Intent(WelcomeActivity.this,
                            MainActivity.class);
                    startActivity(localIntent);
                    ((Activity) WelcomeActivity.this).finish();
                    Log.e("TAG", "run_end");
                    return;
                } catch (Exception localException) {
                    localException.printStackTrace();
                }
            }
        }, 2000L);
        Log.e(TAG, "downversionxml");
        downversionxml();
    }

    @SuppressWarnings("unused")
    private void downversionxml() {
        boolean bool = Common.isNetworkAvailable(this);
        if (Build.VERSION.SDK_INT > 8)
            ;
        for (int i = 4; ; i = 0) {
            SharedPreferences localSharedPreferences = this
                    .getSharedPreferences("config", i);
            String str1 = Common.getDate("yyyy-MM-dd");
            String str2 = localSharedPreferences.getString("downversiondate",
                    Common.getDateadd(str1, -10, "yyyy-MM-dd"));
            Log.v(TAG, "downversiondate:" + str2);
            if ((bool) && (Common.getDaySub(str2, str1) < 7L))
                bool = false;
            if (bool)
                new DownloadVersionThread().start();
            return;
        }
    }

    private void updateDB() {
        Object localObject = "";
        String str2 = null;
        try {
            Cursor localCursor2 = this.db
                    .query("select dbver,dbupdate from dv_kz");
            localCursor2.moveToFirst();
            String str3 = localCursor2.getString(1);
            localObject = str3;
            if (!((String) localObject).equals(""))
                ;
        } catch (Exception localException1) {
            try {
                DBHelper1 localDBHelper1 = new DBHelper1(this);
                Cursor localCursor1 = localDBHelper1
                        .query("select id,sctime from v_dx_list_sc");
                if (!localCursor1.moveToNext()) {
                    localDBHelper1.close();
                    Object[] arrayOfObject = new Object[1];
                    arrayOfObject[0] = getApplicationInfo().packageName;
                    str2 = String.format("/data/data/%s/database/bysmms.db",
                            arrayOfObject);
                    if (!Common.delFile(str2))
                        Log.v(TAG, "del ok (" + str2 + ")");
                    this.db.execsql("update dv_kz set dbupdate='yes'");
                    localException1.printStackTrace();
                    return;
                }
                String str1 = "update dx_list set sc = 1,sctime='"
                        + localCursor1.getString(1) + "' where id="
                        + localCursor1.getString(0);
                this.db.execsql(str1);
            } catch (Exception localException2) {
                localException2.printStackTrace();
                Log.e(TAG, "del error (" + str2 + ")");
            }
        }
    }

    // 把节日已经过去的时间刷新
    private void setJrrl() {
        Cursor localCursor1 = null;
        localCursor1 = this.db
                .query("select id,rl,rlts from v_dx_class where isok=1 and  not rl is null and (jgdays - rlts <-1 or jgdays >360 or rlrq is null)  order by id");
        LogUtils.e("游标大小" + localCursor1.getCount());
        if (null == localCursor1 || localCursor1.getCount() == 0) {
            System.err.println("AAAAAAaa");
            return;
        }
        try {
            while (localCursor1.moveToNext()) {
                String str1 = localCursor1.getString(0); // ID
                LogUtils.e("str1——————" + str1);
                String str2 = localCursor1.getString(1); // 日历
                LogUtils.e("str2——————" + str2);
                int i = localCursor1.getInt(2); // 编号 rlts
                String str3 = "阳历";
                String str4 = null; // 系统当前年份
                String str5 = null; // 农历 MM-dd
                String str13 = null; // 农历表示方式（农历 【马】贰零壹肆 年 十月大十七 甲午年乙亥月癸丑日）
                if (str2.indexOf("农历") == 0) {
                    str3 = "农历";
                    str4 = Common.getDate("yyyy");
                    str5 = str2.replace("农历", "").replace("月", "-")
                            .replace("日", "");
                    str13 = Lunar.getLunar(Common.getDate("yyyy-MM-dd"));
                    if ((str13.length() != 4) || (!Common.isNumeric(str13)))
                        str13 = Common.getDate("yyyy");
                    String str7 = NongLiHelper.getYLrq(str13 + "-" + str5);
                    LogUtils.e(str2
                            + ":"
                            + str7
                            + "#"
                            + Common.getDaySub(Common.getDate("yyyy-MM-dd"),
                            str7));
                    if (Common.getDaySub(Common.getDate("yyyy-MM-dd"), str7)
                            + i >= -1L)
                        ;
                }
                str4 = Common.getDate("yyyy");
                str2 = str2.replace("月", "-").replace("日", "");
                String str6 = String.valueOf(1 + Integer.parseInt(str4)) + "-"
                        + str2;
                System.out.println(str6);
                str13 = Lunar.getLunar(Common.getDate("yyyy-MM-dd"));
                if ((str13.length() != 4) || (!Common.isNumeric(str13)))
                    str13 = Common.getDate("yyyy");
                LogUtils.e("str13_____" + str13);
                LogUtils.e("循环1"
                        + NongLiHelper.getYLrq(String.valueOf(1 + Integer
                        .parseInt(str13)) + "-" + str2));
                LogUtils.e("循环2" + Common.getDate(str6, "yyyy-MM-dd"));
                str5 = str2;
                String a = Common.getDate(str6, "yyyy-MM-dd");
                while (true) {
                    Log.v(TAG, str2 + ":" + a);
                    if (a.length() > 0)
                        ;
                    Log.v(TAG, "rlts=" + i);
                    if (i > 0) {
                        String str9 = Common.getDateadd(a, i, "yyyy-MM-dd");
                        Log.v(TAG, "rlyyyymmdd_2=" + str9);
                        if ((Common.getDaySub(Common.getDate("yyyy-MM-dd"),
                                str9) >= 0L)
                                && (Common.getDaySub(
                                Common.getDate("yyyy-MM-dd"), str9) <= i))
                            a = Common.getDate("yyyy-MM-dd");
                    }
                    String str8 = "update dx_class set rlrq='" + a
                            + "' where id=" + str1;
                    this.db.execsql(str8);
                    if (str2.indexOf("星期") > -1)
                        str3 = "星期";
                    if (!str3.equals("星期"))
                        break;
                    String str10 = "select date('" + str4
                            + "-01-01','start of year','" + str2.split("_")[1]
                            + "','" + str2.split("_")[2] + "','"
                            + str2.split("_")[3] + "')";
                    Cursor localCursor2 = this.db.query(str10);
                    localCursor2.moveToFirst();
                    a = localCursor2.getString(0);
                    if (Common.getDaySub(Common.getDate("yyyy-MM-dd"), a) + i >= -1L)
                        continue;
                    String str11 = String.valueOf(1 + Integer.parseInt(str4));
                    String str12 = "select date('" + str11
                            + "-01-01','start of year','" + str2.split("_")[1]
                            + "','" + str2.split("_")[2] + "','"
                            + str2.split("_")[3] + "')";
                    Cursor localCursor3 = this.db.query(str12);
                    localCursor3.moveToFirst();
                    a = localCursor3.getString(0);
                }
                str6 = str4 + "-" + str5;
                if (Common.getDaySub(Common.getDate("yyyy-MM-dd"), str6) + i >= -1L)
                    continue;
                str6 = String.valueOf(1 + Integer.parseInt(str4)) + "-" + str5;
            }
        } catch (Exception e) {
        } finally {
            localCursor1.close();
        }
    }

    private class DownloadVersionThread extends Thread {

        private DownloadVersionThread() {
        }

        @SuppressWarnings("unused")
        public void run() {
            Log.v(TAG, "DownloadVersionThread.run()." + updateserverurl
                    + versionfilename);
            if (Common.download(WelcomeActivity.this, updateserverurl
                    + versionfilename, versionfilename, true)) {
                if (Build.VERSION.SDK_INT <= 8)
                    return;
            }
            for (int i = 4; ; i = 0) {
                SharedPreferences.Editor localEditor = WelcomeActivity.this
                        .getSharedPreferences("config", i).edit();
                localEditor.putString("downversiondate",
                        Common.getDate("yyyy-MM-dd"));
                localEditor.commit();
                Log.v(TAG, "editor.commit()");
                return;
            }
        }
    }
}
