<%@ page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
int id = (int) request.getAttribute("id");
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
	<h1>게시물 수정</h1>
	<form action="doModify" accept-charset="UTF-8" name="article"
		method='post'>
		<input type="hidden" name="id" value="<%=id %>" />
		<div>
			<label for="title">제목</label>
			<input type="text" name="title" id="title" placeholder="title" />
		</div>
		<div>
			<label for="body">내용</label>
			<textarea type="text" name="body" id="body" placeholder="body"></textarea>
		</div>
		<button type="submit">수정</button>
		<button type="reset">초기화</button>
	</form>
</body>
</html>