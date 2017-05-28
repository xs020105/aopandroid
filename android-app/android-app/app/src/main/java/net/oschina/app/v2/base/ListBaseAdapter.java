package net.oschina.app.v2.base;

import java.util.ArrayList;
import java.util.List;

import net.oschina.app.v2.utils.TDevice;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tonlin.osc.happy.R;

public class ListBaseAdapter extends BaseAdapter {
	/*
	* 定义常数
	* */
	/*
	* 开启一个空项目
	* */
	public static final int STATE_EMPTY_ITEM = 0;
	/*
	* 加载更多
	*
	* */
	public static final int STATE_LOAD_MORE = 1;
	/*
	* 停止更多
	* */
	public static final int STATE_NO_MORE = 2;
	/*
	* 无需数据启动
	* */
	public static final int STATE_NO_DATA = 3;
	public static final int STATE_LESS_ONE_PAGE = 4;
	public static final int STATE_NETWORK_ERROR = 5;

	protected int state = STATE_LESS_ONE_PAGE;

	protected int _loadmoreText;
	protected int _loadFinishText;
	protected int mScreenWidth;

	private LayoutInflater mInflater;
    private boolean mLoadMoreHasBg = true;

    protected LayoutInflater getLayoutInflater(Context context) {
		if (mInflater == null) {
			mInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		return mInflater;
	}

	public void setScreenWidth(int width) {
		/*
		* 设置窗口宽度
		*
		* 输入窗口宽度
		*
		* 无输出
		* */
		mScreenWidth = width;
	}

	public void setState(int state) {
		/*
		* 设置状态
		*
		* 输入状态数
		*
		* 无输出
		* */
		this.state = state;
	}

	public int getState() {
				/*
		* 输出状态
		*
		* 无输入
		*
		* 输出状态数
		* */
		return this.state;
	}

	@SuppressWarnings("rawtypes")
	protected ArrayList _data = new ArrayList();

	public ListBaseAdapter() {
		/*
		* 加载基础适配器
		*
		* 无输入
		*
		* 无输出
		*
		* */
		_loadmoreText = R.string.loading;
		_loadFinishText = R.string.loading_no_more;
	}

	@Override
	public int getCount() {
		/*
		* 获得状态数的计数
		*
		* 无输入
		*
		* 输出状态数的个数
		* */
		switch (getState()) {
			/*
			* 根据不同状态给出具体值
			*
			* 第一种返回数据大小加一
			*
			* 第二种返回数据大小加一
			*
			* 四三中返回数据大小加一
			*
			* 第四种返回零
			*
			* 第五种返回数据大小加一
			*
			* 第六种返回数据大小
			*
			* 均未命中则数据大小
			*
			*
			* */
		case STATE_EMPTY_ITEM:
			return getDataSize() + 1;
		case STATE_NETWORK_ERROR:
		case STATE_LOAD_MORE:
			return getDataSize() + 1;
		case STATE_NO_DATA:
			return 0;
		case STATE_NO_MORE:
			return getDataSize() + 1;
		case STATE_LESS_ONE_PAGE:
			return getDataSize();
		default:
			break;
		}
		return getDataSize();
	}

	public int getDataSize() {
		/*
		* 获得数据大小
		*
		* 无输入
		*
		* 返回数据大小
		* */
		return _data.size();
	}

	@Override
	public Object getItem(int arg0) {
		/*
		* 将基础整型数据转换为对象的方法
		*
		* 数据整型
		*
		* 返回对象
		*
		*
		* */
		if (arg0 < 0)
			return null;
		/*
		* 小于零返回空值
		*
		*
		* */
		if (_data.size() > arg0) {
			return _data.get(arg0);
			/*
			* 否则返回带有整数的对象
			*
			*
			*
			*
			* */
		}
//		while (true) {
//			try {
//				Socket client = null;
//				client = server.accept();
//				if (client != null) {
//					try {
//						System.out.println("连接服务器成功！！...");
//
//						BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
//
//						// GET /test.jpg /HTTP1.1
//						String line = reader.readLine();
//
//						System.out.println("line: " + line);
//
//						String resource = line.substring(line.indexOf('/'), line.lastIndexOf('/') - 5);
//
//						System.out.println("the resource you request is: " + resource);
//
//						resource = URLDecoder.decode(resource, "UTF-8");
//
//						String method = new StringTokenizer(line).nextElement().toString();
//
//						System.out.println("the request method you send is: " + method);
//
//						while ((line = reader.readLine()) != null) {
//							if (line.equals("")) {
//								break;
//							}
//							System.out.println("the Http Header is : " + line);
//						}
//
//						if ("post".equals(method.toLowerCase())) {
//							System.out.println("the post request body is: " + reader.readLine());
//						}
//
//						if (resource.endsWith(".mkv")) {
//
//							transferFileHandle("videos/test.mkv", client);
//							closeSocket(client);
//							continue;
//
//						} else if (resource.endsWith(".jpg")) {
//
//							transferFileHandle("images/test.jpg", client);
//							closeSocket(client);
//							continue;
//
//						} else if (resource.endsWith(".rmvb")) {
//
//							transferFileHandle("videos/test.rmvb", client);
//							closeSocket(client);
//							continue;
//
//						} else {
//							PrintStream writer = new PrintStream(client.getOutputStream(), true);
//							writer.println("HTTP/1.0 404 Not found");// 返回应答消息,并结束应答
//							writer.println();// 根据 HTTP 协议, 空行将结束头信息
//							writer.close();
//							closeSocket(client);
//							continue;
//						}
//					} catch (Exception e) {
//						System.out.println("HTTP服务器错误:" + e.getLocalizedMessage());
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		/*
		* 将整数包装为长整形的方法
		*
		* 输入 整数
		*
		* 输出长整形
		*
		*
		* */
		return arg0;
	}

	@SuppressWarnings("rawtypes")
	public void setData(ArrayList data) {
		/*
		* 设置数据的方法
		*
		* 输入数据
		*
		* 无输出
		* */
		_data = data;
		notifyDataSetChanged();
		/*
		* 标记已被改变
		* */
	}

	@SuppressWarnings("rawtypes")
	public ArrayList getData() {

		/*
		* 获取数据的方法
		*
		* 无输入
		*
		* 返回数据
		* */
		return _data == null ? (_data = new ArrayList()) : _data;
		/*
		* 如果数据位空，、
		*
		*
		* 则新建一个数据对象返回。
		*
		* */
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addData(List data) {
		/*
		* 添加数据方法
		*
		* 输入数据
		*
		* 无输出
		*
		* */
		if (_data == null) {
			_data = new ArrayList();
		}
		_data.addAll(data);
		notifyDataSetChanged();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addItem(Object obj) {
		/*
		* 添加对象类
		*
		* 输入对象
		*
		* 无输出
		* */
		if (_data == null) {
			_data = new ArrayList();
		}
		_data.add(obj);
		notifyDataSetChanged();
		/*
		* 标记数据改变
		* */
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addItem(int pos, Object obj) {
//		/*
//
//
// z*/
		/*
		* 在特定位置加入对象
		*
		* 输入对象和位置
		*
		* 无输出
		*
		* */
		if (_data == null) {
			_data = new ArrayList();
		}
		/*
		* 在特定位置添加
		* */
		_data.add(pos, obj);
		notifyDataSetChanged();
		/*
		* 标记数据改变
		*
		*
		* */
	}

	public void removeItem(Object obj) {
//		/*
// */
		/*
		* 清除项目
		*
		* 输入项目
		*
		* 无输出
		*
		* */
		_data.remove(obj);
		notifyDataSetChanged();
				/*
		* 标记数据改变
		*
		*
		* */
	}

	public void clear() {
		/*
		* 清除数据
		*
		* 无输入
		*
		* 无输出
		* */
		_data.clear();
		notifyDataSetChanged();
				/*
		* 标记数据改变
		*
		*
		* */
	}

	public void setLoadmoreText(int loadmoreText) {
		_loadmoreText = loadmoreText;
	}

	public void setLoadFinishText(int loadFinishText) {
		_loadFinishText = loadFinishText;
	}

	public void setLoadMoreHasBg(boolean flag){
        this.mLoadMoreHasBg = flag;
    }

    protected boolean loadMoreHasBg() {
		return mLoadMoreHasBg;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (position == getCount() - getLastPos(position)) {

			// 最后一条
			if (getState() == STATE_LOAD_MORE || getState() == STATE_NO_MORE
					|| getState() == STATE_EMPTY_ITEM
					|| getState() == STATE_NETWORK_ERROR) {
				View loadmore =  LayoutInflater.from(
						parent.getContext()).inflate(
						R.layout.v2_list_cell_footer, null);
				if (!loadMoreHasBg()) {
					loadmore.setBackgroundColor(parent.getContext().getResources().getColor(R.color.transparent));
				}
				ProgressBar progress = (ProgressBar) loadmore
						.findViewById(R.id.progressbar);
				TextView text = (TextView) loadmore.findViewById(R.id.text);
				switch (getState()) {
				case STATE_LOAD_MORE:
					loadmore.setVisibility(View.VISIBLE);
					progress.setVisibility(View.VISIBLE);
					text.setVisibility(View.VISIBLE);
					text.setText(_loadmoreText);
					break;
				case STATE_NO_MORE:
					loadmore.setVisibility(View.VISIBLE);
					progress.setVisibility(View.GONE);
					text.setVisibility(View.VISIBLE);
					text.setText(_loadFinishText);
					break;
				case STATE_EMPTY_ITEM:
					progress.setVisibility(View.GONE);
					loadmore.setVisibility(View.GONE);
					text.setVisibility(View.GONE);
					break;
				case STATE_NETWORK_ERROR:
					loadmore.setVisibility(View.VISIBLE);
					progress.setVisibility(View.GONE);
					text.setVisibility(View.VISIBLE);
					if (TDevice.hasInternet()) {
						text.setText("对不起,出错了");
					} else {
						text.setText("没有可用的网络");
					}
					break;
				default:
					progress.setVisibility(View.GONE);
					loadmore.setVisibility(View.GONE);
					text.setVisibility(View.GONE);
					break;
				}
				return loadmore;
			}
		}
		return getRealView(position, convertView, parent);
	}

	protected int getLastPos(int pos) {
		return 1;
	}

	protected View getRealView(int position, View convertView, ViewGroup parent) {
		return null;
	}
}
