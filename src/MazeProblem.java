import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class MazeProblem extends UUSearchProblem {

	private Point goal;
	private Maze maze;
	
	public MazeProblem(int sx, int sy, int gx, int gy, Maze maze) {
		this.startNode = new MazeNode(sx, sy, 0);
		this.goal = new Point(gx, gy);
		this.maze = maze;
	}
	
	public class MazeNode implements UUSearchNode {
		
		private Point state;
		private int cost;
		
		public MazeNode(int x, int y, int cost) {
			this.state = new Point(x, y);
			this.cost = cost;
		}

		@Override
		public ArrayList<UUSearchNode> getSuccessors() {
			ArrayList<UUSearchNode> successors = new ArrayList<>();
			for (int i = 0; i < Maze.moves.length; i++) {
				int updatedX = state.x + Maze.moves[i][0];
				int updatedY = state.y + Maze.moves[i][1];
				if (maze.isEmptyCell(updatedX, updatedY)) {
					successors.add(new MazeNode(updatedX, updatedY, cost+1));
				} 
			}
			return successors;
		}

		@Override
		public boolean goalTest() {
			return (state.equals(goal));
		}
		
		@Override
		public String toString() {
			String str = "[" + state.x + ", " + state.y +"]";
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
			list.add(state);
			return list;
		}
		
	}
	
}