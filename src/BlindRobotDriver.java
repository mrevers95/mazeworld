import java.util.List;

import javax.swing.JFrame;

public class BlindRobotDriver {
	public static void main(String args[]) {
		
		Maze maze = Maze.generateMaze("mazes/BlindRobotMaze1.txt");
		BlindRobotProblem brProblem = new BlindRobotProblem(5, 5, maze);
		
		List<UUSearchProblem.UUSearchNode> path;
		
		path = brProblem.breadthFirstSearch();
		System.out.println("bfs path length:  " + path.size() + " " + path);
		brProblem.printStats();
		System.out.println("-----");
		
		path = brProblem.astarSearch();
		System.out.println("a* path length:  " + path.size() + " " + path);
		brProblem.printStats();
		System.out.println("-----");
		
		maze = Maze.generateMaze("mazes/BlindRobotMaze2.txt");
		brProblem = new BlindRobotProblem(2, 2, maze);
		
		path = brProblem.astarSearch();
		System.out.println("a* path length:  " + path.size() + " " + path);
		brProblem.printStats();
		System.out.println("-----");
		
		JFrame frame = new JFrame("Mazeworld");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new MazeAnimation(maze, path));
		frame.pack();
		frame.setVisible(true);

	}
}