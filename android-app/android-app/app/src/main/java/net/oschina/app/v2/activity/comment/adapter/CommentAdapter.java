package net.oschina.app.v2.activity.comment.adapter;

import java.util.List;

import net.oschina.app.v2.base.ListBaseAdapter;
import net.oschina.app.v2.base.RecycleBaseAdapter;
import net.oschina.app.v2.model.Comment;
import net.oschina.app.v2.model.Comment.Refer;
import net.oschina.app.v2.model.Comment.Reply;
import net.oschina.app.v2.model.Tweet;
import net.oschina.app.v2.ui.AvatarView;
import net.oschina.app.v2.ui.text.MyLinkMovementMethod;
import net.oschina.app.v2.ui.text.MyURLSpan;
import net.oschina.app.v2.ui.text.TweetTextView;
import net.oschina.app.v2.utils.DateUtil;
import net.oschina.app.v2.utils.UIHelper;
import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tonlin.osc.happy.R;

public class CommentAdapter extends RecycleBaseAdapter {

	private boolean showSplit;

	public interface OnOperationListener {
		void onMoreClick(Comment comment);
	}

	private OnOperationListener mListener;

	public CommentAdapter(OnOperationListener lis) {
		mListener = lis;
	}

	public CommentAdapter(OnOperationListener lis, boolean showSplit) {
		this.showSplit = showSplit;
		mListener = lis;
	}

    @Override
    protected void onBindHeaderViewHolder(RecycleBaseAdapter.ViewHolder holder, int position) {
        super.onBindHeaderViewHolder(holder, position);
    }

    @Override
    protected View onCreateItemView(ViewGroup parent, int viewType) {
        return getLayoutInflater(parent.getContext()).inflate(
                R.layout.v2_list_cell_comment, null);
    }

    @Override
    protected RecycleBaseAdapter.ViewHolder onCreateItemViewHolder(View view, int viewType) {
        return new ViewHolder(viewType, view);
    }

    @Override
    protected void onBindItemViewHolder(RecycleBaseAdapter.ViewHolder holder, int position) {
        super.onBindItemViewHolder(holder, position);
        ViewHolder vh = (ViewHolder)holder;
        if(position > _data.size()-1)
            return;
        final Comment item = (Comment) _data.get(position);

        vh.name.setText(item.getAuthor());

        vh.content.setMovementMethod(MyLinkMovementMethod.a());
        vh.content.setFocusable(false);
        vh.content.setDispatchToParent(true);
        vh.content.setLongClickable(false);
        Spanned span = Html.fromHtml(item.getContent());
        vh.content.setText(span);
        MyURLSpan.parseLinkText(vh.content, span);

        vh.time.setText(DateUtil.getFormatTime(item.getPubDate()));

        vh.from.setVisibility(View.VISIBLE);
        switch (item.getAppClient()) {
            default:
                vh.from.setText("");
                vh.from.setVisibility(View.GONE);
                break;
            case Tweet.CLIENT_MOBILE:
                vh.from.setText(R.string.from_mobile);
                break;
            case Tweet.CLIENT_ANDROID:
                vh.from.setText(R.string.from_android);
                break;
            case Tweet.CLIENT_IPHONE:
                vh.from.setText(R.string.from_iphone);
                break;
            case Tweet.CLIENT_WINDOWS_PHONE:
                vh.from.setText(R.string.from_windows_phone);
                break;
            case Tweet.CLIENT_WECHAT:
                vh.from.setText(R.string.from_wechat);
                break;
        }

        final Context context = vh.avatar.getContext();

        // setup refers
        List<Refer> refers = item.getRefers();
        vh.refers.removeAllViews();
        if (refers == null || refers.size() <= 0) {
            vh.refers.setVisibility(View.GONE);
        } else {
            vh.refers.setVisibility(View.VISIBLE);

            // add refer item
            for (Refer reply : refers) {
                View replyItemView = getLayoutInflater(context)
                        .inflate(R.layout.v2_list_cell_reply_name_content, null);

                View countView = getLayoutInflater(context)
                        .inflate(R.layout.v2_list_cell_reply_count, null);
                TextView name = (TextView) countView
                        .findViewById(R.id.tv_comment_reply_count);

                name.setText(reply.refertitle);
                vh.refers.addView(name);

                TweetTextView refersContent = (TweetTextView) replyItemView
                        .findViewById(R.id.tv_reply_content);
                refersContent.setMovementMethod(MyLinkMovementMethod.a());
                refersContent.setFocusable(false);
                refersContent.setDispatchToParent(true);
                refersContent.setLongClickable(false);
                Spanned rcontent = Html.fromHtml(reply.referbody);
                refersContent.setText(rcontent);
                MyURLSpan.parseLinkText(refersContent, rcontent);

                vh.refers.addView(replyItemView);
            }
        }

        // setup replies
        List<Reply> replies = item.getReplies();
        vh.relies.removeAllViews();
        if (replies == null || replies.size() <= 0) {
            vh.relies.setVisibility(View.GONE);
        } else {
            vh.relies.setVisibility(View.VISIBLE);

            // add count layout
            View countView = getLayoutInflater(context).inflate(
                    R.layout.v2_list_cell_reply_count, null);
            TextView count = (TextView) countView
                    .findViewById(R.id.tv_comment_reply_count);
            count.setText(context.getResources()
                    .getString(R.string.comment_reply_count, replies.size()));
            vh.relies.addView(countView);

            // add reply item
            for (Reply reply : replies) {
                View replyItemView = getLayoutInflater(context)
                        .inflate(R.layout.v2_list_cell_reply_name_content, null);

                TextView name = (TextView) replyItemView
                        .findViewById(R.id.tv_reply_name);
                name.setText(reply.rauthor + ":");

                TweetTextView replyContent = (TweetTextView) replyItemView
                        .findViewById(R.id.tv_reply_content);
                replyContent.setMovementMethod(MyLinkMovementMethod.a());
                replyContent.setFocusable(false);
                replyContent.setDispatchToParent(true);
                replyContent.setLongClickable(false);
                Spanned rcontent = Html.fromHtml(reply.rcontent);
                replyContent.setText(rcontent);
                MyURLSpan.parseLinkText(replyContent, rcontent);

                vh.relies.addView(replyItemView);
            }
        }

        vh.avatar.setUserInfo(item.getAuthorId(),item.getAuthor());
        vh.avatar.setAvatarUrl(item.getFace());
//        ImageLoader.getInstance().displayImage(item.getFace(), vh.avatar);
//        vh.avatar.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                UIHelper.showUserCenter(context,
//                        item.getAuthorId(), item.getAuthor());
//            }
//        });

        vh.more.setVisibility(View.GONE);
        vh.more.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onMoreClick(item);
                }
            }
        });

        vh.split.setVisibility(showSplit ? View.VISIBLE : View.GONE);
    }

	static class ViewHolder extends RecycleBaseAdapter.ViewHolder {
		TextView name, time, from;
		TweetTextView content;
		LinearLayout relies, refers;
		View split, more;
		AvatarView avatar;

		ViewHolder(int viewType,View view) {
            super(viewType,view);
			avatar = (AvatarView) view.findViewById(R.id.iv_avatar);
			name = (TextView) view.findViewById(R.id.tv_name);
			content = (TweetTextView) view.findViewById(R.id.tv_content);
			time = (TextView) view.findViewById(R.id.tv_time);
			from = (TextView) view.findViewById(R.id.tv_from);
			refers = (LinearLayout) view.findViewById(R.id.ly_refers);
			relies = (LinearLayout) view.findViewById(R.id.ly_relies);
			split = view.findViewById(R.id.split);
			more = view.findViewById(R.id.iv_more);
		}
	}
}
