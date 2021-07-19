package com.itwill07.dao.phonebook;

public class PhoneBookDaoTestMain {

	public static void main(String[] args) throws Exception{
		
		PhoneBookDao phonebook = new PhoneBookDao();
		//insert
		phonebook.insert(new PhoneBook(1, "박박박", "010-9876-5432"));
		phonebook.insert(new PhoneBook(2, "김남정", "011-222-333"));
		phonebook.insert(new PhoneBook(3, "길길", "123-1234"));
		//update
		phonebook.updateByNo(new PhoneBook(3, "이무개", "999-9998"));
		
		//delete
		phonebook.deleteByNo(2);
		
		//selectByPk
		phonebook.selectByNo(1);
		//selectAll
		phonebook.selectAll();

	}

}