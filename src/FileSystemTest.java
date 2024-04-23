import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.*;

public class FileSystemTest {
	
	@Test
	public void testFileSystemAdd() {
	FileSystem file = new FileSystem();
	file.add("mySample.txt", "/user", "2021/02/06");
	file.add("mySample.txt", "/root", "2021/02/01/");
	file.add("mySample2.txt", "/home", "2021/02/01");
	assertEquals(2, file.dateTree.size());
	}
	
	@Test
	public void testFindFileNamesByDate() {
	FileSystem file = new FileSystem();
	ArrayList<String> list = new ArrayList<>(Arrays.asList("mySample2.txt"));
	file.add("mySample.txt", "/user", "2021/02/06");
	file.add("mySample.txt", "/root", "2021/02/01/");
	file.add("mySample2.txt", "/home", "2021/02/01");
	assertEquals(list, file.findFileNamesByDate("2021/02/01"));
	}
}
