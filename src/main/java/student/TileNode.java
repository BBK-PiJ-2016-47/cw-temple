package student;

import java.util.Set;

import game.Tile;

public class TileNode {
    /*
    Creating a node for each tile to keep track of visiting based on searchexample
     */

    private boolean visited;
    private long id;

    TileNode(long id, Tile tile) {
    	this.id = id;
    	visited = false;
    }
    
    public boolean getVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    
    public long getId() {
    	return id;
    }

}