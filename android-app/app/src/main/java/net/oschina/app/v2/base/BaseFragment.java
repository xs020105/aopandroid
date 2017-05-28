package net.oschina.app.v2.base;

import net.oschina.app.v2.ui.dialog.DialogControl;
import net.oschina.app.v2.ui.dialog.WaitDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.tonlin.osc.happy.R;
/*
* 注者：仝秦玮
*
* 这里是基础碎片放抽象方法
*
* 继承自碎片类
*
* 实现视图和监听点击的方法
*
* */
public class BaseFragment extends Fragment implements View.OnClickListener {
	protected static final int STATE_NONE = 0;
	protected static final int STATE_REFRESH = 1;
	protected static final int STATE_LOADMORE = 2;
	protected int mState = STATE_NONE;
	
	protected void hideWaitDialog() {
		/*
		* 隐藏等待会话的窗口
		*
		* 无输入
		*
		* 无输出
		*
		* */
		FragmentActivity activity = getActivity();
		if (activity instanceof DialogControl) {
			((DialogControl) activity).hideWaitDialog();
		}
	}
/*
* 显示加载中的方法
*
* 无输入
*
* 无输出
* */
	protected WaitDialog showWaitDialog() {
		return showWaitDialog(R.string.loading);
	}

	protected WaitDialog showWaitDialog(int resid) {
		FragmentActivity activity = getActivity();
		if (activity instanceof DialogControl) {
			return ((DialogControl) activity).showWaitDialog(resid);
		}
		return null;
	}

	@Override
	public void onClick(View v) {
	}

	public boolean onBackPressed() {
		return false;
	}
}
