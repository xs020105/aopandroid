package com.mcxiaoke.minicat.app;
import android.app.Activity;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.mcxiaoke.minicat.R;
import android.content.Intent;
import android.view.MotionEvent;
import com.mcxiaoke.minicat.app.UIOptions;
/**
 * Created by hasee on 2017/5/17.
 */

public class UIJump extends UIBaseSupport {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_container);
    }

/*
    public boolean onTouchEvent(MotionEvent event) {

        System.out.println("This is jumping.");
        Intent intent = new Intent();
        PackageManager packageManager = this.getPackageManager();

        //intent = packageManager.getLaunchIntentForPackage("com.tonlin.osc.happy");
        //I/System.out: string:Intent { act=android.intent.action.MAIN cat=[android.intent.category.LAUNCHER] flg=0x10000000 pkg=com.tonlin.osc.happy cmp=com.tonlin.osc.happy/net.oschina.app.v2.activity.SplashActivity }

        //com.mcxiaoke.minicat.app.UIOptions
        //intent = packageManager.getLaunchIntentForPackage("com.mcxiaoke.minicat");
        //intent.setClassName("com.mcxiaoke.minicat", "com.mcxiaoke.minicat.app.UIStart");
        //intent.setClassName("com.tonlin.osc.happy", "net.oschina.app.v2.activity.SplashActivity");
        intent.setClass(UIJump.this,UIHome.class);
        //I/System.out: string:Intent { cmp=com.mcxiaoke.minicat2/com.mcxiaoke.minicat.app.UIHome }

        System.out.println("Package:"+intent.getPackage());
        System.out.println("Action:"+intent.getAction());
        System.out.println("class.package:"+intent.getClass().getPackage());
        System.out.println("class.name:"+intent.getClass().getName());
        System.out.println("datastring:"+intent.getDataString());
        System.out.println("csheme:"+intent.getScheme());
        System.out.println("type:"+intent.getType());
        System.out.println("string:"+intent.toString());
        startActivity(intent);
        return super.onTouchEvent(event);
    }
  */
    public boolean onTouchEvent(MotionEvent event){
        Intent intent = new Intent("net.oschina1.app.v2.activity.MaiActivity");
        startActivity(intent);
        return super.onTouchEvent(event);
    }

//    public void startActivity(Intent intent){
//        super.startActivity(intent);
//    }
}
