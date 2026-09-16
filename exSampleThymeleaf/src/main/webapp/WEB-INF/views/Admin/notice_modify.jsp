<%@ page contentType="text/html; charset=UTF-8" %>

<html>
<head>
<title>공지사항 관리 - 관리자페이지</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
<!--
body,td,tr,table{font-size:9pt; font-family:tahoma;color:#666666;line-height:160%;}

A:link {font-family:tahoma;font-size:9pt;color:#666666;text-decoration:none;}
A:visited {font-family:tahoma;font-size:9pt;color:#666666;text-decoration:none;}
A:active {font-family:tahoma;font-size:9pt;color:#666666;text-decoration:none;}
A:hover {font-family:tahoma;font-size:9pt;color:#009900;text-decoration:underline;}
-->
</style>
</head>

<script>
	function notice_modify_send(){
		if(!notice.subject.value){
			alert("제목을 입력하세요");
			notice.subject.focus();
			return;
		}
		notice.submit();
	}
</script>

<body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr><td><jsp:include page="top_menu.jsp" flush="true" /></td></tr>
	<tr>
		<td align="center" height="100%" valign=middle><br>
			<table width="30%" border="1" cellspacing="0" cellpadding="3" bgcolor="#FFCC66" bordercolor="#FFFFFF" bordercolorlight="#000000">
				<tr>
					<td height=40 align="center" style="font-size: 15px;"><b>공지사항 수정</b></a>
					</b></td>
				</tr>
			</table><br>
			<form name="notice" method="post" action="/Admin/Notice/notice_modify">
			<input type="hidden" name="idx" value="${notice.idx}">
			<input type="hidden" name="page" value="${page}">
			<table width="60%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td><table width="100%" border="0" cellpadding="6" cellspacing="1" bgcolor="DDDDDD">
							<tr>
								<td width="20%" align="center" bgcolor="EcECEC"><strong>제목</strong></td>
								<td colspan=3 bgcolor="ffffff"><input type="text" size="70" name="subject" value="${notice.subject}"></td>
							</tr>
							<tr bgcolor="EcECEC">
								<td align="center" bgcolor="EcECEC"><strong>내용</strong></td>
								<td colspan=3 bgcolor="ffffff"><textarea name="contents" rows="10" cols="70">${notice.contents}</textarea></td>
							</tr>
						</table>
					</td>
				</tr>
			</table><br>
			<table width="60%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align=center>
						<a href="javascript:notice_modify_send()"><b>[수정]</b></a>&nbsp;
						<a href="/Admin/Notice/notice_view?page=${page}&idx=${notice.idx}"><b>[취소]</b></a>
					</td>
				</tr>
			</table>
			</form>
		</td>
	</tr>
</table>
</body>
</html>
