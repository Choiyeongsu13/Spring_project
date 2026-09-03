<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../Include/topmenu.jsp" %>

<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
function board_search() {
	var search = $("#searchForm [name=search]").val();
	var key = $("#searchForm [name=key]").val();

	$.post("board_list", { page: 1, search: search, key: key },
		function(html) {
		var $html = $(html);
		$("#boardListResult").html($html.find("#boardListResult").html());
		$("#pageSkipArea").html($html.find("#pageSkipArea").html());
	});
}
</script>

<html>
<head><title>게시판 읽기</title>
<link rel="stylesheet" type="text/css" href="/stylesheet.css">
<style type="text/css">
  a.list {text-decoration:none;color:black;font-size:10pt;}
</style>

</head>
<body bgcolor="#FFFFFF" topmargin="0" leftmargin="0">
<table border="0" width="800">
  <tr>
    <td width="20%" height="500" valign="top" bgcolor="#ecf1ef">

	<!-- 다음에 추가할 부분 -->
	<jsp:include page="../Include/login_form.jsp" /> 
	</td>

	<td width="80%" valign="top">	
		<br>
    <table border="0" cellspacing="1" width="100%" align="center">
      <tr>
        <td colspan="7" align="center" valign="center" height="20">
        <font size="4" face="돋움" color="blue">
        <img src="/Images/img/bullet-01.gif"> <b>자 유 게 시 판</b></font></td></tr>
<tbody id="boardListResult">
      <tr>
        <td colspan="5" align="right" valign="middle" height="20">
		<font size="2" face="고딕">전체 : <b>${totcount}</b>건 - ${page} / ${totpage} Pages</font></td></tr>
 	   <tr bgcolor="e3e9ff">
 	      <td width="10%" align="center" height="20"><font face="돋움" size="2">번 호</font></td>
 	      <td width="50%" align="center" height="20"><font face="돋움" size="2">제 목</font></td>
 	      <td width="15%" align="center" height="20"><font face="돋움" size="2">글쓴이</font></td>
 	      <td width="15%" align="center" height="20"><font face="돋움" size="2">작성일</font></td>
 	      <td width="10%" align="center" height="20"><font face="돋움" size="2">조회수</font></td>
 	   </tr>
<c:forEach var = "board" items="${blist}">
		<tr onMouseOver="style.backgroundColor='#D1EEEE'" onMouseOut="style.backgroundColor=''">
			<td align="center" height="25">
			<font face="돋움" size="2" color="#000000">${listcount}</font></td>
			<td align="left" height="20">&nbsp;
				<font face="돋움" size="2" color="#000000">
				<a class="list" href="/Board/board_view?idx=${board.idx}&page=${page}">${board.subject}</a></td>
					<td align="center" height="20"><font face="돋움" size="2">
					<a class="list" href="mailto:${board.email}">${board.name}</a></font></td>
				<td align="center" height="20"><font face="돋움" size="2">${board.regdate}</font></td>
				<td align="center" height="20"><font face="돋움" size="2" >${board.readcnt}
				</font></td>
		</tr>
		<c:set var = "listcount" value ="${listcount-1}"></c:set>
		 <!-- listcount=listcount-1 -->
		
</c:forEach>
<c:if test="${empty blist}">
	<tr><td colspan="5" align="center" height="30">검색 결과가 없습니다.</td></tr>
</c:if>
</tbody>

	 <div align="center">
        <table width="700" border="0" cellspacing="0" cellpadding="5">
          <tr>&nbsp;</tr><tr>
             <td colspan="5">
                <div id="pageSkipArea" align="center">${pageSkip}</div>
			  </td>
			 </tr>
		</table>

	<table width="600">
		<tr>
			<td width="25%"> &nbsp;</td>
			<td width="50%" align="center">
				<form id="searchForm" name="searchForm" action="/Board/board_list?page=${page}"
					onsubmit="board_search(); return false;">
				<table>
				<!-- 검색어를 이용하여 글제목, 작성자, 글내용 중에 하나를 입력 받아 처리하기 위한 부분 -->
					<tr>
						<td>
							<select name="search">
								<option value="subject" ${pageSearchDTO.search=="subject" ? "selected" : "" } >글제목</option>
								<option value="name" ${pageSearchDTO.search=="name" ? "selected" : "" }>작성자</option>
								<option value="contents" ${pageSearchDTO.search=="contents" ? "selected" : "" }>글내용</option>
							</select>
						</td>
						<td> <input type="text" size=20 name="key" value="${pageSearchDTO.key}"></td>
						<td> <a href="javascript:board_search()"><img src="/Images/img/search2.gif" border="0"></a></td>
					</tr>
				</table>
				</form>
			</td>
			<td width="25%" align="right">
			<a href="/Board/board_write?page=${page}"><img src="/Images/img/write.gif" border="0"></a>
			</td>
		</tr>
	</table>
</body>
</html>

