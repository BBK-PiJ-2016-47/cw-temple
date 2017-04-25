package student;

public class TileNode {
    /*
    Creating a node for each id/nodestatus to keep track of visiting based on searchexample
     */

    private long id;
    private int distance;
    private boolean visited;

    public TileNode(long id, int distance, boolean visited) {
    	this.id = id;
    	this.distance = distance;
    	this.visited = visited;
    }
    
    public long getId() {
    	return id;
    }
    
    public int getDistance() {
    	return distance;
    }
    
    public boolean getVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    
}