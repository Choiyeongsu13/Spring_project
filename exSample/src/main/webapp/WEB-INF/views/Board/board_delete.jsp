<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../Include/topmenu.jsp" %>


<script>
	function board_send(){
		if(!board.pass.value.trim()){
			alert("비밀번호를 입력해주세요.");
			board.pass.focus();
			return;
		}
		alert("글을 삭제 합니다")
		board.submit();
	}
</script>


<html>
   <head><title>게시판 삭제</title>
    <link rel="stylesheet" type="text/css" href="/stylesheet.css">

</head>
 <body topmargin="0" leftmargin="0">
 <table border="0" width="800">
 <tr>
   <td width="20%" height="500" bgcolor="#ecf1ef" valign="top">

   <!-- 다음에 추가할 부분 -->
	<jsp:include page="../Include/login_form.jsp" />
   </td>

   <td width="80%" valign="top">&nbsp;<br>
     <img src="/Images/img/bullet-01.gif"><font size="3" face="돋움" color="blue"> <b>반갑습니다</b></font>
     <font size="2"> - 글 삭제</font><p>
     <img src="/Images/img/bullet-02.gif"><font size="2" face="돋움">삭제하려면 비밀번호를 입력해주세요.</font><p>
     <c:if test="${!empty error}">
       <font color="red" size="2">${error}</font><p>
     </c:if>
     <form name="board" method="post" action="/Board/board_delete?page=${page}">
	<input type="hidden" name="idx" value="${idx}">
	  <table border="0">
	   <tr>
         <td align="right"><img src="/Images/img/bullet-02.gif"></td>
         <td><font size="2" face="돋움">비밀번호</font></td>
          <td><input type="password" size="10" name="pass"></td>
        </tr>
        <tr></tr>
		<tr>
          <td align="right">&nbsp;</td>
          <td><font size="2">&nbsp;</font></td>
          <td>
                     <a href="javascript:board_send()"><img src="/Images/img/del.gif" border=0></a>&nbsp;&nbsp;&nbsp;
                     <a href="javascript:history.back()"><img src="/Images/img/cancle.gif" border=0></a>
          </td>
        </tr>
      </table>
      </form>
    </td>
  </tr>
  </table>
  </body>
  </html>
