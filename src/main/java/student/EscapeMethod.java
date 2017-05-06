package student;

import java.util.Set;

import game.Node;
import game.Tile;

public class EscapeMethod {
	
	Tile exitTile;
	int exitRow;
	int exitColumn;
	Set<Node> exitNeighbours;
	
	public void pinpointExit(Node exit) {
		exitTile = exit.getTile();
		exitRow = exitTile.getRow();
		exitColumn = exitTile.getColumn();
		exitNeighbours = exit.getNeighbours();
	}

}
