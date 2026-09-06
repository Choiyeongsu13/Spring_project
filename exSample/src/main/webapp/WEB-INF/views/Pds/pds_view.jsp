<%@ page contentType="text/html; charset=UTF-8" %>

<%@ include file="../Include/topmenu.jsp" %>

<html>
   <head>
      <title> 게시판 내용 보기 </title>
 <link rel="stylesheet" type="text/css" href="/stylesheet.css">
   <style type="text/css">
     td.title { padding:4px; background-color:#e3e9ff }
     td.content { padding:10px; line-height:1.6em; text-align:justify; }
     a.list { text-decoration:none;color:black;font-size:10pt; }
   </style>
 </head>
 <script>
 	function pds_delete(){
 		var url="/Pds/pds_delete?idx=${pds.idx}&page=${page}";
 		window.open(url,"pds_delete","width=350,height=250");
 	}
 	</script>

   <!--DB에서 검색한 자료를 화면에 출력  -->
 <body topmargin="0" leftmargin="0">
 <table border="0" width="800">
   <tr>
     <td width="20%"  height="500" bgcolor="#ecf1ef" valign="top">

		<!--  로그인 폼 추가 -->
		<jsp:include page="../Include/login_form.jsp" /> 

     </td>
     <td width="80%" valign="top">
       &nbsp;<br>
     <table border="0" width="90%" align="center">
       <tr>
         <td colspan="2"><img src="/Images/img/bullet-01.gif"> 
           <font color="blue" size="3">참 좋은 자료실</font><font size="2"> - 자료읽기</font></td>
       </tr>
     </table>
     <p>

     <table border="0" width="90%" align="center" cellspacing="0" style="border-width:1px;border-color:#0066cc;border-style:outset;">
       <tr bgcolor="e3e9ff">
         <td class="title">
           <img src="/Images/img/bullet-04.gif"> <font size="2" face="돋움">
           ${pds.subject}</font>
           </td>
       </tr>
  <tr>  
    <td class="content">
    <p align="right"><font size="2" face="돋움">  
	<a class ="list" href="mailto:${pds.email}">${pds.name}</a> /
	<font size="2" face="돋음"></font> ${pds.regdate} / ${pds.readcnt}번 읽음</font>
    <p>${pds.contents}<p>
	<img src="/Images/img/disk.gif" align="middle" width="22" height="20" border="0">&nbsp;${pds.filename}
	</td>
	</tr>
  </table>
  <p align="center">
  <font size="2">
  <!-- 수정 하기 -->
  <a href="/Pds/pds_modify?idx=${pds.idx}&page=${page}"><img src="/Images/img/edit-1.gif" border="0"></a>&nbsp;&nbsp;&nbsp;&nbsp;
  <!-- 삭제 -->
  <a href="javascript:pds_delete();">
  <img src="/Images/img/del.gif" border="0"></a>&nbsp;&nbsp;&nbsp;&nbsp;
<!-- 목록보기 -->
	<a href="/Pds/pds_list?page=${page}">
  <img src="/Images/img/list-2.gif" border="0"></a></font></td></tr>  
</table>  
</body>  
</html>


</body>
</html>