// AUTHOR: MITCHELL REVERS, WITH ADDITIONAL CODE PROVIDED BY DAVID BALKCOM

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public abstract class UUSearchProblem {
	
	// used to store performance information about search runs.
	//  these should be updated during the process of searches

	// see methods later in this class to update these values
	protected int nodesExplored;
	protected int maxMemory;

	protected UUSearchNode startNode;
	
	protected interface UUSearchNode extends Comparable<UUSearchNode> {
		public ArrayList<UUSearchNode> getSuccessors();
		public boolean goalTest();
		public int priority();
		public List<Point> getPoints();
	}
	
	public class NodeKey {	
		UUSearchNode previous;
		int priority;
		public NodeKey(UUSearchNode prev, int prior) {
			previous = prev;
			priority = prior;
		}
	}
	
	public List<UUSearchNode> astarSearch() {
		resetStats();
		
		// set up data structures
		PriorityQueue<UUSearchNode> frontier = new PriorityQueue<UUSearchNode>();
		frontier.add(startNode);
		HashMap<UUSearchNode, NodeKey> explored = new HashMap<UUSearchNode, NodeKey>();
		explored.put(startNode, new NodeKey(null, startNode.priority()));
		
		// loop while priority queue is not empty
		while (!frontier.isEmpty()){
			UUSearchNode currentNode = frontier.poll();
			if (currentNode.goalTest()) {
				return astarBackchain(currentNode, explored);
			}	
			ArrayList<UUSearchNode> successors = currentNode.getSuccessors();
			for (UUSearchNode successor : successors) {
				if (!explored.containsKey(successor)) {
					explored.put(successor, new NodeKey(currentNode, successor.priority()));
					frontier.add(successor);
					incrementNodeCount();
					updateMemory(explored.size() + frontier.size());
				} 
				else if (explored.get(successor) != null) {
					if (successor.priority() < explored.get(successor).priority) {
						explored.remove(successor);
						explored.put(successor, new NodeKey(currentNode, successor.priority()));
						frontier.add(successor);
					}
				}
			}
		}
		return null;
	}
	
	// breadthFirstSearch:  return a list of connecting Nodes, or null
	// no parameters, since start and goal descriptions are problem-dependent.
	//  therefore, constructor of specific problems should set up start
	//  and goal conditions, etc.
	
	public List<UUSearchNode> breadthFirstSearch() { 
		resetStats();
		
		// set up data structures
		Queue<UUSearchNode> frontier = new LinkedList<UUSearchNode>();
		frontier.add(startNode);
		HashMap<UUSearchNode, UUSearchNode> explored = new HashMap<UUSearchNode, UUSearchNode>();
		explored.put(startNode, null);
		
		// loop while queue is not empty
		while (!frontier.isEmpty()){
			UUSearchNode currentNode = frontier.remove();
			if (currentNode.goalTest()) {
				return bfsBackchain(currentNode, explored);
			}
			ArrayList<UUSearchNode> successors = currentNode.getSuccessors();
			for (UUSearchNode successor : successors) {
				if (!explored.containsKey(successor)) {
					explored.put(successor, currentNode);
					frontier.add(successor);
					incrementNodeCount();
					updateMemory(explored.size() + frontier.size());
				}
			}
		}
		return null;
	}
	
	// backchain should only be used by bfs, not the recursive dfs
	private List<UUSearchNode> bfsBackchain(UUSearchNode node,
			HashMap<UUSearchNode, UUSearchNode> visited) {
		
		// set up data structures
		LinkedList<UUSearchNode> chain = new LinkedList<UUSearchNode>();
		UUSearchNode current = node;
		
		// backchain through the hashmap starting with current node
		while (current != null) {
			chain.addFirst(current);
			current = visited.get(current);
		}
		return chain;
	}
	
	// backchain should only be used by bfs, not the recursive dfs
	private List<UUSearchNode> astarBackchain(UUSearchNode node,
			HashMap<UUSearchNode, NodeKey> visited) {
			
		// set up data structures
		LinkedList<UUSearchNode> chain = new LinkedList<UUSearchNode>();
		UUSearchNode current = node;
		
		// backchain through the hashmap starting with current node
		while (current != null) {
			chain.addFirst(current);
			current = visited.get(current).previous;
		}
		return chain;
	}
	
	protected void resetStats() {
		nodesExplored = 0;
		maxMemory = 0;
	}
	
	protected void printStats() {
		System.out.println("Nodes explored during last search:  " + nodesExplored);
		System.out.println("Maximum memory usage during last search " + maxMemory);
	}
	
	protected void updateMemory(int currentMemory) {
		maxMemory = Math.max(currentMemory, maxMemory);
	}
	
	protected void incrementNodeCount() {
		nodesExplored++;
	}

}