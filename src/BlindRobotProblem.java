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
		this.startNode = new BlindRobotNode(m.getEmptyCells(), 0);
		this.goal = new Point(gx, gy);
		this.maze = m;
	}
	
	public class BlindRobotNode implements UUSearchNode {
		
		private Set<Point> state;
		private int cost;
		
		public BlindRobotNode(Set<Point> xy, int cost) {
			this.state = xy;
			this.cost = cost;
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
						updatedXY.add(new Point(x, y));
					}
					else {	
						updatedXY.add(new Point(s.x, s.y));
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
			return state.size() == 1 && state.iterator().next().equals(goal);
		}
		
		@Override
		public boolean equals(Object other) {
			return state.equals(((BlindRobotNode) other).state);
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
			int heuristic = maze.height*10000;
			if (state.size() > 1) {
				heuristic = state.size()*maze.height*10000;
				return heuristic + cost;
			}
			for (Point p : state) {
				int xdist = goal.x - p.x;
				int ydist = goal.y - p.y;
				int xydist = Math.abs(xdist) + Math.abs(ydist);
				if (xydist < heuristic) heuristic = xydist;
			}
			return heuristic + cost;
		}
		
		@Override
		public int compareTo(UUSearchNode n) {
			if (priority() > n.priority()) {
				return 1;
			}
			else if (priority() < n.priority()) {
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
		
		@Override
		public String toString() {
			String str = "{";
			Iterator<Point> states = state.iterator();
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
		
	}
}