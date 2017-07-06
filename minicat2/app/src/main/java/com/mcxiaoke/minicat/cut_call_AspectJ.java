package com.mcxiaoke.minicat;

import android.content.Intent;
import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * Created by hasee on 2017/5/21.
 */

@Aspect

public class cut_call_AspectJ {

    private static final String TAG = "gaoshiqing";

    //@Before("execution(* com.mcxiaoke.minicat.app.UIJump.startActivity(..))")
    @Around("call(* android.app.Activity.startActivity(..))")
    public void onActivityMethodBefore(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        String key = proceedingJoinPoint.getSignature().toString();

        Log.d(TAG, "startActivity Around: " + key);
        Intent intent=((Intent)proceedingJoinPoint.getArgs()[0]);
        Log.d(TAG,intent.toString());
        if (intent.getAction()==null)
            proceedingJoinPoint.proceed();
    }

}
