import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class MultiRobotSyncProblem extends UUSearchProblem {
	
	private Maze maze;
	private List<Point> goal;
	
	public MultiRobotSyncProblem(List<Point> sxy, List<Point> gxy, Maze m) {
		startNode = new MultiRobotSyncNode(sxy, 0);
		goal = gxy;
		maze = m;
	}
	
	public class MultiRobotSyncNode implements UUSearchNode {

		private int cost;
		private List<Point> state;
		
		public MultiRobotSyncNode(List<Point> xy, int c) {
			state = new ArrayList<Point>();
			this.state = xy;
			cost = c;
		}
		
		@Override
		public ArrayList<UUSearchNode> getSuccessors() {
			ArrayList<UUSearchNode> successors = new ArrayList<UUSearchNode>();
			
			// make a list of possible moves for each robot
			List<List<Point>> possibleMoves = new ArrayList<List<Point>>();
			for (int i = 0; i < state.size(); i++) {
				List<Point> list = new ArrayList<Point>();
				for (int j = 0; j < Maze.moves.length; j++) {
					int updatedX = this.state.get(i).x + Maze.moves[j][0];
					int updatedY = this.state.get(i).y + Maze.moves[j][1];
					if (maze.isEmptyCell(updatedX, updatedY)) {
						list.add(new Point(updatedX, updatedY));
					}
				}
				possibleMoves.add(list);
			}
			
			// generate all permutations from the moves above recursively
			List<Point> current = new ArrayList<Point>();
			generatePermutations(possibleMoves, successors, 0, current);
			
			return successors;
		}
		
		public void generatePermutations(List<List<Point>> possibleMoves, 
				ArrayList<UUSearchNode> successors, int index, List<Point> current) {
			// base case
			if (index == state.size()) {
				successors.add(new MultiRobotSyncNode(current, cost+1));
				return;
			}
			// recursive case
			for (int i = 0; i < possibleMoves.get(index).size(); i++) {
				List<Point> updatedCurrent = new ArrayList<Point>();
				updatedCurrent.addAll(current);
				// check if robots are on same space
				if (updatedCurrent.contains(possibleMoves.get(index).get(i))) {
					return;
				}
				updatedCurrent.add(possibleMoves.get(index).get(i));
				generatePermutations(possibleMoves, successors, index+1, updatedCurrent);
			}
		}
		
		public boolean isOccupiedCell(int x, int y) {
			for (int i = 0; i < this.state.size(); i++) {
				if (x == this.state.get(i).x && y == this.state.get(i).y) {
					return true;
				}
			}
			return false;
		}
		
		@Override
		public boolean goalTest() {
			return state.equals(goal);
		}
		
		@Override
		public String toString() {
			String str = "{";
			for (int i = 0; i < this.state.size(); i++) {
				str += ("[" + this.state.get(i).x + ", " + this.state.get(i).y + "]");
				if (i != this.state.size() - 1) {
					str += " ";
				}
				else {
					str += "}";
				}
			}
			return str;
		}
		
		@Override
		public boolean equals(Object other) {
			return state.equals(((MultiRobotSyncNode) other).state); 	
		}
		
		@Override
		public int hashCode() {
			int sum = 0;
			for (int j = 0; j < this.state.size(); j++) {
				sum = state.get(j).x*10^(j*2) +  state.get(j).y*10^(j*2+1); 
			}
			return sum;
		}
		
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
		public int priority() {
			int heuristic = 0;
			for (int i = 0; i < this.state.size(); i++) {
				int xdist = goal.get(i).x - state.get(i).x;
				int ydist = goal.get(i).y - state.get(i).y;
				heuristic += Math.abs(xdist) + Math.abs(ydist);
			}
			return heuristic + cost;
		}

		@Override
		public List<Point> getPoints() {
			List<Point> list = new ArrayList<Point>();
			for (int i = 0; i < state.size(); i++) {
				list.add(state.get(i));
			}
			return list;
		}
		
	}
	
}