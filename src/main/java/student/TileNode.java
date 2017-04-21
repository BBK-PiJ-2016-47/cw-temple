package student;


public class TileNode {
    /*
    Creating a node for each tile to keep track of visiting based on searchexample
     */

    private boolean visited;
    private long id;
    private int distance;

    public TileNode(long id, boolean visited, int distance) {
    	this.id = id;
    	this.visited = visited;
    	this.distance = distance;
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
    
    public int getDistance() {
    	return distance;
    }

}