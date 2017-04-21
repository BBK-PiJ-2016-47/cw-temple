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
  
  public TileNode getUnvisitedChildNode(final TileNode node) {
	    final List<TileNode> nodeList = map.get(node);
	    if (nodeList != null) {
	      for (int i = 0; i < nodeList.size(); i++) {
	        final TileNode temp = nodeList.get(i);
	        if (!temp.getVisited()) {
	          return temp;
	        }
	      }
	    }
	    return null;
	  }
  
}
