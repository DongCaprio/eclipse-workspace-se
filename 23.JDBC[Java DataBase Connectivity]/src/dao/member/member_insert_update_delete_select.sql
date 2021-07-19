--insert
INSERT INTO member (m_id, m_password, m_name, m_address, m_age, m_married, m_regdate) 
    VALUES ('aaaa', '1111', '김경호', '서울시 관악구', 37, 'T', sysdate);
INSERT INTO member (m_id, m_password, m_name, m_address, m_age, m_married, m_regdate) 
    VALUES ('bbbb', '2222', '김경우', '서울시 도봉구', 23, 'F', sysdate);
INSERT INTO member (m_id, m_password, m_name, m_address, m_age, m_married, m_regdate) 
    VALUES ('cccc', '3333', '김경미', '서울시 강남구', 45, 'T', sysdate);

--update pk
update member set 
    m_password='0000',
    m_name='김변경',
    m_address='제주도민',
    m_age=20, m_married = 'T' ,
    m_regdate = sysdate 
    where m_id='aaaa';

--delete pk
delete from member where m_id='aaaa';
--select pk
select * from member where m_id = 'bbbb';
--select all
select * from member;

commit;