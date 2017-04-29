package student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

import game.EscapeState;
import game.ExplorationState;
import game.GameState;
import game.Node;
import game.NodeStatus;

public class Explorer {
  //variables for explore method
  private long currentLocation; 
  private long previousLocation;
  private Collection<NodeStatus> neighbouringNodeStatuses; //collection of NodeStatus' neighbours
  private Stack<Long> visitedTilesOrder = new Stack<Long>(); //stack of IDs that have been visited
  private List<NodeStatus> unvisitedNodeStatuses; //list of NodeStatus neighbours who've not been visited
  private Set<Long> visitedTiles = new HashSet<Long>();
  
  //variables for escape method
  private List<Node> visitedEscapeNodes = new ArrayList<Node>();
  private List<Node> unvisitedEscapeNodes;
  private List<Node> neighbouringEscapeNodes;
  private Node currentNode;
  private Node previousNode;

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
	
	while (state.getDistanceToTarget() != 0) {
	  unvisitedNodeStatuses = new ArrayList<>();
	  currentLocation = state.getCurrentLocation();
	  //getting list of current location's neighbours
	  neighbouringNodeStatuses = state.getNeighbours();
	  //filtering current location's neighbours into those that haven't been visited
	  unvisitedNodeStatuses = getUnvisitedNeighbours(neighbouringNodeStatuses);

	  
	  /*
	   * if the location moved to becomes used, move to previous location, then move the current and previous location 
	   * off the stack and put the top of the stack as the previous
	   */
	  if (visitedTiles.contains(currentLocation)) {
		  visitedTilesOrder.pop();
		  state.moveTo(visitedTilesOrder.peek());
		 
	  } else {
		  /*
		   * if the location is not used up, then find the unvisited neighbour with the shortest
		   * difference to orb and move to it, and update previous location
		   */
		  visitedTiles.add(currentLocation);
		  visitedTilesOrder.push(currentLocation);
		  if (unvisitedNodeStatuses != null) {
	        NodeStatus next = returnShortestNeighbour(unvisitedNodeStatuses);
		    state.moveTo(next.getId());
		  }
	  }
	  /*
	  if (unvisitedNodeStatuses.isEmpty()){
		//using this list to filter out nodes to never return to
		//need to update relevant filter methods still
        usedNodeStatuses.add(currentLocation);
	    state.moveTo(previousLocation);
	    Stack<Long> temp = visitedTiles;
		temp.pop();
		temp.pop();
		previousLocation = temp.peek();
	  } else {
		NodeStatus next = returnShortestNeighbour(unvisitedNodeStatuses);
	    previousLocation = currentLocation;
	    state.moveTo(next.getId());
	  }*/
	}
    return;
  }
  
  /**
   *  Returns the NodeStatus of the neighbour with the shortest distance to the orb
   *  @param neighbours - the list of unvisited neighbours to the current node
   */
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
  
  /**
   *  Returns the list of NodeStatuses of the neighbours to the current location
   *  that have not yet been visited
   *  @param neighbours - the list of neighbours to the current node
   */
  public List<NodeStatus> getUnvisitedNeighbours(Collection<NodeStatus> neighbours) {
	  for (NodeStatus n : neighbours) {
          if (!visitedTiles.contains(n.getId())) {
              unvisitedNodeStatuses.add(n);
          }
      }
      return unvisitedNodeStatuses;
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
	  visitedEscapeNodes = new ArrayList<Node>();
	  while (state.getTimeRemaining() != 0 || !state.getCurrentNode().equals(state.getExit())) {
		  unvisitedEscapeNodes = new ArrayList<Node>();
		  currentNode = state.getCurrentNode();
		  //pick up gold without throwing an exception
		  if(state.getCurrentNode().getTile().getGold() > 0){
			  state.pickUpGold(); 
		  }
		  neighbouringEscapeNodes = new ArrayList<Node>(currentNode.getNeighbours()); 
		  unvisitedEscapeNodes = returnUnvisitedEscapeNeighbours(neighbouringEscapeNodes);
		  visitedEscapeNodes.add(currentNode);
		  
		  if(!unvisitedEscapeNodes.isEmpty()) {
			  int size = unvisitedEscapeNodes.size();
			  //generating a random number for the next tile
			  int randomNum = ThreadLocalRandom.current().nextInt(0, size);
			  state.moveTo(unvisitedEscapeNodes.get(randomNum));
		  } else {
			  Node previousTile = visitedEscapeNodes.get(visitedEscapeNodes.size()-1);
              state.moveTo(previousTile);
              visitedEscapeNodes = new ArrayList<Node>();
		  }
		  
	  }
	  return;
  }
  
  /**
   * Finds a list of unvisited nodes to collect gold from
   * 
   * @param neighbours list of neighbour nodes
   * @return list of unvisited neighbour nodes
   */
  
  private List<Node> returnUnvisitedEscapeNeighbours(List<Node> neighbours) {
    for (int i = 0; i < neighbours.size(); i++) {
      Node temp = neighbours.get(i);
      if (!visitedEscapeNodes.contains(temp)) {
    	  unvisitedEscapeNodes.add(temp);
      }
    }
    return unvisitedEscapeNodes;
  }

  private Node returnOptimumTile(List<Node> neighbours) {
    //need to graph out the best route by going through them all?
	//Or leave a buffer of 2 in time to allow for getting lost
	Node bestNeighbour = neighbours.get(0);
	List<Node> nodesWithGold = new ArrayList<Node>();
    //checking which neighbours have gold but also need to know distance too right?
    for(Node n : neighbours) {
      if (n.getTile().getGold() > 0) {
        nodesWithGold.add(n);
      }
    }
	return bestNeighbour;
  }
}