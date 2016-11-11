import java.util.List;
import javax.swing.JFrame;

public class MazeDriver {
	public static void main(String args[]) {
		
		Maze maze = Maze.generateMaze("mazes/SimpleMaze.txt");
		MazeProblem mazeProblem = new MazeProblem(0, 0, 19, 19, maze);
		
		List<UUSearchProblem.UUSearchNode> path;
		
		path = mazeProblem.breadthFirstSearch();
		System.out.println("bfs path length:  " + path.size() + " " + path);
		mazeProblem.printStats();
		System.out.println("-----");
		
		path = mazeProblem.astarSearch();
		System.out.println("astar path length:  " + path.size() + " " + path);
		mazeProblem.printStats();
		
		JFrame frame = new JFrame("Mazeworld");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new MazeAnimation(maze, path));
		frame.pack();
		frame.setVisible(true);
	}
}