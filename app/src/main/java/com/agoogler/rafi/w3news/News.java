package com.agoogler.rafi.w3news;

/**
 * Created by rafi on 6/10/17.
 */

public class News {
    private String author;
    private String title;
    private String desc;
    private String url;
    private String urlToImage;
    private String publishedAt;

    public News( String mauthor, String mtitle, String mdesc, String mUrl, String murlToimage, String mpublisedAt)
    {
        author = mauthor;
        title = mtitle;
        desc = mdesc;
        url = mUrl;
        urlToImage = murlToimage;
        publishedAt = mpublisedAt;

    }

    String getAuthor()
    {
        return author;
    }

    String getTitle()
    {
        return title;
    }

    String getDesc()
    {
        return desc;
    }

    String getUrl()
    {
        return url;
    }

    String getUrlToImage()
    {
        return urlToImage;
    }

    String getPublishedAt()
    {
        return publishedAt;
    }

}
