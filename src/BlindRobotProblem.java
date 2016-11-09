

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class BlindRobotProblem extends UUSearchProblem {

	private Maze maze;
	private Point goal;
	
	public BlindRobotProblem(int gx, int gy, Maze m) {
		Set<Point> empties = m.getEmptyCells();
		startNode = new BlindRobotNode(empties, 0);
		goal = new Point(gx, gy);
		maze = m;
	}
	
	public class BlindRobotNode implements UUSearchNode {
		
		private Set<Point> state;
		private int cost;
		
		public BlindRobotNode(Set<Point> xy, int c) {
			state = new HashSet<Point>();
			this.state = xy;
			cost = c;
		}

		@Override
		public ArrayList<UUSearchNode> getSuccessors() {
			ArrayList<UUSearchNode> successors = new ArrayList<UUSearchNode>();
			for (int j = 0; j < Maze.moves.length; j++) {
				Set<Point> updatedXY = new HashSet<Point>();
				for (Point s : state) {
					int x = s.x + Maze.moves[j][0];
					int y = s.y + Maze.moves[j][1];
					if (maze.isEmptyCell(x, y)) {
						Point xy = new Point(x, y);	
						updatedXY.add(xy);
					}
					else {
						Point xy = new Point(s.x, s.y);	
						updatedXY.add(xy);
					}
				}
				if (updatedXY.size() > 0) {
					BlindRobotNode n = new BlindRobotNode(updatedXY, cost+1);
					successors.add(n);
				}
			}
			return successors;
		}
		
		@Override
		public boolean goalTest() {
			return (state.size() == 1 && state.iterator().next().equals(goal));
		}
		
		@Override
		public String toString() {
			String str = "{";
			Iterator<Point> states = this.state.iterator();
			while (states.hasNext()) {
				Point s = states.next();
				str += "[" + s.x + ", " + s.y + "]";
				if (states.hasNext()) {
					str += ", ";
				}
				else {
					str += "}";
				}
			}
			return str;
		}
		
		@Override
		public boolean equals(Object other) {
			Set<Point> b = ((BlindRobotNode) other).state;
			return this.state.equals(b);
		}
		
		@Override
		public int hashCode() {
			int n = 0, sum = 0;
			for (Point s : state) {
				sum = s.x*10^(n) +  s.y*10^(n+1);
				n += 2;
			}
			return sum;
		}
		
		public int priority() {
			if (this.state.size() > 1) {
				int heuristic = this.state.size()*maze.height*10000;
				return heuristic + cost;
			}
			int heuristic = maze.height*10000;
			for (Point p : state) {
				int xdist = goal.x - p.x;
				int ydist = goal.y - p.y;
				int xydist = Math.abs(xdist) + Math.abs(ydist);
				if (xydist < heuristic) heuristic = xydist;
			}
			return heuristic + cost;
		}
		
		/*
		public int priority() {
			int heuristic = maze.height*10000;
			for (Point p : state) {
				int xdist = goal.x - p.x;
				int ydist = goal.y - p.y;
				int xydist = Math.abs(xdist) + Math.abs(ydist);
				if (xydist < heuristic) heuristic = xydist;
			}
			return heuristic + cost;
		}
		*/
		
		@Override
		public int compareTo(UUSearchNode n) {
			if (this.priority() > n.priority()) {
				return 1;
			}
			else if (this.priority() < n.priority()) {
				return -1;
			}
			return 0;
		}

		@Override
		public List<Point> getPoints() {
			List<Point> list = new ArrayList<Point>();
			for (Point s : state) {
				list.add(s);
			}
			return list;
		}
		
	}
	
}