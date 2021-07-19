package dao.guest;

import dao.member.Member;

public class GuestDaoTestMain {

	public static void main(String[] args) throws Exception {
		/*
				 이름             널?       유형             
		-------------- -------- -------------- 
		GUEST_NO       NOT NULL NUMBER(10)     
		GUEST_NAME     NOT NULL VARCHAR2(100)  
		GUEST_DATE     NOT NULL DATE           
		GUEST_EMAIL             VARCHAR2(100)  
		GUEST_HOMEPAGE          VARCHAR2(100)  
		GUEST_TITLE    NOT NULL VARCHAR2(255)  
		GUEST_CONTENT  NOT NULL VARCHAR2(4000) 
		 */
		GuestDao guestDao = new GuestDao();
		System.out.println("insert -->" + guestDao.insertGuest(new Guest("김경미", "ggg@naver.com", "http://www.naver.com", "제목은", "난게시판내용")));
		System.out.println("delete -->" + guestDao.deleteGuest(2));
		System.out.println("update -->" + guestDao.updateGuest(new Guest(3,"박경미", "asd@naver.com", "http://www.google.com", "제모옥", "게시판내용용")));
		System.out.println("selectById -->" +guestDao.selectByNo(3));
		System.out.println("selectAll -->"+guestDao.selectAll());

	}

}