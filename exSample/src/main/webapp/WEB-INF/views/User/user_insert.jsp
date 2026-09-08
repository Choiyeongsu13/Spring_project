<%@ page contentType="text/html; charset=UTF-8" %>

<html>
<head>
<title>회원등록</title>
   <link rel="stylesheet" type="text/css" href="/stylesheet.css">
   <style type="text/css">
     td.title { padding:4px; background-color:#e3e9ff }
     td.content { padding:10px; line-height:1.6em; text-align:justify; }
     a.list { text-decoration:none;color:black;font-size:10pt; }
   </style>

<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script> 
<script type="text/javascript">
$(function(){

	$("#smscheck").hide();
	$("#emailcheck").hide();
	$("#email").hide(); // 숨기기
	
	

	//인증 방법 (핸드폰/이메일) 선택 상태
	var isPhoneMode = $("#mode1").is(":checked");

	//라디오 버튼 선택시
	$("input[name='mode']").change(function() {
		isPhoneMode = $("#mode1").is(":checked");
        if ($("#mode1").is(":checked")) {
            $("#phone").show();
            $("#email").hide();
           	$("#smscheck").hide();
           	$("#emailcheck").hide();
        } else {
            $("#email").show();
            $("#phone").hide();
           	$("#smscheck").hide();
           	$("#emailcheck").hide();
        }
    });
	

	//핸드폰 인증하기 버튼 클릭시
	$("#phoneBtn1").click(function(){
		$("#smscheck").show();

	});

	//이메일 인증하기 버튼 클릭시
	$("#emailBtn1").click(function(){
		$("#emailcheck").show();

	});

	//이메일 체크(직접입력 또는 선택)
	$("#email3").on("change",function(){
		if($("#email3").prop("selectedIndex") !=0 ){
			$('#email2').prop('readonly', true);//읽기 전용으로
			$('#email2').val($("#email3").val());
		}else{
			$('#email2').prop('readonly', false);//읽기 전용 해제
			$('#email2').val('');
		}
	});

	//id 중복검사(Ajax))
	$("#userid").change(function(){
		var userid = $("#userid").val();
		//alert("AAA");
		
		$.ajax({
			url:"/User/user_idCheck",
			type:"post",
			data:{"userid":userid},
			success:function(result){
				if(result==0){
					userID_c.innerHTML="사용 가능한 아이디입니다";
					$("#userid").prop("readonly",true); //읽기전용으로 변경
				}else{
					userID_c.innerHTML="이미 사용중인 아이디입니다";
					$("#userid").val('');
					$("#userid").focus();
				}
			}
		});
	});
	
	//비밀번호 확인
	$("#repasswd").change(function(){
		if($("#passwd").val() == $("#repasswd").val()){
			repasswd_c.innerHTML="확인 되었습니다"
		}else{
			repasswd_c.innerHTML="비밀번호를 다시 입력하세요"
			$("#repasswd").val('');
			$("#repasswd").focus();
		}	
	});
	
	//sms 본인인증 (발송)
	$("#phoneBtn1").click(function(){
		//전화번호 유효성체크
		if($("#tel").val() == ''){
			alert("전화번호를 입력해주세요");
			$("#tel").focus();
		}
		var tel = $("#tel").val();
		$.ajax({
			url:"/User/user_sms",
			type:"post",
			data:{"tel":tel},
			success:function(result){
				phone_c.innerHTML="인증번호가 전송되었습니다";
				$("#usersms").val(result);
			}
		});
		$("#smscheck").show();
	});
	//sms 본인인증 (확인)
	$("#phoneBtn3").click(function(){
		if($("#usersms").val() == $("#resms").val()){
			resms_c.innerHTML="인증되었습니다";
			$("#resms").prop("readonly",true); //읽기전용으로 변경
		}else{
			resms_c.innerHTML="인증번호가 일치하지 않습니다";
			$("#resms").val('');
			$("#resms").focus();
		}
		
	});
	$("#insert").click(function(){

	if($("#name").val().trim() == ""){
		insert_c.innerHTML="이름을 입력해주세요";
	}else if($("#userid").val().trim() == ""){
		insert_c.innerHTML="아이디를 입력해주세요";
	}else if($("#passwd").val().trim() == ""){
		insert_c.innerHTML="비밀번호를 입력해주세요";
	}else if($("#repasswd").val().trim() == ""){
		insert_c.innerHTML="비밀번호 확인을 진행해주세요";
	}else if($("#passwd").val() != $("#repasswd").val()){
		insert_c.innerHTML="비밀번호가 일치하지 않습니다";
	}else if(isPhoneMode && $("#tel").val().trim() == ""){
		insert_c.innerHTML="전화번호 인증을 진행해주세요";
	}else if(isPhoneMode && $("#usersms").val() == ""){
		insert_c.innerHTML="전화번호 인증확인을 진행해주세요";
	}else if(!isPhoneMode && $("#email1").val().trim() == ""){
		insert_c.innerHTML="이메일을 입력해주세요";
	}else if(!isPhoneMode && $("#email2").val().trim() == ""){
		insert_c.innerHTML="이메일 도메인을 입력해주세요";
	}else{
		insert_c.innerHTML="등록이 완료되었습니다";
		$('#user').submit(); //폼 제출
	}
	});
	
	//다시쓰기
	$("#reset_cancle").click(function(){
		user.reset(); 

	});
	
	
	//email 본인인증


}); //$(function()끝


	
</script>
</head>

<body bgcolor="#FFFFFF" LEFTMARGIN=0  TOPMARGIN=0 >
 
 <!-- 탑 메뉴 영역 삽입-->
<%@ include file="../Include/topmenu.jsp" %>

<table border="0" width="800">
<tr>
  <td width="20%"  bgcolor="#ecf1ef" valign="top" style="padding-left:0;">
	
	<!--로그인 영역 삽입-->
	<%@ include file="../Include/login_form.jsp" %>
	
  </td>
  <td width="80%" valign="top">&nbsp;<img src="/Images/img/title1.gif" ><br>    
	<form name="user" id="user" method=post action="user_write">
	<input type="hidden" id="usersms" name="usersms">
	<table border=0 cellpadding=0 cellspacing=0 width=730 valign=top>
		<tr><td align=center><br>                            
			<table cellpadding=0 cellspacing=0 border=0 width=650 align=center>       
				<tr>
					<td bgcolor="#7AAAD5">            
						<table cellpadding=0 cellspacing=0 border=0 width=100%>
							<tr bgcolor=#7AAAD5>
								<td align=left BORDER="0" HSPACE="0" VSPACE="0"><img src="/Images/img/u_b02.gif"></td>
								<td align=center bgcolor="#7AAAD5"><FONT COLOR="#FFFFFF"><b>사용자등록&nbsp;</b><font color=black>(</font><font color=red>&nbsp;*&nbsp;</font><font color=black>표시항목은 반드시 입력하십시요.)</font></FONT></td>
								<td align=right BORDER="0" HSPACE="0" VSPACE="0"><img src="/Images/img/u_b03.gif"></td>
							</tr>
						</table>
						<table cellpadding=3 cellspacing=1 border=0 width=100%>
							<tr>
								<td width=110 bgcolor=#EFF4F8>&nbsp;회원 성명<font color=red>&nbsp;*</font></td>
								<TD BGCOLOR=WHITE>
									<input type=text id=name name=name size=16 maxlength=20 value="" placeholder="성명은 빈칸없이 입력하세요.">
								</td>
							</tr>
							<tr>
								<TD BGCOLOR="#EFF4F8">&nbsp;회원 ID<font color=red>&nbsp;*</font></td>
								<TD BGCOLOR=WHITE>
									<table cellspacing=0 cellpadding=0>
										<tr>
											<td align=absmiddle>
												<input type=text id=userid name=userid size=12 maxlength=16 value="" style="width:120">
											</td>
											<td id="userID_c">
                  								[ 5~16자 이내의 영문이나 숫자만 가능합니다. ]
                  							</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								<TD BGCOLOR="#EFF4F8">&nbsp;비밀번호<font color=red>&nbsp;*</font></td>
								<TD BGCOLOR=WHITE>
								<input type=password id=passwd name=passwd size=8 maxlength=12 style="width:80">
									6~12자 이내의 영문이나 숫자만 가능합니다.
								</td>
							</tr>
							<tr>
								<TD BGCOLOR="#EFF4F8">&nbsp;비밀번호확인<font color=red>&nbsp;*</font></td>
								<TD BGCOLOR=WHITE><input type=password id=repasswd name=repasswd size=8 maxlength=12 value="" style="width:80">
									<font id=repasswd_c color=red>&nbsp;*비밀번호 확인을 위해서 비밀번호를 한번 더 입력해주세요. </font> 
								</td>
							</tr>
							<tr>
								<TD BGCOLOR="#EFF4F8">&nbsp;인증 방법 선택<font color=red>&nbsp;*</font></td>
								<TD BGCOLOR=WHITE><input type=radio id="mode1" name=mode value="핸드폰" checked>핸드폰
									<input type=radio id="mode2" name="mode" value="이메일" >이메일
								</td>
							</tr>
							<tr id="phone">
								<TD BGCOLOR="#EFF4F8">&nbsp;전화번호<font color=red>&nbsp;*</font></td>
								<TD BGCOLOR=WHITE>
									<input type=text id=tel name=tel size=13 maxlength=13 value="" placeholder="휴대전화번호 (-제외)">
									<input type="button" id="phoneBtn1" value="인증번호받기">
									<font id="phone_c" size="2" color="red">&nbsp;</font>
								</td>
							</tr>
							<tr id="smscheck">
								<TD BGCOLOR="#EFF4F8">&nbsp;인증번호<font color=red>&nbsp;*</font></td>
								<TD BGCOLOR=WHITE>
									<input type=text id=resms name="resms" size=13 maxlength=13 placeholder="인증번호를 입력하세요">
									<input type="button" id="phoneBtn2" value="재발송">
                    				<font id="resms_r" size="2" color="red">&nbsp;</font>
                    					
                    				<input type="button" value="인증" id="phoneBtn3" >
                    				<font id="resms_c" size="2" color="red">&nbsp;</font>
								</td>
							</tr>			
							<tr id="email">
								<TD BGCOLOR="#EFF4F8">&nbsp;E-mail
                					<font color=red>&nbsp;</font>
								</td>
								<td bgcolor=WHITE valign=middle>
									<input type="text" id="email1" name="email" size=13 maxlength="15">
									@ <input type="text" id="email2" name="email2" size=13 maxlength="15">
									<select id="email3" name="email3">
		      							<option value="0">직접입력</option>
		      							<option value="naver.com">naver.com</option>
		      							<option value="daum.net">daum.net</option>
		      							<option value="nate.com">nate.com</option>
		      							<option value="gmail.com">gmail.com</option>
		  							   </select>
									 <input type="button" id="emailBtn1" value="인증하기">
								</td>
							</tr>
							<tr id="emailcheck">
								<TD BGCOLOR="#EFF4F8">&nbsp;이메일 인증번호<font color=red>&nbsp;*</font></td>
								<TD BGCOLOR=WHITE>
									<input type=text id=reemail name="reemail" size=13 maxlength=13 placeholder="이메일 인증번호를 입력하세요">
									<input type="button" id="emailBtn2" value="재발송">
                    				<font id="reemail_r" size="2" color="red">&nbsp;</font>
                    				<input type="button" value="인증" id="emailBtn3">
                    				<font id="reemail_c" size="2" color="red">&nbsp;</font>
								</td>
							</tr>							
						</table>
						
						<table cellpadding=0 cellspacing=0 border=0 width=100%>
					
							<tr bgcolor=#7AAAD5>
								<td valign=bottom>
									<img src="/Images/img/u_b04.gif" align=left hspace=0 vspace=0 border=0>
								</td>
								<td align=center></td>
								<td valign=bottom>
									<img src="/Images/img/u_b05.gif" align=right hspace=0 vspace=0 border=0>
								</td>
							</tr>
							 
							<tr bgcolor=#ffffff>
								<td colspan=3 align=center>
									<img src="/Images/img/u_bt06.gif" vspace=3 border=0 name=img3 id="insert" style="cursor:pointer">
									<img src="/Images/img/u_bt05.gif" border=0 hspace=10 vspace=3 id="reset_cancle" name=img4 style="cursor:pointer">
									<br><font id="insert_c" size="2" color="red">&nbsp;</font>
										<font id="reset_c" size="2" color="red">&nbsp;</font>
								</td>
							</tr>
							
						</table>
						
					</td>
				</tr>
				</td>
			</tr>
		</table>
	</form>
	</td>
</tr>
</table>

 <!-- copyright 영역 삽입-->
  

</body>
</html>
