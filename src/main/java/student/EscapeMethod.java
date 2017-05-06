package student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
	Map<Node, Integer> nodesAndDistances = new HashMap<>();
	
	public void pinpointExit(Node exit) {
		this.exit = exit;
		exitTile = exit.getTile();
		exitRow = exitTile.getRow();
		exitColumn = exitTile.getColumn();
		exitNeighbours = exit.getNeighbours();
		exitEdges = exit.getExits();
	}
	
  public List<Node> scanforRoute(Node current) {
    List<Node> tempRoute = new ArrayList<>();
    tempRoute.add(exit);
    Set<Node> neighbours = nodesAndFriends.get(current);
    for (Node n : neighbours) {
      for (Edge e : exitEdges) {
        if (n.getExits().contains(e)) {
          route.add(current);
          break;
        } else {
          //threads might be a good idea here?
          route.add(n);
          scanforRoute(n);
        }
      }
    }
    return tempRoute;
  }
	
	public void updateMaps(Node current, Set<Node> neighbours) {
	  nodesAndFriends.put(current, neighbours);
	  for (Node n : neighbours) {
		  Set<Node> nodeNeighbours = n.getNeighbours();
		  updateMaps(n, nodeNeighbours);
	  }
        
	}


}
