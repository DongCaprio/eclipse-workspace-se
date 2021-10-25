--member 회원가입 5명
insert into member(m_id,m_password,m_name,m_address) values('aaa','aaa','AIM','AEOUL');
insert into member(m_id,m_password,m_name,m_address) values('bbb','bbb','BIM','BEOUL');
insert into member(m_id,m_password,m_name,m_address) values('ccc','ccc','CIM','CEOUL');
insert into member(m_id,m_password,m_name,m_address) values('ddd','ddd','DIM','DEOUL');
insert into member(m_id,m_password,m_name,m_address) values('eee','eee','EIM','EEOUL');
--member_detail 회원상세정보 3개
insert into member_detail(m_id,md_carno,md_childno) values('aaa','34가5678',1);
insert into member_detail(m_id,md_carno,md_childno) values('bbb','23나4578',2);
insert into member_detail(m_id,md_carno,md_childno) values('ccc','11가0987',0);
--board 회원게시판
insert into board(board_no,board_title,board_content,board_regdate,board_read_count,m_id) 
values(1,'title1','content1',sysdate,0,'aaa');
insert into board(board_no,board_title,board_content,m_id) values(2,'title2','content2','aaa');
insert into board(board_no,board_title,board_content,m_id) values(3,'title3','content3','aaa');
insert into board(board_no,board_title,board_content,m_id) values(4,'title4','content4','bbb');
insert into board(board_no,board_title,board_content,m_id) values(5,'title5','content5','bbb');
insert into board(board_no,board_title,board_content,m_id) values(6,'title6','content6','ccc');

--product 제품
insert into product(p_no,p_name,p_price,p_desc) values (101,'아이패드1',1000,'아이패드1 좋아요!');
insert into product(p_no,p_name,p_price,p_desc) values (102,'갤럭시20',5000,'갤럭시20 좋아요!');
insert into product(p_no,p_name,p_price,p_desc) values (103,'LG그램',20000,'LG그램 좋아요!');
insert into product(p_no,p_name,p_price,p_desc) values (104,'삼성노트북',30000,'삼성노트북 좋아요!');
insert into product(p_no,p_name,p_price,p_desc) values (105,'현주컴퓨터',10000,'현주컴퓨터 좋아요!');
insert into product(p_no,p_name,p_price,p_desc) values (106,'레노버컴퓨터',1000,'레노버컴퓨터 좋아요!');
insert into product(p_no,p_name,p_price,p_desc) values (107,'한성컴퓨터',1000,'한성컴퓨터 좋아요!');
insert into product(p_no,p_name,p_price,p_desc) values (108,'맥북',500000,'맥북 좋아요!');

--로그인한 aaa 유저 cart담기(insert or update)
insert into cart(cart_item_no, cart_item_qty,m_id,p_no) values(7001,1,'aaa',101);
insert into cart(cart_item_no, cart_item_qty,m_id,p_no) values(7002,2,'aaa',102);
insert into cart(cart_item_no, cart_item_qty,m_id,p_no) values(7003,3,'aaa',103);

--로그인한 bbb 유저 cart담기
insert into cart(cart_item_no, cart_item_qty,m_id,p_no) values(7004,1,'bbb',103);
insert into cart(cart_item_no, cart_item_qty,m_id,p_no) values(7005,2,'bbb',104);
--로그인한 ccc 유저 cart담기
insert into cart(cart_item_no, cart_item_qty,m_id,p_no) values(7006,2,'ccc',105);

--로그인한 aaa 유저 주문
/*
    8001--> 101번 1개,102번 2개,103번 3개, 주문총금액 71000원
    8002--> 108번 1개 ,주문총금액 500000원
    8003--> 106번 2개 ,107번 1개,주문총금액 500000원
    
*/
/*************************8001번주문***********************/
insert into orders(o_no,o_date,o_price,m_id) values (8001,sysdate-2,71000,'aaa');

insert into order_item(oi_no,oi_qty,o_no,p_no) values (9001,1,8001,101);
insert into order_item(oi_no,oi_qty,o_no,p_no) values (9002,2,8001,102);
insert into order_item(oi_no,oi_qty,o_no,p_no) values (9003,3,8001,103);
/*************************8002번주문***********************/
insert into orders(o_no,o_date,o_price,m_id) values (8002,sysdate-1,500000,'aaa');

insert into order_item(oi_no,oi_qty,o_no,p_no) values (9004,1,8002,108);
/*************************8003번주문***********************/
insert into orders(o_no,o_date,o_price,m_id) values (8003,sysdate,3000,'aaa');

insert into order_item(oi_no,oi_qty,o_no,p_no) values (9005,2,8003,106);
insert into order_item(oi_no,oi_qty,o_no,p_no) values (9006,1,8003,107);


--로그인한 bbb 유저 주문
/*
    8004--> 103번 1개,104번 2개 주문총금액 80000원
    8005--> 106번 2개 ,주문총금액 2000원
*/
/*************************8004번주문***********************/
insert into orders(o_no,o_date,o_price,m_id) values (8004,sysdate-1,80000,'bbb');

insert into order_item(oi_no,oi_qty,o_no,p_no) values (9007,1,8004,103);
insert into order_item(oi_no,oi_qty,o_no,p_no) values (9008,2,8004,104);
commit;

/*************************8005번주문***********************/
insert into orders(o_no,o_date,o_price,m_id) values (8005,sysdate,2000,'bbb');

insert into order_item(oi_no,oi_qty,o_no,p_no) values (9009,2,8005,106);
commit;


