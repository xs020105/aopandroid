package net.oschina.app.v2.base;

import android.support.v4.app.FragmentManager;

import com.github.ksoichiro.android.observablescrollview.CacheFragmentStatePagerAdapter;

/**
 * Created by Sim on 2015/3/7.
 */

/*注者：仝秦玮
*
* 2017.1.11
*
* 抽象
*
* 基础视图页面配置器
*
* */
public abstract class BaseViewPagerAdapter extends CacheFragmentStatePagerAdapter {

    public BaseViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    /*
    * 构造类
    *
    * 仅仅是调用了父类的构造函数
    *
    * 没输入
    *
    * 没输出
    * */
}
