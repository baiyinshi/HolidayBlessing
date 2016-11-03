package com.bys.holidayblessing.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Common {
    public static Boolean checkFileisExist(Context paramContext,
                                           String paramString) {
        if (new File(paramContext.getFilesDir().getAbsolutePath() + "/"
                + paramString).exists())
            return Boolean.valueOf(true);
        return Boolean.valueOf(false);
    }

    public static boolean delFile(String paramString) {
        try {
            File localFile = new File(paramString);
            boolean bool1 = localFile.isFile();
            boolean i = false;
            if (bool1) {
                boolean bool2 = localFile.exists();
                i = bool2;
                if (bool2) {
                    boolean bool3 = localFile.delete();
                    i = bool3;
                }
            }
            return i;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return false;
    }

    @SuppressWarnings("unused")
    public static void delconfig(Context paramContext, String paramString) {
        if (Build.VERSION.SDK_INT > 8)
            ;
        for (int i = 4; ; i = 0) {
            SharedPreferences.Editor localEditor = paramContext
                    .getSharedPreferences(paramString, i).edit();
            localEditor.clear();
            localEditor.commit();
            return;
        }
    }

    public static int dip2px(Context paramContext, float paramFloat) {
        return (int) (0.5F + paramFloat
                * paramContext.getResources().getDisplayMetrics().density);
    }

    public static boolean download(Context paramContext, String paramString1,
                                   String paramString2, boolean paramBoolean) {
        try {
            Log.v("Common.download",
                    "判断SD卡是否存在，并且是否具有读写权限:"
                            + Environment.getExternalStorageState());
            if ((!paramBoolean)
                    && (!Environment.getExternalStorageState()
                    .equals("mounted")))
                return false;
            String str;
            InputStream localInputStream = null;
            FileOutputStream localFileOutputStream = null;
            byte[] arrayOfByte = null;
            if (paramBoolean) {
                str = paramContext.getFilesDir().getAbsolutePath() + "/";
                HttpURLConnection localHttpURLConnection = (HttpURLConnection) new URL(
                        paramString1).openConnection();
                localHttpURLConnection.setConnectTimeout(5000);
                localHttpURLConnection.setReadTimeout(5000);
                localHttpURLConnection.connect();
                localInputStream = localHttpURLConnection.getInputStream();
                File localFile = new File(str);
                if (!localFile.exists())
                    localFile.mkdir();
                localFileOutputStream = new FileOutputStream(new File(str,
                        paramString2));
                arrayOfByte = new byte[1024];
            }
            int i;
            do {
                i = localInputStream.read(arrayOfByte);
                if (i <= 0) {
                    localFileOutputStream.close();
                    localInputStream.close();
                    str = Environment.getExternalStorageDirectory() + "/";
                    return true;
                }
                localFileOutputStream.write(arrayOfByte, 0, i);
            } while (i > 0);
        } catch (MalformedURLException localMalformedURLException) {
            localMalformedURLException.printStackTrace();
            return false;
        } catch (IOException localIOException) {
            localIOException.printStackTrace();
        }
        return false;
    }

    public static String getAppPathAndFileName(Context paramContext,
                                               String paramString) {
        return paramContext.getFilesDir().getAbsolutePath() + "/" + paramString;
    }

    public static String getDate(String paramString) {
        return new SimpleDateFormat(paramString).format(new Date(System
                .currentTimeMillis()));
    }

    public static String getDate(String paramString1, String paramString2) {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
                paramString2);
        try {
            String str = localSimpleDateFormat.format(localSimpleDateFormat
                    .parse(paramString1));
            return str;
        } catch (ParseException localParseException) {
            localParseException.printStackTrace();
        }
        return "";
    }

    public static String getDateadd(String paramString1, int paramInt,
                                    String paramString2) {
        try {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
                    paramString2);
            paramString1 = paramString1.replace(" ", "");
            paramString1 = paramString1.substring(0, 4) + "-"
                    + paramString1.substring(4, 6) + "-"
                    + paramString1.substring(6, 8);
            Date localDate = localSimpleDateFormat.parse(paramString1);
            Calendar localCalendar = Calendar.getInstance();
            localCalendar.setTime(localDate);
            localCalendar.add(5, paramInt);
            String str = localSimpleDateFormat.format(localCalendar.getTime());
            return str;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return null;
    }

    public static long getDaySub(String paramString1, String paramString2) {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd");
        try {
            Date localDate = localSimpleDateFormat.parse(paramString1);
            long l = (localSimpleDateFormat.parse(paramString2).getTime() - localDate
                    .getTime()) / 86400000L;
            return l;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return 0L;
    }

    public static String getJSONvalue(String paramString1, String paramString2) {
        try {
            String str = new JSONObject(paramString1).get(paramString2)
                    .toString();
            return str;
        } catch (JSONException localJSONException) {
            localJSONException.printStackTrace();
        }
        return "";
    }

    public static String getjgdaysname(int paramInt) {
        switch (paramInt) {
            default:
                return "";
            case -2:
                return "前天";
            case -1:
                return "昨天";
            case 0:
                return "今天";
            case 1:
                return "明天";
            case 2:
        }
        return "后天";
    }

    public static void hiddenInputMethod(View paramView) {
        InputMethodManager localInputMethodManager = (InputMethodManager) paramView
                .getContext().getSystemService("input_method");
        if (!localInputMethodManager.isActive())
            return;
        localInputMethodManager.hideSoftInputFromWindow(
                paramView.getApplicationWindowToken(), 0);
    }

    public static boolean isNetworkAvailable(Context paramContext) {
        return isNetworkAvailable(paramContext, false);
    }

    public static boolean isNetworkAvailable(Context paramContext,
                                             boolean paramBoolean) {
        boolean i;
        try {
            NetworkInfo localNetworkInfo = ((ConnectivityManager) paramContext
                    .getSystemService("connectivity")).getActiveNetworkInfo();
            if (localNetworkInfo != null) {
                boolean bool = localNetworkInfo.isConnected();
                if (bool) {
                    i = true;
                    if ((i == false) && (paramBoolean))
                        Toast.makeText(paramContext, "网路不给力哦！", 0).show();
                    return i;
                }
            }
            i = false;
        } catch (Exception localException) {
            localException.printStackTrace();
            i = false;
        }
        return i;
    }

    public static boolean isNumeric(String paramString) {
        int i = paramString.length();
        do
            if (--i < 0)
                return true;
        while (Character.isDigit(paramString.charAt(i)));
        return false;
    }

    public static int px2dip(Context paramContext, float paramFloat) {
        return (int) (0.5F + paramFloat
                / paramContext.getResources().getDisplayMetrics().density);
    }

    @SuppressWarnings("resource")
    public static String readFile(String paramString) {
        String localObject = "";
        try {
            File localFile = new File(paramString);
            if ((!localFile.isFile()) || (!localFile.exists()))
                return (String) localObject;
            InputStreamReader localInputStreamReader = new InputStreamReader(
                    new FileInputStream(localFile), "UTF-8");
            BufferedReader localBufferedReader = new BufferedReader(
                    localInputStreamReader);
            String str1 = localBufferedReader.readLine();
            if (str1 == null) {
                localInputStreamReader.close();
                return localObject;
            }
            String str2 = localObject + str1;
            localObject = str2;
        } catch (Exception localException) {
            System.out.println("读取文件内容操作出错");
            localException.printStackTrace();
        }
        return (String) localObject;
    }

    @SuppressWarnings("unused")
    public static String readconfig(Context paramContext, String paramString) {
        if (Build.VERSION.SDK_INT > 8)
            ;
        for (int i = 4; ; i = 0)
            return paramContext.getSharedPreferences("config", i).getString(
                    paramString, "");
    }

    public static boolean uploadFile(String paramString1, String paramString2,
                                     String paramString3) {
        try {
            HttpURLConnection localHttpURLConnection = (HttpURLConnection) new URL(
                    paramString1).openConnection();
            localHttpURLConnection.setDoInput(true);
            localHttpURLConnection.setDoOutput(true);
            localHttpURLConnection.setUseCaches(false);
            localHttpURLConnection.setRequestMethod("POST");
            localHttpURLConnection.setRequestProperty("Connection",
                    "Keep-Alive");
            localHttpURLConnection.setRequestProperty("Charset", "UTF-8");
            localHttpURLConnection.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + "*****");
            DataOutputStream localDataOutputStream = new DataOutputStream(
                    localHttpURLConnection.getOutputStream());
            localDataOutputStream.writeBytes("--" + "*****" + "\r\n");
            localDataOutputStream
                    .writeBytes("Content-Disposition: form-data; name=\"file1\";filename=\""
                            + paramString3 + "\"" + "\r\n");
            localDataOutputStream.writeBytes("\r\n");
            FileInputStream localFileInputStream = new FileInputStream(
                    paramString2);
            byte[] arrayOfByte = new byte[1024];
            StringBuffer localStringBuffer;
            int j;
            while (true) {
                int i = localFileInputStream.read(arrayOfByte);
                if (i == -1) {
                    localDataOutputStream.writeBytes("\r\n");
                    localDataOutputStream.writeBytes("--" + "*****" + "--"
                            + "\r\n");
                    localFileInputStream.close();
                    localDataOutputStream.flush();
                    InputStream localInputStream = localHttpURLConnection
                            .getInputStream();
                    localStringBuffer = new StringBuffer();
                    j = localInputStream.read();
                    if (j != -1)
                        break;
                    localDataOutputStream.close();
                    return true;
                }
                localDataOutputStream.write(arrayOfByte, 0, i);
            }
            localStringBuffer.append((char) j);
        } catch (Exception localException) {
        }
        return false;
    }

    public static void writeFile(String paramString1, String paramString2) {
        try {
            File localFile = new File(paramString1);
            if (!localFile.exists())
                localFile.createNewFile();
            BufferedWriter localBufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(localFile),
                            "UTF-8"));
            localBufferedWriter.write(paramString2);
            localBufferedWriter.close();
            return;
        } catch (Exception localException) {
            System.out.println("写文件内容操作出错");
            localException.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    public static void writeconfig(Context paramContext, String paramString1,
                                   String paramString2) {
        if (Build.VERSION.SDK_INT > 8)
            ;
        for (int i = 4; ; i = 0) {
            SharedPreferences.Editor localEditor = paramContext
                    .getSharedPreferences("config", i).edit();
            localEditor.putString(paramString1, paramString2);
            localEditor.commit();
            return;
        }
    }
}
