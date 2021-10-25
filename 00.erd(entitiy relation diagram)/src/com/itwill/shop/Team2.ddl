DROP TABLE product_image CASCADE CONSTRAINTS;
DROP TABLE Product CASCADE CONSTRAINTS;

CREATE TABLE Product(
		p_no                          		NUMBER(10)		 NULL ,
		p_name                        		VARCHAR2(50)		 NULL ,
		p_price                       		NUMBER(10)		 NULL ,
		p_desc                        		VARCHAR2(256)		 NULL ,
		p_stock                       		NUMBER(10)		 NULL ,
		p_regdate                     		DATE		 DEFAULT sysdate		 NULL ,
		p_order_num                   		NUMBER(10)		 NULL 
);


CREATE TABLE product_image(
		pi_name                       		VARCHAR2(100)		 NULL ,
		p_no                          		NUMBER(10)		 NULL 
);



ALTER TABLE Product ADD CONSTRAINT IDX_Product_PK PRIMARY KEY (p_no);

ALTER TABLE product_image ADD CONSTRAINT IDX_product_image_PK PRIMARY KEY (p_no);
ALTER TABLE product_image ADD CONSTRAINT IDX_product_image_FK0 FOREIGN KEY (p_no) REFERENCES Product (p_no);

