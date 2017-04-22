package student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import game.EscapeState;
import game.ExplorationState;
import game.GameState;
import game.Node;
import game.NodeStatus;

public class Explorer {
  private List<NodeStatus> neighbouringTiles;
  private NodeStatus previousNode;
  private List<Long> visitedTiles;
  private long currentLocation;
  private long previousLocation;
  private NodeStatus currentNodeStatus;
  private List<NodeStatus> unvisitedTiles = new ArrayList<>();
    
  private List<Node> neighbouringEscapeTiles;
  private Node currentNode;

  /**
   * Explore the cavern, trying to find the orb in as few steps as possible.
   * Once you find the orb, you must return from the function in order to pick
   * it up. If you continue to move after finding the orb rather
   * than returning, it will not count.
   * If you return from this function while not standing on top of the orb,
   * it will count as a failure.
   *   
   * <p>There is no limit to how many steps you can take, but you will receive
   * a score bonus multiplier for finding the orb in fewer steps.</p>
   * 
   * <p>At every step, you only know your current tile's ID and the ID of all
   * open neighbor tiles, as well as the distance to the orb at each of these tiles
   * (ignoring walls and obstacles).</p>
   * 
   * <p>To get information about the current state, use functions
   * getCurrentLocation(),
   * getNeighbours(), and
   * getDistanceToTarget()
   * in ExplorationState.
   * You know you are standing on the orb when getDistanceToTarget() is 0.</p>
   *
   * <p>Use function moveTo(long id) in ExplorationState to move to a neighboring
   * tile by its ID. Doing this will change state to reflect your new position.</p>
   *
   * <p>A suggested first implementation that will always find the orb, but likely won't
   * receive a large bonus multiplier, is a depth-first search.</p>
   *
   * @param state the information available at the current state
   */
  public void explore(ExplorationState state) {
    //TODO:
    //do this in a separate method to find shortest route before
    //finishing explore? DFS traversal in graphimpl
    //use peek to see two squares ahead?
	visitedTiles = new ArrayList<Long>();
	while (state.getDistanceToTarget() != 0) {
	  currentLocation = state.getCurrentLocation();
	  int distance = state.getDistanceToTarget();
	  neighbouringTiles = (List<NodeStatus>) state.getNeighbours();
	  //TileNode current = new TileNode(currentLocation, true, distance);
	  //ExplorerGraph graph = new ExplorerGraph();
	  unvisitedTiles = getUnvisitedNeighbours(neighbouringTiles);
	  visitedTiles.add(currentLocation);
	  if (unvisitedTiles.isEmpty()){
	    state.moveTo(previousLocation);
	  } else {
		NodeStatus next = returnShortestNeighbour(unvisitedTiles);
	    //next.setVisited(true);
	    previousLocation = currentLocation;
	    state.moveTo(next.getId());
	  }
	}
    return;
  }
  
  private NodeStatus returnShortestNeighbour(List<NodeStatus> neighbours) {
    NodeStatus shortestNeighbour = neighbours.get(0);
    int shortestDistance = shortestNeighbour.getDistanceToTarget();
    for(NodeStatus n : neighbours) {
      int distanceComparison = n.getDistanceToTarget();
      if (shortestDistance > distanceComparison) {
        shortestNeighbour = n;
        shortestDistance = distanceComparison;
      }
    }
    return shortestNeighbour;
  }
  
  public List<NodeStatus> getUnvisitedNeighbours(List<NodeStatus> neighbours) {
    for (int i = 0; i < neighbours.size(); i++) {
      final NodeStatus temp = neighbours.get(i);
      if (!visitedTiles.contains(temp.getId())) {
        unvisitedTiles.add(temp);
      }
    }
    return unvisitedTiles;
  }

  /**
   * Escape from the cavern before the ceiling collapses, trying to collect as much
   * gold as possible along the way. Your solution must ALWAYS escape before time runs
   * out, and this should be prioritized above collecting gold.
   *
   * <p>You now have access to the entire underlying graph, which can be accessed 
   * through EscapeState.
   * getCurrentNode() and getExit() will return you Node objects of interest, and getVertices()
   * will return a collection of all nodes on the graph.</p>
   * 
   * <p>Note that time is measured entirely in the number of steps taken, and for each step
   * the time remaining is decremented by the weight of the edge taken. You can use
   * getTimeRemaining() to get the time still remaining, pickUpGold() to pick up any gold
   * on your current tile (this will fail if no such gold exists), and moveTo() to move
   * to a destination node adjacent to your current node.</p>
   * 
   * <p>You must return from this function while standing at the exit. Failing to do so before time
   * runs out or returning from the wrong location will be considered a failed run.</p>
   * 
   * <p>You will always have enough time to escape using the shortest path from the starting
   * position to the exit, although this will not collect much gold.</p>
   *
   * @param state the information available at the current state
   */
  public void escape(EscapeState state) {
    //TODO: Escape from the cavern before time runs out
	/*  
	  while (state.getTimeRemaining() != 0 || !state.getCurrentNode().equals(state.getExit())) {
		  currentNode = state.getCurrentNode();
		  state.pickUpGold();
		  //need to graph out the distance that maximises time
		  //time == 1 step
		  visitedEscapeTiles = new ArrayList<Node>();
		  visitedEscapeTiles.add(currentNode);
		  state.moveTo(???);
		  
	  }*/
  }
  
/*  private Node returnOptimumTile(EscapeState state) {
	  neighbouringEscapeTiles = (List<Node>) currentNode.getNeighbours();
	  //need to graph out the best route by going through them all?
	  //Or leave a buffer of 2 in time to allow for getting lost
	  Node bestNeighbour = neighbouringEscapeTiles.get(0);
	  for(int i = 1; i < neighbouringEscapeTiles.size(); i++) {
		  Node compare = neighbouringEscapeTiles.get(i);
		  if (!visitedEscapeTiles.contains(i)) {
			  if (compare.compareTo(bestNeighbour) < 0) {
				  bestNeighbour = compare;
				  
			  } 
		  }
		 
	  }
	  return bestNeighbour;
  }*/
}