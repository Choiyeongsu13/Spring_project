<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body><!-- 전달될때는 앞글자가 소문자로 DeptDTO x deptDTO o -->
	부서번호 : ${deptDTO.dno}<br>
	부서이름 : ${deptDTO.dname}<br>
	지역명  : ${deptDTO.loc}<br>
	페이지  : ${page} <br>

</body>
</html>