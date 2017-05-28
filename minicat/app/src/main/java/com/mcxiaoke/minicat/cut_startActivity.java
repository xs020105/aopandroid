package com.mcxiaoke.minicat;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * Created by hasee on 2017/5/24.
 */
@Aspect
public class cut_startActivity {
    private static final String TAG = "cut_startActivity";

    @Around("execution(* com.mcxiaoke.minicat.app.UIJump.onTousdasdchEvent(MotionEvent))")


    public boolean onActivityMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        String key = proceedingJoinPoint.getSignature().toString();

        Log.d(TAG, "Before Jump: " + key);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!This is before jump.");

        //proceedingJoinPoint.proceed();

        Log.d(TAG, "After Jump: " + key);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!This is after jump.");
        return true;
    }
}
