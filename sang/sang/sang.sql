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
alter table member modify password varchar2(100); -- 변경에 따른 암호 수정
select * from member;
insert into member values('ac1','1234','test','010-1111-1111','1@1.com','',sysdate,'N','Y','N');
insert into member values('ac2','1234','test','010-1111-1111','1@1.com','',sysdate,'N','Y','N');
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
insert into category values(2,'','hobby','','','','','');
insert into category values(3,'','','write','','','','');
insert into category values(4,'','','','book','','','');
insert into category values(5,'','','','','design','','');
insert into category values(6,'','','','','','media','');
insert into category values(7,'','','','','','','photo');

-- 강의
create table academy (
	a_no number(20) primary key,			-- 강의번호
	title varchar2(200),					-- 제목
	price number(10),						-- 가격
	intro varchar2(200),					-- 소개
	tag varchar2(100),						-- 키워드
	schedule varchar2(100),					-- 일정
	a_name varchar2(100),					-- 강사
	place varchar2(100),					-- 장소
	inwon varchar2(100),					-- 정원
	turn varchar2(100),						-- 회차
	request varchar2(100),					-- 접수
	onoff char(1) default 'N',				-- 온-오프라인 수업 구분
	image varchar2(200),					-- 섬네일
	count number(10),						-- 수량
	a_date date,							-- 등록일자
	id varchar2(20),						-- 아이디(fk)
	ct_no number(20),						-- 카테고리 번호(fk)
	view_couont number(10),					-- 조회수
	status char(1) default 'N',				-- 대기접수중
	constraint fk_academy_id foreign key(id) references member(id),
	constraint fk_academy_ct_no foreign key(ct_no) references category(ct_no)
);
select * from academy;
--무료특강
insert into academy values(1,'[연말특강] 알고 나서는 모른 체할 수 없는 것들',0,'다양성에 대해 생각해 보고 시야를 넓힐 수 있는 무료 세미나입니다.','free','2022. 11. 19 ~ 12. 3 (매주 토, 3회차)','김지우 작가','','','하단 "세미나 특징"','','Y','ac1','ac1-detail','웅앵웅',100,sysdate,'ac1',1,'','N');
insert into academy values(2,'[소셜 살롱] 잔다리 비스타 소셜 클럽',0,'퇴근 후엔 잔다리로 모이세요. 준비물은 열린 마음과 수다 떨 열정. 단지 그뿐입니다. ','#소셜살롱 #소셜클럽 #취향 #살롱 #미라클나이트 #주류 #음악','2022. 12. 15 ~ 12. 23 (매주 목, 또는 금)','김혜경 작가, "명품맥덕" 유튜버, 조미쉘 A&R',
'[주류 비주류 클럽]  zoom 온라인 /[Friday Listening Club] 상상마당시네마스위트룸','12명 (각 클럽 별)','','','Y','ac2','ac2-detail','웅앵웅',100,sysdate,'ac1',1,'','N');
insert into academy values(3,'[소셜 살롱] 주류 비주류 클럽',0,'홀로 연말을 보내는 자발적 비주류인들을 위한 온라인 연말파티!','#소주 #맥주 #주류 #아무튼술집 #맥주덕후 #프로혼술러','2022. 12. 15 ~ 12. 22 (매주 목, 2회차)','김혜경 작가, "명품맥덕" 유튜버','zoom 온라인','12명','주류 비주류 클럽 신청합니다.','','Y','ac3','ac3-detail','웅앵웅',100,sysdate,'ac1',1,'','N');
insert into academy values(4,'[소셜 살롱] 프라이데이 리스닝 클럽',0,'알고리즘이 추천하는 음악에 질린 리스너들을 위한 금요일 밤의 리스닝 클럽','#음악 #노래 #음악추천 #플레이리스트 #A&R #숨은명곡 #살롱','2022. 12. 16 ~ 12. 23 (매주 금, 2회차)','조미쉘 A&R, 씽잉비틀 대표','상상마당 홍대 시네마 스위트룸','12명','','','N','ac4','ac4-detail','웅앵웅',100,sysdate,'ac1',1,'','N');
insert into academy values(5,'[요즘예술] ART X SCIENCE',0,'인공지능과 로봇이 일상의 영역까지 부쩍 다가온 오늘, 예술은 어디서 움트고 있을까요?','#과학 #인공지능 #로봇 #미래 #예술 #작가 #화가 #neuron #newrun #newlearn','2023. 03. 11 - 03. 18 (매주 토, 2회차)','슬릿스코프, 팀 보이드, 이미영 모더레이터','상상마당 홍대 아카데미','각 20명 내외','','','N','ac5','ac5-detail','웅앵웅',100,sysdate,'ac1',1,'','N');
insert into academy
(a_no,title, price, intro, tag, schedule, a_name,place, inwon,
request, onoff,image, detail_img, detail_txt,count, a_date, id, ct_no,status)
values(36,'[5월 가정의 달 특강] 가족 5락실',0,
'가정의 달, 5월을 맞아 상상마당 아카데미에서 준비한 가족 무료 초대 특강 ',
'#시아노타입 #세밀화 #조현진 #김다혜 #가족사진인화 #봄꽃그리기 #가정의달',
'2023년 05월 20일, 토요일 / (옵션 별 시간 확인)','김다혜 사진작가, 조현진 세밀화가',
'상상마당 홍대 아카데미','각 6명 (3팀)  *2인 1팀으로만 참여 가능',
'홈페이지 신청 후 구글 설문지 답변까지 완료해주세요.','N','ac6','ac6-detail','웅앵웅',
1,sysdate,'ac1',1,'Y');

--취미
insert into academy values(6,'[1DAY] 뜻밖의 크리스마스 : 포인세티아 그리기',42000,'그림으로 식물을 기록해본 적 있나요? 눈으로 담고 손으로 그리는 식물세밀화 입문 수업','#식물세밀화 #색연필그림 #보태니컬아트
#관찰력 #힐링 #몰입 #크리스마스','1회차 / (옵션 별 일정 확인)','조현진 식물세밀화가','상상마당 홍대 아카데미','6명','','','N','h1','h1-detail','본문샤라라',6,sysdate,'ac1',2,'','N')
insert into academy values(7,'[1DAY] 뜻밖의 크리스마스 : 괴물 오너먼트 만들기',35000,'니들 펠트로 나만의 괴물 오너먼트를 만들어 봅니다.','#펠트 #니들펠트 #양모펠트 #키링 #인형','1회차/(옵션 별 일정 확인)','괴물은 몬스터 니들펠트 작가','상상마당 홍대 아카데미','10명','','','N','h2','h2-detail','본문대충 뭐 넣고요',10,sysdate,'ac1',2,'','N')
insert into academy values(8,'[1DAY] 뜻밖의 크리스마스 : 아이패드로 엽서 만들기',35000,'프로크리에이트로 손그림 같은 크리스마스 엽서를 만듭니다.','#엽서만들기 #크리스마스 #아이패드 #프로크리에이트 #디지털드로잉','(옵션 별 일정 확인)','박다예 회화작가','상상마당 홍대 아카데미','10명','','','N','h3','h3-detail','본문대충 뭐 넣고요',10,sysdate,'ac1',2,'','N')
insert into academy values(9,'[개관 기념 특강] 좋은 재료 있으면 소개시켜 줘',10000,'새활용 마스터와 함께 재료들의 환골탈태를 도와주세요!','#새활용 #업사이클링 #지속가능성 #펄피 #펄프오브제 #노플라스틱선데이','9/9 (토), 9/15 (금) / (옵션별 일정 확인)','펄피(pulphy), 노플라스틱선데이(NoPlasticSunday)','상상마당 홍대 아카데미','9/9 (토) 8명, 9/15 (금) 10명','','8/12 (토) - 9/6 (수) 24:00','N','h4','h4-detail','본문대충 뭐 넣고요',10,sysdate,'ac1',2,'','N')
insert into academy values(10,'[1DAY] 뜻밖의 식물세밀화 : 과일 초상화 그리기',42000,'그림으로 식물을 기록해본 적 있나요? 눈으로 담고 손으로 그리는 식물세밀화 입문 수업','#식물세밀화 #색연필그림 #보태니컬아트 #과일그리기 #원데이 #관찰력 #힐링','1회차 / (옵션 별 일정 확인)','조현진 식물세밀화가','상상마당 홍대 아카데미','6명','','','N','h5','h5-detail','본문대충 뭐 넣고요',6,sysdate,'ac1',2,'','N')
--글쓰기
insert into academy values(11,'오늘의 SF, 내일의 소설',290000,'SF 소설을 쓰고자 하는 과학 알못을 위한 SF 소설반!','#SF소설쓰기 #장르소설 #공상과학 #사이파이','기수 별 일정 확인','해도연 작가','Zoom 온라인','15명','','','Y','w1','w1-detail','본문샤라라',15,sysdate,'ac1',3,'','N')
insert into academy values(12,'읽고 싶어 미치는 로맨스 소설 쓰기',300000,'독자의 마음을 쥐락펴락하는 로맨스 웹 소설의 20가지 성공 법칙','#제리안 #로맨스쓰기 #네이버웹소설 #로맨스판타지 #러브픽션 #웹소설문법','(옵션 별 일정 확인)','제리안 작가','Zoom 온라인','15명','','','Y','w2','w2-detail','본문대충 뭐 넣고요',15,sysdate,'ac1',3,'','y')
insert into academy values(13,'내 문장은 어디서부터 고쳐야 할까',180000,'선명하게 내 생각을 쓰는 법, 실전 교정 교열 클래스','#독립출판 #글쓰기 #문법 #맞춤법 #교정교열 #출판에디터 #퇴고하는법 #졸쪼','기수 별 일정 확인','김은경 작가','Zoom 온라인','12명','','','Y','w3','w3-detail','본문대충 뭐 넣고요',12,sysdate,'ac1',3,'','N')
insert into academy values(14,'카피라이팅 레벨 업',70000,'죽은 카피도 되살리는 카피라이팅 5단계 실습 클래스','#카피라이팅 #타이틀쓰는법 #한문장쓰기 #카피라이팅실습','2회차 / (옵션 별 일정 확인)','손수현 카피라이터, 작가','Zoom온라인','15명','','','Y','w4','w4-detail','본문대충 뭐 넣고요',15,sysdate,'ac1',3,'','N')
insert into academy values(15,'독자에서 작가가 되는 에세이 쓰기',280000,'작가가 꿈인데, 어디서부터 어떻게 시작해야 할지 모르겠다면!','#에세이#글쓰기#작가#트렌드#에세이를써보고싶으세요#에세이스트#독립출판','	6회차 / 기수 별 일정 확인','김은경 작가/편집자','상상마당 홍대 아카데미','15명','','','N','w5','w5-detail','본문대충 뭐 넣고요',15,sysdate,'ac1',3,'','N')
--그림책
insert into academy values(16,'볼로냐 그림책 워크숍',0,'','','','','','','','','N','b1','b1-detail','본문샤라라',0,sysdate,'ac1',4,'','N')
insert into academy values(17,'그림책방 오픈까지 세 걸음',150000,'책방지기가 알려주는 그림책방 오픈 체크리스트와 생존법','#그림책방#동네책방#그림책#창업#사업계획서#책방지기 #마케팅 #경영노하우','3회차 / (옵션 별 일정 확인)','박성혜 책방지기','ZOOM 온라인','12명','','','Y','b2','b2-detail','본문대충 뭐 넣고요',12,sysdate,'ac1',4,'','N')
insert into academy values(18,'그림으로 글쓰기',350000,'유리 슐레비츠의「Writing with Picture」로 쉽게 배우는 그림책','#그림책#유리슐레비츠#WritingwithPicture#그림책교과서 #그림책필사#스토리텔링','8회차 / (옵션 별 일정 확인','김난령 스토리텔링 연구가, 번역가','Zoom 온라인','12명','','','Y','b3','b3-detail','본문대충 뭐 넣고요',12,sysdate,'ac1',4,'','N')
insert into academy values(19,'[1DAY] 책방지기가 되고 싶다면 누구나',40000,'7년차 동네 책방지기의 생생한 책방 창업 노하우 원데이 특강','#원데이특강 #책방 #책방창업 #책방트렌드 #마케팅 #공간창업','(옵션별 일정 확인)','박성혜 책방지기','온라인 ZOOM','15명','','','Y','b4','b4-detail','본문대충 뭐 넣고요',15,sysdate,'ac1',4,'','N')
insert into academy values(20,'스토리 드로잉',380000,'이야기 낙서 Story Drawing, 일상 속에서 이야기를 찾고 기록하세요.','#이야기낙서 #글과그림 #그림책 #시놉시스 #크로키 #시각적서술 #그림책창작','8회차 / (옵션 별 일정 확인)','송미경 아동청소년문학 작가','Zoom온라인','15명','','','Y','b5','b5-detail','본문대충 뭐 넣고요',15,sysdate,'ac1',4,'','N')
--디자인
insert into academy values(21,'<SSMD FIGURE DESIGN PRODUCTION 11th> 무료 과정설명회',0,'현직 피규어 작가들에게 무엇이든 물어 보세요😎','','1/27 (토) | 14:00-16:00 | 1회차','','상상마당 홍대 아카데미','','','','N','d1','d1-detail','본문샤라라',0,sysdate,'ac1',5,'','N')
insert into academy values(22,'SSMD FIGURE DESIGN PRODUCTION',0,'	손 조형으로 기초부터! 아트토이 작가 양성 프로젝트','#FIGURE #DESIGN #ARTTOY #SSMD #HANDSINFACTORY #UPTEMPO #HAZARD #TTF','기수별 상이','','상상마당 홍대 아카데미','','','','N','d2','d2-detail','본문대충 뭐 넣고요',100,sysdate,'ac1',5,'','N')
insert into academy values(23,'[1DAY] 하우투 로컬굿즈',20000,'	일반적인 굿즈는 그만, 실패를 줄이는 지역 특성화 굿즈 노하우!','	#굿즈판매 #로컬굿즈 #지역굿즈 #노하우 #셀링포인트','(옵션 별 일정 확인)','황서현 일러스트레이터','Zoom 온라인','20명','','','Y','d3','d3-detail','본문대충 뭐 넣고요',0,sysdate,'ac1',5,'','Y')
insert into academy values(24,'[1DAY] 내 디자인, 뭐가 잘못됐나요?',35000,'베테랑 디자이너가 내려주는 디자인 처방전!','#디자인 #디자이너 #프리랜서 #디자인이론 #디자인처방전','(옵션별 일정 확인)','석중휘 디자이너, 교수','상상마당 홍대 아카데미','15명','','','N','d4','d4-detail','본문대충 뭐 넣고요',0,sysdate,'ac1',5,'','Y')
insert into academy values(25,'한 장씩 그리는 아이패드 애니메이션',220000,'현직 애니메이터와 함께 아이패드만으로 애니메이션을 만들어 봅니다.','#아이패드 #프로크리에이트 #애니메이션 #디지털노마드','5회차 / (옵션 별 일정 확인)','보은 애니메이터','Zoom 온라인','10명','','','Y','d5','d5-detail','본문대충 뭐 넣고요',10,sysdate,'ac1',5,'','N')
--미디어
insert into academy values(26,'KT&G 상상마당 콘서트 기획자 워크숍',0,'내가 기획한 공연을 상상마당 공연장에서! 상상마당 공연기획자 양성과정','','2기','안동용, 박준우, 제이미 전, 박성자 외','상상마당 홍대 아카데미,라이브홀','16명','','2023년 5월 23일 ~ 11월 30일 (매주 화, 수, 목, 19:00-21:00)','N','m1','m1-detail','본문샤라라',16,sysdate,'ac1',6,'','N')
insert into academy values(27,'SSMD ALBUM PRODUCING PROJECT : 키라라의 일단 앨범내기',0,'전자음악가 키라라와 함께 앨범을 만들고 음악가가 됩시다','#SSAP #키라라의일단앨범내기 #일단앨범내기 #키라라 #음반기획','1기','키라라 음악가 외','상상마당 홍대 아카데미','7명','','2023. 11.20(월) ~ 2024.04.01(월) 19:00-22:00, 매주 월','N','m2','m2-detail','본문대충 뭐 넣고요',7,sysdate,'ac1',6,'','N')
insert into academy values(28,'한 장씩 그리는 아이패드 애니메이션',220000,'현직 애니메이터와 함께 아이패드만으로 애니메이션을 만들어 봅니다.','#아이패드 #프로크리에이트 #애니메이션 #디지털노마드','5회차 / (옵션 별 일정 확인)','보은 애니메이터','Zoom 온라인','10명','','','Y','m3','m3-detail','본문대충 뭐 넣고요',10,sysdate,'ac1',6,'','N')
insert into academy values(29,'여동윤의 프라이데이 무비클럽',2400000,'영화와 이야기로 연결된 사람들. 홍대에서 가장 안전한 흑역사 나눔터.','	#영화모임 #이야기 #토론 #소셜살롱 #여동윤','(옵션별 일정 확인)','여동윤 배우 겸 작가','상상마당 홍대 아카데미','10명','','','N','m4','m4-detail','본문대충 뭐 넣고요',0,sysdate,'ac1',6,'','N')
insert into academy values(30,'키라라의 일단 에이블톤',480000,'노트북 한 대로 원하는 음악을! 첫 창작곡을 만들기 위한 쉽고 친절한 에이블톤 클래스','##키라라 #에이블톤라이브 #AbletonLive','10회 / (옵션 별 일정 확인)','키라라 음악가','상상마당 홍대 아카데미','16명','','','N','m5','m5-detail','본문대충 뭐 넣고요',0,sysdate,'ac1',6,'','N')
--사진
insert into academy values(31,'사진가의 독립출판: 출판기획',200000,'국내외 사진책 시장과 독립출판의 현재와 제작실무 가이드에 관하여 알아보는 시간입니다.','	#사진가의책 #책만들기 #독립출판 #사진작가 #사진책 #사진책출판','4회차 / (옵션 별 일정 확인)','김정인 사진가 겸 출판 전문가','ZOOM 온라인','12명','','','Y','p1','p1-detail','본문샤라라',0,sysdate,'ac1',7,'','Y')
insert into academy values(32,'MEET 클래식프린트(주말반)',360000,'클래식프린트와의 첫 만남! 클래식프린트로 자신만의 작업물을 만들어 보는 시간','#고전인화 #특수프린트 #시아노타입 #반다익브라운','4회차 / (옵션 별 일정 확인)','김다혜 사진작가','상상마당 홍대 아카데미','8명','','','N','p2','p2-detail','본문대충 뭐 넣고요',0,sysdate,'ac1',7,'','Y')
insert into academy values(33,'[1DAY] 은하수 사진, 네게만 줄게 my Galaxy',30000,'원데이 특강! 은하수 사진을 찍기 위한 지식과 방법에 대해 알아보는 시간','#은하수사진 #별사진 #은하수포인트','1회차 / (옵션 별 일정 확인)','홍동민 사진작가','	ZOOM 온라인','15명','','','Y','p3','p3-detail','본문대충 뭐 넣고요',0,sysdate,'ac1',7,'','Y')
insert into academy values(34,'Focus on Photofolio',2900000,'준비된 사진 포트폴리오와 함께 사진작가로 자립하기!','	#사진작가 #포트폴리오 #사진크리틱 #작품셀렉 #사진스토리','4회차 / (옵션 별 일정 확인)','임수식 사진작가','상상마당 홍대 아카데미','8명','','','N','p4','p4-detail','본문대충 뭐 넣고요',0,sysdate,'ac1',7,'','Y')
insert into academy values(35,'[1DAY] 친절한 흑백필름 설명서',55000,'상상마당 암실에서 배워보는 흑백필름 현상/인화 원데이 클래스!','#흑백사진 #흑백필름 #필름현상 #필름인화 #암실체험','1회차 / (옵션 별 일정 확인)','김다혜 사진작가','상상마당 홍대 아카데미','프로그램 별 8명','','','N','p5','p5-detail','본문대충 뭐 넣고요',8,sysdate,'ac1',7,'','N')

UPDATE academy SET count = 0 WHERE a_no BETWEEN 1 AND 10;
UPDATE academy SET status = 'Y' WHERE a_no BETWEEN 1 AND 10;
UPDATE academy SET count = 0 WHERE a_no BETWEEN 12 AND 13;
UPDATE academy SET status = 'Y' WHERE a_no = 12;
UPDATE academy SET id='ac2' WHERE a_no BETWEEN 31 AND 35;
alter table academy drop column detail_txt;
alter table academy drop column detail_img;


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
	a_no number(20),					 		-- 강의 번호 (fk)
	ct_no number(20),							-- 카테고리 번호(fk)
	foreign key(id) references member(id),
	foreign key(a_no) references academy(a_no),
	foreign key(ct_no) references category(ct_no)
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

-- 본문내용
drop table detail;
create table detail(
	a_no number(20) primary key,
	txt_title1 varchar2(100),
	text1 varchar2(2000),
	text1_img varchar2(200),
	txt_title2 varchar2(100),
	text2 varchar2(2000),
	text2_img varchar2(200),
	txt_title3 varchar2(100),
	text3 varchar2(2000),
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

select * from detail;

insert into detail (a_no,txt_title1,text1,text1_img,txt_title2,text2,txt_title3,text3,txt_title4,text4)
values(1,'강의소개','강의소개내용~~~~','ac1-detail','프로그램','프로그램내용~~~~','세미나특징','세미나특징내용~~~~~','강연자 소개','강연자소개내용~~~~~');
