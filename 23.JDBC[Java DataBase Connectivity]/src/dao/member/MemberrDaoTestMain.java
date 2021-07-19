package dao.member;

public class MemberrDaoTestMain {

	public static void main(String[] args) throws Exception {
		MemberDao memberDao = new MemberDao();
//		System.out.println("insert -->" + memberDao.insert(new Member("abbb", "2223", "김씨", "서울", 10, "F", null)));
		System.out.println("delete -->" + memberDao.deleteById("aaaa"));
		System.out.println("update -->" + memberDao.updateById(new Member("cccc","4321","박씨","부산",99,"T",null)));
		System.out.println("selectById -->" +memberDao.selectById("bbbb"));
		System.out.println("selectAll -->"+memberDao.selectAll());
	}

}
