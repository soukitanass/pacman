package ca.usherbrooke.pacman.model.movements.astar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import ca.usherbrooke.pacman.model.position.Position;

/**
 * A Star Algorithm
 *
 * @version 2.0, 2017-02-23
 * @author Marcelo Surriabre
 *         https://github.com/marcelo-s/A-Star-Java-Implementation
 */
public class AStar {
  private static int DEFAULT_HV_COST = 10; // Horizontal - Vertical Cost
  private int hvCost;
  private Node[][] searchArea;
  private PriorityQueue<Node> openList;
  private List<Node> closedList;
  private Node initialNode;
  private Node finalNode;

  public AStar(int rows, int cols, Node initialNode, Node finalNode, int hvCost) {
    this.hvCost = hvCost;
    setInitialNode(initialNode);
    setFinalNode(finalNode);
    this.searchArea = new Node[rows][cols];
    this.openList = new PriorityQueue<Node>(new Comparator<Node>() {
      @Override
      public int compare(Node node0, Node node1) {
        return node0.getF() < node1.getF() ? -1 : node0.getF() > node1.getF() ? 1 : 0;
      }
    });
    setNodes();
    this.closedList = new ArrayList<Node>();
  }

  public AStar(int rows, int cols, Node initialNode, Node finalNode) {
    this(rows, cols, initialNode, finalNode, DEFAULT_HV_COST);
  }

  private void setNodes() {
    for (int i = 0; i < searchArea.length; i++) {
      for (int j = 0; j < searchArea[0].length; j++) {
        Node node = new Node(i, j);
        node.calculateHeuristic(getFinalNode());
        this.searchArea[i][j] = node;
      }
    }
  }

  public void setBlocks(List<Position> positions) {
    for (Position position : positions) {
      setBlock(position.getX(), position.getY());
    }
  }

  public List<Node> findPath() {
    openList.add(initialNode);
    while (!isEmpty(openList)) {
      Node currentNode = openList.poll();
      closedList.add(currentNode);
      if (isFinalNode(currentNode)) {
        return getPath(currentNode);
      } else {
        addAdjacentNodes(currentNode);
      }
    }
    return new ArrayList<Node>();
  }

  private List<Node> getPath(Node currentNode) {
    List<Node> path = new ArrayList<Node>();
    path.add(currentNode);
    Node parent;
    while ((parent = currentNode.getParent()) != null) {
      path.add(0, parent);
      currentNode = parent;
    }
    return path;
  }

  private void addAdjacentNodes(Node currentNode) {
    addAdjacentUpperRow(currentNode);
    addAdjacentMiddleRow(currentNode);
    addAdjacentLowerRow(currentNode);
  }

  private void addAdjacentLowerRow(Node currentNode) {
    int row = currentNode.getRow();
    int col = currentNode.getCol();
    int lowerRow = row + 1;
    if (lowerRow < getSearchArea().length) {
      checkNode(currentNode, col, lowerRow, getHvCost());
    }
  }

  private void addAdjacentMiddleRow(Node currentNode) {
    int row = currentNode.getRow();
    int col = currentNode.getCol();
    int middleRow = row;
    if (col - 1 >= 0) {
      checkNode(currentNode, col - 1, middleRow, getHvCost());
    }
    if (col + 1 < getSearchArea()[0].length) {
      checkNode(currentNode, col + 1, middleRow, getHvCost());
    }
  }

  private void addAdjacentUpperRow(Node currentNode) {
    int row = currentNode.getRow();
    int col = currentNode.getCol();
    int upperRow = row - 1;
    if (upperRow >= 0) {
      checkNode(currentNode, col, upperRow, getHvCost());
    }
  }

  private void checkNode(Node currentNode, int col, int row, int cost) {
    Node adjacentNode = getSearchArea()[row][col];
    if (!adjacentNode.isBlock() && !getClosedList().contains(adjacentNode)) {
      if (!getOpenList().contains(adjacentNode)) {
        adjacentNode.setNodeData(currentNode, cost);
        getOpenList().add(adjacentNode);
      } else {
        boolean changed = adjacentNode.checkBetterPath(currentNode, cost);
        if (changed) {
          // Remove and Add the changed node, so that the PriorityQueue can sort again its
          // contents with the modified "finalCost" value of the modified node
          getOpenList().remove(adjacentNode);
          getOpenList().add(adjacentNode);
        }
      }
    }
  }

  private boolean isFinalNode(Node currentNode) {
    return currentNode.equals(finalNode);
  }

  private boolean isEmpty(PriorityQueue<Node> openList) {
    return openList.size() == 0;
  }

  private void setBlock(int row, int col) {
    this.searchArea[row][col].setBlock(true);
  }

  public Node getInitialNode() {
    return initialNode;
  }

  public void setInitialNode(Node initialNode) {
    this.initialNode = initialNode;
  }

  public Node getFinalNode() {
    return finalNode;
  }

  public void setFinalNode(Node finalNode) {
    this.finalNode = finalNode;
  }

  public Node[][] getSearchArea() {
    return searchArea;
  }

  public void setSearchArea(Node[][] searchArea) {
    this.searchArea = searchArea;
  }

  public PriorityQueue<Node> getOpenList() {
    return openList;
  }

  public void setOpenList(PriorityQueue<Node> openList) {
    this.openList = openList;
  }

  public List<Node> getClosedList() {
    return closedList;
  }

  public void setClosedList(List<Node> closedList) {
    this.closedList = closedList;
  }

  public int getHvCost() {
    return hvCost;
  }

  public void setHvCost(int hvCost) {
    this.hvCost = hvCost;
  }
}

