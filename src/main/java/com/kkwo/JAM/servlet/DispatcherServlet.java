package com.kkwo.JAM.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kkwo.JAM.config.Config;
import com.kkwo.JAM.controller.ArticleController;
import com.kkwo.JAM.controller.MainController;
import com.kkwo.JAM.controller.MemberController;
import com.kkwo.JAM.util.DBUtil;
import com.kkwo.JAM.util.SecSql;

@WebServlet("/s/*")
public class DispatcherServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		// DB 연결
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("예외 : 클래스가 없습니다");
			System.out.println("프로그램을 종료합니다");
			return;
		}

		try {
			conn = DriverManager.getConnection(Config.getDBUrl(), Config.getDBUser(), Config.getDBPassword());
			// 모든 요청에 앞서 무조건 실행
			HttpSession session = request.getSession();
			boolean isLogined = false; // false로 가정
			int loginedMemberId = -1;
			Map<String, Object> memberRow = null;

			if (session.getAttribute("loginedMemberId") != null) {
				isLogined = true;
				loginedMemberId = (int) session.getAttribute("loginedMemberId");

				SecSql sql = SecSql.from("SELECT * FROM `member`");
				sql.append("WHERE id = ?", loginedMemberId);

				memberRow = DBUtil.selectRow(conn, sql);
			}

			request.setAttribute("isLogined", isLogined);
			request.setAttribute("loginedMemberId", loginedMemberId);
			request.setAttribute("memberRow", memberRow);

			String requestUri = request.getRequestURI();

			String[] requestUriBits = requestUri.split("/");
			// 예시 ) 여기 0 / 2023_04_JSP_AM / s / *
			// [0] / [1] / [2] / [3] , 나뉜 단위

			/* 정상적인 길이보다 짧다면 */
			if (requestUriBits.length < 5) {
				response.getWriter().append("올바른 요청이 아닙니다");
				return;
			}

			String controllerName = requestUriBits[3];
			String actionMethodName = requestUriBits[4];

			if (controllerName.equals("home")) {
				MainController mainController = new MainController(request, response, conn);

				if (actionMethodName.equals("main")) {
					mainController.main();
				}
			}

			if (controllerName.equals("article")) {
				ArticleController articleController = new ArticleController(request, response, conn);

				if (actionMethodName.equals("list")) {
					articleController.showList();
				}
				if (actionMethodName.equals("write")) {
					articleController.showWriteForm();
				}
				if (actionMethodName.equals("doWrite")) {
					articleController.doWrite();
				}
				if (actionMethodName.equals("detail")) {
					articleController.showDetail();
				}
				if (actionMethodName.equals("modify")) {
					articleController.showModifyForm();
				}
				if (actionMethodName.equals("doModify")) {
					articleController.doModify();
				}
				if (actionMethodName.equals("doDelete")) {
					articleController.doDelete();
				}
			}

			if (controllerName.equals("member")) {
				MemberController memberController = new MemberController(request, response, conn);

				if (actionMethodName.equals("join")) {
					memberController.showJoinFrom();
				}
				if (actionMethodName.equals("doJoin")) {
					memberController.dpJoin();
				}
				if (actionMethodName.equals("login")) {
					memberController.showLoginForm();
				}
				if (actionMethodName.equals("doLogin")) {
					memberController.doLogin();
				}
				if (actionMethodName.equals("doLogout")) {
					memberController.doLogout();
				}
			}
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}
}
