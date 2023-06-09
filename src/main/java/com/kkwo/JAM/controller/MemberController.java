package com.kkwo.JAM.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kkwo.JAM.util.DBUtil;
import com.kkwo.JAM.util.SecSql;

public class MemberController {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Connection conn;

	public MemberController(HttpServletRequest request, HttpServletResponse response, Connection conn) {
		this.request = request;
		this.response = response;
		this.conn = conn;
	}
	
	public void showJoinFrom() throws ServletException, IOException {
		request.getRequestDispatcher("/jsp/member/join.jsp").forward(request, response);
	}

	public void dpJoin() throws ServletException, IOException {
		String loginId = request.getParameter("loginId");
		String loginPw = request.getParameter("loginPw");
		String name = request.getParameter("name");

		SecSql sql = SecSql.from("SELECT COUNT(*) FROM `member`");
		sql.append("WHERE loginId = ?", loginId);
		int cnt = DBUtil.selectRowIntValue(conn, sql);
		
		if (cnt != 0) {
			response.getWriter().append(String
					.format("<script>alert('이미 사용중인 아이디입니다');location.replace('join');</script>"));
		}

		sql = SecSql.from("INSERT INTO `member`");
		sql.append("SET regDate = NOW()");
		sql.append(", loginId = ?", loginId);
		sql.append(", loginPw = ?", loginPw);
		sql.append(", `name` = ?;", name);

		int id = DBUtil.insert(conn, sql);

		response.getWriter().append(
				String.format("<script>alert('%s님 가입되었습니다');location.replace('../home/main');</script>", loginId));
	}
	
	public void showLoginForm() throws ServletException, IOException {
		request.getRequestDispatcher("/jsp/member/login.jsp").forward(request, response);
	}
	
	public void doLogin() throws ServletException, IOException {
		String loginId = request.getParameter("loginId");
		String loginPw = request.getParameter("loginPw");

		SecSql sql = SecSql.from("SELECT * FROM `member`");
		sql.append("WHERE loginId = ?", loginId);
		Map<String, Object> memberRow = DBUtil.selectRow(conn, sql);
		if (memberRow.isEmpty()) {
			response.getWriter().append(String
					.format("<script>alert('%s 일치하는 회원이 없습니다');location.replace('login');</script>", loginId));
			return;
		}
		if (!memberRow.get("loginPw").equals(loginPw)) {
			response.getWriter()
					.append(String.format("<script>alert('비밀번호가 틀렸습니다');location.replace('login');</script>"));
			return;
		}

		HttpSession session = request.getSession();
		session.setAttribute("loginedMemberId", memberRow.get("id"));
		session.setAttribute("loginedMemberLoginId", memberRow.get("loginId"));

		response.getWriter().append(String.format("<script>alert('%s님 환영합니다');location.replace('../home/main');</script>",
				memberRow.get("name")));
	}

	
	public void doLogout() throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("loginedMemberId") == null) {
			response.getWriter().append(
					String.format("<script>alert('로그인 후 이용해주세요');location.replace('../member/login');</script>"));
			return;
		}
		session.removeAttribute("loginedMemberId");
		session.removeAttribute("loginedMemberLoginId");

		response.getWriter().append(String.format(
				"<script>alert('로그아웃되었습니다');location.replace('../home/main');</script>"));
	}


}
