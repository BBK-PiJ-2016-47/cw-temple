package student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import game.EscapeState;
import game.ExplorationState;
import game.Node;
import game.NodeStatus;
import game.Tile;

import java.util.concurrent.ThreadLocalRandom;

public class Explorer {
  //variables for explore method
  private long currentLocation; 
  private Collection<NodeStatus> neighbouringNodeStatuses; //collection of NodeStatus' neighbours
  private Stack<Long> visitedTilesOrder = new Stack<Long>(); //stack of IDs that have been visited
  private List<NodeStatus> unvisitedNodeStatuses; //list of NodeStatus neighbours not been visited
  private Set<Long> visitedTiles = new HashSet<Long>();
  
  //variables for escape method
  private Stack<Node> visitedEscapeOrder = new Stack<Node>();
  private List<Node> unvisitedEscapeNodes = new ArrayList<Node>();
  private List<Node> neighbouringEscapeNodes;
  List<Long>visitedEscapeTiles = new ArrayList<Long>();
  Set<Node> nodesToVisit;
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

    while (state.getDistanceToTarget() != 0) {
      unvisitedNodeStatuses = new ArrayList<>();
      currentLocation = state.getCurrentLocation();
      //getting list of current location's neighbours
      neighbouringNodeStatuses = state.getNeighbours();
      //filtering current location's neighbours into those that haven't been visited
      unvisitedNodeStatuses = getUnvisitedNeighbours(neighbouringNodeStatuses);
      visitedTiles.add(currentLocation);

      /*
       * if there are no unvisited neighbours, pop the current location off the stack and 
       * peek to move to the node before
       */
      if (unvisitedNodeStatuses.isEmpty()) {
        visitedTilesOrder.pop();
        state.moveTo(visitedTilesOrder.peek()); 
      } else {
        /*
         * if the location has neighbours, then find the unvisited neighbour with the shortest
         * difference to orb and move to it, and update the stack
         */
        NodeStatus next = returnShortestNeighbour(unvisitedNodeStatuses);
        visitedTilesOrder.push(next.getId());
        state.moveTo(next.getId());
      }
    }
    return;
  }
  
  /**
   *  Returns the NodeStatus of the neighbour with the shortest distance to the orb
   *  @param neighbours - the list of unvisited neighbours to the current node
   *  @return shortestNeighbour 
   */
  private NodeStatus returnShortestNeighbour(List<NodeStatus> neighbours) {
    neighbours.sort((s1, s2) -> s1.getDistanceToTarget() - s2.getDistanceToTarget());
    return neighbours.get(0);
  }
  
  /**
   *  Returns the list of NodeStatuses of the neighbours to the current location
   *  that have not yet been visited
   *  @param neighbours - the collection of neighbours to the current node
   *  @return unvisitedNodeStatuses - list of filtered neighbours
   */
  public List<NodeStatus> getUnvisitedNeighbours(Collection<NodeStatus> neighbours) {
    neighbours.forEach(n -> {
	if (!visitedTiles.contains(n.getId())) { unvisitedNodeStatuses.add(n); } } );
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
    nodesToVisit = new HashSet<Node>(state.getVertices());
    Map<Node, List<Node>> nodesAndNeighbours = new HashMap<Node, List<Node>>();
    //pushing root node on stack
    visitedEscapeOrder.push(state.getCurrentNode());

    while (!state.getCurrentNode().equals(state.getExit())) {
      //need to figure out how to make a path to exitNode
      Node exitNode = state.getExit();
      Set<Node> exitNeighbours = exitNode.getNeighbours();
      unvisitedEscapeNodes = new ArrayList<Node>();
      currentNode = state.getCurrentNode();
      Tile currentTile = currentNode.getTile();
      Tile exitTile = exitNode.getTile();
      int exitRow = exitTile.getRow();
      int exitColumn = exitTile.getColumn();
      //pick up gold without throwing an exception
      if(currentTile.getGold() > 0){
        state.pickUpGold();
      }
      nodesToVisit.remove(currentNode);
      neighbouringEscapeNodes = new ArrayList<Node>(currentNode.getNeighbours());
      unvisitedEscapeNodes = returnUnvisitedEscapeNeighbours(neighbouringEscapeNodes);
      visitedEscapeTiles.add(currentNode.getId());
      nodesAndNeighbours.put(currentNode, neighbouringEscapeNodes);

      if(!unvisitedEscapeNodes.isEmpty()) {
        for (Node n : unvisitedEscapeNodes) {
          if (exitNeighbours.contains(n) && (currentTile.getRow() == exitRow|| currentTile.getColumn() == exitColumn)) {
            state.moveTo(n);
            return;
          }

          if (n.equals(exitNode)) {
            state.moveTo(exitNode);
            return;
          }
        }
        visitedEscapeOrder.push(unvisitedEscapeNodes.get(0));
        state.moveTo(unvisitedEscapeNodes.get(0));
      } else {
        visitedEscapeOrder.pop();
        state.moveTo(visitedEscapeOrder.peek());
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
    neighbours.forEach(n -> {
      if (!visitedEscapeTiles.contains(n.getId())) { unvisitedEscapeNodes.add(n); } } );
      return unvisitedEscapeNodes;
  }
}