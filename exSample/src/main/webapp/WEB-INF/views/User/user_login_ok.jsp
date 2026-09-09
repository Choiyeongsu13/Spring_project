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
<!-- user 세션 검사 (속도가 빠름)
	<c:if test="${empty sessionScope.user}">
		<script>
			alert("아이디가 없거나 패스워드가 틀렸습니다");
			history.back();
		</script>
	</c:if>
-->



	<c:if test="${empty user}">
		<script>
			alert("아이디가 없거나 패스워드가 틀렸습니다");
			history.back();
		</script>
	</c:if>
	<c:if test="${!empty user}">
		<script type="text/javascript">
		location.href="/";
		</script>
	</c:if>
</body>
</html>