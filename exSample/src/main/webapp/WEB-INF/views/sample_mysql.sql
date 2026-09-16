-- 관리자 테이블
-- 테이블 구조 `tbl_admin`
CREATE TABLE tbl_admin (
  adname varchar(20) NOT NULL ,				--  이름
  adid varchar(20) NOT NULL ,				--  아이디
  adpass varchar(100) NOT NULL ,			--  비밀번호
  addept varchar(20)  NULL ,				--  소속부서
  adtel varchar(20)  NULL ,				--  연락처
  adrole varchar(10) NOT NULL,				--  권한(user,admin,manager)
  adregdate datetime default current_timestamp,		--  등록일자
  adupdate datetime,					--  최근 접록일
  PRIMARY KEY  (adid)
);

-- 회원정보 테이블
-- 테이블 구조 `tbl_user`
create table tbl_user(
   userid varchar(20) primary key,			-- 회원 ID
   name varchar(20) not null,				-- 회원이름
   passwd varchar(100) not null,			-- 비밀번호
   gubun char(1),					-- 인증구분(1:핸드폰/2:email)
   tel varchar(14) not null,				-- 전화(지역/핸드폰)
   email varchar(100),					-- 이메일
   first_time datetime default current_timestamp,	-- 가입날짜
   avatarimg varchar(50)				-- 아바타 이미지
);

-- 회원(관리자포함) 정보 테이블(SpringBoot Security)(
-- 테이블 구조 `tbl_user`
create table tbl_user(
   userid varchar(20) primary key,			-- 회원 ID
   name varchar(20) not null,				-- 회원이름
   passwd varchar(100) not null,			-- 비밀번호
   gubun char(1),					-- 인증구분(1:핸드폰/2:email)
   tel varchar(14) not null,				-- 전화(지역/핸드폰)
   email varchar(100),					-- 이메일
   role varchar(10) NOT NULL,				--  권한(user,admin,manager)
   first_time datetime default current_timestamp,	-- 가입날짜
   avatarimg varchar(50)				-- 아바타 이미지
);

-- 회원관리 테이블
-- 테이블 구조 `tbl_member`
create table tbl_member(
   name varchar(20) not null,				-- 회원이름
   userid varchar(20) primary key,			-- 회원 ID
   passwd varchar(100) not null,			-- 비밀번호
   gubun varchar(6)  null,				-- 주소구분(자택/직장)
   zip varchar(7)  null,				-- 우편번호
   addr1 varchar(100)  null,				-- 나머지주소
   addr2 varchar(100)  null,				-- 나머지주소
   tel varchar(14) not null,				-- 전화(지역/핸드폰)
   email varchar(100),					-- 이메일
   job varchar(20),					-- 직업
   intro varchar(2000),					-- 자기소개
   favorite varchar(255),				-- 관심분야
   first_time datetime default current_timestamp,	-- 가입날짜
   last_time datetime					-- 마지막 로그인날짜
);


-- 공지사항 테이블
-- 테이블 구조 'tbl_notice`
CREATE TABLE tbl_notice (
  idx int NOT NULL auto_increment,			--  고유번호, 자동증가
  adid varchar(20) references tbl_admin ,		--  관리자 아이디
  subject varchar(255) NOT NULL ,			--  제목
  contents varchar(2000) NOT NULL,			--  내용
  regdate datetime default current_timestamp,		--  등록일자
  readcnt int default 0,				--  조회수
  PRIMARY KEY  (idx)
);


-- mySQL --
create table tbl_guest (
   idx int not null auto_increment,			-- 고유번호/자동증가
   name varchar(50) not null,				-- 작성자
   pass varchar(20) not null,				-- 작성자비밀번호
   subject varchar(100) not null,			-- 제목
   contents text not null,				-- 내용
   regdate datetime default current_timestamp,					-- 작성일자
   readcnt int default 0,				-- 조회수
   primary key(idx)					-- 기본키
);

-- 기본형 게시판 테이블
-- 테이블 구조 `tbl_board`
CREATE TABLE tbl_board (
  idx int NOT NULL auto_increment,				--  고유번호, 자동증가
  pass varchar(20) NOT NULL ,			--  비밀번호
  name varchar(20) NOT NULL ,			--  작성자 이름
  email varchar(50) ,				--  작성자 이메일
  regdate datetime default current_timestamp,			--  작성일자
  subject varchar(100) NOT NULL,			--  제목
  contents varchar(2000) NOT NULL,			--  내용
  readcnt int default 0,				--  조회수
  PRIMARY KEY  (idx)
);


-- 회원전용 게시판 테이블
-- 테이블 구조 `tbl_user_board`
CREATE TABLE tbl_board (
  idx int NOT NULL auto_increment,				--  고유번호, 자동증가
  userid varchar2(20) NOT NULL ,			--  회원ID
  subject varchar2(100) NOT NULL,			--  제목
  contents varchar2(2000) NOT NULL,			--  내용
  regdate datetime default current_timestamp,			--  작성일자
  readcnt number default 0,				--  조회수
  PRIMARY KEY  (idx)
);


-- 댓글형 게시판 테이블
-- 테이블 구조 `tbl_boardcomment`
CREATE TABLE tbl_boardcomment (
  idx int NOT NULL auto_increment,				--  고유번호, 자동증가
  pass varchar(20) NOT NULL ,			--  비밀번호
  name varchar(20) NOT NULL ,			--  작성자 이름
  email varchar(50) ,				--  작성자 이메일
  regdate datetime default current_timestamp,			--  작성일자
  subject varchar(100) NOT NULL,			--  제목
  contents varchar(2000) NOT NULL,			--  내용
  readcnt int default 0,				--  조회수
  ip varchar(20) null,				--  작성자 ip주소
  comments int(3) null,				--  댓글 수
  PRIMARY KEY  (idx)
);


-- 댓글 테이블 --
create table tbl_boardcomment_reply(
   idx int(5) primary key auto_increment,				-- 고유번호, 자동증가
   parent int(5) not null,				-- 부모글번호
   name varchar(50) not null,				-- 작성자
   pass varchar(20) not null,				-- 비번
   comments varchar(2000) not null,			-- 댓글
   regdate datetime default current_timestamp			-- 등록일자
);
create sequence tbl_boardcommant_reply_seq_idx;

-- 답변형 게시판 테이블
-- 테이블 구조 `tbl_boardreply`
CREATE TABLE tbl_boardreply (
  idx int NOT NULL auto_increment,				--  고유번호
  pass varchar(20) NOT NULL ,			--  비밀번호
  name varchar(20) NOT NULL ,			--  작성자 이름
  email varchar(50),				--  작성자 이메일
  regdate datetime default current_timestamp,			--  작성일자
  subject varchar(100) NOT NULL,			--  제목
  contents varchar(2000) NOT NULL,			--  내용
  parent int(2) not null,				--  최상위 부모글번호
  realparent int(2) not null,			--  바로 한수준 위의 글번호
  indent int(2) not null,				--  들여쓰기 기준
  depth int(2) not null,				--  답변글의 넘버를 저장하는 필드로 
						--  첫번째 답변글은 1, 두 번째 답변글은 2,
						--  n번째 답변글은 n의 값이 저장
  readcnt int default 0 ,				--  조회수
  ip varchar(20) not null,				--  작성자 ip주소
  PRIMARY KEY  (idx)
);


-- 포토 갤러시 테이블
-- 테이블 구조 'tbl_gallery`
CREATE TABLE tbl_gallery (
  idx int NOT NULL auto_increment,				--  고유번호
  name varchar(20) NOT NULL ,			--  작성자 이름
  pass varchar(20) NOT NULL ,			--  비밀번호
  subject varchar(100) NOT NULL ,			--  제목
  contents varchar(2000),				--  내용
  photo varchar(255) NOT NULL ,			--  사진(다중 파일 업로드)
  regdate datetime default current_timestamp,			--  등록일자
  readcnt number default 0 ,				--  조회수
  PRIMARY KEY  (idx)
);
create sequence tbl_gallery_seq_idx;


-- 포토게시판  테이블(summerenote web editer 사용시)
-- mysql 사용시
-- 테이블 구조 'tbl_boardphoto`
CREATE TABLE tbl_boardphoto (
  idx int NOT NULL auto_increment ,		--  고유번호(자동증가
  name varchar(20) NOT NULL ,			--  작성자 이름
  pass varchar(20) NOT NULL ,			--  비밀번호
  subject varchar(100) NOT NULL ,		--  제목
  contents longtext,				--  내용+이미지(사진)
  regdate datetime default sysdate,			--  등록일자
  readcnt int default 0 ,				--  조회수
  PRIMARY KEY  (idx)
);

-- 파일 업로드용 테이블
-- 테이블 구조 `tbl_pds`
CREATE TABLE tbl_pds (
  idx int NOT NULL auto_increment,				--  고유번호
  name varchar(20) NOT NULL ,			--  작성자 이름
  pass varchar(20) NOT NULL ,			--  비밀번호
  email varchar(100) ,				--  이메일
  subject varchar(100) NOT NULL,			--  제목
  contents varchar(2000) NOT NULL,			--  내용
  regdate datetime default current_timestamp ,			--  작성일자
  readcnt int(2) default 0,				--  조회수
  filename varchar(255) null,				--  파일이름
  PRIMARY KEY  (idx)
);


-- 질문 정보 테이블 테이블
-- questions and answers
-- 테이블 구조 `tbl_qna`

CREATE TABLE tbl_qna (
  q_idx int NOT NULL auto_increment,			-- 기본키
  q_open char(1) NOT NULL ,			-- 공개유무(y/n)(y: 누구나 /  n: 관리자만) 확인가능
  q_gubun varchar(20) NOT NULL,		-- 질문구분
  q_name varchar(20) NOT NULL,		-- 작성자
  q_pass varchar(20) NOT NULL ,		--  비밀번호(회원전용일 경우 생략)
  q_subject varchar(150) NOT NULL,		-- 제목
  q_contents varchar(2000) NOT NULL,		-- 내용
  q_tel varchar(14) ,			-- 연락처
  q_regdate datetime default current_timestamp,		-- 등록일자
  q_readcnt number default 0,			-- 조회수
  q_answer datetime,				-- 답변글 등록일자
  PRIMARY KEY(q_idx)
);


--
-- 답변정보 테이블
-- 하나의 질문에 하나의 답변 또는 하나의 질문에 여러개의 답변
-- 테이블 구조 `tbl_qna_answer`
CREATE TABLE tbl_answer (
  an_idx int NOT NULL auto_increment,			-- 기본키  
  an_name varchar(20) NOT NULL,		-- 답변글 작성자
  an_contents varchar(2000) not null,		-- 답글정보
  an_regdate datetime default current_timestamp,		-- 등록일자
  q_idx int references tbl_qna,		-- 메인글 키번호
  PRIMARY KEY(an_idx)
);


-- 설문조사 테이블
-- 테이블 구조 `tbl_question'
create table tbl_poll (
	idx int not null auto_increment,		-- 고유번호/자동증가
	question varchar(255) not null,	-- 질문 내용을 저장
	reply1 varchar(100) ,		-- 답변1(사용자가 답변할 선택사항 1의 내용)
	reply2 varchar(100) ,		-- 답변2(사용자가 답변할 선택사항 2의 내용)
	reply3 varchar(100) ,		-- 답변3(사용자가 답변할 선택사항 3의 내용)
	reply4 varchar(100) ,		-- 답변4(사용자가 답변할 선택사항 4의 내용)
	replynum1 int ,		-- 답변1(답변1의 응답자 수)
	replynum2 int ,		-- 답변2(답변2의 응답자 수)
	replynum3 int ,		-- 답변3(답변3의 응답자 수)
	replynum4 int ,		-- 답변4(답변4의 응답자 수)
	replytot  int ,			-- 전체 응답자 수
	regdate datetime default current_timestamp,		-- 질문 등록일자
	primary key(idx)			-- 기본키
);


