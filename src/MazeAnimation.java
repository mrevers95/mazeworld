import java.awt.*;
import java.util.List;
import javax.swing.*;
 
@SuppressWarnings("serial")
public class MazeAnimation extends JPanel {

	private static final int frameWidth = 400;
	private static final int frameHeight = 400;
	private static final int updateRate = 200;   // in milliseconds
	private Color[] color = {Color.red, Color.blue, Color.green};
  
	private Maze maze;
	private List<UUSearchProblem.UUSearchNode> path;
	private int step = 0;	
	
	public MazeAnimation(Maze m, List<UUSearchProblem.UUSearchNode> p) {
		this.maze = m;
		this.path = p;
		this.setPreferredSize(new Dimension(frameWidth, frameHeight));
		Thread mazeThread = new MazeThread();
		mazeThread.start();
	}
	
	public class MazeThread extends Thread {
		public void run() {
			while (step < path.size()) { 
				repaint();
				try {
					Thread.sleep(updateRate);
				} 
				catch (InterruptedException e) {
					System.out.println(e.toString());
				}
				step += 1;
			}
		}
	}
  
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int xScale = frameWidth/maze.width;
		int yScale = frameHeight/maze.height;
		maze.paintMaze(g);
		List<Point> points = path.get(step).getPoints();
		for (int i = 0; i < points.size(); i++) {
			g.setColor(color[i%3]);
			g.fillOval(points.get(i).x*xScale, frameHeight-(points.get(i).y+1)*yScale, xScale, yScale);
		}
	}

}