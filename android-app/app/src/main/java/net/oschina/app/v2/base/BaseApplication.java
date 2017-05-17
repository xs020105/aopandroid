package net.oschina.app.v2.base;

import net.oschina.app.v2.utils.TDevice;
import net.oschina.app.v2.utils.TLog;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tonlin.osc.happy.R;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
/*
* 注者仝秦玮
*
* 2017.1.11
*
* 这里是基础应用类
*
* 继承自应用类
*
* */
public class BaseApplication extends Application {
    private static final String KEY_TOAST_MARGIN_BOTTOM_HEIGHT = "key_";
    private static String PREF_NAME = "creativelockerV2.pref";
    static Context _context;
    static Resources _resource;
    private static String lastToast = "";
    private static long lastToastTime;

    private static boolean sIsAtLeastGB;

    static {
        /*
        * 此类的初始化静态块
        * */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            sIsAtLeastGB = true;
        }
    }

    @Override
    public void onCreate() {

        /*
        * 创建方法
        *
        * 调用其父类的同名方法
        *
        * 无输入
        *
        * 无输出
        *
        * */
        super.onCreate();
        _context = getApplicationContext();
        _resource = _context.getResources();
        init();
    }

    protected void init() {
    }

    public static synchronized BaseApplication context() {
        return (BaseApplication) _context;
    }

    public static Resources resources() {
        return _resource;
    }

    public static SharedPreferences getPersistPreferences() {
        return context().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static void apply(Editor editor) {
        if (sIsAtLeastGB) {
            editor.apply();
        } else {
            editor.commit();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static SharedPreferences getPreferences() {
        SharedPreferences pre = context().getSharedPreferences(PREF_NAME,
                Context.MODE_MULTI_PROCESS);
        return pre;
    }

    @Deprecated
    public static void setPersistentObjectSet(String key, String o) {

        /*
        * 这里是设置持久物体设置类
        *
        * 输入关键字符串
        *
        * 无输出
        *
        *
        *
        * */
        SharedPreferences store = getPreferences();
        synchronized (store) {
            SharedPreferences.Editor editor = store.edit();
            if (o == null) {
                editor.remove(key);
            } else {
                Set<String> vals = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    vals = store.getStringSet(key, null);
                } else {
                    String s = store.getString(key, null);
                    if (s != null)
                        vals = new HashSet<>(Arrays.asList(s.split(",")));
                }
                if (vals == null) vals = new HashSet<>();
                vals.add(o);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    editor.putStringSet(key, vals);
                } else {
                    editor.putString(key, join(vals, ","));
                }
            }
            editor.commit();
        }
    }

    @Deprecated
    public static Set<String> getPersistentObjectSet(String key) {

        /*
        * 这里是获取持久物体设置类
        *
        * 输入关键字符串
        *
        * 无输出
        *
        *
        *
        * */
        SharedPreferences store = getPreferences();
        synchronized (store) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                return store.getStringSet(key, null);
            } else {
                String s = store.getString(key, null);
                if (s != null) return new HashSet<>(Arrays.asList(s.split(",")));
                else return null;
            }
        }
    }

    public static String join(Set<String> set, String delim) {

        /*
        * 这里是将一个字符串并入集合的函数
        *
        * 输入集合和字符串
        *
        * 输出字符串
        *
        * */
        StringBuilder sb = new StringBuilder();
        String loopDelim = "";

        for (String s : set) {
            sb.append(loopDelim);
            sb.append(s);

            loopDelim = delim;
        }
        return sb.toString();
    }

    public static Set<String> getStringSet(String key) {
        /*
        * 这里是获取字符设定的方法
        *
        * 输入字符串
        *
        * 返回含有字符串的集合
        *
        * */
        String regularEx = "#";
        SharedPreferences sp = getPreferences();
        String values = sp.getString(key, "");
        String[] strs = values.split(regularEx);
        Set<String> vs = new HashSet<String>();
        for (String str : strs) {
            vs.add(str);
        }
        return vs;
    }

    public static void putStringSet(String key, Set<String> values) {

        /*
        * 这里是将字符压入集合的 方法
        *
        * 输入字符串和集合
        *
        * 无返回
        *
        * */
        String regularEx = "#";
        String str = "";
        SharedPreferences sp = getPreferences();
        if (values != null && values.size() > 0) {
            Iterator<String> itr = values.iterator();
            while (itr.hasNext()) {
                str += itr.next();
                str += regularEx;
            }
            Editor et = sp.edit();
            et.putString(key, str);
            apply(et);
        }
    }

    public static int[] getDisplaySize() {

        /*
        * 这里是获得一个新的返回窗口方法
        *
        * 无输入
        *
        * 返回新
        *
        * 窗口
        * */
        return new int[]{getPreferences().getInt("screen_width", 480),
                getPreferences().getInt("screen_height", 854)};
    }

    public static void saveDisplaySize(Activity activity) {

        /*
        * 这里是保存展示窗口的方法
        *
        * 输入窗口
        *
        * 无输出
        *
        * */
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay()
                .getMetrics(displaymetrics);
        Editor editor = getPreferences().edit();
        editor.putInt("screen_width", displaymetrics.widthPixels);
        editor.putInt("screen_height", displaymetrics.heightPixels);
        editor.putFloat("density", displaymetrics.density);
        editor.commit();
        TLog.log("", "分辨率:" + displaymetrics.widthPixels + "x"
                + displaymetrics.heightPixels + " 密度:" + displaymetrics.density
                + " " + displaymetrics.densityDpi);
    }

    public static String string(int id) {
        return _resource.getString(id);
    }

    public static String string(int id, Object... args) {
        return _resource.getString(id, args);
    }

    public static void showToast(int message) {
        showToast(message, Toast.LENGTH_LONG, 0);
    }

    public static void showToast(String message) {
        showToast(message, Toast.LENGTH_LONG, 0, Gravity.FILL_HORIZONTAL
                | Gravity.TOP);
    }

    public static void showToast(int message, int icon) {
        showToast(message, Toast.LENGTH_LONG, icon);
    }

    public static void showToast(String message, int icon) {
        showToast(message, Toast.LENGTH_LONG, icon, Gravity.FILL_HORIZONTAL
                | Gravity.TOP);
    }

    public static void showToastShort(int message) {
        showToast(message, Toast.LENGTH_SHORT, 0);
    }

    public static void showToastShort(String message) {
        showToast(message, Toast.LENGTH_SHORT, 0, Gravity.FILL_HORIZONTAL
                | Gravity.TOP);
    }

    public static void showToastShort(int message, Object... args) {
        showToast(message, Toast.LENGTH_SHORT, 0, Gravity.FILL_HORIZONTAL
                | Gravity.TOP, args);
    }

    public static void showToast(int message, int duration, int icon) {
        showToast(message, duration, icon, Gravity.FILL_HORIZONTAL
                | Gravity.TOP);
    }

    public static void showToast(int message, int duration, int icon,
                                 int gravity) {
        showToast(context().getString(message), duration, icon, gravity);
    }

    public static void showToast(int message, int duration, int icon,
                                 int gravity, Object... args) {
        showToast(context().getString(message, args), duration, icon, gravity);
    }

    public static void showToast(String message, int duration, int icon,
                                 int gravity) {
        /*
        * 展示土司类消息快
        *
        * 输入消息
        *
        * 持续时间
        *
        * 图标
        *
        * 无返回
        * */
        if (message != null && !message.equalsIgnoreCase("")) {
            long time = System.currentTimeMillis();
            if (!message.equalsIgnoreCase(lastToast)
                    || Math.abs(time - lastToastTime) > 2000) {

                View view = LayoutInflater.from(context()).inflate(
                        R.layout.v2_view_toast, null);
                ((TextView) view.findViewById(R.id.title_tv)).setText(message);
                if (icon != 0) {
                    ((ImageView) view.findViewById(R.id.icon_iv))
                            .setImageResource(icon);
                    ((ImageView) view.findViewById(R.id.icon_iv))
                            .setVisibility(View.VISIBLE);
                }
                Toast toast = new Toast(context());
                toast.setView(view);
                //toast.setGravity(gravity, 0 , TDevice.getActionBarHeight(context()));
                // getToastMarignBottom()
                // toast.setGravity(Gravity.TOP|Gravity.LEFT,0 ,0);
                toast.setDuration(duration);
                toast.show();

                lastToast = message;
                lastToastTime = System.currentTimeMillis();
            }
        }
    }

    public static int getToastMarignBottom() {
        return getPreferences().getInt(KEY_TOAST_MARGIN_BOTTOM_HEIGHT,
                (int) TDevice.dpToPixel(100));
    }

    public static void setToastMarginBottom(int bottom) {
        Editor editor = getPreferences().edit();
        editor.putInt(KEY_TOAST_MARGIN_BOTTOM_HEIGHT, bottom);
        apply(editor);
    }
}
