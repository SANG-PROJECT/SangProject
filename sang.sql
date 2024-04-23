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
drop table category cascade constraints;
drop table member cascade constraints;

-- 회원
create table member (
	id varchar2(20) primary key, 	-- 아이디
	password varchar2(100),			-- 암호
	name varchar2(20),				-- 이름
	tel varchar2(20),				-- 연락처
	email varchar2(50),				-- 이메일
	image varchar2(200),			-- 회원 이미지
	reg_date date,					-- 가입일자
	del char(1) default 'N',		-- 회원삭제
	snt char(1) default 'N',		-- 수강생/강사 구분
	admin char(1) default 'N'		-- 관리자 구분
);
select * from member;

INSERT INTO member (id, password, name, tel, email, image, reg_date, del, snt, admin)
VALUES (
    'user', -- 아이디
    '$2a$10$npapJwUNY/sf4pJVvVRbeOFKxDWV66ZV80VFOUbnTrqkuPDyNmwf.', -- 암호 (해시된 암호로 변경해야 함)
    '홍길동', -- 이름
    '010-1234-5678', -- 연락처
    'user@example.com', -- 이메일
    'user_image.jpg', -- 회원 이미지 파일 경로
    TO_DATE('2024-04-11', 'YYYY-MM-DD'), -- 가입일자 (예시로 현재 날짜로 설정)
    'N', -- 회원 삭제 여부 (기본값 'N')
    'N', -- 수강생/강사 구분 (기본값 'N')
    'N' -- 관리자 구분 (기본값 'N')
);

--카테고리
create table category (
	ct_no number(20) primary key,	--카테고리 번호
	free varchar2(10),				-- 무료특강
	hobby varchar2(10),				-- 취미
	write varchar2(10),				-- 글쓰기
	book varchar2(10),				-- 그림책
	design varchar2(10),			-- 디자인
	media varchar2(10),				-- 미디어
	photo varchar2(10)				-- 사진
);
select * from category;
insert into category values(1,'free','','','','','','');

-- 강의
create table academy (
	a_no number(20) primary key,			-- 강의번호
	title varchar2(200),						-- 제목
	price number(10),						-- 가격
	intro varchar2(200),						-- 소개
	tag varchar2(100),						-- 키워드
	schedule varchar2(100),					-- 일정
	a_name varchar2(100),					-- 강사
	place varchar2(100),						-- 장소
	inwon varchar2(100),						-- 정원
	turn varchar2(100),						-- 회차
	request varchar2(100),					-- 접수
	onoff char(1) default 'N',				-- 온-오프라인 수업 구분
	image varchar2(200),					-- 섬네일
--	detail_img varchar2(200),				-- 상세 이미지
--	detail_txt varchar2(2000),				-- 상세 본문
	count number(10),						-- 수량
	a_date date,							-- 등록일자
	id varchar2(20),						-- 아이디(fk)
	ct_no number(20),						-- 카테고리 번호(fk)
	constraint fk_academy_id foreign key(id) references member(id),
	constraint fk_academy_ct_no foreign key(ct_no) references category(ct_no)
);
select * from academy;

INSERT INTO academy (a_no, title, price, intro, tag, schedule, a_name, place,inwon,turn, request, onoff, image, count, a_date, id, ct_no)
VALUES (
    1,
    '[2024 상상마당 홈커밍데이] 윤승준 사진작가',
    0,
    '윤승준 사진작가의 다양한 시선을 담아 낸 사진 이야기 그리고',
    '#사진 #릴레이특강 #멘토',
    '2024년 4월 26일(금) 19:30-21:00',
    '윤승준',
    '10',
    '3',
    'KT&G',
    '4월 5일(금)',
    'N',
    't1.png',
--    't1.png',
--    '본문',
    8,
    TO_DATE('2024-04-11', 'YYYY-MM-DD'),
    'user',
    1 -- 예시로 첫 번째 카테고리 번호를 사용합니다.
);

delete from academy where a_no = 1;

INSERT INTO academy (a_no, title, price, intro, tag, schedule, a_name, place,inwon,turn, request, onoff, image, count, a_date, id, ct_no)
VALUES (
    2, 
    '[2024 상상마당 홈커밍데이] 윤승준 사진작가2', 
    0,
    '윤승준 사진작가의 다양한 시선을 담아 낸 사진 이야기 그리고', 
    '#사진 #릴레이특강 #멘토', 
    '2024년 4월 26일(금) 19:30-21:00',
    '윤승준',
    '15',
    '4',
    'KT&G ',
    '4월 5일(금)',
    'N', 
    't2.png',
--    't2.png',
--    '본문',
    8,
    TO_DATE('2024-04-11', 'YYYY-MM-DD'),
    'user',
    1
);


-- 장바구니
create table cart (
	c_no number(20) primary key,				-- 장바구니 번호
	c_count number(10),							-- 장바구니 수량
	id varchar2(20),							-- 아이디 (fk)
	a_no number(20),							-- 강의 번호 (fk)
	foreign key(id) references member(id),
	foreign key(a_no) references academy(a_no)
);
insert into cart values(1,1,'user',1);
select * from cart;
select * from cart where id = 'user';

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
insert into buy values(1,sysdate,'n',1,1,'user');
insert into buy values(2,sysdate,'n',2,1,'user');
insert into buy values(3,sysdate,'n',2,1,'3436353406');
select * from buy;
update buy set c_count = 2 where b_no = 1;
update buy set cancle = 'n' where b_no = 2;

-- 찜
create table love (
	l_no number(20) primary key,				-- 찜 번호
	id varchar2(20),							-- 아이디 (fk)
	a_no number(20),							-- 강의 번호 (fk)
	foreign key(id) references member(id),
	foreign key(a_no) references academy(a_no)
);
insert into love values(3,'3436353406',1)
insert into love values(2,'user',2)
select count(*) from Love where id = '3436353406';
select count(*) from qna where id = '3436353406';
select * from love;

-- 리뷰
create table review (
	r_no number(20) primary key,				-- 리뷰 번호
	r_title varchar2(100),						-- 리뷰 제목
	r_image varchar2(200),						-- 리뷰 사진
	r_content varchar2(2000),					-- 리뷰 내용
	r_score number(1),							-- 리뷰 별점
	r_date date,								-- 등록 일자
	id varchar2(20),							-- 아이디 (fk)
	a_no number(20),					 		-- 강의 번호 (fk)
	ct_no number(20),							-- 카테고리 번호(fk)
	foreign key(id) references member(id),
	foreign key(a_no) references academy(a_no),
	foreign key(ct_no) references category(ct_no)
);
select * from review;

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

--본문내용
drop table detail;
create table detail(
	a_no number(20) primary key,
	txt_title1 varchar2(100),
	text1 varchar2(2000),
	text1_img varchar2(200),
	txt_title2 varchar2(100),
	text2 varchar2(2000),
	text2_img varchar2(100),
	txt_title3 varchar2(100),
	text3 varchar2(200),
	text3_img varchar2(200),
	txt_title4 varchar2(100),
	text4 varchar2(2000),
	text4_img varchar2(200),
	txt_title5 varchar2(100),
	text5 varchar2(2000),
	text5_img varchar2(200),
	txt_title6 varchar2(100),
	text6 varchar2(2000),
	text6_img varchar2(200),
	foreign key(a_no) references academy(a_no)
);

--+구매취소내역
create table cancle(
	ca_no number(20) primary key,
	ca_option varchar2(100),
	ca_content varchar2(1000),
	b_no number(20),
	foreign key(b_no) references buy(b_no)
);
select * from cancle;

--+ 1:1문의
drop table QnA;
create table QnA(
	q_no number(20) primary key,
	q_title varchar2(100),
	q_content varchar2(2000),
	q_date Date,
	id varchar2(20),
	foreign key(id) references member(id)
);
select * from QnA;