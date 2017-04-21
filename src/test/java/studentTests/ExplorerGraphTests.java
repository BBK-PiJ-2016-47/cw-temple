package studentTests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import student.TileNode;

public class ExplorerGraphTests {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void connectNodeTest() {
		TileNode first = new TileNode(4758920, false, 10);
		TileNode second = new TileNode(4924805, false, 0);
	}
	
	@Test
	public void testGetUnvisitedNeighbours() {
		TileNode current = new TileNode(4758920, true, 10);
	}

}
