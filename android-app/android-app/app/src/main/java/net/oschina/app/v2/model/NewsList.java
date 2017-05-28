package net.oschina.app.v2.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.oschina.app.v2.AppException;
import net.oschina.app.v2.utils.StringUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

/**
 * 新闻列表实体类
 *
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class NewsList extends Entity implements ListEntity {

    private static final long serialVersionUID = 1067118838408833362L;

    public static final String NODE_NEWS_COUNT = "newsCount";
    public final static int CATALOG_ALL = 1;
    public final static int CATALOG_INTEGRATION = 2;
    public final static int CATALOG_SOFTWARE = 3;


    private int catalog;
    private int pageSize;
    private int newsCount;
    private List<News> newslist = new ArrayList<News>();

    public int getCatalog() {
        return catalog;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getNewsCount() {
        return newsCount;
    }

    public List<News> getNewslist() {
        return newslist;
    }

    public static NewsList parse(InputStream inputStream) throws IOException,
            AppException {
        NewsList newslist = new NewsList();
        News news = null;
        // 获得XmlPullParser解析器
        XmlPullParser xmlParser = Xml.newPullParser();
        try {
            xmlParser.setInput(inputStream, UTF8);
            // 获得解析到的事件类别，这里有开始文档，结束文档，开始标签，结束标签，文本等等事件。
            int evtType = xmlParser.getEventType();
            // 一直循环，直到文档结束
            while (evtType != XmlPullParser.END_DOCUMENT) {
                String tag = xmlParser.getName();
                switch (evtType) {
                    case XmlPullParser.START_TAG:
                        if (tag.equalsIgnoreCase(NODE_CATALOG)) {
                            newslist.catalog = StringUtils.toInt(
                                    xmlParser.nextText(), 0);
                        } else if (tag.equalsIgnoreCase(NODE_PAGE_SIZE)) {
                            newslist.pageSize = StringUtils.toInt(
                                    xmlParser.nextText(), 0);
                        } else if (tag.equalsIgnoreCase(NODE_NEWS_COUNT)) {
                            newslist.newsCount = StringUtils.toInt(
                                    xmlParser.nextText(), 0);
                        } else if (tag.equalsIgnoreCase(News.NODE_START)) {
                            news = new News();
                        } else if (news != null) {
                            if (tag.equalsIgnoreCase(News.NODE_ID)) {
                                news.id = StringUtils
                                        .toInt(xmlParser.nextText(), 0);
                            } else if (tag.equalsIgnoreCase(News.NODE_BODY)) {
                                news.setBody(xmlParser.nextText());
                            } else if (tag.equalsIgnoreCase(News.NODE_TITLE)) {
                                news.setTitle(xmlParser.nextText());
                            } else if (tag.equalsIgnoreCase(News.NODE_URL)) {
                                news.setUrl(xmlParser.nextText());
                            } else if (tag.equalsIgnoreCase(News.NODE_AUTHOR)) {
                                news.setAuthor(xmlParser.nextText());
                            } else if (tag.equalsIgnoreCase(News.NODE_AUTHORID)) {
                                news.setAuthorId(StringUtils.toInt(
                                        xmlParser.nextText(), 0));
                            } else if (tag.equalsIgnoreCase(News.NODE_COMMENTCOUNT)) {
                                news.setCommentCount(StringUtils.toInt(
                                        xmlParser.nextText(), 0));
                            } else if (tag.equalsIgnoreCase(News.NODE_PUBDATE)) {
                                news.setPubDate(xmlParser.nextText());
                            } else if (tag.equalsIgnoreCase(News.NODE_TYPE)) {
                                news.getNewType().type = StringUtils.toInt(
                                        xmlParser.nextText(), 0);
                            } else if (tag.equalsIgnoreCase(News.NODE_ATTACHMENT)) {
                                news.getNewType().attachment = xmlParser.nextText();
                            } else if (tag.equalsIgnoreCase(News.NODE_AUTHORUID2)) {
                                news.getNewType().authoruid2 = StringUtils.toInt(
                                        xmlParser.nextText(), 0);
                            }
                        } else if (tag.equalsIgnoreCase(Notice.NODE_NOTICE)) {// 通知信息
                            newslist.setNotice(new Notice());
                        } else if (newslist.getNotice() != null) {
                            if (tag.equalsIgnoreCase(Notice.NODE_ATME_COUNT)) {
                                newslist.getNotice().setAtmeCount(
                                        StringUtils.toInt(xmlParser.nextText(), 0));
                            } else if (tag.equalsIgnoreCase(Notice.NODE_MESSAGE_COUNT)) {
                                newslist.getNotice().setMsgCount(
                                        StringUtils.toInt(xmlParser.nextText(), 0));
                            } else if (tag.equalsIgnoreCase(Notice.NODE_REVIEW_COUNT)) {
                                newslist.getNotice().setReviewCount(
                                        StringUtils.toInt(xmlParser.nextText(), 0));
                            } else if (tag.equalsIgnoreCase(Notice.NODE_NEWFANS_COUNT)) {
                                newslist.getNotice().setNewFansCount(
                                        StringUtils.toInt(xmlParser.nextText(), 0));
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        // 如果遇到标签结束，则把对象添加进集合中
                        if (tag.equalsIgnoreCase(News.NODE_START) && news != null) {
                            newslist.getNewslist().add(news);
                            news = null;
                        }
                        break;
                }
                // 如果xml没有结束，则导航到下一个节点
                int a = xmlParser.next();
                evtType = a;
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            throw AppException.xml(e);
        } finally {
            inputStream.close();
        }
        return newslist;
    }

    @Override
    public List<?> getList() {
        return newslist;
    }
}