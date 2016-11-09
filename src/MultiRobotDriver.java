

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class MultiRobotDriver {
	public static void main(String args[]) {
		
		Maze maze = Maze.generateMaze("mazes/MultiRobotMaze1.txt");
		List<Point> start = new ArrayList<Point>();
		start.add(new Point(0, 1));
		start.add(new Point(5, 1));
		List<Point> goal = new ArrayList<Point>();
		goal.add(new Point(5, 1)); 
		goal.add(new Point(0, 1));
		MultiRobotProblem mrProblem = new MultiRobotProblem(start, goal, maze);
		
		List<UUSearchProblem.UUSearchNode> path;
		
		path = mrProblem.breadthFirstSearch();	
		System.out.println("bfs path length:  " + path.size() + " " + path);
		mrProblem.printStats();
		System.out.println("-----");
		
		path = mrProblem.astarSearch();	
		System.out.println("astar path length:  " + path.size() + " " + path);
		mrProblem.printStats();
		System.out.println("-----");
		
		maze = Maze.generateMaze("mazes/MultiRobotMaze2.txt");
		start = new ArrayList<Point>();
		start.add(new Point(1, 1));
		start.add(new Point(2, 1));
		start.add(new Point(4, 1));
		start.add(new Point(3, 1));
		goal = new ArrayList<Point>();
		goal.add(new Point(4, 1)); 
		goal.add(new Point(3, 1));
		goal.add(new Point(2, 1));
		goal.add(new Point(1, 1));
		mrProblem = new MultiRobotProblem(start, goal, maze);
		
		path = mrProblem.breadthFirstSearch();	
		System.out.println("bfs path length:  " + path.size() + " " + path);
		mrProblem.printStats();
		System.out.println("-----");
		
		path = mrProblem.astarSearch();	
		System.out.println("astar path length:  " + path.size() + " " + path);
		mrProblem.printStats();
		System.out.println("-----");
		
		maze = Maze.generateMaze("mazes/MultiRobotMaze3.txt");
		start = new ArrayList<Point>();
		start.add(new Point(0, 0));
		start.add(new Point(6, 6));
		start.add(new Point(6, 0));
		goal = new ArrayList<Point>();
		goal.add(new Point(6, 6)); 
		goal.add(new Point(6, 0));
		goal.add(new Point(0, 0));
		mrProblem = new MultiRobotProblem(start, goal, maze);
		
		path = mrProblem.breadthFirstSearch();	
		System.out.println("bfs path length:  " + path.size() + " " + path);
		mrProblem.printStats();
		System.out.println("-----");
		
		path = mrProblem.astarSearch();	
		System.out.println("astar path length:  " + path.size() + " " + path);
		mrProblem.printStats();
		System.out.println("-----");
		
		maze = Maze.generateMaze("mazes/MultiRobotMaze4.txt");
		start = new ArrayList<Point>();
		start.add(new Point(0, 0));
		start.add(new Point(13, 20));
		goal = new ArrayList<Point>();
		goal.add(new Point(13, 20)); 
		goal.add(new Point(0, 0));
		mrProblem = new MultiRobotProblem(start, goal, maze);
		
		path = mrProblem.breadthFirstSearch();	
		System.out.println("bfs path length:  " + path.size() + " " + path);
		mrProblem.printStats();
		System.out.println("-----");
		
		path = mrProblem.astarSearch();	
		System.out.println("astar path length:  " + path.size() + " " + path);
		mrProblem.printStats();
		System.out.println("-----");
		
		maze = Maze.generateMaze("mazes/MultiRobotMaze5.txt");
		start = new ArrayList<Point>();
		start.add(new Point(0, 0));
		start.add(new Point(19, 8));
		goal = new ArrayList<Point>();
		goal.add(new Point(19, 8)); 
		goal.add(new Point(0, 0));
		mrProblem = new MultiRobotProblem(start, goal, maze);
		
		path = mrProblem.breadthFirstSearch();	
		System.out.println("bfs path length:  " + path.size() + " " + path);
		mrProblem.printStats();
		System.out.println("-----");
		
		path = mrProblem.astarSearch();	
		System.out.println("astar path length:  " + path.size() + " " + path);
		mrProblem.printStats();
		System.out.println("-----");
		
		
		// place this after the path you would like to see displayed
		JFrame frame = new JFrame("Mazeworld");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new MazeAnimation(maze, path));
		frame.pack();
		frame.setVisible(true);

	}
}