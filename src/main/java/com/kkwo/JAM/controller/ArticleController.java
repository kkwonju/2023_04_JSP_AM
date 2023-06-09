package com.kkwo.JAM.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kkwo.JAM.dto.Article;
import com.kkwo.JAM.service.ArticleService;
import com.kkwo.JAM.util.DBUtil;
import com.kkwo.JAM.util.SecSql;

public class ArticleController {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Connection conn;
	private ArticleService articleService;

	public ArticleController(HttpServletRequest request, HttpServletResponse response, Connection conn) {
		this.request = request;
		this.response = response;
		this.conn = conn;
		this.articleService = new ArticleService(conn);
	}

	public void showList() throws ServletException, IOException {
		int page = 1;
		if (request.getParameter("page") != null && request.getParameter("page").length() != 0) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		int itemsInAPage = articleService.getItemsInAPage();
		int totalPage = articleService.getTotalPage();

		List<Article> articles = articleService.getForPrintArticles(page);

		request.setAttribute("articles", articles);
		request.setAttribute("page", page);
		request.setAttribute("totalPage", totalPage);
		request.getRequestDispatcher("/jsp/article/list.jsp").forward(request, response);
	}

	public void showWriteForm() throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("loginedMemberId") == null) {
			response.getWriter().append(String.format("<script>alert('로그인 후 이용해주세요');history.back();</script>"));
			return;
		}

		request.setAttribute("loginedMemberId", session.getAttribute("loginedMemberId"));
		request.getRequestDispatcher("/jsp/article/write.jsp").forward(request, response);
	}

	public void doWrite() throws ServletException, IOException {
		String title = request.getParameter("title");
		String body = request.getParameter("body");
		int memberId = (int) Integer.parseInt(request.getParameter("memberId"));

		SecSql sql = SecSql.from("INSERT INTO article");
		sql.append("SET regDate = NOW()");
		sql.append(", memberId = ?", memberId);
		sql.append(", title = ?", title);
		sql.append(", `body` = ?;", body);

		int id = DBUtil.insert(conn, sql);

		response.getWriter()
				.append(String.format("<script>alert('%d번 글이 생성되었습니다');location.replace('list');</script>", id));
	}

	public void showDetail() throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));

		SecSql sql = SecSql.from("SELECT a.*,m.name AS 'writer'");
		sql.append("FROM article AS a");
		sql.append("INNER JOIN `member` AS m");
		sql.append("ON a.memberId = m.id");
		sql.append("WHERE a.id = ?;", id);

		Map<String, Object> articleRow = DBUtil.selectRow(conn, sql);

		response.getWriter().append(articleRow.toString());

		request.setAttribute("articleRow", articleRow);
		request.getRequestDispatcher("/jsp/article/detail.jsp").forward(request, response);
	}

	public void showModifyForm() throws ServletException, IOException {
		HttpSession session = request.getSession();

		if (session.getAttribute("loginedMemberId") == null) {
			response.getWriter().append(
					String.format("<script>alert('로그인 후 이용해주세요');location.replace('../member/login');</script>"));
			return;
		}
		int memberId = (int) session.getAttribute("loginedMemberId");
		int id = Integer.parseInt(request.getParameter("id"));

		SecSql sql = SecSql.from("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);

		Map<String, Object> articleRow = DBUtil.selectRow(conn, sql);
		if (!articleRow.get("memberId").equals(memberId)) {
			response.getWriter().append(String.format("<script>alert('수정 권한이 없습니다');history.back();</script>"));
			return;
		}

		sql = SecSql.from("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE id = ?;", id);

		articleRow = DBUtil.selectRow(conn, sql);

		response.getWriter().append(articleRow.toString());

		request.setAttribute("articleRow", articleRow);
		request.getRequestDispatcher("/jsp/article/modify.jsp").forward(request, response);
	}

	public void doModify() throws ServletException, IOException {
		HttpSession session = request.getSession();

		if (session.getAttribute("loginedMemberId") == null) {
			response.getWriter().append(
					String.format("<script>alert('로그인 후 이용해주세요');location.replace('../member/login');</script>"));
			return;
		}

		int memberId = (int) session.getAttribute("loginedMemberId");
		int id = Integer.parseInt(request.getParameter("id"));

		SecSql sql = SecSql.from("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);

		Map<String, Object> articleRow = DBUtil.selectRow(conn, sql);
		if (!articleRow.get("memberId").equals(memberId)) {
			response.getWriter().append(String.format("<script>alert('수정 권한이 없습니다');history.back();</script>"));
			return;
		}

		String title = request.getParameter("title");
		String body = request.getParameter("body");

		sql = SecSql.from("UPDATE article");
		sql.append("SET regDate = NOW()");
		sql.append(", title = ?", title);
		sql.append(", `body` = ?", body);
		sql.append("WHERE id = ?;", id);

		DBUtil.update(conn, sql);

		response.getWriter().append(
				String.format("<script>alert('%d번 글이 수정되었습니다');location.replace('detail?id=%s');</script>", id, id));
	}

	public void doDelete() throws ServletException, IOException {
		HttpSession session = request.getSession();

		if (session.getAttribute("loginedMemberId") == null) {
			response.getWriter().append(
					String.format("<script>alert('로그인 후 이용해주세요');location.replace('../member/login');</script>"));
			return;
		}
		int memberId = (int) session.getAttribute("loginedMemberId");
		int id = Integer.parseInt(request.getParameter("id"));

		SecSql sql = SecSql.from("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);

		Map<String, Object> articleRow = DBUtil.selectRow(conn, sql);
		if (!articleRow.get("memberId").equals(memberId)) {
			response.getWriter().append(String.format("<script>alert('삭제 권한이 없습니다');history.back();</script>"));
			return;
		}

		sql = SecSql.from("DELETE ");
		sql.append("FROM article");
		sql.append("WHERE id =?", id);

		DBUtil.delete(conn, sql);

		response.getWriter()
				.append(String.format("<script>alert('%d번 글이 삭제되었습니다');location.replace('list');</script>", id));
	}

}
