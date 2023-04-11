<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>
	<div>
		<a href="../home/main">메인으로</a>
	</div>
	<h1>로그인</h1>

	<script type="text/javascript">
		function JoinForm__submit(form) {
			var joinForm__submitDone = false;
			
			if (joinForm__submitDone) {
				alert('처리중 입니다');
				return;
			}
			
			var loginId = form.loginId.value.trim();
			var loginPw = form.loginPw.value.trim();
			
			if (loginId.length == 0) {
				alert('아이디를 입력해주세요');
				form.loginId.focus();
				return;
			}
			if (loginPw.length == 0) {
				alert('비밀번호를 입력해주세요');
				form.loginPw.focus();
				return;
			}
			JoinForm__submitDone = true;
			form.submit();
		}
	</script>

	<form action="doLogin" accept-charset="UTF-8" method="post"
		onsubmit="JoinForm__submit(this); return false;">
		<p>아이디</p>
		<p>
			<input autocomplete="off" type="text" name="loginId"
				placeholder="loginId" autofocus/>
		</p>
		<p>비밀번호</p>
		<p>
			<input type="text" name="loginPw" placeholder="loginPw"
				id="loginPw" />
		</p>
		<input type="submit" value="로그인"/>
	</form>
</body>
</html>