package student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import game.NodeStatus;

public class ExplorerGraph {
  private final List<TileNode> tileNodes = new ArrayList<TileNode>();
  private final Map<TileNode, List<TileNode>> explorerMap = new ConcurrentHashMap<>();
  private TileNode rootNode;
  
  
  public TileNode getRootNode() {
    return rootNode;
  }
  
  public void setRootNode(TileNode root) {
    this.rootNode = root;
  }
  
  public void addTileNodeToGraph(TileNode node) {
    tileNodes.add(node);
  }
  
  public void addTileNodeToMap(TileNode node, List<TileNode> neighbours) {
	  explorerMap.put(node, neighbours);
  }
  
  /**
   * 
   * @param start
   * @param end
   */
  
  public void connectNode(final TileNode start, final TileNode end) {
    List<TileNode> nodeList = explorerMap.get(start);
      if (nodeList == null) {
        nodeList = new ArrayList<>();
      }
    explorerMap.put(start, nodeList);
    nodeList.add(end);
  }
  
  /**
   *  Returns the list of TileNodes of the neighbours to the current location
   *  that have not yet been visited
   *  @param neighbours - the list of neighbours to the current node
   *  @return ArrayList of unvisited neighbours
   */
 
  public List<TileNode> getUnvisitedNeighbours(final TileNode node) {
    final List<TileNode> neighbours = explorerMap.get(node);
    final List<TileNode> unvisitedNeighbours = new ArrayList<TileNode>();
    if (neighbours != null) {
      for (int i = 0; i < neighbours.size(); i++) {
        final TileNode temp = neighbours.get(i);
        if (!temp.getVisited()) {
          unvisitedNeighbours.add(temp);
        }
      }
    }
    return unvisitedNeighbours;
  }
  /**
   *  Returns the NodeStatus of the neighbour with the shortest distance to the orb
   *  @param neighbours - the list of unvisited neighbours to the current node
   *  @return TileNode with the shorted distance to orb
   */
  public TileNode returnShortestNeighbour(List<TileNode> neighbours) {
    TileNode shortestNeighbour = neighbours.get(0);
    int shortestDistance = shortestNeighbour.getDistance();
    for(TileNode n : neighbours) {
      int distanceComparison = n.getDistance();
      if (shortestDistance > distanceComparison) {
        shortestNeighbour = n;
        shortestDistance = distanceComparison;
      }
    }
    
    return shortestNeighbour;
  } 
}
