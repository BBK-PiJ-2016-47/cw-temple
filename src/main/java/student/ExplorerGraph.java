package student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import searchexample.Node;

public class ExplorerGraph {
  private final List<TileNode> tileNodes = new ArrayList<TileNode>();
  private final Map<TileNode, List<TileNode>> map = new ConcurrentHashMap<>();
  private TileNode rootNode;
  
  
  public TileNode getRootNode() {
	  return rootNode;
  }
  
  public void setRootNode(TileNode root) {
	  this.rootNode = root;
  }
  
  public void addTileNode(TileNode node) {
	  tileNodes.add(node);
  }
  
  public void connectNode(final TileNode start, final TileNode end) {
	  List<TileNode> nodeList = map.get(start);
	    if (nodeList == null) {
	      nodeList = new ArrayList<>();
	    }
	    map.put(start, nodeList);
	    nodeList.add(end);
	    }
  
  public List<TileNode> getUnvisitedNeighbours(final TileNode node) {
	    final List<TileNode> neighbours = map.get(node);
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
  
}
