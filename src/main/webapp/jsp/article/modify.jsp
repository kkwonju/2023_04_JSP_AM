<%@ page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
Map<String, Object> articleRow = (Map<String, Object>) request.getAttribute("articleRow");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 수정</title>
</head>
<body>
	<div>
		<a href="list">목록으로</a>
	</div>
	<h1><%=articleRow.get("id") %>번 게시물 수정</h1>
	<form action="doModify" accept-charset="UTF-8" name="article"
		method='post'>
		<input value="${param.id}" type="hidden" name="id"/>
		<div>
			날짜:
			<%=articleRow.get("regDate")%>,
		</div>
		<div>
			제목:
			<input value="<%=articleRow.get("title")%>" type="text" name="title" autofocus/>
		</div>
		<div>
			내용:
			<textarea name="body"><%=articleRow.get("body")%></textarea>
		</div>
		<button type="submit">수정</button>
		<button type="reset">초기화</button>
	</form>
</body>
</html>