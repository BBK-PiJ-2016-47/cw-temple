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
  
  public void connectNode(final TileNode start, final TileNode end) {
    List<TileNode> nodeList = explorerMap.get(start);
      if (nodeList == null) {
        nodeList = new ArrayList<>();
      }
    explorerMap.put(start, nodeList);
    nodeList.add(end);
  }
  
  /* 
   * returns an arraylist of neighbours that have not been visited
   */
  public List<TileNode> getUnvisitedNeighbours(final TileNode node) {
    final List<TileNode> neighbours = explorerMap.get(node);
    final List<TileNode> unvisitedNeighbours = new ArrayList<TileNode>();
    if (neighbours != null) {
      for (int i = 0; i < neighbours.size(); i++) {
        final TileNode temp = neighbours.get(i);
        if (tileNodes.contains(node)) {
          unvisitedNeighbours.add(temp);
        }
      }
    }
    return unvisitedNeighbours;
  }
  
}
