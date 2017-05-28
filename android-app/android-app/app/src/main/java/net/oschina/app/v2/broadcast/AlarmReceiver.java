package net.oschina.app.v2.broadcast;

import net.oschina.app.v2.service.NoticeUtils;
import net.oschina.app.v2.utils.TLog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
/*
* 注者 仝秦玮
*
* 2016.12.27
*
* 定义警告接受类
*
* 继承自广播接收类
*
*
* */
public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		TLog.log("onReceive ->收到定时获取消息");
		NoticeUtils.requestNotice(context);
	}
}
