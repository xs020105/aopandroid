package net.oschina.app.v2.base;

import net.oschina.app.v2.AppContext;
import net.oschina.app.v2.ui.dialog.CommonToast;
import net.oschina.app.v2.ui.dialog.DialogControl;
import net.oschina.app.v2.ui.dialog.DialogHelper;
import net.oschina.app.v2.ui.dialog.WaitDialog;
import net.oschina.app.v2.utils.SystemBarTintManager;
import net.oschina.app.v2.utils.TDevice;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
//import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.LayoutParams;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.tonlin.osc.happy.R;
/*
* 作者：仝秦玮
*
* 2017.1.11
*
* 这是一个抽象类
*
* 描述基础的活动页面
*
* 继承自活动类
*
* 实现了数个接口
*
* */
public abstract class BaseActivity extends AppCompatActivity implements
        DialogControl, VisibilityControl, OnClickListener {
    public static final String INTENT_ACTION_EXIT_APP = "INTENT_ACTION_EXIT_APP";
    /*
    * 常量，提示语
    * */
    private boolean _isVisible;
    /*
    * 是否可视化
    * */
    private WaitDialog _waitDialog;
    /*
    * 等待回话实体
    * */

    protected LayoutInflater mInflater;
    private Toolbar mActionBar;
    private TextView mTvActionTitle;

    private BroadcastReceiver mExistReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };


    protected int getActionBarSize() {

        /*
        * 此处为获取活动窗口大小的方法
        *
        * 返回整数
        * */
        TypedValue typedValue = new TypedValue();
        int[] textSizeAttr = new int[]{R.attr.actionBarSize};
        int indexOfAttrTextSize = 0;
        TypedArray a = obtainStyledAttributes(typedValue.data, textSizeAttr);
        int actionBarSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1);
        a.recycle();
        return actionBarSize;
    }

    @TargetApi(19)
    protected void setTranslucentStatus(boolean on) {
        /*
        * 这里为设置活动为半透明的方法
        *
        * 输入控制语句
        *
        * 无返回
        * */
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
        * 创建一个数图
        *
        * 无输入
        *
        * 无返回
        * */
        super.onCreate(savedInstanceState);
        AppContext.saveDisplaySize(this);

        if (!hasActionBar()) {
            supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        onBeforeSetContentLayout();
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        mActionBar = (Toolbar) findViewById(R.id.actionBar);//getSupportActionBar();
        mInflater = getLayoutInflater();
        if (hasActionBar()) {
            initActionBar(mActionBar);
        }
        init(savedInstanceState);

        IntentFilter filter = new IntentFilter(INTENT_ACTION_EXIT_APP);
        registerReceiver(mExistReceiver, filter);
    }

    @Override
    protected void onDestroy() {

        /*
        * 这里是销毁一个视图
        *
        * 无输入
        *
        * 无返回
        * */
        unregisterReceiver(mExistReceiver);
        mExistReceiver = null;
        super.onDestroy();
    }

    protected void onBeforeSetContentLayout() {
    }

    protected boolean hasActionBar() {
        return true;
    }

    protected int getLayoutId() {
        return 0;
    }

    protected View inflateView(int resId) {
        return mInflater.inflate(resId, null);
    }

    protected int getActionBarTitle() {
        return R.string.app_name;
    }

    protected boolean hasBackButton() {
        return false;
    }

    protected int getActionBarCustomView() {
        return 0;
    }

    protected void init(Bundle savedInstanceState) {
        initStatusBar();
    }

    protected void initStatusBar() {// buG
//        View root = findViewById(R.id.root);
//        root = root == null ? findViewById(R.id.activity_root) : root;
//        if (root != null && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP
//                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            setTranslucentStatus(true);
//
//            SystemBarTintManager tintManager = new SystemBarTintManager(this);
//            tintManager.setStatusBarTintEnabled(true);
//            tintManager.setStatusBarTintResource(R.color.theme_primary_dark);
//
//            SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
//            root.setPadding(0, config.getPixelInsetTop(false), 0, config.getPixelInsetBottom());
//        }
    }

    protected void initActionBar(Toolbar actionBar) {
        /*
        * 这里是初始化活动条的方法
        *
        * 无输入
        *
        * 无返回
        *
        * */
        if (actionBar == null)
            return;
        setSupportActionBar(actionBar);
        if (hasBackButton()) {
            //actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

            int layoutRes = getActionBarCustomView();
            if (layoutRes != 0) {
                View view = inflateView(layoutRes);
                Toolbar.LayoutParams params = new Toolbar.LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT);
                actionBar.addView(view, params);
                mTvActionTitle = (TextView) view.findViewById(R.id.tv_actionbar_title);
                int titleRes = getActionBarTitle();
                if (titleRes != 0 && mTvActionTitle != null) {
                    mTvActionTitle.setText(titleRes);
                }
            }

            int titleRes = getActionBarTitle();


            if (titleRes != 0) {
                actionBar.setTitle(titleRes);
            }

            actionBar.setNavigationIcon(R.drawable.actionbar_back_icon_normal);
            actionBar.setNavigationOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });


            actionBar.setPadding(0, 0, 0, 0);
        } else {
            int titleRes = getActionBarTitle();
            if (titleRes != 0) {
                actionBar.setTitle(titleRes);
            }
            //actionBar.setLogo(0);
            actionBar.setPadding((int) TDevice.dpToPixel(16), 0, 0, 0);
        }
        /*actionBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return onOptionsItemSelected(menuItem);
            }
        });*/
        startSupportActionMode(new ActionModeCallback());
    }

    public void setActionBarTitle(int resId) {
        /*
        * 这里是设置活动标题的方法
        *
        * 输入标号
        *
        * 无返回
        *
        * */
        if (resId != 0) {
            setActionBarTitle(getString(resId));
        }
    }

    public void setActionBarTitle(String title) {
                /*
        * 这里是设置活动标题的方法
        *
        * 输入标题
        *
        * 无返回
        *
        * */
        if (hasActionBar()) {
            if (mTvActionTitle != null) {
                mTvActionTitle.setText(title);
            }
            if (mActionBar != null) {
                mActionBar.setTitle(title);
            }
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        /*
        * 这里是重写是否挑选项目方法
        *
        * 输入项目
        *
        * 返回是否挑选
        *
        * */
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {

        /*
        * 这里是重写暂停方法
        *
        * 无输入
        *
        * 无返回
        * */
        _isVisible = false;
        hideWaitDialog();
        super.onPause();
    }

    @Override
    protected void onResume() {

        /*
        * 这里是重写继续方法
        *
        * 无输入
        *
        * 无返回
        * */
        _isVisible = true;
        super.onResume();
    }

    public void showToast(int msgResid, int icon, int gravity) {
        showToast(getString(msgResid), icon, gravity);
    }

    public void showToast(String message, int icon, int gravity) {
        CommonToast toast = new CommonToast(this);
        toast.setMessage(message);
        toast.setMessageIc(icon);
        toast.setLayoutGravity(gravity);
        toast.show();
    }

    @Override
    public WaitDialog showWaitDialog() {

        /*
        * 这里是显示加载中的方法
        *
        * 无输入
        *
        * 无返回
        * */
        return showWaitDialog(R.string.loading);
    }

    @Override
    public WaitDialog showWaitDialog(int resid) {
        return showWaitDialog(getString(resid));
    }

    @Override
    public WaitDialog showWaitDialog(String message) {
        /*
        * 显示等待回话
        *
        * 加载中
        *
        * 输入msg
        *
        * 输入等待回话
        *
        * */
        if (_isVisible) {
            if (_waitDialog == null) {
                _waitDialog = DialogHelper.getWaitDialog(this, message);
            }
            if (_waitDialog != null) {
                _waitDialog.setMessage(message);
                _waitDialog.show();
            }
            return _waitDialog;
        }
        return null;
    }

    @Override
    public void hideWaitDialog() {
        /*
        * 隐藏等待回话
        *
        * 无输入
        *
        * 无输出
        * */
        if (_isVisible && _waitDialog != null) {
            try {
                _waitDialog.dismiss();
                _waitDialog = null;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public boolean isVisible() {
        /*
        * 返回是否可视
        * */
        return _isVisible;
    }

    protected int getScreenHeight() {
        return findViewById(android.R.id.content).getHeight();
    }

    class ActionModeCallback implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            //if (mActionBar != null)
            //   mActionBar.setVisibility(View.GONE);
            return false;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            //if (mActionBar != null)
            //   mActionBar.setVisibility(View.VISIBLE);
        }
    }
}