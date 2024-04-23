import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.*;

public class BSTTest {

	private DefaultMap<Integer, String> testMap;
	private DefaultMap<Integer, String> mapWithCap;
	
	@Test
	public void testPut() {
		testMap = new BST<>();
		testMap.put(9, "10");
		testMap.put(8, "10");
		testMap.put(1, "5");
		testMap.put(100, "t");
		testMap.put(2, "");
		assertEquals(5, testMap.size());
		assertEquals(false, testMap.put(100, "t"));
	}
	
	@Test
	public void testGet() {
		testMap = new BST<>();
		testMap.put(9, "e");
		testMap.put(10, "f");
		testMap.put(1, "5");
		testMap.put(100, "t");
		testMap.put(2, "");
		assertEquals("t", testMap.get(100));
		assertEquals(false, testMap.containsKey(3));
	}

	@Test
	public void testRemove() {
		testMap = new BST<>();
		testMap.put(9, "e");
		testMap.put(10, "f");
		testMap.put(1, "5");
		testMap.put(100, "t");
		testMap.put(2, "");
		assertEquals(false, testMap.remove(0));
		assertEquals(true, testMap.containsKey(100));
	}
	
	@Test
	public void testSet() {
		testMap = new BST<>();
		testMap.put(9, "e");
		testMap.put(10, "f");
		testMap.put(1, "5");
		testMap.put(100, "t");
		testMap.put(2, "");
		testMap.set(100, "replace");
		assertEquals(true, testMap.replace(100, "rereplace"));
		assertEquals("rereplace", testMap.get(100));
	}
}
