package student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import game.Edge;
import game.EscapeState;
import game.Node;
import game.Tile;

public class EscapeMethod {
	
	Tile exitTile;
	int exitRow;
	int exitColumn;
	Set<Node> exitNeighbours;
	Set<Edge> exitEdges;
	Node exit;
	List<Node> route = new ArrayList<>();
	Map<Node, Set<Node>> nodesAndFriends = new HashMap<>();
	Map<Node, Node> nodesAndParents = new HashMap<>();
	
	public void pinpointExit(Node exit) {
		this.exit = exit;
		exitTile = exit.getTile();
		exitRow = exitTile.getRow();
		exitColumn = exitTile.getColumn();
		exitNeighbours = exit.getNeighbours();
		exitEdges = exit.getExits();
	}
	
  public List<Node> scanforRoute(Node current) {
	  System.out.println("starting route scan");
	nodesAndParents.put(current, null);
	System.out.println("put nodes in maps");
    Queue<Node> routePlan = new LinkedList<>();
    Node tempCurrent = current;
    routePlan.add(tempCurrent);
    while (!routePlan.isEmpty()) {
        System.out.println("starting while routeplan is not empty loop");
        Set<Node> neighbours = tempCurrent.getNeighbours();
        routePlan.poll();
      for (Node n : neighbours) {
        if (!nodesAndParents.containsKey(n)){
        	 System.out.println("iterating through neighbours to add to maps");
    	  nodesAndParents.put(n, tempCurrent);
    	  routePlan.add(n);
        }
      }
    }
    List<Node> actualRoute = new ArrayList<>();
    actualRoute.add(exit);
    while (!exit.equals(current)) {
    	System.out.println("starting adding nodes to current loop ");
    	exit = nodesAndParents.get(exit);
    	actualRoute.add(exit);
    }
    return actualRoute;
  }


  
  public void followRoute(EscapeState state, List<Node> route) {
    for (Node n : route) {
      state.moveTo(n);
    }
  }


	public void updateMaps(Node current, Set<Node> neighbours) {
	  nodesAndFriends.put(current, neighbours);
	  for (Node n : neighbours) {
		  Set<Node> nodeNeighbours = n.getNeighbours();
		  if(!nodesAndFriends.keySet().contains(n)) {
		    updateMaps(n, nodeNeighbours);
		  }
	  }
        
	}


}
