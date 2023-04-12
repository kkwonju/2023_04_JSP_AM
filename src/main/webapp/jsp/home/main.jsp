<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
boolean isLogined = (boolean) request.getAttribute("isLogined");
int loginedMemberId = (int) request.getAttribute("loginedMemberId");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인 페이지</title>
</head>
<body>
	<h1>Main</h1>
	<%
	if (isLogined) {
	%>
	<p><%=loginedMemberId %>번 회원 로그인 중</p>
	<a href="../member/doLogout">로그아웃</a>
	<%
	}
	%>
	<%
	if (!isLogined) {
	%>
	<a href="../member/login">로그인</a>
	<div>
		<a href="../member/join">회원 가입</a>
	</div>
	<%
	}
	%>

	<div>
		<a href="../article/list">게시물 리스트</a>
	</div>
</body>
</html>