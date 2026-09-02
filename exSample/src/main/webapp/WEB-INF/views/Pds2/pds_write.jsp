<%@ page contentType="text/html; charset=UTF-8" %>

<%@ include file="/Include/topmenu.jsp" %>

<html>
<head><title> 자료 올리기 </title>
<link rel="stylesheet" type="text/css" href="stylesheet.css">
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

   <script type="text/javascript">
   $(document).ready(function(){
	   $('#addParam').click(function() {
	   	var html = '<tr name="trTest">'+
	        	'<td align="right"> </td>'+
	        	'<td> <font size="2" face="돋움">첨부파일</font></td>'+
	        	'<td><input type="file" value="" size="30" name="filename" /></td>'+
	        	'</tr>';
	        	
	        var trHtml = $("tr[name=trTest]");
	        trHtml.after(html);
	    });
   });
   </script>
</head> 
<body>
<table border="0" width="800">
 <tr>
   <td width="20%" height="500" bgcolor="#ecf1ef" valign="top">

	<!--     다음에 추가할 로그인  -->
	<jsp:include page="/Include/login_form.jsp" /> 

   </td>
   <td width="80%" valign="top">&nbsp;<br>
     <img src="/Images/img/bullet-01.gif"><font size="3" face="돋움" color="blue"> <b>참 좋은 자료실</b></font>
     <font size="2"> - 자료올리기</font><p>
     <img src="/Images/img/bullet-03.gif"><font size="2" face="돋움" color="orange"> 잠깐</font> &nbsp;
     <img src="/Images/img/bullet-02.gif"><font size="2" face="돋움">는 필수 입력 사항입니다.</font><p>
	<div id="pds">
	  <table border="0" >
		<tr >
         <td width="5%" align="right"><img src="/Images/img/bullet-02.gif"></td>
         <td width="15%"><font size="2" face="돋움">글쓴이</font></td>
         <td width="80%">
			<input type="text" size="20" name="name"></td>
		</tr>
		<tr> 
		  <td align="right">&nbsp;</td>
          <td><font size="2" face="돋움">메일주소</font></td>
		  <td><input type="text" size="20" name="email"></td>
		</tr>	
       <tr>
         <td align="right"><img src="/Images/img/bullet-02.gif"></td>
         <td><font size="2" face="돋움">제목</font></td>
         <td><input type="text" size="60" name="subject"></td>
       </tr>
       <tr>
         <td align="right"><img src="/Images/img/bullet-02.gif"></td>
         <td><font size="2" face="돋움">내용</font></td>
         <td><textarea wrap="physical" rows="10" name="contents" cols="60"></textarea></td>
       </tr>
       <tr>
         <td align="right"><img src="/Images/img/bullet-02.gif"></td>
         <td><font size="2" face="돋움">비밀번호</font></td>
          <td><input type="password" size="10" name="pass" > 
          <font size="2" face="돋움">*.게시글의 수정 및 삭제시 필요.</font>
         </td>
        </tr>
		<tr name="trTest">
		  <td align="right"><img src="/Images/img/bullet-02.gif"></td>
		  <td><font size="2" face="돋움">파일첨부</font></td>
		  <td>
		  	<input type="file" name="filename" size="30">
		  	<a id="addParam" href="javascript:void(0)">[파일추가]</a>
		  </td>
		</tr>
		
	</table>
	</div>
	<table>
	   <tr>
          <td align="right">&nbsp;</td>
          <td><font size="2">&nbsp;</font></td>
          <td align=center>
			<img src="/Images/img/purple_save.gif" width="56" height="18" border="0">
			<img src="/Images/img/purple_cancle.gif" width="56" height="18" border="0">
		  </td>
        </tr>
      </table>
      <h3 id="userID_c"> </h3>
    </td>
  </tr>
 </table>
</body>		
</html>
