package com.kkwo.JAM.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kkwo.JAM.util.DBUtil;
import com.kkwo.JAM.util.SecSql;

@WebServlet("/article/doWrite")
public class ArticleDoWriteServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");

		// DB 연결
		String url = "jdbc:mysql://127.0.0.1:3306/JSPAM?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
		String user = "root";
		String password = "";

		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("예외 : 클래스가 없습니다");
			System.out.println("프로그램을 종료합니다");
			return;
		}

		try {
			conn = DriverManager.getConnection(url, user, password);
			
			request.getRequestDispatcher("/jsp/article/doWrite.jsp").forward(request, response);
			
			String title = request.getParameter("title");
			String body = request.getParameter("body");
			
			SecSql sql = SecSql.from("INSERT INTO article");
			sql.append("SET regDate = NOW()");
			sql.append(", title = ?", title);
			sql.append(", `body` = ?;", body);
			
			int id = DBUtil.insert(conn, sql);
			
			response.getWriter().append(String.format("<script>alert('%d번 글이 생성되었습니다');location.replace('list');</script>", id));
			
	
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}