package net.oschina.app.v2.base;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.List;

import net.oschina.app.v2.AppContext;
import net.oschina.app.v2.cache.CacheManager;
import net.oschina.app.v2.model.Base;
import net.oschina.app.v2.model.ListEntity;
import net.oschina.app.v2.model.NewsList;
import net.oschina.app.v2.model.Notice;
import net.oschina.app.v2.ui.empty.EmptyLayout;
import net.oschina.app.v2.utils.TDevice;
import net.oschina.app.v2.utils.UIHelper;

import org.apache.http.Header;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.tonlin.osc.happy.R;
/*
* 这里是碎片列表抽象类
*
* 继承自作者写的另一个类
*
* 实现多个接口
* */
public abstract class BaseListFragment extends BaseTabFragment implements
		OnRefreshListener<ListView>, OnLastItemVisibleListener,
		OnItemClickListener {

	public static final String BUNDLE_KEY_CATALOG = "BUNDLE_KEY_CATALOG";

    protected SwipeRefreshLayout mSwipeRefresh;
	protected ListView mListView;
	protected ListBaseAdapter mAdapter;
	protected EmptyLayout mErrorLayout;
	protected int mStoreEmptyState = -1;

	protected int mCurrentPage = 0;
	protected int mCatalog = NewsList.CATALOG_ALL;

	private AsyncTask<String, Void, ListEntity> mCacheTask;
	private ParserTask mParserTask;

	protected int getLayoutRes() {
		return R.layout.v2_fragment_swipe_refresh_listview;
	}

	public void onCreate(Bundle savedInstanceState) {
		/*
		* 这里是创建碎片列表的方法
		*
		*输入以保存的状态实例
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
		* 这里是创建视图列表的方法
		*
		*输入以保存的状态实例
		*
		* 输出视图
		* */
		View view = inflater.inflate(getLayoutRes(), container, false);
		initViews(view);
		return view;
	}

	protected void initViews(View view) {
		/*
		* 初始化视图的函数
		*
		* 视图输入
		*
		* 无输出
		* */
		mErrorLayout = (EmptyLayout) view.findViewById(R.id.error_layout);
		mErrorLayout.setOnLayoutClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mCurrentPage = 0;
				mState = STATE_REFRESH;
				mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
				requestData(true);
			}
		});

        mSwipeRefresh = (SwipeRefreshLayout)view.findViewById(R.id.srl_refresh);
        mSwipeRefresh.setColorSchemeColors(R.color.main_green);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               BaseListFragment.this.onRefresh(null);
            }
        });

		mListView = (ListView) view.findViewById(R.id.listview);
		mListView.setOnItemClickListener(this);
        mListView.setOnScrollListener(mScrollListener);

		if (mAdapter != null) {
			mListView.setAdapter(mAdapter);
			mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
		} else {
			mAdapter = getListAdapter();
			mListView.setAdapter(mAdapter);

			if (requestDataIfViewCreated()) {
				mCurrentPage = 0;
				mState = STATE_REFRESH;
				mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
				requestData(requestDataFromNetWork());
			} else {
				mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
			}
		}
		if (mStoreEmptyState != -1) {
			mErrorLayout.setErrorType(mStoreEmptyState);
		}
	}
	
	@Override
	public void onDestroyView() {

		/*
		* 销毁视图的方法
		*
		* 无输入
		*
		* 无输出
		*
		*
		*
		* */
		mStoreEmptyState = mErrorLayout.getErrorState();
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
				/*
		* 执行销毁视图的方法
		*
		* 无输入
		*
		* 无输出
		*
		*
		*
		* */
		cancelReadCacheTask();
		cancelParserTask();
		super.onDestroy();
	}

	protected abstract ListBaseAdapter getListAdapter();
	
	protected boolean requestDataFromNetWork() {
		return false;
	}
	
	protected boolean requestDataIfViewCreated() {

		/*
		* 返回是否可以回应数据的方法
		*
		* 	* 无输入
		*
		* 无输出
		*
		*
		* */
		return true;
	}

	protected String getCacheKeyPrefix() {
		/*
		* 返回检查关键方法
		* 	* 无输入
		*
		* 无输出
		*
		*
		* */
		return null;
	}

	protected ListEntity parseList(InputStream is) throws Exception {
		return null;
	}

	protected ListEntity readList(Serializable seri) {
		return null;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView) {
		/*
		* 执行更新的方法
		*
		* 输入碎片列表
		*
		* 无输出
		* */
		mCurrentPage = 0;
		mState = STATE_REFRESH;
		requestData(true);
	}

	@Override
	public void onLastItemVisible() {

		/*
		* 这里是最后一个视图可视化的方法
		*
		* 无输入
		*
		* 无输出
		*
		* */
		if (mState == STATE_NONE) {
			if (mAdapter.getState() == ListBaseAdapter.STATE_LOAD_MORE) {
				mCurrentPage++;
				mState = STATE_LOADMORE;
				requestData(false);
			}
		}
	}

	protected String getCacheKey() {

		/*
		* 这里是获得隐藏关键字的方法
		*
		* 无输入
		*
		* 无输出
		* */
		return new StringBuffer(getCacheKeyPrefix()).append(mCatalog)
				.append("_").append(mCurrentPage).append("_")
				.append(TDevice.getPageSize()).toString();
	}

	protected void requestData(boolean refresh) {
		/*
		* 这里是更新数据的方法
		*
		* 无输入
		*
		* 无输出
		* */
		String key = getCacheKey();
		if (TDevice.hasInternet()
				&& (!CacheManager.isReadDataCache(getActivity(), key) || refresh)) {
			sendRequestData();
		} else {
			readCacheData(key);
		}
	}

	protected void sendRequestData() {
	}

	private void readCacheData(String cacheKey) {

		/*
		* 读取隐藏关键字的方法
		*
		* 输入为隐藏关键字
		*
		* 无输出
		* */
		cancelReadCacheTask();
		mCacheTask = new CacheTask(getActivity()).execute(cacheKey);
	}

	private void cancelReadCacheTask() {
				/*
		* 取消读取隐藏关键字的方法
		*
		* 无输入
		*
		* 无输出
		* */
		if (mCacheTask != null) {
			mCacheTask.cancel(true);
			mCacheTask = null;
		}
	}
/*
* 这里是隐藏任务的公共类
*
*继承自模板
*
* 异步任务
*
*
* */
	private class CacheTask extends AsyncTask<String, Void, ListEntity> {
		private WeakReference<Context> mContext;

		private CacheTask(Context context) {
			mContext = new WeakReference<Context>(context);
		}

		@Override
		protected ListEntity doInBackground(String... params) {
			Serializable seri = CacheManager.readObject(mContext.get(),
					params[0]);
			if (seri == null) {
				return null;
			} else {
				return readList(seri);
			}
		}

		@Override
		protected void onPostExecute(ListEntity list) {
			/*销毁任务方法、
			*
			* 输入销毁列表
			*
			* 无输出
			*
			* */
			super.onPostExecute(list);
			if (list != null) {
				executeOnLoadDataSuccess(list.getList());
			} else {
				executeOnLoadDataError(null);
			}
			executeOnLoadFinish();
		}
	}
/*
* 这里是保存隐藏任务的方法
*
*
*继承自模板
*
* 异步任务
*
*
* */
	private class SaveCacheTask extends AsyncTask<Void, Void, Void> {
		private WeakReference<Context> mContext;
		private Serializable seri;
		private String key;

		private SaveCacheTask(Context context, Serializable seri, String key) {
			/*
			* 保存隐藏任务
			*
			* 输入任务
			*
			* 无输出
			*
			*
			* */
			mContext = new WeakReference<Context>(context);
			this.seri = seri;
			this.key = key;
		}

		@Override
		protected Void doInBackground(Void... params) {
			/*
			* 后台执行方法
			*
			* 无输入
			*
			* 无输出
			* */
			CacheManager.saveObject(mContext.get(), seri, key);
			return null;
		}
	}

	protected AsyncHttpResponseHandler mHandler = new AsyncHttpResponseHandler() {

		@Override
		public void onSuccess(int statusCode, Header[] headers,
				byte[] responseBytes) {
//
			/*
			* 检测是否成功的方法
			*
			*
			* */
			if (isAdded()) {
				if (mState == STATE_REFRESH) {
					onRefreshNetworkSuccess();
					AppContext.setRefreshTime(getCacheKey(),
							System.currentTimeMillis());
				}
				executeParserTask(responseBytes);
			}
		}

		@Override
		public void onFailure(int arg0, Header[] arg1, byte[] arg2,
				Throwable arg3) {
						/*
			* 检测是否失败的方法
			*
			*
			* */

			if (isAdded()) {
				readCacheData(getCacheKey());
			}
		}
	};

	protected void executeOnLoadDataSuccess(List<?> data) {
//		/*/*
//
// */*/
		/*
		* 加载数据成功的方法
		*
		* 输入未知列表
		*
		* 无返回
		* */
		if (mState == STATE_REFRESH)
			mAdapter.clear();
		mAdapter.addData(data);
		mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
		if (data.size() == 0 && mState == STATE_REFRESH) {
			mErrorLayout.setErrorType(EmptyLayout.NODATA);
		} else if (data.size() < TDevice.getPageSize()) {
			if (mState == STATE_REFRESH)
				mAdapter.setState(ListBaseAdapter.STATE_NO_MORE);
			else
				mAdapter.setState(ListBaseAdapter.STATE_NO_MORE);
		} else {
			mAdapter.setState(ListBaseAdapter.STATE_LOAD_MORE);
		}
	}

	protected void onRefreshNetworkSuccess() {

	}

	protected void executeOnLoadDataError(String error) {
				/*
		* 加载数据遇到错误的方法
		*
		* 输入未知列表
		*
		* 无返回
		* */
		if (mCurrentPage == 0) {
			mErrorLayout.setErrorType(EmptyLayout.NETWORK_ERROR);
		} else {
			mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
			mAdapter.setState(ListBaseAdapter.STATE_NETWORK_ERROR);
			mAdapter.notifyDataSetChanged();
		}
	}

	protected void executeOnLoadFinish() {
        mSwipeRefresh.setRefreshing(false);
		mState = STATE_NONE;
	}

	private void executeParserTask(byte[] data) {
		cancelParserTask();
		mParserTask = new ParserTask(data);
		mParserTask.execute();
	}

	private void cancelParserTask() {
		if (mParserTask != null) {
			mParserTask.cancel(true);
			mParserTask = null;
		}
	}

	class ParserTask extends AsyncTask<Void, Void, String> {

		private byte[] reponseData;
		private boolean parserError;
		private List<?> list;

		public ParserTask(byte[] data) {
			this.reponseData = data;
		}

		@Override
		protected String doInBackground(Void... params) {
			try {
				ListEntity data = parseList(new ByteArrayInputStream(
						reponseData));
				if (data instanceof Base) {
					Notice notice = ((Base) data).getNotice();
					if (notice != null) {
						UIHelper.sendBroadCast(getActivity(), notice ,"list");
					}
				}
				new SaveCacheTask(getActivity(), data, getCacheKey()).execute();
				list = data.getList();
			} catch (Exception e) {
				e.printStackTrace();
				parserError = true;
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (parserError) {
				readCacheData(getCacheKey());
			} else {
				executeOnLoadDataSuccess(list);
				executeOnLoadFinish();
			}
		}
	}

    private AbsListView.OnScrollListener mScrollListener = new AbsListView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            if (mState== STATE_NONE && mAdapter != null
                    && mAdapter.getDataSize() > 0
                    && mListView.getLastVisiblePosition() == (mListView
                    .getCount() - 1)) {
                onLastItemVisible();
            }
        }
    };
}
