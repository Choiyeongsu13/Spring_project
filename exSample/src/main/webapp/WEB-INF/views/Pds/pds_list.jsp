<%@ page contentType="text/html; charset=UTF-8" %>

<%@ include file="../Include/topmenu.jsp" %>

<html>
   <head>
      <title> 자료실 리스트 보기 </title>
	<link rel="stylesheet" type="text/css" href="stylesheet.css">
	<style type="text/css">
		a.list {text-decoration:none;color:black;font-size:10pt;}
	</style>
   </head> 

<!-- 제목 부분 출력 -->
<body bgcolor="#FFFFFF" topmargin="0" leftmargin="0">
  <table border="0" width="800">
    <tr>
      <td width="20%" height="500" valign="top" bgcolor="#ecf1ef">

		<!--  다음에 추가할 부분 -->
		<jsp:include page="../Include/login_form.jsp" /> 

	</td>

	  <td width="80%" valign="top">	
    <br>
    <table border="0" cellspacing="1" width="100%" align="center">
      <tr>
        <td colspan="7" align="center" valign="center" height="20">
        <font size="4" face="돋움" color="blue">
        <img src="/Images/img/bullet-01.gif"> <b>참 좋은 자료들</b></font></td></tr>
      <tr>
        <td colspan="7" align="right" valign="middle" height="20">
		  <font size="2" face="고딕">전체 : <b>${totcount}</b>건 </font>
		</td>
	  </tr>
	  <tr bgcolor="e3e9ff">
        <td width="7%" align="center" height="20"><font face="돋움" size="2">번호</font></td>
        <td width="32%" align="center" height="20"><font face="돋움" size="2">제목</font></td>
        <td width="35%" align="center" height="20"><font face="돋움" size="2">파일이름 및 크기</font></td>
        <td width="10%" align="center" height="20"><font face="돋움" size="2">올린이</font></td>
        <td width="11%" align="center" height="20"><font face="돋움" size="2">날짜</font></td>
        <td width="5%" align="center" height="20"><font face="돋움" size="2">조회</font></td></tr>

      <tr onMouseOver="style.backgroundColor='#D1EEEE'" onMouseOut="style.backgroundColor=''">
        <td align="center" height="25">
        <font face="돋움" size="2" color="#000000">15</font></td>
				<td align="left" height="20">&nbsp;<font face="돋움" size="2">좋은하루 되세요</font></td>
        <td align="center" height="20"><font face="돋움" size="2">test.zip</td>
				<td align="left" height="20"><font face="돋움" size="2">홍길동</font></td>
				<td align="left" height="20"><font face="돋움" size="2">2007-10-11</font></td>
				<td align="center" height="20"><font face="돋움" size="2">1</font></td> 	      
			</tr>  	   
      <tr onMouseOver="style.backgroundColor='#D1EEEE'" onMouseOut="style.backgroundColor=''">
        <td align="center" height="25">
        <font face="돋움" size="2" color="#000000">14</font></td>
				<td align="left" height="20">&nbsp;<font face="돋움" size="2">우리들의 이야기</font></td>
        <td align="center" height="20"><font face="돋움" size="2">&nbsp;</td>
				<td align="left" height="20"><font face="돋움" size="2">홍길동</font></td>
				<td align="left" height="20"><font face="돋움" size="2">2007-10-09</font></td>
				<td align="center" height="20"><font face="돋움" size="2">3</font></td> 	      
			</tr>  	   
	 <div align="center">
        <table width="700" border="0" cellspacing="0" cellpadding="5">
          <tr>&nbsp;</tr><tr>
             <td colspan="5">        
                <div align="center">[1][2][3]</div>
			  </td>
			 </tr>
		</table>

	<table width="600">
		<tr>
			<td width="25%"> &nbsp;</td>
			<td width="50%" align="center">
				<table>
					<form>	
					<!-- 검색어를 이용하여 글제목, 작성자, 글내용 중에 하나를 입력 받아 처리하기 위한 부분 -->
						<tr>
							<td>
								<select name="search">
									<option value="">글제목</option>
									<option value="">작성자</option>
									<option value="">글내용</option>
								</select>
							</td>
							<td> <input type="text" size=20 name=""></td>
							<td> <a href="#"><img src="/Images/img/search2.gif" border="0"></a></td>
						</tr>
					</form>
				</table>
			</td>
			<td width="25%" align="right">
			<a href="/Pds/pds_write?page=${page}"><img src="/Images/img/write.gif" border="0"></a>
			</td>
		</tr>
	</table>
</body>
</html>

