/**
 *
 */
package com.mcxiaoke.minicat.controller;
import android.content.Intent;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import com.mcxiaoke.minicat.R;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import com.mcxiaoke.minicat.app.UIAbout;
import com.mcxiaoke.minicat.app.UIConversation;
import com.mcxiaoke.minicat.app.UIDebugMode;
import com.mcxiaoke.minicat.app.UIFavorites;
import com.mcxiaoke.minicat.app.UIGallery;
import com.mcxiaoke.minicat.app.UIHome;
import com.mcxiaoke.minicat.app.UILogin;
import com.mcxiaoke.minicat.app.UIOptions;
import com.mcxiaoke.minicat.app.UIJump;
import com.mcxiaoke.minicat.app.UIPhoto;
import com.mcxiaoke.minicat.app.UIPhotos;
import com.mcxiaoke.minicat.app.UIProfile;
import com.mcxiaoke.minicat.app.UIRecords;
import com.mcxiaoke.minicat.app.UISearch;
import com.mcxiaoke.minicat.app.UISearchResults;
import com.mcxiaoke.minicat.app.UIStatus;
import com.mcxiaoke.minicat.app.UIThread;
import com.mcxiaoke.minicat.app.UITimeline;
import com.mcxiaoke.minicat.app.UIUserList;
import com.mcxiaoke.minicat.app.UIWrite;
import com.mcxiaoke.minicat.dao.model.DirectMessageModel;
import com.mcxiaoke.minicat.dao.model.StatusModel;
import com.mcxiaoke.minicat.dao.model.StatusUpdateInfo;
import com.mcxiaoke.minicat.dao.model.UserModel;
import com.mcxiaoke.minicat.service.SyncService;
import com.mcxiaoke.minicat.util.StatusHelper;
import com.mcxiaoke.minicat.util.Utils;
import android.content.ComponentName;
import java.util.ArrayList;

/**
 * @author mcxiaoke
 * @version 1.1 2012.04.24
 */
public class UIController {

    private static void startUIByAnimation(Activity activity, Intent intent) {
        activity.startActivity(intent);
    }

    private static void startUIByAnimationBack(Activity activity, Intent intent) {
        activity.startActivity(intent);
    }

    public static void showFanfouBlog(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://blog.fanfou.com/"));
        context.startActivity(intent);
    }

    public static void showOption(Activity context) {
        Intent intent = new Intent(context, UIOptions.class);
        startUIByAnimation(context, intent);
    }

    public static void showJump(Activity context){
        Intent intent = new Intent(context, UIJump.class);
        startUIByAnimation(context, intent);
    }

    public static void goStatusPage(Activity context, StatusModel s) {
        if (s != null) {
            Intent intent = new Intent(context, UIStatus.class);
            intent.putExtra("data", s);
            startUIByAnimation(context, intent);
        }
    }

    public static void showDebug(Activity context) {
        startUIByAnimation(context, new Intent(context, UIDebugMode.class));
    }

    public static void showAbout(Activity context) {
        startUIByAnimation(context, new Intent(context, UIAbout.class));
    }

    public static void showLogin(Context context) {
        Intent intent = new Intent(context, UILogin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void showTopic(Activity context) {
        startUIByAnimation(context, new Intent(context, UISearch.class));
    }

    public static void showHome(Activity context) {
        Intent intent = new Intent(context, UIHome.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void showConversation(Activity context, DirectMessageModel dm) {
        Intent intent = new Intent(context, UIConversation.class);
        intent.putExtra("refresh", true);
        if (dm.isIncoming()) {
            intent.putExtra("id", dm.getSenderId());
            intent.putExtra("screen_name", dm.getSenderScreenName());
            intent.putExtra("profile_image_url", dm.getSenderProfileImageUrl());
        } else {
            intent.putExtra("id", dm.getRecipientId());
            intent.putExtra("screen_name", dm.getRecipientScreenName());
            intent.putExtra("profile_image_url",
                    dm.getRecipientProfileImageUrl());
        }
        startUIByAnimation(context, intent);
    }

    public static void showConversation(Activity context, UserModel user,
                                        boolean refresh) {
        Intent intent = new Intent(context, UIConversation.class);
        intent.putExtra("id", user.getId());
        intent.putExtra("screen_name", user.getScreenName());
        intent.putExtra("profile_image_url", user.getProfileImageUrlLarge());
        intent.putExtra("refresh", refresh);
        startUIByAnimation(context, intent);
    }

    public static void showWrite(Activity activity) {
        Intent intent = new Intent(activity, UIWrite.class);
        startUIByAnimation(activity, intent);
    }

    public static void goBackToWrite(Activity activity, StatusUpdateInfo info) {
        Intent intent = new Intent(activity, UIWrite.class);
        intent.putExtra(StatusUpdateInfo.TAG, info);
        startUIByAnimationBack(activity, intent);
    }

    public static void showWrite(Activity context, String text) {
        Intent intent = new Intent(context, UIWrite.class);
        intent.putExtra("text", text);
        startUIByAnimation(context, intent);
    }

    public static void doFavorite(final Context context, String id) {
        final Handler handler = getFavoriteHandler(context);
        SyncService.favorite(context, id, handler);
    }

    public static void doUnFavorite(final Context context, String id) {
        final Handler handler = getFavoriteHandler(context);
        SyncService.unfavorite(context, id, handler);
    }

    private static Handler getFavoriteHandler(final Context context) {
        final Handler handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case SyncService.RESULT_SUCCESS:
                        boolean favorited = msg.getData().getBoolean("boolean");
                        Utils.notify(context, favorited ? "收藏成功" : "取消收藏成功");
                        break;
                    case SyncService.RESULT_ERROR:
                        break;
                    default:
                        break;
                }
            }
        };
        return handler;
    }

    public static void doRetweet(Activity context, final StatusModel status) {
        Intent intent = new Intent(context, UIWrite.class);
        StringBuilder builder = new StringBuilder();
        builder.append(" 转@").append(status.getUserScreenName()).append(" ")
                .append(status.getSimpleText());
        intent.putExtra("text", builder.toString());
        intent.putExtra("id", status.getId());
        intent.putExtra("type", StatusUpdateInfo.TYPE_REPOST);
        startUIByAnimation(context, intent);
    }

    public static void doShare(Context context, StatusModel status) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "来自" + status.getUserScreenName()
                + "的饭否消息");
        intent.putExtra(Intent.EXTRA_TEXT, status.getSimpleText());
        context.startActivity(Intent.createChooser(intent, "分享"));
    }

    public static void doReply(Activity context, StatusModel status) {

        if (status != null) {
            StringBuilder sb = new StringBuilder();
            boolean replyToAll = true;
            if (replyToAll) {
                ArrayList<String> names = StatusHelper.getMentions(status);
                for (String name : names) {
                    sb.append("@").append(name).append(" ");
                }
            } else {
                sb.append("@").append(status.getUserScreenName()).append(" ");
            }

            Intent intent = new Intent(context, UIWrite.class);
            intent.putExtra("id", status.getId());
            intent.putExtra("text", sb.toString());
            intent.putExtra("type", StatusUpdateInfo.TYPE_REPLY);
            startUIByAnimation(context, intent);
        } else {
            showWrite(context);
        }

    }

    public static void showRecords(Activity context) {
        Intent intent = new Intent(context, UIRecords.class);
        startUIByAnimation(context, intent);
    }

    public static void showProfile(Activity context, String id) {
        Intent intent = new Intent(context, UIProfile.class);
        intent.putExtra("id", id);
        startUIByAnimation(context, intent);
    }

    public static void showProfile(Activity context, UserModel user) {
        Intent intent = new Intent(context, UIProfile.class);
        intent.putExtra("id", user.getId());
        startUIByAnimation(context, intent);
    }

    public static void showTimeline(Activity context, UserModel user) {
        Intent intent = new Intent(context, UITimeline.class);
        intent.putExtra("data", user);
        startUIByAnimation(context, intent);
    }

    public static void showFavorites(Activity context, UserModel user) {
        Intent intent = new Intent(context, UIFavorites.class);
        intent.putExtra("data", user);
        startUIByAnimation(context, intent);
    }

    public static void showThread(Activity context, String id) {
        Intent intent = new Intent(context, UIThread.class);
        intent.putExtra("id", id);
        startUIByAnimation(context, intent);
    }

    private static void showUserList(Activity context, String id, String name, boolean following) {
        Intent intent = new Intent(context, UIUserList.class);
        intent.putExtra("type", following ? UserModel.TYPE_FRIENDS : UserModel.TYPE_FOLLOWERS);
        intent.putExtra("id", id);
        intent.putExtra("name", name);
        startUIByAnimation(context, intent);
    }

    public static void showFollowing(Activity context, String id, String name) {
        showUserList(context, id, name, true);
    }

    public static void showFollowers(Activity context, String id, String name) {
        showUserList(context, id, name, false);
    }


    public static void showPhoto(Activity context, String url) {
        Intent intent = new Intent(context, UIPhoto.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    public static void showAlbum(Activity context, UserModel user) {
        Intent intent = new Intent(context, UIPhotos.class);
        intent.putExtra("user", user);
        startUIByAnimation(context, intent);
    }


    public static void showSearchResults(Activity context, String query) {
        Intent intent = new Intent(context, UISearchResults.class);
        intent.setAction(Intent.ACTION_SEARCH);
        intent.putExtra(SearchManager.QUERY, query);
        context.startActivity(intent);
    }

    public static void showGallery(Activity context, String userId, int index) {
        Intent intent = new Intent(context, UIGallery.class);
        intent.putExtra("userId", userId);
        intent.putExtra("index", index);
        context.startActivity(intent);
    }

}
