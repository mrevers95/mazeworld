import java.awt.Graphics;
import java.awt.Point;
import java.io.*;
import java.util.ArrayList;
//import javax.swing.JFrame;
import java.util.HashSet;
import java.util.Set;

public class Maze {
	
	public int width;
	public int height;
	public int spaces;
	private char [][] cells;
	
	public static int[][] moves = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
	
	public static Maze generateMaze(String fileName) {
		
		Maze maze = new Maze();
		
		try {
			FileReader inputFile = new FileReader(fileName);
			BufferedReader bufferReader = new BufferedReader(inputFile);
			ArrayList<ArrayList<Character>> columns = new ArrayList<ArrayList<Character>>();
			
			String line;
			while ((line = bufferReader.readLine()) != null)   {
				ArrayList<Character> row = new ArrayList<Character>();
				for (int j = 0; j < line.length(); j++) {
					row.add(line.charAt(j));
				}
				columns.add(row);	
			}
			bufferReader.close();
			
			maze.width = columns.get(0).size();
			maze.height = columns.size();
			maze.cells = new char[maze.height][maze.width];
			maze.spaces = 0;
			for (int i = 0; i < maze.height; i++) {
				for (int j = 0; j < maze.width; j++) {
					if (columns.get(i).get(j) == '.') {
						maze.spaces++;
					}	
					maze.cells[maze.height-i-1][j] = columns.get(i).get(j);;
				}
			}
			return maze;
		}
		catch (Exception e) {
			System.out.println(e.toString());
			return null;
		} 
	}
	
	public Set<Point> getEmptyCells() {
		Set<Point> sxy = new HashSet<Point>();
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (isEmptyCell(i, j)) {
					sxy.add(new Point(i, j));
				}
			}
		}
		return sxy;
	}
	
	public boolean isEmptyCell(int x, int y) {
		return x >= 0 && y >= 0 && x < width && y < height && cells[y][x] != '#';
	}
	
	public void paintMaze(Graphics g) {
		int xScale = 400/width;
		int yScale = 400/height;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				g.drawRect(i*xScale, 400-(j+1)*yScale, xScale, yScale);
				if (!isEmptyCell(i, j)) {
					g.fillRect(i*xScale, 400-(j+1)*yScale, xScale, yScale);
				}
			}
		}
	}
	
}