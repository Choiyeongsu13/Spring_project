<%@ page contentType="text/html; charset=UTF-8" %>

<%@ include file="../Include/topmenu.jsp" %>

<html>
   <head><title>게시판 작성</title>
    <link rel="stylesheet" type="text/css" href="/stylesheet.css">
<script>
	function board_send(){
		alert("글을 수정합니다")
		pds.submit();
	}
</script>
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
     <font size="2"> - 글 수정</font><p>
     <img src="/Images/img/bullet-03.gif"><font size="2" face="돋움" color="orange"> 잠깐</font> &nbsp;
     <img src="/Images/img/bullet-02.gif"><font size="2" face="돋움">는 필수 입력 사항입니다.</font><p>
     <form name="pds" method="post" action="/Pds/pds_modify" enctype="multipart/form-data">
     <input type="hidden" name="idx" value="${pds.idx}">
     <input type="hidden" name="page" value="${page}">
     <input type="hidden" name="oldfilename" value="${pds.filename}">

	  <table border="0">
       <tr>
         <td width="5%" align="right"><img src="/Images/img/bullet-02.gif"></td>
         <td width="15%"><font size="2" face="돋움">글쓴이</font></td>
         <td width="80%">
         <input type="text" size="20" name="name" value="${pds.name}" readonly></td>
       </tr>
       <tr>
         <td align="right">&nbsp;</td>
         <td ><font size="2" face="돋움">메일주소</font></td>
         <td>
          <input type="text" size="20" name="email" value="${pds.email}"></td>
       </tr>
	   <tr>
         <td align="right"><img src="/Images/img/bullet-02.gif"></td>
         <td><font size="2" face="돋움">제목</font></td>
         <td><input type="text" size="60" name="subject" value="${pds.subject}"></td>
       </tr>
       <tr>
         <td align="right"><img src="/Images/img/bullet-02.gif"></td>
         <td><font size="2" face="돋움">내용</font></td>
         <td><textarea wrap="physical" rows="10" name="contents" cols="60">${pds.contents}</textarea></td>
       </tr>
	   <tr>
         <td align="right"><img src="/Images/img/bullet-02.gif"></td>
         <td><font size="2" face="돋움">비밀번호</font></td>
          <td><input type="password" size="10" name="pass" ><font size="2" face="돋움">*.수정과 삭제시 꼭 입력하셔야 합니다.</font></td>
        </tr>
        <tr>
          <td align="right">&nbsp;</td>
          <td><font size="2" face="돋움">첨부파일</font></td>
          <td>
            <font size="2" face="돋움">현재파일 : ${pds.filename}</font><br>
            <input type="file" name="upfile">
          </td>
        </tr>
		<tr>
          <td align="right">&nbsp;</td>
          <td><font size="2">&nbsp;</font></td>
          <td>
                     <a href="javascript:board_send()"><img src="/Images/img/save.gif" border=0></a>&nbsp;&nbsp;&nbsp;
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