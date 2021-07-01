package file;

import java.io.File;

public class FileMain {

	public static void main(String[] args) throws Exception{
		/*
		 현재경로 [기준경로]
		    eclipse --> C:\2021-05-JAVADEVELOPER-HOME\eclipse-workspace-se\21.입출력[IO]
		 상대(relaative)경로
		    현재경로를 기준으로 경로를 기술
		 절대(absolute)경로
		     C:, D: 드라이브를 기준으로 경로기술
		 */
		
		//file path
		File file1 = new File("a.txt");
		File file2 = new File("sample/Account.java");
//		File file3 = new File("")
		System.out.println(file1.getPath());
		System.out.println(file2.getPath());
		
		System.out.println();
		// file absolute path
		System.out.println(file1.getAbsolutePath());
		System.out.println(file2.getAbsolutePath());
		
		System.out.println();
		//file name
		System.out.println(file1.getName());
		System.out.println(file2.getName());
		System.out.println();
		//file parent --> 부모를 알려줘
		System.out.println(file1.getParent());
		System.out.println(file2.getParent());
		System.out.println();
		
		File dir1 = new File("sample");
		File dir2 = new File("sample/subSample1");
		File dir3 = new File("sample","subSample1");
		System.out.println("---------5.isDirectory, isFile------------");
		System.out.println(dir1.isDirectory());
		System.out.println(dir1.isFile());
		System.out.println(dir2.isDirectory());
		System.out.println(dir2.isFile());
		System.out.println("------------6.디렉토리안에 파일(디렉토리)의 목록----------(String[])");
		String[] fileNameList = dir1.list();
		for (String fileName : fileNameList) {
			System.out.println("\t"+fileName);
		}
		System.out.println("----------7. 디렉토리안에 파일(디렉토리)의 목록(File[]) ");
		File[] fileList = dir1.listFiles();
		for (File file : fileList) {
			if(file.isDirectory()) {
				System.out.println("\t D:"+file.getName());
				File[] subFileList = file.listFiles();
				for (File subFile : subFileList) {
					System.out.println("\t\t"+subFile.getName());
				}
			} else if(file.isFile()){
				System.out.println("\t F:"+file.getName());
			}
			
		}
		System.out.println("---------------8. root directory 목록---------");
		File[] rootDriveFileList =File.listRoots();
		for (File rootFile : rootDriveFileList) {
			System.out.println(rootFile.getPath());
		}
		System.out.println("-------------C:\\ 파일목록----------");
		File[] cDriveFileList = rootDriveFileList[0].listFiles();
		for (File cDriveFile : cDriveFileList) {
			System.out.println(cDriveFile.getName());
		}

		System.out.println("----------9. directory 생성---------------");
		File newDir1 = new File("newDir");
		File newDir2 = new File("newDir2");
		File newDir3 = new File("sample", "subSample3");
		System.out.println("newDir1의 존재여부"+newDir1.exists());
		System.out.println("newDir1의 생성 : "+newDir1.mkdir());
		System.out.println("newDir2의 생성 : "+newDir2.mkdir());
		System.out.println("newDir3의 생성 : "+newDir3.mkdir());
		System.out.println("------------10. directory이름변경--------------");
		newDir2.renameTo(new File("renameDir2"));
		System.out.println("-----------11.directory 삭제----------------");
		System.out.println("newDir3의 삭제 "+newDir3.delete());
		
		System.out.println("--------------<<<<File생성,삭제,이름변경>>>>----------------");
		File newFile1=new File("newDir1","newFile1.txt");
		File newFile2=new File("newDir1","newFile2.txt");
//		System.out.println("newFile1 생성:"+newFile1.createNewFile());
//		System.out.println("newFile2 생성:"+newFile2.createNewFile());
//		System.out.println("newFile2 이름변경:"
//				+newFile2.renameTo(new File("newDir1","renameNewFile2.txt")));
//		System.out.println("newFile1 삭제:"+newFile1.delete());
		//System.out.println("newFile1 삭제:"+newFile1.deleteOnExit());
		
		
		
		
	}

}
