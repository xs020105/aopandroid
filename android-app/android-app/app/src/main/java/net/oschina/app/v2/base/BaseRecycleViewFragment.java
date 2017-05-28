package net.oschina.app.v2.base;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.MySwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.tonlin.osc.happy.R;

import net.oschina.app.v2.AppContext;
import net.oschina.app.v2.cache.v2.CacheManager;
import net.oschina.app.v2.model.ListEntity;
import net.oschina.app.v2.model.NewsList;
import net.oschina.app.v2.ui.decorator.DividerItemDecoration;
import net.oschina.app.v2.ui.empty.EmptyLayout;
import net.oschina.app.v2.ui.widget.FixedRecyclerView;
import net.oschina.app.v2.utils.TDevice;
import net.oschina.app.v2.utils.TLog;
import net.oschina.app.v2.utils.UIHelper;
import net.oschina.app.v2.utils.WeakAsyncTask;

import org.apache.http.Header;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.List;
/*
* 注者 仝秦玮
*
* 2017.1.11
*
*
* */

/*
* 这里是基础的回收视图抽象类
*
* 继承自作者写的另一个抽象类
*
*
* */
public abstract class BaseRecycleViewFragment extends BaseTabFragment implements
        RecycleBaseAdapter.OnItemClickListener, RecycleBaseAdapter.OnItemLongClickListener, RecycleBaseAdapter.OnSingleViewClickListener {

    public static final String BUNDLE_KEY_CATALOG = "BUNDLE_KEY_CATALOG";
    private static final String TAG = "BaseRecycleViewFragment";
    protected MySwipeRefreshLayout mSwipeRefresh;
    protected FixedRecyclerView mRecycleView;
    protected LinearLayoutManager mLayoutManager;
    protected RecycleBaseAdapter mAdapter;
    protected EmptyLayout mErrorLayout;
    protected int mStoreEmptyState = -1;
    protected String mStoreEmptyMessage;

    protected int mCurrentPage = 0;
    protected int mCatalog = NewsList.CATALOG_ALL;

    //private AsyncTask<String, Void, ListEntity> mCacheTask;
    private ParserTask mParserTask;

    private RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            /*
            * 回收滚轴监听方法
            *
            * 输入视图
            *
            * 及其位置
            *
            *
            * */
            super.onScrolled(recyclerView, dx, dy);
            int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            int totalItemCount = mLayoutManager.getItemCount();
            if (lastVisibleItem >= totalItemCount - 4 && dy > 0) {
                if (mState == STATE_NONE && mAdapter != null
                        && mAdapter.getDataSize() > 0) {
                    loadMore();
                }
            }
        }
    };

    protected int getLayoutRes() {
        return R.layout.v2_fragment_swipe_refresh_recyclerview;
    }

    public void onCreate(Bundle savedInstanceState) {
        /*
        * 创建回收视图方法
        *
        * 输入保存状态
        *
        * 无输出
        * */
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mCatalog = args.getInt(BUNDLE_KEY_CATALOG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /*
        * 创建视图方法
        * */
        View view = inflater.inflate(getLayoutRes(), container, false);
        initViews(view);
        return view;
    }

    protected void initViews(View view) {
        /*
        *初始化视图
        *
        * 输入视图
        *
        * 无输出
        *
        *
        * */
        mErrorLayout = (EmptyLayout) view.findViewById(R.id.error_layout);
        mErrorLayout.setOnLayoutClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mCurrentPage = 0;//
                mState = STATE_REFRESH;//
                mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);//
                requestData(true);
            }
        });

        mSwipeRefresh = (MySwipeRefreshLayout) view.findViewById(R.id.srl_refresh);
        mSwipeRefresh.setColorSchemeResources(R.color.main_green, R.color.main_gray, R.color.main_black, R.color.main_purple);//等待
        mSwipeRefresh.setOnRefreshListener(new MySwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        mRecycleView = (FixedRecyclerView) view.findViewById(R.id.recycleView);//
        mRecycleView.setOnScrollListener(mScrollListener);

        if (isNeedListDivider()) {
            // use a linear layout manager
            mRecycleView.addItemDecoration(new DividerItemDecoration(getActivity(),//
                    DividerItemDecoration.VERTICAL_LIST));//
        }

        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycleView.setLayoutManager(mLayoutManager);
        mRecycleView.setHasFixedSize(true);

        if (mAdapter != null) {
            mRecycleView.setAdapter(mAdapter);
            mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
        } else {
            mAdapter = getListAdapter();
            mAdapter.setOnItemClickListener(this);
            mAdapter.setOnItemLongClickListener(this);
            mAdapter.setOnSingleViewClickListener(this);
            mRecycleView.setAdapter(mAdapter);

            if (requestDataIfViewCreated()) {
                mCurrentPage = 0;
                mState = STATE_REFRESH;
                if(useSingleState()){
                    mAdapter.setState(RecycleBaseAdapter.STATE_SINGLE_LOADING);
                } else {
                    mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
                }
                //requestData(requestDataFromNetWork());
                new ReadCacheTask(this).execute();
            } else {
                mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            }
        }

        if(!useSingleState()) {
            if (mStoreEmptyState != -1) {
                mErrorLayout.setErrorType(mStoreEmptyState);
            }
            if (!TextUtils.isEmpty(mStoreEmptyMessage)) {
                mErrorLayout.setErrorMessage(mStoreEmptyMessage);
            }
        }
    }

    @Override
    public void onDestroyView() {
//
        /*
        * 销毁视图方法
        *
        * 无输入
        *
        * 无输出
        * */
        mStoreEmptyState = mErrorLayout.getErrorState();
        mStoreEmptyMessage = mErrorLayout.getMessage();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        //cancelReadCacheTask();
        //cancelParserTask();
        super.onDestroy();
    }

    @Override
    public void onItemClick(View view) {
        /*
        * 在项目上点到物体的方法
        *
        * 输入视图
        *
        * 无输出
        * */
        onItemClick(view, mRecycleView.getChildPosition(view));
    }

    protected void onItemClick(View view, int position) {
    }

    @Override
    public boolean onItemLongClick(View view) {
        return onItemLongClick(view, mRecycleView.getChildPosition(view));
    }

    protected boolean onItemLongClick(View view, int position) {
        return false;
    }

    @Override
    public void onSingleViewClick(View view) {
        /*
        * 单击视图项目的方法
        *
        * 输入视图
        *
        * 无输出
        * */
        mCurrentPage = 0;
        mState = STATE_REFRESH;
        mAdapter.setState(RecycleBaseAdapter.STATE_SINGLE_LOADING);
        mAdapter.notifyDataSetChanged();
        //mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
        requestData(true);
    }

    protected abstract RecycleBaseAdapter getListAdapter();

    @Deprecated
    protected boolean requestDataFromNetWork() {
        return false;
    }

    protected boolean requestDataIfViewCreated() {
        return true;
    }

    protected String getCacheKeyPrefix() {
        return null;
    }

    protected ListEntity parseList(InputStream is) throws Exception {
        return null;
    }

    protected ListEntity readList(Serializable seri) {
        return null;
    }

    protected AsyncHttpResponseHandler getResponseHandler() {
        return new ResponseHandler(this);
    }

    protected void notifyDataSetChanged() {
        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();
    }

    public void refresh() {
        /*
        * 重新更新视图
        *
        * 无输入
        *
        * 无输出
        * */
        mCurrentPage = 0;
        mState = STATE_REFRESH;
        requestData(true);
    }

    public void loadMore() {
        /*
        * 加载更多视图方法
        *
        * 无输入
        *
        * 无输出
        *
        *
        * */
        if (mState == STATE_NONE) {
            if (mAdapter.getState() == ListBaseAdapter.STATE_LOAD_MORE
                    || mAdapter.getState() == ListBaseAdapter.STATE_NETWORK_ERROR) {
                TLog.log(TAG, "begin to load more data.");
                mCurrentPage++;
                mState = STATE_LOADMORE;
                requestData(false);
            }
        }
    }

    protected String getCacheKey() {

        /*
        * 获取隐藏关键字
        *
        * 无输入
        *
        * 输出隐藏关键字
        *
        *
        * */
        return new StringBuffer(getCacheKeyPrefix()).append(mCatalog)
                .append("_").append(mCurrentPage).append("_")
                .append(TDevice.getPageSize()).toString();
    }

    protected void requestData(boolean refresh) {
        sendRequestData();
    }

    protected void sendRequestData() {
    }

    protected boolean useSingleState() {
        return false;
    }

    public long getCacheExpire() {
        return Constants.CACHE_EXPIRE_OND_DAY;
    }

    protected boolean isNeedListDivider() {
        return true;
    }
/*
* 静态类
*
* 读取隐藏任务
*
* 继承自模板类
* */
    static class ReadCacheTask extends
            WeakAsyncTask<Void, Void, byte[], BaseRecycleViewFragment> {

        public ReadCacheTask(BaseRecycleViewFragment target) {
            super(target);
        }

        @Override
        protected byte[] doInBackground(BaseRecycleViewFragment target,
                                        Void... params) {
            if (target == null) {
                TLog.log(TAG, "weak task target is null.");
                return null;
            }
            if (TextUtils.isEmpty(target.getCacheKey())) {
                TLog.log(TAG, "unset cache key.no cache.");
                return null;
            }

            byte[] data = CacheManager.getCache(target.getCacheKey());
            if (data == null) {
                TLog.log(TAG, "cache data is empty.:" + target.getCacheKey());
                return null;
            }

            TLog.log(TAG, "exist cache:" + target.getCacheKey() + " data:"
                    + data);

            return data;
        }

        @Override
        protected void onPostExecute(BaseRecycleViewFragment target,
                                     byte[] result) {
            /*
            * 上传执行类
            *
            * 有输入
            *
            * 没输出
            *
            *
            * */
            super.onPostExecute(target, result);
            if (target == null)
                return;
            if (result != null) {
                try {
                    target.executeParserTask(result, true);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    TLog.log(TAG, "parser cache error :" + e.getMessage());
                }
            }
//            target.requestData(true);
            target.refresh();
        }
    }
/*
* 私有静态类
*
* 响应标题类
*
* 继承自Http响应
* */
    private static class ResponseHandler extends AsyncHttpResponseHandler {
        private WeakReference<BaseRecycleViewFragment> mInstance;

        ResponseHandler(BaseRecycleViewFragment instance) {
            mInstance = new WeakReference<>(instance);
        }

        @Override
        public void onSuccess(int i, Header[] headers, byte[] responseBytes) {
            /*
            * 成功相应处理方法
            *
            * 输入标题，响应字节
            *
            * 无输出
            * */
            if (mInstance != null) {
                BaseRecycleViewFragment instance = mInstance.get();
                if (instance != null && instance.isAdded()) {
                    //if (instance.mState == STATE_REFRESH) {
                    //    instance.onRefreshNetworkSuccess();
                    //AppContext.setRefreshTime(instance.getCacheKey(),System.currentTimeMillis());
                    //}
                    instance.executeParserTask(responseBytes, false);
                }
            }
        }

        @Override
        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                        /*
            * 失败相应处理方法
            *
            * 输入标题，响应字节
            *
            * 无输出
            * */
            if (mInstance != null) {
                BaseRecycleViewFragment instance = mInstance.get();
                if (instance != null && instance.isAdded()) {
                    //.readCacheData(instance.getCacheKey());
                    instance.executeOnLoadDataError(null);
                    instance.executeOnLoadFinish();
                }
            }
        }
    }
/*
* 私有静态类
*
* 任务解析器
*
* */
    // Parse model when request data success.
    private static class ParserTask extends AsyncTask<Void, Void, String> {
        private WeakReference<BaseRecycleViewFragment> mInstance;
        private byte[] responseData;
        private boolean parserError;
        private boolean fromCache;
        private List<?> list;

        public ParserTask(BaseRecycleViewFragment instance, byte[] data, boolean fromCache) {
            /*
            * 解析任务
            *
            * 构造类
            * */
            this.mInstance = new WeakReference<>(instance);
            this.responseData = data;
            this.fromCache = fromCache;
        }

        @Override
        protected String doInBackground(Void... params) {
            /*
            * 后台执行方法
            *
            * 无输入
            *
            * 输出信息
            * */
            BaseRecycleViewFragment instance = mInstance.get();
            if (instance == null) return null;
            try {
                ListEntity data = instance.parseList(new ByteArrayInputStream(responseData));
                if (!fromCache) {
                    UIHelper.sendNoticeBroadcast(instance.getActivity(), data);
                }
                //new SaveCacheTask(instance, data, instance.getCacheKey()).execute();
                // save the cache
                if (!fromCache && instance.mCurrentPage == 0 && !TextUtils.isEmpty(instance.getCacheKey())) {
                    CacheManager.setCache(instance.getCacheKey(), responseData,
                            instance.getCacheExpire(), CacheManager.TYPE_INTERNAL);
                }
                list = data.getList();
            } catch (Exception e) {
                e.printStackTrace();
                parserError = true;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            /*
            * 展示执行方法
            *
            * 受保护
            *
            * 输入结果集
            *
            * 无输出
            * */
            super.onPostExecute(result);
            BaseRecycleViewFragment instance = mInstance.get();
            if (instance != null) {
                if (parserError) {
                    //instance.readCacheData(instance.getCacheKey());
                    instance.executeOnLoadDataError(null);
                } else {
                    instance.executeOnLoadDataSuccess(list);
                    if (!fromCache) {
                        if (instance.mState == STATE_REFRESH) {
                            instance.onRefreshNetworkSuccess();
                        }
                    }
                    instance.executeOnLoadFinish();
                }
                if (fromCache) {
                    TLog.log(TAG, "key:" + instance.getCacheKey()
                            + ",set cache data finish ,begin to load network data.");
//                    instance.requestData(true);
                    instance.refresh();
                }
            }
        }
    }

    protected void executeOnLoadDataSuccess(List<?> data) {
        /*
        * 加载成功数据
        *
        * 输入列表
        *
        * 无输出
        *
        * */
        if (mState == STATE_REFRESH)
            mAdapter.clear();
        mAdapter.addData(data);
        mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
        if (data.size() == 0 && mState == STATE_REFRESH) {
            if(useSingleState()){
                mAdapter.setState(RecycleBaseAdapter.STATE_SINGLE_EMPTY);
                String emptyTip = getEmptyTip();
                if (!TextUtils.isEmpty(emptyTip))
                    mAdapter.setEmptyText(emptyTip);
            } else {
                mErrorLayout.setErrorType(EmptyLayout.NODATA);
                String emptyTip = getEmptyTip();
                if (!TextUtils.isEmpty(emptyTip))
                    mErrorLayout.setErrorMessage(emptyTip);
            }
        } else if (data.size() < TDevice.getPageSize()) {
            if (mState == STATE_REFRESH)
                mAdapter.setState(ListBaseAdapter.STATE_LESS_ONE_PAGE);
            else
                mAdapter.setState(ListBaseAdapter.STATE_NO_MORE);
        } else {
            mAdapter.setState(ListBaseAdapter.STATE_LOAD_MORE);
        }
    }

    protected String getEmptyTip() {
        return null;
    }

    protected void onRefreshNetworkSuccess() {
        // TODO do nothing
    }

    protected void executeOnLoadDataError(String error) {

//
        /*、
        * 加载错误汇报
        *
        * 输入错误语句
        *
        * 无输出
        *
        * */
        if (mCurrentPage == 0) {
            if (mAdapter.getDataSize() == 0) {
                if(useSingleState()){
                    mAdapter.setState(RecycleBaseAdapter.STATE_SINGLE_ERROR);
                } else {
                    mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
                }
            } else {
                mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
                String message = error;
                if (TextUtils.isEmpty(error)) {
                    if (TDevice.hasInternet()) {
                        message = getString(R.string.tip_load_data_error);
                    } else {
                        message = getString(R.string.tip_network_error);
                    }
                }
                AppContext.showToastShort(message);
            }
        } else {
            mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            mAdapter.setState(ListBaseAdapter.STATE_NETWORK_ERROR);
        }
        mAdapter.notifyDataSetChanged();
    }

    protected void executeOnLoadFinish() {
//
        /*
        *加载完成信息显示
        *
        * 无输入
        *
        * 无输出
        *
        * */
        mSwipeRefresh.setRefreshing(false);
        mState = STATE_NONE;
    }

    private void executeParserTask(byte[] data, boolean fromCache) {
//
        //
        /*
        * 返回个人任务方法
        *
        * */
        cancelParserTask();
        mParserTask = new ParserTask(this, data, fromCache);
        mParserTask.execute();
    }

//    package Test;
//
//    import java.io.*;
//    import java.net.*;
//    import java.util.*;
//
//    public class ServerSocketTest implements Runnable {
//
//        static String line = null;
//        static String filename;
//        static StringTokenizer tokenizerLine;
//        static ServerSocket serverSocket;
//        static  Socket socket;
//
//        static BufferedReader br;
//
//        static File file;
//        public static void main(String[] args) throws IOException, InterruptedException {
//            serverSocket = new ServerSocket(10001);
//            socket = serverSocket.accept();
//            System.out.println("Connected: " + socket.getRemoteSocketAddress());
//
//            //br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            int i=0;
//            while (i<100) {
//                try {
//                    br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                    while ((line = br.readLine()) != null) {
//                        System.out.print(i++);
//                        System.out.println(line);
//                        System.out.println("begin!"+i++);
//                        ServerSocketTest tr = new ServerSocketTest();
//                        Thread thread = new Thread(tr);
//                        thread.start();
//                        System.out.println("end!"+i--);
//
//                        break;
//                    }
//                } catch (Exception e) { // 客户端断开的情况
//                    System.out.println("Connection Close");
//                    break;
    private void cancelParserTask() {
        if (mParserTask != null) {
            mParserTask.cancel(true);
            mParserTask = null;
        }
    }
}
