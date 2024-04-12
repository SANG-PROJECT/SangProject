--실험
select * from member;

-- 테이블 삭제
drop table attend cascade constraints;
drop table staff cascade constraints;
drop table notice cascade constraints;
drop table artist cascade constraints;
drop table review cascade constraints;
drop table love cascade constraints;
drop table buy cascade constraints;
drop table cart cascade constraints;
drop table academy cascade constraints;
drop table member cascade constraints;


-- 회원
create table member (
	id varchar2(20) primary key, 	-- 아이디
	password varchar2(100),			-- 암호 [변경2 100자리 => 암호화]
	name varchar2(20),				-- 이름
	tel varchar2(20),				-- 연락처
	email varchar2(50),				-- 이메일
	image varchar2(200),			-- [변경1 회원이미지 컬럼추가]
	reg_date date,					-- 가입일자
	del char(1) default 'N',		-- 회원삭제
	snt char(1) default 'N',		-- 수강생/강사 구분
	admin char(1) default 'N'		-- 관리자 구분
);
select * from member;
select * from member where email = 'zzx9603@naver.com';
alter table member modify password varchar2(100); -- 변경에따른 암호 수정
select * from member where name = '황재기' and tel = '01077343854';

-- 강의
create table academy (
	a_no number(20) primary key,			-- 강의번호
	title varchar2(100),						-- 제목
	price number(10),						-- 가격
	intro varchar2(100),						-- 소개
	tag varchar2(50),						-- 키워드
	schedule varchar2(50),					-- 일정
	a_name varchar2(20),					-- 강사
	place varchar2(50),						-- 장소
	request varchar2(50),					-- 접수
	onoff char(1) default 'N',				-- 온-오프라인 수업 구분
	image varchar2(200),					-- 섬네일
	detail_img varchar2(200),				-- 상세 이미지
	detail_txt varchar2(2000),				-- 상세 본문
	count number(10),						-- 수량
	a_date date,							-- 등록일자
	id varchar2(20),						-- 아이디(fk)
	constraint fk_academy_id foreign key(id) references member(id)
);
select * from academy;
-- 장바구니
create table cart (
	c_no number(20) primary key,				-- 장바구니 번호
	c_count number(10),							-- 장바구니 수량
	id varchar2(20),							-- 아이디 (fk)
	a_no number(20),							-- 강의 번호 (fk)
	foreign key(id) references member(id),
	foreign key(a_no) references academy(a_no)
);


-- 구매
create table buy (
	b_no number(20) primary key,				-- 구매 번호
	b_date date,								-- 주문 날짜
	cancle char(1) default 'N',					-- 취소
	c_count number(10),							-- 장바구니 수량
	a_no number(20),							-- 강의 번호 (fk)
	id varchar2(20),							-- 아이디 (fk)
	foreign key(id) references member(id),
	foreign key(a_no) references academy(a_no)
);

-- 찜
create table love (
	l_no number(20) primary key,				-- 찜 번호
	id varchar2(20),							-- 아이디 (fk)
	a_no number(20),							-- 강의 번호 (fk)
	foreign key(id) references member(id),
	foreign key(a_no) references academy(a_no)
);

-- 리뷰
create table review (
	r_no number(20) primary key,				-- 리뷰 번호
	r_title varchar2(100),						-- 리뷰 제목
	r_image varchar2(200),						-- 리뷰 사진
	r_content varchar2(2000),					-- 리뷰 내용
	r_score number(1),							-- 리뷰 별점
	r_date date,								-- 등록 일자
	id varchar2(20),							-- 아이디 (fk)
	a_no number(20),							-- 강의 번호 (fk)
	foreign key(id) references member(id),
	foreign key(a_no) references academy(a_no)
);

-- 소식
create table artist (
	ar_no number(20) primary key,				-- 소식(아티스트) 번호
	ar_title varchar2(100),						-- 소식(아티스트) 제목
	ar_image varchar2(200),						-- 소식(아티스트) 사진
	ar_content varchar2(2000),					-- 소식(아티스트) 내용
	ar_date date,								-- 등록 일자
	id varchar2(20),							-- 아이디 (fk)
	foreign key(id) references member(id)
);

-- 공지
create table notice (
	nt_no number(20) primary key,				-- 공지 번호
	nt_title varchar2(100),						-- 공지 제목
	nt_image varchar2(200),						-- 공지 사진
	nt_content varchar2(2000),					-- 공지 내용
	nt_date date,								-- 등록 일자
	id varchar2(20),							-- 아이디 (fk)
	foreign key(id) references member(id)
);

-- 강사 소개
create table staff (
	st_no number(20) primary key,				-- 강사소개 번호
	st_title varchar2(100),						-- 강사소개 제목
	st_image varchar2(200),						-- 강사소개 사진
	st_content varchar2(2000),					-- 강사소개 내용
	st_date date,								-- 등록 일자
	id varchar2(20),							-- 아이디 (fk)
	foreign key(id) references member(id)
);

-- 참여
create table attend (
	at_no number(20) primary key,				-- 참여 번호
	at_title varchar2(100),						-- 참여 제목
	at_image varchar2(200),						-- 참여 사진
	at_content varchar2(2000),					-- 참여 내용
	at_date date,								-- 등록 일자
	id varchar2(20),							-- 아이디 (fk)
	foreign key(id) references member(id)
);
