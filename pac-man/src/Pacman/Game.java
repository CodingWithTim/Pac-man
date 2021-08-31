package Pacman;

import javax.swing.JFrame;

public class Game {
	private JFrame frame;
	private Pacman player;
	private Map background;
	private Draw graphics;
	
	private Ghost blinky;
	private Ghost pinky;
	private Ghost inky;
	private Ghost clyde;
	
	private final int width = 570;
	private final int height = 600;
	
	public Game() {
		frame = new JFrame();
		player = new Pacman(17, 17);
		
		blinky = new Ghost("BLINKY", 9, 7);
		pinky = new Ghost("PINKY", 8, 9);
		inky = new Ghost("INKY", 9, 9);
		clyde = new Ghost("CLYDE", 10, 9);
		
		background = new Map(width, height);
		graphics = new Draw(background, player, blinky, pinky, inky, clyde, width, height);
		
		frame.add(graphics);
		frame.setTitle("AI Plays PAC-man");
		frame.setSize(width, height);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	//methods
}
