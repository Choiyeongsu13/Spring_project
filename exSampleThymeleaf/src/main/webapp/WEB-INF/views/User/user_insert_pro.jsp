<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:if test="${row==1}">
		<script>
			alert("회원가입이 완료되었습니다");
			location.href="/User/user_login";
		</script>
	</c:if>
	<c:if test="${row!=1}">
		<script>
			alert("회원가입에 실패했습니다\n\n다시 시도해주세요");
			history.back();
		</script>
	</c:if>

</body>
</html>
