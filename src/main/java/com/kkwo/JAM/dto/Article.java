package com.kkwo.JAM.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class Article {
	public int id;
	public LocalDateTime regDate;
	public String title;
	public String body;
	public int memberId;
	
	public String extra__writer;

	public Article(Map<String, Object> articleRow) {
		this.id = (int) articleRow.get("id");
		this.regDate = (LocalDateTime) articleRow.get("regDate");
		this.title = (String) articleRow.get("title");
		this.body = (String) articleRow.get("body");
		this.memberId = (int) articleRow.get("memberId");
		
		if(articleRow.get("extra__writer") != null) {
			this.extra__writer = (String) articleRow.get("extra__writer");
		}
	}
}
