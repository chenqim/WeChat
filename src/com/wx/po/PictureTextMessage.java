package com.wx.po;

import java.util.List;

public class PictureTextMessage extends BaseMessage{
	private int ArticleCount;
	private List<PictureText> Articles;
	public int getArticleCount() {
		return ArticleCount;
	}
	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}
	public List<PictureText> getArticles() {
		return Articles;
	}
	public void setArticles(List<PictureText> articles) {
		Articles = articles;
	}
	
	
}
