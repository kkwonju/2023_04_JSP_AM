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
		<a href="../home/main">메인으로</a>
	</div>
	<h1>회원가입</h1>

	<script type="text/javascript">
		function JoinForm__submit(form) {
			var joinForm__submitDone = false;
			
			if (joinForm__submitDone) {
				alert('처리중 입니다');
				return;
			}
			
			var loginId = form.loginId.value.trim();
			var loginPw = form.loginPw.value.trim();
			var loginPwConfirm = form.loginPwConfirm.value.trim();
			var name = form.name.value.trim();
			
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
			if (loginPwConfirm.length == 0) {
				alert('비밀번호 확인을 입력해주세요');
				form.loginPwConfirm.focus();
				return;
			}
			if (loginPw != loginPwConfirm) {
				alert('비밀번호가 일치하지 않습니다');
				form.loginPw.focus();
				return;
			}
			if (name.length == 0) {
				alert('이름을 입력해주세요');
				form.name.focus();
				return;
			}
			JoinForm__submitDone = true;
			form.submit();
		}
	</script>

	<form action="doJoin" accept-charset="UTF-8" method="post"
		onsubmit="JoinForm__submit(this); return false;">
		<p>아이디</p>
		<p>
			<input autocomplete="off" type="text" name="loginId"
				placeholder="loginId" />
		</p>
		<p>비밀번호</p>
		<p>
			<input type="password" name="loginPw" placeholder="loginPw"
				id="loginPw" />
		</p>
		<p>비밀번호 확인</p>
		<p>
			<input type="password" placeholder="loginPwConfirm"
				id="loginPwConfirm" />
		</p>
		<p>이름</p>
		<p>
			<input type="text" name="name" placeholder="name" />
		</p>
		<p>
			<input type="submit" value="register" />
		</p>
	</form>
</body>
</html>