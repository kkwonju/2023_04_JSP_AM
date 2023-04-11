<%@ page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 작성</title>
</head>
<body>
	<div>
		<a href="list">뒤로 가기</a>
	</div>
	<h1>게시물 작성</h1>

	<form action="doWrite" accept-charset="UTF-8" name="article"
		method='post'>
		<div>
			<label for="title">제목</label>
			<input type="text" name="title" id="title" placeholder="title" autofocus/>
		</div>
		<div>
			<label for="body">내용</label>
			<textarea name="body" id="body" placeholder="body"></textarea>
		</div>
		<button type="submit">등록</button>
		<button type="reset">초기화</button>
	</form>
</body>
</html>