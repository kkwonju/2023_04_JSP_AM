package com.kkwo.JAM.service;

import java.sql.Connection;
import java.util.List;

import com.kkwo.JAM.dao.ArticleDao;
import com.kkwo.JAM.dto.Article;

public class ArticleService {
	private ArticleDao articleDao;
	private Connection conn;

	public ArticleService(Connection conn) {
		this.conn = conn;
		this.articleDao = new ArticleDao(conn);
	}

	public int getItemsInAPage() {
		return 10;
	}

	public int getTotalPage() {
		int itemsInAPage = getItemsInAPage();
		int totalCnt = articleDao.getTotalCnt(); 
		int totalPage = (int) Math.ceil((double) totalCnt / itemsInAPage);
		return totalPage;
	}

	public List<Article> getForPrintArticles(int page) {
		int itemsInAPage = getItemsInAPage();
		int limitFrom = (page - 1) * itemsInAPage;
		List<Article> articles = articleDao.getForPrintArticles(itemsInAPage, limitFrom);
		return articles;
	}
}
