import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class MazeProblem extends UUSearchProblem {

	private Point goal;
	private Maze maze;
	
	public MazeProblem(int sx, int sy, int gx, int gy, Maze m) {
		startNode = new MazeNode(sx, sy, 0);
		goal = new Point(gx, gy);
		maze = m;
	}
	
	public class MazeNode implements UUSearchNode {
		
		private Point state;
		private int cost;
		
		public MazeNode(int x, int y, int c) {
			state = new Point(x, y);
			this.cost = c;
		}

		@Override
		public ArrayList<UUSearchNode> getSuccessors() {
			ArrayList<UUSearchNode> successors = new ArrayList<UUSearchNode>();
			for (int i = 0; i < Maze.moves.length; i++) {
				int updatedX = this.state.x + Maze.moves[i][0];
				int updatedY = this.state.y + Maze.moves[i][1];
				if (maze.isEmptyCell(updatedX, updatedY)) {
					successors.add(new MazeNode(updatedX, updatedY, cost+1));
				} 
			}
			return successors;
		}

		@Override
		public boolean goalTest() {
			return (this.state.equals(goal));
		}
		
		@Override
		public String toString() {
			String str = "[" + this.state.x + ", " + this.state.y +"]";
			return str;
		}
		
		@Override
		public boolean equals(Object other) {
			return (state.equals(((MazeNode) other).state));
		}
		
		@Override
		public int hashCode() {
			return state.x*10 + state.y;
		}
		
		@Override
		public int priority() {
			int xdist = goal.x - state.x;
			int ydist = goal.y - state.y;
			int heuristic = Math.abs(xdist) + Math.abs(ydist);
			return heuristic + cost;
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
		public List<Point> getPoints() {
			List<Point> list = new ArrayList<Point>();
			list.add(state);
			return list;
		}
		
	}
	
}