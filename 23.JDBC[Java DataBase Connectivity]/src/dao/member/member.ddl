DROP TABLE member CASCADE CONSTRAINTS;

/**********************************/
/* Table Name: member */
/**********************************/
CREATE TABLE member(
		m_id                          		VARCHAR2(20)		 NULL ,
		m_password                    		VARCHAR2(10)		 NULL ,
		m_name                        		VARCHAR2(50)		 NULL ,
		m_address                     		VARCHAR2(100)		 NULL ,
		m_age                         		NUMBER(3)		 DEFAULT 0		 NULL ,
		m_married                     		CHAR(1)		 DEFAULT 'F'		 NULL ,
		m_regdate                     		DATE		 DEFAULT sysdate		 NULL 
);

COMMENT ON TABLE member is 'member';
COMMENT ON COLUMN member.m_id is 'm_id';
COMMENT ON COLUMN member.m_password is 'm_password';
COMMENT ON COLUMN member.m_name is 'm_name';
COMMENT ON COLUMN member.m_address is 'm_address';
COMMENT ON COLUMN member.m_age is 'm_age';
COMMENT ON COLUMN member.m_married is 'm_married';
COMMENT ON COLUMN member.m_regdate is 'm_regdate';



ALTER TABLE member ADD CONSTRAINT IDX_member_PK PRIMARY KEY (m_id);

